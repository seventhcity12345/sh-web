<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<input type="hidden" name="hidden_current_level"
	id="hidden_current_level" />
<input type="hidden" name="keyId" id="key_id" />
<input type="hidden" name="userId" id="user_id" />
<input type="hidden" name="coursePackageName" id="course_package_name" />
<input type="hidden" name="orderId" id="order_id" />
<input type="hidden" name="categoryType" id="hidden_category_type" />
<input type="hidden" name="hidden_limit_show_time"
	id="hidden_limit_show_time" />
<input type="hidden" name="limitShowTimeUnit" id="limitShowTimeUnit" />
<input type="hidden" name="coursePackageType" id="coursePackageType" />
<input type="hidden" name="coursePackagePriceOptionId"
	id="coursePackagePriceOptionId" />
<!-- modify by athrun.cw 2016年4月19日17:43:32 添加合同的折扣价 -->
<!--   <input type="hidden" name="totalDiscountPrice" id="total_discount_price" /> -->

<!-- modified by ivan.mgh，2016年5月18日17:30:00 -->
<input type="hidden" name="giftTime" id="hidden_gift_time" />


<!-- 选择要续约的合同 动态table 只有在insert续约合同中 才有 -->
<!--  <table id="renewalOrderCourseTable" name="renewalOrderCourseTable" cellpadding="5"></table> -->

<table cellpadding="5" class="yang">
	<tr>
		<td>学生来源:</td>
		<td><select class="easyui-combobox" name="userFromType"
			id="user_from_type" style="width: 250px;"
			data-options="panelHeight:'auto'
	    		,required:true,editable:false
	    		,onSelect:function(rec){selectUserFromType(rec);}">
				<option value="0">常规</option>
				<option value="1">线下售卖</option>
				<option value="2">线下转线上</option>
				<option value="3">团训</option>
				<option value="4">学员推荐</option>
				<option value="5">内部员工</option>
				<option value="6">测试</option>
				<option value="7">VIP学员</option>
		</select></td>
	</tr>
	<tr>
		<td>价格策略:</td>
		<td><select class="easyui-combobox" name="packagePriceName"
			id="packagePriceName" style="width: 250px;"
			data-options="panelHeight:'auto',
		    	required:true,editable:false,
		    	url:'${ctx}/admin/coursePackagePrice/findListByTime',
		    	valueField:'keyId',
		    	textField:'packagePriceName',
		    	onSelect:function(rec){selectPackagePriceName();}
	    	">
		</select></td>
	</tr>
	<tr>
		<td>课程包:</td>
		<td>
			<!-- modified by ivan.mgh,2016年4月25日13:48:08 --> <!------------start-------------->
			<c:choose>
				<c:when test="${authMap.crmflag }">
					<input class="easyui-combobox" name="coursePackageId"
						id="course_package_id" style="width: 250px;"
						data-options="
           onSelect:function(rec){
            selectCoursePackage(rec);
           },
           editable:false,
           valueField:'keyId',
           textField:'packageName',
           required:true,
           panelHeight:'350px'">
				</c:when>
				<c:otherwise>
					<input class="easyui-combobox" name="coursePackageId"
						id="course_package_id" style="width: 250px;"
						data-options="
           onSelect:function(rec){
            selectCoursePackage(rec);
           },
           editable:false,
           valueField:'keyId',
           textField:'packageName',
           required:true,
           panelHeight:'350px'">

				</c:otherwise>
			</c:choose> <!------------end-------------->

		</td>
	</tr>
	<tr>
		<td id="jsLimitShowTime">时效:</td>
		<td><input class="easyui-numberbox" name="limitShowTime"
			id="limit_show_time" data-options="required:true,readonly:true"
			style="width: 150px" /></td>
	</tr>
</table>
<!-- 课程包子项动态table -->
<table id="coursePackageOptionTable" name="coursePackageOptionTable"
	cellpadding="5">

</table>

<!-- 赠送课程动态table -->
<table id="giftCourseTable" name="giftCourseTable" cellpadding="5">

</table>

<!-- 赠送时间 -->
<table id="giftTimeTable" name="giftTimeTable" cellpadding="5">
</table>

<!-- 用户级别动态table -->
<table id="currentLevelTable" name="currentLevelTable" cellpadding="5">

</table>
<!-- 差价table 续约合同insert中才有 -->
<!-- <table id="totalFinalPriceTable" name="totalFinalPriceTable" cellpadding="5"> -->
<table cellpadding="5">
	<tr>
		<td>原始价:</td>
		<td><input class="easyui-numberbox" id="total_show_price"
			name="totalShowPrice" data-options="editable:false" /></td>
	</tr>
	<tr>


		<td>优惠价:</td>
		<td>
			<!-- modified by ivan.mgh,2016年4月25日11:58:41 --> <!------------start-------------->
			<c:choose>
				<c:when
					test="${authMap.crmflag && authMap.isCrmHavaChangeOrderCoursePricePermission}">
					<input class="easyui-numberbox" id="total_real_price"
						name="totalRealPrice"
						data-options="required:true,editable:true,validType:{length:[0,7]}" />

				</c:when>
				<c:when
					test="${authMap.crmflag != true && isHavaChangeOrderCoursePricePermission }">
					<input class="easyui-numberbox" id="total_real_price"
						name="totalRealPrice"
						data-options="required:true,editable:true,validType:{length:[0,7]}" />
				</c:when>
				<c:otherwise>
					<input class="easyui-numberbox" id="total_real_price"
						name="totalRealPrice" data-options="required:true,editable:false" />
				</c:otherwise>
			</c:choose> <!------------end-------------->
		</td>

	</tr>
	<!-- 
   <tr>
    <td>开课时间:</td>
    <td><input class="easyui-datetimebox" name="start_order_time" id="start_order_time" data-options="required:true,editable:false"/></td>
   </tr>
    -->

	<tr>
		<td>学员姓名:</td>
		<td><input class="easyui-textbox" name="userName" id="user_name"
			data-options="required:true,validType:'username'" /></td>
	</tr>
	<tr>
		<td>手机号:</td>
		<td><input class="easyui-textbox" name="userPhone"
			id="user_phone" data-options="editable:false" /></td>
	</tr>
	<tr>
		<td>身份证号:</td>
		<td><input class="easyui-textbox" name="idcard" id="idcard"
			data-options="required:true,validType:'idcard'" /></td>
	</tr>
	<tr>
		<td>销售顾问:</td>
		<td><input class="easyui-textbox" name="seller" id="seller"
			data-options="readonly:true" /></td>
	</tr>
	<!-- 
   <tr>
    <td>支付类型:</td>
    <td><input class="easyui-combobox"
      id="pay_type"
      name="pay_type"
      data-options="
           onSelect:function(rec){
            $('#pay_type').val(rec.pay_type);
           },
           editable:false,
           required:true,
           panelHeight:'auto'"></td>
   </tr>
   
    -->

	<tr>
		<td>指定LC:</td>
		<td><select class="easyui-combobox" name="learningCoachId"
			id="learningCoachId" style="width: 170px;"
			data-options="panelHeight:'100',editable:false,
		    	url:'${ctx}/admin/adminUser/findLcRota',
		    	valueField:'keyId',
		    	textField:'adminUserName'
	    	">
		</select></td>
	</tr>

	<tr>
		<td>订单流水号:</td>
		<td><input class="easyui-textbox" name="orderNumberId"
			id="order_number_id" data-options="readonly:true" /></td>
	</tr>
	
	<tr>
		<td>备注:</td>
		<td><input class="easyui-textbox" id="orderRemark"
			name="orderRemark" style="width: 400px; height: 200"
			data-options="multiline:true,required:true,validType:'length[1,2000]'" />
		</td>
	</tr>
	
</table>

