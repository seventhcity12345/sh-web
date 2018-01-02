<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.webi.hwj.constant.VersonConstant" %>
<%@ include file="/WEB-INF/jsp/common/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" /> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragrma","no-cache");
response.setDateHeader("Expires",0);

%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head id="Head1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	    <title><%=VersonConstant.projectName %></title> 
	    <link rel="stylesheet" type="text/css" href="${ctx}/skin/plugins/easyui/themes/default/easyui.css" />
	    <link rel="stylesheet" type="text/css" href="${ctx}/skin/plugins/easyui/themes/icon.css" />
	
	    <script type="text/javascript" src="${ctx}/skin/plugins/easyui/jquery.min.js"></script>
	    <script type="text/javascript" src="${ctx}/skin/plugins/easyui/jquery.easyui.min.js"></script>
	   	<script type="text/javascript" src="${ctx}/skin/plugins/easyui/easyui-lang-zh_CN.js"></script>
	    <script type="text/javascript" src="${ctx}/skin/js/common/easyui-common.js"></script>
	    
	    <script language="javascript">
		    
		 // 当前画面 不是顶层画面
		    if(top.location !== self.location) {
		        top.location=self.location; 
		    } 
		 
		    document.onkeydown = function(e){
		        var event = e || window.event;  
		        var loginCode = event.keyloginCode || event.which || event.charloginCode;
		        if (loginCode == 13) {
		            login();
		        }
		    }
		    
		    function login(){
		    	var bol = true;
		    	var account = $("#account").val();
		    	var pwd = $("#pwd").val();
		    	
		    	if (!$('#account').validatebox('isValid')) {
		    		bol = false;
		    	}
		    	
		    	if (!$('#pwd').validatebox('isValid')) {
		    		bol = false;
		    	}
		    	
		    	if (!bol){
		    		return;
		    	}
		    	
		    	
		    	
		    	$.ajax({
		    		type : "POST", // post提交方式默认是get
		    		dataType : 'json',
		    		url : basePath+'/encodeLogin',
		    		data : {
		    			code : pwd
		    		},
		    		error : function(data) { // 设置表单提交出错
		    			alert('网络不稳定，请重试~~~');
		    		},
		    		success : function(result) {// 提交成功
		    			if (result.success) {
		    				
		    				$.ajax({          
		    		            type:"POST",//post提交方式默认是get
		    		            dataType:'json', 
		    		            url:"${ctx}/bLogin", 
		    		            data: {
		    		            	account: account,
		    		            	pwd: result.msg
		    		            },
		    		            error:function(request) {// 设置表单提交出错 
		    		         		$('#loading-mask').hide();
		    		           		$.messager.alert('提示','系统出现异常,请联系管理员','error');
		    		            },
		    		            success:function(data) {
		    		                if(data.success){
		    		                	location.href = "${ctx}/bgmanagementadmin";
		    		                }else{
		    		                	$.messager.alert('提示',data.msg);
		    		                } 
		    		           	  
		    		            }            
		    		      });
		    			} else {
		    				alert('加密出现异常!');
		    			}
		    		}
		    	});
		    	
		    	
		    }
	    </script>
	</head> 
<body>
	<div id="loginDiv" name="loginDiv" class="easyui-dialog" title="<%=VersonConstant.projectName %>"
	    style="width:300px;height:200px;padding:10px;" closable="false"
	    modal="true" resizable="false" 
	    data-options="
	    buttons: [{
	          text:'登录',
	          iconCls:'icon-ok',
	          handler:function(){
	              login();
	          }
	      }]
	    " >
	    
	    <form id="myForm" name="myForm" method="post">
	    	<table cellpadding="6">
	        	<tr>
					<td>用户名:</td>
			        <td> 
						<input name="account" id="account" type="text"  class="easyui-validatebox" required="true" missingMessage="用户名不能为空" value=""/>
			        </td>
				</tr>
				<tr>
					<td>密码:</td>
			        <td> 
			        	<input name="pwd" id="pwd" type="password" class="easyui-validatebox" required="true" missingMessage="密码不能为空" value=""/>
			        </td>
				</tr> 
			</table>
	    </form>
	</div>
 
</body>
</html>
