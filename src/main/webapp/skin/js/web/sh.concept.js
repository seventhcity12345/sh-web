/* speakhi index js v3
/* 2016-6-16 17:05:07
/*********************************************************/
$(function () {

    /* zixun button event
    /**********************/
    $("#customerService")
        .on("click", function () {
            ShowMask();
            $("#customerServiceLayer")
                .show()
                .removeClass("bounceOut")
                .addClass("animated")
                .addClass("bounceIn");
        });
    $("#closeService")
        .on("click", function () {
            $("#customerServiceLayer")
                .removeClass("bounceIn")
                .addClass("bounceOut")
                .hide();
            CloseMask();
        });

    /* scroll spy event
    /**********************/
    $(window)
        .scroll(function (event) {
            var curScrollTop = ($(window)
                .scrollTop());
            // comprehension
            var isLanCompShow = false;
            if (curScrollTop > 50 && !isLanCompShow) {
                $("#languageComprehensionImg")
                    .show()
                    .addClass("animated")
                    .addClass("fadeInLeft");
                isLanCompShow = true;
            }
            // effective
            var isLanEffectShow = false;
            if (curScrollTop > 750 && !isLanEffectShow) {
                $("#languageEffectiveImg")
                    .show()
                    .addClass("animated")
                    .addClass("fadeInLeft");
                isLanEffectShow = true;
            }
            // patience
            var isLanPatience = false;
            if (curScrollTop > 1500 && !isLanPatience) {
                $("#languagePatienceImg")
                    .show()
                    .addClass("animated")
                    .addClass("fadeIn");
                isLanPatience = true;
            }
            // method
            var isLanMethod = false;
            if (curScrollTop > 2000 && !isLanMethod) {
                $("#languageMethodImg2")
                    .show()
                    .addClass("animated")
                    .addClass("pulse");
                isLanMethod = true;
            }
        });
});
