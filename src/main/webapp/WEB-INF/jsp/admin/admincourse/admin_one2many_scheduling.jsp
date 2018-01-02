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
<link rel="stylesheet" type="text/css" href="${ctx}/skin/plugins/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/skin/plugins/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css"
    href="${ctx}/skin/plugins/easyui/themes/input_file_common.css" />
<script type="text/javascript" src="${ctx}/skin/plugins/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/skin/plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/skin/plugins/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/common/easyui-common.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/common/jsonDate_common.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/admin/admincourse/admin_course_scheduling.js"></script>
<style>
td{
	font-size:12px;
}
</style>
</head>
<body>
	<table id="one2manySchedulingDataGrid" name="one2manySchedulingDataGrid" class="easyui-datagrid"
		fit="true" fitColumns="true"
		data-options="pageList:[100,200],pagination:true,pageSize:100,fit:true,singleSelect:true,
				rownumbers:true,url:'${ctx}/admin/courseOne2manyScheduling/findSchedulingList',
				toolbar:'#buttonGroup',onDblClickRow:function(rowIndex,rowData){dblClickRowListener(rowData);
				  },onSelect:function(index,row){}">
		<thead>
			<tr>
				<th data-options="field:'ids',checkbox:true"></th>
				<th data-options="field:'keyId',hidden:true"></th>
				<th data-options="field:'thirdFrom',hidden:true"></th>
				<th data-options="field:'teacherTimeId',hidden:true">keyId</th>
				<th data-options="field:'startTime',width:'100',sortable:false,formatter:formatDate">开始时间</th>
				<th data-options="field:'endTime',width:'100',sortable:false,formatter:formatDate">结束时间</th>
				<th data-options="field:'courseTypeName',width:'100',sortable:false">课程类型</th>
				<th data-options="field:'courseType',width:'100',sortable:false,hidden:true">hidden</th>
				<th data-options="field:'teacherName',width:'50',sortable:false">教师</th>
				<th data-options="field:'courseLevel',width:'100',sortable:false">级别</th>
				<th data-options="field:'courseTitle',width:'100',sortable:false">课程</th>
				<th data-options="field:'createDate',width:'50',sortable:false,formatter:formatDate">生成时间</th>
				<th data-options="field:'isSubscribe',width:'50',sortable:false,formatter:formatYesOrNo">是否已订</th>
				<th data-options="field:'isConfirm',width:'100',sortable:false,formatter:formatYesOrNo">确认出席</th>
				<th data-options="field:'teacherUrl',hidden:true">teacherUrl</th>
				<th data-options="field:'studentUrl',hidden:true">studentUrl</th>
			</tr>
		</thead>
	</table>
	<div id="buttonGroup" style="padding: 5px; height: auto">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addScheduling()">创建排课</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="huanxunAddScheduling()">环迅排课</a>
			<a href="#" class="easyui-linkbutton" id="deleteButton" iconCls="icon-remove" onclick="deleteScheduling()">删除排课</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="changeTeacherOneToMany()">更换老师</a>
	</div>
	
	<!-- 展示信息 -->
	<div id="schedulingInfoDialog" name="schedulingInfoDialog" class="easyui-dialog" title="排课信息"
		style="width: 400px; height: 550px; padding: 10px;" modal="true"
		resizable="false" closed="true"
		data-options="iconCls:'icon-edit',
    		buttons: [{
                    text:'返回',
                    iconCls:'icon-ok',
                    handler:function(){
                    	$('#schedulingInfoDialog').dialog('close');
                    }
                }]
    		">
    	<form id="one2manySchedulingInfoFrom" method="post">
			<table cellpadding="5">
				<tr>
					<td>开始时间:</td>
					<td>
						<input class="easyui-textbox" id="startTimeInfo" name="startTimeInfo" style="width:200px;" data-options="editable:false"/>
					</td>
				</tr>
				<tr>
					<td>结束时间:</td>
					<td>
						<input class="easyui-textbox" id="endTimeInfo" name="endTimeInfo" style="width:200px;" data-options="editable:false"/>
					</td>
				</tr>
				<tr>
					<td>课程类型:</td>
					<td>
						<input class="easyui-textbox" id="courseTypeInfo" name="courseTypeInfo" style="width:200px;" data-options="editable:false"/>
					</td>
				</tr>
				<tr>
					<td>教师:</td>
					<td>
						<input class="easyui-textbox" id="teacherNameInfo" name="teacherNameInfo" style="width:200px;" data-options="editable:false"/>
					</td>
				</tr>
				<tr>
					<td>级别:</td>
					<td>
						<input class="easyui-textbox" id="courseLevelInfo" name="courseLevelInfo" style="width:200px;" data-options="editable:false"/>
					</td>
				</tr>
				<tr>
					<td>课程:</td>
					<td>
						<input class="easyui-textbox" id="courseTitleInfo" name="courseTitleInfo" style="width:200px;" data-options="editable:false"/>
					</td>
				</tr>
				<tr>
					<td>生成时间:</td>
					<td>
						<input class="easyui-textbox" id="createDateInfo" name="createDateInfo" style="width:200px;" data-options="editable:false"/>
					</td>
				</tr>
				<tr>
					<td>是否已订:</td>
					<td>
						<input class="easyui-textbox" id="isSubscribeInfo" name="isSubscribeInfo" style="width:200px;" data-options="editable:false"/>
					</td>
				</tr>
				<tr>
					<td>确认出席:</td>
					<td>
						<input class="easyui-textbox" id="isConfirmInfo" name="isConfirmInfo" style="width:200px;" data-options="editable:false"/>
					</td>
				</tr>
				<tr>
					<td>教师URL:</td>
					<td>
						<input class="easyui-textbox" id="teacherUrlInfo" name="teacherUrlInfo" style="width:200px;height:50" data-options="editable:false,multiline:true,"/>
					</td>
				</tr>
				<tr>
					<td>学生URL:</td>
					<td>
						<input class="easyui-textbox" id="studentUrlInfo" name="studentUrlInfo" style="width:200px;height:50" data-options="editable:false,multiline:true,"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 新增排课表单 -->
	<div id="addSchedulingDialog" name="addSchedulingDialog" class="easyui-dialog" title="创建排课"
		style="width: 400px; height: 400px; padding: 10px;" modal="true"
		resizable="false" closed="true"
		data-options="iconCls:'icon-edit',
    		buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                    	submitAddScheduling();
                    }
                }]
    		">
    	<form id="addSchedulingFrom" method="post">
			<table cellpadding="5">
				<tr>
					<td>选择日期:</td>
					<td>
						<input id="schedulingDate" type="text" class="easyui-datebox" style="width:200px;" 
						data-options="required:true,onChange:function(newValue,oldValue){teacherDataLoader();}">
					</td>
				</tr>
				<tr>
					<td>开始时间:</td>
					<td>
						<select id="startHour" class="easyui-combobox" name="startHour" style="width:70px;"
							data-options="value:'',required:true,editable:false,onSelect:function(rec){},
							onChange:function(newValue,oldValue){teacherDataLoader();}"> 
						</select>
						时
						<select id="startMinute" class="easyui-combobox" name="startMinute" style="width:70px;"
							data-options="value:'',required:true,editable:false,onSelect:function(rec){},
							onChange:function(newValue,oldValue){teacherDataLoader();}"> 
						</select>
						分
					</td>
				</tr>
				<tr>
					<td>选择体系:</td>
					<td>
						<input id="keyId" name="keyId" type="hidden"/>
						<select id="categoryType" class="easyui-combobox" name="categoryType" style="width:200px;"
						data-options="value:'',required:true,editable:false,onSelect:function(rec){},
						onChange:function(newValue,oldValue){changeCategoryListener(newValue);},
							url:'${ctx}/admin/config/findCourseType?config_type=category',
							valueField:'config_name',textField:'config_value'"> 
						</select> 
					</td>
				</tr>
				<tr>
					<td>选择课程类型:</td>
					<td>
						<select id="courseType" class="easyui-combobox" name="courseType" style="width:200px;"
							data-options="value:'',required:true,editable:false,onSelect:function(rec){courseTypeOnSelectListener(rec);},
							onChange:function(newValue,oldValue){courseTypeChangeListener(newValue);},
							url:'${ctx}/courseType/findCourseTypeByParam?courseTypeFlag=2',
							valueField:'courseType',textField:'courseTypeChineseName'"> 
						</select>
					</td>
				</tr>
				<tr>
					<td>选择教师:</td>
					<td>
						<select id="teacherId" class="easyui-combobox" name="teacherId" style="width:200px;"
							data-options="value:'',required:true,editable:false,onSelect:function(rec){},
							onChange:function(newValue,oldValue){}"> 
						</select>
					</td>
				</tr>
				<tr id="platformTr">
					<td>选择上课平台:</td>
					<td>
						<select id="platform" class="easyui-combobox" name="platform" style="width:200px;"
							data-options="value:'1',required:true,editable:false,onSelect:function(rec){},
							onChange:function(newValue,oldValue){}"> 
							<option value="1">展示互动</option>
							<option value="2">Classin</option>
						</select>
					</td>
				</tr>
				<tr id="courseLevelTr">
					<td>选择级别:</td>
					<td>
						<select id="courseLevel" class="easyui-combobox" name="courseLevel" style="width:200px;"
							data-options="value:'',disabled:true,required:true,editable:false,onSelect:function(rec){},
							onChange:function(newValue,oldValue){courseDataLoader();}"> 
						</select>
					</td>
				</tr>
				<tr>
					<td>选择课程:</td>
					<td>
						<select id="courseId" class="easyui-combobox" name="courseId" style="width:200px;"
							data-options="value:'',required:true,editable:false,onSelect:function(rec){},
							onChange:function(newValue,oldValue){}"> 
						</select>
					</td>
				</tr>
				<tr id="teacherUrlTr">
					<td>教师URL:</td>
					<td>
						<input class="easyui-textbox" id="teacherUrl" name="teacherUrl" style="width:200px;height:50" data-options="multiline:true,required:true,validType:'length[1,240]'"/>
					</td>
				</tr>
				<tr id="studentUrlTr">
					<td>学生URL:</td>
					<td>
						<input class="easyui-textbox" id="studentUrl" name="studentUrl" style="width:200px;height:50" data-options="multiline:true,required:true,validType:'length[1,240]'"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	
	
    <!-- 新增环迅排课表单 -->
	<div id="huanxunAddSchedulingDialog" name="addSchedulingDialog" class="easyui-dialog" title="创建环迅排课"
		style="width: 400px; height: 400px; padding: 10px;" modal="true"
		resizable="false" closed="true"
		data-options="iconCls:'icon-edit',
    		buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                    	huanxunSubmitAddScheduling();
                    }
                }]
    		">
    	<form id="huanxunAddSchedulingFrom" method="post">
			<table cellpadding="5">
				<tr>
					<td>选择日期:</td>
					<td>
						<input id="huanxunSchedulingDate" type="text" class="easyui-datebox" style="width:200px;" 
						data-options="required:true,onChange:function(newValue,oldValue){teacherDataLoader();}">
					</td>
				</tr>
				<tr>
					<td>开始时间:</td>
					<td>
						<select id="huanxunStartHour" class="easyui-combobox" name="huanxunStartHour" style="width:70px;"
							data-options="value:'',required:true,editable:false,onSelect:function(rec){},
							onChange:function(newValue,oldValue){teacherDataLoader();}"> 
						</select>
						时
						<select id="huanxunStartMinute" class="easyui-combobox" name="huanxunStartMinute" style="width:70px;"
							data-options="value:'',required:true,editable:false,onSelect:function(rec){},
							onChange:function(newValue,oldValue){teacherDataLoader();}"> 
						</select>
						分
					</td>
				</tr>
				<tr>
					<td>选择体系:</td>
					<td>
						<input id="keyId" name="keyId" type="hidden"/>
						<select id="huanxunCategoryType" class="easyui-combobox" name="huanxunCategoryType" style="width:200px;"
						data-options="value:'',required:true,editable:false,
						onChange:function(newValue,oldValue){huanxunChangeCategoryListener(newValue);},
							url:'${ctx}/admin/config/findCourseType?config_type=category',
							valueField:'config_name',textField:'config_value'"> 
						</select> 
					</td>
				</tr>
				<tr>
					<td>选择课程类型:</td>
					<td>
						<select id="huanxunCourseType" class="easyui-combobox" name="huanxunCourseType" style="width:200px;"
							data-options="value:'',required:true,editable:false,onSelect:function(rec){huanxunCourseTypeOnSelectListener(rec);},
							onChange:function(newValue,oldValue){huanxunCourseTypeChangeListener(newValue);}"> 
							<option value="course_type2">extension1v6</option>
							<option value="course_type8">English Studio</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>选择教师:</td>
					<td>
						<select id="huanxunTeacherId" class="easyui-combobox" name="huanxunTeacherId" style="width:200px;"
							data-options="value:'',required:true,editable:false,
							url:'${ctx}/admin/teacher/findTeacherListByThirdFrom/huanxun',
							valueField:'keyId',textField:'account'
							"> 
						</select>
					</td>
				</tr>
				<tr id="huanxunPlatformTr">
					<td>选择上课平台:</td>
					<td>
						<select id="huanxunPlatform" class="easyui-combobox" name="huanxunPlatform" style="width:200px;"
							data-options="value:'1',required:true,editable:false,onSelect:function(rec){},
							onChange:function(newValue,oldValue){}"> 
							<option value="1">展示互动</option>
							<option value="2">Classin</option>
						</select>
					</td>
				</tr>
				<tr id="huanxunCourseLevelTr">
					<td>选择级别:</td>
					<td>
						<select id="huanxunCourseLevel" class="easyui-combobox" name="huanxunCourseLevel" style="width:200px;"
							data-options="value:'',disabled:true,required:true,editable:false,onSelect:function(rec){},
							onChange:function(newValue,oldValue){huanxunCourseDataLoader();}"> 
						</select>
					</td>
				</tr>
				<tr>
					<td>选择课程:</td>
					<td>
						<select id="huanxunCourseId" class="easyui-combobox" name="huanxunCourseId" style="width:200px;"
							data-options="value:'',required:true,editable:false,onSelect:function(rec){},
							onChange:function(newValue,oldValue){}"> 
						</select>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<!-- 更换老师 -->
	<div id="changeTeacherDialog" name="changeTeacherDialog" class="easyui-dialog" title="更换老师"
		style="width: 30%; height: 50%; padding: 10px;" modal="true"
		resizable="false" closed="true"
		data-options="iconCls:'icon-edit',
    		 buttons: [{
                    text:'保存',
                    iconCls:'icon-ok',
                    handler:function(){
                        changeTeacherSubmit('#one2manySchedulingDataGrid');
                    }
                },{
                    text:'取消',
                    iconCls:'icon-redo',
                    handler:function(){
                     $('#changeTeacherDialog').dialog('close');
                    }
                }] 
    		">
    	<form id="changeTeacherFrom" method="post">
		</form>
	</div>
	
</body>
</html>