gridName = 'dataGrid1';
		
$(function(){
	//设置表单提交回调函数 
    $('#noticeForm').form({
        success:function(data){
        	data = eval("("+data+")");
            if(data.code == 200){
            	$.messager.alert('提示',"编辑成功");	
            	
            	$('#dataGrid1').datagrid('reload');
            	$('#noticeDiv').dialog('close');
            }else{
            	$.messager.alert('提示',"编辑失败");
            } 
        },onLoadSuccess:function(data){
        	editor1.html($('#noticeContent').val());
        }
    }); 
}); 

//格式化公告类型
function formatNoticeType(type){
	var result = "";
	if(type == 0){
		result =  "公告";
	}
	else if(type == 1){
		result = "banner";
	}
	return result;
}

//格式化公告内容
function formatNoticeContent(content){
	var result = "";
	var noticeContent = content.substr(0, 7);
	if(noticeContent == "banner:"){
		result =  "无";
	}
	else{
		result = content;
	}
	return result;
}

//新增公告
function addNotice(){
	
	$("#keyId").val("");
	
	$('#noticeDiv').dialog({
		title : "新增banner&公告"
	});
	
	$("#noticeTitle").textbox('setValue', "");
	$("#noticeStartTime").datebox('setValue', "");
	$("#noticeEndTime").datebox('setValue', "");
	document.getElementById('noticeTypeNotice').checked=true;
	editor1.html("");
	
	$('#noticeDiv').dialog('open');
}

//编辑公告
function updateNotice(){

	var row = $('#dataGrid1').datagrid('getSelections');
	if(row.length!=1){
		$.messager.alert('提示','请先选中一条数据！');
		return;
	}
	
	$("#keyId").val("");
	
	$('#noticeDiv').dialog({
		title : "编辑banner&公告"
	});

	$("#keyId").val(row[0].keyId);
	$("#noticeTitle").textbox('setValue', row[0].noticeTitle);
	$("#noticeStartTime").datebox('setValue', formatDateDay(row[0].noticeStartTime));
	$("#noticeEndTime").datebox('setValue',  formatDateDay(row[0].noticeEndTime));
	var noticeContent = row[0].noticeContent;
	if(row[0].noticeType == 0){
		document.getElementById('noticeTypeNotice').checked=true;
		editor1.html(noticeContent);
	}
	else{
		document.getElementById('noticeTypeBanner').checked=true;
		editor1.html(noticeContent.substr(7, noticeContent.length - 7));
	}
	
	$('#noticeDiv').dialog('open');
}

		  
function updateNoticeSubmit(){

	if(editor1.text().length <= 0){
		$.messager.alert('提示', "公告内容不能为空");
		return;
	}
	
	if(editor1.text().length > 200){
		$.messager.alert('提示', "公告内容不能超过200字,现有"+editor1.text().length+"字");
		return;
	}
	
	if(editor1.html().length > 500){
		$.messager.alert('提示', "请检查是否超链接地址过长！");
		return;
	}
	
	if(new Date($("#noticeStartTime").datebox("getValue")).getTime() > new Date($("#noticeEndTime").datebox("getValue")).getTime()){
		$.messager.alert('提示', "生效开始时间必须小于生效结束时间！");
		return;
	}
	
	
	$('#noticeContent').val(editor1.html());
	$('#noticeForm').submit();
}

//批量删除数据
function deleteNotice(){
    var row = $('#dataGrid1').datagrid('getSelections');
    if(row.length < 1){
        $.messager.alert('提示','请先选中至少一条数据！');
        return;
    }
    $.messager.confirm("确认", "确认要删除这 " + row.length + " 条数据吗？", function(r){
        if(r){
            //key的字符串集合
            var keys = "";
            for (var i = 0; i < row.length; i++) {
                if (i == row.length - 1) {
                    keys = keys + row[i].keyId;
                    break;
                }
                keys = keys + row[i].keyId + ",";
            }

            $.ajax({
                type : "POST", //post提交方式默认是get
                dataType : 'json',
                url : basePath + '/admin/notice/deleteNotice',
                data : {
                    keys : keys
                },
                error : function(data) { // 设置表单提交出错 
                    $('#loading-mask').hide();
                    $.messager.alert('提示', '系统出现异常,请联系管理员', 'error');
                },
                success : function(result) {
                    $('#loading-mask').hide();

                    if(result.code == 200){
                    	$.messager.alert('提示',"成功删除"+result.data+"条数据");	
                    	$('#dataGrid1').datagrid('reload');
                    }else{
                    	$.messager.alert('提示',"删除失败");
                    } 
                }
            });
        }
    });
}