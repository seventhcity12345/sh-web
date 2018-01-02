<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragrma", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>综合查询</title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/skin/plugins/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/skin/plugins/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/skin/plugins/easyui/themes/input_file_common.css" />
<script type="text/javascript"
	src="${ctx}/skin/plugins/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/skin/plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/skin/plugins/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${ctx}/skin/js/common/easyui-common.js"></script>
<script type="text/javascript"
	src="${ctx}/skin/js/common/jsonDate_common.js"></script>
<script type="text/javascript"
	src="${ctx}/skin/js/admin/user/admin_user_detail.js"></script>
<script type="text/javascript">
	//查看所有的学员，填充到本页面中的dataGrid中
	function findAllStudent() {
		$('#dataGrid1').datagrid('options').url = basePath
				+ '/admin/keyFollowup/findKeyFollowupStudentPage?findAllFlag=true&findType=lessFollowup';
		var queryParams = {};
		$('#dataGrid1').datagrid('load', queryParams);
	}
	gridName = 'dataGrid1';
	//双击事件
	function doubleClickListener(rowIndex, rowData) {
		rowData.user_id = rowData.userId;
		lookUserDetail(rowIndex, rowData);
	}
</script>
<style>
td {
	font-size: 12px;
}
</style>
</head>
<body>
	<table id="dataGrid1" name="dataGrid1" class="easyui-datagrid"
		fit="true" fitColumns="true"
		data-options="pageList:[100,200],pagination:true,pageSize:100,fit:true,singleSelect:true,
				rownumbers:true,url:'${ctx}/admin/keyFollowup/findKeyFollowupStudentPage?findAllFlag=false&findType=lessFollowup',
				toolbar:'#buttonGroup',onDblClickRow :function(rowIndex,rowData){doubleClickListener(rowIndex, rowData);} "
		nowrap="false">
		<thead>
			<tr>
				<th data-options="field:'ids',checkbox:true"></th>
				<th data-options="field:'userId',hidden:true">user_id</th>
				
				<th data-options="field:'userCode',width:'50',sortable:true">ID</th>
				<th data-options="field:'realName',width:'100',sortable:true">真实姓名</th>
				<th data-options="field:'englishName',width:'50',sortable:true">英文名</th>
				<th data-options="field:'adminUserName',width:'50',sortable:true">LC名字</th>
				<th
					data-options="field:'phone',width:'100',sortable:true,sortable:true">手机号</th>
				<th
					data-options="field:'startOrderTime',width:'100',sortable:true,formatter:formatDate">合同开始时间</th>
				<th data-options="field:'followupCount',width:'100',sortable:true">本月Follow次数</th>
				<th
					data-options="field:'lastFollowTime',width:'100',sortable:true,formatter:formatDate">最后一次Follow时间</th>
			</tr>
		</thead>
	</table>
	<div id="buttonGroup" style="padding: 5px; height: auto">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit"
			onclick="findAllStudent()">全部学员</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-search"
			onclick="groupSearch()">组合查询</a>
	</div>
		<!-- 查看学员详情 -->
	<div id="lookUserDetailDiv" name="lookUserDetailDiv"
		class="easyui-dialog" title="查看详情"
		style="width: 1024px; height: 500px; padding: 10px;" modal="true"
		resizable="false" closed="true" data-options="iconCls:'icon-edit'">
	</div>
	<!-- 组合查询 -->

	<c:import url="/WEB-INF/jsp/common/group_search_div.jsp" />
</body>
</html>