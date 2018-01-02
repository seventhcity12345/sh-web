<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragrma","no-cache");
response.setDateHeader("Expires",0);
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
    <script type="text/javascript" src="${ctx}/skin/js/common/filetype.js"></script>
    <script type="text/javascript" src="${ctx}/skin/js/admin/base/admin_badmin_user.js"></script>
	</head> 
	<body >  
		<table id="dataGrid1" name="dataGrid1" class="easyui-datagrid" fit="true" fitColumns="true" 
			data-options="pageList:[20,40,60,80,100],pagination:true,pageSize:20,fit:true,
				rownumbers:true,ctrlSelect:true,
				url:'${ctx}/admin/adminUser/pagelist',
				toolbar:'#tb'">
			<thead>
	            <tr>
	            	<th data-options="field:'ids',checkbox:true"></th> 
	            	<th data-options="field:'account',width:'100',sortable:true">用户名</th>
	            	<th data-options="field:'admin_user_name',width:'100',sortable:true">姓名</th>
	            	<th data-options="field:'admin_user_photo',formatter:formatterImg">照片</th>
					<th data-options="field:'role_name',width:'100',sortable:true">角色</th>
					<th data-options="field:'create_date',width:'100',sortable:true,formatter:formatDate">创建日期</th>
					<th data-options="field:'key_id',width:'100',sortable:true,hidden:true">key_id</th>
					
					<th data-options="field:'employee_number',width:'100',sortable:true">员工号</th>
					<th data-options="field:'telphone',width:'100',sortable:true">手机号</th>
					<th data-options="field:'email',width:'100',sortable:true">邮箱</th>
					<th data-options="field:'weixin',width:'100',sortable:true">微信号</th>
	            </tr>
	    	</thead>
    	</table>
    	
    	<div id="tb" style="padding:5px;height:auto">
	        <div style="margin-bottom:5px">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="edit(0)">新增</a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="batchDelete()">删除</a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="edit(1)">修改</a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="showDetail()">查看</a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="setRoles()">分配角色</a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="groupSearch()">组合查询</a>
	        </div>
	    </div>
    	 
    	<!-- 新增/修改窗口 -->
    	<div id="saveDiv" name="saveDiv" class="easyui-dialog" title="编辑" 
    		style="width:300px;height:400px;padding:10px;" 
    		modal="true" resizable="false" closed="true"
    		data-options="iconCls:'icon-edit',
    		buttons: [{
                    text:'确定',
                    iconCls:'icon-ok',
                    handler:function(){
                        myFormSubmit();
                    }
                }] 
    		" >  
    		
    		<form id="myForm" name="myForm" method="post" enctype="multipart/form-data"
    		action="${ctx}/admin/adminUser/save">
    			<input type="hidden" name="key_id" />
	            <table cellpadding="5">
           			<tr>
						<td>账号:</td>
	                    <td> 
	                    	<input  class="easyui-textbox" type="text" name="account" id="account" data-options="required:true"/>
	                    </td>
	                </tr> 
           			<tr>
						<td>密码:</td>
	                    <td> 
	                    	<input  class="easyui-textbox" type="password" name="pwd" id="pwd" data-options="required:true"/>
	                    </td>
	                </tr>
			   		<tr>
						<td>姓名:</td>
	                    <td> 
	                    	<input  class="easyui-textbox" type="text" name="admin_user_name" id="admin_user_name" data-options="required:true"/>
	                    </td>
	                </tr> 
	                <tr>
						<td>工号:</td>
	                    <td> 
	                    	<input  class="easyui-textbox" type="text" name="employee_number" id="employee_number"  data-options="validType:'length[0,13]'" />
	                    </td>
	                </tr> 
	                <tr>
						<td>手机号:</td>
	                    <td> 
	                    	<input  class="easyui-textbox" type="text" name="telphone" id="telphone"  data-options="validType:'mobile'" />
	                    </td>
	                </tr> 
	                <tr>
						<td>邮箱:</td>
	                    <td> 
	                    	<input  class="easyui-textbox" type="text" name="email" id="email"  data-options="validType:'email'" />
	                    </td>
	                </tr> 
	                <tr>
						<td>微信号:</td>
	                    <td> 
	                    	<input  class="easyui-textbox" type="text" name="weixin" id="weixin"  data-options="validType:'length[0,30]'" />
	                    </td>
	                </tr> 
	                <tr>
					<td>照片</td>
					<td><input type="hidden" name="admin_user_photo" id="admin_user_photo" />  
					<input class="easyui-filebox" name="fileId" id="fileId"
						data-options="editable:false,prompt:'选择照片...', buttonText: '上传照片'"/>
					</td>
				</tr>
				</table>
	        </form>  
    	</div>
    	
    	<!-- 详情信息 -->
    	<div id= "detailDiv" name ="detailDiv" class="easyui-dialog" title="查看详情" 
    		data-options ="iconCls:'icon-search'" style="width :600px;height:500px; padding:10px ;" 
    		modal="true" resizable= "false" closed ="true">
	    	
	    	<table class="easyui-datagrid" id="detailTable" name="detailTable" style="width:560px;height:auto" fitColumns="true">
                <thead>
                    <tr>
						<th field="a1" width="15">属性名称</th>
						<th field="a2" width="30">属性内容</th> 
                    </tr>
                </thead>
                <tbody>
               		<tr>
				        <td>姓名</td>
				        <td></td>	
				    </tr> 
				    <tr>
				        <td>角色名称</td>
				        <td></td>	
				    </tr>
			        <tr>
				        <td>账号</td>
				        <td></td>	
				    </tr> 
			        <tr>
				        <td>创建日期</td>
				        <td></td>	
				    </tr>  
				</tbody>
	        </table>	 
        </div>
        
        <!-- 分配角色页面 -->
    	<div id= "setRoleDiv" name ="setRoleDiv" class="easyui-dialog" title="分配角色" 
    		style="width :600px;height:500px; padding:10px ;" 
    		modal="true" resizable= "false" closed ="true" 
    		data-options ="iconCls:'icon-search',
                buttons: [{
                    text:'确定',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitSetRoles();
                    }
                }]
    		">
	    	  <table id="dataGrid2" name="dataGrid2" class="easyui-datagrid" fit="true" fitColumns="true" 
				data-options="fit:true,singleSelect:true,
				rownumbers:true,onLoadSuccess:dataGrid2Load">
				<thead>
		            <tr>
		            	<th data-options="field:'ids',checkbox:true"></th>
		            	<th data-options="field:'r_name',width:'100',sortable:true">名称</th> 
	       				<th data-options="field:'r_desc',width:'100',sortable:true">描述</th> 
						<th data-options="field:'key_id',width:'100',sortable:true,hidden:true">key_id</th>
		            </tr>
		    	</thead>
    		</table>
        </div>
        
		<c:import url="/WEB-INF/jsp/common/group_search_div.jsp"/>
	</body>
</html>