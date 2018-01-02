//新增老师对话框
function addNewTeacher() {

	$("#add_account").textbox('setValue', "");
	$("#add_teacher_name").textbox('setValue', "");
	$("#add_teacher_contact_content").textbox('setValue', "");
	$("#add_teacher_photo").textbox('setValue', "");
	$("#addTeacherDesc").textbox('setValue', "");

	// 老师可教课程下来选择框（可做复选框）
	$('#add_teacher_course_type').combobox(
			{
				multiple : true,

				formatter : function(row) {
					var opts = $(this).combobox('options');
					return '<input type="checkbox" class="combobox-checkbox">'
							+ row[opts.textField]
				},
				onLoadSuccess : function() {
					var opts = $(this).combobox('options');
					var target = this;
					var values = $(target).combobox('getValues');
					$.map(values, function(value) {
						var el = opts.finder.getEl(target, value);
						el.find('input.combobox-checkbox')._propAttr('checked',
								true);
					})
				},
				onSelect : function(row) {
					// console.log(row);
					var opts = $(this).combobox('options');
					var el = opts.finder.getEl(this, row[opts.valueField]);
					el.find('input.combobox-checkbox')._propAttr('checked',
							true);
				},
				onUnselect : function(row) {
					var opts = $(this).combobox('options');
					var el = opts.finder.getEl(this, row[opts.valueField]);
					el.find('input.combobox-checkbox')._propAttr('checked',
							false);
				}
			});

	// $('#add_teacher_course_type').combobox('select', 'course_type1');

	// 老师来源下拉选择框
	$('#add_third_from').combobox({
		url : basePath + '/admin/config/findTeacherThirdFrom',
		valueField : 'config_value',
		textField : 'config_name'
	});

	// 国籍下拉框
	$('#add_teacher_nationality').combobox({
		data : countryInfo,
		valueField : 'value',
		textField : 'text'
	});

	// 性别下拉框
	$('#add_teacher_gender').combobox({
		data : genderInfo,
		valueField : 'value',
		textField : 'text'
	});

	// 工作性质下拉框
	$('#add_teacher_job_type').combobox({
		data : jobTypeInfo,
		valueField : 'value',
		textField : 'text'
	});

	$('#fileId1').filebox('setValue', "");

	// 打开新增老师信息浮层
	$('#addTeacherDiv').dialog('open');
}

// 提交新增老师信息效验
function submitAddTeacherDiv() {

	if (!isfile($('#fileId1').filebox('getValue'), 1)) {
		$.messager.alert('提示', "图片类型只能为jpg、gif、bmp、png、jpeg");
		return;
	}

	$('#addTeacherForm').form('submit', {
		onSubmit : function() {
			// easyui的校验
			if ($(this).form('enableValidation').form('validate')) {
				// $('#addTeacherDiv').dialog('close');
				return true;
			} else {
				return false;
			}
		},
		success : function(result) {
			result = eval("(" + result + ")");
			if (result.success) {
				$('#dataGrid').datagrid('reload');
				$('#addTeacherDiv').dialog('close');
				$.messager.alert('提示', result.msg);
			} else {
				$.messager.alert('提示', result.msg);
			}
		},
		error : function(status) {
			$.messager.alert('提示', '系统出现异常,请联系管理员', 'error' + status);
		}
	});
}

// 编辑老师信息
function editTeacher() {
	var row = $('#dataGrid').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请先选中一条数据！');
		return;
	}

	var key_id = row[0].keyId;
	var teacher_name = row[0].teacherName;
	// var account = row[0].account;
	// var pwd = row[0].pwd;
	var teacher_gender = row[0].teacherGender;
	var teacher_course_type = row[0].teacherCourseTypeId;
	var third_from = row[0].thirdFrom;
	var teacher_nationality = row[0].teacherNationality;
	var teacher_contact_content = row[0].teacherContactContent;
	var teacher_job_type = row[0].teacherJobType;
	var teacher_photo = row[0].teacherPhoto;
	var teacherDesc = row[0].teacherDesc;
	var isBindWechat = formatYesOrNo(row[0].isBindWechat);

	// alert("key_id" + key_id + ", teacher_name=" + teacher_name);

	// 赋值
	$('#edit_key_id').val(key_id);
	$("#edit_teacher_name").textbox('setValue', teacher_name);
	$("#edit_teacher_contact_content").textbox('setValue',
			teacher_contact_content);
	$('#edit_teacher_photo').val(teacher_photo);
	$("#editTeacherDesc").textbox('setValue', teacherDesc);
	$("#editTeacherIsBind").textbox('setValue', isBindWechat);

	// 老师可教课程下来选择框（可做复选框）
	$('#edit_teacher_course_type').combobox(
			{
				multiple : true,

				formatter : function(row) {
					var opts = $(this).combobox('options');
					return '<input type="checkbox" class="combobox-checkbox">'
							+ row[opts.textField]
				},
				onLoadSuccess : function() {
					var opts = $(this).combobox('options');
					var target = this;
					var values = $(target).combobox('getValues');
					$.map(values, function(value) {
						var el = opts.finder.getEl(target, value);
						el.find('input.combobox-checkbox')._propAttr('checked',
								true);
					})
				},
				onSelect : function(row) {
					// console.log(row);
					var opts = $(this).combobox('options');
					var el = opts.finder.getEl(this, row[opts.valueField]);
					el.find('input.combobox-checkbox')._propAttr('checked',
							true);
				},
				onUnselect : function(row) {
					var opts = $(this).combobox('options');
					var el = opts.finder.getEl(this, row[opts.valueField]);
					el.find('input.combobox-checkbox')._propAttr('checked',
							false);
				}
			});

	var teacher_course_type_result = teacher_course_type.split(",");
	for (var i = 0; i < teacher_course_type_result.length; i++) {
		if ("" != teacher_course_type_result[i]) {
			$('#edit_teacher_course_type').combobox('select',
					teacher_course_type_result[i]);
		}
	}

	// 来源下拉框
	$('#edit_third_from').combobox({
		url : basePath + '/admin/config/findTeacherThirdFrom',
		valueField : 'config_value',
		textField : 'config_name'
	});
	$('#edit_third_from').combobox('select', third_from);

	// 性别下拉框
	$('#edit_teacher_gender').combobox({
		data : genderInfo,
		valueField : 'value',
		textField : 'text'
	});
	$('#edit_teacher_gender').combobox('select', teacher_gender);

	// 国籍下拉框
	$('#edit_teacher_nationality').combobox({
		data : countryInfo,
		valueField : 'value',
		textField : 'text'
	});
	$('#edit_teacher_nationality').combobox('select', teacher_nationality);

	// 工作性质下拉框
	$('#edit_teacher_job_type').combobox({
		data : jobTypeInfo,
		valueField : 'value',
		textField : 'text'
	});

	$('#edit_teacher_job_type').combobox('select', teacher_job_type);

	$('#fileId2').filebox('setValue', "");
	$('#fileId2').filebox('setText', teacher_photo);

	$('#editTeacherDiv').dialog('open');
}

// 效验修改的老师信息
function submitEditTeacherDiv() {

	if (!isfile($('#fileId2').filebox('getValue'), 1)) {
		$.messager.alert('提示', "图片类型只能为jpg、gif、bmp、png、jpeg");
		return;
	}

	$('#editTeacherForm').form('submit', {
		onSubmit : function() {
			// easyui的校验
			if ($(this).form('enableValidation').form('validate')) {
				$('#editTeacherDiv').dialog('close');
				return true;
			} else {
				return false;
			}
		},
		success : function(result) {
			result = eval("(" + result + ")");
			if (result.success) {
				$('#dataGrid').datagrid('reload');
				$.messager.alert('提示', result.msg);
			} else {
				$.messager.alert('提示', result.msg);
			}
		},
		error : function(status) {
			$.messager.alert('提示', '系统出现异常,请联系管理员', 'error' + status);
		}
	});
}

// 批量删除数据
function deleteTeacher() {
	var row = $('#dataGrid').datagrid('getSelections');
	if (row.length < 1) {
		$.messager.alert('提示', '请先选中至少一条数据！');
		return;
	}
	$.messager.confirm("确认", "确认要删除这 " + row.length + " 条数据吗？", function(r) {
		if (r) {
			// key的字符串集合
			var keys = "";
			for (var i = 0; i < row.length; i++) {
				if (i == row.length - 1) {
					keys = keys + row[i].keyId;
					break;
				}
				keys = keys + row[i].keyId + ",";
			}

			$.ajax({
				type : "POST", // post提交方式默认是get
				dataType : 'json',
				url : basePath + '/admin/teacher/deleteTeacher',
				data : {
					keys : keys
				},
				error : function(data) { // 设置表单提交出错
					$('#loading-mask').hide();
					$.messager.alert('提示', '系统出现异常,请联系管理员', 'error');
				},
				success : function(result) {
					$('#loading-mask').hide();

					if (result.success) {
						$('#dataGrid').datagrid('reload');
						$.messager.alert('提示', result.msg);
					} else {
						$.messager.alert('提示', result.msg);
					}
				}
			});
		}
	});
}

// 查看老师详细信息
function lookTeacherDetail(rowIndex, rowData) {

	// alert('Teacher');
	/*
	 * var row = $('#dataGrid').datagrid('getSelections'); if (row.length != 1) {
	 * $.messager.alert('提示', '请选中一条数据！'); return; }
	 * $('#lookTeacherDetailDiv').dialog({ //href : basePath +
	 * '/admin/user/lookUserDetail/' + rowData.user_id, modal : true });
	 * 
	 * $('#lookTeacherDetailDiv').dialog('open');
	 */
}

// 打开微信绑定链接地址弹窗
function generateWechatUrl() {
	$.ajax({
		type : "GET", // post提交方式默认是get
		dataType : 'json',
		url : basePath + '/admin/teacher/findTeacherWechatBindUrl',
		error : function(data) { // 设置表单提交出错
			$('#loading-mask').hide();
			$.messager.alert('提示', '系统出现异常,请联系管理员', 'error');
		},
		success : function(result) {
			$('#loading-mask').hide();

			if (result.code == 200) {
				$('#wechatBindUrl').html('绑定链接：'+result.data);
				$('#wechatBindDiv').dialog('open');
			} else {
				$.messager.alert('提示', errorCode[result.code]);
			}
		}
	});

}

// 工作性质格式化
function formatterJobType(value) {
	var result = "";
	if (value == 1) {
		result = "全职";
	} else if (value == 0) {
		result = "兼职";
	} else if (value == 2) {
		result = "买断";
	} else if (value == 3) {
		result = "实消";
	}
	return result;
}

//批量删除数据
function adminLogin() {
	var row = $('#dataGrid').datagrid('getSelections');
	if (row.length < 1) {
		$.messager.alert('提示', '请先选中至少一条数据！');
		return;
	}
	$.ajax({
		type : "POST", // post提交方式默认是get
		dataType : 'json',
		url : basePath + '/admin/teacher/login',
		data : {
			teacherId : row[0].keyId
		},
		error : function(data) { // 设置表单提交出错
			$.messager.alert('提示', '系统出现异常,请联系管理员', 'error');
		},
		success : function(result) {
			if (result.code == 200) {
				window.open(result.data,"blank");
			} else {
				$.messager.alert('提示', result.msg);
			}
		}
	});
}

