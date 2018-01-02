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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>口语训练营</title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/skin/plugins/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/skin/plugins/easyui/themes/icon.css" />

<script type="text/javascript"
	src="${ctx}/skin/plugins/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/skin/plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/skin/plugins/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${ctx}/skin/js/common/easyui-common.js"></script>
<script type="text/javascript"
	src="${ctx}/skin/js/admin/subscribecourse/admin_training_camp.js"></script>
	<script type="text/javascript" src="${ctx}/skin/js/common/filetype.js"></script>
</head>
<body>
	<table id="trainingCampGrid" name="trainingCampGrid"
		class="easyui-datagrid" fit="true" fitColumns="true" nowrap="false"
		data-options="pageList:[20,40,60,80,100],pagination:true,pageSize:20,fit:true,
				rownumbers:true,ctrlSelect:true,
				url:'${ctx}/api/speakhi/v1/admin/trainingCampList',
				toolbar:'#toolbar'">
		<thead>
			<tr>
				<th data-options="field:'ids',checkbox:true"></th>
				<th data-options="field:'keyId',hidden:true">key_id</th>
				<th
					data-options="field:'trainingCampTitle',width:'30',sortable:true">训练营名称</th>
				<th
					data-options="field:'trainingCampDesc',width:'50',unableGroupSearch:true">训练营简介</th>
				<th
					data-options="field:'trainingCampNum',width:'20',unableGroupSearch:true">当前人数</th>
				<th
					data-options="field:'trainingCampPic',width:'30',unableGroupSearch:true,formatter:formatterImg">训练营封面</th>
				<th data-options="field:'account',width:'30',sortable:true">创建者</th>
				<th
					data-options="field:'trainingCampStartTime',width:'30',sortable:true,formatter:formatDateDay">生效开始时间</th>
				<th
					data-options="field:'trainingCampEndTime',width:'30',sortable:true,formatter:formatDateDay">生效结束时间</th>
				<th
					data-options="field:'updateDate',width:'30',sortable:true,formatter:formatDateDay">最后编辑时间</th>
				<th
					data-options="field:'isCanUsed',width:'30',sortable:false,unableGroupSearch:true,formatter:formatIsCanUsed">是否生效中</th>
				<th
					data-options="field:'trainingCampAverageScore',width:'30',sortable:true">平均分</th>
			</tr>
		</thead>
	</table>

        <div id="toolbar" style="padding:5px;height:auto">
	        <div style="margin-bottom:5px">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="addTrainingCamp()">新增</a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="updateTrainingCamp()">编辑</a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteTrainingCamp()">删除</a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="manageTrainingMember()">管理训练营成员</a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="groupSearchTraining('trainingCampGrid')">组合查询</a>
	            <span  style="font-weight: bold; font-size:20px;color: red;float:right;">口语训练营平均分，每天凌晨刷新</span>
	        </div>
	    </div>
	    
	    
<!--         新增/编辑训练营弹框 -->
        <div id="trainingCampDiv" name="trainingCampDiv" class="easyui-dialog" title="新增/编辑 口语训练营" 
    		style="width:800px;height:500px;padding:10px;" 
    		modal="true" resizable="false" closed="true"
    		data-options="iconCls:'icon-edit',
    		buttons: [{
                  text:'保存',
                  iconCls:'icon-ok',
                  handler:function(){
                      updateTrainingCampSubmit();
                  }
                },{
                    text:'取消',
                    iconCls:'icon-redo',
                    handler:function(){
                     $('#trainingCampDiv').dialog('close');
                  }
              }]
    		" >  
    		
			<form id="trainingCampForm" name="trainingCampForm" method="post" enctype="multipart/form-data"
			action="${ctx}/api/speakhi/v1/admin/trainingCamp">
				<input type="hidden" name="keyId" id="keyId" />
				 <table>
				 <tr>
                    <td>标题：</td>
                    <td><input class="easyui-textbox" name="trainingCampTitle"
                        id="trainingCampTitle"
                        data-options="required:true,validType:'length[1,30]'" />
                    </td>
                </tr>
                <tr>
                  <td>开始时间：</td>
                    <td><input class="easyui-datebox" name="trainingCampStartTime"
                        id="trainingCampStartTime"
                        data-options="required:true,editable:false" />
                    </td>
                      <td>结束时间：</td>
                    <td><input class="easyui-datebox" name="trainingCampEndTime"
                        id="trainingCampEndTime"
                        data-options="required:true,editable:false" />
                    </td>
                </tr>
                </table>
                <br/>
                <br/>
               	 简介:<input class="easyui-textbox" id="trainingCampDesc"
						name="trainingCampDesc" style="width: 500px; height: 200"
						data-options="required:true,multiline:true,validType:'length[1,200]'" />
				<br/>
				<br/>
				训练营封面：<input type="hidden" name="trainingCampPic"
						id="trainingCampPic" /> 
						 <input class="easyui-filebox" name="fileId" id="fileId"
						data-options="editable:false,prompt:'选择照片...', buttonText: '上传照片'"/>
			</form>
		</div>
		<!--         新增/编辑训练营弹框 -->
		
<!-- 		训练营成员列表 -->
		<div id="trainingMemberDiv" name="trainingMemberDiv" class="easyui-dialog" title="管理训练营成员" 
    		style="width:100%;height:100%;padding:10px;" 
    		modal="true" resizable="false" closed="true"
    		data-options="iconCls:'icon-edit'" >  
    		
			<table id="trainingMemberGrid" name="trainingMemberGrid" class="easyui-datagrid" 
			fit="true" fitColumns="true"  nowrap="false"
			data-options="pageList:[20,40,60,80,100],pagination:true,pageSize:20,fit:true,
				rownumbers:true,ctrlSelect:true,
				toolbar:'#trainingMemberBar'">
				<thead>
		            <tr>
		            	<th data-options="field:'ids',checkbox:true"></th> 
		            	<th data-options="field:'keyId',hidden:true">key_id</th>
		            	<th data-options="field:'trainingCampId',hidden:true">训练营id</th>
		            	<th data-options="field:'trainingMemberUserId',hidden:true">学员id</th>
						<th data-options="field:'trainingMemberUserCode',width:'30',sortable:true">ID</th>
						<th data-options="field:'trainingMemberRealName',width:'50',sortable:true">真实姓名</th>
						<th data-options="field:'trainingMemberEnglishName',width:'20',sortable:true">英文姓名</th>
						<th data-options="field:'trainingMemberPhone',width:'30',sortable:true">手机号</th>
						<th data-options="field:'trainingMemberCurrentLevel',width:'30',sortable:true">当前级别</th>
						<th data-options="field:'trainingMemberTotalScore',width:'30',sortable:true">总积分</th>
						<th data-options="field:'trainingMemberRsaScore',width:'30',sortable:true">课件积分</th>
						<th data-options="field:'trainingMemberCourseScore',width:'30',sortable:true">上课积分</th>
						<th data-options="field:'addScore',width:'30',sortable:false,unableGroupSearch:true">加分</th>
						<th data-options="field:'reductionScore',width:'30',sortable:false,unableGroupSearch:true">扣分</th>
		            </tr>
		    	</thead>
    		</table>
    	
	    	<div id="trainingMemberBar" style="padding:5px;height:auto">
		        <div style="margin-bottom:5px">
		        <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="groupSearchTraining('trainingMemberGrid')">组合查询</a>
		            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="addScore(1)">加分</a>
		            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="addScore(0)">扣分</a>
		            <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="openUserNotInTrainingMemberDiv()">新增成员</a>
		            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteTrainingMember()">删除成员</a>
		            <span  style="font-weight: bold; font-size:20px;color: red;float:right;">课件积分、上课积分，每天凌晨刷新</span>
		        </div>
		    </div>
		</div>
		<!-- 		训练营成员列表 -->
		
<!-- 		加分/扣分弹框 -->
		 <div id="trainingMemberOptionDiv" name="trainingMemberOptionDiv" class="easyui-dialog" title="加分" 
    		style="width:350px;height:200px;padding:10px;" 
    		modal="true" resizable="false" closed="true"
    		data-options="iconCls:'icon-edit',
    		buttons: [{
                  text:'确定',
                  iconCls:'icon-ok',
                  handler:function(){
                      addScoreSubmit();
                  }
                }
              ]
    		" >  
    		
			<form id="trainingMemberOptionForm" name="trainingMemberOptionForm" 
				method="post" action="${ctx}/api/speakhi/v1/admin/trainingMemberOption">
				<input type="hidden" name="trainingCampId" id="trainingCampId" />
				<input type="hidden" name="trainingMemberUserId" id="trainingMemberUserId" />
				<input type="hidden" name="trainingMemberOptionType" id="trainingMemberOptionType" />
				 <table>
				 <tr>
                    <td><span id = "trainingMemberOptionReasonTitle">加分事由:</span></td>
                    <td><input class="easyui-textbox" name="trainingMemberOptionReason"
                        id="trainingMemberOptionReason"
                        data-options="required:true,validType:'length[1,30]'" />
                    </td>
                </tr>
                <tr>
                  <td><span id = "trainingMemberOptionScoreTitle">加分分值:</span></td>
                    <td><input class="easyui-textbox" name="trainingMemberOptionScore"
                        id="trainingMemberOptionScore"
                        data-options="required:true,validType:'range[1,100]'" />
                    </td>
                </tr>
                </table>
			</form>
		</div>
		<!-- 		加分/扣分弹框 -->
		
		<!-- 	新增训练营成员：不在训练营内的学员成员列表 -->
		<div id="userNotInTrainingMemberDiv" name="userNotInTrainingMemberDiv" class="easyui-dialog" title="新增训练营成员" 
    		style="width:100%;height:100%;padding:10px;" 
    		modal="true" resizable="false" closed="true"
    		data-options="iconCls:'icon-edit',
    		buttons: [{
                  text:'新增',
                  iconCls:'icon-ok',
                  handler:function(){
                      addTrainingMember();
                  }
                },{
                    text:'取消',
                    iconCls:'icon-redo',
                    handler:function(){
                     $('#userNotInTrainingMemberDiv').dialog('close');
                  }
                }]" >  
    		
    		
			<table id="userNotInTrainingMemberGrid" name="userNotInTrainingMemberGrid" class="easyui-datagrid" 
			fit="true" fitColumns="true"  nowrap="false"
			data-options="pageList:[20,40,60,80,100],pagination:true,pageSize:20,fit:true,
				rownumbers:true,ctrlSelect:true,
				toolbar:'#userNotInTrainingMemberGridBar'">
				<thead>
		            <tr>
		            	<th data-options="field:'ids',checkbox:true"></th> 
		            	<th data-options="field:'keyId',hidden:true">key_id</th>
		            	<th data-options="field:'userCode',sortable:false">ID</th>
		            	<th data-options="field:'realName',sortable:false">学员姓名</th>
						<th data-options="field:'englishName',width:'30',sortable:false">学员英文名</th>
						<th data-options="field:'phone',width:'50',sortable:false">学员手机号</th>
		            </tr>
		    	</thead>
    		</table>
    	
	    	<div id="userNotInTrainingMemberGridBar" style="padding:5px;height:auto">
		        <div style="margin-bottom:5px">
		        <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="groupSearchTraining('userNotInTrainingMemberGrid')">组合查询</a>
		        </div>
		    </div>
		</div>
		<!-- 		不在训练营内的学员成员列表 -->
		
		
		<c:import url="/WEB-INF/jsp/common/group_search_div.jsp"/>


</body>
</html>