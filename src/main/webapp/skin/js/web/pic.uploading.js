$(function() {
    // ---------  Uploader -------------
    // ---------------------------------

    var Uploader = (function() {

        // -------setting-------
        // 如果使用原始大小，超大的图片可能会出现 Croper UI 卡顿，所以这里建议先缩小后再crop.
        var FRAME_WIDTH = 1600;

        var _ = WebUploader;
        var Uploader = _.Uploader;
        var uploaderContainer = $('.uploader-pic');
        var uploader, file;

        if (!Uploader.support()) {
            console.log('上传组件不支持您的浏览器！');
            throw new Error(
                'Upload plugin does not support the browser you are using.'
            );
        }

        // hook,
        // 在文件开始上传前进行裁剪。
        Uploader.register({
            'before-send-file': 'cropImage'
        }, {
            cropImage: function(file) {

                var data = file._cropData,
                    image, deferred;

                file = this.request('get-file',
                    file);
                deferred = _.Deferred();

                image = new _.Lib.Image();

                deferred.always(function() {
                    image.destroy();
                    image = null;
                });
                image.once('error', deferred.reject);
                image.once('load', function() {
                    image.crop(data.x, data
                        .y,
                        data.width,
                        data.height,
                        data.scale);
                });

                image.once('complete', function() {
                    var blob, size;

                    // 移动端 UC / qq 浏览器的无图模式下
                    // ctx.getImageData 处理大图的时候会报 Exception
                    // INDEX_SIZE_ERR: DOM Exception 1
                    try {
                        blob = image.getAsBlob();
                        size = file.size;
                        file.source = blob;
                        file.size = blob.size;

                        file.trigger(
                            'resize',
                            blob.size,
                            size
                        );

                        deferred.resolve();
                    } catch (e) {
                        //console.log(e);
                        // 出错了直接继续，让其上传原始图片
                        deferred.resolve();
                    }
                });

                file._info && image.info(file._info);
                file._meta && image.meta(file._meta);
                image.loadFromBlob(file.source);
                return deferred.promise();
            }
        });

        return {
            init: function(selectCb) {
                uploader = new Uploader({
                    pick: {
                        id: '.uploader-pic',
                        multiple: false
                    },
                    accept: {
                        // 文件描述
                        title: 'Images',
                        // 只允许的后缀名
                        extensions: 'jpg,jpeg,png',
                        mimeTypes: 'image/*'
                    },

                    // 设置用什么方式去生成缩略图。
                    thumb: {
                        quality: 70,

                        // 不允许放大
                        allowMagnify: false,

                        // 是否采用裁剪模式。如果采用这样可以避免空白内容。
                        crop: false
                    },

                    // 禁掉分块传输，默认是开起的。
                    chunked: false,

                    // 禁掉上传前压缩功能，因为会手动裁剪。
                    compress: {
                        width: 140,
                        height: 140,

                        // 图片质量，只有type为`image/jpeg`的时候才有效。
                        quality: 90,

                        // 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
                        allowMagnify: false,

                        // 是否允许裁剪。
                        crop: false,

                        // 是否保留头部meta信息。
                        preserveHeaders: true,

                        // 如果发现压缩后文件大小比原来还大，则使用原来图片
                        // 此属性可能会影响图片自动纠正功能
                        noCompressIfLarger: false,

                        // 单位字节，如果图片大小小于此值，不会采用压缩。
                        compressSize: 0
                    },

                    fileSingleSizeLimit: 5 *
                        1024 *
                        1024,

                    server: basePath + '/ucenter/user/updateUserPhoto',
                    swf: basePath + '/skin/media/Uploader.swf',
                    fileNumLimit: 1,
                    onError: function(type) {
                        switch (type) {
                            case 'Q_TYPE_DENIED':
                                TipsBox("body",
                                    "260px",
                                    basePath + "/skin/images/hint.png",
                                    "文件类型不符合要求!",
                                    1500,
                                    null);
                                break;
                            case 'Q_EXCEED_NUM_LIMIT':
                                uploader.reset();
                                //uploader.retry();
//                                TipsBox("body",
//                                    "260px",
//                                    basePath + "/skin/images/hint.png",
//                                    "文件数目超过限制!",
//                                    1500,
//                                    null);
                                break;
                            case 'F_EXCEED_SIZE':
                                TipsBox("body",
                                    "260px",
                                    basePath + "/skin/images/hint.png",
                                    "文件大小超过限制!",
                                    1500,
                                    null);
                                break;
                            default:
                                var args = [].slice
                                    .call(
                                        arguments,
                                        0);
                                //alert(args.join('\n'));
                                TipsBox("body",
                                    "260px",
                                    basePath + "/skin/images/hint.png",
                                    args[0],
                                    1500,
                                    null);
                                break;
                        }
                    }
                });

                uploader.on('fileQueued', function(_file) {
                    file = _file;

                    uploader.makeThumb(file,
                        function(
                            error, src) {

                            if (error) {
                                alert('不能预览');
                                return;
                            }

                            selectCb(src);

                        }, FRAME_WIDTH, 1); // 注意这里的 height 值是 1，被当成了 100% 使用。
                });
                
                uploader.on('uploadError', function(file,reason) {
                    //console.log("error:"+file.name+":"+reason);
                    TipsBox("body", "260px",
                        basePath + "/skin/images/hint.png",
                        "保存失败!", 1500,
                        null);
                });
                
                uploader.on('uploadBeforeSend', function(file,reason) {
                	BeginLoading();
                });
                
                uploader.on('uploadComplete', function(file,reason) {
                	EndLoading();
                	location.href = basePath + "/ucenter/user/viewUserPhoto";
                });

                uploader.on('uploadSuccess', function(file,
                    response) {
                	//console.log("response:"+response.success);
                    if (response.success) {
                        //console.log("result:"+response);
                    } else {
                        TipsBox("body", "260px",
                            basePath + "/skin/images/hint.png",
                            "保存失败!", 1500,
                            null);
                    }
                });
            },

            crop: function(data) {

                var scale = Croper.getImageSize().width /
                    file._info
                    .width;
                data.scale = scale;

                file._cropData = {
                    x: data.x1,
                    y: data.y1,
                    width: data.width,
                    height: data.height,
                    scale: data.scale
                };
            },

            reset: function() {
                uploader.reset();
            },

            upload: function() {
                uploader.upload();
            }
        };
    })();

    // ---------------------------------
    // ---------  Crpper ---------------
    // ---------------------------------
    var Croper = (function() {
        var container = $('.pic-prepare');
        var $image = container.find('.pic-init img');
        var btn = $('#pic-save');
        var isBase64Supported, callback;

        $image.cropper({
            aspectRatio: 1 / 1,
            preview: ".picp-preview",
            done: function(data) {
                //console.log(data);
            }
        });

        function srcWrap(src, cb) {

            // we need to check this at the first time.
            if (typeof isBase64Supported === 'undefined') {
                (function() {
                    var data = new Image();
                    var support = true;
                    data.onload = data.onerror = function() {
                        if (this.width != 1 || this.height !=
                            1) {
                            support = false;
                        }
                    };
                    data.src = src;
                    isBase64Supported = support;
                })();
            }

            if (isBase64Supported) {
                cb(src);
            } else {
                // otherwise we need server support.
                // convert base64 to a file.
                $.ajax('/', {
                    method: 'POST',
                    data: src,
                    dataType: 'json'
                }).done(function(response) {
                    if (response.result) {
                        cb(response.result);
                    } else {
                        alert("预览出错");
                    }
                });
            }
        }

        btn.on('click', function() {
            callback && callback($image.cropper(
                "getData"));
            return false;
        });

        return {
            setSource: function(src) {

                // 处理 base64 不支持的情况。
                // 一般出现在 ie6-ie8
                srcWrap(src, function(src) {
                    $image.hide();
                    $("#uploaderPic").css(
                        "visibility", "visible"
                    );
                    $("#pic-save").prop("disabled",
                        false).removeClass(
                        "btnDisabled");
                    $(".pic-init .pic-cover").hide();
                    $(".pic-init .pic-action").hide();
                    $image.cropper("setImgSrc", src);
                    setTimeout(function() {
                        $image.show();
                    }, 100);
                });

                container.removeClass(
                    'webuploader-element-invisible');

                return this;
            },

            getImageSize: function() {
                var img = $image.get(0);
                return {
                    width: img.naturalWidth,
                    height: img.naturalHeight
                };
            },

            setCallback: function(cb) {
                callback = cb;
                return this;
            },

            disable: function() {
                $image.cropper("disable");
                return this;
            },

            enable: function() {
                $image.cropper("enable");
                return this;
            }
        };

    })();

    var container = $('.uploader-pic');

    $("#uploaderPic").on("click", function() {
        //Uploader.reset();
    });

    Uploader.init(function(src) {

        Croper.setSource(src);

        // 隐藏选择按钮。
        //container.addClass('webuploader-element-invisible');

        // 当用户选择上传的时候，开始上传。
        Croper.setCallback(function(data) {
            Uploader.crop(data);
            Uploader.upload();
        });
    });

});
