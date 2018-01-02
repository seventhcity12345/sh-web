<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/taglib.jsp"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link type="text/css" rel="stylesheet" href="${ctx}/skin/css/xx/datagrid.css"/>

<script type="text/javascript" src="${ctx}/skin/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/common/common-effect.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/common/page.js"></script>

<script type="text/javascript">
	currentPage = ${page.pageNumber};
	totalPage =${page.totalPage};
	
	//演示ajax操作,后台不用写一行代码
	function updateSex(key_id){
		$.ajax({
		   type:"POST",   //post提交方式默认是get
		   dataType:'json', 
		   timeout : 8000, //超时时间8秒
		   url:basePath+'/demo/save', 
		   data : {
			   keyId : key_id,
			   userName : "hahahha"
		   },
		   beforeSend:ajaxLoading,
		   error:function(jqXHR, textStatus, errorThrown){   
			   ajaxLoadEnd();
		       if(textStatus=="timeout"){  
		           alert("请求超时，页面自动刷新！");
		       }else{   
		    	   alert('系统出现异常,请联系管理员');
		           alert(textStatus);   
		       }
		   },  
		   success:function(result) {//提交成功
			   ajaxLoadEnd();
			   if(result.success){
				   alert(result.msg);//后台需要使用 new JsonMessage()对象返回
				   history.go(0);
			   }else{
				   alert(result.msg);
			   }
		   }
		}); 
	}
</script>
</head>
<body>
	<table border="1">
		<tr>
			<td>用户名</td>
			<td>当前级别</td>
			<td>创建日期</td>
			<td>操作</td>
		</tr>
		<c:forEach var="obj" items="${page.datas}" varStatus="status">
			<tr>
				<td>${obj.userName}</td>
				<td>${obj.currentLevel}</td>
				<td><fmt:formatDate value="${obj.createDate}" pattern="yyyy-MM-dd HH:mm" /></td>
				<td><a href="${ctx}/demo/detail/${obj.keyId}" target="_blank">查看</a>
				,<a href="javascript:updateSex('${obj.keyId}')" >设置姓名</a>
				<a href="${ctx}/demo/delete/${obj.keyId}" >删除</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<a href="javascript:pageFun('first')">首页</a>
			<a href="javascript:pageFun('previous')">上一页</a>&nbsp;${page.pageNumber}/${page.totalPage}
			<a href="javascript:pageFun('next')">下一页</a>
			<a href="javascript:pageFun('last')">尾页</a>

		</tr>


	</table>
</body>
</html>