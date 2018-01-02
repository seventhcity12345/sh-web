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
								+ '/admin/subscribeCourse/findDemoSubscribeCourseInfoPage?startTime='
								+ startTime + '&endTime=' + endTime
					});
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

function formatUrlAndMeeting(value,row,index){
	return "域名:" + webexUrlFormater(row.webexRequestUrl) + "<br/>" + "会议号:" + row.webexMeetingKey;
}

// 截取webex url
function webexUrlFormater(url){
	if(url !== null && url !== undefined && url !== ''){
		var urlArr = url.split("/");
		if(urlArr.length >= 3){
			return urlArr[0] + '//' +urlArr[1] + urlArr[2];
		}
	}
}

//截取如果金额为0 显示空
function transactionAmountFormater(amount){
	if(amount == 0){
		return '';
	} else {
		return amount;
	}
}

//截取如果金额为0 显示空
function haveReportFormater(value,row,index){
	if(row.subscribeStatus){
		return '有';
	} else {
		return '';
	}
}



// 打开demo课报告页
function openReportUrl(){
	var row = $('#dataGrid1').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请选中一条数据！');
		return;
	}
	if (!row[0].subscribeStatus) {
		$.messager.alert('提示', '此记录没有报告!');
		return;
	}
	OpenWindow(row[0].reportUrl);
}


// 打开demo课评论页
function openCommentUrl(){
	var row = $('#dataGrid1').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请选中一条数据！');
		return;
	}
	OpenWindow(row[0].commentUrl);
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


