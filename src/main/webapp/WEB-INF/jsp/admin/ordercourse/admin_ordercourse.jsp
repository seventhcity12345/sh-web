<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="com.webi.hwj.util.SessionUtil" %>
<%@ page import="com.webi.hwj.bean.SessionAdminUser" %>
<%@ include file="/WEB-INF/jsp/common/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragrma", "no-cache");
	response.setDateHeader("Expires", 0);
	
	SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
	
	boolean isHaveGiveLesson = sessionAdminUser.isHavePermisson("contract:giveLesson");
	boolean isHaveGiveTime = sessionAdminUser.isHavePermisson("contract:giveTime");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">

<!-- modified by ivan.mgh,2016年5月10日11:03:36 -->
<!---------------start------------------->
<script type="text/javascript">
	// 全局变量
	// crm标记
	var crmflag = null;
	// 授权凭证
	var auth = null;
	
	//是否有权限修改价格
	var isHavaChangeOrderCoursePricePermission = "${isHavaChangeOrderCoursePricePermission}";
</script>
<!---------------end------------------->

<script>
	var isHaveGiveLesson = '<%=isHaveGiveLesson%>';
	var isHaveGiveTime = '<%=isHaveGiveTime%>';
</script>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>综合查询</title>
<link rel="stylesheet" type="text/css" href="${ctx}/skin/plugins/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/skin/plugins/easyui/themes/icon.css" />

<script type="text/javascript" src="${ctx}/skin/plugins/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/skin/plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/skin/plugins/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/common/easyui-common.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/admin/user/admin_user_detail.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/admin/user/admin_user_detail_order.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/web/angular.min.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/web/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/web/messages_zh.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/admin/ordercourse/pay.orderlist.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/admin/ordercourse/admin_create_redeem_code.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/admin/ordercourse/admin_change_cc.js"></script>

<script>

	//合同状态
	function formatContractStatus(contractStatus) {
		var returnStr = '';
		switch (contractStatus) {
		case 1:
			returnStr = '待确认';
			break;
		case 2:
			returnStr = '执行中';
			break;
		case 3:
			returnStr = '已结束';
			break;
		}
		return returnStr;
	}

	function formatOrderStatus(orderStatus) {
		var returnStr = '';
		switch (orderStatus) {
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
	
	//撤回合同
	function withdrawOrderCourse() {
		var row = $('#dataGrid1').datagrid('getSelections');

		if (row.length != 1) {
			$.messager.alert('提示', '请选中一条数据！');
			return;
		}
		$.messager.confirm('确认', '是否确认撤回订单？', function(r) {
			if (r) {
				//order_status < 5
				if (row[0].order_status == '1' || row[0].order_status == '2'
						|| row[0].order_status == '3') {
					$.ajax({
						type : "POST", //post提交方式默认是get
						dataType : 'json',
						url : basePath
								+ '/admin/orderCourse/withdrawOrderCourse',
						data : {
							order_id : row[0].key_id
						},
						error : function(data) { // 设置表单提交出错 
							$('#loading-mask').hide();
							$.messager.alert('提示', '系统出现异常,请联系管理员', 'error');
						},
						success : function(result) {
							$('#loading-mask').hide();
							if (result.success) {
								$('#dataGrid1').datagrid('reload');
								$.messager.alert('提示', result.msg);
							} else {
								$.messager.alert('提示', result.msg);
							}
						}
					});
				} else {
					$.messager.alert('提示', '此合同已产生付款记录，不可撤回！');
					return;
				}
			}
		});
	}

	//删除合同
	function deleteOrderCourse() {
		var row = $('#dataGrid1').datagrid('getSelections');

		if (row.length != 1) {
			$.messager.alert('提示', '请选中一条数据！');
			return;
		}
		$.messager.confirm('确认', '是否确认删除订单？', function(r) {
			if (r) {
				$.ajax({
					type : "POST", //post提交方式默认是get
					dataType : 'json',
					url : basePath + '/admin/orderCourse/deleteOrderCourse',
					data : {
						order_id : row[0].key_id
					},
					error : function(data) { // 设置表单提交出错 
						$('#loading-mask').hide();
						$.messager.alert('提示', '系统出现异常,请联系管理员', 'error');
					},
					success : function(result) {
						$('#loading-mask').hide();
						if (result.code == 200) {
							$('#dataGrid1').datagrid('reload');
							$.messager.alert('提示', "删除成功");
						} 
                        else if (result.code == 10001) {
                            $.messager.alert('提示', result.msg);
                        }
                        else {
                            $.messager.alert('提示', errorCode[result.code]);
                        }
					}
				});
			}
		});
	}
	
</script>

</head>
<body>
	<table id="dataGrid1" name="dataGrid1" class="easyui-datagrid"
		fit="true" fitColumns="true" nowrap="false"
		data-options="pageList:[20,40,60,80,100],pagination:true,pageSize:20,fit:true,
				rownumbers:true,url:'${ctx}/admin/orderCourse/pagelist',ctrlSelect:true,
				toolbar:'#tb',onDblClickRow:function(rowIndex,rowData){
				   lookUserDetail(rowIndex, rowData);
				}">
		<thead>
			<tr>
				<th data-options="field:'ids',checkbox:true"></th>
				<th data-options="field:'renewaled_limit_show_time',width:'50',hidden:true">renewaled_limit_show_time</th>
				<th data-options="field:'renewaled_limit_show_time_unit',width:'50',hidden:true">renewaled_limit_show_time_unit</th>
				<th data-options="field:'renewaled_total_final_price',width:'50',hidden:true">renewaled_total_final_price</th>
				<th data-options="field:'renewaled_total_real_price',width:'50',hidden:true">renewaled_total_real_price</th>
				<th data-options="field:'total_final_price',width:'50',hidden:true">total_final_price</th>
				<th data-options="field:'limit_show_time',width:'50',hidden:true">limit_show_time</th>
				<th data-options="field:'limit_show_time_unit',width:'50',hidden:true">limit_show_time_unit</th>
				<th data-options="field:'category_type',width:'50',hidden:true">category_type</th>
				<th data-options="field:'order_original_type',width:'50',hidden:true">order_original_type</th>
				<th data-options="field:'total_final_price',width:'50',hidden:true">total_final_price</th>
				<th data-options="field:'from_path',width:'50',hidden:true">from_path</th>
				<th data-options="field:'course_package_id',width:'150',hidden:true">course_package_id</th>
				<th data-options="field:'course_package_price_id',width:'150',hidden:true">course_package_price_id</th>
				<th data-options="field:'package_price_end_time',width:'150',hidden:true">package_price_end_time</th>
				<th data-options="field:'learning_coach_id',width:'150',hidden:true">learning_coach_id</th>
				<th data-options="field:'learning_coach_name',width:'150',hidden:true">learning_coach_name</th>
				<th data-options="field:'key_id',width:'150',hidden:true">key_id</th>
				<th data-options="field:'gift_time',width:'50',hidden:true">gift_time</th>
				
				<th data-options="field:'user_code',width:'50',sortable:true">用户编码</th>
				<th data-options="field:'core.user_name',width:'80',sortable:true">用户姓名</th>
				<th data-options="field:'u.phone',width:'80',sortable:true">用户手机</th>
				<th data-options="field:'admin_user_name',width:'50',sortable:true,ambiguous:'bu'">CC</th>
				<th data-options="field:'format_user_from_type',width:'70',sortable:false,unableGroupSearch:true">学生来源</th>
				<th data-options="field:'total_show_price',width:'50',sortable:true">原始价格</th>
				<th data-options="field:'core.key_id',width:'180',sortable:true">订单编号</th>
				<th data-options="field:'core.create_date',width:'80',sortable:true,formatter:formatDate">创建时间</th>
				<th data-options="field:'start_order_time',width:'80',sortable:true,formatter:formatDate">支付时间</th>
				<th
					data-options="field:'end_order_time',width:'80',sortable:true,formatter:formatDate">合同结束时间</th>
				<th data-options="field:'order_status',width:'50',sortable:true,formatter:formatOrderStatus">订单状态</th>
				<th data-options="field:'contract_status',width:'50',sortable:false,formatter:formatContractStatus">合同状态</th>
				<th data-options="field:'order_remark',width:'200',sortable:false">合同备注</th>
			</tr>
		</thead>
	</table>

	<div id="tb" style="padding: 5px; height: auto">
		<div style="margin-bottom: 5px">
			<!-- 
	            <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="editContract(0)">新增</a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="batchDelete()">删除</a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="editContract(1)">修改</a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="showDefaultDetailDiv('user/detail')">查看</a>
	            
	        -->
	        
	        
	        <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="groupSearch()">组合查询</a>
	        <%if(sessionAdminUser.isHavePermisson("contract:deleteOrder")){%>
	        	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteOrderCourse()">删除订单</a>
	        <%}%>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="splitOrderCourse()">拆分订单</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="withdrawOrderCourse()">撤回订单</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="updateContract()">修改订单</a>
			<%if(sessionAdminUser.isHavePermisson("contract:createRedeemCode")){%>
	        	<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="createRedeemCode()">生成兑换码</a>
	        <%}%>
			<%if(sessionAdminUser.isHavePermisson("contract:changeCc")){%>
	        	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="openChangeCcDialog()">更换CC</a>
	        <%}%>
			
			
			
		</div>
	</div>
	
	<!-- 生成兑换码 -->
	<div id="createRedeemCodeDiv" name="createRedeemCodeDiv" class="easyui-dialog" title="生成兑换码"
		style="width: 600px; height: 300px; padding: 10px;" modal="true"
		resizable="false" closed="true"
		data-options="iconCls:'icon-add',
    		buttons: [{
                    text:'批量生成',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitAndCreateRedeemCode();
                    }
                },{
	         		text:'关闭',
	         		iconCls:'icon-cancel',
	         		handler:function(){
                     $('#createRedeemCodeDiv').dialog('close');
                    }
	      		}] 
    		">
    	<form id="createRedeemCodeForm" enctype="multipart/form-data"
    	 action="${ctx }/admin/redeemCode/betchCreateRedeemCodeBySent"
            method="post">
			<table cellpadding="5">
				<tr>
					<td>活动名称:</td>
					<td>
						<input class="easyui-textbox" id="activityName" name="activityName" style="width:150px;"
							data-options="editable:true,required:true"/>
					</td>
				</tr>
				<tr>
					<td>生成数量:</td>
					<td>
						<input class="easyui-textbox" id="redeemCodeNum" name="redeemCodeNum" style="width:150px;"
						 data-options="editable:true,required:true,validType:'range[1,2000]'"/>
					</td>
				</tr>
				<tr>
    				<td>价格策略:</td>
    				<td>
	   				<select class="easyui-combobox" name="coursePackagePriceId" id ="coursePackagePriceId" 
	    					style="width:150px;" data-options="panelHeight:'100',
					    	required:true,editable:false,
					    	url:'${ctx}/admin/coursePackagePrice/findListByTime',
					    	valueField:'keyId',
					    	textField:'packagePriceName',
					    	onSelect:function(rec){selectRedeemPackagePriceName();}">
					</select>
				    </td>
				</tr>
				<tr>
				    <td>课程包:</td>
				    <td>
					<input class="easyui-combobox" name="coursePackageId" id="coursePackageId"
				      style="width: 150px;"
				      data-options="
				           editable:false,
				           valueField:'keyId',
				           textField:'packageName',
				           required:true,
				           panelHeight:'250px'"/>
				    </td>
			   </tr>
			   <tr>
					<td>有效期:</td>
					<td>
						<input id="activityStartTime" name="activityStartTime" class="easyui-datetimebox" style="width:150px;" 
						data-options="editable:false,required:true"/>
					</td>
					<td>--</td>
					<td>
						<input id="activityEndTime" name="activityEndTime" class="easyui-datetimebox" style="width:150px;" 
						data-options="editable:false,required:true"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 生成兑换码 -->
	
	<!-- 添加新用户 -->
	<div id="addNewUserDiv" name="addNewUserDiv" class="easyui-dialog" title="用户注册"
		style="width: 300px; height: 150px; padding: 10px;" modal="true"
		resizable="false" closed="true"
		data-options="iconCls:'icon-edit',
    		buttons: [{
                    text:'注册',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitAddNewUser();
                    }
                }] 
    		">
			<table cellpadding="5">
				<tr>
					<td>手机号码:</td>
					<td>
						<input class="easyui-textbox" id="phone" name="phone" data-options="editable:true,required:true"/>
					</td>
				</tr>
			</table>
	</div>
	<!-- 添加新用户 -->

	<!-- 编辑级别窗口 -->
	<div id="editCurrentLevelDiv" name="editCurrentLevelDiv" class="easyui-dialog" title="编辑级别"
		style="width: 300px; height: 150px; padding: 10px;" modal="true"
		resizable="false" closed="true"
		data-options="iconCls:'icon-edit',
    		buttons: [{
                    text:'修改级别',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitEditLevel();
                    }
                }] 
    		">
			
			<table cellpadding="5">
				<tr>
					<td>当前级别:</td>
					<td>
						<input class="easyui-combobox" id="edit_current_level" name="edit_current_level" data-options="editable:false,required:true"/>
					</td>
				</tr>
			</table>
	</div>
	
	<!-- 拆分合同订单 -->
	<div id="splitOrderCourseDiv" name="splitOrderCourseDiv" class="easyui-dialog" title="拆分合同"
		style="width: 1000px; height: 600px; padding: 10px;" modal="true"
		resizable="false" closed="true"
		data-options="iconCls:'icon-edit',
    		buttons: [{
                    text:'关闭',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#splitOrderCourseDiv').dialog('close');
                    }
                }] 
    		">
	</div>
	
	<!-- 查看学员详情 -->
    <div id="lookUserDetailDiv" name="lookUserDetailDiv" class="easyui-dialog" title="查看详情"
	  style="width: 1024px; height: 500px; padding: 10px;" modal="true"
	  resizable="false" closed="true" data-options="iconCls:'icon-edit'">
	  
	</div>
	
	
	<!-- 预览合同title="预览合同" -->
	 <div id="lookContractDiv" name="lookContractDiv" class="easyui-dialog" title="合同管理-预览合同"
	  style="width: 600px; height: 550px; padding: 10px;" modal="true"
	  resizable="false" closed="true"
	  data-options="iconCls:'icon-edit',
         buttons: [{
	         text:'拆分订单',
	         iconCls:'icon-edit',
	         handler:function(){
	         	splitOrderCourse();
	         }
	      	},{
	         text:'返回修改',
	         iconCls:'icon-cancel',
	         handler:function(){
	         	returnUpdate();
	         }
	      }] ">
     
     
	</div>
	
	<!-- 拟定合同/拆分合同相关内容 -->
	<%@ include file="/WEB-INF/jsp/admin/ordercourse/ordercoursesave/admin_ordercourse_save_update.jsp"%> 
	
	
	<!-- 更换cc -->
		<!-- 编辑级别窗口 -->
	<div id="changeCcDialog" name="changeCcDialog" class="easyui-dialog" title="更换CC"
		style="width: 350px; height: 180px; padding: 30px;" modal="true"
		resizable="false" closed="true"
		data-options="iconCls:'icon-edit',
    		buttons: [{
                    text:'更换CC',
                    iconCls:'icon-ok',
                    handler:function(){
                       submitChangeCc();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-redo',
                    handler:function(){
                     $('#changeCcDialog').dialog('close');
                    }
                }] 
    	">
		<!-- 编辑table-->
		<table cellpadding="5">
				<tr>
					<td>CC名字</td>
					<td>
						<input class="easyui-combobox" name="changeCcId" id="changeCcId" data-options="
						      url:'${ctx }/admin/adminUser/findAdminUserListCc',
						      editable:false,
						      valueField:'keyId',
						      textField:'account',
						      required:true,
						      panelHeight:'88px'"/>
					</td>
				</tr>
		</table>
		<!-- 编辑table-->
	</div>
		
		

	<c:import url="/WEB-INF/jsp/common/group_search_div.jsp" />
</body>
</html>