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
	src="${ctx}/skin/js/admin/subscribecourse/month_subscribe_count.js"></script>
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
				rownumbers:true,toolbar:'#buttonGroup',pagination:false"
		nowrap="false">
		<thead>
			<tr>
				<th data-options="field:'ids',checkbox:true"></th>

				<th data-options="field:'classDate',width:'50'">月份</th>
				<th data-options="field:'one2OneCount',width:'50'">core1v1</th>
				<th data-options="field:'one2ManyCount',width:'50'">extension1v6</th>
				<th data-options="field:'extensionOne2OneCount',width:'50'">extension1v1</th>
				<th data-options="field:'englishStudioCount',width:'50'">English
					Studio课程</th>
				<th data-options="field:'ocCount',width:'50'">OC课程</th>
				<th data-options="field:'demoOne2OneCount',width:'50'">demo1v1</th>
				<th data-options="field:'total',width:'100'">合计</th>
			</tr>
		</thead>
	</table>
	<div id="buttonGroup" style="padding: 5px; height: auto">
		开始时间:<input class="easyui-datetimebox" name="startTime" id="startTime"
			data-options="required:true,showSeconds:true" style="width: 150px">
			结束时间:<input class="easyui-datetimebox" name="endTime" id="endTime"
			data-options="required:true,showSeconds:true" style="width: 150px">
				<input type="checkbox" name="studentShow" id="studentShow">StudentShow</input>
				<a href="#" class="easyui-linkbutton" iconCls="icon-search"
				onclick="reloadDataGrid()">查询</a>
	</div>
</body>
</html>