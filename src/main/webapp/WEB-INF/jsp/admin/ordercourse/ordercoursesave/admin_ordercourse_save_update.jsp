<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<script type="text/javascript" src="${ctx}/skin/js/admin/ordercourse/admin_ordercourse_create_update.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/admin/ordercourse/admin_ordercourse_create_common.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/common/jsonDate_common.js"></script>

<style>
td{
	font-size:12px;
}

</style>
<!-- 非form 公用模块  -->
<%@ include file="/WEB-INF/jsp/admin/ordercourse/ordercoursesave/admin_ordercourse_save_common_notform.jsp"%>

<div id="editContractDiv" name="editContractDiv" class="easyui-dialog" title="修改合同"
 style="width: 600px; height: 80%; padding: 10px;" modal="true"
 resizable="false" closed="true"
 data-options="iconCls:'icon-edit',
     buttons: [{
                   text:'预览合同',
                   iconCls:'icon-ok',
                   handler:function(){
                       submitViewContract();
                   }
               },{
                   text:'关闭',
                   iconCls:'icon-redo',
                   handler:function(){
                    	$('#editContractDiv').dialog('close');
                   }
               }]
     ">
	<input type="hidden" id="hidden_seller" name="hidden_seller" value="${sessionScope.adminSessionUser.adminUserName}"/>
	<form id="editContractForm" name="editContractForm" method="post" action="${ctx}/admin/orderCourse/saveOrderCourse" >
		<!-- 差价table -->
		<table id="totalFinalPriceTable" name="totalFinalPriceTable" cellpadding="5"></table> 
		<!-- 拟定合同/拆分合同相关form内容 -->
		<%@ include file="/WEB-INF/jsp/admin/ordercourse/ordercoursesave/admin_ordercourse_save_common_form.jsp"%>
	
		<!-- update更新 新的 -->
		<input type="hidden" name="renewalOrderCourseKeyId" id="renewalOrderCourseKeyId" />
	</form>

</div>