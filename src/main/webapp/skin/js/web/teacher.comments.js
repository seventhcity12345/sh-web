/* document ready
/***********************************************************/
$(function() {
    ratingSlide();

    $(".cr-rating").find(".t-to-me").css("position", "absolute").css("left", "100%");
    $(".cr-rating").find(".me-to-t").css("position", "relative").css("left", "0");
})

/* rating switch
/***********************************************************/
$(function() {
    $(".crr-title li").delegate("a", "click", function() {
        if ($(this).parents(".cr-rating").find(".t-to-me .rating-text").attr("data-h") >= 90) {
            $(".rating-text").animate({
                height: '90'
            }, 0, function() {
                $(this).parents(".cr-rating").find(".t-to-me .rating-text span[id^='text-unfold']").show();
            });
        } else {
            $(this).parents(".cr-rating").find(".t-to-me .rating-text span[id^='text-unfold']").hide();
            $(this).parents(".cr-rating").find(".t-to-me .rating-text span[id^='text-fold']").hide();
        }
    })
    function teacherComment(_this) {
        if ($(_this).hasClass("active")) {
            return;
        };
        $(_this).parents(".crr-title").find(".active").removeClass("active");
        $(_this).addClass("active");

        if ($(_this).parents(".cr-rating").find(".t-to-me").css("position") != "absolute") {
            $(_this).parents(".cr-rating").find(".t-to-me").css("position", "absolute");
            $(_this).parents(".cr-rating").find(".t-to-me").animate({
                left: '100%'
            }, 300);

            $(_this).parents(".cr-rating").find(".me-to-t").css("position", "relative");
            $(_this).parents(".cr-rating").find(".me-to-t").css("left", "-100%");
            $(_this).parents(".cr-rating").find(".me-to-t").animate({
                left: 0
            }, 300);
        } else {
            $(_this).parents(".cr-rating").find(".t-to-me").css("position", "relative");
            $(_this).parents(".cr-rating").find(".t-to-me").css("left", "100%");
            $(_this).parents(".cr-rating").find(".t-to-me").animate({
                left: 0
            }, 300);

            $(_this).parents(".cr-rating").find(".me-to-t").css("position", "absolute");
            $(_this).parents(".cr-rating").find(".me-to-t").animate({
                left: '-100%'
            }, 300);
        }
    };
    $(".crr-title").delegate("li", "click", function() {
        teacherComment($(this).children());
    });
})

/* rating-text slideToggle
/***********************************************************/
function ratingSlide() {
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
}