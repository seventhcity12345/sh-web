gridName = 'dataGrid1';

$(function() {
	var currentTime = new Date();
	// 今天0点
	var startTime = currentTime.format("yyyy-MM-dd") + ' 00:00:00';
	// 获取31*3天前的时间
	var twoDaysLater = new Date(currentTime.getTime() + 2 * 24 * 60 * 60 * 1000);
	var endTime = twoDaysLater.format("yyyy-MM-dd") + ' 00:00:00';

	$('#startTime').datetimebox('setValue', startTime);
	$('#endTime').datetimebox('setValue', endTime);
	reloadDataGrid();
});

// 加载数据
function reloadDataGrid() {
	var startTime = $('#startTime').datetimebox('getValue');
	var endTime = $('#endTime').datetimebox('getValue');
	$('#dataGrid1')
			.datagrid(
					{
						url : basePath
								+ '/admin/subscribeCourse/findSubscribeCourseInfoPage?startTime='
								+ startTime + '&endTime=' + endTime
					});
}

//弹出弹窗事件
function openDialog(){
	$("#exportTuanxunExelDialog").dialog("open");
}
//关闭弹窗事件
function closeDialog(){
	$("#exportTuanxunExelDialog").dialog("close");
}
//导出事件
function exportTuanxunData(){
        var form = document.forms[0];
        if($("#exportTuanxunExelFrom").form('validate')){
        	form.submit();  
        }
}
// 双击事件
function doubleClickListener(rowIndex, rowData) {
	rowData.user_id = rowData.userId;
	lookUserDetail(rowIndex, rowData);
}

// 加红加粗
function addRedBoldForNo(value, row, index) {
	if (!value) {
		return 'font-weight:bold;color:red;';
	}
}

// 加红加粗
function addRedBold(value, row, index) {
	return 'font-weight:bold;color:red;';
}

// 切换学员出席状态
function changeStudentShowStatus() {
	var row = $('#dataGrid1').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请选中一条数据！');
		return;
	}
	$.messager.confirm('确认', '确认修改"学员Show"的状态吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST", // post提交方式默认是get
				dataType : 'json',
				url : basePath
						+ '/admin/subscribeCourse/changeStudentShowStatus',
				data : {
					subscribeId : row[0].keyId,
					oldStatus : row[0].subscribeStatus
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

// 打开添加修改note的表单
function openSubscribeNoteDialog() {
	var row = $('#dataGrid1').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请选中一条数据！');
		return;
	}
	// form表单初始化
	$('#addOrModifyNoteDialog').form('load', row[0]);

	$('#addOrModifyNoteDialog').dialog('open');
}

// 添加修改note
function addOrModifySubscribeNote() {
	var url = basePath + "/subscribeCourseNote/addOrModifyNote";

	$('#addOrModifyNoteFrom').form('submit', {
		url : url,
		onSubmit : function() {
			// easyui的校验
			if ($('#addOrModifyNoteFrom').form('validate')) {
				$('#addOrModifyNoteDialog').dialog('close');
				return true;
			} else {
				return false;
			}
		},
		success : function(result) {
			var res;
			res = eval('(' + result + ')');
			$('#dataGrid1').datagrid('reload');
			$.messager.alert('提示', res.msg);
		},
		error : function(status) {
			$.messager.alert('提示', '系统出现异常,请联系管理员', 'error' + status);
		}
	});

}

// 后台进入教室 flag 为true表示直接进入教室，false表示弹框
function gotoClassByAdmin(flag) {

	var row = $('#dataGrid1').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请选中一条数据！');
		return;
	}

	$.ajax({
		type : "post", // post提交方式默认是get
		dataType : 'json',
		async : false,
		timeout : 8000, // 超时时间8秒
		url : basePath + '/admin/subscribeCourse/goToClassByAdminUser/'
				+ row[0].teacherTimeId,
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus == "timeout") {
				$.messager.alert('提示',
						'timeout, page will automatically refresh!');
			} else {
				$.messager.alert('提示',
						'system error, please contract the administrator!');
				$.messager.alert('提示', textStatus);
			}
		},
		success : function(result) {// 提交成功
			if (result.success) {
				if(flag){
					OpenWindow(result.msg);
				} else {
					showUrl(result.msg);
				}
			} else {
				$.messager.alert('提示', result.msg);
			}
		}
	});
}

//展示url
function showUrl(msg){
	$('#urlShowInput').textbox('setValue',msg);
	$('#showUrlDiv').dialog('open');
}

/*******************************************************************************
 * common func -- OpenWindow() /* // ajax: async: false /
 ******************************************************************************/
function OpenWindow(url) {
	var target = '_blank';
	var a = document.createElement("a");
	a.setAttribute("href", url);
	if (target === null || target === '') {
		target = '_blank';
	}
	a.setAttribute("target", target);
	document.body.appendChild(a);
	if (a.click) {
		a.click();
	} else {
		try {
			var evt = document.createEvent('Event');
			a.initEvent('click', true, true);
			a.dispatchEvent(evt);
		} catch (e) {
			window.open(url);
		}
	}
	document.body.removeChild(a);
}

// 生成学生url
function displayStudentUrl() {

	var row = $('#dataGrid1').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请选中一条数据！');
		return;
	}

//	$.messager.alert('URL', row[0].inviteUrl);
//
//	document.execCommand("copy", false, null);
	showUrl(row[0].inviteUrl);
	
}

// 获取当天的课程数去重
function findCourseCount() {
	$.ajax({
		type : "post", // post提交方式默认是get
		dataType : 'json',
		async : false,
		timeout : 8000, // 超时时间8秒
		url : basePath + '/admin/subscribeCourse/findCourseCount',
		success : function(result) {// 提交成功
			$('#courseCountDiv').text(result);
		}
	});
}

// 格式化记录类型
function formatSubscribeNoteType(type) {
	var noteTypeStr = "";
	switch (type) {
	case 1:
		noteTypeStr = "NS-学员失联";
		break;
	case 2:
		noteTypeStr = "NS-学员个人原因";
		break;
	case 3:
		noteTypeStr = "NS-学员设备故障";
		break;
	case 4:
		noteTypeStr = "NS-老师设备故障";
		break;
	case 5:
		noteTypeStr = "NS-老师未出席";
		break;
	case 6:
		noteTypeStr = "老师迟到";
		break;
	case 7:
		noteTypeStr = "平台故障";
		break;
	case 8:
		noteTypeStr = "首课";
		break;
	}
	return noteTypeStr;
}


// 进入rsa
function gotoRsaByAdmin() {
	var phone = $('#rsaPhone').textbox('getValue');
	if(phone == null){
		$.messager.alert('提示',
		'请输入手机号!');
		return;
	}

	$.ajax({
		type : "post", // post提交方式默认是get
		async : false,
		timeout : 8000, // 超时时间8秒
		url : basePath + '/admin/tellmemore/gotoRsa',
		data : {
			phone : phone
		},
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus == "timeout") {
				$.messager.alert('提示',
						'超时请重试!');
			} else {
				$.messager.alert('提示',
						'系统错误请联系管理员!');
			}
		},
		success : function(result) {// 提交成功
			if (result.success) {
				OpenWindow(result.msg);
			} else {
				$.messager.alert('提示', result.msg);
			}
		}
	});
}
