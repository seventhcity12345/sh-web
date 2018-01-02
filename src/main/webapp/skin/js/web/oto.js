/* document ready module
/* pc one to one sliding js
/***********************************************************/
$(function() {
    if ($(".hwj-pc-oto").length > 0) {
        setTimeout(function() {
            $(".hwj-pc-oto").css("top", $(".item-wrap").offset()
                .top);
            $(".hwj-pc-oto").css("left", $(".item-wrap").offset()
                .left +
                $(".item-wrap").width());
            $(".hwj-pc-oto").fadeIn(300);
            scrollPanel();
        }, 500);
        $(window).scroll(function() {
            scrollPanel();
        });
        $(window).resize(function() {
            $(".hwj-pc-oto").hide();
            $(".oto-tag").remove();
            setTimeout(function() {
                $(".hwj-pc-oto").css("top", $(
                        ".item-wrap")
                    .offset().top);
                $(".hwj-pc-oto").css("left", $(
                        ".item-wrap")
                    .offset().left + $(".item-wrap")
                    .width()
                );
                $(".hwj-pc-oto").fadeIn(300);
                scrollPanel();
            }, 500);

            ShowTag();
        });

        /* show or hide oto module
        /* pc one to one sliding js
        /***********************************************************/
        ShowTag();

        $("body").delegate(".oto-tag", "click", function() {
            $(".oto-tag").remove();
            $(".hwj-pc-oto").animate({
                left: $(window).width() - $(
                    ".hwj-pc-oto").width()
            }, 500);
        });

        $(".pc-oto-title").on({
            click: function() {
                ShowTag();
            }
        });
    }

    /**********************************************************/
    function ShowTag() {
        setTimeout(function() {
            var cWidth = $(".hwj-pc-oto").width();
            var cTop = $(".hwj-pc-oto").offset().top;
            var cPosLeft = $(".hwj-pc-oto").offset().left +
                cWidth;
            if (cPosLeft >= $("body").width() && $(window).width() >
                1000) {
                $(".hwj-pc-oto").animate({
                    left: $(window).width()
                }, 500, function() {
                    if ($("img.oto-tag").length <= 0) {
                        $("body").append(
                            "<img class='oto-tag' src='skin/images/oto_tag.jpg'>"
                        );
                    }
                    $(".oto-tag").css("top", cTop).css(
                            "right", "0")
                        .fadeIn(200);
                });
            } else {
                $(".oto-tag").remove();
            }
        }, 1000);
    }

    function scrollPanel() {
        var otoHeight = $(".hwj-pc-oto").height();
        var MaxHeight = $(".item-wrap").offset().top + $(".item-wrap").height() +
            60 - otoHeight;
        var offsetTop = 0;
        if ($(document).scrollTop() >= $(".item-wrap").offset().top &&
            $(document).scrollTop() < MaxHeight) {
            offsetTop = $(document).scrollTop() + "px";
            $(".hwj-pc-oto,.oto-tag").animate({
                top: offsetTop
            }, {
                duration: 700,
                queue: false
            });
        } else if ($(document).scrollTop() >= MaxHeight) {
            $(".hwj-pc-oto,.oto-tag").animate({
                top: MaxHeight
            }, {
                duration: 700,
                queue: false
            });
        } else {
            offsetTop = $(".item-wrap").offset().top + "px";
            $(".hwj-pc-oto,.oto-tag").animate({
                top: offsetTop
            }, {
                duration: 700,
                queue: false
            });
        }
    }
});
