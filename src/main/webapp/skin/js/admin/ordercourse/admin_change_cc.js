// 打开更换cc窗口
function openChangeCcDialog(){
	var rows = $("#dataGrid1").datagrid('getSelections');
	if (rows.length < 1) {
		$.messager.alert('提示', '请至少选中一条数据！');
		return;
	}
	$('#changeCcId').combobox('clear');// 课程体系
	$('#changeCcDialog').dialog('open');
}



//更换CC
function submitChangeCc() {
	
	var row = $('#dataGrid1').datagrid('getSelections');
	if (row.length < 1) {
		$.messager.alert('提示', '请至少选中一条数据！');
		return;
	}
	var changeCcId = $('#changeCcId').combobox('getValue');
	
	if(changeCcId == null || changeCcId == ''){
		$.messager.alert('提示', '请选择CC！');
		return;
	}
	
	//orderId的字符串集合
	var orderIds = "";
	for (var i = 0; i < row.length; i++) {
		if (i == row.length - 1) {
			orderIds = orderIds + row[i].key_id;
			break;
		}
		orderIds = orderIds + row[i].key_id + ",";
	}
	
	$.ajax({
		type : "POST", // post提交方式默认是get
		dataType : 'json',
		url : basePath + '/admin/orderCourse/changeCc',
		data : {
			orderId : orderIds,
			ccId : changeCcId
		},
		error : function(data) { // 设置表单提交出错
			$.messager.alert('提示', '系统出现异常,请联系管理员', 'error');
		},
		success : function(result) {
			$('#changeCcDialog').dialog('close');
			$('#dataGrid1').datagrid('reload');
			if(result.code == 200){
				$.messager.alert('提示', '操作成功');
			} else {
				$.messager.alert('提示', result.msg);
			}
		}
	});
}