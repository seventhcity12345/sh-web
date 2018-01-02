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
<script type="text/javascript">
	gridName = 'dataGrid1';
	
	$(function(){
		var currentTime = new Date();
		var endTime =  currentTime.format("yyyy-MM-dd hh:mm:ss");   
		var oneMonthAgo = new Date(currentTime.getTime() - 31 * 24 * 60 * 60 * 1000);
		var  startTime = oneMonthAgo.format("yyyy-MM-dd hh:mm:ss");
		
		$('#startTime').datetimebox('setValue', startTime);
		$('#endTime').datetimebox('setValue', endTime);
		reloadDataGrid();
	});
	
	//加载数据
	function reloadDataGrid(){
		var startTime = $('#startTime').datetimebox('getValue');	
		var endTime = $('#endTime').datetimebox('getValue');	
		$('#dataGrid1').datagrid({
		    url:basePath + '/admin/courseComment/findTeacherCourseCommentList?startTime='+startTime
		    		+'&endTime='+endTime});
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
				rownumbers:true,toolbar:'#buttonGroup'"
		nowrap="false">
		<thead>
			<tr>
				<th data-options="field:'ids',checkbox:true"></th>
				
				<th data-options="field:'courseTitle',width:'50',sortable:true">课程标题</th>
				<th data-options="field:'startTime',width:'50',sortable:true,formatter:formatDate">上课时间</th>
				<th data-options="field:'teacherName',width:'50',sortable:true,ambiguous:'tt'">老师名字</th>
				<th data-options="field:'thirdFrom',width:'50',sortable:true">老师来源</th>
				<th data-options="field:'userName',width:'50',sortable:true">学员名字</th>
				<th data-options="field:'userCode',width:'50',sortable:true">学员编号</th>
				<th data-options="field:'preparationScore',width:'100',sortable:true">准备度</th>
				<th data-options="field:'deliveryScore',width:'50',sortable:true">专业度</th>
				<th data-options="field:'interactionScore',width:'50',sortable:true">互动性</th>
				<th data-options="field:'showScore',width:'100',sortable:true,sortable:true">综合</th>
				<th data-options="field:'commentContent',width:'100',sortable:true">评论</th>
			</tr>
		</thead>
	</table>
	<div id="buttonGroup" style="padding: 5px; height: auto">
		<a href="#"
			class="easyui-linkbutton" iconCls="icon-search"
			onclick="groupSearch()">组合查询</a>
		开始时间:<input class="easyui-datetimebox" name="startTime"  id="startTime"
        data-options="required:true,showSeconds:true" style="width:150px">
                        结束时间:<input class="easyui-datetimebox" name="endTime" id="endTime"
        data-options="required:true,showSeconds:true" style="width:150px">
        <a href="#"
			class="easyui-linkbutton" iconCls="icon-search"
			onclick="reloadDataGrid()">查询</a>
	</div>
	<!-- 组合查询 -->

	<c:import url="/WEB-INF/jsp/common/group_search_div.jsp" />
</body>
</html>