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
	<script>
		var editor1 = null;
	</script>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>综合查询</title> 
    <link rel="stylesheet" type="text/css" href="${ctx}/skin/plugins/easyui/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/skin/plugins/easyui/themes/icon.css" />

    <script type="text/javascript" src="${ctx}/skin/plugins/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/skin/plugins/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctx}/skin/plugins/easyui/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${ctx}/skin/js/common/easyui-common.js"></script> 
    <script type="text/javascript" src="${ctx}/skin/js/admin/base/admin_config.js"></script>
    
    <link rel="stylesheet" href="${ctx}/skin/plugins/kindeditor/themes/default/default.css" />
	<link rel="stylesheet" href="${ctx}/skin/plugins/kindeditor/plugins/code/prettify.css" />
	<script charset="utf-8" src="${ctx}/skin/plugins/kindeditor/kindeditor.js"></script>
	<script charset="utf-8" src="${ctx}/skin/plugins/kindeditor/lang/zh_CN.js"></script>
	<script charset="utf-8" src="${ctx}/skin/plugins/kindeditor/plugins/code/prettify.js"></script>
	
	<script language="javascript">
		KindEditor.ready(function(K) {
			editor1 = K.create('textarea[name="config_value"]', {
				cssPath : '${ctx}/skin/plugins/kindeditor/plugins/code/prettify.css',
				uploadJson : '${ctx}/skin/plugins/kindeditor/jsp/upload_json.jsp',
				fileManagerJson : '${ctx}/skin/plugins/kindeditor/jsp/file_manager_json.jsp',
				allowFileManager : true,
				afterCreate : function() {
					var self = this; 
				}
			});
			prettyPrint();
		});
		
		function initMemcache(){
			$.ajax({
				   type:"POST",   //post提交方式默认是get
				   dataType:'json', 
				   url:basePath+'/admin/config/initAdminConfigMemcache', 
				   error:function(data) {      // 设置表单提交出错 
					   alert('系统出现异常,请联系管理员');
				   },
				   success:function(result) {//提交成功
					   if(result.success){
						   $.messager.alert('提示','操作成功！');
					   }else{
						   alert(result.msg);
					   }
				   }            
			});
		}
	</script>
</head> 
	<body >  
		<table id="dataGrid1" name="dataGrid1" class="easyui-datagrid" fit="true" fitColumns="true" 
			data-options="pageList:[20,40,60,80,100],pagination:true,pageSize:20,fit:true,
				rownumbers:true,ctrlSelect:true,
				url:'${ctx}/admin/config/pagelist',
				toolbar:'#tb'">
			<thead>
	            <tr>
	            	<th data-options="field:'ids',checkbox:true"></th> 
					<th data-options="field:'config_name',width:'30',sortable:true">名称</th>
					<th data-options="field:'config_desc',width:'30',sortable:true">描述</th>
					<th data-options="field:'key_id',sortable:true,hidden:true">key_id</th>
	            </tr>
	    	</thead>
    	</table>
    	
    	<div id="tb" style="padding:5px;height:auto">
	        <div style="margin-bottom:5px">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="setKe()">设置值</a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="initMemcache()">初始化缓存</a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="showDefaultDetailDiv('config/detail')">查看</a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="groupSearch()">组合查询</a>
	        </div>
	    </div>
        
        <div id="keDiv" name="keDiv" class="easyui-dialog" title="编辑" 
    		style="width:800px;height:500px;padding:10px;" 
    		modal="true" resizable="false" closed="true"
    		data-options="iconCls:'icon-edit',buttons: [{
                  text:'确定',
                  iconCls:'icon-ok',
                  handler:function(){
                      myForm2Submit();
                  }
              }] 
    		" >  
    		
			<form id="myForm2" name="myForm2" method="post" action="${ctx}/admin/config/save">
				<input type="hidden" name="key_id" id="ke_id" />
				<textarea name="config_value" id="config_value" cols="100" rows="8" style="width:755px;height:400px;visibility:hidden;"></textarea>
			</form>
		</div>
		
		
		
		<c:import url="/WEB-INF/jsp/common/group_search_div.jsp"/>
	</body>
</html>