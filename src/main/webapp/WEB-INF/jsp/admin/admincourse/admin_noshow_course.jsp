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
				+ '/admin/course/findNoShowCoursePage?findAllFlag=true';
		var queryParams = {};
		$('#dataGrid1').datagrid('load', queryParams);
	}
	gridName = 'dataGrid1';

	//双击事件
	function doubleClickListener(rowIndex, rowData) {
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
				rownumbers:true,url:'${ctx}/admin/course/findNoShowCoursePage?findAllFlag=false',
				toolbar:'#buttonGroup',onDblClickRow :function(rowIndex,rowData){doubleClickListener(rowIndex, rowData);} "
		nowrap="false">
		<thead>
			<tr>

				<th data-options="field:'user_id',hidden:true">user_id</th>

				<th data-options="field:'ids',checkbox:true"></th>
				<th
					data-options="field:'start_time',width:'50',sortable:true,formatter:formatDate">上课时间</th>
				<th data-options="field:'course_title',width:'100',sortable:true">NoShow课程名字</th>
				<th data-options="field:'tu.user_name',width:'50',sortable:true">学员名字</th>
				<th data-options="field:'english_name',width:'50',sortable:true">学员英文名</th>
				<th
					data-options="field:'user_code',width:'100',sortable:true,sortable:true">学员编号(ID)</th>
				<th data-options="field:'phone',width:'100',sortable:true">手机号</th>
				<th data-options="field:'admin_user_name',width:'100',sortable:true">LC名字</th>
				<th data-options="field:'course_type',width:'100',sortable:true">课程类型</th>
				<th
					data-options="field:'last_follow_time',width:'100',sortable:true,formatter:formatDate">最后一次Follow时间</th>
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