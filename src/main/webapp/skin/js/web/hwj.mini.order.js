/* course order ready module
/* config the width of course slides
/***********************************************************/
$(function() {
    $(window).resize(function() {
        $(".vip-order-list-inner").css("width", $(
                ".vip-order-list").width() -
            ($(".order-prev-wrap").width() + 31) * 2 + "px"
        );
    });
});

/* course order ready module
/* slide bg on vip-order-list
/***********************************************************/
$(function() {
    $(".vip-order-list-inner ul li:eq(1)").addClass("order-current").removeClass(
        "order-future");

    $(".vip-order-list").delegate(".order-future", "click", function(
        index) {
        var cur = $(this);
        setTimeout(function() {
            $(".order-current").find("div").css("color",
                "#498dca");
            $(".order-current").addClass("order-future")
                .removeClass(
                    "order-current");
        }, 100);
        var cLeft = cur.position().left;
        $("#mini-order-current").animate({
            left: cLeft
        }, 300, function() {
            cur.addClass("order-current").removeClass(
                "order-future");
            cur.find("div").css("color", "#fff");
            //         if (cur.offset().left < $(".vip-order-list-inner").offset().left) {
            //          $(".vip-order-slide").animate({
            //              left: $(".vip-order-slide").offset().left-$(".order-current").width() * (cur.index())
            //          }, 300);
            //         }
            //         if ((cur.offset().left+cur.width()) > ($(".vip-order-list-inner").offset().left+$(".vip-order-list-inner").width())) {
            // $(".vip-order-slide").animate({
            //              left: $(".vip-order-slide").offset().left-$(".order-current").width() * (cur.index())
            //          }, 300);
            //         };
        });
    });

    var _vipOrderSlide = 0;
    var _slideDistance = 500;
    $("ul").delegate(".order-next-active", "click", function() {
        _vipOrderSlide -= _slideDistance;
        if (_vipOrderSlide < 0) {
            $(".order-prev").addClass("order-prev-active").removeClass(
                "order-prev");
            $(".order-prev-wrap").addClass(
                "order-prev-active-wrap").removeClass(
                "order-prev-wrap");
        }
        var _sWidth = $(".vip-order-list-inner").width() - 1030;
        if (_vipOrderSlide <= _sWidth) {
            _vipOrderSlide = _sWidth;
            $(this).addClass("order-next").removeClass(
                "order-next-active");
            $(this).parent().addClass("order-next-wrap").removeClass(
                "order-next-active-wrap");
        } else {
            $(this).addClass("order-next-active").removeClass(
                "order-next");
            $(this).parent().addClass("order-next-active-wrap")
                .removeClass(
                    "order-next-wrap");
        }
        $(".vip-order-slide").animate({
            left: _vipOrderSlide
        }, 800, "easeInOutSine", function() {});
    });
    $("ul").delegate(".order-prev-active", "click", function() {
        _vipOrderSlide += _slideDistance;
        if (_vipOrderSlide >= 0) {
            _vipOrderSlide = 0;
            $(".order-prev-active").addClass("order-prev").removeClass(
                "order-prev-active");
            $(".order-prev-active-wrap").addClass(
                "order-prev-wrap").removeClass(
                "order-prev-active-wrap");
        }
        var _sWidth = $(".vip-order-list-inner").width() - 1030;
        if (_vipOrderSlide > _sWidth) {
            $(".order-next").addClass("order-next-active").removeClass(
                "order-next");
            $(".order-next-wrap").addClass(
                "order-next-active-wrap").removeClass(
                "order-next-wrap");
        }
        $(".vip-order-slide").animate({
            left: _vipOrderSlide
        }, 800, "easeInOutSine", function() {});
    });
});

/* mini download style
/***********************************************************/
$(function() {
    $(".mini-download").on({
        mouseenter: function() {
            $(this).stop();
            $(this).animate({
                right: 0
            }, 300);
        },
        mouseleave: function() {
            $(this).stop();
            $(this).animate({
                right: '-60px'
            }, 300);
        }
    });
});

/* mini btn event
/***********************************************************/
$(function() {
    var bkid = null;

    //取消小包课预约
    $(".mini-item").delegate("a[bkid^='qxyy']", "click", function() {
        var tid = $(this).attr('bkid');
        bkid = tid.substr(4, tid.length);
        ConfirmBox("body", "250px", null, "确认取消当前课程?",
            "(后续课程将一并取消)",
            confirmCancel, null);
    });

    $(".mini-item #lock-order").on({
        click: function() {
            _obj = $(this).parents("li").find(".mini-tips");
            if (_obj.css(
                    "bottom") ==
                '-36px') {
                _obj.stop();
                _obj.animate({
                    bottom: 0
                }, 300);
            } else {
                _obj.stop();
                _obj.animate({
                    bottom: '-36px'
                }, 300);
            }
        },
        mouseenter: function() {
            _obj = $(this).parents("li").find(".mini-tips");
            _obj.stop();
            _obj.animate({
                bottom: 0
            }, 300);
        },
        mouseleave: function() {
            _obj = $(this).parents("li").find(".mini-tips");
            _obj.stop();
            _obj.animate({
                bottom: '-36px'
            }, 300);
        }
    });

    /* confirm event */
    function confirmCancel() {
        $.ajax({
            type: "POST", //post提交方式默认是get
            dataType: 'json',
            timeout: 8000, //超时时间8秒
            url: basePath +
                '/ucenter/subscribeCourse/smallpack/cancelSmallpack/' +
                bkid,
            data: {

            },
            beforeSend: BeginLoading,
            error: function(jqXHR, textStatus, errorThrown) {
                EndLoading();
                if (textStatus == "timeout") {
                    alert("请求超时，页面自动刷新！");
                } else {
                    alert('系统出现异常,请联系管理员');
                }
            },
            success: function(result) { //提交成功
                EndLoading();
                if (result.success) {
                    //成功消息提示！！！
                    alert(result.msg);
                    //成功了需要刷下页面：
                    location.href = basePath + '/ucenter/course/main/default/default';
                } else {
                    alert(result.msg);
                }

            }
        });

        bkid = null; //置空
    }
});

/* mini time table pop up
/***********************************************************/
$(function() {
	var eventList = {
		"popupTimeTable": function() {
			PopupTimeTable(true);
		}
	};

	$("body").on("click", "[data-timetable]", function() {
		var actionName = $(this).data("timetable");
		var action = eventList[actionName];
		$("#time-table .popup-tips").html($(this).parents(".mini-item").find("label").next().html());
		$("#time-table").attr("data-cid", $(this).attr("cid"));
		if ($.isFunction(action)) {
			action();
		}
	});

    var hasChosen = false;

    /*
    $("#time-table").delegate(".popup-confirm", "click", function() {
     	if($("#time-table li[class=active]").length == 0){
     		alert('请选择上课时间！');
     		return false;
     	}
     	//否则就异步提交数据！
     	//bookSmallpack();//预约小包课

         // TO DO other operation here
         if ($("#time-table").find(".time-table li.active").html() != undefined) {
             hasChosen = true;
             console.log($("#time-table").find(".order-current div:eq(0)").html() + "," + $("#time-table").find(".order-current div:eq(1)").html());
             console.log($("#time-table").find(".time-table li.active").parent().prev().html() + "," + $("#time-table").find(".time-table li.active a").html());
           //  TipsBox("body", "180px", basePath+"/skin/images/success.png", "预约成功!", null);
         }

         if (!hasChosen) {
             return;
         };

         $("#time-table").fadeOut(300);
         CloseMask();
     });

     $("#time-table").delegate(".popup-confirm", "click", function() {
         // TO DO other operation here
         if ($("#time-table").find(".time-table li.active").html() != undefined) {
             hasChosen = true;
             console.log($("#time-table").find(".order-current div:eq(0)").html() + "," + $("#time-table").find(".order-current div:eq(1)").html());
             console.log($("#time-table").find(".time-table li.active").parent().prev().html() + "," + $("#time-table").find(".time-table li.active a").html());

             TipsBox("body", "180px", basePath+"/skin/images/success.png", "预约成功!", null);
         }
         if (!hasChosen) {
             return;
         };
         $("#time-table").fadeOut(300);
         CloseMask();
     });
     */


    $(".time-table").delegate("li", "click", function() {
        $(".time-table li").removeClass("active");

        $(this).addClass("active");
    });

    function PopupTimeTable(canMove) {
        ShowTMask();
        $("#time-table").css("visibility", "hidden");
        setTimeout(function() {
            var wrapWidth = $("#time-table").width();
            var wrapHeight = $("#time-table").height();
            $("#time-table").css("left", "50%");
            $("#time-table").css("top", "50%");
            $("#time-table").css("margin-left", -wrapWidth / 2 +
                "px");
            $("#time-table").css("margin-top", -wrapHeight / 2 +
                "px");
            $("#time-table").css("visibility", "visible");
        }, 100);

        $("#time-table .order-current").find("div").css("color",
            "#498dca");
        $("#time-table .order-current").removeClass("order-current").addClass(
            "order-future");
        $("#time-table .vip-order-list-inner ul li:eq(1)").addClass(
            "order-current");
        $("#time-table .order-current").find("div").css("color", "#fff");

        $("#mini-order-current").animate({
            left: $("#time-table .order-current").position().left
        }, 0);

        $("#time-table").fadeIn(300);

        // move module

        if (canMove) {
            $("#time-table .popup-header").mousedown(function(event) {
                var isMoving = true;
                $(this).css("cursor", "move");
                var curLeft = event.clientX - $(this).parent().position()
                    .left;
                var curTop = event.clientY - $(this).parent().position()
                    .top;

                $(document).mousemove(function(event) {
                    if (isMoving) {
                        $("#time-table").css({
                            'left': event.clientX -
                                curLeft,
                            'top': event.clientY -
                                curTop
                        });
                    }
                }).mouseup(function() {
                    isMoving = false;
                    $("#time-table .popup-header").css(
                        "cursor", "default");
                });
            });
        }

        $(".time-table li").removeClass("active");

        $(".vip-order-list-inner").css("width", $(".vip-order-list").width() -
            ($(".order-prev-wrap").width() + 31) * 2 + "px");
        $("#mini-order-current").animate({
            left: $(".order-current").position().left
        }, 0);
    }
});

/* close pop up
/***********************************************************/
$(function() {
    $("#time-table").delegate(".popup-closer,.popup-cancel", "click",
        function() {
            $("#time-table").fadeOut(300);
            CloseMask();
        });
});
