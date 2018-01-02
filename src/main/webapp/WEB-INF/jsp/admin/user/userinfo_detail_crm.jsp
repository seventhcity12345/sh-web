<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="com.webi.hwj.util.SessionUtil"%>
<%@ page import="com.webi.hwj.bean.SessionAdminUser"%>
<%@ include file="/WEB-INF/jsp/common/taglib.jsp"%>
<%
  response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragrma", "no-cache");
			response.setDateHeader("Expires", 0);
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
<link rel="stylesheet" type="text/css"
	href="${ctx}/skin/plugins/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/skin/plugins/easyui/themes/icon.css" />
<script type="text/javascript"
	src="${ctx}/skin/plugins/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/skin/plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/skin/plugins/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${ctx}/skin/js/common/easyui-common.js"></script>
<script type="text/javascript"
	src="${ctx}/skin/js/web/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/skin/js/web/messages_zh.js"></script>
<script type="text/javascript"
	src="${ctx}/skin/js/admin/user/admin_user_detail.js"></script>
<script type="text/javascript"
	src="${ctx}/skin/js/admin/user/admin_user_detail_content.js"></script>
<script type="text/javascript"
	src="${ctx}/skin/js/admin/user/admin_user_detail_order.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>嗨英语-学员详情</title>
<script type="text/javascript">
	//格式化lc信息
	function formatLcInfo(lcInfo) {
		if (lcInfo) {
			return lcInfo;
		} else {
			return "暂无";
		}
	}

	var userInfoData;
	var userId = '{$userId}';
	$(function() {
		$
				.ajax({
					type : "GET", //post提交方式默认是get
					dataType : 'json',
					url : basePath + '/admin/user/lookUserDetailInfo/${userId}',
					error : function(data) { // 设置表单提交出错 
						$('#loading-mask').hide();
						$.messager.alert('提示', '系统出现异常,请联系管理员', 'error');
					},
					success : function(result) {
						userInfoData = result;
						// 待订课要不要显示
						if (userInfoData.user.is_student) {
							$('#studentDiv').show();
						} else {
							$('#studentDiv').hide();
						}
						// 显示个人详情的东西
						$("#userInfoRealName").html(
								userInfoData.userInfo.real_name);
						$("#userInfoUserCode")
								.html(userInfoData.user.user_code);

						if (1 == userInfoData.userInfo.gender) {
							$("#userInfoGender").html("男");
						} else {
							$("#userInfoGender").html("女");
						}

						$("#userInfoPhone").html(userInfoData.user.phone);
						$("#userInfoIdcard").html(userInfoData.userInfo.idcard);
						$("#userInfoInitLevel").html(
								userInfoData.user.init_level);
						$("#userInfoEnglishName").html(
								userInfoData.userInfo.english_name);
						$("#userInfoAddress").html(
								userInfoData.userInfo.address);
						$("#userInfoEmail").html(userInfoData.userInfo.email);
						$("#userInfoLearnTool").html(
								userInfoData.userInfo.learn_tool);
						$("#userInfoCurrentLevel").html(
								userInfoData.user.current_level);
						$("#userInfoPersonalSign").html(
								userInfoData.userInfo.personal_sign);

						$("#userInfoAdminUserName").html(
								userInfoData.badminUser.admin_user_name);
						$("#userInfoCurrentLevelCoreCourseCount")
								.html(
										userInfoData.generalStudentInfo.currentLevelCoreCourseCount);
						$("#userInfoCurrentLevelExtensionCourseCount")
								.html(
										userInfoData.generalStudentInfo.currentLevelExtensionCourseCount);
						$("#userInfoCurrentMonthTmmWorkingTime")
								.html(
										userInfoData.generalStudentInfo.currentMonthTmmWorkingTime);

						$("#lcName")
								.html(
										formatLcInfo(userInfoData.badminUser.admin_user_name));
						$("#lcWeixin").html(
								formatLcInfo(userInfoData.badminUser.weixin));
						$("#lcPhone").html(
								formatLcInfo(userInfoData.badminUser.telphone));
						$("#lcEmail").html(
								formatLcInfo(userInfoData.badminUser.email));

					}
				});
	});
</script>
</head>
<h3>学员信息</h3>
<table width=980>
	<td width='70%'>
		<table>
			<tr>
				<td>真实姓名：<span id="userInfoRealName"></span>
				</td>
				<td>ID：<span id="userInfoUserCode"></span></td>
				<td>性别：<span id="userInfoGender"></span></td>
			</tr>
			<tr>
				<td>手机号：<span id="userInfoPhone"></span></td>
				<td>身份证：<span id="userInfoIdcard"></span></td>
				<td>学员初始等级：<span id="userInfoInitLevel"></span></td>
			</tr>
			<tr>
				<td>英文名：<span id="userInfoEnglishName"></span></td>
				<td>所在地：<span id="userInfoAddress"></span></td>
			</tr>
			<tr>
				<td>邮箱：<span id="userInfoEmail"></span></td>
				<td>学习工具：<span id="userInfoLearnTool"></span></td>
			</tr>
			<tr>
				<td>学习等级：<span id="userInfoCurrentLevel"></span></td>
				<td>个性签名：<span id="userInfoPersonalSign"></span></td>
			</tr>
		</table>
	</td>
	<td width='20%'>
		<table>
			<tr>
				<td>LC：<span id="userInfoAdminUserName"></span></td>
			</tr>
			<tr>
				<td>本级已上core课程数：<span id="userInfoCurrentLevelCoreCourseCount"></span>
				</td>
			</tr>
			<tr>
				<td>本级已上ext课程数：<span
					id="userInfoCurrentLevelExtensionCourseCount"></span>节
				</td>
			</tr>
			<tr>
				<td>本月RSA课件学习时长：<span id="userInfoCurrentMonthTmmWorkingTime"></span></td>
			</tr>
		</table>
	</td>
</table>

<div id="userinfo_detail_tabs" name="userinfo_detail_tabs"
	class="easyui-tabs" style="width: 980px; height: 320px; padding: 4px;">
	<div title="follow-up记录">
		<table id="followUpGrid" name="followUpGrid" class="easyui-datagrid"
			fit="true" fitColumns="true"
			data-options="pageList:[5],pagination:true,pageSize:5,fit:true,
				rownumbers:true,url:'${ctx}/admin/userFollowup/pagelist?user_id=${userId}',ctrlSelect:true,
				toolbar:'#userFollowUpTb',onDblClickRow :function(rowIndex,rowData){followUpEdit(1)}
				">
			<thead>
				<tr>
					<th data-options="field:'ids',checkbox:true"></th>
					<th
						data-options="field:'key_id',width:'100',sortable:true,hidden:true">主键</th>
					<th
						data-options="field:'user_id',width:'100',sortable:true,hidden:true">学生id</th>
					<th
						data-options="field:'learning_coach_id',width:'100',hidden:true,sortable:true">教务id</th>
					<th
						data-options="field:'create_date',width:'100',sortable:true,formatter:formatDate">记录日期</th>
					<th data-options="field:'followup_title',width:'100',sortable:true">主题</th>
					<th
						data-options="field:'followup_content',width:'100',sortable:true">内容</th>
					<th
						data-options="field:'learning_coach_name',width:'50',sortable:true">记录人</th>
				</tr>
			</thead>
		</table>

		<div id="userFollowUpTb" style="padding: 5px; height: auto">
			<div style="margin-bottom: 5px">
				<a href="#" class="easyui-linkbutton" iconCls="icon-add"
					onclick="followUpEdit(0)">新增follow-up记录</a>
			</div>
		</div>

		<!-- 新增/修改窗口 -->
		<div id="followUpSaveDiv" name="followUpSaveDiv" class="easyui-dialog"
			title="follow-up记录"
			style="width: 300px; height: 210px; padding: 10px;" modal="true"
			resizable="false" closed="true"
			data-options="iconCls:'icon-edit',
	    		buttons: [{
	                    text:'确定',
	                    iconCls:'icon-ok',
	                    handler:function(){
	                        followUpFormSubmit();
	                    }
	                }] 
	    		">

			<form id="followUpForm" name="followUpForm" method="post"
				action="${ctx}/admin/userFollowup/save">
				<input type="hidden" name="followup_key_id" id="followup_key_id" />
				<input type="hidden" name="followup_user_id" id="followup_user_id" />

				<table cellpadding="5">
					<tr>
						<td>主题:</td>
						<td style="width: 80%; height: 20%"><select
							class="easyui-combobox" id="followup_title" name="followup_title"
							style="width: 151px;"
							data-options="panelHeight:'auto',editable:false,required:true">
								<option value="首Call">首Call</option>
								<option value="入门指导">入门指导</option>
								<option value="首课关怀">首课关怀</option>
								<option value="学员常规跟进">学员常规跟进</option>
								<option value="No Show跟进">No Show跟进</option>
								<option value="学员问题-邮件报备">学员问题-邮件报备</option>
								<option value="微信QQ答疑">微信QQ答疑</option>
								<option value="退费申请">退费申请</option>
								<option value="补课">补课</option>
								<option value="升级">升级</option>
								<option value="调整级别">调整级别</option>
								<option value="学员口测">学员口测</option>
								<option value="申请延期/延期证明">申请延期/延期证明</option>
								<option value="更换手机号码">更换手机号码</option>
								<option value="取消课程">取消课程</option>
						</select></td>
					</tr>

					<tr>
						<td>内容:</td>
						<td><input class="easyui-textbox" type="text"
							name="followup_content" id="followup_content"
							style="height: 60px"
							data-options="required:true,multiline:true,validType:'length[1,600]'" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div title="订课详情">
		<table id="dataGridCancelSubscribeCourse"
			name="dataGridCancelSubscribeCourse" class="easyui-datagrid"
			fit="true" fitColumns="true"
			data-options="pageList:[5],pagination:true,pageSize:5,fit:true,
					rownumbers:true,url:'${ctx}/admin/user/findSubscribedCourseByUserId/${userId}',ctrlSelect:true">
			<thead>
				<tr>
					<th data-options="field:'ids',checkbox:true"></th>
					<th data-options="field:'user_id',hidden:true">user_id</th>
					<th data-options="field:'course_id',hidden:true">course_id</th>
					<th data-options="field:'teacher_time_id',hidden:true">teacher_time_id</th>
					<th data-options="field:'course_type',hidden:true">course_type</th>
					<th data-options="field:'subscribe_id',hidden:true">subscribe_id</th>
					<th data-options="field:'order_type',width:'80',sortable:true">课程体系</th>
					<th
						data-options="field:'format_course_type',width:'100',sortable:true">课程类型</th>
					<th data-options="field:'course_title',width:'150',sortable:true">课程名称</th>
					<th
						data-options="field:'admin_user_name',width:'150',sortable:true">订课人</th>
					<th data-options="field:'teacher_name',width:'100',sortable:true">上课老师</th>
					<th
						data-options="field:'create_date',width:'80',sortable:true,formatter:formatDate">订课日期</th>
					<th
						data-options="field:'start_time',width:'120',sortable:true,formatter:formatDatehhmmss">上课时间</th>
					<th
						data-options="field:'end_time',width:'120',sortable:true,formatter:formatDatehhmmss">下课时间</th>
					<th
						data-options="field:'format_subscribe_status',width:'60',sortable:true">出席情况</th>
				</tr>
			</thead>
		</table>
	</div>

	<!-- 非学员也能显示合同详情 -->
	<!-- 合同详情 -->
	<div title="合同详情">
		<table id="orderCourseInfo" name="orderCourseInfo"
			class="easyui-datagrid" fit="true" fitColumns="true" nowrap="false"
			data-options="pageList:[5],pagination:true,pageSize:5,fit:true,
						rownumbers:true,url:'${ctx}/admin/user/findOrderCourseListByUserId/${userId}'
						,singleSelect:true
						,onDblClickRow:function(rowIndex,rowData){
						lookContract();}">
			<thead>
				<tr>
					<th data-options="field:'ids',checkbox:true"></th>
					<th data-options="field:'key_id',hidden:true">key_id</th>
					<th data-options="field:'user_id',hidden:true">user_id</th>
					<th data-options="field:'user_name',hidden:true,width:'100'">user_name</th>
					<th data-options="field:'course_package_name',width:'100'">课程包名称</th>
					<th data-options="field:'admin_user_name',width:'100'">CC</th>
					<th
						data-options="field:'start_order_time',width:'100',formatter:formatDatehhmmss">合同开始日期</th>
					<th
						data-options="field:'end_order_time',width:'100',formatter:formatDatehhmmss">合同结束日期</th>
					<th data-options="field:'total_show_price',width:'100'">原价</th>
					<th data-options="field:'total_real_price',width:'100'">优惠价</th>
					<th data-options="field:'total_final_price',width:'100'">实际付款金额</th>
					<th
						data-options="field:'order_status',width:'100',formatter:formatContractStatus">合同状态</th>
					<th data-options="field:'courseCountStr',width:'100'">合同课程进度</th>
					<th data-options="field:'order_remark',width:'100'">备注</th>
				</tr>
			</thead>
		</table>
	</div>
</div>

<!-- 查看合同 -->
<div id="lookContractNoButtonsDiv" name="lookContractNoButtonsDiv"
	class="easyui-dialog" title="查看合同"
	style="width: 600px; height: 550px; padding: 10px;" modal="true"
	resizable="false" closed="true" data-options="iconCls:'icon-edit'">
</div>

<table width=980>
	<td width='50%'>
		<h3>学员学习教练（LC）信息</h3>
		<table>
			<tr>
				<td>LC姓名：<span id="lcName"></span>
				</td>
			</tr>
			<tr>
				<td>LC微信号：<span id="lcWeixin"></span></td>
			</tr>
			<tr>
				<td>LC手机号：<span id="lcPhone"></span></td>
			</tr>
			<tr>
				<td>邮箱：<span id="lcEmail"></span></td>
			</tr>
			<tr>
				<td></td>
			</tr>
		</table>
	</td>
	<td width='50%'>
		<h3>值班技术支持（CS）信息</h3>
		<table>
			<tr>
				<td>LC值班电话：400-820-9169</td>
			</tr>
			<tr>
				<td>LC值班QQ：3131500197</td>
			</tr>
			<tr>
				<td>CS值班电话：021-34189116</td>
			</tr>
			<tr>
				<td>CS值班QQ：3385283164</td>
			</tr>
			<tr>
				<td>服务时间：10:30-19:30</td>
			</tr>
		</table>
	</td>
</table>

</html>
