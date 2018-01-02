//查看学员详情
function lookUserDetail(rowIndex, rowData){
	var row = $('#dataGrid1').datagrid('getSelections');
	if(row.length!=1){
		$.messager.alert('提示','请选中一条数据！');
		return;
	}
	
	refreshUserDetail(rowData.user_id);
}

//刷新学员详情
function refreshUserDetail(user_id){
	$('#lookUserDetailDiv').dialog({ 
        closed: false,
        cache: false,
        href: basePath+'/admin/user/lookUserDetail/'+ user_id,
        modal: true
    });
}

//合同延期
function contractExtension(){
	$('#extensionLimitShowTime').textbox('setValue',"");
	$('#extensionLimitShowTimeUnit').combobox('setValue',"")
	
	var row = $('#orderCourseInfo').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请先选中一条数据！');
		return;
	}
	
	$('#contractExtensionDiv').dialog('open');	
}

//确认提交合同延期
function submitContractExtension(){
	var row = $('#orderCourseInfo').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请先选中一条数据！');
		return;
	}
	
	//合同id
	var orderId = row[0].key_id;
	
	$('#contractExtensionDiv').dialog('close');
	
	$.ajax({
		type : "POST",
		dataType : 'json',
		timeout : 8000,
		url : basePath+"/admin/orderCourse/updateContractExtension",
		data : {
			keyId : orderId,
			limitShowTime : $('#extensionLimitShowTime').textbox('getValue'),
			limitShowTimeUnit : $('#extensionLimitShowTimeUnit').combobox('getValue'),
		},
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus == "timeout") {
				$.messager.alert('提示',"请求超时，请重新查看！");
			} else {
				$.messager.alert('提示','系统出现异常,请联系管理员!'+textStatus);
			}
		},
		success : function(result) {//提交成功
			if (result.code==200) {
				$('#orderCourseInfo').datagrid('reload');
				$.messager.alert('提示',"延期操作成功！");
			} else {
				$.messager.alert('提示',result.msg);
			}
		}
	});
	
}

//合同延期选中时效单位触发
function selectLimitShowTimeUnit(recNew,recOld){
	//单选按月0/按天1，输入框按月校验1-6，按天校验1-15
	if(recNew == 0){
		$('#extensionLimitShowTime').textbox({
			validType : 'range[1,6]'
		})
	}
	else{
		$('#extensionLimitShowTime').textbox({
			validType : 'range[1,15]'
		})
	}
	
}


