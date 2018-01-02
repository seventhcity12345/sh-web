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
<script type="text/javascript" src="${ctx}/skin/js/admin/admincourse/admin_course_scheduling.js"></script>
<script type="text/javascript">
	gridName = 'one2oneSchedulingDataGrid';

	function addOne2oneScheduling(){
		$('#addSchedulingFrom').form('clear');
		$('#courseType').combobox('setValue', 'course_type1');
		$('#addSchedulingDialog').dialog('open');
	}
	
	//提交添加表单
	function submitAddOne2OneScheduling(){
		//获取当前日期
		var selectDate = $('#schedulingDate').datebox('getValue');
		if(diffDate(selectDate) == 1){
			 $.messager.alert('提示', "选择日期小于当前日期,请重新选择!");
		}
		//获得开始时间
		var startTime = getFallTime($('#schedulingDate').datebox('getValue'),
				$('#startHour').combobox('getValue'),$('#startMinute').combobox('getValue'));
		
		
		$('#addSchedulingFrom').form('submit', {
		    url: basePath + '/admin/courseOne2oneScheduling/addAdminCourseOne2oneScheduling',
		    onSubmit: function(data){
	    	    if($('#addSchedulingFrom').form('validate')){
                    $('#addSchedulingDialog').dialog('close');
    		    	data.startTime = startTime;
                    return true;
                }else{
                    return false;
                }
		    },
		    success:function(result) {
            	var res;
            	res = eval('(' + result + ')');
                $('#one2oneSchedulingDataGrid').datagrid('reload');
                if (result.success) {
                    $.messager.alert('提示', res.msg);
                } else {
                    $.messager.alert('提示', res.msg);
                }
         	}, 
         	error:function(status){
                $.messager.alert('提示','系统出现异常,请联系管理员','error'+status);
            }
		});
	}

//一键排课
function addOnekeyScheduling(){
    //选择日期下拉框
    $('#add_select_date').combobox({
		url: basePath + "/admin/courseOne2oneScheduling/getDateList",
			 valueField:'dateStr',
			 textField:'dateStr',
    });

    //打开一鍵排课浮层
    $('#onekeySchedulingDiv').dialog('open');
}
//提交一键排课效验
function submitOnekeySchedulingDiv(){
    $('#onekeySchedulingForm').form('submit',{
        onSubmit:function(){
            //easyui的校验
            if($(this).form('enableValidation').form('validate')){
                $('#onekeySchedulingDiv').dialog('close');
                return true;
            }else{
                return false;
            }
        },
        success:function(result) {
               result = eval("("+result+")");
               if (result.success) {
                   $('#one2oneSchedulingDataGrid').datagrid('reload');
                   $.messager.alert('提示', result.msg);
               } else {
                   $.messager.alert('提示', result.msg);
               }
        },
        error:function(status){
             $.messager.alert('提示','系统出现异常,请联系管理员','error'+status);
        }
    });
}
	
</script><style>
td{
	font-size:12px;
}
</style>
</head>
<body>
	<table id="one2oneSchedulingDataGrid" name="one2oneSchedulingDataGrid" class="easyui-datagrid"
		fit="true" fitColumns="true"
		data-options="pageList:[100,200],pagination:true,pageSize:100,fit:true,singleSelect:true,
				rownumbers:true,url:'${ctx}/admin/courseOne2oneScheduling/findSchedulingList',
				toolbar:'#buttonGroup',onDblClickRow:function(rowIndex,rowData){dblClickRowListener(rowData);
				  },onSelect:function(index,row){}">
		<thead>
			<tr>
				<th data-options="field:'ids',checkbox:true"></th>
				<th data-options="field:'keyId',hidden:true"></th>
				<th data-options="field:'thirdFrom',hidden:true"></th>
				<th data-options="field:'startTime',width:'50',sortable:false,formatter:formatDate">开始时间</th>
				<th data-options="field:'endTime',width:'50',sortable:false,formatter:formatDate">结束时间</th>
				<th data-options="field:'teacherCourseType',width:'100',sortable:false">课程类型</th>
				<th data-options="field:'teacherName',width:'50',sortable:false">教师</th>
				<th data-options="field:'isSubscribe',width:'100',sortable:false,formatter:formatYesOrNo">是否已订</th>
				<th data-options="field:'isConfirm',width:'100',sortable:false,formatter:formatYesOrNo">确认出席</th>
			</tr>
		</thead>
	</table>
	<div id="buttonGroup" style="padding: 5px; height: auto">
			<a href="#" class="easyui-linkbutton" id="batchButton" iconCls="icon-add" onclick="addOnekeyScheduling()">一键排课</a>
			<a href="#" class="easyui-linkbutton" id="addButton" iconCls="icon-add" onclick="addOne2oneScheduling()">添加排课</a>
			<a href="#" class="easyui-linkbutton" id="findButton" iconCls="icon-search" onclick="groupSearch()">组合查询</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="changeTeacherOneToOne()">更换老师</a>
	</div>
<!--一键排课 -->
    <div id="onekeySchedulingDiv" name="onekeySchedulingDiv" class="easyui-dialog"
        title="一键排课" style="width: 350px; height: 300px; padding: 30px;"
        modal="true" resizable="false" closed="true"
        data-options="iconCls:'icon-edit',
            buttons: [{
                    text:'一键排课',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitOnekeySchedulingDiv();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-redo',
                    handler:function(){
                     $('#onekeySchedulingDiv').dialog('close');
                    }
                }] 
        ">

        <form id="onekeySchedulingForm" enctype="multipart/form-data" 
        action="${ctx }/admin/courseOne2oneScheduling/addOnekeyCourseOne2oneScheduling"
            method="post">
            <table cellpadding="5">
                <tr>
                    <td>选择日期</td>
                    <td><input class="easyui-combobox" name="selectDate"
                        id="add_select_date"
                        data-options="required:true,editable:false,panelHeight:'160'" />
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <!--一键排课-->

<div id="addSchedulingDialog" name="addSchedulingDialog" class="easyui-dialog" title="创建排课"
		style="width: 400px; height: 300px; padding: 10px;" modal="true"
		resizable="false" closed="true"
		data-options="iconCls:'icon-edit',
    		buttons: [{
                    text:'提交',
                    iconCls:'icon-ok',
                    handler:function(){
                    	submitAddOne2OneScheduling();
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
				<tr style="display:none">
					<td>选择课程类型:</td>
					<td>
						<select id="courseType" class="easyui-combobox" name="courseType" style="width:200px;"
							data-options="value:'course_type1',required:true,editable:false,onSelect:function(rec){}"> 
							<option value="course_type1">course_type1</option>
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
			</table>
		</form>
		<c:import url="/WEB-INF/jsp/common/group_search_div.jsp" />
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
                        changeTeacherSubmit('#one2oneSchedulingDataGrid');
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