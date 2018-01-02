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
    <script type="text/javascript" src="${ctx}/skin/js/admin/base/admin_resource.js"></script>
	</head> 
<body >  
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="tools:'#tt',region:'west',title:'资源目录',split:true" class="easyui-panel" style="width:250px;">
			<ul id="tree" class="easyui-tree" data-options="animate:false,dnd:true"></ul>
		</div>
	    
	    <div id="tt">
			<a href="#" class="icon-add" onclick="edit()"></a>
			<a href="#" class="icon-remove" onclick="batchDelete()"></a>
		</div> 
		
		<div data-options="region:'center',split:true" class="easyui-panel" style="width:200px;" title="添加/修改资源">
               	<div style="padding:10px 60px 20px 40px">
               		<form id="myForm" name="myForm" method="post" action="${ctx}/admin/resource/save">
		    			<input type="hidden" name="key_id" id="key_id" />
		    			<input type="hidden" name="pid" id="pid"/>
			            <table cellpadding="5">
			            	<tr>
								<td>父资源:</td>
			                    <td> 
			                    	<input type="text" name="pname" id="pname" readonly="readonly" >
			                    </td>
			                </tr> 
	            			<tr>
								<td>资源名称:</td>
			                    <td> 
			                    	<input  class="easyui-validatebox" type="text" name="r_name" id="r_name" data-options="required:true" maxlength="50">
			                    </td>
			                </tr> 
						   	<tr>
								<td>权限:</td>
			                    <td> 
			                    	<input  class="easyui-validatebox" type="text" name="permission" id="permission" maxlength="50">
			                    </td>
			                </tr> 
						   	<tr>
								<td>分类:</td>
			                    <td> 
			                    	<select class="easyui-combobox" id="r_type" name="r_type" style="width:151px;" data-options="panelHeight:'auto',editable:false,required:true">
			                    		<option value="0">菜单</option>
			                    		<option value="1">功能</option>
			                    	</select>
			                    </td>
			                </tr> 
						    <tr>
								<td>资源URL:</td>
			                    <td> 
			                    	<input  class="easyui-validatebox" type="text" name="url" id="url" maxlength="50">
			                    </td>
			                </tr> 
						</table>
		        	</form> 
               	  	
		        </div>
		        <div style="padding:10px 60px 20px 40px">
		            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitForm();">确定</a> 
				</div>
			</div>	
		</div>	 
</body>
</html>