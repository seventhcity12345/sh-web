//格式化输出文件类型
function fileTypeFormat(value, row, index) {
	if (value != '') {
		var temp = value.split('.');
		return temp[temp.length - 1].toUpperCase();
	} else {
		return '';
	}
}

// 添加新课程
function addCourse() {
	// $('#courseform').form('clear');//会导致filebox没法用

	$('#categoryType').combobox('setValue', '');// 课程体系
	$('#courseTitle').textbox('setValue', '');// 课程名称
	$('#courseType').combobox('setValue', '');// 课程类型
	$('#courseLevel').textbox('setValue', '');// 课程级别
	$('#coursePicFile').filebox('setValue', '');
	$('#coursePicFile').filebox('setText', ''); // 课程图片
	$('#courseCoursewareFile').filebox('setValue', '');
	$('#courseCoursewareFile').filebox('setText', ''); // 课程教材
	$('#courseDesc').textbox('setValue', '');// 课程简介

	$('#keyId').val(''); // keyid
	$('#documentId').val(''); // keyid
	$('#coursePic').val('');
	$('#courseCourseware').val('');
	
	$('#courseType').combobox('reload',basePath + '/courseType/findCourseTypeByParam?courseTypeFlag=2');
	$('#courseType').combobox('enable');
	$('#courseType').combobox('validate');
	
	// 2016-04-20
	$('#addOrModifyCourse').panel({
		title : "添加课程"
	});
	$('#addOrModifyCourse').dialog('open');
}

// 修改课程
function modifyCourse() {

	$('#courseType').combobox('reload',basePath + '/courseType/findCourseTypeByParam');// 允许修改1v1课程
	// 2016-04-20
	var row = $('#dataGrid1').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请选中一条数据！');
		return;
	}

	hideOrShowLevel(row[0].courseTypeId);
	
	// form表单初始化
	$('#courseform').form('load', row[0]);
	// 为了触发onChange方法
	$('#categoryType').combobox('setValue', row[0].categoryTypeId);// 课程体系
	$('#courseType').combobox('setValue', row[0].courseTypeId);// 课程类型
	// 为了清空filebox中的value
	$('#coursePicFile').filebox('setValue', '');
	$('#coursePicFile').filebox('setText', row[0].coursePic); // 课程图片
	$('#courseCoursewareFile').filebox('setValue', '');
	$('#courseCoursewareFile').filebox('setText', row[0].courseCourseware); // 课程教材
	$('#courseType').combobox('disable');

	$('#addOrModifyCourse').panel({
		title : "修改课程"
	});
	$('#addOrModifyCourse').dialog('open');
}

// 选择课程级别
function chooseLevel() {
	var categoryTypeValue = $('#categoryType').combobox('getValue');
	var courseTypeValue = $('#courseType').combobox('getValue');
	if ('' == categoryTypeValue) {
		$.messager.alert('提示', '请先选择课程体系！');
		return;
	}
	if ('' == courseTypeValue) {
		$.messager.alert('提示', '请先选择课程类型！');
		return;
	}
	//根据课程体系加载不同数据
	if ('category_type1' == categoryTypeValue) {
		$('#chooseLevelGrid').datagrid({
			data : businessLevelInfo
		});
	} else if ('category_type2' == categoryTypeValue) {
		$('#chooseLevelGrid').datagrid({
			data : generalLevelInfo
		});
	}
	//根据课程类型修改为单选还是多选
	if ("course_type1" == courseTypeValue || "course_type9" == courseTypeValue) {
		$('#chooseLevelGrid').datagrid({
			singleSelect : true,
			ctrlSelect : false
		});
	} else {
		$('#chooseLevelGrid').datagrid({
			singleSelect : false,
			ctrlSelect : false
		});
	}
	$('#chooseLevelDialog').dialog('open');
}
// 删除课程
function deleteCourse() {
	var row = $('#dataGrid1').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请选中一条数据！');
		return;
	}
	$.messager.confirm('确认', '是否确认删除课程？', function(r) {
		if (r) {
			$.ajax({
				type : "POST", // post提交方式默认是get
				dataType : 'json',
				url : basePath + '/admin/course/deleteCourse',
				data : {
					keyId : row[0].keyId,
					courseType : row[0].courseTypeId
				},
				error : function(data) { // 设置表单提交出错
					$('#loading-mask').hide();
					$.messager.alert('提示', '系统出现异常,请联系管理员', 'error');
				},
				success : function(result) {
					$('#loading-mask').hide();
					$('#dataGrid1').datagrid('reload');
					$.messager.alert('提示', result.msg);
				}
			});
		}
	});
}

// level随课程体系变更
function selectLevel() {
	var rows = $('#chooseLevelGrid').datagrid('getSelections');
	var levels = '';
	for (var i = 0; i < rows.length; i++) {
		levels += rows[i].courseLevel + ',';
	}
	if ($('#categoryType').combobox('getValue') == 'course_type1'
			&& rows.length > 1) {
		$.messager.alert('提示', '1v1只能选一个级别');
	}
	if (levels != '') {
		levels = levels.substring(0, levels.length - 1);
	}
	$('#courseLevel').textbox('setValue', levels);
	$('#chooseLevelDialog').dialog('close');
}
// 增加或修改代码
function addOrModifyCourse() {
	var keyId = $('#keyId').val(); // keyid
	var url = basePath;
	if (!isfile($('#coursePicFile').filebox('getText'), 1)) {
		$.messager.alert('提示', "图片类型只能为jpg、gif、bmp、png、jpeg");
		return;
	}
	if (!isfile($('#courseCoursewareFile').filebox('getText'), 2)) {
		$.messager.alert('提示', "课件类型只能为PPT、PPTX、PDF");
		return;
	}

	if ('' == keyId || 'undefined' == keyId || keyId == null) {
		url += "/admin/course/addCourse";
	} else {
		url += "/admin/course/modifyCourse";
	}

	$('#courseform').form(
			'submit',
			{
				url : url,
				onSubmit : function() {
					// 将courseType设为可用 否则 表单不会将其提交
					$('#courseType').combobox('enable');
					// easyui的校验
					if ($('#courseform').form('validate')) {
						$('#addOrModifyCourse').dialog('close');
						return true;
					} else {
						return false;
					}
				},
				success : function(result) {
					var res;
					try {
						res = eval('(' + result + ')');
					} catch (e) {
						$.messager.alert('提示', '系统出现异常,请保证上传文件在5M以内', 'error'
								+ status);
					}

					$('#dataGrid1').datagrid('reload');
					$.messager.alert('提示', res.msg);
				},
				error : function(status) {
					$.messager.alert('提示', '系统出现异常,请联系管理员', 'error' + status);
				}
			});

}

// 监听选择事件不允许删除1v1课程
function deleteSelectListener(index, row) {
	if (row.courseTypeId == 'course_type1' || row.courseTypeId == 'course_type9' || row.courseTypeId == 'course_type4') {
		$('#deleteButton').linkbutton('disable');
	} else {
		$('#deleteButton').linkbutton('enable');
	}
}

// 修改课程类型监听
function courseTypeOnSelectListener(rec){
	hideOrShowLevel(rec.courseType);
}

// 根据课程类型隐藏课程级别
function hideOrShowLevel(courseType){
	if("course_type8" == courseType || "course_type5" == courseType || "course_type4" == courseType){
		$('#courseLevelTr').hide();
		$('#courseLevel').textbox('disable');
		$('#courseLevel').textbox('disableValidation');
		
	} else {
		$('#courseLevelTr').show();
		$('#courseLevel').textbox('enable');
		$('#courseLevel').textbox('enableValidation');
	}
}
