/* document ready
/***********************************************************/

var imgLeft = new Image();
var imgRight = new Image();
if (basePath == undefined) {
    var basePath = "";
};
imgLeft.src = basePath+"/skin/images/studying_left.jpg";
imgRight.src = basePath+"/skin/images/studying_right.jpg";

$(imgLeft).load(function() {
    $(".s-left").css("visibility","visible");
});
$(imgRight).load(function() {
    $(".s-right").css("visibility","visible");
});

$(window).load(function() {
    $(".s-left").css("visibility","visible");
    $(".s-left").animate({
        opacity: 1,
        marginLeft: 0
    }, 1500);

    $(".s-right").css("visibility","visible");
    $(".s-right").animate({
        opacity: 1,
        marginRight: 0
    }, 1500);
});

$(function() {
    $(window).resize(function() {
        CloseSlide();
    });

    $("#slide-left,#slide-right").on({
        mouseleave: function() {
            $(this).stop();
            $(this).animate({
                top: 0
            }, 300);
        }
    });

    $("#slide-right").on("mouseenter", function(event) {
        $(this).stop();

        $(".slide-right").stop();
        $(".slide-right").animate({
            right: '-100%'
        }, 50);

        $(this).animate({
            top: '-10px'
        }, 300);

        $(".slide-left").show();
        var curLeft = -346 + ($(document).width() - $(".studying-wrap").width()) / 2;
        $(".slide-left").animate({
            left: curLeft
        }, 500);

        var e = window.event || event;

        if (e.stopPropagation) { //如果提供了事件对象，则这是一个非IE浏览器
            e.stopPropagation();
        } else {
            //兼容IE的方式来取消事件冒泡
            window.event.cancelBubble = true;
        }
    });

    $("#slide-left").on("mouseenter", function(event) {
        $(this).stop();

        $(".slide-left").stop();
        $(".slide-left").animate({
            left: '-100%'
        }, 50);

        $(this).animate({
            top: '-10px'
        }, 300);

        $(".slide-right").show();
        var curLeft = -346 + ($(document).width() - $(".studying-wrap").width()) / 2;
        $(".slide-right").animate({
            right: curLeft
        }, 500);

        var e = window.event || event;

        if (e.stopPropagation) { //如果提供了事件对象，则这是一个非IE浏览器 
            e.stopPropagation();
        } else {
            //兼容IE的方式来取消事件冒泡 
            window.event.cancelBubble = true;
        }
    });

    $(".slide-left").on("mouseenter", function() {
        $(".slide-right").stop();
        $(".slide-right").animate({
            right: '-100%'
        }, 50);
    });

    $(".slide-right").on("mouseenter", function() {
        $(".slide-left").stop();
        $(".slide-left").animate({
            left: '-100%'
        }, 50);
    });

    $(".slide-left").on("mouseleave", function() {
        $(this).stop();
        $(".slide-left").animate({
            left: '-100%'
        }, 500);
    });

    $(".slide-right").on("mouseleave", function() {
        $(this).stop();
        $(".slide-right").animate({
            right: '-100%'
        }, 500);
    });

    $(".arrow-l").on("click", function() {
        $(".slide-left").stop();
        $(".slide-left").animate({
            left: '-100%'
        }, 500, function() {
            $(".slide-left").hide();
        });
    });

    $(".arrow-r").on("click", function() {
        $(".slide-right").stop();
        $(".slide-right").animate({
            right: '-100%'
        }, 500, function() {
            $(".slide-right").hide();
        });
    });

    // $(".studying").not("#slide-left,#slide-right").on("click", function() {
    //  CloseSlide();
    // });

    function CloseSlide() {
        $(".slide-left").stop();
        $(".slide-left").animate({
            left: '-100%'
        }, 500, function() {
            $(".slide-left").hide();
        });

        $(".slide-right").stop();
        $(".slide-right").animate({
            right: '-100%'
        }, 500, function() {
            $(".slide-right").hide();
        });
    }
})
