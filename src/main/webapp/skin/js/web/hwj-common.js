/* nav for mobile
/***********************************************************/
$(document).bind("mobileinit", function() {
	// disable ajax nav
	$.mobile.ajaxEnabled = false;
});
$(function() {
    if ($(".footer").position().top <= $(document).height()-$(".footer").height()) {
        $(".footer").css("position","absolute");
        $(".teacher-footer").css("position","static").css("background-color","#fff");
    }

    if ($(".footer").length <= 0) {
        return false;
    }
});

/*
/***********************************************************
 * jQuery placeholder, fix for IE6,7,8,9
 */
var JPlaceHolder = {
    //检测
    _check: function() {
        return 'placeholder' in document.createElement('input');
    },
    //初始化
    init: function() {
        if (!this._check()) {
            this.fix();
        }
    },
    //修复
    fix: function() {
        jQuery(':input[placeholder]').each(function(index, element) {
            var self = $(this),
                txt = self.attr('placeholder');
            self.wrap($('<div></div>').css({
                position: 'relative',
                zoom: '1',
                border: 'none',
                background: 'none',
                padding: 'none',
                margin: 'none'
            }));
            var pos = self.position(),
                h = self.outerHeight(true),
                paddingleft = self.css('padding-left'),
                paddingtop = self.css('padding-top'),
                fontsize = 'medium';
            if (self.parent().parent().hasClass("act-sm-text") || self.parent()
                .parent().hasClass("act-sm-panel")) {
                pos.top = pos.top - 5;
                fontsize = 'small';
            }
            if ($(this).prop("tagName") == "TEXTAREA") {
                var holder = $('<span></span>').text(txt).css({
                    position: 'absolute',
                    left: pos.left,
                    top: pos.top,
                    height: h,
                    paddingLeft: paddingleft,
                    paddingTop: paddingtop,
                    color: '#aaa',
                    fontSize: 'small'
                }).appendTo(self.parent());
            } else {
                var holder = $('<span></span>').text(txt).css({
                    position: 'absolute',
                    left: pos.left,
                    top: pos.top,
                    height: h,
                    paddingLeft: paddingleft,
                    paddingTop: paddingtop,
                    color: '#aaa',
                    fontSize: fontsize
                }).appendTo(self.parent());
            }

            self.focusin(function(e) {
                holder.hide();
            }).focusout(function(e) {
                if (!self.val()) {
                    holder.show();
                }
            });
            holder.click(function(e) {
                holder.hide();
                self.focus();
            });
        });
    }
};
//执行
jQuery(function() {
    JPlaceHolder.init();
});

/*
/* after login
/************************************************************/
$(function() {
    $(".after-login").css("right", "-31px");
    $(".vip-xs-photo-wrap").parent().on({
        mouseenter: function() {
            $(".after-login").show();
        },
        mouseleave: function() {
            $(".after-login").hide();
        },
        click: function() {
            if ($(".after-login").css("display") != "none") {
                $(".after-login").hide();
            } else {
                $(".after-login").show();
            }
        }
    });
});

/* cover config
/***********************************************************/
$(function() {
    $(".cover").css("height", document.body.scrollHeight + "px");
});

/* full mask module
/* modal dialog function
/* <param>obj: as a panel for bearing modal dialog</param>
/* <param>tips: as the window's name</param>
/* <param>title: as the main title of the content</param>
/* <param>content_obj: as the main content object</param>
/* <param>showClose: whether showing close icon</param>
/* <param>fn: call the function if needed</param>
/***********************************************************/

var ShowMask = function() {
    $("body").append("<div class='full-mask'></div>");
    $(".full-mask").css("height", $(document).height() + "px");
    $(".full-mask").fadeIn(300);
};

var ShowLoadingMask = function() {
    $("body").append("<div class='loading-mask'></div>");
    $(".loading-mask").css("height", $(document).height() + "px");
    $(".loading-mask").fadeIn(300);
};

var ShowTMask = function() {
    $("body").append("<div class='full-tmask'></div>");
    $(".full-tmask").css("height", $(document).height() + "px");
    $(".full-tmask").fadeIn(300);
};

var CloseMask = function() {
    $(".full-mask").fadeOut(300);
    $(".full-mask").remove();
    $(".full-tmask").remove();
};

var CloseLoadingMask = function() {
    $(".loading-mask").fadeOut(300);
    $(".loading-mask").remove();
};

var ClosePopup = function() {
    $(".popup-wrap").fadeOut(300);
    CloseMask();
};

$(window).resize(function() {
    var wrapWidth = $("#time-table .popup-wrap").width();
    var wrapHeight = $("#time-table .popup-wrap").height();
    $("#time-table .popup-wrap").css("margin-left", -wrapWidth / 2 + "px");
    $("#time-table .popup-wrap").css("margin-top", -wrapHeight / 2 + "px");

    var cBoxWidth = $("#confirmBox").width();
    var cBoxHeight = $("#confirmBox").height();
    $("#confirmBox .popup-wrap").css("margin-left", -cBoxWidth / 2 + "px");
    $("#confirmBox .popup-wrap").css("margin-top", -cBoxHeight / 2 + "px");

    var tBoxWidth = $("#tipsBox").width();
    var tBoxHeight = $("#tipsBox").height();
    $("#tipsBox .popup-wrap").css("margin-left", -tBoxWidth / 2 + "px");
    $("#tipsBox .popup-wrap").css("margin-top", -tBoxHeight / 2 + "px");
});

$(function() {
    $("body").delegate("#confirmBox .popup-closer,#confirmBox .popup-cancel",
        "click",
        function() {
            $("#confirmBox").remove();
            CloseMask();
        });
});

function TipsBox(obj, width, icon, content, time, fn) {
    ShowTMask();
    if ($("#tipsBox") != undefined) {
        $("#tipsBox").remove();
    }
    $(obj).append("<div class='popup-wrap' id='tipsBox'></div>");

    // basic attribute
    $("#tipsBox").css("z-index", "10000");
    if (width != null) {
        $("#tipsBox").css("width", width);
    } else {
        $("#tipsBox").css("width", "100%");
    }

    // add main body
    $("#tipsBox").append("<div class='popup-body'></div>");
    $("#tipsBox .popup-body").append("<img class='popup-icon' src=" + icon + ">");
    $("#tipsBox .popup-body").append("<span class='popup-content'>" + content +
        "</span>");

    var wrapWidth = $("#tipsBox").width();
    var wrapHeight = $("#tipsBox").height();
    $("#tipsBox").css("margin-left", -wrapWidth / 2 + "px");
    $("#tipsBox").css("margin-top", -wrapHeight / 2 + "px");

    // show main panel
    $("#tipsBox").fadeIn(300);

    var t = 800;
    if (time != null) {
        t = time;
    }
    setTimeout(function() {
        $("#tipsBox").fadeOut(500);
        $("#tipsBox").remove();
        CloseMask();
    }, t);

    if (fn != null) {
        fn.call(this);
    }
}

function ConfirmBox(obj, width, tips, content, sub_content, confirm, fn) {
    ShowTMask();
    if ($("#confirmBox") != undefined) {
        $("#confirmBox").remove();
    }
    $(obj).append("<div class='popup-wrap' id='confirmBox'></div>");

    // basic attribute
    if (width != null) {
        $("#confirmBox").css("width", width);
    } else {
        $("#confirmBox").css("width", "100%");
    }

    // add header
    if (tips != null) {
        $("#confirmBox").append("<div class='popup-header clearfix'></div>");

        $("#confirmBox .popup-header").append("<div class='popup-tips'></div>");
        $("#confirmBox .popup-tips").html(tips);

        $("#confirmBox .popup-header").append("<div class='popup-closer'></div>");
    };

    // add main body
    $("#confirmBox").append("<div class='popup-body'></div>");
    $("#confirmBox .popup-body").append("<div class='popup-content'>" + content +
        "</div>");
    if (sub_content != null) {
        $("#confirmBox .popup-body").append("<div class='popup-sub-content'>" +
            sub_content + "</div>");
    };

    // add btn
    $("#confirmBox").append("<div class='popup-btngroup'></div>");
    $("#confirmBox .popup-btngroup").append(
        "<button class='popup-confirm'>确定</button>");
    $("#confirmBox .popup-btngroup").append("<button class='popup-cancel'>取消</button>");

    var wrapWidth = $("#confirmBox").width();
    var wrapHeight = $("#confirmBox").height();
    $("#confirmBox").css("margin-left", -wrapWidth / 2 + "px");
    $("#confirmBox").css("margin-top", -wrapHeight / 2 + "px");

    // show main panel
    $("#confirmBox").fadeIn(300);

    // setTimeout(function() {
    //      ClosePopup();
    // },700);

    if (confirm != null) {
        $("#confirmBox").delegate(".popup-confirm", "click", function() {
            confirm.call(this);
            $("#confirmBox").remove();
            CloseMask();
        });
    } else {
        $("#confirmBox").delegate(".popup-confirm", "click", function() {
            $("#confirmBox").remove();
            CloseMask();
        });
    }

    if (fn != null) {
        fn.call(this);
    }
}

function ShowBox(obj, width, tips, title, content_obj, confirm, fn) {
    $(obj).append("<div class='popup-wrap'></div>");

    // basic attribute
    if (width != null) {
        $(".popup-wrap").css("width", width);
    } else {
        $(".popup-wrap").css("width", "100%");
    }

    // add header
    if (tips != null) {
        $(".popup-wrap").append("<div class='popup-header clearfix'></div>");

        $(".popup-header").append("<div class='popup-tips'></div>");
        $(".popup-tips").html(tips);

        $(".popup-header").append("<div class='popup-closer'></div>");
    }

    // add main body
    $(".popup-wrap").append("<div class='popup-body'></div>");
    //$(content_obj).show();
    //$(".popup-body").append(content_obj);

    // add btn
    $(".popup-wrap").append("<div class='popup-btngroup'></div>");
    $(".popup-btngroup").append("<button class='popup-confirm'>确定</button>");
    //$(".popup-btngroup").append("<button class='popup-cancel'>取消</button>");

    var wrapWidth = $(".popup-wrap").width();
    var wrapHeight = $(".popup-wrap").height();
    $(".popup-wrap").css("margin-left", -wrapWidth / 2 + "px");
    $(".popup-wrap").css("margin-top", -wrapHeight / 2 + "px");

    // show main panel
    $(".popup-wrap").fadeIn(300);

    // setTimeout(function() {
    //      ClosePopup();
    // },700);

    if (confirm != null) {
        $("body").delegate(".popup-confirm", "click", function() {
            confirm.call(this);
            $(".popup-wrap").fadeOut(300);
            CloseMask();
        });
    } else {
        $("body").delegate(".popup-confirm", "click", function() {
            $(".popup-wrap").fadeOut(300);
            CloseMask();
        });
    }

    if (fn != null) {
        fn.call(this);
    }
}

/* common func -- OpenWindow()
/* // ajax: async: false
/***********************************************************/
function OpenWindow(url) {
    var target = '_blank';
    var a = document.createElement("a");
    a.setAttribute("href", url);
    if (target === null || target === '') {
        target = '_blank';
    }
    a.setAttribute("target", target);
    document.body.appendChild(a);
    if (a.click) {
        a.click();
    } else {
        try {
            var evt = document.createEvent('Event');
            a.initEvent('click', true, true);
            a.dispatchEvent(evt);
        } catch(e) {
            window.open(url);
        }
    }
    document.body.removeChild(a);
}

/* loading pop up
/* depend on full mask
/***********************************************************/
var commonLoadingTimer = null;
var commonLoadingSpeed = 500;

var RunTextLoading = function() {
    var baseText = "正在加载&nbsp;";
    var dot = "&middot;";
    setTimeout(function() {
        $(".common-loading-text").html(baseText);
    }, 0 * commonLoadingSpeed);
    setTimeout(function() {
        $(".common-loading-text").html(baseText + dot);
    }, 1 * commonLoadingSpeed);
    setTimeout(function() {
        $(".common-loading-text").html(baseText + dot + dot);
    }, 2 * commonLoadingSpeed);
    setTimeout(function() {
        $(".common-loading-text").html(baseText + dot + dot + dot);
    }, 3 * commonLoadingSpeed);
};

var BeginLoading = function() {

    ShowLoadingMask();

    $("body").append("<div class='common-loading'></div>");
    $(".common-loading").append("<img src='"+basePath+"/skin/images/common_loading.gif'>");
    $(".common-loading").append("<span class='common-loading-text'></span>");

    $(".common-loading-text").html("正在加载&nbsp;");

    setTimeout(function() {
        RunTextLoading();
    }, 0);

    commonLoadingTimer = setInterval(function() {
        RunTextLoading();
    }, 4 * commonLoadingSpeed);
};

var EndLoading = function(loading) {
    clearInterval(commonLoadingTimer);
    $(".common-loading").remove();
    CloseLoadingMask();
};

/* common pre loading function()
/* depend on ...
/***********************************************************/

/* login pop up and regis pop up
/* depend on full mask
/***********************************************************/
$(function() {

    var imgSubLeft = new Image();
    var imgSubRight = new Image();
    var imgBack = new Image();

   imgSubLeft.src = basePath + "/skin/images/subtitle_l.png";
   imgSubRight.src = basePath + "/skin/images/subtitle_r.png";
   imgBack.src = basePath + "/skin/images/subtitle_back.png";

    $(imgSubLeft).load(function() {
        $(".subtitle-img-l").css("visibility", "visible");
    });
    $(imgSubRight).load(function() {
        $(".subtitle-img-r").css("visibility", "visible");
    });

    /* login and regis part
    /************************************/
    var actionCommonList = {
        "login": function() {
            ShowMask();
            $("#loginPopup").fadeIn(500);
        },
        "regis": function() {
            ShowMask();
            $("#regisPopup").fadeIn(500);
        }
    };
    $("body").on("click", "[data-popup]", function() {
        var dataVal = $(this).data("popup");
        var action = actionCommonList[dataVal];
        if ($.isFunction(action)) {
            action();
        }
    });
    $("#loginPopup .close-icon").on("click", function() {
        $("#loginPopup").fadeOut(200);
        CloseMask();
    });
    $(".login-to-regis a").on("click", function() {
        $("#loginPopup").fadeOut(500);
        $("#regisPopup").fadeIn(500);
    });
    $("#loginPopup input").next("span").css("left", "35px").css("padding-top",
        0).css("line-height", "44px");

    $("#regisPopup .close-icon").on("click", function() {
        $("#regisPopup").fadeOut(200);
        CloseMask();
    });
    $(".regis-to-login a").on("click", function() {
        $("#regisPopup").fadeOut(500);
        $("#loginPopup").fadeIn(500);
    });

    /* psw part for student
    /************************************/
    var bodyEventList = {
        "psw-popup": function() {
            ShowMask();
            $("#psw-pop").fadeIn(500);
        }
    };
    $("body").delegate("[data-psw]", "click", function() {
        var getPswName = $(this).data("psw");
        var action = bodyEventList[getPswName];
        if ($.isFunction(action)) {
            action();
        }
    });
    $("#psw-pop .close-icon").on("click", function() {
        $("#psw-pop").fadeOut(200);
        CloseMask();
    });

    /* psw part for teacher
    /************************************/
    $("body").delegate("#tpsw-btn", "click", function() {
        ShowMask();
        $("#tpsw-pop").fadeIn(500);
    });
    $("#tpsw-pop .close-icon").on("click", function() {
        $("#tpsw-pop").fadeOut(200);
        CloseMask();
    });
});

/* mini entry event
/***********************************************************/
$(function() {
    // mini entry event
    var actionClickList = {
        //关闭主站页公众号
        "wbIconClose": function(obj) {
            obj.parents(".wb_icon").remove();
        },
        //关闭会员页服务号
        "wbCenterIconClose": function(obj) {
            obj.parents(".center-wb-icon").remove();
        },
        "sh-services": function(obj) {
            //TODO 联系客服
        },
        "sh-qrcode": function(obj) {
            
        },
        "goto-top": function(obj) {
            if (navigator.userAgent.indexOf('Firefox') >= 0){
                var initScrollTop = $(document).scrollTop()||$(window).scrollTop();
                var timer = setInterval(function() {
                    if (initScrollTop <= 0) {
                        $(document).scrollTop(0);
                        clearInterval(timer);
                    }
                    $(document).scrollTop(initScrollTop);
                    initScrollTop -= 160;
                }, 20);
            }
            else
            {
                $("body,html").animate({
                    scrollTop: 0
                }, "ease-in");
            }
        }
    };
    var actionMouseenterList = {
        "sh-services": function(obj) {
            obj.find("img").hide();
            obj.find("span").css("z-index", "2").show();
        },
        "sh-qrcode": function(obj) {
            obj.parent().find('.center-wb-icon').css('display','list-item');
            obj.find('.sh-qrcode-gray').hide();
            obj.find('.sh-qrcode-white').show();
        },
        "goto-top": function(obj) {
            obj.find("img").hide();
            obj.find("span").css("z-index", "2").show();
        }
    };
    var actionMouseleaveList = {
        "sh-services": function(obj) {
            obj.find("img").show();
            obj.find("span").css("z-index", "0").hide();
        },
        "sh-qrcode": function(obj) {
            obj.parent().find('.center-wb-icon').hide();
            obj.find('.sh-qrcode-white').hide();
            obj.find('.sh-qrcode-gray').show();
        },
        "goto-top": function(obj) {
            obj.find("img").show();
            obj.find("span").css("z-index", "0").hide();
        }
    };

    $("#sh-minientry").on("click", "[data-action]", function() {
        var actionName = $(this).data("action");
        var action = actionClickList[actionName];
        if ($.isFunction(action)) {
            action($(this));
        }
    });
    $("#sh-minientry").on("mouseenter", "[data-action]", function() {
        var actionName = $(this).data("action");
        var action = actionMouseenterList[actionName];
        if ($.isFunction(action)) {
            action($(this));
        }
    });
    $("#sh-minientry").on("mouseleave", "[data-action]", function() {
        var actionName = $(this).data("action");
        var action = actionMouseleaveList[actionName];
        if ($.isFunction(action)) {
            action($(this));
        }
    });

    $(document).scroll(function() {
        if ($(this).scrollTop() < 30) {
            $("#goto-top").css("visibility","hidden");
        } else {
            $("#goto-top").show().css("visibility","visible");
        }
    });
});
/* sh banner,top,header scroll event
/***********************************************************/
$(function() {
    $(document).scroll(function() {
        var val = 0;
        if ($("#shBannerFixed").hasClass("sh-banner-c")) {
            val = Number($(document).height()-$(window).height()-210-430);
        } else {
            val = Number($(document).height()-$(window).height()-210);
        }
        //console.log($(this).scrollTop()+","+Number($("body").height()-$(window).height()));
        if ($(window).scrollTop() >= val) {
            $("#shBannerFixed").hide(0);
        } else {
            $("#shBannerFixed").show(0);
        }
    });
});
