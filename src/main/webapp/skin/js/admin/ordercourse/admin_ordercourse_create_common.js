//定义全局变量，标识续约
var renewalOrderCourse = null;
var userId = null;

// 拟定/修改合同相关js
var addGiftCount = 0;

// 全局的订单/合同ID
var global_order_id = "";

// 原始折扣价（用于cyndi的新需求）
var local_final_real_price = 0;

// 续约(源合同的价格)
var resource_final_real_price = 0;
// 源合同的优惠价(被续约合同的优惠价)
var resource_final_discount_price = 0;

// 优惠价和差价的变化次数（防止循环修改）
var totalRealPriceFlag = 0;
var totalFinalPriceFlag = 1;

//课程包原始价（要从数据库中拿，不再按页面计算课程包价格）
var coursePackageShowPrice = 0;

// 选择学员来源
function selectUserFromType(rec) {
	/**
	 * modify by athrun.cw 续约
	 */
	if (renewalOrderCourse != null
			&& renewalOrderCourse == "renewalOrderCourse") {
		var renewalOrderCourseKeyId = $('#renewalOrderCourseKeyId').combobox(
				'getValue');
		// alert("renewalOrderCourseKeyId"+renewalOrderCourseKeyId);
		if (renewalOrderCourseKeyId == "" || renewalOrderCourseKeyId == "null") {
			$.messager.alert('提示', '请先选择要续约的合同!');
			$('#user_from_type').combobox('setValue', '');
			return;
		}
	}
}

// 计算总价
function countTotal() {
	var tempShowPrice = coursePackageShowPrice;

	/**
	 * modified by komi 2016年8月30日11:48:34
	 * 课程包原价从数据库里拿，不再在页面计算（赠送课程、赠送时间等还是在页面计算）
	 *
	// 计算课程包里的默认价格
	var realPrice = $("input[id^='realPrice_']");// 课程包里的默认单价
	var courseCount = $("input[id^='courseCount_']");// 课程包里的默认数量
	for (var i = 0; i < realPrice.size(); i++) {
		// tempRealPrice += Number(realPrice[i].value)
		// * Number(courseCount[i].value);
		tempShowPrice += Number(realPrice[i].value)
				* Number(courseCount[i].value);
	}
	*/

	// 计算赠课里的价格
	var realPriceGift = $("input[id^='realPriceGift_']");// 原价(单价)
	var courseCountGift = $("input[id^='courseCountGift_']");// 课节数

	for (var i = 0; i < realPriceGift.size(); i++) {
		tempShowPrice += Number(realPriceGift[i].value)
				* Number(courseCountGift[i].value);
	}

	/**
	 * modified by komi 2016年8月30日11:48:34
	 * 赠送时间不再赠送课程，先默认赠送时间价格为0
	 * 
	// modified by ivan.mgh，2016年5月20日11:06:30
	// ---------------start----------------
	var sumUnitPrice = calculateGiftTimeMoney();
	tempShowPrice += Number(sumUnitPrice);
	// ---------------end----------------
	*/
	
	// 原始价
	$("#total_show_price").numberbox('setValue', tempShowPrice);
}

// 初始化合同
function initContract() {
	addGiftCount = 0;

	$("#giftCourseTable").html("");
	$("#coursePackageOptionTable").html("");
	$("#currentLevelTable").html("");
	
	//初始化赠送时间
	initGiftTimeInnerHTML();
}

// 删除赠送课程
function removeTr() {
	if (addGiftCount != 1) {
		$("#gift_" + addGiftCount).remove();
		addGiftCount--;
	} else {
		// 清空下拉框
		$("#courseCountGift_1").numberbox('setValue', '');
		$("#realPriceGift_1").numberbox('setValue', '');
		$('#courseTypeGift_1').combobox('select', '');
		$('#courseUnitTypeGift_1').combobox('clear');
	}
	countTotal();
}
// 新增赠送课程
function addGift(coursePriceVersion,course_count, real_price,course_unit_type,course_type_id) {
	if (isHaveGiveLesson == 'true') {
		$('#giftCourseTable').show();
	} else {
		$('#giftCourseTable').hide();
	}

	addGiftCount++;

	var tempStr = "<tr id=\"gift_" + addGiftCount + "\" name=\"gift_"
			+ addGiftCount + "\"><td>赠送课程:</td>";
	tempStr += "<td><input class=\"easyui-combobox\" id=\"courseTypeGift_"
			+ addGiftCount
			+ "\" name=\"courseTypeGift_"
			+ addGiftCount
			+ "\" data-options=\"editable:false\" style=\"width:150px;\"/></td>";
	tempStr += "<td><input class=\"easyui-numberbox\" name=\"courseCountGift_"
			+ addGiftCount
			+ "\" id=\"courseCountGift_"
			+ addGiftCount
			+ "\" style=\"width:50px\" data-options=\"min:1,max:200,onChange:function(){countTotal()}\"/>"
	tempStr += "<input class=\"easyui-combobox\" id=\"courseUnitTypeGift_" 
			+ addGiftCount
			+ "\" name=\"courseUnitTypeGift_" 
			+ addGiftCount 
			+ "\"data-options=\"editable:false\" style=\"width:50px;\"/>&nbsp;";
	tempStr += "<input class=\"easyui-numberbox\" name=\"realPriceGift_"
			+ addGiftCount
			+ "\" id=\"realPriceGift_"
			+ addGiftCount
			+ "\" style=\"width:50px\" data-options=\"editable:false,min:1,max:1000,onChange:function(){countTotal()}\"/>元&nbsp;";
	/**
	 * modified by komi 2016年7月6日14:52:03 
	 * 增加课程单位类型
	 */
	
	
	if (addGiftCount == 1) {
		tempStr += "<input type=\"button\" value=\"增加\" onclick=\"addGift("+coursePriceVersion+")\"/>&nbsp;<input type=\"button\" value=\"删除\" onclick=\"removeTr()\"/>";
	}

	tempStr += "</td></tr>";

	$("#giftCourseTable").append(tempStr);
	
	$('#courseUnitTypeGift_' + addGiftCount).combobox({
		panelHeight:'auto'
	});
	$('#courseCountGift_' + addGiftCount).numberbox({});
	$('#realPriceGift_' + addGiftCount).numberbox({});

	addGiftCourseCombo(coursePriceVersion,addGiftCount, course_count, real_price,course_unit_type,course_type_id);
}

//选择价格策略下拉框触发
function selectPackagePriceName(){
	//清空课程包id
	$('#course_package_id').combobox('setValue', '');
	//清空时效
	$("#limit_show_time").textbox('setValue','');
	//初始化合同数据
	initContract();
	
	var packagePriceNameId = $('#packagePriceName').combobox('getValue');
	
	//获取课程包
	var listUrl = getUrl(basePath + '/coursePackage/selectlist','keyId='+packagePriceNameId);
	$('#course_package_id').combobox('reload', listUrl);	
	
}


// 选择课程包下拉框触发
function selectCoursePackage(rec) {
	var packagePriceName = $('#packagePriceName').combobox('getValue');
	if (packagePriceName == "" || packagePriceName == "null") {
		$.messager.alert('提示', "请先选择价格策略!");
		$('#course_package_id').combobox('setValue', '');
		return;
	}

	initContract();
	 $('#hidden_category_type').val(rec.categoryType);
     $('#hidden_limit_show_time').val(rec.limitShowTime);
     $('#limitShowTimeUnit').val(rec.limitShowTimeUnit);
     $('#coursePackagePriceOptionId').val(rec.coursePackagePriceOptionId);
     $('#coursePackageType').val(rec.coursePackageType);
     

	var categoryType = rec.categoryType;
	var limitShowTime = rec.limitShowTime;
	var limitShowTimeUnit = rec.limitShowTimeUnit;

	// 新增续约订单
	if (renewalOrderCourse != null
			&& renewalOrderCourse == 'renewalOrderCourse') {
		checkRenewalOrderCoursePackage(categoryType, limitShowTime,limitShowTimeUnit);
	}
	// 修改续约订单
	if (renewalOrderCourse != null
			&& renewalOrderCourse == 'updateRenewalOrderCourse') {
		// alert('hidden_limit_show_time' + hidden_limit_show_time);
		// alert('hidden_category_type' + hidden_category_type);
		checkUpdateRenewalOrderCoursePackage(categoryType, limitShowTime,limitShowTimeUnit);
	}
	var hiddenCurrentLevel = $("#hidden_current_level").val();

	if (categoryType == "category_type1") {// 商务英语
		var tempStr = "<tr><td>用户级别:</td>"
				+ "<td><select class=\"easyui-combobox\" id=\"contract_current_level\" name=\"currentLevel\" style=\"width:150px;\" "
				+ "data-options=\"editable:false,required:true,valueField:'value',textField:'label'\"></select></td></tr>";

		$("#currentLevelTable").html(tempStr);

		$('#contract_current_level').combobox({
			data : businessLevelCombobox,
			valueField : 'label',
			textField : 'value'
		});

		if (hiddenCurrentLevel.indexOf("Business") != -1) {
			$('#contract_current_level').combobox('select', hiddenCurrentLevel);
		} else {
			$('#contract_current_level').combobox('select', "");
		}

	} else if (categoryType == "category_type2") {// 通用英语
		var tempStr = "<tr><td>用户级别:</td>"
				+ "<td><select class=\"easyui-combobox\" id=\"contract_current_level\" name=\"currentLevel\" style=\"width:150px;\" "
				+ "data-options=\"editable:false,required:true,valueField:'value',textField:'label'\"></select></td></tr>";

		$("#currentLevelTable").html(tempStr);

		$('#contract_current_level').combobox({
			data : generalLevelCombobox,
			valueField : 'label',
			textField : 'value'
		});

		if (hiddenCurrentLevel.indexOf("General") != -1) {
			$('#contract_current_level').combobox('select', hiddenCurrentLevel);
		} else {
			$('#contract_current_level').combobox('select', "");
		}

	} else {
		$("#currentLevelTable").html("");
	}

	$("#hidden_current_level").val("");

	var course_package_id = $('#course_package_id').combobox('getValue');
	$('#course_package_name').val($('#course_package_id').combobox('getText'));

	// 编辑合同的逻辑,就是之前已经编辑过合同了,需要把合同的内容带回来,下面的逻辑超级复杂...
	if (global_order_id != null && global_order_id != ""
			&& global_order_id != "null") {

		// modified by ivan.mgh,2016年4月25日15:17:40
		var getJsonObjUrl = getUrl(basePath + '/admin/orderCourse/getJsonObj');

		// 1.首先我需要按照已经编辑过合同的主键来查询合同对象
		$.ajax({
					type : "POST", // post提交方式默认是get
					dataType : 'json',
					url : getJsonObjUrl,
					async : false,
					data : {
						key_id : global_order_id
					},
					error : function(data) {// 设置表单提交出错
						alert('系统出现异常,请联系管理员');
					},
					success : function(result) {
						if (result.success) {
							// 2.将查询出的合同对象赋值给表单对象,三个时间
							$("#limit_show_time").textbox('setValue',
									result.data.limit_show_time);
							// alert(result.data.total_final_price);
							local_final_real_price = result.data.total_final_price;
							// $('#start_order_time').datetimebox('setValue',
							// formatDate(result.data.start_order_time));

							// modify by athrun.cw 2016年1月8日11:31:56
							// 给优惠价赋值
							$("#total_real_price").numberbox('setValue',
									result.data.total_real_price);

							resource_final_real_price = result.data.total_final_price;
							// resource_final_discount_price =
							// result.data.total_discount_price;
							
							//给原始价赋值
							$("#total_show_price").numberbox('setValue',rec.coursePackageShowPrice);
							coursePackageShowPrice = rec.coursePackageShowPrice;

							// modified by ivan.mgh,2016年4月25日15:17:40
							var listUrl = getUrl(basePath
									+ '/admin/orderCourseOption/list');

							// 3.通过合同主键查询合同的子表数据
							$
									.ajax({
										type : "POST", // post提交方式默认是get
										dataType : 'json',
										url : listUrl,
										async : false,
										data : {
											order_id : result.data.key_id
										},
										error : function(data) { // 设置表单提交出错
											alert('系统出现异常,请联系管理员');
										},
										success : function(result) {// 提交成功
											if (result.success) {
												$("#coursePackageOptionTable")
														.html("");
												var tempData = result.data;

												// 普通课程数量
												var normalCount = 0;
												// 4.循环合同的子表数据
												for (var i = 0; i < tempData.length; i++) {
													// 5.如果子表数据为赠送课程
													if (tempData[i].is_gift == 1) {
														// modify by komi
														// 2016年6月8日11:22:36
														// 修改赠送课程逻辑，增加联动效果，增加权限判定
														// 5.1 添加赠送课程的各种表单对象
														addGift(rec.coursePriceVersion,
																tempData[i].show_course_count,
																tempData[i].real_price,
																tempData[i].course_unit_type,
																tempData[i].course_type_id);

														// 5.3.3 给下拉框赋默值
														$('#courseTypeGift_' + addGiftCount).combobox('select',tempData[i].course_type_id);

													} else {
														// 6.如果子表数据为普通课程
														var tempStr = "<tr><td>"
																+ tempData[i].course_type
																+ "(单价"
																+ tempData[i].real_price
																+ "元):"
																+ "</td><td>"
																+ "<input type=\"hidden\" id=\"courseType_"
																+ normalCount
																+ "\" name=\"courseType_"
																+ normalCount
																+ "\" value=\""
																+ tempData[i].course_type_id
																+ "\">"
																+ "<input type=\"hidden\" id=\"realPrice_"
																+ normalCount
																+ "\" name=\"realPrice_"
																+ normalCount
																+ "\" value=\""
																+ tempData[i].real_price
																+ "\">"
																+ "<input type=\"hidden\" id=\"courseUnitType_"
																+ normalCount
																+ "\" name=\"courseUnitType_"
																+ normalCount
																+ "\" value=\""
																+ tempData[i].course_unit_type
																+ "\">"
																+ "<input class=\"easyui-textbox\" type=\"text\" name='courseCount_"
																+ normalCount
																+ "' id='courseCount_"
																+ normalCount
																+ "' value='"
																+ tempData[i].show_course_count
																+ "' style=\"width:50px\" data-options=\"readonly:true\"/>";

														// 微课和English
														// Studio都需要把"节"改成"月"
														if (tempData[i].course_unit_type == 1) {
															tempStr += "月,";
														} else if (tempData[i].course_unit_type == 0){
															tempStr += "节,";
														}else if (tempData[i].course_unit_type == 2){
															tempStr += "天,";
														}
														
														tempStr += (Number(tempData[i].show_course_count) * Number(tempData[i].real_price))
																+ "元</td></tr>";
														// 6.1 添加普通课程的各种表单对象
														$(
																"#coursePackageOptionTable")
																.append(tempStr);
														// 6.2
														// 将普通课程的各种表单对象变成easyui形式
														$(
																'#courseCount_'
																		+ normalCount)
																.textbox({});
														normalCount++;
													}
												}

												// 7.如果赠送课程默认没有，那么需要新建一个为空的默认课程行
												if (addGiftCount == 0) {
													addGift(rec.coursePriceVersion);
												}

												// 计算价格
												countTotal();
											} else {
												alert(result.msg);
											}
										}
									});

						} else {
							alert(result.msg);
						}
					}
				});
		// 8.将标识字段置空，此字段只为了判断是否为已有合同数据所用。
		global_order_id = null;
	} else {// 正常用户选择下拉框的逻辑

		//优惠价
		$("#total_real_price").numberbox('setValue',
				rec.coursePackageRealPrice);
		//时效
		$("#limit_show_time").textbox('setValue',
				rec.limitShowTime);
		
		//判断课程包时效单位
		if(rec.limitShowTimeUnit == 0){
			$("#jsLimitShowTime").html("时效(月):");
		}
		else if(rec.limitShowTimeUnit == 1){
			$("#jsLimitShowTime").html("时效(天):");
		}
		//原价
		$("#total_show_price").numberbox('setValue',rec.coursePackageShowPrice);
		coursePackageShowPrice = rec.coursePackageShowPrice;
		
		// modified by ivan.mgh,2016年4月25日15:40:27
		var coursePackageOptionListUrl = getUrl(basePath
				+ '/coursePackageOption/findOptionAndPirceList');

		// 查询课程包子表
		$.ajax({
					type : "POST", // post提交方式默认是get
					dataType : 'json',
					url : coursePackageOptionListUrl,
					async : false,
					data : {
						coursePackageId : course_package_id,
						coursePriceVersion : rec.coursePriceVersion
					},
					error : function(data) { // 设置表单提交出错
						alert('系统出现异常,请联系管理员');
					},
					success : function(result) {// 提交成功
						if (result.success) {
							var tempStr = "";
							var tempData = result.data;
							for (var i = 0; i < tempData.length; i++) {
								// alert("type=22==>"+tempData[i].course_type);
								tempStr += "<tr><td>"
										+ tempData[i].courseType
										+ "(单价"
										+ tempData[i].coursePriceUnitPrice
										+ "元):"
										+ "</td><td>"
										+ "<input type=\"hidden\" id=\"courseType_"
										+ i
										+ "\" name=\"courseType_"
										+ i
										+ "\" value=\""
										+ tempData[i].courseTypeId
										+ "\">"
										+ "<input type=\"hidden\" id=\"realPrice_"
										+ i
										+ "\" name=\"realPrice_"
										+ i
										+ "\" value=\""
										+ tempData[i].coursePriceUnitPrice
										+ "\">"
										+ "<input type=\"hidden\" id=\"courseUnitType_"
										+ i
										+ "\" name=\"courseUnitType_"
										+ i
										+ "\" value=\""
										+ tempData[i].courseUnitType
										+ "\">"
										+ "<input class=\"easyui-textbox\" type=\"text\" name='courseCount_"
										+ i
										+ "' id='courseCount_"
										+ i
										+ "' value='"
										+ tempData[i].courseCount
										+ "' style=\"width:50px\" data-options=\"readonly:true\"/>";

								// 微课和English Studio都需要把"节"改成"月"
								if (tempData[i].courseUnitType == 1) {

									tempStr += "月,";
								} else if (tempData[i].courseUnitType == 0){
									tempStr += "节,";
								} else if (tempData[i].courseUnitType == 2){
									tempStr += "天,";
								}

								tempStr += (Number(tempData[i].courseCount) * Number(tempData[i].coursePriceUnitPrice))
										+ "元</td></tr>";
								// 计算默认的价格(此时2个价格是一样的)
							}
							$("#coursePackageOptionTable").html(tempStr);
							countTotal();
							// 激活easyui文本框
							for (var i = 0; i < tempData.length; i++) {
								$('#courseCount_' + i).textbox({});
							}

							// 加载赠送课程
							addGift(rec.coursePriceVersion);

						} else {
							alert(result.msg);
						}
					}
				});
	}
}

/**
 * @author ivan.mgh
 * @param url
 * @param param
 * @returns
 */
function getUrl(url, param) {
	if (crmflag == 'true') {
		url += '/crm';
		url += '?auth=' + auth;
		if (param) {
			url += "&" + param;
		}
	} else {
		if (param) {
			url += "?" + param;
		}
	}

	return url;
}

// 新增赠课列表
function addGiftCourseCombo(coursePriceVersion,ownerGiftCount, course_count, real_price,course_unit_type,course_type_id) {
	var course_package_id = $('#course_package_id').combobox('getValue');
	var selectlistUrl = getUrl(basePath + '/courseType/findCourseTypeByParam');

	$('#courseTypeGift_' + ownerGiftCount).combobox(
			{	
				url : selectlistUrl,
				onSelect : function(rec) {
					var courseTypeId = "";
					if(rec != undefined){
						courseTypeId = rec.courseType;
						//根据选中的课程类型，给课程单位赋值
						$('#courseUnitTypeGift_' + ownerGiftCount).combobox({
							url:basePath + '/coursePrice/findCourseUnitAndPriceListByVersion?coursePriceVersion='+coursePriceVersion
							+'&courseType='+courseTypeId,
							valueField:'coursePriceUnitType',
							textField:'coursePriceUnitTypeName',
							//根据选中的课程类型和课程单位，给课程单价赋值
							onSelect : function(rec) {
								$("#realPriceGift_" + ownerGiftCount).numberbox('setValue', rec.coursePriceUnitPrice);
								$("#courseUnitTypeGift_" + ownerGiftCount).combobox('setValue',rec.coursePriceUnitType);
								countTotal();
							},
							onLoadSuccess : function() { // 数据加载完毕事件
								$("#courseUnitTypeGift_" + ownerGiftCount).combobox('setValue','');
								$("#courseUnitTypeGift_" + ownerGiftCount).combobox('setText','');
							}
						});
					}
					else{
						courseTypeId = course_type_id;
						//根据选中的课程类型，给课程单位赋值
						$('#courseUnitTypeGift_' + ownerGiftCount).combobox({
							url:basePath + '/coursePrice/findCourseUnitAndPriceListByVersion?coursePriceVersion='+coursePriceVersion
							+'&courseType='+courseTypeId,
							valueField:'coursePriceUnitType',
							textField:'coursePriceUnitTypeName',
							//根据选中的课程类型和课程单位，给课程单价赋值
							onSelect : function(rec) {
								$("#realPriceGift_" + ownerGiftCount).numberbox('setValue', rec.coursePriceUnitPrice);
								$("#courseUnitTypeGift_" + ownerGiftCount).combobox('setValue',rec.coursePriceUnitType);
								countTotal();
							}
						});
					}
					
					if (rec != undefined) {
						$("#courseCountGift_" + ownerGiftCount).numberbox('setValue', 1);
						$("#realPriceGift_" + ownerGiftCount).numberbox('setValue', '');
					} else {
						$("#courseCountGift_" + ownerGiftCount).numberbox('setValue', course_count);
						$("#realPriceGift_" + ownerGiftCount).numberbox('setValue', real_price);
						$("#courseUnitTypeGift_" + ownerGiftCount).combobox('setValue',course_unit_type);
						
//						var unitName = "节";
//						if(course_unit_type == 0){
//							unitName = "节";
//						}
//						else if(course_unit_type == 1){
//							unitName = "月";
//						}
//						else if(course_unit_type == 2){
//							unitName = "天";
//						}
//						$("#courseUnitTypeGift_" + ownerGiftCount).combobox('setText',unitName);
					}
					countTotal();
				},
				valueField : 'courseType',
				textField : 'courseTypeChineseName',
				panelHeight : "150px",
				editable : false, // 不允许手动输入
				onLoadSuccess : function() { // 数据加载完毕事件
				}
			});
}

// 提交预览合同
function submitViewContract() {
	// modified by ivan.mgh,2016年4月25日17:02:47
	// --------------start-------------
	setEditContractFormAction();
	// --------------end-------------

	$('#editContractForm').form('submit', {
		onSubmit : function() {
			// easyui的校验
			if ($(this).form('enableValidation').form('validate')) {
				$('#editContractDiv').dialog('close');
				return true;
			} else {
				return false;
			}
		},
		success : function(result) {
			/**
			 * modified by komi 2016年6月15日18:31:56
			 * 此处加载打开了一次lookContractDiv时，viewContract方法中$('#lookContractDiv').dialog({})又打开加载了一次
			 * 所以会执行两次href操作
			 */
			// $('#lookContractDiv').dialog('open');
			result = eval('(' + result + ')');
			if (result.success) {
				// modified by ivan.mgh,2016年4月25日17:02:47
				// --------------start-------------
				if (crmflag != "true") {
					// 刷新列表
					$('#dataGrid1').datagrid('reload');
					$('#lookUserDetailDiv').dialog('close');
				}
				// --------------end-------------

				// $('#editContractForm').form('clear');
				$('#order_id').val(result.data);
				viewContract(result.data);
			} else {
				$.messager.alert('提示', result.msg);
			}
		},
		error : function(status) {
			$.messager.alert('提示', '系统出现异常,请联系管理员', 'error' + status);
		}
	});
}

/**
 * modified by komi 2016年8月12日13:39:11
 * 增加合同返回修改方法，为了把global_order_id 重新置为空
 */
function returnUpdate(){
	$('#lookContractDiv').dialog('close');
	global_order_id = null;
 	$('#editContractDiv').dialog('open');
}

// modified by ivan.mgh,2016年4月26日15:53:11
// --------------start-------------
/**
 * @author ivan.mgh
 */
function setEditContractFormAction() {
	var url = basePath + "/admin/orderCourse/saveOrderCourse";
	if (crmflag == "true") {
		url += "/crm?auth=" + auth;
	}

	$("#editContractForm").attr("action", url);
}
// --------------end-------------

/**
 * 差价和优惠价的相互联动（续约专用!!!）
 */
function changeFinalPriceAndRealPriceTogether(priceId) {
	$("#" + priceId).numberbox(
			{
				required : true,
				onChange : function(newValue, oldValue) {

					var currentSelectFinalPrice = Number($("#" + priceId)
							.numberbox('getValue'));

					if ("total_final_price" == priceId) {
						// 判定全局变量优惠价和差额都变化过后，则不改变并都置0
						if (totalFinalPriceFlag == 1) {
							totalFinalPriceFlag = 0;
							return;
						}

						totalRealPriceFlag = 1;

						// modify by komi 2016年6月1日17:38:22 增加原合同折后价（学员最终付的价格）
						// modify by komi 2017年1月22日13:27:20 需求486
						//差价不再减源合同优惠价Number(resource_final_discount_price);
						var finalPrice = currentSelectFinalPrice;

						// 设置单价值
						$('#total_real_price')
								.numberbox('setValue', finalPrice);
					} else if ("total_real_price" == priceId) {
						// 判定全局变量优惠价和差额都变化过后，则不改变并都置0
						if (totalRealPriceFlag == 1) {
							totalRealPriceFlag = 0;
							return;
						}

						totalFinalPriceFlag = 1;

						// modify by komi 2016年6月1日17:38:22 增加原合同折后价（学员最终付的价格）
						// modify by komi 2017年1月22日13:27:20 需求486
						//差价不再减源合同优惠价Number(resource_final_discount_price);
						var finalPrice = currentSelectFinalPrice;

						// 设置单价值
						$('#total_final_price').numberbox('setValue',
								finalPrice);
					}
				}
			});

}

/**
 * author:athrun.cw 2016年3月30日16:12:08 初始化 差价
 */
function initTotalFinalPrice() {
	// alert("new:"+initTotalFinalPrice);
	// 差价
	$("#totalFinalPriceTable").html("");
	var tempStr = "<tr id=\"totalFinalPriceTr\" name=\"totalFinalPriceTr\"><td>差价:</td>";
	tempStr += "<td><input class=\"easyui-numberbox\" id=\"total_final_price\" name=\"totalFinalPrice\" ";
	// modify by komi 2016年6月2日15:31:25
	// 有修改权限的角色可以修改差价，并将差价和折扣价联动修改
	if (isHavaChangeOrderCoursePricePermission == "true") {
		tempStr += " data-options=\"required:true,editable:true,validType:{length:[0,7]}\" ";
	} else {
		tempStr += " data-options=\"required:true,editable:false\" ";
	}
	tempStr += "style=\"width:150px;\"/></td></tr>";
	$("#totalFinalPriceTable").append(tempStr);

	/**
	 * modify by komi 2016年6月1日17:38:22 修改差额的时候需要联动修改优惠价（下面2个调用alex评审的时候改过）
	 */
	changeFinalPriceAndRealPriceTogether("total_real_price");
	changeFinalPriceAndRealPriceTogether("total_final_price");

	// $("#total_final_price").numberbox('setValue', total_final_price);
}

// modified by ivan.mgh,2016年5月13日16:02:32
// ------------------start----------------------
// 初始化赠送时间控件
function initGiftTimeInnerHTML() {
	// 清空
	emptyGiftTimeInnerHTML();

	// modify by komi 2016年6月8日11:27:27 修改为时间表单都可见，根据权限判定是否能够修改
	var innerHTML = "<tr>"
			+ "<td>赠送时间:</td>"
			+ "<td>"
			+ "<select id=\"giftTime_\" name=\"giftTime_\" style=\"width:150px;\">"
			+ "<option value=\"0\">&nbsp;&nbsp;</option>"
			+ "<option value=\"1\">1</option>"
			+ "<option value=\"2\">2</option>"
			+ "<option value=\"3\">3</option>"
			+ "<option value=\"4\">4</option>"
			+ "<option value=\"5\">5</option>"
			+ "<option value=\"6\">6</option>"
			+ "<option value=\"7\">7</option>"
			+ "<option value=\"8\">8</option>"
			+ "<option value=\"9\">9</option>"
			+ "<option value=\"10\">10</option>"
			+ "<option value=\"11\">11</option>"
			+ "<option value=\"12\">12</option>" + "</select>"
			+ "<span>&nbsp;月</span>" + "</td>" + "</tr>";

	// 生成HTML
	$("#giftTimeTable").append(innerHTML);

	$('#giftTime_').combobox({
		editable : false,
		onSelect : function(record) {
			$("#hidden_gift_time").val(record.value);
			// 计算价格
			countTotal();
		}
	});

	if (isHaveGiveTime == "true") {
		$("#giftTimeTable").show();
	} else {
		$("#giftTimeTable").hide();
	}
}

// 清空赠送时间table
function emptyGiftTimeInnerHTML() {
	// 清空
	$("#giftTimeTable").empty();
	$("#hidden_gift_time").val(0);
}

// 获得赠送时间课程的价格总和
function calculateGiftTimeMoney() {
	// modify by komi 2016年6月8日11:36:22 这里计算时间价格总和，去掉权限判定
	// if(isHaveGiveTime=="true"){
	var sumUnitPrice;
	var sumUnitPriceUrl = getUrl(basePath
			+ '/admin/coursePackageOption/sumEnglishStudioRsaWeikeUnitPrice');

	$.ajax({
		type : "GET",
		dataType : 'json',
		async : false,
		url : sumUnitPriceUrl,
		error : function() { // 设置表单提交出错
			alert('系统出现异常,请联系管理员');
		},
		success : function(result) {// 提交成功
			if (result.success) {
				sumUnitPrice = result.data;
			}
		}
	});

	var giftTimeVal = $('#giftTime_').combobox('getValue');
	if (!isNaN(giftTimeVal) && !isNaN(sumUnitPrice)) {
		return Number(giftTimeVal) * Number(sumUnitPrice);
	} else {
		return 0;
	}
	// } else {
	// return 0;
	// }
}
// ------------------end----------------------
