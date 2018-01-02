<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!-- modified by ivan.mgh,2016年4月21日13:08:29 -->
<!-----------------start--------------------->
<%@ include file="/WEB-INF/jsp/common/taglib.jsp"%>

<script type="text/javascript">
	// 全局变量
	// crm标记
	var crmflag = null;
	// 授权凭证
	var auth = null;
	
	//是否有权限修改价格
	var isHavaChangeOrderCoursePricePermission = "${isHavaChangeOrderCoursePricePermission}";
</script>

<c:if test="${authMap.crmflag }">
	<link rel="stylesheet" type="text/css"
		href="${ctx}/skin/plugins/easyui/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css"
		href="${ctx}/skin/plugins/easyui/themes/icon.css" />

	<script type="text/javascript"
		src="${ctx}/skin/plugins/easyui/jquery.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/skin/plugins/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/skin/plugins/easyui/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript"
		src="${ctx}/skin/js/common/easyui-common.js"></script>
	
	<!-- 初始化 -->
	<script type="text/javascript">
		crmflag = "${authMap.crmflag }";
		auth = "${authMap.auth }";
		isHavaChangeOrderCoursePricePermission = "${authMap.isCrmHavaChangeOrderCoursePricePermission}";
		
		var isHaveGiveLesson = "${authMap.isHaveGiveLesson }";
		var isHaveGiveTime = "${authMap.isHaveGiveTime }";
	</script>
	
	<script type="text/javascript" src="${ctx}/skin/js/web/angular.min.js"></script> 
	<script type="text/javascript" src="${ctx}/skin/js/web/jquery.validate.js"></script>
	<script type="text/javascript" src="${ctx}/skin/js/web/messages_zh.js"></script>
	<script type="text/javascript" src="${ctx}/skin/js/admin/ordercourse/pay.orderlist.js"></script>
	
	
	<script type="text/javascript"
		src="${ctx}/skin/js/admin/user/admin_user_detail.js"></script>
	<script type="text/javascript"
		src="${ctx}/skin/js/admin/user/admin_user_detail_order.js"></script>
	<script type="text/javascript"
		src="${ctx}/skin/js/admin/user/admin_user_level.js"></script>
</c:if>

<!-----------------end--------------------->

<!-- modified by ivan.mgh,2016年4月21日17:11:59 -->
<!------------start-------------->
<!-- 隐藏域 -->
<input type="hidden" id="crm_hidden_crmflag" value="${authMap.crmflag }"/>
<input type="hidden" id="crm_hidden_auth" value="${authMap.auth }"/>
<c:choose>
	<c:when test="${authMap.crmflag }">
		<input type="hidden" id="crm_hidden_seller" value="${authMap.seller }" />
	</c:when>
	<c:otherwise>
		<input type="hidden" id="hidden_seller" name="hidden_seller"
			value="${sessionScope.adminSessionUser.adminUserName}" />
	</c:otherwise>
</c:choose>
<input type="hidden" id="crm_hidden_user_id" value="${authMap.user_id }"/>
<input type="hidden" id="crm_hidden_user_phone" value="${authMap.user_phone }"/>
<input type="hidden" id="crm_hidden_user_name" value="${authMap.user_name }"/>
<input type="hidden" id="crm_hidden_cl" value="${authMap.current_level }"/>
<!------------end-------------->

<script type="text/javascript" src="${ctx}/skin/js/admin/ordercourse/admin_ordercourse_save_insert.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/common/jsonDate_common.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/admin/ordercourse/admin_ordercourse_create_common.js"></script>
<style>
td{
	font-size:12px;
}

</style>
<!-- 非form 公用模块  -->
<!-- modified by ivan.mgh,2016年4月21日13:24:36 -->
<!------------start-------------->
<c:if test="${authMap.crmflag != true }">
<%@ include file="/WEB-INF/jsp/admin/ordercourse/ordercoursesave/admin_ordercourse_save_common_notform.jsp"%>
</c:if>
<!------------end-------------->

<!-- 拟定合同DIV -->
<div id="editContractDiv" name="editContractDiv" class="easyui-dialog" title="新增合同"
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
    
    <!-- modified by ivan.mgh,2016年4月21日13:11:12 -->
    <!-- 去掉了form的action属性 -->
	<form id="editContractForm" name="editContractForm" method="post" >
		<!-- 选择要续约的合同 动态table,如果拟定的是续约合同才会导致这个table加载并初始化部分input字段，否则这个table形同虚设。 -->
		<table id="renewalOrderCourseTable" name="renewalOrderCourseTable" cellpadding="5"></table>
		<%@ include file="/WEB-INF/jsp/admin/ordercourse/ordercoursesave/admin_ordercourse_save_common_form.jsp"%> 
		<!-- 差价table,如果拟定的是续约合同才会导致这个table加载并初始化部分input字段，否则这个table形同虚设。  -->
		<table id="totalFinalPriceTable" name="totalFinalPriceTable" cellpadding="5"></table>
	</form>
</div>


<!-- modified by ivan.mgh,2016年4月25日16:55:08 -->
<!------------start-------------->
<!-- 预览合同 、查看合同、拆分合同订单 三个dialog div -->
<div style="display: none;">
	<!-- 预览合同 -->
	<div id="lookContractDiv" name="lookContractDiv" class="easyui-dialog"
		title="合同管理-预览合同" style="width: 600px; height: 550px; padding: 10px;"
		modal="true" resizable="false" closed="true"
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

	<!-- 查看合同 -->
	<div id="lookContractNoButtonsDiv" name="lookContractNoButtonsDiv"
		class="easyui-dialog" title="查看合同"
		style="width: 600px; height: 550px; padding: 10px;" modal="true"
		resizable="false" closed="true" data-options="iconCls:'icon-edit'">
	</div>
	
	<!-- 拆分合同订单 -->
	<div id="splitOrderCourseDiv" name="splitOrderCourseDiv"
		class="easyui-dialog" title="拆分合同"
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
</div>
<!------------end-------------->

<!-- modified by ivan.mgh,2016年4月21日13:11:12 -->
<!------------start-------------->
<c:if test="${authMap.crmflag }">
<script type="text/javascript">
$(function(){
	insertContractFromCrm();
});

</script>
</c:if>
<!------------end-------------->
