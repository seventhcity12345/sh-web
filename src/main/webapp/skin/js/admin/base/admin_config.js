gridName = 'dataGrid1';
		
$(function(){
	//设置表单提交回调函数 
    
    $('#myForm2').form({
        success:function(data){
        	data = eval("("+data+")");
            if(data.success){
            	$.messager.alert('提示',data.msg);	
            	
            	$('#dataGrid1').datagrid('reload');
            	$('#keDiv').dialog('close');
            }else{
            	$.messager.alert('提示',data.msg);
            } 
        },onLoadSuccess:function(data){
        	editor1.html($('#config_value').val());
        }
    }); 
    
    
}); 

		  
function myForm2Submit(){
	$('#config_value').val(editor1.html());
	$('#myForm2').submit();
}

function setKe(key_id){
	var row = $('#dataGrid1').datagrid('getSelections');
	if(row.length!=1){
		$.messager.alert('提示','请先选中一条数据！');
		return;
	}
	var key_id = row[0].key_id;
	
	$('#ke_id').val(key_id);
	$('#myForm2').form('load', basePath+'/admin/config/get?key_id='+key_id+"&wtf="+Math.random());
	$('#keDiv').dialog('open');
}