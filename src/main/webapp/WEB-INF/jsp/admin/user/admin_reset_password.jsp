<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragrma", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>重置用户密码</title>
<link rel="stylesheet" type="text/css" href="${ctx}/skin/plugins/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/skin/plugins/easyui/themes/icon.css" />

<script type="text/javascript" src="${ctx}/skin/plugins/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/skin/plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/skin/plugins/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/common/easyui-common.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/web/jquery.validate.js"></script>
	<script>
    $.extend($.fn.textbox.defaults.rules, {
        mobile: {// 验证联系方式
            validator: function (value) {
                return  /^0?1\d{10}$/i.test(value);
            },              
            message: '请输入11位手机号码'
       },
    });
    
  //form表单提交手机号
	function submitUserPhone(){
		$('#submitPhoneForm').form('submit',{
	        onSubmit:function(){
	        	//easyui的校验
	        	if($(this).form('enableValidation').form('validate')){
	        		return true;
	        	}else{
	        		return false;
	        	}
	        },
			success:function(result) {
				result = eval("("+result+")");
				if(result.success){					
					$('#resetPasswordDiv').dialog('open');
				}
				else{
					$.messager.alert("提示", result.msg);
				}
		    },
	        error:function(status){
	        	 $.messager.alert('提示','系统出现异常,请联系管理员','error'+status);
	        }
	    });
	}
  
	//重置密码
	function resetUserPassword(){
		var phone = $('#phone').val();

		$.ajax({
			type : "POST", //post提交方式默认是get
			dataType : 'json',
			url : basePath + '/admin/user/resetUserPasswordByAdmin',
			data : {
				phone : phone,
			},
			error : function(data) {
				$('#loading-mask').hide();
				$.messager.alert('提示', '系统出现异常,请联系管理员', 'error');
			},
			success : function(result) {
				$.messager.alert('提示', '重置成功！新密码：123456');
				$('#phone').textbox('setValue','');
				$('#resetPasswordDiv').dialog('close');
			}
		});
	}
	</script>
</head>
<body>
<!-- 输入手机号判定学员是否存在 -->
	<div id="submitPhonePanel" name="submitPhonePanel" class="easyui-panel" 
		style="padding: 30px;" 
		data-options="region:'center',split:true">
		<form id="submitPhoneForm" name="submitPhoneForm" action="${ctx}/admin/user/fingStudentIsExist" method="post">
			<table cellpadding="30">
				<tr>
					<td>学员手机号：
						<input class="easyui-textbox" type="text" name="phone" id="phone" data-options="required:true,validType:'mobile'"/>
					</td>
					<td>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitUserPhone();">一键重置</a> 
					</td>
				</tr>
				
				
			</table>
		</form>
	</div> 
	<!-- 输入手机号判定学员是否存在 -->
	
	<!-- 重置密码窗口 -->
    <div id="resetPasswordDiv" name="resetPasswordDiv" class="easyui-dialog" title="重置密码"
		style="width: 400px; height: 150px; padding: 10px;" modal="true"
		resizable="false" closed="true"
		data-options="iconCls:'icon-edit',
		 buttons: [{
	         text:'确认',
	         iconCls:'icon-edit',
	         handler:function(){
	         	resetUserPassword();
	         }
	      	},{
	         text:'取消',
	         iconCls:'icon-cancel',
	         handler:function(){
	         	$('#resetPasswordDiv').dialog('close');
	         }
	      }] ">
		<table>
			<tr><td>重置后密码为：123456，确认重置？</td></tr>
		</table>
    </div>
    <!-- 重置密码窗口 -->
</body>
</html>