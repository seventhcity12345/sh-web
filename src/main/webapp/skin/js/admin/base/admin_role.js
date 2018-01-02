gridName = 'dataGrid1';
$(function(){
	//设置表单提交回调函数
    $('#myForm').form({
        success:function(data){
        	data = eval("("+data+")");
            if(data.success){
            	$.messager.alert('提示',data.msg);	
            	$('#dataGrid1').datagrid('reload');
            	clearForm();
            	$('#saveDiv').dialog('close');
            }else{
            	$.messager.alert('提示',data.msg);
            } 
        }
    }); 
    
    
    $('#tree').tree({
    	data :[{
			"id":"0",
			"text":"管理系统",
			"state":"closed"
    	}],
        lines:true,
        cascadeCheck:false,
        onBeforeExpand: function(node){
            if(node){
                $('#tree').tree('options').url = basePath + "/admin/resource/tree?key_id=" + node.id;    
            }
        }
    });
    
    _root = $('#tree').tree('getRoot');//展开根节点
    $('#tree').tree('expand',_root.target);
});

//分配资源
function setResources(){ 
	cleanTreeSelected('tree');
	$("#treeSelectAll").removeAttr("checked");
	var row = $('#dataGrid1').datagrid('getSelections');
	if(row.length!=1){
		$.messager.alert('提示','请选中一条数据!');	
		return false;
	}
	$('#setResourceDiv').dialog('open');
	setDefault(row[0].key_id);
}


//设置tree的默认值
function setDefault(roleId){
	$.ajax({          
     type:"POST",   //post提交方式默认是get
     dataType:'json', 
     url:basePath+'/admin/role/findRoleResources', 
     data : {
  	   roleId :roleId
     },
     error:function(data) {      // 设置表单提交出错 
  	   $('#loading-mask').hide();
  	   $.messager.alert('提示','系统出现异常,请联系管理员','error');
     },
     success:function(result) {
  	   $('#loading-mask').hide(); 
  	   
  	   $('#tree').tree('uncheck',_root.target);
  	   
  	   var tempStr = result.msg;//逗号分割id
  	   var tempArray = tempStr.split(",");
  	   for(var i = 0 ; i < tempArray.length ; i ++){
  		   if(tempArray[i]!=null && tempArray[i]!= ""){
  			   var tempObj = $('#tree').tree('find', tempArray[i]);
  			   if(tempObj!=null){
  				   $('#tree').tree('check',tempObj.target);
  			   }
  		   } 
  	   }
     }            
	}); 
	
}

//GRID加载回调函数
function successLoad(){
	// 按钮初期化
	$(".easyui-linkbutton").each(function(){				
		$(this).linkbutton();				
	})
	
	$(".easyui-menubutton").each(function(){		
		$(this).menubutton();		
	}) 
}

//批量删除
function batchDelete(key_id){ 
	if(key_id==null){
		var row = $('#dataGrid1').datagrid('getSelections');
		if(row.length==0){
			$.messager.alert('提示','请选中数据');
			return;
		} 
	}
	
	$.messager.confirm('确认','确认执行此操作？',function(r){
		if (r){
			var ids = "";
			if(key_id!=null){
				ids = key_id;
			} else {
				for(var i = 0 ; i <row.length ; i++){
					if(i==row.length-1){
						ids += row[i].key_id;
					}else{
						ids += row[i].key_id+",";
					}
				}
			} 
		
			$.ajax({          
		       type:"POST",   //post提交方式默认是get
		       dataType:'json', 
		       url:basePath+'/admin/role/delete', 
		       data : {
		    	   ids :ids
		       },
		       error:function(data) {      // 设置表单提交出错 
		    	   $('#loading-mask').hide();
		    	   $.messager.alert('提示','系统出现异常,请联系管理员','error');
		       },
		       success:function(result) {
		    	   $('#loading-mask').hide(); 
		    	   
		    	   if(result.success){
		    		   $('#dataGrid1').datagrid('reload');
		    		   $.messager.alert('提示',result.msg);
		    	   }else{
		    		   $.messager.alert('提示',result.msg);
		    	   }
		       }            
		 	}); 	  
	    }
	 }
	); 
}   
		
//新增or编辑
function edit(operateType,key_id){
	clearForm();
	if(operateType==1){//更新
		if(key_id==null){
			var row = $('#dataGrid1').datagrid('getSelections');
			if(row.length!=1){
				$.messager.alert('提示','请先选中一条数据！');
				return;
			}
			key_id = row[0].key_id;
		}
		
		
		$('#myForm').form('load', basePath+'/admin/role/get?key_id='+key_id+"&wtf="+Math.random());
	} 
	$('#saveDiv').dialog('open');
} 

//清空表单
function clearForm(){
	$('#myForm').form('clear');
}  



//分配资源-确定
function submitSetResource(){
	var row = $('#dataGrid1').datagrid('getSelected');
	
	var nodes = $('#tree').tree('getChecked'); 
	if(nodes.length==0){
		$.messager.alert('提示','请选中数据!');	
		return false;
	}
	var ids = '';
	for(var i=0; i<nodes.length; i++){ 
	    if (ids != '') ids += ',';
	    ids += nodes[i].id;
	} 
	  
	$.ajax({          
     type:"POST",   //post提交方式默认是get
     dataType:'json', 
     url:basePath+'/admin/role/submitRoleResources', 
     data : {
  	   roleId :row.key_id,
  	   ids:ids
     },
     error:function(data) {// 设置表单提交出错 
  	   $('#loading-mask').hide();
  	   $.messager.alert('提示','系统出现异常,请联系管理员','error');
     },
     success:function(result) {
  	   $('#loading-mask').hide(); 
  	   
  	   if(result.success){
  		   $.messager.alert('提示',result.msg);
  		   $('#setResourceDiv').dialog('close');
  	   }else{
  		   $.messager.alert('提示',result.msg);
  	   }
     }            
	}); 
} 