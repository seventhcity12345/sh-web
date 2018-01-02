//修改续约合同时候，需要保存原来合同的category_type&& limit_show_time
var resourceCourseType = null;
var resourceLimitTime = null;
var resourceLimitTimeUnit = null;

//更新合同
function updateContract() {
	//清除 缓存
	resource_final_discount_price = 0;
	
	//清除缓存
	resourceCourseType = null;
	resourceLimitTime = null;
	resourceLimitTimeUnit = null;
	
	//初始化 消除缓存
	local_final_real_price = null;
	renewalOrderCourse = null;
	userId = null;
	$("#totalFinalPriceTable").html("");
	
	var row = $('#dataGrid1').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请先选中一条数据！');
		return;
	}
	
	//不存在未支付的其他合同
	// 判断合同状态,销售拟定完的合同，潜客一旦开始支付，任何人就不能再对合同作任何改动。
	if(row[0].order_status == '4' || row[0].order_status == '5' || row[0].order_status == '6' || row[0].order_status == '7'){
		$.messager.alert('提示', '已经开始付款，不能修改合同！');
		return;
	}
	
	//判断价格策略是否过期，过期则不能修改
	if(new Date().getTime() >= row[0].package_price_end_time){
		$.messager.alert('提示', '价格政策已失效，不能修改合同！');
		return;
	}
	
	if(row[0].phone == null || row[0].phone == ''){
		$.messager.alert('提示', '手机号不能为空！');
		return;
	}
	
	var user_id = row[0].user_id;
	
	var order_original_type =  row[0].order_original_type;
	var total_final_price =  row[0].total_final_price;
	
	//alert('1='+local_final_real_price);
	local_final_real_price = total_final_price;
	//alert('2='+local_final_real_price);
	$('#editContractForm').form('clear');
	initContract();
	
	/**
	 * 修改合同
	 * 续约
	 */
	if(order_original_type != null && order_original_type == '1'){
		//初始化续约标识
		initTotalFinalPrice();
		//初始化续约标识
		renewalOrderCourse = "updateRenewalOrderCourse";
		userId = user_id;
		var from_path = row[0].from_path.split(',');
		$('#renewalOrderCourseKeyId').val(from_path[from_path.length - 2]);
		//alert("----:"+$('#renewalOrderCourseKeyId').val());
		resourceCourseType = row[0].category_type;
		
		//所有limit_show_time和 total_final_price 限制都是源合同的，
		resourceLimitTime = row[0].renewaled_limit_show_time;
		resourceLimitTimeUnit = row[0].renewaled_limit_show_time_unit
		resource_final_real_price = row[0].renewaled_total_final_price;
		resource_final_discount_price = row[0].renewaled_total_real_price;
	}
	
	var order_id = row[0].key_id;
	
	$("#order_id").val(order_id);
	global_order_id = order_id;
	// 订单id
	$("#order_number_id").textbox('setValue', order_id);
	//合同备注
	$("#orderRemark").textbox('setValue',row[0].order_remark);
	//用户来源
	$('#user_from_type').combobox('setValue', row[0].user_from_type);
	//手机号
	$("#user_phone").textbox('setValue', row[0].phone);
	$("#seller").textbox('setValue', $('#hidden_seller').val());//销售
	$("#user_id").val(user_id);
	$("#user_phone").textbox('setValue', row[0].phone);
	$("#idcard").textbox('setValue', row[0].idcard);
	$("#user_name").textbox('setValue', row[0].user_name);
	$("#hidden_current_level").val(row[0].current_level);// 用户级别
	
	//选中价格策略
	if (row[0].course_package_price_id != null && row[0].course_package_price_id != "null"
			&& row[0].course_package_price_id != "") {
		$('#packagePriceName').combobox('select', row[0].course_package_price_id);
	}
	
	$("#learningCoachId").combobox('setValue', row[0].learning_coach_id);
	$("#learningCoachId").combobox('setText', row[0].learning_coach_name);
	
	//需要先加载价格政策后，加载完课程包列表，才能选中
	setTimeout(function() {
		// 如果以前已经有过编辑合同，需要选中课程包下拉框。
		if (row[0].course_package_id != null && row[0].course_package_id != "null"
				&& row[0].course_package_id != "") {
			$('#course_package_id').combobox('select', row[0].course_package_id);
		}
		
		$('#total_final_price').textbox('setValue', local_final_real_price);
		
		// modified by ivan.mgh，2016年5月18日20:15:03
		// --------------start----------------
		// modified by komi 2016年6月8日11:07:56  原来是setValue，不会计算总价，改为select
		if(row[0].gift_time != null){
			$('#giftTime_').combobox('select', row[0].gift_time);
		}
		// --------------end----------------
		
		$('#editContractDiv').dialog('open');
	},200);
	
}


//校验 续约（更新合同）
function checkUpdateRenewalOrderCoursePackage(categoryType, limitShowTime,limitShowTimeUnit){
	/**
	 * modify athrun.cw 2016年4月8日14:35:03
	 * 如果是续约的话，需要判断category
	 */
	if(renewalOrderCourse != null && renewalOrderCourse == 'updateRenewalOrderCourse'){
		var renewalOrderCourseKeyId = $('#renewalOrderCourseKeyId').val();
		if(renewalOrderCourseKeyId=="" || renewalOrderCourseKeyId =="null"){
			$.messager.alert('提示', '该合同数据有误，不是续约的合同!');
			$('#user_from_type').combobox('setValue', '');
			initContract();
			$("#limit_show_time").textbox('setValue', '');
			$("#course_package_id").combobox('setValue', '');
			
			//$('#editContractForm').form('clear');
			return;
		}
		
		//获取选择的源合同的category_type,limit_show_time
		if(resourceCourseType != categoryType){
			$.messager.alert('提示', "请选择类别相同的合同！");
			initContract();
			$("#limit_show_time").textbox('setValue', '');
			$("#course_package_id").combobox('setValue', '');
			$("#course_package_id").combobox('hidePanel');
			//$('#editContractForm').form('clear');
			return;
		}
		
		//modified by komi 2017年1月23日10:17:47 需求486，不需要大于源合同时效了
//		if((limitShowTime - resourceLimitTime) <= 0){
//			$.messager.alert('提示', "请选择时效大于" + resourceLimitTime + "的课程包！");
//			initContract();
//			$("#limit_show_time").textbox('setValue', '');
//			$("#course_package_id").combobox('setValue', '');
//			$("#total_final_price").numberbox('setValue', '');
//			$("#course_package_id").combobox('hidePanel');
//			//$('#editContractForm').form('clear');
//			return;
//		}
		
		//课程包时效单位不同，不能续约
		if(limitShowTimeUnit != resourceLimitTimeUnit){
			var unitName = "月";
			if(resourceLimitTimeUnit == 1){
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