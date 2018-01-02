<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="com.webi.hwj.util.SessionUtil" %>
<%@ page import="com.webi.hwj.bean.SessionAdminUser" %>
<%@ include file="/WEB-INF/jsp/common/taglib.jsp"%>
<%
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragrma", "no-cache");
	response.setDateHeader("Expires", 0);
	
	SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
	boolean isHaveGiveLesson = sessionAdminUser.isHavePermisson("contract:giveLesson");
	boolean isHaveGiveTime = sessionAdminUser.isHavePermisson("contract:giveTime");
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>嗨英语-学员详情</title>
	<script type="text/javascript">
		var isHaveGiveLesson = '<%=isHaveGiveLesson%>';
		var isHaveGiveTime = '<%=isHaveGiveTime%>';
		
		var userInfoData;
		
		var userIdForUserInfoDetail = '${userId}';
		
		$(function(){ 
			$.ajax({
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
					if(userInfoData.user.is_student){
						$('#studentDiv').show();
					} else {
						$('#studentDiv').hide();
					}
					// 显示个人详情的东西
					$("#userInfoRealName").html(userInfoData.userInfo.real_name);
					$("#userInfoUserCode").html(userInfoData.user.user_code);
					
					if(1 == userInfoData.userInfo.gender){
						$("#userInfoGender").html("男");
					} else {
						$("#userInfoGender").html("女");
					}
					
					$("#userInfoPhone").html(userInfoData.user.phone);
					$("#userInfoIdcard").html(userInfoData.userInfo.idcard);
					$("#userInfoInitLevel").html(userInfoData.user.init_level);
					$("#userInfoEnglishName").html(userInfoData.userInfo.english_name);
					$("#userInfoAddress").html(userInfoData.userInfo.address);
					$("#userInfoEmail").html(userInfoData.userInfo.email);
					$("#userInfoLearnTool").html(userInfoData.userInfo.learn_tool);
					$("#userInfoCurrentLevel").html(userInfoData.user.current_level);
					$("#userInfoPersonalSign").html(userInfoData.userInfo.personal_sign);
					
					$("#userInfoAdminUserName").html(userInfoData.badminUser.admin_user_name);
					$("#userInfoCurrentLevelCoreCourseCount").html(userInfoData.generalStudentInfo.currentLevelCoreCourseCount);
					$("#userInfoCurrentLevelExtensionCourseCount").html(userInfoData.generalStudentInfo.currentLevelExtensionCourseCount);
					$("#userInfoCurrentMonthTmmWorkingTime").html(userInfoData.generalStudentInfo.currentMonthTmmWorkingTime);
					
					
				}
			});
		}); 
	
		// tab标签监听
		function tabOnSelectListener(title,index){
			switch(index)
			{
			case 0:
			 if( $('#followUpGrid').datagrid('getRows').length == 0) {
				 $('#followUpGrid')
				.datagrid(
						{
							url : basePath+'/admin/userFollowup/pagelist?user_id='+ userIdForUserInfoDetail
						});
			 }
			 break;
			case 1:
			 if( $('#dataGridCancelSubscribeCourse').datagrid('getRows').length == 0) {
				$('#dataGridCancelSubscribeCourse')
				.datagrid(
						{
							url : basePath+'/admin/user/findSubscribedCourseByUserId/'+ userIdForUserInfoDetail
						});
			 }
			 break;
			case 2:
			 if( $('#orderCourseInfo').datagrid('getRows').length == 0) {
				$('#orderCourseInfo')
				.datagrid(
						{
							url : basePath+'/admin/user/findOrderCourseListByUserId/'+ userIdForUserInfoDetail
						});
			 }
			  break;
			}
		}
	</script>
</head>
	<table>
		<td>
			<table>
				<tr><td>真实姓名：<span id="userInfoRealName"></span> </td>
					<td>ID：<span id="userInfoUserCode"></span></td>
					<td>性别：<span id="userInfoGender"></span></td>
				</tr>
				<tr><td>手机号：<span id="userInfoPhone"></span></td>
					<td>身份证：<span id="userInfoIdcard"></span></td>
					<td>学员初始等级：<span id="userInfoInitLevel"></span></td>
				</tr>
				<tr><td>英文名：<span id="userInfoEnglishName"></span></td>
					<td>所在地：<span id="userInfoAddress"></span></td>
				</tr>
				<tr><td>邮箱：<span id="userInfoEmail"></span></td>
					<td>学习工具：<span id="userInfoLearnTool"></span></td>
				</tr>
				<tr><td>学习等级：<span id="userInfoCurrentLevel"></span></td>
					<td>个性签名：<span id="userInfoPersonalSign"></span></td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr><td>LC：<span id="userInfoAdminUserName"></span></td></tr>
				<tr><td>本级已上core课程数：<span id="userInfoCurrentLevelCoreCourseCount"></span> </td></tr>
				<tr><td>本级已上ext课程数：<span id="userInfoCurrentLevelExtensionCourseCount"></span>节</td></tr>
				<tr><td>本月RSA课件学习时长：<span id="userInfoCurrentMonthTmmWorkingTime"></span></td></tr>
			</table>
		</td>
	</table>
	<table>
		<tr>
			<td>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="insertContract('insert')">拟定合同</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="editStudentInfo()">编辑学员信息</a>
				<span id="studentDiv">
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="subscribeCoursesButton()">代订课</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="insertContract('renewal_insert')">续约</a>
				</span>
			</td>
		</tr> 
	</table>
	
	<div id="userinfo_detail_tabs" name="userinfo_detail_tabs" class="easyui-tabs"
		style="width: 980px; height: 320px; padding: 4px;" 	data-options="onSelect:function(title,index){tabOnSelectListener(title,index);}">
		<div title="follow-up记录" >
			<table id="followUpGrid" name="followUpGrid" class="easyui-datagrid" fit="true" fitColumns="true" 
			data-options="pageList:[5],pagination:true,pageSize:5,fit:true,
				rownumbers:true,ctrlSelect:true,
				toolbar:'#userFollowUpTb',onDblClickRow :function(rowIndex,rowData){followUpEdit(1)}
				">
				<thead>
		            <tr>
		            	<th data-options="field:'ids',checkbox:true"></th> 
	            		<th data-options="field:'key_id',width:'100',sortable:true,hidden:true">主键</th>
						<th data-options="field:'user_id',width:'100',sortable:true,hidden:true">学生id</th>
						<th data-options="field:'learning_coach_id',width:'100',hidden:true,sortable:true">教务id</th>
						<th data-options="field:'create_date',width:'100',sortable:true,formatter:formatDate">记录日期</th>
						<th data-options="field:'followup_title',width:'100',sortable:true">主题</th>
						<th data-options="field:'followup_content',width:'100',sortable:true">内容</th>
						<th data-options="field:'learning_coach_name',width:'50',sortable:true">记录人</th>
					</tr>
		    	</thead>
	    	</table>
	    	
	    	<div id="userFollowUpTb" style="padding:5px;height:auto">
		        <div style="margin-bottom:5px">
		            <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="followUpEdit(0)">新增follow-up记录</a>
		        </div>
		    </div>
		    
		    <!-- 新增/修改窗口 -->
	    	<div id="followUpSaveDiv" name="followUpSaveDiv" class="easyui-dialog" title="follow-up记录"
	    		style="width:300px;height:210px;padding:10px;" 
	    		modal="true" resizable="false" closed="true"
	    		data-options="iconCls:'icon-edit',
	    		buttons: [{
	                    text:'确定',
	                    iconCls:'icon-ok',
	                    handler:function(){
	                        followUpFormSubmit();
	                    }
	                }] 
	    		" >  
	    		
	    		<form id="followUpForm" name="followUpForm" method="post" action="${ctx}/admin/userFollowup/save">
	    			<input type="hidden" name="followup_key_id" id="followup_key_id" />
	    			<input type="hidden" name="followup_user_id" id="followup_user_id" />
	    			
		            <table cellpadding="5">
						<tr>
							<td>主题:</td>
		                    <td style="width: 80%; height: 20%"><select
							class="easyui-combobox" id="followup_title"
							name="followup_title" style="width: 151px;"
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
		                    <td> 
		                    	<input class="easyui-textbox" type="text" name="followup_content" id="followup_content" 
		                    		style="height:60px" data-options="required:true,multiline:true,validType:'length[1,600]'"/>
		                    </td>
		                </tr> 
					</table>
		        </form>  
	    	</div>
		</div>
		<div title="订课详情" >
			<table id="dataGridCancelSubscribeCourse" name="dataGridCancelSubscribeCourse" class="easyui-datagrid" 
				fit="true" fitColumns="true" 
				data-options="pageList:[5],pagination:true,pageSize:5,fit:true,
					rownumbers:true,ctrlSelect:true,
					toolbar:'#cancelSubscribeBar'" > 
					<thead>
						<tr >
							<th data-options="field:'ids',checkbox:true"></th>
							<th data-options="field:'user_id',hidden:true">user_id</th>
							<th data-options="field:'course_id',hidden:true">course_id</th>
							<th data-options="field:'teacher_time_id',hidden:true">teacher_time_id</th>
							<th data-options="field:'course_type',hidden:true">course_type</th>
							<th data-options="field:'subscribe_id',hidden:true">subscribe_id</th>
							<th data-options="field:'order_type',width:'80',sortable:true">课程体系</th>
							<th data-options="field:'format_course_type',width:'100',sortable:true">课程类型</th>
							<th data-options="field:'course_title',width:'150',sortable:true">课程名称</th>
							<th data-options="field:'admin_user_name',width:'150',sortable:true">订课人</th>
							<th data-options="field:'teacher_name',width:'100',sortable:true">上课老师</th>
							<th data-options="field:'create_date',width:'80',sortable:true,formatter:formatDate">订课日期</th>
							<th data-options="field:'start_time',width:'120',sortable:true,formatter:formatDatehhmmss">上课时间</th>
							<th data-options="field:'end_time',width:'120',sortable:true,formatter:formatDatehhmmss">下课时间</th>
							<th data-options="field:'format_subscribe_status',width:'60',sortable:true">出席情况</th>
						</tr>
					</thead>
			</table>
			<!-- 新增/修改窗口 -->
	    	<div id="supplementDialogDiv" name="supplementDialogDiv" class="easyui-dialog" title="补课"
	    		style="width:300px;height:210px;padding:10px;" 
	    		modal="true" resizable="false" closed="true"
	    		data-options="iconCls:'icon-edit',
	    		buttons: [{
	                    text:'确定',
	                    iconCls:'icon-ok',
	                    handler:function(){
	                        supplementFormSubmit();
	                    }
	                }] 
	    		" >  
	    		
	    		<form id="supplementForm" name="supplementForm" method="post" action="${ctx}/admin/subscribeSupplement/supplyCourse">
	    			<input type="hidden" name="supplement_user_id" id="supplement_user_id" />
	    			<input type="hidden" name="supplement_user_name" id="supplement_user_name" "/>
	    			<input type="hidden" name="supplement_course_id" id="supplement_course_id" />
	    			<input type="hidden" name="supplement_course_title" id="supplement_course_title" />
	    			<input type="hidden" name="supplement_subscribe_id" id="supplement_subscribe_id" />
	    			<input type="hidden" name="supplement_course_type" id="supplement_course_type" />
		            <table cellpadding="5">
						<tr>
							<td>补课原因:</td>
		                    <td> 
		                    	<input class="easyui-textbox" name="supplement_reason" id="supplement_reason" 
		                    		style="height:80px" data-options="required:true,multiline:true,validType:'length[1,200]'"/>
		                    </td>
		                </tr> 
					</table>
		        </form>  
	    	</div>
		</div>
		<!-- cancelSubscribeBar声明 -->
		<div id="cancelSubscribeBar" style="padding: 5px; height: auto">
			<div style="margin-bottom: 5px">
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="cancelSubscribeCourse()">取消课程</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="subscribeSupplement()">补课</a>
			</div>
		</div>
		<!-- 非学员也能显示合同详情 -->
			<!-- 合同详情 -->
			<div title="合同详情" >
				<table id="orderCourseInfo" name="orderCourseInfo" class="easyui-datagrid" 
					fit="true" fitColumns="true" nowrap="false"
					data-options="pageList:[5],pagination:true,pageSize:5,fit:true,
						rownumbers:true,singleSelect:true,toolbar:'#orderToolbar'
						,onDblClickRow:function(rowIndex,rowData){
						lookContract();}" >
					<thead>
						<tr>
							<th data-options="field:'ids',checkbox:true"></th>
							<th data-options="field:'key_id',hidden:true">key_id</th>
							<th data-options="field:'user_id',hidden:true">user_id</th>
							<th data-options="field:'user_name',hidden:true,width:'100'">user_name</th>
							<th data-options="field:'course_package_name',width:'100'">课程包名称</th>
							<th data-options="field:'admin_user_name',width:'100'">CC</th>
							<th data-options="field:'start_order_time',width:'100',formatter:formatDatehhmmss">合同开始日期</th>
							<th data-options="field:'end_order_time',width:'100',formatter:formatDatehhmmss">合同结束日期</th>
							<th data-options="field:'total_show_price',width:'100'">原价</th>
							<th data-options="field:'total_real_price',width:'100'">优惠价</th>
							<th data-options="field:'total_final_price',width:'100'">实际付款金额</th>
							<th data-options="field:'order_status',width:'100',formatter:formatContractStatus">合同状态</th>
							<th data-options="field:'courseCountStr',width:'100'">合同课程进度</th>
							<th data-options="field:'order_remark',width:'100'">备注</th>
						</tr>
					</thead>
				</table>
				<div id="orderToolbar" style="padding: 5px; height: auto">
				<%if(sessionAdminUser.isHavePermisson("contract:contractExtension")){%>
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="contractExtension()">合同延期</a>
				<%}%>
				</div>
			</div>
	</div>
	
	<!-- 合同延期弹窗 -->
			<div id="contractExtensionDiv" name="contractExtensionDiv" class="easyui-dialog" title="合同延期"
				style="width: 400px; height: 250px; padding: 10px;" modal="true"
				resizable="false" closed="true"
				data-options="iconCls:'icon-edit',
		    		buttons: [{
		                    text:'确认延期',
		                    iconCls:'icon-ok',
		                    handler:function(){
		                        submitContractExtension();
		                    }
		                }] 
		    		">
				<table cellpadding="5">
				注：延期时效单位为月时，ES和RSA和口语训练营和按月卖的OC课时会同步延期，<br>
				延期时效单位为天时，所有课程均不延期，只延期合同结束日期！！！
					<tr>
						<td>延期时效单位：</td>
						<td>
							<select class="easyui-combobox" id="extensionLimitShowTimeUnit" name="extensionLimitShowTimeUnit" 
								style="width:50px;"
								data-options="editable:false,required:true,panelHeight:'auto',
								onChange:function(recNew,recOld){selectLimitShowTimeUnit(recNew,recOld);}">
								<option value='0'>月</option>
								<option value='1'>天</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>延期时长：</td>
						<td>
							<input class="easyui-textbox" id="extensionLimitShowTime" name="extensionLimitShowTime" 
								style="width:50px;"
								data-options="required:true,validType:'range[1,6]'"/>
						</td>
					</tr>
				</table>
			</div>
	
	<!-- 编辑学员信息 -->
	<div id="editStudentInfoDiv" name="editStudentInfoDiv" class="easyui-dialog" title="编辑学生信息"
		style="width: 650px; height: 400px; padding: 30px;" modal="true"
		resizable="false" closed="true"
		data-options="iconCls:'icon-edit',
    		buttons: [{
                    text:'保存',
                    iconCls:'icon-ok',
                    handler:function(){
                        submitEditStudentInfoForm();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-redo',
                    handler:function(){
                     $('#editStudentInfoDiv').dialog('close');
                    }
                }] 
    	">
		<form action="${ctx }/admin/user/editUserInfo" id="editStudentInfoForm" method="post">
			<table cellpadding="5">
					<tr>			
						<td>手机号</td>
						<td>
							<input type="hidden" name="key_id" id="edit_key_id" />
							<!--modified by komi 2016年8月11日10:41:18
								增加权限判定，有权限的才能修改手机号 -->
							<%if(sessionAdminUser.isHavePermisson("contract:updatePhone")){%>
								<input class="easyui-textbox" name="phone" id="edit_phone" data-options="required:false,validType:'length[11,11]'"/>
            				<%} else{%>
            					<input class="easyui-textbox" name="phone" id="edit_phone" data-options="required:false,validType:'length[11,11]',editable:false"/>
            				<%} %>
							
					&nbsp	学习工具
							<input class="easyui-textbox" name="learn_tool" id=edit_learn_tool data-options="required:false,validType:'length[0,50]'"/>
						</td>
						<!--  
						<td>密码</td>
						<td>
							<input class="easyui-textbox" name="pwd" id="edit_pwd" data-options=""/>
						</td>
						-->
					</tr>
					
					<tr>
						<td>真实姓名</td>
						<td>
							<input class="easyui-textbox" name="real_name" id="edit_real_name" data-options="required:false,validType:'username'"/>
						 &nbsp 英语名 &nbsp 
							<input class="easyui-textbox" name="english_name" id="edit_english_name" data-options="required:true,validType:['englishName','length[1,20]']"/>
						</td>
					</tr>
					
					<tr>
						<td>身份证号</td>
						<td>
							<input class="easyui-textbox" name="idcard" id="edit_idcard" data-options="required:false,validType:'idcard'" />
						&nbsp 性别 &nbsp &nbsp
							<input class="easyui-combobox" name="gender" id="edit_gender"  data-options="editable:false,panelHeight:'auto'"/>
						</td>
					</tr>
						
					<tr>
						<td>联系方式</td>
						<td>
						<input class="easyui-textbox" name="contract_func" id="edit_contract_func" data-options="required:true,validType:'phone'"/>
					&nbsp	邮箱   &nbsp &nbsp <input class="easyui-textbox" name="email" id="edit_email" data-options="required:false,validType:'email'"/>
						</td>
					</tr>
					
					<tr>
						<td>通信地址</td>
						<td><input type="hidden" name="province" id="provinceHidden" />
						<input class="easyui-combobox" name="edit_province" id="edit_province" style="width: 80px;"/>   
						省   &nbsp		
						<input type="hidden" name="city" id="cityHidden" />				 
						<input class="easyui-combobox" name="edit_city" id="edit_city" style="width: 150px;" />   
						 市   &nbsp					
						<input class="easyui-combobox" name="district" id="edit_district" style="width: 150px;" />
						 区/县  &nbsp 
						</td>			
					</tr>
					<tr>
						<td>详细地址</td>
						<td>
							<input class="easyui-textbox" name="address" id="edit_address" style="width: 500px;" data-options="required:false,validType:'length[0,40]'"/>
						</td>
					</tr>
					
					<tr>
						<td>个性签名</td>
						<td>
							<input class="easyui-textbox" name="personal_sign" id="edit_personal_sign" style="width: 500px;" data-options="required:false,validType:'length[0,100]'"/>
						</td>
					</tr>
					 
			</table>
		</form>
	</div>
	<!-- 编辑学员信息-->
	
	
	
	<!-- 预约你就把这里当成是一个空的容器就完了，为的就是打开一个dialog -->
    <div id="subscribeDialogContrallor" name="subscribeDialogContrallor" class="easyui-dialog" 
    	title="代订课" closed="true"
		style="width: 1024px; height: 500px;" 
		data-options="iconCls:'icon-search'">
 
		<!-- 查询出来的数据 显示位置 -->
		<table id="dataGridSubscribeCourse" name="dataGridSubscribeCourse" class="easyui-datagrid" fit="true"
		fitColumns="true" data-options="pageList:[20,40,60,80,100],pagination:true,pageSize:20,fit:true,
		rownumbers:true,ctrlSelect:true,toolbar:'#toolbarSubscribeCourse'" > 
			<thead>
				<tr >
					<th data-options="field:'ids',checkbox:true"></th>
					<th data-options="field:'user_id',width:'100',hidden:true">user_id</th>
					<th data-options="field:'course_id',width:'100',hidden:true">course_id</th>
					<th data-options="field:'teacher_time_id',width:'100',hidden:true">teacher_time_id</th>
					<th data-options="field:'course_type',width:'100',hidden:true">course_type</th>
					<th data-options="field:'teacher_name',width:'100'">上课老师</th>
					<th data-options="field:'start_time',width:'100',formatter:formatDatehhmmss">上课时间</th>
					<th data-options="field:'end_time',width:'100',formatter:formatDatehhmmss">结束时间</th>
				</tr>
			</thead>
		</table>
		<!-- bar声明 -->
		<div id="toolbarSubscribeCourse" style="padding: 5px; height: auto">
			<div style="margin-bottom: 5px">
				<input class="easyui-combobox" name="category_type" id="category_type" 
						data-options="editable:false"/>
				
				<input class="easyui-combobox" name="course_type" id="course_type" 
						data-options="onSelect:function(rec){
						  	selectCourseId(rec);
						}, 
					      	editable:false,
					      	required:true,
					      	panelHeight:'auto'"/>
				<input class="easyui-combobox" name="course_id" id="course_id" 
						data-options="required:true,editable:false"/>
						
				<!-- 时间日期框 -->
				<!-- <input class="easyui-datetimebox" name="date_time" id="date_time" 
						data-options="editable:false"/> -->
				<input class="easyui-datebox" name="date_time" id="date_time" 
						data-options="editable:false"/>
				<!-- 只有日期没有时间框 -->
				<!-- <input class="easyui-datebox" type="text" style="width:100px;" data-options="editable:false" id="startCollectionDate" name="startCollectionDate" /> -->
						
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="submitTeacherTime()">查询</a>		
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="userSubscribeThisCourse()">预约课程</a>
			</div>
		</div>
	</div>
 
	<!-- 拟定合同/拆分合同相关内容 -->
	<%@ include file="/WEB-INF/jsp/admin/ordercourse/ordercoursesave/admin_ordercourse_save_insert.jsp"%> 
	
	<script type="text/javascript" src="${ctx}/skin/js/web/angular.min.js"></script>
	<script type="text/javascript" src="${ctx}/skin/js/web/jquery.validate.js"></script>
	<script type="text/javascript" src="${ctx}/skin/js/web/messages_zh.js"></script>
	<script type="text/javascript" src="${ctx}/skin/js/admin/ordercourse/pay.orderlist.js"></script>
	<script type="text/javascript" src="${ctx}/skin/js/common/easyui-common.js"></script>
	<script type="text/javascript" src="${ctx}/skin/js/admin/user/admin_user_detail.js"></script>
	<script type="text/javascript" src="${ctx}/skin/js/admin/user/admin_user_detail_content.js"></script>
</html>
