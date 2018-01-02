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
    
    <script type="text/javascript" src="${ctx}/skin/js/admin/base/admin_role.js"></script>
	</head> 
	<body >  
		<table id="dataGrid1" name="dataGrid1" class="easyui-datagrid" fit="true" fitColumns="true" 
			data-options="pageList:[20,40,60,80,100],pagination:true,pageSize:20,fit:true,
				rownumbers:true,ctrlSelect:true,
				url:'${ctx}/admin/role/pagelist',
				toolbar:'#tb'">
			<thead>
	            <tr>
	            	<th data-options="field:'ids',checkbox:true"></th> 
	            	<th data-options="field:'r_name',width:'50',sortable:true">角色名称</th>
					<th data-options="field:'key_id',width:'100',sortable:true,hidden:true">key_id</th>
					<th data-options="field:'r_desc',width:'100',sortable:true">角色描述</th>
	            </tr>
	    	</thead>
    	</table>
    	
    	<div id="tb" style="padding:5px;height:auto">
	        <div style="margin-bottom:5px">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="edit(0)">新增</a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="batchDelete()">删除</a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="edit(1)">修改</a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="setResources()">分配资源</a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="groupSearch()">组合查询</a>
	        </div>
	    </div>
    	 
    	<!-- 新增/修改窗口 -->
    	<div id="saveDiv" name="saveDiv" class="easyui-dialog" title="编辑" 
    		style="width:300px;height:300px;padding:10px;" 
    		modal="true" resizable="false" closed="true"
    		data-options="iconCls:'icon-edit',
    		buttons: [{
                    text:'确定',
                    iconCls:'icon-ok',
                    handler:function(){
                        $('#myForm').submit();
                    }
                }] 
    		" >  
    		
    		<form id="myForm" name="myForm" method="post" action="${ctx}/admin/role/save">
    			<input type="hidden" name="key_id" />
	            <table cellpadding="5">
	            	<tr>
						<td>角色名称:</td>
	                    <td> 
	                    	<input  class="easyui-textbox" type="text" name="r_name" id="r_name" data-options="required:true"/>
	                    </td>
			     	</tr> 
					
					<tr>
						<td>角色描述:</td>
			            <td> 
	                    	<input  class="easyui-textbox" data-options="multiline:true" style="height:60px" name="r_desc" id="r_desc" />
	                    </td>
	                </tr> 
				</table>
	        </form>  
    	</div> 
        
        <!-- 分配资源页面 -->
    	<div id= "setResourceDiv" name ="setResourceDiv" class="easyui-dialog" title="分配资源" 
    		style="width :400px;height:500px; padding:10px ;" 
    		modal="true" resizable= "false" closed ="true" 
    		data-options ="iconCls:'icon-search',
                buttons: [{
                    text:'确定',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitSetResource();
                    }
                }],toolbar:'#setResourceToolBar'
    		">
	    	 <ul id="tree" class="easyui-tree" data-options="animate:true,checkbox:true"></ul>	 
        </div>
        
        <div id="setResourceToolBar" >
        	<input title="全选/反选" type="checkbox" id="treeSelectAll" name="treeSelectAll" onClick="treeChecked(this,'tree')"/>全选/反选
        </div>
        
		<c:import url="/WEB-INF/jsp/common/group_search_div.jsp"/>
	</body>
</html>
<script language="javascript">
	function yang(){
		$('#tree').tree('expandAll');
	}
	setTimeout(function() { yang(); }, 1000); 
	
</script>