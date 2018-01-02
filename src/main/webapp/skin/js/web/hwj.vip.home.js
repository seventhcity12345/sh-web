/* common js
/***********************************************************/
$(function() {
    // mini download
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

    // icon-video
    $(".sh-video .imgTag,.sh-creview .imgTag").on({
        mouseenter: function() {
            $(this).children(".mask").stop().fadeIn(500);
            $(this).find(".mini-img").addClass("xs-scale");
        },
        mouseleave: function() {
            $(this).children(".mask").stop().fadeOut(500);
            $(this).find(".mini-img").removeClass("xs-scale");
        }
    });
});

/* document ready
/***********************************************************/
$(function() {
    /* init
    /*******************************************/
    if ($(".sh-schedule li").length === 1) {
        $(".sh-schedule .schedule-order").height(260 + "px");
    } else {
        $(".sh-schedule .schedule-order").css("visibility", "hidden");
        setTimeout(function() {
            $(".sh-schedule .schedule-order").height($(".sh-schedule li:first-child").height());
            $(".sh-schedule .schedule-order").css("visibility", "visible");
        }, 100);
    }
    $(window).resize(function() {
        if ($(".sh-schedule li").length === 1) {
            $(".sh-schedule .schedule-order").height(260 + "px");
        } else {
            $(".sh-schedule .schedule-order").css("visibility", "hidden");
            setTimeout(function() {
                $(".sh-schedule .schedule-order").height($(".sh-schedule li:first-child").height());
                $(".sh-schedule .schedule-order").css("visibility", "visible");
            }, 100);
        }
    });

    /* schedule hide or show
    /*******************************************/
    var totalNum = $(".sh-schedule li").length;
    var actLen = 3;
    if ($(window).width() <= 768) {
        actLen = 2;
    }
    var curActiveNum = actLen;
    for (var i = 0; i < actLen; i++) {
        $(".sh-schedule li").eq(i).addClass("active");
    }
    $(window).resize(function() {
        setTimeout(function() {
            if ($(window).width() <= 768) {
                actLen = 2;
                curActiveNum = actLen;
            } else {
                actLen = 3;
                curActiveNum = actLen;
            }
            $(".sh-schedule li").removeClass("active");
            $("#schedule-more").show();
            $("#schedule-fold").hide();
            for (var i = 0; i < actLen; i++) {
                $(".sh-schedule li").eq(i).addClass("active");
            }
        }, 100);
    });
    $("#schedule-more").on("click", function() {
        for (var i = 0; i < actLen; i++) {
            $(".schedule li:eq(" + Number(curActiveNum++) + ")").addClass("active");
            //console.log(curActiveNum+","+totalNum);
            if (totalNum - curActiveNum < actLen) {
                $("#schedule-more").hide();
                $("#schedule-fold").show();
            }
        }
    });
    $("#schedule-fold").on("click", function() {
        for (var i = actLen; i < totalNum; i++) {
            $(".schedule li:eq(" + i + ")").removeClass("active");
        }
        curActiveNum = actLen;
        $("#schedule-more").show();
        $("#schedule-fold").hide();
        if ($(window).width() < 768) {
            $("body").animate({
                scrollTop: $(".sh-schedule").offset().top - 30
            }, 300);
        }
    });
});

/* cprogress list slide
/*******************************************/
$(function() {
    var CPList = {
        obj: $("#cprogress-list"),
        prev: $("#cp-prev"),
        next: $("#cp-next"),
        interval: 500,
        curIndex: 0,
        isRunningSlide: false,
        curLength: function() {
            return this.obj.children("li").length;
        },
        // allprogress expect and real
        runningProgress: function() {
            _this = this;
            var curActive = _this.obj.children("li.active");
            var curPanel = curActive.find(".allprogress-panel");
            var curExpect = curPanel.children("#allprogress-expect");
            var curReal = curPanel.children("#allprogress-real");
            var curNum = curPanel.children("#allprogress-num");

            var allprogressTime = 1000;
            var expectValue = curExpect.attr("data-expect");
            var realValue = curReal.attr("data-real");
            var numValue = curReal.attr("data-real");
            numValue = numValue > 999 ? 999 : numValue;
            expectValue = expectValue > 100 ? 100 : expectValue;
            realValue = realValue > 100 ? 100 : realValue;

            curExpect.css("width", 0);
            curReal.css("width", 0);
            curNum.css("left", 0);

            curExpect.animate({
                width: expectValue + "%"
            }, allprogressTime / 2);
            curReal.animate({
                width: realValue + "%"
            }, allprogressTime);
            curNum.animate({
                left: realValue + "%"
            }, allprogressTime);
            var curValue = 0;
            var numTimer = setInterval(function() {
                curNum.html(parseFloat(curValue++) + "%");
                if (curValue > numValue) {
                    clearInterval(numTimer);
                }
            }, allprogressTime / numValue);
        },
        runningSlide: function() {
            _this = this;
            var curActive = _this.obj.children("li.active");
            curActive.hide().css("left", 0).show();
            _this.curIndex = curActive.index();
            _this.runningProgress();

            // 如果只有一条信息，则不显示切换项
            if (_this.curLength() === 1) {
                _this.prev.hide();
                _this.next.hide();
                return;
            }

            // next action
            _this.next.on("click", function() {
                if (_this.isRunningSlide) {
                    return;
                } else {
                    _this.isRunningSlide = true;
                    curActive = _this.obj.children("li.active");
                    curActive.animate({
                        left: "-100%"
                    }, _this.interval);
                    curActive.removeClass("active");

                    if (_this.curIndex === _this.curLength() - 1) {
                        _this.curIndex = -1;
                    }

                    var nextActive = _this.obj.children("li:eq("+(_this.curIndex+1)+")");
                    nextActive.addClass("active");
                    nextActive.hide(0).css("left", "100%").show(0);
                    nextActive.animate({
                        left: "0"
                    }, _this.interval, function() {
                        _this.isRunningSlide = false;
                    });
                    _this.curIndex = nextActive.index();
                    _this.runningProgress();
                }
            });
            // prev action
            _this.prev.on("click", function() {
                if (_this.isRunningSlide) {
                    return;
                } else {
                    _this.isRunningSlide = true;
                    curActive = _this.obj.children("li.active");
                    curActive.animate({
                        left: "100%"
                    }, _this.interval);
                    curActive.removeClass("active");

                    if (_this.curIndex === 0) {
                        _this.curIndex = _this.curLength();
                    }

                    var prevActive = _this.obj.children("li:eq("+(_this.curIndex-1)+")");
                    prevActive.addClass("active");
                    prevActive.hide(0).css("left", "-100%").show(0);
                    prevActive.animate({
                        left: "0"
                    }, _this.interval, function() {
                        _this.isRunningSlide = false;
                    });
                    _this.curIndex = prevActive.index();
                    _this.runningProgress();
                }
            });
        }
    };

    CPList.runningSlide();
});
