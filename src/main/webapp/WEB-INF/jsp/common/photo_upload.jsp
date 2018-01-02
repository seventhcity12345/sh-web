<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/jsp/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragrma","no-cache");
response.setDateHeader("Expires",0);

String photoType = request.getParameter("photoType");
String fieldName = request.getParameter("fieldName");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>上传图片</title>  
    <link rel="stylesheet" type="text/css" href="${ctx}/easyui/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/easyui/themes/icon.css" />
     
    <script type="text/javascript" src="${ctx}/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctx}/easyui/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${ctx}/js/easyui-common.js"></script>
	<script> 
		$(function(){
			//设置表单提交回调函数
		    $('#uploadForm').form({
		        success:function(data){
		        	data = eval("("+data+")");
		            if(data.success){
						alert("图片上传成功");								            	
						if(window.opener){
							window.opener.document.getElementById("<%=fieldName%>").value = data.msg;
						}
						window.close();
		            }else{
		            	alert(data.msg);
		            } 
		        }
		    }); 
		});
		
		function submitPhoto(){
			var file=$("input[name='file_photo']").val(); 
			if(file==""||file==null){
				alert("请上传图片");
				return ;
			}
			$("#uploadForm").submit();
		}
	</script>
</head> 
<body>
	<form id="uploadForm" name="uploadForm" method="post" action="${ctx}/photoUpload" enctype="multipart/form-data">
		<input type="file" name="file_photo" id="file_photo" />
		<input type="hidden" id="photoType" name="photoType" value="<%=photoType %>"/>
		<input type="button" value="上传" onclick="submitPhoto()"/>
		<input type="button" value="取消" onclick="window.close()"/>
	</form>
</body>
</html>
 