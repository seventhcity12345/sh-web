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
</head> 
	<body >  
		<table border="1">
			<tr>
				<td>名称</td>
				<td>内容</td>
			</tr>
			
			<tr>
				<td>key</td>
				<td>${obj.config_name}</td>
			</tr>
			
			<tr>
				<td>value</td>
				<td>${obj.config_value}</td>
			</tr>
			
			<tr>
				<td>描述</td>
				<td>${obj.config_desc}</td>
			</tr>
		</table>
	</body>
</html>