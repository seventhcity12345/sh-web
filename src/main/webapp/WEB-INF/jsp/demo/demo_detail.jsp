<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/taglib.jsp"%> 
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
</head>
<body>
	<table>
		<tr>
			<td>主键</td>
			<td>用户名</td>
			<td>是否为学员</td>
			<td>照片</td>
			<td>创建日期</td>
		</tr>
			<tr>
				<td>${obj.keyId}</td>
				<td>${obj.userName}</td>
				<td>
					<c:if test="${obj.isStudent==true}"> 
						是
					</c:if>
					<c:if test="${obj.isStudent==false}"> 
						否
					</c:if>
				</td>
				<td><img src="${obj.userPhoto}" height="100" width="100" /></td>
				<td><fmt:formatDate value="${obj.createDate}" pattern="yyyy-MM-dd HH:mm"/></td>
			</tr>
		
	
	
	</table>
	
</body>
</html>