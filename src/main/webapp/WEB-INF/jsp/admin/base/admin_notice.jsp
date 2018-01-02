<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragrma","no-cache");
response.setDateHeader("Expires",0);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head ">
	<script>
		var editor1 = null;
	</script>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>公告管理</title> 
    <link rel="stylesheet" type="text/css" href="${ctx}/skin/plugins/easyui/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/skin/plugins/easyui/themes/icon.css" />

    <script type="text/javascript" src="${ctx}/skin/plugins/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/skin/plugins/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctx}/skin/plugins/easyui/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${ctx}/skin/js/common/easyui-common.js"></script> 
    <script type="text/javascript" src="${ctx}/skin/js/admin/base/admin_notice.js"></script>
    
    <link rel="stylesheet" href="${ctx}/skin/plugins/kindeditor/themes/default/default.css" />
	<link rel="stylesheet" href="${ctx}/skin/plugins/kindeditor/plugins/code/prettify.css" />
	<script charset="utf-8" src="${ctx}/skin/plugins/kindeditor/kindeditor.js"></script>
	<script charset="utf-8" src="${ctx}/skin/plugins/kindeditor/lang/zh_CN.js"></script>
	<script charset="utf-8" src="${ctx}/skin/plugins/kindeditor/plugins/code/prettify.js"></script>
	
	<script language="javascript">
		KindEditor.ready(function(K) {
			editor1 = K.create('textarea[name="noticeContent"]', {
				pasteType : 1,  //粘贴 没格式
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
		
		gridName = 'dataGrid1';
	</script>
</head> 
	<body >  
		<table id="dataGrid1" name="dataGrid1" class="easyui-datagrid" fit="true" fitColumns="true" 
			data-options="pageList:[20,40,60,80,100],pagination:true,pageSize:20,fit:true,
				rownumbers:true,ctrlSelect:true,
				url:'${ctx}/admin/notice/pagelist',
				toolbar:'#toolbar'">
			<thead>
	            <tr>
	            	<th data-options="field:'ids',checkbox:true"></th> 
	            	<th data-options="field:'keyId',hidden:true">key_id</th>
					<th data-options="field:'noticeTitle',width:'30',sortable:true">标题</th>
					<th data-options="field:'noticeType',width:'30',sortable:true,formatter:formatNoticeType">类型</th>
					<th data-options="field:'noticeContent',width:'50',sortable:true,formatter:formatNoticeContent">内容</th>
					<th data-options="field:'adminUserName',width:'30',sortable:true">创建者</th>
					<th data-options="field:'noticeStartTime',width:'30',sortable:true,formatter:formatDateDay">生效开始时间</th>
					<th data-options="field:'noticeEndTime',width:'30',sortable:true,formatter:formatDateDay">生效结束时间</th>
					<th data-options="field:'createDate',width:'30',sortable:true,formatter:formatDateDay">创建时间</th>
					<th data-options="field:'isUsed',width:'30',sortable:false,unableGroupSearch:true,formatter:formatYesOrNo">是否生效中</th>
	            </tr>
	    	</thead>
    	</table>
    	
    	<div id="toolbar" style="padding:5px;height:auto">
	        <div style="margin-bottom:5px">
	            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="addNotice()">新增</a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="updateNotice()">编辑</a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="deleteNotice()">删除</a>
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="groupSearch()">组合查询</a>
	        </div>
	    </div>
        
        <div id="noticeDiv" name="noticeDiv" class="easyui-dialog" title="编辑" 
    		style="width:800px;height:500px;padding:10px;" 
    		modal="true" resizable="false" closed="true"
    		data-options="iconCls:'icon-edit',
    		buttons: [{
                  text:'保存',
                  iconCls:'icon-ok',
                  handler:function(){
                      updateNoticeSubmit();
                  }
                },{
                    text:'取消',
                    iconCls:'icon-redo',
                    handler:function(){
                     $('#noticeDiv').dialog('close');
                  }
              }]
    		" >  
    		
			<form id="noticeForm" name="noticeForm" method="post" action="${ctx}/admin/notice/updateNotice">
				<input type="hidden" name="keyId" id="keyId" />
				 <table>
				 <tr>
                    <td><input type="radio" name="noticeType" id="noticeTypeNotice" value="0" checked="checked"/>
                        <label for="noticeTypeNotice" class="radio-label">公告</label>
                        <input type="radio" name="noticeType" id="noticeTypeBanner" value="1"/>
                        <label for="noticeTypeBanner" class="radio-label">banner</label>
                    </td>
                    <td></td>
                    <td>标题：</td>
                    <td><input class="easyui-textbox" name="noticeTitle"
                        id="noticeTitle"
                        data-options="required:true,validType:'length[0,16]'" />
                    </td>
                </tr>
                <tr>
                  <td>开始时间：</td>
                    <td><input class="easyui-datebox" name="noticeStartTime"
                        id="noticeStartTime"
                        data-options="required:true,editable:false" />
                    </td>
                      <td>结束时间：</td>
                    <td><input class="easyui-datebox" name="noticeEndTime"
                        id="noticeEndTime"
                        data-options="required:true,editable:false" />
                    </td>
                </tr>
                </table>
				<textarea name="noticeContent" id="noticeContent" cols="100" rows="8" style="width:755px;height:400px;visibility:hidden;"></textarea>
			</form>
		</div>
		
		
		<c:import url="/WEB-INF/jsp/common/group_search_div.jsp"/>
	</body>
</html>