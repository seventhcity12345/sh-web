/* document ready
/***********************************************************/
$(function() {
    $(".cr-rating").find(".me-to-t").css("position", "absolute").css("left", "100%");
    $(".cr-rating").find(".t-to-me").css("position", "relative").css("left", "0");
})

/* rating switch
/***********************************************************/
$(function() {
    function commentRunning(_this) {
        if ($(_this).hasClass("active")) {
            return;
        };
        $(_this).parents(".crr-title").find(".active").removeClass("active");
        $(_this).addClass("active");

        if ($(_this).parents(".cr-rating").find(".t-to-me").css("position") != "absolute") {
            $(_this).parents(".cr-rating").find(".t-to-me").css("position", "absolute");
            $(_this).parents(".cr-rating").find(".t-to-me").animate({
                left: '-100%'
            }, 300);

            $(_this).parents(".cr-rating").find(".me-to-t").css("position", "relative");
            $(_this).parents(".cr-rating").find(".me-to-t").css("left", "100%");
            $(_this).parents(".cr-rating").find(".me-to-t").animate({
                left: 0
            }, 300);
        } else {
            $(_this).parents(".cr-rating").find(".t-to-me").css("position", "relative");
            $(_this).parents(".cr-rating").find(".t-to-me").css("left", "-100%");
            $(_this).parents(".cr-rating").find(".t-to-me").animate({
                left: 0
            }, 300);

            $(_this).parents(".cr-rating").find(".me-to-t").css("position", "absolute");
            $(_this).parents(".cr-rating").find(".me-to-t").animate({
                left: '100%'
            }, 300);
        }

    };
    $(".crr-title").delegate("li", "click", function() {
        commentRunning($(this).children());
    });
})

/* give rating v2 鼠标点击选中，还可以再点击选中
/***********************************************************/
var getTotalScore = function(cur) {
    var preparation_score = Number(cur.parents('#dimensionList').find('li:eq(0)').attr('data-score'));
    var delivery_score = Number(cur.parents('#dimensionList').find('li').eq(1).attr('data-score'));
    var interaction_score = Number(cur.parents('#dimensionList').find('li:eq(2)').attr('data-score'));
    cur.parents('.crr-wrap').find('.crr-score').html(((preparation_score+delivery_score+interaction_score)/3).toFixed(1));
};
var runningRating = function(base, dis, cur) {
    // 1. 置空评分
    cur.removeClass(
        "rating0 rating1 rating2 rating3 rating4 rating5").addClass(
        "rating0");
    //console.log(Math.ceil(dis/base));
    var rating = Math.ceil(dis/base);
    if (rating <= 0) {
        if (!cur.parent().parent().hasClass(
                "rating-tboard")) {
            cur.prev().addClass("ff-s").html("为老师评分");
        }
    } else {
        cur.removeClass(
            "rating0 rating1 rating2 rating3 rating4 rating5").addClass(
            "rating"+rating);
        if (!cur.parent().parent().hasClass(
                "rating-tboard")) {
              cur.parents('li').attr('data-score', rating);
              getTotalScore(cur);
        }
    }
};
$(".rating-icon").click(function(event) {
    var base = 15.6;
    var cur = $(this);
    var curLeft = cur.offset().left;
    var dis = event.pageX - curLeft;

    if (cur.hasClass("active")) {
        runningRating(base, dis, cur);
    }
});

///* give rating v1
///***********************************************************/
//$(".rating0").mouseenter(function() {
//    var cur = $(this);
//    var curLeft = cur.offset().left;
//    cur.mousemove(function(event) {
//        if (cur.hasClass("active")) {
//            var base = 15.6;
//            var dis = event.pageX - curLeft;
//            if (dis <= 0) {
//                cur.removeClass(
//                    "rating0 rating1 rating2 rating3 rating4 rating5").addClass(
//                    "rating0");
//                if (!cur.parent().parent().hasClass(
//                        "rating-tboard")) {
//                    cur.prev().addClass("ff-s").html("为老师评分");
//                }
//            }
//            if (dis <= base * 1 && dis > 0) {
//                cur.removeClass(
//                    "rating0 rating1 rating2 rating3 rating4 rating5").addClass(
//                    "rating1");
//                if (!cur.parent().parent().hasClass(
//                        "rating-tboard")) {
//                    cur.prev().removeClass("ff-s").html(
//                        "<span><b>1.0</b></span>分");
//                }
//            }
//            if (dis <= base * 2 && dis > base * 1) {
//                cur.removeClass(
//                    "rating0 rating1 rating2 rating3 rating4 rating5").addClass(
//                    "rating2");
//                if (!cur.parent().parent().hasClass(
//                        "rating-tboard")) {
//                    cur.prev().removeClass("ff-s").html(
//                        "<span><b>2.0</b></span>分");
//                }
//            }
//            if (dis <= base * 3 && dis > base * 2) {
//                cur.removeClass(
//                    "rating0 rating1 rating2 rating3 rating4 rating5").addClass(
//                    "rating3");
//                if (!cur.parent().parent().hasClass(
//                        "rating-tboard")) {
//                    cur.prev().removeClass("ff-s").html(
//                        "<span><b>3.0</b></span>分");
//                }
//            }
//            if (dis <= base * 4 && dis > base * 3) {
//                cur.removeClass(
//                    "rating0 rating1 rating2 rating3 rating4 rating5").addClass(
//                    "rating4");
//                if (!cur.parent().parent().hasClass(
//                        "rating-tboard")) {
//                    cur.prev().removeClass("ff-s").html(
//                        "<span><b>4.0</b></span>分");
//                }
//            }
//            if (dis <= base * 5 && dis > base * 4) {
//                cur.removeClass(
//                    "rating0 rating1 rating2 rating3 rating4 rating5").addClass(
//                    "rating5");
//                if (!cur.parent().parent().hasClass(
//                        "rating-tboard")) {
//                    cur.prev().removeClass("ff-s").html(
//                        "<span><b>5.0</b></span>分");
//                }
//            }
//        };
//    });
//}).mouseleave(function() {
//    if ($(this).hasClass("active")) {
//        $(this).removeClass("rating0 rating1 rating2 rating3 rating4 rating5").addClass(
//            "rating0");
//        if (!$(this).parent().parent().hasClass(
//                "rating-tboard")) {
//            $(this).prev().addClass("ff-s").html("为老师评分");
//        }
//    };
//}).click(function(event) {
//    var base = 15.6;
//    var cur = $(this);
//    var curLeft = cur.offset().left;
//    var dis = event.pageX - curLeft;
//    if (cur.hasClass("active")) {
//        if (dis <= 0) {
//            cur.removeClass("rating0 rating1 rating2 rating3 rating4 rating5").addClass(
//                "rating0");
//            if (!cur.parent().parent().hasClass(
//                    "rating-tboard")) {
//                cur.prev().addClass("ff-s").html("为老师评分");
//            }
//        }
//        if (dis <= base * 1 && dis > 0) {
//            cur.removeClass("rating0 rating1 rating2 rating3 rating4 rating5").addClass(
//                "rating1");
//            if (!cur.parent().parent().hasClass(
//                    "rating-tboard")) {
//                cur.prev().removeClass("ff-s").html("<span><b>1.0</b></span>分");
//            }
//
//            isCheck = true;
//            cur.removeClass("active");
//        }
//        if (dis <= base * 2 && dis > base * 1) {
//            cur.removeClass("rating0 rating1 rating2 rating3 rating4 rating5").addClass(
//                "rating2");
//            if (!cur.parent().parent().hasClass(
//                    "rating-tboard")) {
//                cur.prev().removeClass("ff-s").html("<span><b>2.0</b></span>分");
//            }
//
//            isCheck = true;
//            cur.removeClass("active");
//        }
//        if (dis <= base * 3 && dis > base * 2) {
//            cur.removeClass("rating0 rating1 rating2 rating3 rating4 rating5").addClass(
//                "rating3");
//            if (!cur.parent().parent().hasClass(
//                    "rating-tboard")) {
//                cur.prev().removeClass("ff-s").html("<span><b>3.0</b></span>分");
//            }
//
//            isCheck = true;
//            cur.removeClass("active");
//        }
//        if (dis <= base * 4 && dis > base * 3) {
//            cur.removeClass("rating0 rating1 rating2 rating3 rating4 rating5").addClass(
//                "rating4");
//            if (!cur.parent().parent().hasClass(
//                    "rating-tboard")) {
//                cur.prev().removeClass("ff-s").html("<span><b>4.0</b></span>分");
//            }
//
//            isCheck = true;
//            cur.removeClass("active");
//        }
//        if (dis <= base * 5 && dis > base * 4) {
//            cur.removeClass("rating0 rating1 rating2 rating3 rating4 rating5").addClass(
//                "rating5");
//            if (!cur.parent().parent().hasClass(
//                    "rating-tboard")) {
//                cur.prev().removeClass("ff-s").html("<span><b>5.0</b></span>分");
//            }
//
//            isCheck = true;
//            cur.removeClass("active");
//        }
//    };
//
//});

/* limit characters
/***********************************************************/
var maxLen = 100;
//$(function() {
//    $(".rating-form textarea").on({
//        keyup: function() {
//            limitBody($(this));
//        },
//        mouseleave: function() {
//            limitBody($(this));
//        },
//        keydown: function(e) {
//            var curSize = $(this).val().length;
//            if (curSize >= maxLen && filterKeyCode(e.keyCode)) {
//                return false;
//            };
//        }
//    });
//
//    function limitBody(obj) {
//        var curSize = $(obj).val().length;
//        if (curSize >= maxLen) {
//            $(obj).next().find("#max-text b").css("color", "red");
//            $(obj).next().find("#max-text b").html(0);
//        } else if (curSize >= maxLen - 5) {
//            $(obj).next().find("#max-text b").css("color", "red");
//            $(obj).next().find("#max-text b").html(maxLen - curSize);
//        } else {
//            $(obj).next().find("#max-text b").css("color", "#a0a0a0");
//            $(obj).next().find("#max-text b").html(maxLen - curSize);
//        }
//
//        /*if (curSize >= maxLen && filterKeyCode(e.keyCode || e.which)) {
//            return false;
//        };*/
//    }
//
//    function filterKeyCode(code) {
//        switch (code) {
//            case 8:
//            case 37:
//            case 38:
//            case 39:
//            case 40:
//            case 46:
//                return false;
//                break;
//            default:
//                return true;
//                break;
//        }
//    }
//})

/* submit event
/***********************************************************/
/*$(function() {
    $(".rating-form a").click(function() {
        if ($(this).parents(".me-to-t").find(".rating-icon").hasClass("active")) {
            alert("请为老师打分哦！");
        } else if ($(this).parents(".rating-form").find("textarea").val().length == 0) {
            ConfirmBox("body", "400px", "温馨提示", "确定不给老师一些评语吗?222222", null, confirmSubmit, null);
        }
    });

    function confirmSubmit() {
    //页面中有
    }
})*/

/* rating-text slideToggle
/***********************************************************/
$(function() {
    $(".rating-text").each(function() {
        var curRatingHeight = $(this).height() + 30;
        $(this).attr("data-h", curRatingHeight);
    });
    $(".rating-text").each(function() {
        if ($(".rating-text").attr("data-h") >= 90) {
            $(".rating-text").animate({
                height: '90'
            }, 0, function() {
                $(".rating-text span[id^='text-unfold']").show();
            });
        } else {
            $(".rating-text span[id^='text-unfold']").hide();
            $(".rating-text span[id^='text-fold']").hide();
        }
    });

    $(".rating-text").delegate("span[id^='text-unfold']", "click", function() {
        $(this).parent().find("span[id^='text-unfold']").hide();
        $(this).parent().animate({
            height: $(this).parent().attr("data-h")
        }, 300, function() {
            $(this).find("span[id^='text-fold']").show();
        });
    });

    $(".rating-text").delegate("span[id^='text-fold']", "click", function() {
        $(this).parent().find("span[id^='text-fold']").hide();
        $(this).parent().animate({
            height: '90'
        }, 300, function() {
            $(this).find("span[id^='text-unfold']").show();
        });
    });
})
