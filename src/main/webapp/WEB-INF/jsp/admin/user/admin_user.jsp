<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="com.webi.hwj.util.SessionUtil" %>
<%@ page import="com.webi.hwj.bean.SessionAdminUser" %>

<%@ include file="/WEB-INF/jsp/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragrma", "no-cache");
	response.setDateHeader("Expires", 0);
	
	SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
	boolean isHaveGiveLesson = sessionAdminUser.isHavePermisson("contract:giveLesson");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
<script>
var isHaveGiveLesson = '<%=isHaveGiveLesson%>';
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>综合查询</title>
<link rel="stylesheet" type="text/css" href="${ctx}/skin/plugins/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/skin/plugins/easyui/themes/icon.css" />
<script type="text/javascript" src="${ctx}/skin/plugins/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/skin/plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/skin/plugins/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/common/easyui-common.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/admin/user/admin_user_detail_order.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/admin/user/admin_user_level.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/admin/user/admin_user_detail.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/admin/user/admin_user_demo.js"></script>
<script>
	
	
	//添加新用户
	function addNewUser(){
		$('#addNewUserDiv').dialog('open');
	}
	
	//提交用户的注册手机号
	function submitAddNewUser(){
		$.ajax({
		       type:"POST",   //post提交方式默认是get
		       dataType:'json', 
		       url: basePath + '/admin/user/addNewUser', 
		       data : {
		    	   phone : $('#phone').textbox('getValue'),
		       },
		       error:function(data) {      // 设置表单提交出错 
		    	   $('#loading-mask').hide();
		    	   $.messager.alert('提示','系统出现异常,请联系管理员','error');
		       },
		       success:function(result) {
		    	   $('#loading-mask').hide(); 
		    	   if(result.success){
		    		   $('#addNewUserDiv').dialog('close');
		    		   $('#dataGrid1').datagrid('reload');
		    		   $.messager.alert('提示', result.msg);
		    	   }else{
		    		   $.messager.alert('提示', result.msg);
		    	   }
		       }            
		 	});
	}

	// 截取webex url
	function webexUrlFormater(url){
		if(url !== null && url !== undefined && url !== ''){
			var urlArr = url.split("/");
			if(urlArr.length >= 3){
				return urlArr[0] + '//' +urlArr[1] + urlArr[2];
			}
		}
	}
	
</script>
<style>
	.demoTable tr td:nth-child(1)
	{
	    text-align:right;
	}
</style>
</head>
<body>
	<table id="dataGrid1" name="dataGrid1" class="easyui-datagrid"
		fit="true" fitColumns="true" nowrap="false"
		data-options="pageList:[20,40,60,80,100],pagination:true,pageSize:20,fit:true,
				rownumbers:true,url:'${ctx}/admin/user/pagelist',singleSelect:true,
				toolbar:'#tb',onDblClickRow:function(rowIndex,rowData){
				   lookUserDetail(rowIndex, rowData);
				  }">
		<thead>
			<tr>
				<th data-options="field:'ids',checkbox:true"></th>
				<th data-options="field:'user_id',hidden:true">user_id</th>
				<th data-options="field:'subscribeId',hidden:true">subscribeId</th>
				
				<th data-options="field:'user_code',width:'50',sortable:false">用户编码</th>
				<th data-options="field:'phone',width:'100',sortable:false">手机号</th>
				<th data-options="field:'user_name',width:'100',sortable:false">用户姓名</th>
				<th data-options="field:'webexRoomHostId',width:'100',sortable:false,unableGroupSearch:true">房间号</th>
				<th data-options="field:'webexRequestUrl',width:'150',sortable:false,unableGroupSearch:true,formatter:webexUrlFormater">域名</th>
				<th data-options="field:'webexMeetingKey',width:'100',sortable:false,unableGroupSearch:true">demo会议号</th>
				<th data-options="field:'startTime',width:'100',sortable:false,unableGroupSearch:true,formatter:formatDate">demo时间</th>
				<th data-options="field:'test_level',width:'100',sortable:false">笔试等级</th>
				<th data-options="field:'init_level',width:'100',sortable:false">初始等级</th>
				<th data-options="field:'current_level',width:'100',sortable:false">当前等级</th>
				<th data-options="field:'create_date',width:'100',sortable:false,formatter:formatDate,unableGroupSearch:true">注册日期</th>
			</tr>
		</thead>
	</table>

	<div id="tb" style="padding: 5px; height: auto">
		<div style="margin-bottom: 5px">
			<!-- 
	            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="batchDelete()">删除</a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="editContract(1)">修改</a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="showDefaultDetailDiv('user/detail')">查看</a>
	        -->
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="groupSearch()">组合查询</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="editUserLevel()">修改级别</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addNewUser()">用户注册</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="openDemoDialog()">预约demo</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="userCancelSubscribeCourseDemo()">取消demo</a>
		</div>
	</div>
	
	<!-- 添加新用户 -->
	<div id="addNewUserDiv" name="addNewUserDiv" class="easyui-dialog" title="用户注册"
		style="width: 300px; height: 150px; padding: 10px;" modal="true"
		resizable="false" closed="true"
		data-options="iconCls:'icon-edit',
    		buttons: [{
                    text:'注册',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitAddNewUser();
                    }
                }] 
    		">
			<table cellpadding="5">
				<tr>
					<td>手机号码:</td>
					<td>
						<input class="easyui-textbox" id="phone" name="phone" data-options="editable:true,required:true"/>
					</td>
				</tr>
			</table>
	</div>
	<!-- 添加新用户 -->

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
	  resizable="false" closed="true" data-options="iconCls:'icon-edit'">
	  
	</div>
	
	
	<!-- 拆分合同订单 -->
	<div id="splitOrderCourseDiv" name="splitOrderCourseDiv" class="easyui-dialog" title="拆分合同"
		style="width: 800px; height: 600px; padding: 10px;" modal="true"
		resizable="false" closed="true"
		data-options="iconCls:'icon-edit',
    		buttons: [{
                    text:'关闭',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#splitOrderCourseDiv').dialog('close');
                    }
                }] 
    		">
	</div>
	
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
					<th data-options="field:'startTime',width:'100',formatter:formatDate">上课时间</th>
					<th data-options="field:'endTime',width:'100',formatter:formatDate">结束时间</th>
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
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="userSubscribePrecondition()">预约demo课</a>
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
                    	userSubscribeDemoCourse();
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

	<c:import url="/WEB-INF/jsp/common/group_search_div.jsp" />
</body>
</html>