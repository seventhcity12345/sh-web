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

<script type="text/javascript" src="${ctx}/skin/plugins/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/skin/plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/skin/plugins/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/common/easyui-common.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/admin/user/admin_user_detail.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/admin/user/admin_user_detail_order.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/admin/user/admin_user_level.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/admin/user/admin_user_demo.js"></script><!-- add by felix.yl -->
<script type="text/javascript">
	//日期格式化(yyyy-MM-dd)
	function formatDate(val,rec){
		 if(val==null || val==''){
		  return '';
		 }
		 return new Date(val).format("yyyy-MM-dd");
	}
	
	//日期格式化(yyyy-MM-dd hh:mm)
	function formatDate2(val,rec){
		 if(val==null || val==''){
		  return '';
		 }
		 return new Date(val).format("yyyy-MM-dd hh:mm");
	}
	
	//查看所有的学员，填充到本页面中的dataGrid中
	function findAllStudent(){
		$('#dataGrid1').datagrid('options').url=basePath+'/admin/user/findAllStudent';
		var queryParams = {};    
		$('#dataGrid1').datagrid('load',queryParams);
	}
	
	//更换LC
	function editChangeLC(){
		var row = $('#dataGrid1').datagrid('getSelections');
		if(row.length < 1){
			$.messager.alert('提示','请先选中至少一条数据！');
			return;
		}
		//user_id的字符串集合
		var user_ids = "";
		for (var i = 0; i < row.length; i++) {
			if (i == row.length - 1) {
				user_ids = user_ids + row[i].user_id;
				break;
			}
			user_ids = user_ids + row[i].user_id + ",";
		}
		$('#user_id4ChangeLC').val(user_ids);
		//modify by komi 2016年5月23日16:10:51 修改教务只显示教务角色，如果学员原来“教务”是其他角色，显示空白
		var learningCoachId = "";
		for(var i = 0; i<$('#learning_coach_id').combobox('getData').length;i++){
			if(row[0].learning_coach_id == $('#learning_coach_id').combobox('getData')[i].learning_coach_id){
				learningCoachId = $('#learning_coach_id').combobox('getData')[i].learning_coach_id;
				break;
			}
		}
		$('#learning_coach_id').combobox('select', learningCoachId);
		//modify end
		$('#editChangeLCDiv').dialog('open');
	}

	//form 提交 修改LC数据
	function submitEditChangeLC() {
		$('#editChangeLCForm').form('submit', {
			onSubmit : function() {
				//easyui的校验
				if ($(this).form('enableValidation').form('validate')) {
					$('#editChangeLCDiv').dialog('close');
					return true;
				} else {
					return false;
				}
			},
			success : function() {
				$.messager.alert("提示", "修改成功！");
				$('#dataGrid1').datagrid('reload');
			},
			error : function(status) {
				$.messager.alert('提示', '系统出现异常,请联系管理员', 'error' + status);
			}
		});
	}
	
	// 遇到分号换行
	function packageNameFormatter(data){
		if(data != null){
			return data.replace(/;/g, ";<br/>");
		}
		return "";
	}
	
	//更换LC
	function findRsaLearningProgress(){
		var row = $('#dataGrid1').datagrid('getSelections');
		if(row.length != 1){
			$.messager.alert('提示','请先选中一条数据！');
			return;
		}
		var userId = row[0].user_id;
		$('#rsaLearningProgressDialog').dialog('open');
		$('#rsaLearningProgressGrid')
		.datagrid(
				{
					url : basePath
							+ '/admin/user/findRsaLearningProgress/' + userId
				});
	}
	
	//是否学够了30 分钟
	function ifUpToTheStandardFormater(value,row,index){
		if(row.changeTmmWorkingtime >= 30){
			return '是';
		} else {
			return '';
		}
	}
</script>

</head>
<body>

	<table id="dataGrid1" name="dataGrid1" class="easyui-datagrid" fit="true"
	fitColumns="true" data-options="pageList:[20,40,60,80,100],pagination:true,pageSize:100,fit:true,
	rownumbers:true,url:'${ctx}/admin/user/studentPageList',ctrlSelect:true,toolbar:'#toolbar',onDblClickRow :function(rowIndex,rowData){
 		lookUserDetail(rowIndex, rowData);
 	} " > 
		<thead>
			<tr >
			<th data-options="field:'ids',checkbox:true"></th>
			<th data-options="field:'user_id',hidden:true">user_id</th>
			<th data-options="field:'learning_coach_id',hidden:true">教务id</th>
			<th data-options="field:'user_code',width:'50',sortable:true">ID</th>
			<th data-options="field:'real_name',width:'100',sortable:true">真实姓名</th>
			<th data-options="field:'english_name',width:'100',sortable:true">英文姓名</th>
			<th data-options="field:'phone',width:'100',sortable:true">手机号</th>
			<th data-options="field:'current_level',width:'100',sortable:true">当前级别</th>
			<th data-options="field:'course_package_names',width:'100',sortable:false,unableGroupSearch:true,formatter:packageNameFormatter">课程包名</th>
			<th data-options="field:'account',width:'100',sortable:true">LC</th>
			<th data-options="field:'one2OneStandardCount',width:'100',sortable:false,unableGroupSearch:true">1v1(实际/标准)</th>
			<th data-options="field:'one2ManyStandardCount',width:'100',sortable:false,unableGroupSearch:true">1vn(实际/标准)</th>
			<th data-options="field:'isActivity',width:'100',sortable:false,formatter:formatYesOrNo,unableGroupSearch:true">是否达标</th>
			<th data-options="field:'last_followup_time',width:'100',sortable:false,formatter:formatDate,unableGroupSearch:true">最近follow-up时间</th>
			<th data-options="field:'last_access_time',width:'100',sortable:false,formatter:formatDate,unableGroupSearch:true">最近出席课程时间</th>
			<th data-options="field:'this_month_course_count',width:'100',sortable:false,unableGroupSearch:true">本月上课数量</th>
			</tr>
		</thead>
	</table>
	
	<!-- bar声明 -->
	<div id="toolbar" style="padding: 5px; height: auto">
		<div style="margin-bottom: 5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="editChangeLC()">更换LC</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="editUserLevel()">修改级别</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="findAllStudent()">全部学员</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="groupSearch()">组合查询</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="openDemoDialog()">预约demo</a><!-- add by felix.yl -->
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findRsaLearningProgress()">课件进度</a>
		</div>
	</div>
	
	<!-- 编辑级别窗口 -->
	<div id="editChangeLCDiv" name="editChangeLCDiv" class="easyui-dialog" title="更换LC"
		style="width: 350px; height: 180px; padding: 30px;" modal="true"
		resizable="false" closed="true"
		data-options="iconCls:'icon-edit',
    		buttons: [{
                    text:'更换LC',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitEditChangeLC();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-redo',
                    handler:function(){
                     $('#editChangeLCDiv').dialog('close');
                    }
                }] 
    	">
		<!-- 编辑table-->
		<form action="${ctx }/admin/user/batchUpdateUserLC" id="editChangeLCForm" method="post">
			<table cellpadding="5">
					<tr>
						<input type="hidden" name="user_id" id="user_id4ChangeLC" />
						<td>LC名称</td>
						<td>
							<input class="easyui-combobox" name="learning_coach_id" id="learning_coach_id" data-options="
							      url:'${ctx }/admin/user/findAdminUserListByLc',
							      editable:false,
							      valueField:'keyId',
							      textField:'account',
							      required:true,
							      panelHeight:'88px'"/>
						</td>
					</tr>
			</table>
		</form>
		<!-- 编辑table-->
	</div>
	
	<!-- 编辑级别窗口 -->
	<div id="editCurrentLevelDiv" name="editCurrentLevelDiv" class="easyui-dialog" title="编辑级别"
		style="width: 300px; height: 150px; padding: 10px;" modal="true"
		resizable="false" closed="true"
		data-options="iconCls:'icon-edit',
    		buttons: [{
                    text:'修改级别',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitEditLevel();
                    }
                }] 
    		">
			
			<table cellpadding="5">
				<tr>
					<td>当前级别:</td>
					<td>
						<input class="easyui-combobox" id="edit_current_level" name="edit_current_level" data-options="editable:false,required:true"/>
					</td>
				</tr>
			</table>
	</div>
	
	<!-- 查看学员详情 -->
    <div id="lookUserDetailDiv" name="lookUserDetailDiv" class="easyui-dialog" title="查看详情"
		style="width: 1024px; height: 500px; padding: 10px;" modal="true"
		resizable="false" closed="true"
		data-options="iconCls:'icon-edit'">
    </div>
    <!-- 查看学员详情 -->
    
    <!-- demo待订课  -->
	<!-- 预约你就把这里当成是一个空的容器就完了，为的就是打开一个dialog -->
    <div id="demoSubscribeDialog" name="demoSubscribeDialog" class="easyui-dialog" 
    	title="预约demo课" closed="true"
		style="width: 1024px; height: 500px;" 
		data-options="iconCls:'icon-search'">
 
		<!-- 查询出来的数据 显示位置 -->
		<table id="demoGridSubscribeCourse" name="demoGridSubscribeCourse" class="easyui-datagrid" fit="true"
		fitColumns="true" data-options="pagination:false,singleSelect:true,
		rownumbers:true,ctrlSelect:false,toolbar:'#toolbarDemoSubscribeCourse',
	    		onLoadSuccess:function(data){gridOnloadListener();}" > 
			<thead>
				<tr >
					<th data-options="field:'ids',checkbox:true"></th>
					
					<th data-options="field:'keyId',width:'100',hidden:true">teacher_time_id</th>
					<th data-options="field:'courseType',width:'100',hidden:true">courseType</th>
					<th data-options="field:'teacherName',width:'100'">上课老师</th>
					<th data-options="field:'teacherNationality',width:'100',formatter:teacherNationalityFormater"">老师类型</th>
					<th data-options="field:'thirdFrom',width:'100'">来源渠道</th>
					<th data-options="field:'startTime',width:'100',formatter:formatDate2">上课时间</th>
					<th data-options="field:'endTime',width:'100',formatter:formatDate2">结束时间</th>
				</tr>
			</thead>
		</table>
		<!-- bar声明 -->
		<div id="toolbarDemoSubscribeCourse" style="padding: 5px; height: auto">
			<div style="margin-bottom: 5px">
				<input class="easyui-combobox" name="courseType" id="courseType" 
						data-options="onSelect:function(rec){
							selectListener(rec);
						}, 
					      	editable:false,
					      	required:true,
					      	panelHeight:'auto',
					      	data: [{
								text: 'demo1v1',
								value: 'course_type4'
							},{
								text: '纠音1v1',
								value: 'course_type13'
							}]"/>
				<input class="easyui-combobox" name="demoCourse" id="demoCourse" 
						data-options="required:true,editable:false"/>
						
				<!-- 时间日期框 -->
				<input class="easyui-datebox" name="dateTime" id="dateTime" 
						data-options="editable:false"/>
						
				<input id="webexRoomHostId" class="easyui-combobox" name="webexRoomHostId" style="width:150px;"
						data-options="value:'',required:true,editable:false,
							onSelect:function(rec){findTeacherTime();},
							url:'${ctx}/admin/webex/findListDemoRoom',
							valueField:'webexRoomHostId',textField:'webexRoomName',panelHeight:'auto'"/> 
				
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findTeacherTime()">查询</a>		
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="userSubscribePreconditionByStudent()">预约demo课</a>
			</div>
		</div>
	</div>
	
	<!-- demo课输入房间号会议号 -->
	<div id="demoInputRoomIdDialog" name="demoInputRoomIdDialog" class="easyui-dialog" title="请选择Demo课房间和Demo课类型"
		style="width: 35%; height: 50%; padding: 10px;" modal="true"
		resizable="false" closed="true"
		data-options="iconCls:'icon-add',
    		buttons: [{
                    text:'预约',
                    iconCls:'icon-ok',
                    handler:function(){
                    	userSubscribeDemoCourseByStudent();
                    }
                },
                {
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                       $('#demoInputRoomIdDialog').dialog('close');
                    }
                }] 
    		">
    		<span style="font-weight:bold;color:red;font-size: 16px;">选择不同的Demo类型，将生成不同的Demo报告，注意不要选错<br/><br/></span>
    		
			<table class="demoTable" cellpadding="5">
				<tr>
					<td>WebEx房间:</td>
					<td colspan="2"><input class="easyui-textbox" id="webexRoomHostIdText"
						name="webexRoomHostIdText" style="width: 200px;"
						data-options="disabled:true,required:true" />
					</td>
				</tr>
				<tr>
					<td>会议号:</td>
					<td colspan="2"><input class="easyui-textbox" id="webexMeetingKeyText"
						name="webexMeetingKeyText" style="width: 200px;"
						data-options="disabled:true,required:true" />
					</td>
				</tr>
				<tr>
					<td>Demo类型:</td>
					<td colspan="2">
						<input type="radio"  name="subscribeType" value="1" checked/>青少Demo
						<input type="radio" name="subscribeType" value="0" />成人Demo
					</td>
				</tr>
				<tr>
					<td>备注:</td>
					<td colspan="2"><input class="easyui-textbox" id="subscribeRemark"
						name="subscribeRemark" style="width: 400px; height: 200"
						data-options="multiline:true,required:true,validType:'length[1,2000]'" />
					</td>
				</tr>
			</table>
	</div>
	
	<!-- 预约你就把这里当成是一个空的容器就完了，为的就是打开一个dialog -->
    <div id="rsaLearningProgressDialog" name="rsaLearningProgressDialog" class="easyui-dialog" 
    	title="每日RSA学习进度" closed="true"
		style="width: 600px; height: 500px;" 
		data-options="iconCls:'icon-search'">
 
		<!-- 查询出来的数据 显示位置 -->
		<table id="rsaLearningProgressGrid" name="rsaLearningProgressGrid" class="easyui-datagrid" fit="true"
		fitColumns="true" data-options="pageList:[20,40,60,80,100],pagination:true,pageSize:20,fit:true,singleSelect:true,
		rownumbers:true,ctrlSelect:false" > 
			<thead>
				<tr >
					<th data-options="field:'ids',checkbox:true"></th>
					
					<th data-options="field:'createDate',width:'100',formatter:formatDate">学习日期</th>
					<th data-options="field:'changeTmmWorkingtime',width:'100'">当日学习时长(分钟)</th>
					<th data-options="field:'ifUpToTheStandard',width:'100',formatter:ifUpToTheStandardFormater">是否达标(30分钟以上)</th>
				</tr>
			</thead>
		</table>
	</div>
    
    <c:import url="/WEB-INF/jsp/common/group_search_div.jsp"/>
</body>
</html>