var addPname;
var addPid;
var _root; 
$(function(){ 
	//设置表单提交回调函数
    $('#myForm').form({
        success:function(data){
        	data = eval("("+data+")");
            if(data.success){
            	$.messager.alert('提示',data.msg);	
            	clearForm();
            	reloadTree();
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
        onClick:function(node){ 
            addPname = node.text;
            addPid = node.id;
            
            if(node.id=="0"){//根节点
            	$('#pname').val(addPname);
                $('#pid').val(addPid);
            }else{//叶子节点
            	var pname = node.pname;
            	var pid = node.pid;
            	
                if(pname==null||pname==""){
                	pname = "管理系统";
                }
            	$('#pname').val(pname);
                $('#pid').val(pid);
                $('#myForm').form('load', basePath+'/admin/resource/get?key_id='+node.id+"&wtf="+Math.random());
            } 
        },
        onBeforeExpand: function(node){
            if(node){
                $('#tree').tree('options').url = basePath + "/admin/resource/tree?key_id=" + node.id;    
            }
        },
        onExpand : function (node) {
            //common_area_id = node.id;
        },onBeforeDrop : function (targetNode, source, point){//监听拖拽事件
        	var target = $(this).tree('getNode', targetNode); 
        	if(point=="append"){
        		$.messager.alert('提示',"无法拖拽");
        		return false;
        	}
        	
        	if(source.pid!=target.pid){
        		$.messager.alert('提示',"无法拖拽");
        		return false;
        	}
        	
        	
        	$.ajax({          
 		       type:"POST",   //post提交方式默认是get
 		       dataType:'json', 
 		       url:basePath+'/admin/resource/sort', 
 		       data : {
 		    	   source :source.sort,
 		    	   target :target.sort,
 		    	   point :point,
 		    	   pid :source.pid,
 		    	   key_id :source.key_id
 		       },
 		       error:function(data) {      // 设置表单提交出错 
 		    	   $('#loading-mask').hide();
 		    	   $.messager.alert('提示','系统出现异常,请联系管理员','error');
 		       },
 		       success:function(result) {
 		    	   $('#loading-mask').hide(); 
 		    	   
 		    	   if(result.success){
 		    		   $.messager.alert('提示',result.msg);
 		    		   clearForm();
 		    		   reloadTree();
 		    	   }else{
 		    		   $.messager.alert('提示',result.msg);
 		    	   }
 		       }            
 		 	});
        },onLoadSuccess : function (node, data){
        	if(node!=null){
        		$('#tree').tree('expandAll',node.target);
        	}
        }
    });
    
    //展开根节点
    _root = $('#tree').tree('getRoot');
    $('#tree').tree('expand',_root.target); 
});

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
	var node = $('#tree').tree('getSelected');
	
	if(node==null){
		$.messager.alert('提示','请选中数据');
		return;
	}
	
	$.messager.confirm('确认','确认执行此操作？',function(r){
		if (r){ 
			$.ajax({          
		       type:"POST",   //post提交方式默认是get
		       dataType:'json', 
		       url:basePath+'/admin/resource/delete', 
		       data : {
		    	   ids :node.id
		       },
		       error:function(data) {      // 设置表单提交出错 
		    	   $('#loading-mask').hide();
		    	   $.messager.alert('提示','系统出现异常,请联系管理员','error');
		       },
		       success:function(result) {
		    	   $('#loading-mask').hide(); 
		    	   
		    	   if(result.success){
		    		   $.messager.alert('提示',result.msg);
		    		   clearForm();
		    		   reloadTree(1);
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
function edit(){
	clearForm();
	if(addPname!=null&&addPname!=""){
		$('#pname').val(addPname);
		$('#pid').val(addPid);
	}else{
		$('#pname').val("管理系统");
		$('#pid').val(0);
	} 
} 

//清空表单
function clearForm(){
	$('#myForm').form('clear');
	$('#type').combobox('select','0'); 
}

function submitForm(){
	if($('#pname').val()==""){
		$.messager.alert('提示','请选中父资源');
		return false;
	} 
	$('#myForm').submit();
	
}

//刷新tree
function reloadTree(){
	$('#tree').tree('reload',_root.target);
	//展开根节点
    $('#tree').tree('expand',_root.target); 
}
