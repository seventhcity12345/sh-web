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
	src="${ctx}/skin/js/admin/subscribecourse/teacher_course_count.js"></script>
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

				<th data-options="field:'classDate',width:'50',unableGroupSearch:true">上课日期</th>
				<th data-options="field:'thirdFrom',width:'100',sortable:true">老师来源</th>
				<th data-options="field:'teacherName',width:'50',sortable:true">老师名字</th>
				<th data-options="field:'courseType',width:'50',sortable:true">课程类型</th>
				<th data-options="field:'courseCount',width:'100',unableGroupSearch:true">课程数</th>
				<th data-options="field:'courseTime',width:'100',unableGroupSearch:true">课时数</th>
			</tr>
		</thead>
	</table>
	<div id="buttonGroup" style="padding: 5px; height: auto">
		<a href="#" class="easyui-linkbutton" iconCls="icon-search"
			onclick="groupSearch()">组合查询</a> 开始时间:<input
			class="easyui-datetimebox" name="startTime" id="startTime"
			data-options="required:true,showSeconds:true" style="width: 150px">
			结束时间:<input class="easyui-datetimebox" name="endTime" id="endTime"
			data-options="required:true,showSeconds:true" style="width: 150px">
				<input type="checkbox" name="studentShow" id="studentShow" checked="checked">StudentShow</input>
				<a href="#" class="easyui-linkbutton" iconCls="icon-search"
				onclick="reloadDataGrid()">查询</a>
	</div>
	<!-- 组合查询 -->

	<c:import url="/WEB-INF/jsp/common/group_search_div.jsp" />
</body>
</html>