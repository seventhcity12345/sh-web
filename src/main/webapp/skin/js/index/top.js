$(function() {
	$('#login_pwd').keydown(function(e) {
		if (e.keyCode == 13) {
			login();
		}
	});
});

// CRM 's cityName
var cityName = "上海";
$.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js',
		function(_result) {
			if (remote_ip_info.ret == '1') {
				if (remote_ip_info.city != null && remote_ip_info.city != ''
						&& remote_ip_info.city != undefined) {
					cityName = remote_ip_info.city;
				}
			} else {
				alert('没有找到匹配的IP地址信息！');
			}
		});

// 学员登录
function login() {
	var login_check = $("#login_checkbox").prop("checked");
	var login_phone = $("#login_phone").val();
	if (login_phone.length == 0) {
		alert("手机号不能为空！");
		return false;
	}
	var login_pwd = $("#login_pwd").val();
	if (login_pwd.length == 0) {
		alert("密码不能为空！");
		return false;
	}
	if (login_phone.length != 11) {
		alert("手机号为11位！");
		return false;
	}

	if (login_pwd.length < 6 || login_pwd.length > 20) {
		alert("密码的长度为6-20！");
		return false;
	}

	$
			.ajax({
				type : "POST", // post提交方式默认是get
				dataType : 'json',
				url : basePath + '/encodeLogin',
				data : {
					code : login_pwd
				},
				error : function(data) { // 设置表单提交出错
					alert('网络不稳定，请重试~~~');
				},
				success : function(result) {// 提交成功
					if (result.success) {
						$
								.ajax({
									type : "POST", // post提交方式默认是get
									dataType : 'json',
									url : basePath + '/login',
									data : {
										phone : login_phone,
										pwd : result.msg,
										loginFrom : "",
										remember_me_for_cookie : login_check == true ? "remember_me_for_cookie"
												: false,
									},
									error : function(data) { // 设置表单提交出错
										alert('网络不稳定，请重试~~~');
									},
									success : function(result) {// 提交成功
										if (result.success) {
											location.href = basePath
													+ "/ucenter/index";
										} else {
											alert("用户名或密码错误!");
											$("#login_pwd").val("");
										}
									}
								});
					} else {
						alert('加密出现异常!');
					}
				}
			});
}

// 校验手机号
function checkPhone() {
	var ret = false;
	var phone = $("#reg_phone").val();
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
		url : basePath + '/checkRepeat',
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

// 学员注册发送验证码
function sendCode() {
	var boolFlag = checkPhone();// 校验手机
	if (boolFlag) {
		var phone = $("#reg_phone").val();
		// 发送验证码
		$.ajax({
			type : "POST", // post提交方式默认是get
			dataType : 'json',
			url : basePath + '/sendSms',
			data : {
				mobile : phone
			},
			error : function(data) {// 设置表单提交出错
				alert('网络异常，请重试!');
			},
			success : function(result) {// 提交成功
				if (result != "1") {// 失败
					alert("短信发送失败，很抱歉!");
				} else {// 成功
					setTimeout(valnum(60), 1000);
				}
			}
		});
	}
}

function valnum(is) {
	if (is == 0) {
		$("#yzma").attr("onclick", "sendCode()");
		$("#yzma").text("获取验证码");
		$("#yzma").removeClass("btn-disabled");
		is--;
	} else {
		$("#yzma").addClass("btn-disabled");
		$("#yzma").attr("onclick", "void(0)");
		$("#yzma").text(is + "秒后获取");
		is--;
		setTimeout(function() {
			valnum(is)
		}, 1000);
	}
}

// 注册
function register() {
	var boolFlag = checkPhone();// 校验手机
	if (boolFlag) {
		var code = $("#code").val();
		if (code.length == 0) {
			alert("请输入验证码!");
			return;
		}
		var pwd = $("#reg_pwd").val();
		if (pwd.length == 0) {
			alert("请输入密码!");
			return;
		}
		if (pwd.length < 6 || update_oldPassword.length > 20) {
			alert("密码长度为6-20位!");
			return;
		}
		var reg_phone = $("#reg_phone").val();

		var ADID = $("#ADID").val();

		if (ADID != null && ADID != "" && ADID != "null") {
		} else {
			// default
			ADID = "QGHQFN06NI";
		}

		/**
		 * modify by komi 2016年5月26日14:26:22  
		 * crm修改至后台异步化，消息队列
		 */
		// normal register logic
		// 获取加密密码
		$.ajax({
			type : "POST", // post提交方式默认是get
			dataType : 'json',
			url : basePath + '/encodeRegister',
			data : {
				code : pwd
			},
			error : function(data) { // 设置表单提交出错
				alert('加密出现异常!');
			},
			success : function(result) {// 提交成功
				if (result.success) {
					$.ajax({
						type : "POST", // post提交方式默认是get
						dataType : 'json',
						timeout : 8000,
						url : basePath + '/register',
						data : {
							phone : $("#reg_phone").val(),
							pwd : result.msg,// 加密后的密码
							code : code,
							adid : ADID
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
								alert("注册成功~");// 后台需要使用 new JsonMessage()对象返回
								location.href = basePath + "/ucenter/index";
							} else {
								alert(result.msg);
							}
						}
					});
				} else {
					alert("用户名或密码错误!");
					$("#login_pwd").val("");
				}
			}
		});
	}
}

// 学员修改密码
function updateUserPassword() {
	var update_oldPassword = $("#update_oldPassword").val();
	if (update_oldPassword.length == 0) {
		alert("请输入原始密码!");
		return;
	}
	if (update_oldPassword.length < 6 || update_oldPassword.length > 20) {
		alert("密码长度为6-20位!");
		return;
	}

	var update_newPassword = $("#update_newPassword").val();
	if (update_newPassword.length == 0) {
		alert("请输入新密码!");
		return;
	}
	if (update_newPassword.length < 6 || update_newPassword.length > 20) {
		alert("密码长度为6-20位!");
		return;
	}
	// 前端加密：获取加密密码
	$
			.ajax({
				type : "POST", // post提交方式默认是get
				dataType : 'json',
				url : basePath + '/encodeRegister',
				data : {
					code : update_oldPassword
				},
				error : function(data) { // 设置表单提交出错
					alert('加密出现异常!');
				},
				success : function(data) {// 提交成功
					update_oldPassword = data.msg;

					$
							.ajax({
								type : "get", // post提交方式默认是get
								dataType : 'json',
								url : basePath + '/encodeRegister',
								data : {
									code : update_newPassword
								},
								error : function(data) { // 设置表单提交出错
									alert('加密出现异常!');
								},
								success : function(result) {// 提交成功
									update_newPassword = result.msg;

									if (result.success) {
										$
												.ajax({
													type : "POST", // post提交方式默认是get
													dataType : 'json',
													timeout : 8000,
													url : basePath
															+ '/ucenter/user/updateUserPassword',
													data : {
														update_newPassword : update_newPassword,// 加密后的密码
														update_oldPassword : update_oldPassword
													},
													beforeSend : BeginLoading,
													error : function(jqXHR,
															textStatus,
															errorThrown) {
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
															alert("密码修改成功~");// 后台需要使用
																				// new
																				// JsonMessage()对象返回
															location.href = basePath
																	+ "/ucenter/index";
														} else {
															alert(result.msg);
														}
													}
												});
									} else {
										alert("密码错误!");
										$("#update_oldPassword").val('');
										$("#update_newPassword").val('');
									}
								}
							});
				}
			});
}

// 老师修改密码
function updateTeacherPassword() {
	var update_oldPassword = $("#updateTeacher_oldPassword").val();
	if (update_oldPassword.length == 0) {
		alert("请输入原始密码!");
		return;
	}
	if (update_oldPassword.length < 6 || update_oldPassword.length > 20) {
		alert("密码长度为6-20位!");
		return;
	}

	var update_newPassword = $("#updateTeacher_newPassword").val();
	if (update_newPassword.length == 0) {
		alert("请输入新密码!");
		return;
	}
	if (update_newPassword.length < 6 || update_newPassword.length > 20) {
		alert("密码长度为6-20位!");
		return;
	}
	// 前端加密：获取加密密码
	$.ajax({
		type : "POST", // post提交方式默认是get
		dataType : 'json',
		url : basePath + '/encodeRegister',
		data : {
			code : update_oldPassword
		},
		error : function(data) { // 设置表单提交出错
			alert('加密出现异常!');
		},
		success : function(data) {// 提交成功
			update_oldPassword = data.msg;

			$.ajax({
				type : "get", // post提交方式默认是get
				dataType : 'json',
				url : basePath + '/encodeRegister',
				data : {
					code : update_newPassword
				},
				error : function(data) { // 设置表单提交出错
					alert('加密出现异常!');
				},
				success : function(result) {// 提交成功
					update_newPassword = result.msg;

					if (result.success) {
						$.ajax({
							type : "POST", // post提交方式默认是get
							dataType : 'json',
							timeout : 8000,
							url : basePath + '/tcenter/updateTeacherPassword',
							data : {
								update_newPassword : update_newPassword,// 加密后的密码
								update_oldPassword : update_oldPassword
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
									alert("密码修改成功~");// 后台需要使用 new
														// JsonMessage()对象返回
									location.href = basePath + "/t";
								} else {
									alert(result.msg);
								}
							}
						});
					} else {
						EndLoading();
						alert("密码错误!");
						$("#update_oldPassword").val('');
						$("#update_newPassword").val('');
					}
				}
			});
		}
	});
}
