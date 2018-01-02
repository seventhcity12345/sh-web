gridName = 'dataGrid1';
//alert("basePath="+basePath);
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
});
function myFormSubmit(){
	if( $('#fileId').filebox('getValue') != ''){
		if (!isfile($('#fileId').filebox('getValue'), 1)) {
			$.messager.alert('提示', "图片类型只能为jpg、gif、bmp、png、jpeg");
			return;
		}
	}

	var pwd = $('#pwd').val();
	if(pwd != ''){
		$.ajax({
			type : "POST", // post提交方式默认是get
			dataType : 'json',
			url : basePath+'/encodeRegister',
			data : {
				code : $('#pwd').val()
			},
			error : function(data) { // 设置表单提交出错
				alert('网络不稳定，请重试~~~');
			},
			success : function(result) {// 提交成功
				if (result.success) {
					$("#pwd").textbox('setValue',result.msg);
					$('#myForm').submit();
				}else{
					alert("加密失败");
				}
			}});
	} else {
		$('#myForm').submit();
	}
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
		       url:basePath+'/admin/adminUser/delete', 
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

//查看详情
function showDetail(key_id){
	if(key_id!=null){
		
	}else{
		var row = $('#dataGrid1').datagrid('getSelections');
		if(row.length!=1){
			$.messager.alert('提示','请选中一条数据','info');
			return;
		}
		key_id = row[0].key_id;
	}
	
	$('#detailDiv').dialog('open');
	
	$.ajax({
	    type:"POST",   //post提交方式默认是get
	    dataType:'json', 
	    url:basePath+"/admin/adminUser/get", 
	    data : {
	    	key_id :key_id
	    },
	    error:function(data) {// 设置表单提交出错 
	 	   $('#loading-mask').hide();
	 	   $.messager.alert('提示','系统出现异常,请联系管理员','error');
	    },
	    success:function(result) {
		    $('#loading-mask').hide();
	       	//$("#detailDiv").find("div .datagrid-header").eq(1).attr('style','display:none');// 隐藏弹出页GRID 表头
		    $('#detailDiv').find('.datagrid-row').find('td:odd').eq(0).html('<div style=";height:auto;" class="datagrid-cell datagrid-cell-c2-a2">'+ result.admin_user_name +'</div>'); 
			$('#detailDiv').find('.datagrid-row').find('td:odd').eq(1).html('<div style=";height:auto;" class="datagrid-cell datagrid-cell-c2-a2">'+ result.role_name +'</div>');
			$('#detailDiv').find('.datagrid-row').find('td:odd').eq(2).html('<div style=";height:auto;" class="datagrid-cell datagrid-cell-c2-a2">'+ result.account +'</div>');
			$('#detailDiv').find('.datagrid-row').find('td:odd').eq(3).html('<div style=";height:auto;" class="datagrid-cell datagrid-cell-c2-a2">'+ formatDate(result.create_date) +'</div>');
			
	    }
	});
}
		
//新增or编辑
function edit(operateType,key_id){
	clearForm();
	$('#fileId').filebox('setValue', "");
	$('#fileId').filebox('setText', "");
	
	if(operateType==1){//更新
		// 设置编辑窗口名称
		$('#saveDiv').dialog('setTitle','编辑');
		$('#pwd').textbox('disableValidation');
		
		
		if(key_id==null){
			var row = $('#dataGrid1').datagrid('getSelections');
			if(row.length!=1){
				$.messager.alert('提示','请先选中一条数据！');
				return;
			}
			key_id = row[0].key_id;
		}
		
		
		$('#myForm').form('load', basePath+'/admin/adminUser/get?key_id='+key_id+"&wtf="+Math.random());
		
		setTimeout(function() {
			$('#fileId').filebox('setText', $('#admin_user_photo').val());
	    },500);
	} else if (operateType==0){
		// 设置编辑窗口名称
		$('#saveDiv').dialog('setTitle','新增');
		$('#pwd').textbox('enableValidation');
	} 
	
	$('#saveDiv').dialog('open');
} 

//清空表单
function clearForm(){
	$('#account').textbox('setValue', "");
	$('#pwd').textbox('setValue', "");
	$('#admin_user_name').textbox('setValue', "");
	$('#employee_number').textbox('setValue', "");
	$('#telphone').textbox('setValue', "");
	$('#email').textbox('setValue', "");
	$('#weixin').textbox('setValue', "");
	$('#admin_user_photo').val("");
}  



//分配角色
function setRoles(){
	var row = $('#dataGrid1').datagrid('getSelected');
	if(row==null){
		$.messager.alert('提示','请选中数据');
		return;
	}  
	$('#setRoleDiv').dialog('open');
	$('#dataGrid2').datagrid('options').url = basePath+'/admin/role/pagelist';
	$('#dataGrid2').datagrid('reload');
}



function dataGrid2Load(data){
	var row = $('#dataGrid1').datagrid('getSelected');
	if(data){
		$.ajax({          
		     type:"POST",   //post提交方式默认是get
		     dataType:'json', 
		     url:basePath+'/admin/adminUser/findUserRoles', 
		     data : {
		  	   key_id :row.key_id
		     },
		     error:function(data) {      // 设置表单提交出错 
		  	   $('#loading-mask').hide();
		  	   $.messager.alert('提示','系统出现异常,请联系管理员','error');
		     },
		     success:function(result) {
		  	   $('#loading-mask').hide();  
		  	   $('#dataGrid2').datagrid('uncheckAll'); 
		  	   
	  		   $.each(data.rows, function(index, item){
	  			   if(item.key_id == result.data){
	  				   $('#dataGrid2').datagrid('checkRow', index);
	  			   }
	  		   });
		     }            
			});  
	}
} 



//分配角色-确定
function submitSetRoles(){
	var row = $('#dataGrid1').datagrid('getSelected');
	var row2 = $('#dataGrid2').datagrid('getSelections');
	
	if(row2.length==0){
		$.messager.alert('提示','请选中数据!');	
		return false;
	}
	  
  $.ajax({          
   type:"POST",   //post提交方式默认是get
   dataType:'json', 
   url:basePath+'/admin/adminUser/submitUserRoles', 
   data : {
	   key_id :row.key_id,
	   role_id:row2[0].key_id,
	   role_name:row2[0].r_name
   },
   error:function(data) {// 设置表单提交出错 
	   $('#loading-mask').hide();
	   $.messager.alert('提示','系统出现异常,请联系管理员','error');
   },
   success:function(result) {
	   $('#loading-mask').hide(); 
	   
	   if(result.success){
		   $.messager.alert('提示',result.msg);
		   $('#dataGrid1').datagrid('reload');
		   $('#setRoleDiv').dialog('close');
	   }else{
		   $.messager.alert('提示',result.msg);
	   }
   }            
	}); 
	
}