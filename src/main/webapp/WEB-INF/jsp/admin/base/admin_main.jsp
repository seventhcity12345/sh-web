<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="com.webi.hwj.util.SessionUtil" %>
<%@ page import="com.webi.hwj.bean.SessionAdminUser" %>
<%@ include file="/WEB-INF/jsp/common/taglib.jsp"%>
<%@ page import="com.webi.hwj.constant.VersonConstant" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%  
	response.setHeader("Pragma", "no-cache");  
	response.setHeader("Cache-Control", "no-cache");  
	response.setDateHeader("Expires", 0);
	
	SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
	
	if (sessionAdminUser==null) { 
	   response.sendRedirect(request.getContextPath()+"/bgmanagementadmin");
	}
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title><%=VersonConstant.projectName %></title> 
    
    <link rel="stylesheet" type="text/css" href="${ctx}/skin/css/default.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/skin/plugins/easyui/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/skin/plugins/easyui/themes/icon.css" />

    <script type="text/javascript" src="${ctx}/skin/plugins/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/skin/plugins/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctx}/skin/plugins/easyui/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${ctx}/skin/js/common/easyui-common.js"></script> 

    <script type="text/javascript"> 
		// 判断当前session是否为空
			 $(function() {
		        	$('#btnModify').click(function(){
		        		pwdModify();
		        	}
		        	);
		        	$('#btnCancel').click(function(){$('#pwdModify').window('close')});
		            $('#loginOut').click(function() {
		                $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {
		                    if (r) {

		                        //ajax异步提交
		                        $.ajax({
		                               type:"POST",   //post提交方式默认是get
		                               dataType:'json',
		                               url:"<%=request.getContextPath()%>/adminLogout",
		                               error:function(request) {// 设置表单提交出错
		                              	 $.messager.alert('提示','系统出现异常,请联系管理员','error');
		                               },
		                               success:function(data) {
		                            	   $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
		                            	   $("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});
		                            	   history.go(0);
		                               }
		                         }); 
		                    }
		                });
		            })
		             
		            
		            $('#menu_tree').tree({
		            	data :[{
		        			"id":"0",
		        			"text":"管理系统",
		        			"state":"closed"
		            	}],
		                lines:true,
		                cascadeCheck:false,
		                onClick:function(node){
		                    if(node.id=="0"){//根节点
		                    	
		                    }else{//叶子节点
		                    	if(node.pid!=0){
		                    		addTab(node.text,basePath+node.url,'');	
		                    	}
		                    } 
		                },
		                onBeforeExpand: function(node){
		                    if(node){
		                        $('#menu_tree').tree('options').url = basePath + "/adminLeftMain?keyId=" + node.id;    
		                    }
		                },
		                onExpand : function (node) {
		                    //common_area_id = node.id;
		                },onLoadSuccess : function (node, data){
		                	if(node!=null){
		                		$('#menu_tree').tree('expandAll',node.target);
		                	}
		                }
		            });
		            
		            //展开根节点
		            var _root = $('#menu_tree').tree('getRoot');
		            $('#menu_tree').tree('expand',_root.target); 
        });

			 
function openWin_modify(){
	$('#oldPwd').val('');
	$('#pwd').val('');
	 
	$( '#pwdModify').dialog('open' );
}    

function pwdModify(){
	var bol = true;		
	if (!$('#oldPwd').validatebox('isValid')) {
		bol = false;
	}
		
	if (!$('#pwd').validatebox('isValid')) {
		bol = false;
	} 
		
	if (!bol) {
		return;
	}
	
	var encodeOldPwd = "";
	var encodeNewPwd = "";
	
	
	//加密老密码
	$.ajax({
		type : "POST", // post提交方式默认是get
		dataType : 'json',
		url : basePath+'/encodeRegister',
		data : {
			code : $('#oldPwd').val()
		},
		error : function(data) { // 设置表单提交出错
			alert('网络不稳定，请重试~~~');
		},
		success : function(result) {// 提交成功
			if (result.success) {
				encodeOldPwd = result.msg;
				//加密新密码
				$.ajax({
					type : "POST", // post提交方式默认是get
					dataType : 'json',
					url : basePath+'/encodeRegister',
					data : {
						code : $('#pwd').val()
					},
					error : function(data) { // 设置表单提交出错
						alert('网络不稳定，请重试~~~');
					},
					success : function(result) {// 提交成功
						if (result.success) {
							encodeNewPwd = result.msg;
							
							
							
							
							//调用修改密码接口
						    $.ajax({
						       type:"POST",   //post提交方式默认是get
						       dataType:'json', 
						       url:"<%=request.getContextPath()%>/adminChagnePwd", 
						       data : {
						    	   oldPwd :encodeOldPwd,
						    	   pwd :encodeNewPwd
						       },
						       error:function(data) {// 设置表单提交出错 
						    	   $('#loading-mask').hide();
						    	   $.messager.alert('提示','系统出现异常,请联系管理员','error');
						       },
						       success:function(result) {
						    	   $('#loading-mask').hide();    	   
						    	   //$.messager.alert('提示',result.msg,'info');  
						    	   if(result.msg=="操作成功"){
						    		   $('#pwdModify').window('close');
						    		   //退出登录
						    		   $.ajax({
			                               type:"POST",   //post提交方式默认是get
			                               dataType:'json',
			                               url:"<%=request.getContextPath()%>/adminLogout",
			                               error:function(request) {// 设置表单提交出错
			                              	 $.messager.alert('提示','系统出现异常,请联系管理员','error');
			                               },
			                               success:function(data) {
			                            	   $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
			                            	   $("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});
			                            	   history.go(0);
			                               }
			                         }); 
						    	   }
						       }
						 	});	
						} else {
							alert('加密出现异常!');
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
<body class="easyui-layout" style="overflow-y: hidden;background:white;">
<noscript>
<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
    抱歉，请开启脚本支持！
</div>
</noscript>
 
<!-- 修改密码窗口 --> 
<div id= "pwdModify" name ="pwdModify" class="easyui-dialog" title="修改密码" 
	data-options ="iconCls:'icon-search'" style="width :320px;height:170px; padding:10px ;" modal="true" resizable= "false" closed ="true">


	<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
	    <input type="hidden" name="id" id ="id"/>
	    <table>
	        <tr>
	            <td>原密码：</td> 
	            <td><input type="password" name="oldPwd" id ="oldPwd" maxlength="20" required="true" class="easyui-validatebox" style="width:150px;" validType="length[6,20]" /></td>                       
	        </tr>
	        <tr>
	        	<td>新密码：</td>
	            <td><input type="password" name="pwd" id ="pwd" maxlength="20" required="true" class="easyui-validatebox" style="width:150px;" validType="length[6,20]"/></td>
	        </tr>                  
	    </table>
	</div>
	<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
	    <a id="btnModify" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >确定</a> 
	    <a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
	</div>    
</div> 

	<div id="loading-mask" style="position:absolute;top:0px; left:0px; width:100%; height:100%; background:#D2E0F2; z-index:20000">
		<!--
		<div id="pageloading" style="position:absolute; top:50%; left:50%; margin:-120px 0px 0px -120px; text-align:center;  border:2px solid #8DB2E3; width:200px; height:40px;  font-size:14px;padding:10px; font-weight:bold; background:#fff; color:#15428B;">
		    <img src="images/loading.gif" align="absmiddle" /> 正在加载中,请稍候...
		</div>
		--> 
	</div>
	
	<div id="loading-mask-processing" style="display:none;position:absolute;top:0px; left:0px; width:100%; height:100%; background: none transparent scroll repeat 0% 0%; z-index:20000">
		<div id="pageloading-processing" style="position:absolute; top:50%; left:44%; margin:-60px 0px 0px -120px; text-align:center;  border:2px solid #8DB2E3; width:400px; height:30px;  font-size:14px;padding:10px; font-weight:bold; background:#fff; color:#15428B;"> 
	    	<img src="${ctx}/skin/plugins/easyui/themes/black/images/loading.gif" align="absmiddle" /> 正在执行中,请稍候...
	    </div>
	</div>
	
	<div data-options="region:'north',border:true" style="overflow:hidden;height:25px;background:#1b5177;">
		<div class="header">
			<div id="jnkc"><%=VersonConstant.projectName %></div>
		</div>
        <div class="con">
             <li class="u_out"><a href="#" id="loginOut" style="color:white" >注销</a></li>             
             <li class="u_pws"><a href="#" style="color:white" onclick="openWin_modify()" >修改密码</a></li>
			 <li class="u_name">欢迎您：${sessionScope.adminSessionUser.adminUserName}(<font color="red">${sessionScope.adminSessionUser.roleName}</font>)
        </div>
	</div>
	
 	<div data-options="region:'south',border:true" style="overflow:hidden;height:25px;background:#1b5177;">
 		<div class="footer" >
             <div id="jnkc">版本号：<%=VersonConstant.currentVerson %> <a href="javascript:alert('这是一个彩蛋。。。')" target="_blank"><%=VersonConstant.teamName %></a></div>
        </div>
 	</div>
 	
    <div region="west" split="false"  title="导航菜单" style="width:180px;" id="west">
		<div id="nav">
			<ul id="menu_tree" class="easyui-tree" data-options="animate:true,dnd:true"></ul>
		</div> 
    </div>
    <div id="mainPanle" region="center" style="overflow-y:hidden">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
			<div title="欢迎页面" style="padding:20px;overflow:hidden;" >
				${welcomeObj}
			</div>
		</div>
    </div>
</body>
<script type="text/javascript" src='${ctx}/skin/js/admin/base/admin_main.js'> </script>
</html>