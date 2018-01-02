/* document ready module
/*
/***********************************************************/
$(function() {

})

/* psw input and info ready module
/* validate input text for psw
/***********************************************************/
$(function() {
    $("#old-psw").on({
        focusout: function() {
            if ($("#old-psw").val() == "") {
                $("#old-psw-info").removeClass("light-dark-text");
                $("#old-psw-info").addClass("red-text");
                $("#old-psw").addClass("hint-icon");
            }
        },
        keyup: function() {
            $("#old-psw-info").removeClass("red-text");
            $("#old-psw").removeClass("hint-icon");
            $("#old-psw-info").addClass("light-dark-text");
        }
    });
    $("#new-psw").on({
        focusout: function() {
            if ($("#new-psw").val() == "") {
                $("#new-psw-info-1,#new-psw-info-2").removeClass("light-dark-text");
                $("#new-psw-info-1,#new-psw-info-2").addClass("red-text");
                $("#new-psw").addClass("hint-icon");
            };
        },
        keyup: function() {
            $("#new-psw-info-1,#new-psw-info-2").removeClass("red-text");
            $("#new-psw").removeClass("hint-icon");
            $("#new-psw-info-1,#new-psw-info-2").addClass("light-dark-text");

            if ($("#new-psw").val() != $("#confirm-psw").val() && $(
        		"#confirm-psw").val() != "") {
        		$("#confirm-psw-info").html("两次密码不一致!");		
                $("#confirm-psw-info").removeClass("light-dark-text");
                $("#confirm-psw-info").addClass("red-text");
                $("#confirm-psw").addClass("hint-icon");
        	} else {
        		$("#confirm-psw-info").html("请再次输入你的密码");
        		$("#confirm-psw-info").removeClass("red-text");
            	$("#confirm-psw").removeClass("hint-icon");
            	$("#confirm-psw-info").addClass("light-dark-text");
        	}
        },
        mouseleave: function() {
        	var reg = /^[_\0-9\a-z]{6,16}$/;
        	if ($("#new-psw").val() == $("#old-psw").val() && $(
        		"#new-psw").val() != "") {
        		$("#new-psw-info-1").removeClass("light-dark-text");
                $("#new-psw-info-1").addClass("red-text");
                $("#new-psw").addClass("hint-icon");
        	}
        	if (!reg.test($("#new-psw").val())) {
				$("#new-psw-info-2").removeClass("light-dark-text");
                $("#new-psw-info-2").addClass("red-text");
                $("#new-psw").addClass("hint-icon");
        	}
        }
    });
    $("#confirm-psw").on({
        focusout: function() {
            if ($("#confirm-psw").val() == "") {
                $("#confirm-psw-info").removeClass("light-dark-text");
                $("#confirm-psw-info").addClass("red-text");
                $("#confirm-psw").addClass("hint-icon");
            };
        },
        keyup: function() {
        	if ($("#new-psw").val() != $("#confirm-psw").val()) {
        		$("#confirm-psw-info").html("两次密码不一致!");		
                $("#confirm-psw-info").removeClass("light-dark-text");
                $("#confirm-psw-info").addClass("red-text");
                $("#confirm-psw").addClass("hint-icon");
        	} else {
        		$("#confirm-psw-info").html("请再次输入你的密码");
        		$("#confirm-psw-info").removeClass("red-text");
            	$("#confirm-psw").removeClass("hint-icon");
            	$("#confirm-psw-info").addClass("light-dark-text");
        	}
        },
        mouseleave: function() {
        	if ($("#new-psw").val() != $("#confirm-psw").val()) {
        		$("#confirm-psw-info").html("两次密码不一致!");		
                $("#confirm-psw-info").removeClass("light-dark-text");
                $("#confirm-psw-info").addClass("red-text");
                $("#confirm-psw").addClass("hint-icon");
        	} else if ($("#confirm-psw").val() != "") {
        		$("#confirm-psw-info").html("请再次输入你的密码");
        		$("#confirm-psw-info").removeClass("red-text");
            	$("#confirm-psw").removeClass("hint-icon");
            	$("#confirm-psw-info").addClass("light-dark-text");
        	}
        }
    });
})
