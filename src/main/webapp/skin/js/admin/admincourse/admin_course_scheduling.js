//时间选项
var hourData = new Array();
var minuteData = new Array();
// 分钟的间隔
var minuteSpliteSize = 5;
// 只能选 6-24 时 的时间
var beginHour = 6;
// 初始化时间选择框
$(function() {
	// 普通排课表单
	initTimeSelectNormal();
	// 环迅表单
	initTimeSelectHuanxun();
});
// 将小于0的前面加0
function transNumber(number) {
	if (number < 10) {
		return '0' + number;
	} else {
		return number;
	}
}

// 列表双击事件显示详细信息和URL
function dblClickRowListener(rowData) {
	// 返显到表单中
	$('#one2manySchedulingInfoFrom').form('load', {
		startTimeInfo : formatDate(rowData.startTime),
		endTimeInfo : formatDate(rowData.endTime),
		courseTypeInfo : rowData.courseType,
		teacherNameInfo : rowData.teacherName,
		courseLevelInfo : rowData.courseLevel,
		courseTitleInfo : rowData.courseTitle,
		createDateInfo : formatDate(rowData.createDate),
		isSubscribeInfo : formatYesOrNo(rowData.isSubscribe),
		isConfirmInfo : formatYesOrNo(rowData.isConfirm),
		teacherUrlInfo : rowData.teacherUrl,
		studentUrlInfo : rowData.studentUrl
	});
	$('#schedulingInfoDialog').dialog('open');
}

//大课更换老师
function changeTeacherOneToMany() {
	var row = $('#one2manySchedulingDataGrid').datagrid('getSelections');
	
	if (row.length != 1) {
		$.messager.alert('提示', '请选中一条数据！');
		return;
	}
	
//	if(row[0].thirdFrom == "huanxun"){
//		$.messager.alert('提示', '环迅老师暂时不支持更换！');
//		return;
//	}
	
	var findTeacherUrl = basePath
	+ '/admin/courseOne2manyScheduling/findAvailableTeacher?';
	findTeacherUrl += '&startTime=' + formatDate(row[0].startTime)+":00" + '&courseType=' + row[0].courseType;
	
	$.ajax({
		type : "POST", // post提交方式默认是get
		dataType : 'json',
		url : findTeacherUrl,
		error : function(data) { // 设置表单提交出错
			$.messager.alert('提示', '系统出现异常,请联系管理员', 'error');
		},
		success : function(result) {
			if(result.length == 0){
				$.messager.alert('提示', '没有可以更换的老师！');
				return;
			}
			var str = "";
			for(var i = 0,len=result.length;i<len;i++){
				if(i == 0){
					str = str + "<input type='radio' name='teacherId' value='"+result[i].teacherId+"' checked='checked'>"+result[i].account+"</input><br>";
				}
				else{
					str = str + "<input type='radio' name='teacherId' value='"+result[i].teacherId+"'>"+result[i].account+"</input><br>";
				}
			}
			str = str + "<input type='hidden' name='startTime' id='changeStartTime'></input>";
			str = str + "<input type='hidden' name='endTime' id='changeEndTime'></input>";
			str = str + "<input type='hidden' name='thirdFrom' id='thirdFrom'></input>";
			//大课才需要大课排课表id
			str = str + "<input type='hidden' name='courseId' id='changeCourseId'></input>";
			str = str + "<input type='hidden' name='teacherTimeId' id='changeTeacherTimeId'></input>";
			

			$('#changeTeacherFrom').html(str);
			
			$('#changeStartTime').val(formatDate(row[0].startTime)+":00");
			$('#changeEndTime').val(formatDate(row[0].endTime)+":00");
			$('#thirdFrom').val(row[0].thirdFrom);
			//大课才需要大课排课表id
			$('#changeCourseId').val(row[0].keyId);
			$('#changeTeacherTimeId').val(row[0].teacherTimeId);
			
			$('#changeTeacherDialog').dialog('open');
		}
	});
	

}

//1v1更换老师
function changeTeacherOneToOne() {
	var row = $('#one2oneSchedulingDataGrid').datagrid('getSelections');
	
	if (row.length != 1) {
		$.messager.alert('提示', '请选中一条数据！');
		return;
	}
	
	/**
	 * modified by komi 2017年5月3日17:24:05
	 * 环迅老师现在也要可以换
	 */
//	if(row[0].thirdFrom == "huanxun"){
//		$.messager.alert('提示', '环迅老师暂时不支持更换！');
//		return;
//	}
	
	var findTeacherUrl = basePath
	// modify by seven 更换老师要根据订的课的course_type 来更换
	+ '/admin/courseOne2manyScheduling/findAvailableTeacherByTeacherTime?';
	findTeacherUrl += '&startTime=' + formatDate(row[0].startTime)+":00" + '&courseType=' + row[0].courseType
	 + '&teacherTimeId=' + row[0].keyId;
	
	$.ajax({
		type : "POST", // post提交方式默认是get
		dataType : 'json',
		url : findTeacherUrl,
		error : function(data) { // 设置表单提交出错
			$.messager.alert('提示', '系统出现异常,请联系管理员', 'error');
		},
		success : function(result) {
			if(result.length == 0){
				$.messager.alert('提示', '没有可以更换的老师！');
				return;
			}
			var str = "";
			for(var i = 0,len=result.length;i<len;i++){
				if(i == 0){
					str = str + "<input type='radio' name='teacherId' value='"+result[i].teacherId+"' checked='checked'>"+result[i].account+"</input><br>";
				}
				else{
					str = str + "<input type='radio' name='teacherId' value='"+result[i].teacherId+"'>"+result[i].account+"</input><br>";
				}
			}
			str = str + "<input type='hidden' name='startTime' id='changeStartTime'></input>";
			str = str + "<input type='hidden' name='endTime' id='changeEndTime'></input>";
			str = str + "<input type='hidden' name='thirdFrom' id='thirdFrom'></input>";
			str = str + "<input type='hidden' name='teacherTimeId' id='changeTeacherTimeId'></input>";
			
			$('#changeTeacherFrom').html(str);
			
			$('#changeStartTime').val(formatDate(row[0].startTime)+":00");
			$('#changeEndTime').val(formatDate(row[0].endTime)+":00");
			$('#changeTeacherTimeId').val(row[0].keyId);
			$('#thirdFrom').val(row[0].thirdFrom);
			
			$('#changeTeacherDialog').dialog('open');
		}
	});
	

}
//提交更换老师
function changeTeacherSubmit(dataGridName) {
	$('#changeTeacherFrom')
	.form(
			'submit',
			{
				url : basePath
						+ '/admin/course/changeTeacher',
				onSubmit : function(data) {
					if ($('#changeTeacherFrom').form('validate')) {
						 $('#changeTeacherDialog').dialog('close');
						return true;
					} else {
						return false;
					}
				},
				success : function(result) {
					result = eval("("+result+")");
					$(dataGridName).datagrid('reload');
					if (result.code == 200) {
						$.messager.alert('提示', "更改成功！");
					} else if (result.code == 10001){
						$.messager.alert('提示', result.msg);
					}
					else{
						$.messager.alert('提示', errorCode[result.code]);
					}
				},
				error : function(status) {
					$.messager.alert('提示', '系统出现异常,请联系管理员', 'error'
							+ status);
				}
			});
}
// 删除排课
function deleteScheduling() {
	var row = $('#one2manySchedulingDataGrid').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请选中一条数据！');
		return;
	}
	$.messager.confirm('确认', '是否确认删除排课？', function(r) {
		if (r) {
			$.ajax({
				type : "POST", // post提交方式默认是get
				dataType : 'json',
				url : basePath
						+ '/admin/courseOne2manyScheduling/deleteScheduling',
				data : {
					keyId : row[0].keyId,
					teacherTimeId : row[0].teacherTimeId,
					courseType : row[0].courseType
				},
				error : function(data) { // 设置表单提交出错
					$.messager.alert('提示', '系统出现异常,请联系管理员', 'error');
				},
				success : function(result) {
					if (result.success) {
						$('#one2manySchedulingDataGrid').datagrid('reload');
						$.messager.alert('提示', result.msg);
					} else {
						$.messager.alert('提示', result.msg);
					}
				}
			});
		}
	});
}

// 创建排课
function addScheduling() {
	$('#addSchedulingFrom').form('clear');
	
	$("#categoryType").combobox('select',"category_type2");
	
	$("#courseType").combobox('select',"course_type2");
	 
	$('#addSchedulingDialog').dialog('open');
	
}

// level随课程体系变更
function changeCategoryListener(newValue) {
	// 将全局变量初始化为combobox所需要的对象数组
	var categorySourceData = new Array();
	if ('category_type1' == newValue) {
		categorySourceData = businessLevelInfo;
	} else if ('category_type2' == newValue) {
		categorySourceData = generalLevelInfo;
	}
	var categorySelectData = new Array();
	for (var i = 0; i < categorySourceData.length; i++) {
		var elemet = new Object();
		elemet.text = categorySourceData[i].courseLevel;
		elemet.value = categorySourceData[i].courseLevel;
		categorySelectData[i] = elemet;
	}
	$('#courseLevel').combobox({
		data : categorySelectData,
		disabled : false
	});
	// 改变时重新加载课程
	courseDataLoader();
}

// 课程类型变更监听
function courseTypeChangeListener(newValue) {
	// oc课程显示URL
//	if ('course_type5' != newValue ) {
//		$('#teacherUrlTr').hide();
//		$('#studentUrlTr').hide();
//		$('#teacherUrl').textbox({
//			required : false,
//			disabled : true
//		});
//		$('#studentUrl').textbox({
//			required : false,
//			disabled : true
//		});
//	} else {
//		$('#teacherUrlTr').show();
//		$('#studentUrlTr').show();
//		$('#teacherUrl').textbox({
//			required : true,
//			disabled : false
//		});
//		$('#studentUrl').textbox({
//			required : true,
//			disabled : false
//		});
//	}
	
	$('#teacherUrlTr').hide();
	$('#studentUrlTr').hide();
	$('#teacherUrl').textbox({
		required : false,
		disabled : true
	});
	$('#studentUrl').textbox({
		required : false,
		disabled : true
	});
	
	// 改变时重新加载课程
	courseDataLoader();

	// 加载老师
	teacherDataLoader();
}

// 选择开始小时监听
function startHourSelectListener(newValue) {
	// 结束小时要大于等于开始小时
	$('#endHour').combobox({
		data : hourData.slice(newValue),
	});
	$('#endHour').combobox('clear');
}
// 选择结束小时监听
function endHourSelectListener(newValue) {
	// 开始小时等于结束小时则结束分钟要大于开始分钟
	if (newValue == $('#startHour').combobox('getValue')) {
		var startSelectMinute = $('#startMinute').combobox('getValue');
		if (startSelectMinute > 0) {
			$('#endMinute').combobox({
				data : minuteData.slice(startSelectMinute / 5 + 1)
			});
		} else {
			$('#endMinute').combobox({
				data : minuteData
			});
		}
		// 结束小时等于24则结束分钟只能是00
	} else if (newValue == 24) {
		$('#endMinute').combobox({
			data : minuteData.slice(0, 1)
		});
	} else {
		$('#endMinute').combobox({
			data : minuteData
		});
	}
}

// 选择开始分钟监听
function startMinuteSelectListener(newValue) {
	endHourSelectListener($('#endHour').combobox('getValue'));
	$('#endMinute').combobox('clear');
}

// 加载课程信息
function courseDataLoader() {
	// 组装请求地址
	var findCourseUrl = basePath
			+ '/admin/adminCourseOne2many/findOne2ManyCourse?';
	findCourseUrl += '&categoryType=' + $('#categoryType').combobox('getValue')
			+ '&courseType=' + $('#courseType').combobox('getValue')
			+ '&courseLevel=' + $('#courseLevel').combobox('getValue')
	// 加载课程
	$('#courseId').combobox({
		url : findCourseUrl,
		valueField : 'keyId',
		textField : 'courseTitle'
	});
}

// 加载老师信息
function teacherDataLoader() {
	var startDate = $('#schedulingDate').datebox('getValue');
	var startHour = $('#startHour').combobox('getValue');
	var startMinute = $('#startMinute').combobox('getValue');

	// 清空选项
	$('#teacherId').combobox({
		url : '',
		data : []
	});

	// 获得老师上课类型
	var courseType = $('#courseType').combobox('getValue');
	// 判断时间是否完整
	if (startDate == '' || startHour == '' || startMinute == ''
			|| courseType == '') {
		return;
	}
	// 获得开始时间
	var startTime = getFallTime(startDate, startHour, startMinute);

	// 组装请求地址
	var findTeacherUrl = basePath
			+ '/admin/courseOne2manyScheduling/findAvailableTeacher?';
	findTeacherUrl += '&startTime=' + startTime + '&courseType=' + courseType;

	// 重新加载
	$('#teacherId').combobox({
		url : findTeacherUrl,
		valueField : 'teacherId',
		textField : 'account'
	});

}
// 获得完整时间
function getFallTime(date, hour, minite) {
	return date + ' ' + hour + ':' + minite + ':00'
}

// 提交添加表单
function submitAddScheduling() {
	// 获取当前日期
	var selectDate = $('#schedulingDate').datebox('getValue');
	if (diffDate(selectDate) == 1) {
		$.messager.alert('提示', "选择日期小于当前日期,请重新选择!");
		return;
	}
	// 获得开始时间
	var startTime = getFallTime($('#schedulingDate').datebox('getValue'), $(
			'#startHour').combobox('getValue'), $('#startMinute').combobox(
			'getValue'));

	$('#addSchedulingFrom')
			.form(
					'submit',
					{
						url : basePath
								+ '/admin/courseOne2manyScheduling/addAdminCourseOne2ManyScheduling',
						onSubmit : function(data) {
							if ($('#addSchedulingFrom').form('validate')) {
								var options ={title:'',msg:'提交中',text:'请耐心等待...',interval:300};
								$.messager.progress(options);//进度条提示消息
								data.startTime = startTime;
								return true;
							} else {
								return false;
							}
						},
						success : function(result) {
							$.messager.progress('close');
							var res;
							res = eval('(' + result + ')');
							$('#one2manySchedulingDataGrid').datagrid('reload');
							if (res.success) {
								$('#addSchedulingDialog').dialog('close');
								$.messager.alert('提示', res.msg);
							} else {
								$.messager.alert('提示', res.msg);
							}
						},
						error : function(status) {
							$.messager.progress('close');
							$.messager.alert('提示', '系统出现异常,请联系管理员', 'error'
									+ status);
						}
					});
}
// 比较当前日期和选择日期
function diffDate(evalue) {
	var now = new Date();
	var dB = new Date(evalue.replace(/-/g, "/"));
	if (new Date(now.toLocaleDateString().replace(/-/g, "/")) > Date.parse(dB)) {
		return 1;
	}
	return 0;
}

// 初始化时间框普通使用
function initTimeSelectNormal() {
	// 将小时初始胡
	for (var i = 0; i <= 24; i++) {
		var hourElement = new Object();
		hourElement.text = transNumber(i);
		hourElement.value = transNumber(i);
		hourData[i] = hourElement;
	}
	// 将分钟初始胡
	for (var j = 0; j < 60 / minuteSpliteSize; j++) {
		var minuteElement = new Object();
		minuteElement.text = transNumber(j * minuteSpliteSize);
		minuteElement.value = transNumber(j * minuteSpliteSize);
		minuteData[j] = minuteElement;
	}
	// 初始化开始小时
	$('#startHour').combobox({
		data : hourData.slice(beginHour, hourData.length - 1),
	});
	// 初始化开始分钟
	$('#startMinute').combobox({
		data : minuteData,
	});
}


///////////////////////////////////////
// 下面是环迅排课使用的方法，如果和之前的用一套会显得特别麻烦，很乱。就另外写一套

// 初始化时间框环迅使用
function initTimeSelectHuanxun() {

	// 环迅只能整点和半点开课
	var huanxunMinuteDate = new Array();

	// 将分钟初始胡
	for (var j = 0; j < 60 / 30; j++) {
		var minuteElement = new Object();
		minuteElement.text = transNumber(j * 30);
		minuteElement.value = transNumber(j * 30);
		huanxunMinuteDate[j] = minuteElement;
	}

	// 初始化开始小时
	$('#huanxunStartHour').combobox({
		data : hourData.slice(beginHour, hourData.length - 1),
	});
	// 初始化开始分钟
	$('#huanxunStartMinute').combobox({
		data : huanxunMinuteDate,
	});
}

// 环迅创建排课
function huanxunAddScheduling() {
	$('#huanxunAddSchedulingFrom').form('clear');
	
	$("#huanxunCategoryType").combobox('select',"category_type2");
	
	$("#huanxunCourseType").combobox('select',"course_type2");
	
	$('#huanxunAddSchedulingDialog').dialog('open');
}

// 环迅排课level随课程体系变更
function huanxunChangeCategoryListener(newValue) {
	// 将全局变量初始化为combobox所需要的对象数组
	var categorySourceData = new Array();
	if ('category_type1' == newValue) {
		categorySourceData = businessLevelInfo;
	} else if ('category_type2' == newValue) {
		categorySourceData = generalLevelInfo;
	}
	var categorySelectData = new Array();
	for (var i = 0; i < categorySourceData.length; i++) {
		var elemet = new Object();
		elemet.text = categorySourceData[i].courseLevel;
		elemet.value = categorySourceData[i].courseLevel;
		categorySelectData[i] = elemet;
	}
	$('#huanxunCourseLevel').combobox({
		data : categorySelectData,
		disabled : false
	});
	// 改变时重新加载课程
	huanxunCourseDataLoader();
}

// 环迅加载课程信息
function huanxunCourseDataLoader() {
	// 组装请求地址
	var findCourseUrl = basePath
			+ '/admin/adminCourseOne2many/findOne2ManyCourse?';
	findCourseUrl += '&categoryType='
			+ $('#huanxunCategoryType').combobox('getValue') + '&courseType='
			+ $('#huanxunCourseType').combobox('getValue') + '&courseLevel='
			+ $('#huanxunCourseLevel').combobox('getValue')
	// 加载课程
	$('#huanxunCourseId').combobox({
		url : findCourseUrl,
		valueField : 'keyId',
		textField : 'courseTitle'
	});
}

// 环迅课程类型变更监听
function huanxunCourseTypeChangeListener(newValue) {
	// 改变时重新加载课程
	huanxunCourseDataLoader();
}


//环迅提交添加表单
function huanxunSubmitAddScheduling() {
	// 获取当前日期
	var selectDate = $('#huanxunSchedulingDate').datebox('getValue');
	if (diffDate(selectDate) == 1) {
		$.messager.alert('提示', "选择日期小于当前日期,请重新选择!");
		return;
	}
	// 获得开始时间
	var startTime = getFallTime($('#huanxunSchedulingDate').datebox('getValue'), $(
			'#huanxunStartHour').combobox('getValue'), $('#huanxunStartMinute').combobox(
			'getValue'));

	$('#huanxunAddSchedulingFrom')
			.form(
					'submit',
					{
						url : basePath
								+ '/admin/courseOne2manyScheduling/addHuanxunCourseOne2ManyScheduling',
						onSubmit : function(data) {
							if ($('#huanxunAddSchedulingFrom').form('validate')) {
								var options ={title:'',msg:'提交中',text:'请耐心等待...',interval:300};
								$.messager.progress(options);//进度条提示消息
								data.startTime = startTime;
								return true;
							} else {
								return false;
							}
						},
						success : function(result) {
							$.messager.progress('close');
							var res;
							res = eval('(' + result + ')');
							$('#one2manySchedulingDataGrid').datagrid('reload');
							if (res.success) {
								$('#huanxunAddSchedulingDialog').dialog('close');
								$.messager.alert('提示', res.msg);
							} else {
								$.messager.alert('提示', res.msg);
							}
						},
						error : function(status) {
							$.messager.progress('close');
							$.messager.alert('提示', '系统出现异常,请联系管理员', 'error'
									+ status);
						}
					});
}

/////////////////////////////////////去除大课的level

//修改课程类型监听
function courseTypeOnSelectListener(rec){
	hideOrShowLevel(rec.courseType);
	if("course_type8" == rec.courseType || "course_type5" == rec.courseType){
		courseDataLoader();
	}
	
}
// 根据课程类型隐藏课程级别
function hideOrShowLevel(courseType){
	if("course_type8" == courseType || "course_type5" == courseType){
	    $('#courseLevelTr').hide();
	    $('#courseLevel').combobox('setValue','');
		$('#courseLevel').combobox('disable');
		$('#courseLevel').combobox('disableValidation');
		
		$('#platformTr').show();
		$('#platform').combobox('setValue','');
		$('#platform').combobox('enable');
		$('#platform').combobox('enableValidation');
		
		 $('#platform').combobox('setValue','1');
	} else {
	    $('#courseLevelTr').show();
	    $('#courseLevel').combobox('setValue','');
		$('#courseLevel').combobox('enable');
		$('#courseLevel').combobox('enableValidation');

		$('#platformTr').hide();
	    $('#platform').combobox('setValue','');
		$('#platform').combobox('disable');
		$('#platform').combobox('disableValidation');
	}
}

/////////////////////////////////////去除大课的levelhuanxun

//修改课程类型监听
function huanxunCourseTypeOnSelectListener(rec){
	huanxunHideOrShowLevel(rec.value);
	if("course_type8" == rec.courseType || "course_type5" == rec.courseType){
		huanxunCourseDataLoader();
	}
}
// 根据课程类型隐藏课程级别
function huanxunHideOrShowLevel(courseType){
	if("course_type8" == courseType || "course_type5" == courseType){
	    $('#huanxunCourseLevelTr').hide();
	    $('#huanxunCourseLevel').combobox('setValue','');
		$('#huanxunCourseLevel').combobox('disable');
		$('#huanxunCourseLevel').combobox('disableValidation');
		
		
		$('#huanxunPlatformTr').show();
	    $('#huanxunPlatform').combobox('setValue','');
		$('#huanxunPlatform').combobox('enable');
		$('#huanxunPlatform').combobox('enableValidation');
		
		 $('#huanxunPlatform').combobox('setValue','1');
	} else {
	    $('#huanxunCourseLevelTr').show();
	    $('#huanxunCourseLevel').combobox('setValue','');
		$('#huanxunCourseLevel').combobox('enable');
		$('#huanxunCourseLevel').combobox('enableValidation');

		$('#huanxunPlatformTr').hide();
	    $('#huanxunPlatform').combobox('setValue','');
		$('#huanxunPlatform').combobox('disable');
		$('#huanxunPlatform').combobox('disableValidation');
	}
}
