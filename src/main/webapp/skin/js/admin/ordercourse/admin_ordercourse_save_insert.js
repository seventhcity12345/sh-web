/**
 * 选择源合同
 * @desc 因为源合同下拉框里返回出的数据是一个逗号分割的字符串，因此需要做解析。
 * @author athurn
 * @param rec
 */
function selectRenewalOrderCourse(rec){
	//获取课程体系
	var tempCategoryType = rec.strBuf.split(',')[1];
	//给全局变量赋值==>续约(源合同的优惠价),用于减差价
	resource_final_discount_price = rec.strBuf.split(',')[5];
	
	//只能续约商务英语和通用英语
	if(tempCategoryType == 'category_type1' || tempCategoryType == 'category_type2' ){
		//alert(tempCategoryType);
		
		//从课程包中获取的课程体系
		var categoryType = $('#hidden_category_type').val();
		//从课程包中获取的有效期（x个月）
		var limitShowTime = $('#hidden_limit_show_time').val();
		//在续约合同的场景中，在“续约合同”下拉框中，右侧需要选择一个“优惠价”，这个价格用于计算差价
		$('#renewalOrderCourseRealPrice').numberbox("setValue",resource_final_discount_price);
		
		//alert(categoryType + ", " + limitShowTime);
		//alert("limitShowTime != null : "+ limitShowTime != null && limitShowTime != '' && limitShowTime != 'null');
		if(limitShowTime != null && limitShowTime != '' && limitShowTime != 'null'){
			checkRenewalOrderCoursePackage(categoryType, limitShowTime);	
		}
		
		//modify by komi 2016年6月3日10:37:53 原合同包修改时，优惠价改动联动差额改动
		//最终支付价/差价=续约合同的优惠价-源合同的优惠价(被续约合同的优惠价)
		$('#total_final_price').numberbox(
				'setValue',Number($("#total_real_price").numberbox('getValue')) - Number(resource_final_discount_price));
	
	}else{
		$.messager.alert('提示', '不能续约主题课！');
	}
}


/**
 * author:athrun 2016年3月30日16:12:08
 * 初始化续约合同（添加了只有续约合同专享的一些逻辑）
 */
function initRenewalOrderCourse(){
	/**
	 * modify by athrun.cw 2016年3月29日17:15:32
	 * 续约时候初始化
	 */
	$("#renewalOrderCourseTable").html("");
	
	var tempStr = "<tr id=\"renewalTr\" name=\"renewalTr\"><td>原始合同:</td>"+
	        "<td><input class=\"easyui-combobox\" id=\"renewalOrderCourseKeyId\" name=\"renewalOrderCourseKeyId\" " +
			" data-options=\"required:true,editable:false\" style=\"width:150px;\"/></td>"+
			
			//modify by komi 2016年6月3日10:37:53 
			//在续约合同的场景中，在“续约合同”下拉框中，右侧需要选择一个“优惠价”，这个价格用于计算差价
			"<td>优惠价：<input class=\"easyui-numberbox\" id=\"renewalOrderCourseRealPrice\" name=\"renewalOrderCourseRealPrice\" " +
			"data-options=\"required:true,editable:false\" style=\"width:80px;\"/> 元</td></tr>";
	
	$("#renewalOrderCourseTable").append(tempStr);
	//alert(tempStr);
	/**
	 * 获取可续约的合同列表放在下拉框中
	 * （找他爹）
	 */
	$('#renewalOrderCourseKeyId').combobox({
		url: basePath + "/admin/orderCourse/findNeedRenewalOrderCourseListByUserId/" + userId,
		valueField:'strBuf',
		textField:'course_package_name',
		panelHeight: "auto",
		onSelect:function(rec){
			//用户在选择源合同下拉框时的一些逻辑和判断，例如判断合同体系等等
			selectRenewalOrderCourse(rec);
	    },
	});
	
	//渲染源合同下拉框旁边的优惠价数字框
	$('#renewalOrderCourseRealPrice').numberbox({});
	
	//初始化差价
	initTotalFinalPrice();
}

//新增合同
function insertContract(flag) {
	//清除 缓存
	resource_final_discount_price = 0;
	
	var row = $('#dataGrid1').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请先选中一条数据！');
		return;
	}
	var user_id = row[0].user_id;
	/**
	 * 判断合同入口
	 */
	if(flag=="renewal_insert"){
		//续约合同
		//初始化续约标识
		renewalOrderCourse = "renewalOrderCourse";
		userId = user_id;
		initRenewalOrderCourse();
	}else{
		//正常合同
		//去除緩存影响
		renewalOrderCourse = null;
		userId = null;
		//初始化续约来源合同框
		$("#renewalOrderCourseTable").html("");
		$("#totalFinalPriceTable").html("");
		emptyGiftTimeInnerHTML();
	}
	
	$('#editContractForm').form('clear');
	initContract();
	
	$("#seller").textbox('setValue', $('#hidden_seller').val());//销售
	$("#user_id").val(user_id);
	$("#user_phone").textbox('setValue', row[0].phone);
	$("#idcard").textbox('setValue', row[0].idcard);
	$("#user_name").textbox('setValue', row[0].user_name);
	$("#hidden_current_level").val(row[0].current_level);// 用户级别
	
	/**
	 * modify by athrun.cw 2016年3月29日16:16:18
	 * 添加了续约的入口
	 */
	//判断是否还有其他的合同没有支付
	$.ajax({
		type : "POST",
		dataType : 'json',
		timeout : 8000,
		url : basePath+'/admin/orderCourse/whetherCanMakeNewOrder',
		data : {
			user_id : user_id
		},
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus == "timeout") {
				alert("请求超时，请重新查看！");
			} else {
				alert('系统出现异常,请联系管理员!');
				alert(textStatus);
			}
		},
		success : function(result) {
			if (result.success) {
				$('#editContractDiv').dialog('open');
			} else {
				//存在未支付的其他合同
				alert(result.msg);
				return;
			}
		}});

}



// modified by ivan.mgh,2016年4月21日13:56:13
//---------------start------------------
// 拟定合同
function insertContractFromCrm() {
	// 清除 缓存
	resource_final_discount_price = 0;

	// 去除緩存影响
	renewalOrderCourse = null;
	// 初始化续约来源合同框
	$("#renewalOrderCourseTable").html("");
	$("#totalFinalPriceTable").html("");
	emptyGiftTimeInnerHTML();

//	var order_original_type = row[0].order_original_type;
//	var total_final_price = row[0].total_final_price;
//	var from_path = row[0].from_path;

	$('#editContractForm').form('clear');
	initContract();
	
	var user_id = $("#crm_hidden_user_id").val();

	$("#seller").textbox('setValue', $('#crm_hidden_seller').val());// 销售
	$("#user_id").val(user_id);
	$("#user_phone").textbox('setValue', $("#crm_hidden_user_phone").val());
	
	// 以下三个属性注册时均没有，拟合同时手动输入即可
//	$("#idcard").textbox('setValue', row[0].idcard);
//	$("#user_name").textbox('setValue', $("#crm_hidden_user_name").val());
//	$("#hidden_current_level").val($("#crm_hidden_cl").val());// 用户级别

	// 拟定合同
//	if (flag != null && (flag == "insert" || flag == "renewal_insert")) {
		/**
		 * modify by athrun.cw 2016年3月29日16:16:18 添加了续约的入口
		 */
		// 判断是否还有其他的合同没有支付
		$.ajax({
			type : "POST",
			dataType : 'json',
			timeout : 8000,
			url : basePath + '/admin/orderCourse/crm/whetherCanMakeNewOrder?auth='+auth,
			data : {
				user_id : user_id
			},
			error : function(jqXHR, textStatus, errorThrown) {
				if (textStatus == "timeout") {
					alert("请求超时，请重新查看！");
				} else {
					alert('系统出现异常,请联系管理员!');
					alert(textStatus);
				}
			},
			success : function(result) {
				if (result.success) {
					$('#editContractDiv').dialog('open');
				} else {
					// 存在未支付的其他合同
					alert(result.msg);
					return;
				}
			}
		});
//	}
}
//---------------end------------------

/**
 * 用于续约合同的相关校验（拟定续约合同时）
 * 1.续约合同的时效必须大于源合同
 * 2.源合同和老合同的体系必须一致
 * 3.请先选择要续约的合同
 * @author athrun
 */
function checkRenewalOrderCoursePackage(categoryType, limitShowTime,limitShowTimeUnit){
	/**
	 * modify athrun.cw 2016年3月30日18:15:47
	 * 如果是续约的话，需要判断category
	 */
	if(renewalOrderCourse != null && renewalOrderCourse == 'renewalOrderCourse'){
		//获取源合同的主键
		var renewalOrderCourseKeyId = $('#renewalOrderCourseKeyId').combobox('getValue');
		
		//判定源合同key是否为空，测试后没问题可以删除
		/*if(renewalOrderCourseKeyId=="" || renewalOrderCourseKeyId =="null"){
			$.messager.alert('提示', '请先选择要续约的合同!');
			$('#user_from_type').combobox('setValue', '');
			initContract();
			$("#limit_show_time").textbox('setValue', '');
			$("#course_package_id").combobox('setValue', '');
			
			//$('#editContractForm').form('clear');
			return;
		}*/
		var renewalOrderCourseKeyIdArr =  renewalOrderCourseKeyId.split(',');
		
		resource_final_discount_price = renewalOrderCourseKeyIdArr[5];
		
		//获取选择的源合同的category_type,limit_show_time
		if(renewalOrderCourseKeyIdArr[1] != categoryType){
			$.messager.alert('提示', "请选择类别" + renewalOrderCourseKeyIdArr[3] + "的合同！");
			initContract();
			$("#limit_show_time").textbox('setValue', '');
			$("#course_package_id").combobox('setValue', '');
			$("#course_package_id").combobox('hidePanel');
			//$('#editContractForm').form('clear');
			return;
		}
		
		//modified by komi 2017年1月23日10:17:47 需求486，不需要大于源合同时效了
//		if((limitShowTime - renewalOrderCourseKeyIdArr[2]) <= 0){
//			
//			$.messager.alert('提示', "请选择时效大于" + renewalOrderCourseKeyIdArr[2] + "的课程包！");
//			initContract();
//			$("#limit_show_time").textbox('setValue', '');
//			$("#course_package_id").combobox('setValue','');
//			//modify by seven 2016年7月15日18:32:36 修改bug391续约合同时，选择比当前合同月数小的课程包时，可以多次提交，弹出多个提示框
//			$("#course_package_id").combobox('hidePanel');
//			$("#total_final_price").numberbox('setValue', '');
//			//$('#editContractForm').form('clear');
//			return;
//		}
		
		//课程包时效单位不同，不能续约
		if(limitShowTimeUnit != renewalOrderCourseKeyIdArr[6]){
			var unitName = "月";
			if(renewalOrderCourseKeyIdArr[6] == 1){
				unitName = "天";
			}
			$.messager.alert('提示', "请选择时效为：'" + unitName + "'的合同！");
			initContract();
			$("#limit_show_time").textbox('setValue', '');
			$("#course_package_id").combobox('setValue', '');
			$("#course_package_id").combobox('hidePanel');
			//$('#editContractForm').form('clear');
			return;
		}
	}
}

