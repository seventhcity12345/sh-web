// 查看合同
function lookContract() {
	var row = $('#orderCourseInfo').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请先选中一条数据！');
		return;
	}
	viewContract(row[0].key_id, "1");
}

function formatOrderStatus(orderStatus){
	var returnStr = '';
	switch(orderStatus){
		case 1:
			returnStr = '已拟定';
		break;
		case 2:
			returnStr = '已发送';
		break;
		case 3:
			returnStr = '已确认';
		break;
		case 4:
			returnStr = '支付中';
		break;
		case 5:
			returnStr = '已支付';
		break;
		case 6:
			returnStr = '已过期';
		break;
		case 7:
			returnStr = '已终止';
			break;
	}
	return returnStr; 
}

gridName = 'dataGrid1';


//预览合同
function viewContract(orderId,flag){
//	alert("预览合同:" + orderId + ", " +flag);
	if(orderId==null || orderId==null || orderId=="null"){
		$.messager.alert('提示',"请先拟定合同！");
		return ;
	}
	 $.ajax({
         type : "POST", 
         dataType : 'json',
         url : basePath + "/admin/config/findConfigByParam",
         data : {
        	 config_name : "contract_owner_url"
         },
         error : function(data) { // 设置表单提交出错
        	 $.messager.alert('提示',"系统出现异常,请联系管理员");
         },
         success : function(result) {// 提交成功
             if (result.length!=0) {
            	 var headUrl = result[0].config_value;
            	 if(flag=="1"){//只是查看，没有操作按钮
            			$('#lookContractNoButtonsDiv').dialog({
            		        closed: false,
            		        cache: false,
//            		        href: 'https://www.baidu.com',//basePath+'/admin/orderCourse/detail/'+orderId,
            		        content: '<iframe src="'+headUrl+'/web/views/member/contract-detail.html?'
            		        	+'keyId='+orderId+'"'
            		        	+'frameborder=0 height=100% width=100% max-height:99% max-width:99% scrolling="auto" ></iframe>',
            		        modal: true
            		    });
            		}else{//有操作按钮
            			global_order_id = orderId;
            			$('#lookContractDiv').dialog({
            				closed : false,
            				cache : false,
//            				href : basePath + '/admin/orderCourse/detail/' + orderId + "?auth="+auth,
            				content: '<iframe src="'+headUrl+'/web/views/member/contract-detail.html?'
            					+'keyId='+orderId+'"'
            		        	+'frameborder=0 height=100% width=100% max-height:99% max-width:99% scrolling="auto" ></iframe>',
            				modal : true,
            				buttons : [ {
            					text : '拆分订单',
            					iconCls : 'icon-edit',
            					handler : function() {
            						splitOrderCourse(orderId,"1");
            					}
            				}, {
            					text : '返回修改',
            					iconCls : 'icon-cancel',
            					handler : function() {
            						returnUpdate();
            					}
            				} ]
            			});
            		}
             } else {
            	 $.messager.alert('提示',result.msg);
             }
         }
     });
}

// 批量删除
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
						ids += row[i].user_id;
					}else{
						ids += row[i].user_id+",";
					}
				}
			} 
		
			$.ajax({          
		       type:"POST",   //post提交方式默认是get
		       dataType:'json', 
		       url:basePath+'/admin/user/delete', 
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

//续约详情浮层	
function openRenewalOrderCourseDiv(orderId) {
	/*$('#renewalOrderCourseDiv').dialog({
		closed : false,
		cache : false,
		href : basePath + '/admin/orderCourse/renewalOrderCourseDetailJsp/' + global_order_id,
		modal : true
	});*/
	$('#renewalOrderCourseDiv').dialog({
		closed : false,
		cache : false,
		href : basePath + '/orderCourse/renewalOrderCourseDetailJsp/' + orderId,
		modal : true,
		buttons : [{
            text:'关闭',
            iconCls:'icon-cancel',
            handler:function(){
                $('#renewalOrderCourseDiv').dialog('close');
            }
        }]
	});
	
	//$('#renewalOrderCourseDiv').dialog('open');
}

/**
 * 拆分订单
 * @desc 目前有2个入口，一个是用户详情页面，一个是合同管理页面
 * @param orderId 合同id
 * @param orderStatus 合同状态
 * @author alex
 */
function splitOrderCourse(orderId,orderStatus) {
	//如果参数都为空的话，则尝试从grid里获取
	if(orderId==null && orderStatus==null){
		var row = $('#dataGrid1').datagrid('getSelections');
		if (row.length != 1) {
			$.messager.alert('提示', '请选中一条数据！');
			return;
		}
		orderId = row[0].key_id;
		orderStatus = row[0].order_status;
	}
	
	if(orderId==null || orderId == "" || orderId=="null"){
		alert("orderId is null");
		return;
	}
	//直接在合同管理拆分

	//只有是“已支付”状态之前的状态才可以点击，否则提示：“该订单已支付，无法生成订单”
	//order_status < 5
	if (orderStatus == '1' || orderStatus == '2'
		|| orderStatus == '3' || orderStatus == '4') {
		
		$('#splitOrderCourseDiv').dialog({
			closed : false,
			cache : false,
			href : basePath
					+ '/orderCourseSplit/preOrderCourseSplitJsp?order_id='
					+ orderId,
			modal : true
		});
	} else {
		$.messager.alert('提示', '该订单当前状态无法拆分！');
		return;
	}
}