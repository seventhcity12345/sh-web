
//提交变更级别
function submitEditLevel(){
	var row = $('#dataGrid1').datagrid('getSelections');
	if(row.length!=1){
		$.messager.alert('提示','请先选中一条数据！');
		return;
	}
	
	$.ajax({
	       type:"POST",   //post提交方式默认是get
	       dataType:'json', 
	       url:basePath+'/admin/user/saveCurrentLevel', 
	       data : {
	    	   key_id :row[0].user_id,
	    	   phone:row[0].phone,
	    	   current_level:$('#edit_current_level').combobox('getValue'),
	       },
	       error:function(data) {      // 设置表单提交出错 
	    	   $.messager.alert('提示','系统出现异常,请联系管理员','error');
	       },
	       success:function(result) {
	    	   if(result.success){
	    		   $('#editCurrentLevelDiv').dialog('close');
	    		   $('#dataGrid1').datagrid('reload');
	    		   $.messager.alert('提示',result.msg);
	    	   }else{
	    		   $.messager.alert('提示',result.msg);
	    	   }
	       }            
	 	});
}

//修改级别
function editUserLevel(){
	var row = $('#dataGrid1').datagrid('getSelections');
	if(row.length!=1){
		$.messager.alert('提示','请先选中一条数据！');
		return;
	}
	var current_level = row[0].current_level;
	if(current_level==null|| current_level == "" || current_level == "null"){
		$.messager.alert('提示','当前用户还没有级别！');
		return false;
	}
	
	if(current_level.indexOf("General")!=-1){//通用英语
		$('#edit_current_level').combobox({
		    data:[{
		         label:'General Level 1',
		         value:'General Level 1'
		     },{
		    	 label:'General Level 2',
		         value:'General Level 2'
		     },{
		    	 label:'General Level 3',
		         value:'General Level 3'
		     },{
		    	 label:'General Level 4',
		         value:'General Level 4'
		     },{
		    	 label:'General Level 5',
		         value:'General Level 5'
		     },{
		    	 label:'General Level 6',
		         value:'General Level 6'
		     },{
		    	 label:'General Level 7',
		         value:'General Level 7'
		     },{
		    	 label:'General Level 8',
		         value:'General Level 8'
		     },{
		    	 label:'General Level 9',
		         value:'General Level 9'
		     },{
		    	 label:'General Level 10',
		         value:'General Level 10'
		     },{
		    	 label:'General Level 11',
		         value:'General Level 11'
		     },{
		    	 label:'General Level 12',
		         value:'General Level 12'
		     },{
		    	 label:'General Level 13',
		         value:'General Level 13'
		     },{
		    	 label:'General Level 14',
		         value:'General Level 14'
		     },{
		    	 label:'General Level 15',
		         value:'General Level 15'
		     },{
		    	 label:'General Level 16',
		         value:'General Level 16'
		     },{
		    	 label:'General Level 17',
		         value:'General Level 17'
		     },{
		    	 label:'General Level 18',
		         value:'General Level 18'
		     }],
		    valueField:'label',
		    textField:'value'
		});
		
		$('#edit_current_level').combobox('select', current_level);
	}else{//商务英语
		$('#edit_current_level').combobox({
		    data:[{
		         label:'Business Level 1',
		         value:'Business Level 1'
		     },{
		    	 label:'Business Level 2',
		         value:'Business Level 2'
		     },{
		    	 label:'Business Level 3',
		         value:'Business Level 3'
		     },{
		    	 label:'Business Level 4',
		         value:'Business Level 4'
		     },{
		    	 label:'Business Level 5',
		         value:'Business Level 5'
		     },{
		    	 label:'Business Level 6',
		         value:'Business Level 6'
		     },{
		    	 label:'Business Level 7',
		         value:'Business Level 7'
		     },{
		    	 label:'Business Level 8',
		         value:'Business Level 8'
		     },{
		    	 label:'Business Level 9',
		         value:'Business Level 9'
		     },{
		    	 label:'Business Level 10',
		         value:'Business Level 10'
		     },{
		    	 label:'Business Level 11',
		         value:'Business Level 11'
		     },{
		    	 label:'Business Level 12',
		         value:'Business Level 12'
		     }],
		    valueField:'label',
		    textField:'value'
		});
		
		$('#edit_current_level').combobox('select', current_level);
	}
	
	$('#editCurrentLevelDiv').dialog('open');
	
}