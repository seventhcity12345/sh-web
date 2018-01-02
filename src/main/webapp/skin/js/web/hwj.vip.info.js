/* document ready
/***********************************************************/
$(function() {

    /* init ready
    /***********************************************************/
    init("province", "city", "district");

    /* profile-progress ready
    /* value 为要达到的值
    /***********************************************************/
    var RunProgressTime = function(value, progressTime) {
        var curData = parseInt($("#profile-rate").html());
        $(".profile-progress span").animate({
            width: value + '%'
        }, progressTime);
        if (value > curData) {
            var timer = setInterval(function() {
                if (value <= curData) {
                    clearInterval(timer);
                }
                $("#profile-rate").html(curData++ + '%');
            }, progressTime / (value - curData));
            $("#profile-rate").attr("data-rate", value);
        } else if (value < curData) {
            var timer = setInterval(function() {
                if (value >= curData) {
                    clearInterval(timer);
                }
                $("#profile-rate").html(curData-- + '%');
            }, progressTime / (curData - value));
            $("#profile-rate").attr("data-rate", value);
        } else {
            return;
        }
    };
    RunProgressTime(parseInt($("#profile-rate").attr("data-srate")),
        1000);

    /* event part
    /***********************************************************/
    // edit btn
    var actionList = {
        'profile-edit': function() {
            $("#personal_sign").val($("#personal_sign").val().toString().replace(/<br\s*\/?>/gi,"\r\n"));
            $("#profile-form ul li .content").hide();
            $("#profile-form ul li .action").fadeIn(300);
            $("#profile-edit").hide();
            $("#profile-save").show().css("display", "inline-block");
        }
    };

    $("body").on("click", "[data-action]", function() {
        var actionName = $(this).data('action');
        var action = actionList[actionName];
        if ($.isFunction(action)) {
            action();
        }
    });

    // text-limit event
    RunningLimit();
    $("#personal_sign").on({
        change: function() {
            RunningLimit();
        },
        keyup: function() {
            RunningLimit();
        }
    });

    function RunningLimit() {
        var limitLen = 100;
        var curLen = $("#personal_sign").val().length;
        $("#text-limit").html(curLen);
        if (curLen > limitLen - 5) {
            $("#text-limit").addClass("red");
        } else {
            $("#text-limit").removeClass("red");
        }
    }

    /* validation part
    /***********************************************************/
    $("#profile-save").on("click", function() {
        var invalidList = $("#profile-form").find("input[aria-invalid=true]");
        if (invalidList.length > 0) {
            var topVal = $(invalidList[0]).offset().top - 50;
            $("html,body").animate({
                scrollTop: topVal
            }, 300);
            return;
        }
        if (profileValidate.form()) {

            // 替换掉文本域中的换行符/单引号/双引号
            $("#address").val($("#address").val().toString().replace(/[\r\n]/g, '<br>'));
            $("#address").val($("#address").val().toString().replace(/[']/g, '&apos;'));
            $("#address").val($("#address").val().toString().replace(/["]/g, '&quot;'));
            
            $("#learn_tool").val($("#learn_tool").val().toString().replace(/[\r\n]/g, '<br>'));
            $("#learn_tool").val($("#learn_tool").val().toString().replace(/[']/g, '&apos;'));
            $("#learn_tool").val($("#learn_tool").val().toString().replace(/["]/g, '&quot;'));
            
            var personalSign = $("#personal_sign").val();
            $("#personal_sign").val(personalSign.toString().replace(/[\r\n]/g, '<br>'));
            $("#personal_sign").val($("#personal_sign").val().toString().replace(/[']/g, '&apos;'));
            $("#personal_sign").val($("#personal_sign").val().toString().replace(/["]/g, '&quot;'));
            //console.log($("#personal_sign").val());

		  updateUserInfo();
		}
    });
    var profileValidate = $("#profile-form").validate({
        debug: true,
        rules: {
            english_name: {
                required: true,
                rangelength: [1, 20],
                ruleEngame: true
            },
            contract_func: {
                required: true,
                rangelength: [8, 14],
                ruleContract: true
            },
            email: {
                maxlength: 50,
                ruleEmail: true
            },
            real_name: {
                maxlength: 5,
                ruleRealname: true
            },
            idcard: {
                maxlength: 18,
                ruleIdcard: true
            },
            address: {
                maxlength: 40
            },
            learn_tool: {
                maxlength: 50
            },
            personal_sign: {
                maxlength: 100
            },
            province: {
                ruleSel: true
            },
            city: {
                ruleSel: true
            },
            district: {
                ruleSel: true
            }
        },
        messages: {
            english_name: {
                required: "英文名不能为空",
                rangelength: "输入长度范围在{0}到{1}位之间"
            },
            contract_func: {
                required: "联系方式不能为空",
                rangelength: "输入长度范围在{0}到{1}位之间"
            },
            email: {
                maxlength: "最大长度为{0}哦"
            },
            real_name: {
                maxlength: "最大长度为{0}哦"
            },
            idcard: {
                maxlength: "最大长度为{0}哦"
            },
            address: {
                maxlength: "最大长度为{0}哦"
            },
            learn_tool: {
                maxlength: "最大长度为{0}哦"
            },
            personal_sign: {
                maxlength: "最大长度为{0}哦"
            }
        },
        groups: {
            selplace: "province city district"
        },
        success: function(label) {
            // set &nbsp; as text for IE
            label.html("&nbsp;").addClass("valid");
        },
        errorPlacement: function(error, element) {
            // if (element.attr("name") == "province" || element.attr("name") == "city" || element.attr("name") == "district")
            //   error.appendTo(element.parent());
            // else
            error.appendTo(element.parent());
        },
        showErrors: function(errorMap, errorList) {
            // $("#errorMsg").html("Your form contains " + this.numberOfInvalids() + " errors,see details above.");
            this.defaultShowErrors();
        },
        submitHandler: function(form) {
        }
    });

    /* custome rules
    /*************************************************/
    $.validator.addMethod("ruleContract", function(value, params) {
        var reg =
            /^\d{3}-\d{4,10}$|^\d{4}-\d{3,9}$|^1\d{10}$|^\d{8,14}$/;
        return this.optional(params) || (reg.test(value));
    }, "联系方式由8-14位数字或者区号加'-'加号码组成");

    $.validator.addMethod("ruleIdcard", function(value, params) {
        var reg = /^(\d{18,18}|\d{15,15}|\d{17,17}(x|X))$/;
        return this.optional(params) || (reg.test(value));
    }, "请填写正确的身份证号");

    $.validator.addMethod("ruleRealname", function(value, params) {
        var reg = /^[\u4e00-\u9fa5]{0,5}$/;
        return this.optional(params) || (reg.test(value));
    }, "请填写正确的中文姓名");

    $.validator.addMethod("ruleEngame", function(value, params) {
        var reg = /^([A-Za-z]+\s?)*[A-Za-z]$/;
        return this.optional(params) || (reg.test(value));
    }, "英文名由长度为20的大小写字母和空格组成");

    $.validator.addMethod("ruleEmail", function(value, params) {
        var reg =
            /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
        return this.optional(params) || (reg.test(value));
    }, "请填写正确的email");

    $.validator.addMethod("ruleSel", function(value, params) {
        var conProvince = $("#province").find("option:selected")
            .text() ==
            '省份';
        var conCity = $("#city").find("option:selected").text() ==
            '城市';
        var conDistrict = $("#district").find("option:selected")
            .text() ==
            '县区';

        return !(!conProvince && conDistrict);
    }, "请完整选择所在地");

    /* validation response event
    /*************************************************/

    // var curError = profileValidate.numberOfInvalids();
    // if (profileValidate.numberOfInvalids() != curError) {
    // }
    // var validateTimer = setInterval(function() {
    //   if (profileValidate.numberOfInvalids() != curError) {
    //   }
    // },200);

    // 返回当前完成度百分比值
    function CountValidItems() {
        var validItems = 0;
        var totalTestItems = 12;

        // english_name
        var engValue = $("#english_name").val();
        if (engValue != null && engValue != '' && profileValidate.element(
                $(
                    "#english_name"))) {
            validItems++;
            //console.log("eng" + validItems);
        }
        // contract_func
        var contractValue = $("#contract_func").val();
        if (contractValue != null && contractValue != '' &&
            profileValidate.element(
                $("#contract_func"))) {
            validItems++;
            //console.log("contract" + validItems);
        }
        // email
        var emailValue = $("#email").val();
        if (emailValue != null && emailValue != '' && profileValidate.element(
                $(
                    "#email"))) {
            validItems++;
            //console.log("email" + validItems);
        }
        // real_name
        var realValue = $("#real_name").val();
        if (realValue != null && realValue != '' && profileValidate.element(
                $(
                    "#real_name"))) {
            validItems++;
            //console.log("real_name" + validItems);
        }
        // radio
        var radioValue = $('input:radio[name="gender"]:checked').val();
        if (radioValue != null) {
            validItems++;
            //console.log("radio" + validItems);
        }
        // idcard
        var idcardValue = $("#idcard").val();
        if (idcardValue != null && idcardValue != '' && profileValidate
            .element(
                $("#idcard"))) {
            validItems++;
            //console.log("idcard" + validItems);
        }
        // province
        var provinceValue = $("#province").val();
        if (provinceValue != null && provinceValue != '' &&
            provinceValue !=
            '省份') {
            validItems++;
            //console.log("province" + validItems);
        }
        // city
        var cityValue = $("#city").val();
        if (cityValue != null && cityValue != '' && cityValue != '城市') {
            validItems++;
            //console.log("city" + validItems);
        }
        // district
        var districtValue = $("#district").val();
        if (districtValue != null && districtValue != '' &&
            districtValue !=
            '县区') {
            validItems++;
            //console.log("district" + validItems);
        }
        // address
        var addressValue = $("#address").val();
        if (addressValue != null && addressValue != '' &&
            profileValidate.element(
                $("#address"))) {
            validItems++;
            //console.log("address" + validItems);
        }
        // learn_tool
        var learnValue = $("#learn_tool").val();
        if (learnValue != null && learnValue != '' && profileValidate.element(
                $(
                    "#learn_tool"))) {
            validItems++;
            //console.log("learn_tool" + validItems);
        }
        // personal_sign
        var signValue = $("#personal_sign").val();
        if (signValue != null && signValue != '' && profileValidate.element(
                $(
                    "#personal_sign"))) {
            validItems++;
            //console.log("personal_sign" + validItems);
        }
        //console.log(validItems);
        return parseInt(Math.floor((validItems / 12) * 100));
    }

    // 触发效果
    var ptActionList = {
        "ptvalidate": function() {
            var resultValue = CountValidItems();
            //console.log(resultValue);

            // var staticValue = parseInt($("#profile-rate").attr("data-srate"));
            // var curValue = parseInt($("#profile-rate").attr("data-rate"));
            RunProgressTime(resultValue, 300);
        }
    };

    $("body").on("focusout keyup", "[data-test]", function() {
        var actionName = $(this).data('test');
        var action = ptActionList[actionName];
        if ($.isFunction(action)) {
            action();
        }
    });

});
