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
	src="${ctx}/skin/js/admin/admincourse/admin_course.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/common/filetype.js"></script>
<script type="text/javascript"
	src="${ctx}/skin/js/common/jsonDate_common.js"></script>
<script type="text/javascript">
	gridName = 'dataGrid1';
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
		data-options="pageList:[20,40,60,80,100],pagination:true,pageSize:20,fit:true,singleSelect:true,
				rownumbers:true,url:'${ctx}/admin/course/findAllCourseList',
				toolbar:'#buttonGroup',onSelect:function(index,row){deleteSelectListener(index,row);}"
		nowrap="false">
		<thead>
			<tr>
				<th data-options="field:'ids',checkbox:true"></th>
				
				<th data-options="field:'keyId',hidden:true">keyId</th>
				<th data-options="field:'categoryTypeId',hidden:true">categoryTypeId</th>
				<th data-options="field:'courseTypeId',hidden:true">courseTypeId</th>
				<th data-options="field:'documentId',hidden:true">documentId</th>

				<th
					data-options="field:'categoryType',width:'50',sortable:true">课程体系</th>
				<th data-options="field:'courseTitle',width:'100',sortable:true">课程名称</th>
				<th
					data-options="field:'coursePic',width:'50',formatter:formatterImg,unableGroupSearch:true">课程图片</th>
				<th
					data-options="field:'courseCourseware',width:'50',formatter:fileTypeFormat,unableGroupSearch:true">课程教材</th>
				<th
					data-options="field:'courseLevel',width:'100',sortable:true">课程级别</th>
				<th data-options="field:'courseType',width:'100',sortable:true">课程类型</th>
				<th data-options="field:'courseDesc',width:'100'">课程简介</th>
			</tr>
		</thead>
	</table>
	<div id="buttonGroup" style="padding: 5px; height: auto">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addCourse()">添加课程</a>
		<a href="#" class="easyui-linkbutton" id="deleteButton" iconCls="icon-remove" onclick="deleteCourse()">删除课程</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="modifyCourse()">修改课程</a>
		<a href="#" class="easyui-linkbutton" id="findButton" iconCls="icon-search" onclick="groupSearch()">组合查询</a>
	</div>

	<!-- 添加修改用户 -->
	<div id="addOrModifyCourse" name="addOrModifyCourse"
		class="easyui-dialog" title="添加课程"
		style="width: 400px; height: 500px; padding: 10px;" modal="true"
		resizable="false" closed="true"
		data-options="iconCls:'icon-edit',
    		buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                    	addOrModifyCourse();
                    }
                }]
    		">
		<form id="courseform" method="post" enctype="multipart/form-data">
			<input id="coursePic" name="coursePic" type="hidden" /> 
			<input id="courseCourseware" name="courseCourseware" type="hidden" /> 
			<input id="keyId" name="keyId" type="hidden" /> 
			<input id="documentId" name="documentId" type="hidden" />

			<table cellpadding="5">
				<tr>
					<td>课程体系:</td>
					<td><select id="categoryType" class="easyui-combobox"
						name="categoryType" style="width: 200px;"
						data-options="value:'',required:true,editable:false">
							<option value="category_type1">商务英语</option>
							<option value="category_type2">通用英语</option>
					</select></td>
				</tr>
				<tr>
					<td>课程名称:</td>
					<td><input class="easyui-textbox" id="courseTitle"
						name="courseTitle" style="width: 200px;"
						data-options="required:true,validType:'length[0,200]'" /></td>
				</tr>
				<tr>
					<td>课程类型:</td>
					<td>
						<!-- value:'' 为了当加载表单时能触发onChange方法 --> <select id="courseType"
						class="easyui-combobox" name="courseType" style="width: 200px;"
						data-options="value:'',required:true,editable:false,
						onSelect:function(rec){courseTypeOnSelectListener(rec);},
						url:'${ctx}/courseType/findCourseTypeByParam?courseTypeFlag=2',
							valueField:'courseType',textField:'courseTypeChineseName'">
					</select>
					</td>
				</tr>
				<tr id="courseLevelTr">
					<td>课程级别:</td>
					<td><input class="easyui-textbox" id="courseLevel"
						name="courseLevel" style="width: 200px;"
						data-options="editable:false,required:true" /> <a href="#"
						class="easyui-linkbutton" iconCls="" onclick="chooseLevel()">选择级别</a>
					</td>
				</tr>
				<tr>
					<td>课程图片:</td>
					<td><input class="easyui-filebox" id="coursePicFile"
						name="coursePicFile" style="width: 200px;"
						data-options="required:true,buttonText:'选择文件'
						,missingMessage:'图片不能为空'" />
					</td>
				</tr>
				<tr>
					<td>课程教材:</td>
					<td><input class="easyui-filebox" id="courseCoursewareFile"
						name="courseCoursewareFile" style="width: 200px;"
						data-options="buttonText:'选择文件'" /></td>
				</tr>
				<tr>
					<td>课程简介:</td>
					<td><input class="easyui-textbox" id="courseDesc"
						name="courseDesc" style="width: 200px; height: 50"
						data-options="multiline:true,required:false,validType:'length[0,250]'" />
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="chooseLevelDialog" name="chooseLevelDialog"
		class="easyui-dialog" title="选择级别"
		style="width: 400px; height: 500px; padding: 10px;" modal="true"
		resizable="false" closed="true"
		data-options="iconCls:'icon-edit',
    		buttons: [{
                    text:'确定',
                    iconCls:'icon-ok',
                    handler:function(){
                    	selectLevel();
                    }
                }] 
    		">
		<table class="easyui-datagrid" id="chooseLevelGrid"
			name="chooseLevelGrid" data-options="ctrlSelect:true,fitColumns:true">
			<thead>
				<tr>
					<th data-options="field:'ids',checkbox:true"></th>
					<th data-options="field:'courseLevel'">课程级别</th>
				</tr>
			</thead>
		</table>
	</div>
	<!-- 组合查询 -->
	<c:import url="/WEB-INF/jsp/common/group_search_div.jsp" />
</body>
</html>