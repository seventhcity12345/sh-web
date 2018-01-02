var curCodeSubscribe = null;

//校验手机号
function checkPhoneSubscribe() {
	var ret = false;
	var phone = $("#subscribe_phone").val();
	var szReg = /^1\d{10}$/;
	if (!szReg.test(phone)) {// 正则校验
		alert("请输入正确的手机号!");
		return false;
	}

	// 校验手机号是否重复

	$.ajax({
		type : "POST", // post提交方式默认是get
		dataType : 'json',
		async : false,
		url : basePath + '/checkRepeatSubscribe',
		data : {
			phone : phone
		// 参数
		},
		error : function(data) {// 设置表单提交出错
			alert('网络异常，请重试!');
		},
		success : function(result) {// 提交成功
			// alert(result.success);
			if (result.success) {
				ret = true;
			} else {
				alert(result.msg);
			}
		}
	});
	return ret;
}

// 发送验证码
function sendCodeSubscribe() {
	var boolFlag = checkPhoneSubscribe();// 校验手机
	if (boolFlag) {
		var phone = $("#subscribe_phone").val();
		curCodeSubscribe = Math.floor((Math.random() * 9 + 1) * 1000);
		// 获取验证码
		$.ajax({
			type : "POST", // post提交方式默认是get
			dataType : 'json',
			url : basePath + '/sendSms',
			data : {
				mobile : phone,
				ckno : curCodeSubscribe
			},
			error : function(data) {// 设置表单提交出错
				alert('网络异常，请重试!');
			},
			success : function(result) {// 提交成功
				if (result != "1") {// 失败
					alert("短信发送失败，很抱歉!" + result);
				} else {// 成功
					setTimeout(valnumSubscribe(60), 1000);
				}
			}
		});
	}
}

function valnumSubscribe(is) {
	if (is == 0) {
		$("#yzma_subscribe").attr("onclick", "sendCodeSubscribe()");
		$("#yzma_subscribe").text("获取验证码");
		$("#yzma_subscribe").removeClass("btn-disabled");
		is--;
	} else {
		$("#yzma_subscribe").addClass("btn-disabled");
		$("#yzma_subscribe").attr("onclick", "void(0)");
		$("#yzma_subscribe").text(is + "秒后获取");
		is--;
		setTimeout(function() {
			valnumSubscribe(is)
		}, 1000);
	}
}

// 注册
function registerSubscribe() {
	var boolFlag = checkPhoneSubscribe();// 校验手机
	if (boolFlag) {
		var subscribe_name = $("#subscribe_name").val();
		if (subscribe_name.length == 0) {
			alert("请输入姓名！");
			return;
		}
		
		var subscribe_phone = $("#subscribe_phone").val();
		if (subscribe_phone.length == 0) {
			alert("请输入手机号!");
			return;
		}
		
		var code = $("#code_subscribe").val();
		if (code != curCodeSubscribe) {
			alert("请输入正确的验证码！");
			return;
		}
		
		var job = $("#job").val();
		if (job.length == 0) {
			alert("请输入职业!");
			return;
		}
		
		var reason = $("#reason").val();
		if (reason.length == 0) {
			alert("请输入学习理由!");
			return;
		}else if(reason.length>100){
			alert("学习理由最多为100字!");
			return;
		}
		

		$.ajax({
			type : "POST", // post提交方式默认是get
			dataType : 'json',
			timeout : 8000,
			url : basePath + '/subscribeUserRegister',
			data : {
				phone : subscribe_phone,
				name :  subscribe_name,
				reason : reason,
				job : job
			},
			beforeSend : BeginLoading,
			error : function(jqXHR, textStatus, errorThrown) {
				EndLoading();
				if (textStatus == "timeout") {
					alert("请求超时，页面自动刷新！");
				} else {
					alert('网络不稳定，请重试~~~');
					alert(textStatus);
				}
			},
			success : function(result) {// 提交成功
				EndLoading();
				if (result.success) {
					alert("预约成功~");// 后台需要使用 new JsonMessage()对象返回
					history.go(0);
				} else {
					alert(result.msg);
				}
			}
		});
	}
}

/* for page style
/**************************************************/
 $(function(){
         var abLength = $(".stu").length;
          $(".stu").css("cursor","pointer");
         for(var i=0;i<abLength;i++){
              $(".stu").eq(i).hover(function(){
                  $(this).find("div").show();
              },function(){$(this).find("div").hide()});
            
         };



         $('.img').rotate({angle:45});
         $(".part2-super img").rotate({ 
           bind: 
             { 
                mouseover : function() { 
                    $(this).rotate({animateTo:180});
                },
                mouseout : function() { 
                    $(this).rotate({animateTo:0});
                }
             } 
           
        });

         $(".part2-super div:nth-child(2n)").css('float','right')
    })


