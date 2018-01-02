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
    
    /* banner guidance button event
    /**********************/
    $("#bannerBtn2")
        .on("click", function () {
            ShowMask();
            $("#customerGuidanceLayer")
                .show()
                .removeClass("bounceOut")
                .addClass("animated")
                .addClass("bounceIn");
            $("#customerGuidanceLayer")
                .children().not("label").hide();

            $("#cgStepOne")
                .show();
    });
    $("#cgStepOne img")
        .on("click", function () {
            $("#cgStepOne").hide();
            $("#cgStepTwo").show();
    });
    $("#cgStepTwo img")
        .on("click", function () {
            $("#cgStepTwo").hide();
            $("#cgStepThree").show();
    });
    $("#cgStepThree img")
        .on("click", function () {
            $("#cgStepThree").hide();
            $("#cgStepFour").show();
            $("#cgStepFour li").removeClass("cg-step-four-hour");
    });
    $("#cgStepFour li")
        .on("click", function () {
            $("#cgStepFour li").removeClass("cg-step-four-hour");
            $(this).addClass("cg-step-four-hour");
        });
    $("#cgStepSubmit")
        .on("click", function () {
            if (!$("#cgStepFour li").hasClass("cg-step-four-hour")) {
                alert("请选择其中一项");
                return false;
            } else {
                $("#customerGuidanceLayer")
                .removeClass("bounceIn")
                .addClass("bounceOut")
                .hide();
                CloseMask();
                $("[data-popup=regis]").first().trigger("click");
            }
        });
    $("#cgStepSubmit2")
        .on("click", function () {
            $("#customerGuidanceLayer")
            .removeClass("bounceIn")
            .addClass("bounceOut")
            .hide();
            CloseMask();
            $("[data-popup=regis]").first().trigger("click");
        });
    $("#closeGuidance")
        .on("click", function () {
            $("#customerGuidanceLayer")
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
            // team
            var isTeamShow = false;
            if (curScrollTop > 50 && !isTeamShow) {
                $("#teacherTeamImg")
                    .show()
                    .addClass("animated")
                    .addClass("fadeInLeft");
                isTeamShow = true;
            }
            // conn
            var isConnShow = false;
            if (curScrollTop > 520 && !isConnShow) {
                $(".js_Animate")
                    .show()
                    .addClass("animated")
                    .addClass("bounceIn");
                isConnShow = true;
                $(".conn-line1-wrap")
                    .show()
                    .addClass("conn-line1-animate");
                $(".conn-line2-wrap")
                    .show()
                    .addClass("conn-line2-animate");
                $(".conn-line3-wrap")
                    .show()
                    .addClass("conn-line3-animate");
            }
            // learning coach
            var isLearningCoach = false;
            if (curScrollTop > 1000 && !isLearningCoach) {
                $("#learningCoachImg")
                    .show()
                    .addClass("animated")
                    .addClass("fadeIn");
                isLearningCoach = true;
            }
            // method
            var isLanMethod = false;
            if (curScrollTop > 1500 && !isLanMethod) {
                $("#scientificMethodImg")
                    .show()
                    .addClass("animated")
                    .addClass("slideInUp");
                isLanMethod = true;
            }
        });
});
