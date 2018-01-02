//双击课程表，进入补课dialog
function subscribeSupplement(){
	var row = $('#dataGridCancelSubscribeCourse').datagrid('getSelections');
	if(row.length != 1){
		$.messager.alert('提示','请先选中一条数据！');
	   	return;
	}
	//只有上课结束时间 小于当前时间，才能补课
	if(row[0].end_time >= new Date()){
		$.messager.alert('提示','该节课还未结束，无法补课');
	   	return;
	}
	//能够补课逻辑，开始为form表单赋值
	//每次赋值钱，数据清空
	$('#supplement_user_id').val('');
	$('#supplement_user_name').val('');
	$('#supplement_course_id').val('')
	$('#supplement_course_title').val('');
	$('#supplement_subscribe_id').val('');
	$('#supplement_course_type').val('');
	$('#supplement_reason').textbox('setValue', '');
	
	
	//重新赋值
	$('#supplement_user_id').val(row[0].user_id);
	$('#supplement_user_name').val(userInfoData.user.user_name);
	$('#supplement_course_id').val(row[0].course_id);
	$('#supplement_course_title').val(row[0].course_title);
	$('#supplement_subscribe_id').val(row[0].subscribe_id);
	$('#supplement_course_type').val(row[0].course_type);
	
	$('#supplementDialogDiv').dialog('open');
}

//supplementFormSubmit()
function supplementFormSubmit(){
	$('#supplementForm').form('submit',{
        onSubmit:function(){
        	//easyui的校验
        	if($(this).form('enableValidation').form('validate')){
        		$('#supplementDialogDiv').dialog('close');
        		return true;
        	}else{
        		return false;
        	}
        },
		success:function(result) {
			result = eval("("+result+")");
			if (result.success) {
				$.messager.alert('提示','补课成功');
    		   	$('#dataGridCancelSubscribeCourse').datagrid('reload');	
			}else{
				$.messager.alert('提示','补课失败,'+result.msg);
			}
	    },
        error:function(status){
        	 $.messager.alert('提示','系统出现异常,请联系管理员','error'+status);
        }
    });
}


$(function(){
	//设置表单提交回调函数
    $('#followUpForm').form({
        success:function(data){
        	data = eval("("+data+")");
            if(data.success){
            	$.messager.alert('提示',data.msg);	
            	$('#followUpGrid').datagrid('reload');
            	$('#followUpForm').form('clear');
            	$('#followUpSaveDiv').dialog('close');
            }else{
            	$.messager.alert('提示',data.msg);
            } 
        }
    }); 
}); 

function followUpFormSubmit(){
	var followUpKeyId = $("#followup_key_id").val();
	if(followUpKeyId!=null&&followUpKeyId!=''){//更新
		$('#followUpSaveDiv').dialog('close');
	}else{//新增
		$("#followup_user_id").val(userInfoData.user.key_id);
		$('#followUpForm').submit();
	}
} 
		
//新增or编辑
function followUpEdit(operateType){
	$('#followUpForm').form('clear');
	
	if(operateType==1){//更新
		$('#followup_title').textbox('disable');
		$('#followup_content').textbox('disable');
		
		var row = $('#followUpGrid').datagrid('getSelections');
		if(row.length!=1){
			$.messager.alert('提示','请先选中一条数据！');
			return;
		}
		var key_id = row[0].key_id;
		
		$.ajax({
			type : "POST", //post提交方式默认是get
			dataType : 'json',
			url : basePath + '/admin/userFollowup/get',
			data : {
				key_id : key_id,
				wtf : Math.random()
			},
			error : function(data) { // 设置表单提交出错 
				$('#loading-mask').hide();
				$.messager.alert('提示', '系统出现异常,请联系管理员', 'error');
			},
			success : function(result) {
				$("#followup_key_id").val(result.key_id);
				$("#followup_title").textbox('setValue',result.followup_title);
				$("#followup_content").textbox('setValue',result.followup_content);
			}
		});
	}  else {
		$('#followup_title').textbox('enable');
		$('#followup_content').textbox('enable');
	}
	
	$('#followUpSaveDiv').dialog('open');
} 

//合同状态
function formatContractStatus(val) {
	var returnStr = '';
	switch (val) {
	case 1:
	case 2:
	case 3:
	case 4:
		returnStr = '待确认';
		break;
	case 5:
		returnStr = '执行中';
		break;
	case 6:
	case 7:
		returnStr = '已结束';
		break;
	}
	return returnStr;
}

//日期格式化(yyyy-MM-dd hh:mm:ss)
function formatDatehhmmss(val, rec) {
	if (val == null || val == '') {
		return '';
	}
	return new Date(val).format("yyyy-MM-dd hh:mm:ss");
}


var categoryType = '';
var courseType = '';

//取消预约课程
function cancelSubscribeCourse() {
	var row = $('#dataGridCancelSubscribeCourse').datagrid(
			'getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请先选中一条数据！');
		return;
	}
	
	$.messager.confirm("确认", "确认取消这节课吗？", function(r) {
		if (r) {
			if('course_type4' == row[0].course_type || 'course_type13' == row[0].course_type){
				var obj = {
						subscribeId : row[0].subscribe_id
				};
				$.ajax({
					type : "POST",
					dataType : 'json',
					timeout : 8000,
					contentType:'application/json;charset=UTF-8',
					url : basePath + "/admin/subscribeCourse/cancelSubscribeCourseDemo",
					data : JSON.stringify(obj),
					error : function(jqXHR, textStatus, errorThrown) {
						if (textStatus == "timeout") {
							alert("请求超时，请重新查看！");
						} else {
							alert('系统出现异常,请联系管理员!');
							alert(textStatus);
						}
					},
					success : function(result) {// 提交成功
						if (result.code == 200) {
							$('#dataGridCancelSubscribeCourse').datagrid(
							'reload');
							$.messager.alert('提示', "demo取消预约成功!");
			             } else if(result.code == 20011) {
			        		 if(result.data != 0){
			    				 $.messager.alert('提示', "该教师来自"+ result.data.thirdFrom  + "，需要提前" +
			    						 result.data.courseTypeSubscribeTime/60 + "小时取消预约！");
			        		 } else {
			        			 $.messager.alert('提示', errorCode[result.code]);
			        		 }
			             } else if(result.code == 20012) {
			            	 $.messager.alert('提示', "该教师来自环迅，需要提前取消预约！");
			             } else {
			            	 $.messager.alert('提示', errorCode[result.code]);
			            	 
			             }
					}
				});
			}else{ 
				$.ajax({
					type : "POST", //post提交方式默认是get
					dataType : 'json',
					url : basePath
							+ "/admin/user/cancelSubscribeCourseByAdmin",
					data : {
						key_id : row[0].subscribe_id,
						user_id : row[0].user_id,
						course_type : row[0].course_type
					},
					error : function(data) { // 设置表单提交出错 
						$('#loading-mask').hide();
						$.messager.alert('提示', '系统出现异常,请联系管理员', 'error');
					},
					success : function(result) {
						$('#loading-mask').hide();

						if (result.code == 200) {
							$('#dataGridCancelSubscribeCourse').datagrid(
									'reload');
							$.messager.alert('提示', "取消成功");
						}
						else if (result.code == 10001) {
							$.messager.alert('提示', result.msg);
						}
						else {
							$.messager.alert('提示', errorCode[result.code]);
						}
					}
				});
			}
		}
	});

}

//打开新的代订课的dialog
function subscribeCoursesButton() {
	//category_type初始化 每次都是重新加载
	$('#category_type')
			.combobox(
					{
						url : basePath + '/admin/user/findUserCategoryTypeList/' + userInfoData.user.key_id,
						valueField : 'category_type',
						textField : 'order_type',
						panelHeight : "auto",
						required : true,
						editable : false,
						onSelect : function(rec) {
							selectCourseType(rec.category_type);
						},
						onLoadSuccess : function() { //数据加载完毕事件
							selectCourseType("0");
						}
					});

	//打开新的代订课的dialog
	$('#subscribeDialogContrallor').dialog('open');
}

//预约课程
function userSubscribeThisCourse() {
	var row = $('#dataGridSubscribeCourse').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请先选中一条数据！');
		return;
	}
	$.ajax({
		type : "POST",
		dataType : 'json',
		timeout : 8000,
		url : basePath + "/admin/user/subscribeCourseByAdmin",
		data : {
			courseId : row[0].course_id,
			userId : row[0].user_id,
			teacherTimeId : row[0].teacher_time_id,
			courseType : row[0].course_type
		},
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus == "timeout") {
				alert("请求超时，请重新查看！");
			} else {
				alert('系统出现异常,请联系管理员!');
				alert(textStatus);
			}
		},
		success : function(result) {//提交成功
			if (result.code == 200) {
				$.messager.alert('提示', "预定成功");
				$('#subscribeDialogContrallor').dialog('close');
				$('#dataGridCancelSubscribeCourse').datagrid('reload');
			} 
			 else if (result.code == 10001) {
                 $.messager.alert('提示', result.msg);
             }
             else {
            	 if(result.msg == "预约课程失败！110"){
            		 $.messager.alert("学员没有设置英文名，无法预约课程，LC可以在为学员在'编辑个人信息'中填写英文名后为学员订课");
					}
					else{
						$.messager.alert('提示', errorCode[result.code]);
					}
             }
		},
		complete : function(data) {
		},
	});
}

//更具输入的条件，找合适的排课数据
function submitTeacherTime() {
	var courseId = $('#course_id').textbox('getValue');
	if (courseId == null || courseId == '') {
		$.messager.alert('提示', '请选择课程！');
		return;
	}

	//刷新dataGridSubscribeCourse
	var queryParams = {};
	queryParams["user_id"] = userInfoData.user.key_id;
	queryParams["category_type"] = categoryType;
	queryParams["course_type"] = courseType;
	queryParams["course_id"] = courseId;
	//时间：只有1对1或者小包课的时候，才会选择时间，1对多 不能选择时间
	if (courseType != null && courseType != 'course_type2') {
		var dateTime = $('#date_time').datebox('getValue');
		if (dateTime == null || dateTime == '') {
			$.messager.alert('提示', '非1对多课程类型，必须选择时间！');
			return;
		} else {
			queryParams["date_time"] = dateTime;
		}
	}
	//因为Controller中起别的URL 无效，使用caowei123
	$('#dataGridSubscribeCourse').datagrid('options').url = basePath
			+ '/admin/user/caowei123';
	$('#dataGridSubscribeCourse').datagrid('load', queryParams);
}

//选择了课程类别后，级联限制 能够选择的 课程
function selectCourseId(rec) {
	//每次重新选择时候，清空course_id date_time
	$('#course_id').combobox('setValue', '');
	$('#date_time').datebox('setValue', '');

	var course_type = $('#course_type').combobox('getValue');
	//给全局变量courseType赋值
	courseType = course_type;
	//1.获取课程列表
	$('#course_id').combobox(
			{
				url : basePath + '/admin/user/findUserCourseList?user_id=' + userInfoData.user.key_id
						+ '&category_type=' + categoryType
						+ '&course_type=' + courseType,
				valueField : 'course_id',
				textField : 'course_title',
				panelHeight : 150,
				required : true,
				editable : false, //不允许手动输入
				onLoadSuccess : function() { //数据加载完毕事件
				}
			});

	//2.判断选择的course_type是否是1对1，如果是，则时间选择框可选
	$('#date_time').datebox('disable');
	if (rec.courseTypeFlag == 1) {
		$('#date_time').datebox('enable');
	}
}

//选择了体系类别后，级联选择课程类别
function selectCourseType(category_type) {
	//初始化
	if (category_type == "0") {
		//alert("初始化啦~~~2222");
		$('#course_type').combobox('loadData', []);
		$('#course_type').combobox('setValue', '');
		$('#course_id').combobox('loadData', []);
		$('#course_id').combobox('setValue', '');
		$('#date_time').datebox('setValue', '');
	} else {
		if (category_type == null || category_type == '') {
			return;
		}
		//每次重新选择时候，清空course_id date_time
		$('#course_id').combobox('setValue', '');
		$('#date_time').datebox('setValue', '');

		//给全局变量categoryType赋值
		categoryType = category_type;

		switch (category_type) {
		case 'category_type1':
		case 'category_type2':
			$('#course_type').combobox({
				url:basePath + '/courseType/findCourseTypeByBookingClass',
				valueField : 'courseType',
				textField : 'courseTypeChineseName'
			});
			break;
		case 'category_type3':
		case 'category_type4':
			$('#course_type').combobox({
				url:basePath + '/courseType/findCourseTypeByBookingClass',
				valueField : 'courseType',
				textField : 'courseTypeChineseName'
			});
			break;
		}
	}
}

//编辑学员信息
function editStudentInfo() {
	/*
	var row = $('#dataGrid1').datagrid('getSelections');
	if(row.length!=1){
		$.messager.alert('提示','请先选中一条数据！');
		return;
	}
	 */

	var key_id = userInfoData.userInfo.key_id;
	var phone = userInfoData.user.phone;
	//var pwd =  '${user.pwd}';
	var real_name = userInfoData.userInfo.real_name;
	var english_name = userInfoData.userInfo.english_name;
	//var current_level = '${user.current_level}';

	var idcard = userInfoData.userInfo.idcard;
	var gender = userInfoData.userInfo.gender;

	var province = userInfoData.userInfo.province;
	var city = userInfoData.userInfo.city;
	var district = userInfoData.userInfo.district;
	var address = userInfoData.userInfo.address;

	var learn_tool = userInfoData.userInfo.learn_tool;
	var personal_sign = userInfoData.userInfo.personal_sign;
	var email = userInfoData.userInfo.email;
	var contract_func = userInfoData.userInfo.contract_func;

	//赋值
	$('#edit_key_id').val(key_id);
	$("#edit_phone").textbox('setValue', phone);
	$('#edit_real_name').textbox('setValue', real_name);
	$('#edit_english_name').textbox('setValue', english_name);
	//$('#edit_pwd').textbox('setValue',pwd);

	$("#edit_idcard").textbox('setValue', idcard);
	$('#edit_english_name').textbox('setValue', english_name);
	//省下拉框
	$('#edit_province').combobox(
			{
				data : provinceInfo,
				valueField : 'value',
				textField : 'text',
				editable : false,
				onSelect : function() {
					$('#provinceHidden').val($("#edit_province").combobox('getText'));
					//市下拉框
					$('#edit_city').combobox({
						data : cityInfo[$("#edit_province").combobox('getValue')]
					});
					$('#cityHidden').val("");
					
					//区下拉框
					$('#edit_district').combobox({
						data : []
					});
				}
			});
	$("#edit_province").combobox('setValue', province);
	$('#provinceHidden').val(province);
	
	//市下拉框
	$('#edit_city').combobox({
		data : [],
		valueField : 'value',
		textField : 'text',
		editable : false,
		onSelect : function() {
			$('#cityHidden').val(
					$("#edit_city").combobox('getText'));
			//区下拉框
			$('#edit_district').combobox({
				data : districtInfo[$("#edit_city").combobox('getValue')]
			});
		}
	});
	$('#edit_city').combobox('setValue', city);
	$('#cityHidden').val(city);
	
	//区下拉框
	$('#edit_district').combobox({
		data : [],
		valueField : 'value',
		textField : 'text',
		editable : false
	});
	$('#edit_district').combobox('select', district);
	
	$("#edit_address").textbox('setValue', address);

	$('#edit_learn_tool').textbox('setValue', learn_tool);
	$('#edit_personal_sign').textbox('setValue', personal_sign);
	$("#edit_email").textbox('setValue', email);
	$('#edit_contract_func').textbox('setValue', contract_func);

//		//真实姓名有值时锁定，不能修改
//		if (real_name == null || real_name == "") {
//			$('#edit_real_name').textbox({
//				editable : true
//			})
//		} else {

//			$('#edit_real_name').textbox({
//				editable : false
//			})
//		}

//		//身份真号有值时锁定，不能修改
//		if (idcard == null || idcard == "") {
//			$('#edit_idcard').textbox({
//				editable : true
//			})
//		} else {

//			$('#edit_idcard').textbox({
//				editable : false
//			})
//		}

	//性别下拉框
	$('#edit_gender').combobox({
			data : [ {
				value : '1',
				text : '男'
			}, {
				value : '0',
				text : '女'
			}, {
				value : '2',
				text : '暂不选择'
			} ],
			valueField : 'value',
			textField : 'text'
		});

	//性别不为2（未选择）时锁定，不能修改
	if (gender == "0") {
		$('#edit_gender').combobox('select', '0');
	} else if (gender == "1") {
		$('#edit_gender').combobox('select', '1');
	} else {
		$('#edit_gender').combobox('select', '2');
	}

	$('#editStudentInfoDiv').dialog('open');
}

//form 提交 修改学员数据
function submitEditStudentInfoForm() {
	$('#editStudentInfoForm').form('submit', {
		onSubmit : function() {
			//easyui的校验
			if ($(this).form('enableValidation').form('validate')) {
				//$('#editStudentInfoDiv').dialog('close');
				return true;
			} else {
				return false;
			}
		},
		success : function(result) {
			result = eval("(" + result + ")");

			if (result.success) {

				$('#editStudentInfoDiv').dialog('close');
				$('#lookUserDetailDiv').dialog('close');
				//refreshUserDetail('${userInfo.key_id}');

				$.messager.alert("提示", result.msg);
				$('#dataGrid1').datagrid('reload');
			} else {
				$.messager.alert("提示", result.msg);
			}
		},
		error : function(status) {

			$('#editStudentInfoDiv').dialog('close');

			$.messager.alert('提示', '系统出现异常,请联系管理员', 'error' + status);
		}
	});
}