/* document ready module
/*
/***********************************************************/
$(function() {
    init("province","city");
    initBirth("p-year","p-month","p-day");
})

/* vip profile ready module
/* changing phone or email on profile page
/***********************************************************/
$(function() {
	/*change phone*/
    var changePhoneTimer = null;
    $("#change-phone").click(function() {
        if (changePhoneTimer != null) {
            $("#confirm-vc").show();
            $("#confirm-vc-code").fadeIn(300);
        } else {
            $("#confirm-vc-code").hide();
            $("#confirm-vc").hide();
        }
        $("#change-phone-1").hide();
        $("#change-phone-2").fadeIn(300);
    });
    $("#cancel-change-phone").click(function() {
        $("#change-phone-2").hide();
        $("#change-phone-1").fadeIn(300);
        $("#confirm-vc-code").fadeOut(300);
        $("#confirm-vc").hide();
    });
    $("#verification-code").click(function() {
        $("#confirm-vc").show();
        $("#confirm-vc-code").fadeIn(300);
        if ($(this).hasClass("blue-bg")) {
            $(this).removeClass("blue-bg");
            $(this).addClass("cursor-clear");
            $(this).addClass("dark-gray-bg");
            var time = 60;
            changePhoneTimer = setInterval(function() {
                $("#verification-code").html(time + "秒后重发");
                if (time <= 0) {
                    ReactiveAction();
                };
                time--;
            }, 1000);
            $(this).click(function() {
                return;
            });
        };
    });

    function ReactiveAction() {
        if (changePhoneTimer != null) {
            clearInterval(changePhoneTimer);
            changePhoneTimer = null;
        };
        $("#verification-code").removeClass("dark-gray-bg");
        $("#verification-code").addClass("cursor-p");
        $("#verification-code").addClass("blue-bg");
        $("#verification-code").html("获取验证码");
    }

    /*change email*/
    $("#change-email").click(function() {
        $("#change-email-1").hide();
        $("#change-email-2").fadeIn(300);
    });
    $("#cancel-change-email").click(function() {
        $("#change-email-2").hide();
        $("#change-email-1").fadeIn(300);
    });
})
