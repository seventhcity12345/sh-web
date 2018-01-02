<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
  response.setHeader("Cache-Control", "no-store");
  response.setHeader("Pragrma", "no-cache");
  response.setDateHeader("Expires", 0);
%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>TeacherManager</title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/skin/plugins/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/skin/plugins/easyui/themes/icon.css" />
<!--  <link rel="stylesheet" type="text/css"
    href="${ctx}/skin/plugins/easyui/themes/input_file_common.css" />-->


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
<script type="text/javascript" src="${ctx}/skin/js/common/filetype.js"></script>
<script type="text/javascript"
	src="${ctx}/skin/js/admin/teacher/admin_teacher.js"></script>

<style>
td {
	font-size: 12px;
}

th {
	font-size: 12px;
}
</style>

</head>
<body>
	<table id="dataGrid" name="dataGrid" class="easyui-datagrid" fit="true"
		fitColumns="true" nowrap="false"
		data-options="pageList:[20,40,60,80,100],pagination:true,pageSize:20,fit:true,
    rownumbers:true,url:'${ctx}/admin/teacher/pageList',ctrlSelect:true,
    toolbar:'#toolbar' , onDblClickRow :function(rowIndex,rowData){
         lookTeacherDetail(rowIndex, rowData);}">
		<thead>
			<tr>
				<th data-options="field:'ids',checkbox:true"></th>
				<th
					data-options="field:'keyId',width:'100',sortable:true,hidden:true">key_id</th>
				<th
					data-options="field:'teacherCourseTypeId',width:'100',sortable:true,hidden:true">teacherCourseTypeId</th>
				<th
					data-options="field:'isBindWechat',width:'30',formatter:formatYesOrNo">老师是否绑定微信</th>
				<th
					data-options="field:'account',width:'100',sortable:true,hidden:true">账号</th>
				<th data-options="field:'pwd',width:'100',sortable:true,hidden:true">密码</th>
				<th data-options="field:'teacherName',width:'50',sortable:true">教师名称</th>
				<th
					data-options="field:'teacherGender',width:'10',sortable:true,formatter:formatSex">性别</th>
				<th data-options="field:'thirdFrom',width:'30',sortable:true">来源渠道</th>
				<th data-options="field:'teacherPhoto',formatter:formatterImg">头像</th>
				<th
					data-options="field:'createDate',width:'30',sortable:true,formatter:formatDateDay">创建时间</th>
				<th
					data-options="field:'updateDate',width:'30',sortable:true,formatter:formatDateDay">最后修改时间</th>
				<th
					data-options="field:'teacherNationality',width:'30',sortable:true">国籍</th>
				<th data-options="field:'teacherDesc',width:'30',sortable:true">教师简介</th>
				<th data-options="field:'teacherContactContent',width:'50'">联系方式</th>
				<th
					data-options="field:'teacherJobType',width:'15',sortable:true,formatter:formatterJobType">工作性质</th>
				<th
					data-options="field:'teacherCourseType',width:'50',sortable:true">上课权限</th>
			</tr>
		</thead>
	</table>

	<!-- toolbar -->
	<div id="toolbar" style="padding: 5px; height: auto">
		<div style="margin-bottom: 5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add"
				onclick="addNewTeacher()">新增老师</a> <a href="#"
				class="easyui-linkbutton" iconCls="icon-edit"
				onclick="editTeacher()">编辑老师</a> <a href="#"
				class="easyui-linkbutton" iconCls="icon-remove"
				onclick="deleteTeacher()">删除老师</a>
				<a href="#"
				class="easyui-linkbutton" iconCls="icon-edit"
				onclick="adminLogin()">进入教师平台</a>
			<!--
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="showDefaultDetailDiv('user/detail')">查看</a>  
            -->
		</div>
	</div>

	<!-- 新增教师 -->
	<div id="addTeacherDiv" name="addTeacherDiv" class="easyui-dialog"
		title="新增老师" style="width: 600px; height: 80%; padding: 30px;"
		modal="true" resizable="false" closed="true"
		data-options="iconCls:'icon-edit',
            buttons: [{
                    text:'新增老师',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitAddTeacherDiv();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-redo',
                    handler:function(){
                     $('#addTeacherDiv').dialog('close');
                    }
                }] 
        ">

		<form id="addTeacherForm" enctype="multipart/form-data"
			action="${ctx }/admin/teacher/addTeacher" method="post">
			<table cellpadding="5">
				<tr>
					<th>*账号不可重复</th>
					<th>*密码默认为111111</th>
				</tr>
				<tr>
					<td>老师账号</td>
					<td><input class="easyui-textbox" name="account"
						id="add_account"
						data-options="required:true,validType:'length[1,20]'" /></td>
				</tr>
				<tr>
					<td>老师姓名</td>
					<td><input class="easyui-textbox" name="teacherName"
						id="add_teacher_name"
						data-options="required:true,validType:'length[1,200]'" /></td>
				</tr>
				<tr>
					<td>性别</td>
					<td><input class="easyui-combobox" name="teacherGender"
						id="add_teacher_gender"
						data-options="required:true,editable:false,panelHeight:'auto'" /></td>
				</tr>
				<tr>
					<td>来源渠道</td>
					<td><input class="easyui-combobox" name="thirdFrom"
						id="add_third_from"
						data-options="required:true,editable:false,panelHeight:'auto'" />
					</td>
				</tr>
				<tr>
					<td>国籍</td>
					<td><input class="easyui-combobox" name="teacherNationality"
						id="add_teacher_nationality"
						data-options="required:true,editable:false,panelHeight:'160'" /></td>
				</tr>
				<tr>
					<td>教师简介:</td>
					<td><input class="easyui-textbox" id="addTeacherDesc"
						name="teacherDesc" style="width: 175px; height: 50"
						data-options="multiline:true,required:false,validType:'length[0,300]'" />
					</td>
				</tr>
				<tr>
					<td>联系方式</td>
					<td><input class="easyui-textbox" name="teacherContactContent"
						id="add_teacher_contact_content" data-options="" /></td>
				</tr>
				<tr>
					<td>工作性质</td>
					<td><input class="easyui-combobox" name="teacherJobType"
						id="add_teacher_job_type"
						data-options="required:true,editable:false,panelHeight:'auto'" />
					</td>
				</tr>
				<tr>
					<td>上课类型</td>
					<td><input class="easyui-combobox" name=teacherCourseType
						id="add_teacher_course_type"
						data-options="required:true,editable:false,panelHeight:'auto',
                        url:'${ctx}/courseType/findCourseTypeByParam',
                        valueField:'courseType',textField:'courseTypeChineseName'" />
					</td>
				</tr>
				<tr>
					<td>老师照片</td>
					<td><input class="easyui-filebox" name="fileId" id="fileId1"
						data-options="required:true,editable:false,prompt:'选择照片...', buttonText: '上传照片'">
					</td>
				</tr>
				<tr>
					<td>是否绑定微信推送</td>
					<td><input class="easyui-textbox" name="teacherIsBind"
						id="addTeacherIsBind" value="否" data-options="editable:false,disabled:true" /></td>
					<td><a href="#" class="easyui-linkbutton" iconCls="icon-add"
						onclick="generateWechatUrl()">生成绑定链接</a></td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 新增教师-->

	<!-- 编辑教师 -->
	<div id="editTeacherDiv" name="editTeacherDiv" class="easyui-dialog"
		title="编辑老师信息" style="width: 600px; height: 80%; padding: 30px;"
		modal="true" resizable="false" closed="true"
		data-options="iconCls:'icon-edit',
            buttons: [{
                    text:'编辑',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitEditTeacherDiv();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-redo',
                    handler:function(){
                     $('#editTeacherDiv').dialog('close');
                    }
                }] 
        ">

		<form id="editTeacherForm" enctype="multipart/form-data"
			action="${ctx }/admin/teacher/editTeacher" method="post">
			<table cellpadding="5">
				<tr>
					<td>老师姓名</td>
					<td><input type="hidden" name="keyId" id="edit_key_id" /> <input
						class="easyui-textbox" name="teacherName" id="edit_teacher_name"
						data-options="required:true,validType:'length[1,200]'" /></td>
				</tr>
				<tr>
					<td>性别</td>
					<td><input class="easyui-combobox" name="teacherGender"
						id="edit_teacher_gender"
						data-options="required:true,editable:false,panelHeight:'auto'" /></td>
				</tr>
				<tr>
					<td>来源渠道</td>
					<td><input class="easyui-combobox" name="thirdFrom"
						id="edit_third_from"
						data-options="required:true,editable:false,panelHeight:'auto'" />
					</td>
				</tr>
				<tr>
					<td>国籍</td>
					<td><input class="easyui-combobox" name="teacherNationality"
						id="edit_teacher_nationality"
						data-options="required:true,editable:false,panelHeight:'160'" /></td>
				</tr>
				<tr>
					<td>教师简介:</td>
					<td><input class="easyui-textbox" id="editTeacherDesc"
						name="teacherDesc" style="width: 175px; height: 50"
						data-options="multiline:true,required:false,validType:'length[0,300]'" />
					</td>
				</tr>
				<tr>
					<td>联系方式</td>
					<td><input class="easyui-textbox" name="teacherContactContent"
						id="edit_teacher_contact_content" data-options="" /></td>
				</tr>
				<tr>
					<td>工作性质</td>
					<td><input class="easyui-combobox" name="teacherJobType"
						id="edit_teacher_job_type"
						data-options="required:true,editable:false,panelHeight:'auto'" />
					</td>
				</tr>
				<tr>
					<td>上课类型</td>
					<td><input class="easyui-combobox" name=teacherCourseType
						id="edit_teacher_course_type"
						data-options="required:true,editable:false,panelHeight:'auto',
                        url:'${ctx}/courseType/findCourseTypeByParam',
                        valueField:'courseType',textField:'courseTypeChineseName'" />
					</td>
				</tr>
				<tr>
					<td>老师照片</td>
					<td><input type="hidden" name="teacherPhoto"
						id="edit_teacher_photo" /> <!--<input class="easyui-textbox" name=teacherPhoto
                        id="edit_teacher_photo"
                        data-options="required:true,editable:false" />
                           <a href="javascript:;" class="a-upload">
						<input type="file" name="fileId" id="fileId2" accept="image/jpg,image/jpeg,image/png,image/gif" onchange="updatePhoto()">上传照片
						</a>--> <input class="easyui-filebox" name="fileId" id="fileId2"
						data-options="editable:false,prompt:'选择照片...', buttonText: '上传照片'">
					</td>
				</tr>
				<tr>
					<td>是否绑定微信推送</td>
					<td><input class="easyui-textbox" name="teacherIsBind"
						id="editTeacherIsBind" value="否" data-options="editable:false,disabled:true" /></td>
					<td><a href="#" class="easyui-linkbutton" iconCls="icon-add"
						onclick="generateWechatUrl()">生成绑定链接</a></td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 编辑教师-->



	<!-- 查看教师详情 -->
	<div id="lookTeacherDetailDiv" name="lookTeacherDetailDiv"
		class="easyui-dialog" title="查看详情"
		style="width: 1024px; height: 500px; padding: 10px;" modal="true"
		resizable="false" closed="true" data-options="iconCls:'icon-edit'">
	</div>
	<!-- 查看教师详情 -->

	<!-- 微信绑定链接 -->
	<div id="wechatBindDiv" name="wechatBindDiv" class="easyui-dialog"
		title="微信绑定链接" style="width: 600px; height: 80%; padding: 20px;"
		modal="true" resizable="false" closed="true"
		data-options="iconCls:'icon-add'
        ">
		<table cellpadding="5">
			<tr>
				<td id="wechatBindUrl">绑定链接：</td>
			</tr>
			<tr>
				<td style="color: red">提醒老师:</td>
			</tr>
			<tr>
				<td style="color: red">1、用老师微信号关注我们的服务号“韦博嗨英语”</td>
			</tr>
			<tr>
				<td style="color: red">2、复制以上链接，发送至老师微信</td>
			</tr>
			<tr>
				<td style="color: red">3、提醒老师微信中打开该链接，并绑定“SpeakHi教师账号”</td>
			</tr>
			<tr>
				<td style="color: red">注：不要输错，否则老师将无法收到上课提醒</td>
			</tr>
		</table>
	</div>

</body>
</html>