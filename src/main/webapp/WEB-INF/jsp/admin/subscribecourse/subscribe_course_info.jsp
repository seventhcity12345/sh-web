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
	src="${ctx}/skin/js/admin/subscribecourse/subscribe_course_info.js"></script>
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

				<th
					data-options="field:'startTime',width:'100',sortable:true,formatter:formatDate">上课时间</th>
				<th data-options="field:'courseTitle',width:'100',sortable:true">课程名字</th>
				<th data-options="field:'courseTypeChineseName',width:'50',sortable:true,dbColumnName:'course_type'">课程类型</th>
				<th data-options="field:'teacherName',width:'100',sortable:true,ambiguous:'tsc'">老师名字</th>
				<th
					data-options="field:'thirdFrom',width:'50'">老师来源</th>
				<th
					data-options="field:'adminUserName',width:'50'">LC</th>
				<th data-options="field:'userName',width:'100',sortable:true,ambiguous:'tsc'">学员名字</th>
				<th data-options="field:'userCode',width:'100',sortable:true">学员ID</th>
				<th
					data-options="field:'isFirst',width:'50',sortable:false,formatter:formatYesOrNo,unableGroupSearch:true">是否首课</th>
				<th
					data-options="field:'subscribeStatus',width:'50',sortable:true,formatter:formatShowOrNoShow,styler:addRedBoldForNo">学员Show</th>
				<th
					data-options="field:'isAttend',width:'50',sortable:true,formatter:formatShowOrNoShow,styler:addRedBoldForNo,unableGroupSearch:true">教师Show</th>
					<th
					data-options="field:'subscribeNoteType',width:'50',sortable:false,formatter:formatSubscribeNoteType,styler:addRedBold,unableGroupSearch:true">记录类型</th>
				<th
					data-options="field:'subscribeNote',width:'100',sortable:true,styler:addRedBold">记录</th>
				<th data-options="field:'roomId',width:'100',unableGroupSearch:true">房间ID</th>
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
			onclick="changeStudentShowStatus()">切换学员状态</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-edit"
			onclick="openSubscribeNoteDialog()">编辑记录</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-redo"
			onclick="gotoClassByAdmin(true)">进入教室</a>
			<a href="#"
			class="easyui-linkbutton" iconCls="icon-redo"
			onclick="gotoClassByAdmin(false)">生成教师URL</a>
			 <a href="#"
			class="easyui-linkbutton" iconCls="icon-redo"
			onclick="displayStudentUrl()">生成学员URL</a>
			<a  href = "#" 
			class="easyui-linkbutton" iconCls="icon-reload" onclick="openDialog()" >导出团训报表</a>
			 今天共<span id="courseCountDiv"
			name="courseCountDiv" style="font-weight: bold; color: red;"></span>节课
	</div>
	
	<div id="exportTuanxunExelDialog" name="exportTuanxunExelDialog"
		class="easyui-dialog" title="导出团训学员报表"
		style="width: 600px; height: 30%; padding: 10px;" modal="true"
		resizable="false" closed="true">
		<div style="margin: 10px 0px;">
			<span>请选择所查团训学员,开通合同的时间段</span>
			<form id="exportTuanxunExelFrom" action = "${ctx}/admin/subscribeCourse/downloadTuanxunInfo" method="post">
				<table style="width: 100%;"> 
					<tr>
						<td>
						开始时间:<input style="width: 30%;" id="startTime" type="text" name="startOrderTime" class="easyui-datetimebox" required="required"/>
					 	结束时间:<input style="width: 30%;" id="endTime" type="text" name="endOrderTime" class="easyui-datetimebox" required="required"/>
					 	</td>
					 	<td style="float: right;">
				         <a href="javascript:void(0)" onclick="exportTuanxunData()" class="easyui-linkbutton" iconCls="icon-ok">导出</a>  
				         <a href="javascript:void(0)" id="cancel" onclick="closeDialog()" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>  
					 	</td>
					</tr>	
			    </table>  
			</form>
			<span>-----------------------------------------------------------------------------------</span><br/>
			<br/>
			<br/>
			<span>按手机号查询学员RSA进度</span>
			<table style="width: 100%;"> 
				<tr>
					<td>
						<form id="gotoRsaForm" action = "${ctx}/" method="post">
							学员手机号:<input style="width: 60%;" id="rsaPhone" type="text" name="rsaPhone" class="easyui-textbox" required="required"/>
						</form>
					 </td>
				 	<td style="float: right;">
			         <a href="javascript:void(0)" onclick="gotoRsaByAdmin()" class="easyui-linkbutton" iconCls="icon-ok">查询</a>  
			         <a href="javascript:void(0)" id="cancel" onclick="closeDialog()" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>  
				 	</td>
				</tr>	
		    </table>  
		</div>
	</div>
           
           
                                          
	<!-- 查看学员详情 -->
	<div id="lookUserDetailDiv" name="lookUserDetailDiv"
		class="easyui-dialog" title="查看详情"
		style="width: 1024px; height: 500px; padding: 10px;" modal="true"
		resizable="false" closed="true" data-options="iconCls:'icon-edit'">
	</div>

	<!-- 显示URL -->
	<div id="showUrlDiv" name="showUrlDiv"
		class="easyui-dialog" title="URL"
		style="width: 250; height: auto; padding: 10px;" modal="true"
		resizable="false" closed="true" data-options="iconCls:'icon-edit'">
		<input class="easyui-textbox" id="urlShowInput" name="urlShowInput" 
		style="width:200px;height:100" data-options="editable:false,multiline:true,"/>
	</div>


	<!-- 新增编辑记录 -->
	<div id="addOrModifyNoteDialog" name="addOrModifyNoteDialog"
		class="easyui-dialog" title="记录课程"
		style="width: 30%; height: 40%; padding: 1%;" modal="true"
		resizable="false" closed="true"
		data-options="iconCls:'icon-edit',
    		buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                    	addOrModifySubscribeNote();
                    }
                }]
    		">
		<form id="addOrModifyNoteFrom" method="post"
			enctype="multipart/form-data">
			<input id="keyId" name="keyId" type="hidden" />

			<table cellpadding="5" style="width: 95%; height: 95%">
				<tr>
					<td style="width: 15%; height: 20%">记录人:</td>
					<td style="width: 80%; height: 20%">${sessionScope.adminSessionUser.adminUserName}</td>
				</tr>
				<tr>
					<td style="width: 15%; height: 20%">记录类型:</td>
					<td style="width: 80%; height: 20%"><select
						class="easyui-combobox" id="subscribeNoteType"
						name="subscribeNoteType" style="width: 151px;"
						data-options="panelHeight:'auto',editable:false">
							<option value="0">空</option>
							<option value="1">NS-学员失联</option>
							<option value="2">NS-学员个人原因</option>
							<option value="3">NS-学员设备故障</option>
							<option value="4">NS-老师设备故障</option>
							<option value="5">NS-老师未出席</option>
							<option value="6">老师迟到</option>
							<option value="7">平台故障</option>
							<option value="8">首课</option>
					</select></td>
				</tr>
				<tr>
					<td style="width: 15%; height: 80%">记录:</td>
					<td style="width: 80%; height: 80%"><input
						class="easyui-textbox" id="courseDesc" name="subscribeNote"
						style="width: 100%; height: 80%"
						data-options="multiline:true,required:false,validType:'length[0,500]'" />
					</td>
				</tr>
			</table>
		</form>
	</div>

	<!-- 组合查询 -->
	<c:import url="/WEB-INF/jsp/common/group_search_div.jsp" />
</body>
</html>