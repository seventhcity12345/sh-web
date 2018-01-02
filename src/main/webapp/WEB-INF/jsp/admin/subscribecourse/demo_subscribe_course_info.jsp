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
	src="${ctx}/skin/js/admin/subscribecourse/demo_subscribe_course_info.js"></script>
<script type="text/javascript"
	src="${ctx}/skin/js/admin/user/admin_user_detail.js"></script>
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
				rownumbers:true,toolbar:'#buttonGroup',onDblClickRow:function(rowIndex,rowData){doubleClickListener(rowIndex, rowData);},
				onLoadSuccess:function(data){findCourseCount();}"
		nowrap="false">
		<thead>
			<tr>
				<th data-options="field:'ids',checkbox:true"></th>
				<th data-options="field:'userId',hidden:true">user_id</th>
				<th data-options="field:'keyId',hidden:true">预约id</th>
				<th data-options="field:'inviteUrl',hidden:true">inviteUrl</th>
				<th data-options="field:'teacherTimeId',hidden:true">teacherTimeId</th>
				<th data-options="field:'reportUrl',hidden:true">reportUrl</th>
				<th data-options="field:'commentUrl',hidden:true">commentUrl</th>

				<th
					data-options="field:'startTime',width:'100',sortable:true,formatter:formatDate,unableGroupSearch:true">上课时间</th>
				<th data-options="field:'courseTitle',width:'100',sortable:false,unableGroupSearch:true">课程名字</th>
				<th data-options="field:'courseTypeChineseName',width:'50',sortable:true,dbColumnName:'course_type',unableGroupSearch:true">课程类型</th>
				<th data-options="field:'teacherName',width:'100',sortable:true">老师名字</th>
				<th data-options="field:'thirdFrom',width:'50',unableGroupSearch:true">老师来源</th>
				<th data-options="field:'adminUserName',width:'50'">订课人</th>
				<th data-options="field:'createDate',ambiguous:'tsc',width:'100',formatter:formatDate">定课时间</th>
				<th data-options="field:'userName',width:'100',sortable:true,ambiguous:'tsc',unableGroupSearch:true">学员名字</th>
				<th data-options="field:'userCode',width:'50',sortable:true,unableGroupSearch:true">学员ID</th>
				<th
					data-options="field:'phone',width:'50',sortable:false">学员手机</th>
				<th
					data-options="field:'subscribeStatus',width:'50',sortable:true,formatter:formatShowOrNoShow,styler:addRedBoldForNo">学员Show</th>
				<th
					data-options="field:'isAttend',width:'50',sortable:true,formatter:formatShowOrNoShow,styler:addRedBoldForNo,unableGroupSearch:true">教师Show</th>
					<th
					data-options="field:'urlAndMeetingId',width:'150',sortable:false,formatter:formatUrlAndMeeting,unableGroupSearch:true">域名&会议号</th>
				<th
					data-options="field:'webexRoomHostId',width:'100',sortable:false,unableGroupSearch:true">房间号</th>	
				<th
					data-options="field:'haveReport',width:'50',formatter:haveReportFormater,unableGroupSearch:true">是否有demo报告</th>
				<th data-options="field:'transactionAmount',width:'100',unableGroupSearch:true,formatter:transactionAmountFormater">成交金额</th>
			</tr>
		</thead>
	</table>
	<div id="buttonGroup" style="padding: 5px; height: auto">
		<a href="#" class="easyui-linkbutton" iconCls="icon-search"
			onclick="groupSearch()">组合查询</a> 开始时间:<input
			class="easyui-datetimebox" name="startTime" id="startTime"
			data-options="required:true,showSeconds:true" style="width: 150px" />
		结束时间:<input class="easyui-datetimebox" name="endTime" id="endTime"
			data-options="required:true,showSeconds:true" style="width: 150px" />
		<a href="#" class="easyui-linkbutton" iconCls="icon-search"
			onclick="reloadDataGrid()">查询</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-reload"
			onclick="changeStudentShowStatus()">切换学员状态</a>
			<a href="#"
			class="easyui-linkbutton" iconCls="icon-search"
			onclick="openReportUrl()">查看demo报告</a>
			<a href="#"
			class="easyui-linkbutton" iconCls="icon-edit"
			onclick="openCommentUrl()">修改demo报告</a>
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