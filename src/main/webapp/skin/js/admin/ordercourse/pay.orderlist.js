function loadOrderSplit() {
    var $formValidateList = [];
    var checkFormValidateList = function() {
        for (var i = 0; list = $formValidateList, i < list.length; i++) {
            if (!list[i].validate.form()) {
                return false;
            }
        }
        return true;
    };

    // 表单校验规则
    var payListValidate = {
        debug: false,
        rules: {
            pay_cc_name: {
                // required: true,
                // rangelength: [2, 5],
                // ruleRealname: true
                rangelength: [0, 20],
                ruleCommonString: true,
            },
            pay_city_name: {
                //required: true,
                rangelength: [0, 20],
                ruleCommonString: true,
                //ruleCenterinfo: true
            },
            pay_center_name: {
                //required: true,
                rangelength: [0, 20],
                ruleCommonString: true,
                //ruleCenterinfo: true
            },
            pay_bank: {
                rangelength: [0, 20],
                ruleCommonString: true,
                //rulePaybank: true
            },
            pay_success_sequence: {
                rangelength: [8, 40],
                rulePaysequence: true
            }
        },
        messages: {
            pay_cc_name: {
                //required: "销售姓名不能为空",
                rangelength: "请输入{0}到{1}位中文姓名"
            },
            pay_city_name: {
                //required: "城市不能为空",
                rangelength: "请输入{0}到{1}位城市名称"
            },
            pay_center_name: {
                //required: "中心不能为空",
                rangelength: "请输入{0}到{1}位中心名称"
            },
            pay_bank: {
                rangelength: "请输入{0}到{1}位银行名称"
            },
            pay_success_sequence: {
                rangelength: "请输入{0}到{1}位银行流水号"
            }
        }
    };
    $.validator.setDefaults(payListValidate);

    $.validator.addMethod("ruleCommonString", function(value, params) {
        var reg = /^[a-zA-Z\u4e00-\u9fa5]{0,20}$/;
        return this.optional(params) || (reg.test(value));
    }, "请填写中文或字母字符");
    $.validator.addMethod("ruleRealname", function(value, params) {
        var reg = /^[\u4e00-\u9fa5]{2,5}$/;
        return this.optional(params) || (reg.test(value));
    }, "请填写中文字符");
    $.validator.addMethod("ruleCenterinfo", function(value, params) {
        var reg = /^[\u4e00-\u9fa5]{2,10}$/;
        return this.optional(params) || (reg.test(value));
    }, "请填写中文字符");
    $.validator.addMethod("rulePaybank", function(value, params) {
        var reg = /^[\u4e00-\u9fa5]{4,10}$/;
        return this.optional(params) || (reg.test(value));
    }, "请填写中文字符");
    $.validator.addMethod("rulePaysequence", function(value, params) {
        var reg = /^[a-zA-Z0-9]{8,40}$/;
        return this.optional(params) || (reg.test(value));
    }, "请填写由英文数字组成的交易流水号");

    // pay split v2 test end

    var payOrderList = angular.module('adminOrderList', []);

    payOrderList.controller("adminOrderListCtrl", function($scope, $http) {
        // common function
        var JoinList = function(a, b) {
            for (var i = 0; i < a.length; i++) {
                for (var item in b) {
                    a[i][item] = b[item];
                }
                //console.log(a[i]);
            }
            return a;
        };
        var getViewModel = function(dataModel) {
            for (var i = 0; i < dataModel.length; i++) {
                // console.log("---",dataModel)
                // if (dataModel[i]["split_pay_type"] !== null || dataModel[i]["split_pay_type"] !== '') {
                //     dataModel[i]["payTypeSelect"] = payTypeOptions[Number(dataModel[i]["split_pay_type"])];
                // }
              
                if (dataModel[i]["split_pay_type"] !== null || dataModel[i]["split_pay_type"] !== '') {
                     if(dataModel[i]["split_pay_type"]==5){
						   // 如果是5的话，select显示百度分期
                           dataModel[i]["payTypeSelect"] = payTypeOptions[4];
                     }else  if(dataModel[i]["split_pay_type"]==6){
						  // 如果是6的话，select显示招联分期
						   dataModel[i]["payTypeSelect"] = payTypeOptions[5];
					 }else{
                           dataModel[i]["payTypeSelect"] = payTypeOptions[Number(dataModel[i]["split_pay_type"])];
                     }                    
                }
				  
             
                //               if ($scope.studentRole.id === 2 && i === 0) {
                //   dataModel[0].split_price = $scope.havePaidPrice;
                //   dataModel[0].split_status = 2;
                //               }
            }
            return dataModel;
        };
        // 角色权限类型
        var userRoles = [{
            "id": 0,
            "name": "没有确认按钮"
        }, {
            "id": 1,
            "name": "有确认按钮"
        }];

        // 学员类型
        //        var studentRoles = [{
        //            "id": 0,
        //            "name": "常规学员"
        //        }, {
        //            "id": 1,
        //            "name": "线下售卖".
        //        }, {
        //            "id": 2,
        //            "name": "线下转线上"
        //        }];

        var payTypeOptions = [{
            "id": 0,
            "name": "线上支付"
        }, {
            "id": 1,
            "name": "POS机刷卡"
        }, {
            "id": 2,
            "name": "现金"
        }, {
            "id": 3,
            "name": "个人转账"
        }, {
            "id": 5,
            "name": "百度分期"
        }, {
            "id": 6,
            "name": "招联分期"
        }];

        // 获取列表数据begin
        var order = {};
        order.order_id = angular.element("#hidden_order_id_by_split").val();

        var loadOrderCourseSplitURL = basePath + "/orderCourseSplit/preLoadOrderCourseSplit";

        $http({
            method: "POST",
            url: loadOrderCourseSplitURL,
            dataType: "json",
            headers: { 'Content-type': 'application/json;charset=UTF-8' },
            data: JSON.stringify(order)
        }).success(function(data, status) {
            // success handle code
            if (data.code !== "500") {

                // modified by ivan.mgh,2016年4月27日15:45:59
                // --------------start----------------
                if (crmflag == "true") {
                    data = initCcInfo(data);
                }
                // --------------end----------------

                $scope.runViewModel(data, $scope);
            } else {
                alert(data.msg);
            }
            // alert(data);
        }).error(function(data, status) {
            // error handle code
            if (data.code.toString() == '500') {
                alert('系统内部错误！');
            } else {
                alert(data.msg);
            }
        });

        // render view
        $scope.runViewModel = function(data, $scope) {

            var newItem = {
                "key_id": "",
                "split_price": "0",
                "split_status": "0",
                "update_date": "",
                "pay_from": "",
                // 支付方式
                "split_pay_type": "",
                // 支付银行
                "pay_bank": "",
                // 支付流水号
                "pay_success_sequence": "",
                // 线下销售姓名
                "pay_cc_name": "",
                // 线下中心名称
                "pay_center_name": "",
                // 线下城市名称
                "pay_city_name": "",
                "payTypeSelect": payTypeOptions[0]
            };

            var dataModel = data.split_list;

            if (dataModel.length <= 0) {
                dataModel.push(newItem);
            }
            $scope.shouldPayPrice = data.should_pay_price;
            $scope.havePaidPrice = data.have_paid_price;
            console.log(data);
            var haveNotSplitPrice = function() {
                var res = 0;
                for (var i = 0, len = dataModel.length; i < len; i++) {
                    
                    console.log(i,res,dataModel);
                    //百度分期 如果状态是申请中 申请成功，
                    if(dataModel[i].split_pay_type===5){
                       
                            res += Number(dataModel[i].split_price);
                            $scope.baidumoney56=res;
                            console.log($scope.baidumoney56)
                        
                    }else if(dataModel[i].split_pay_type===6){
					    	console.log("支付类型",dataModel[i].split_pay_type)
						   
                            res += Number(dataModel[i].split_price);
                            $scope.baidumoney56=res;
                            console.log($scope.baidumoney56)
                       
					}else{
                        res += Number(dataModel[i].split_price);
                    }
                }
                return res;
            };
            console.log("haveNotSplitPrice()",haveNotSplitPrice())
            $scope.canSplitPrice = Number(data.should_pay_price) + Number(data.have_paid_price) - haveNotSplitPrice();

            $scope.isOverflow = false;

            //            $scope.studentRole = studentRoles[data.user_from_type];
            //console.log($scope.studentRole);

            // // 获取使用者权限
            // var checkAdminUserPermissionURL = basePath + "/admin/adminUser/checkAdminUserPermission";

            // // modified by ivan.mgh,2016年4月26日14:26:47
            // if (crmflag == "true") {
            //     checkAdminUserPermissionURL += "/crm?auth=" + auth;
            // }
            //  获取使用者权限
            var checkAdminUserPermissionURL;
            checkAdminUserPermissionURL = crmflag == "true" ? checkAdminUserPermissionURL + ("/crm?auth=" + auth) : basePath + "/admin/adminUser/checkAdminUserPermission";


            $http({
                method: "POST",
                url: checkAdminUserPermissionURL,
                dataType: "json",
                headers: { 'Content-type': 'application/json;charset=UTF-8' },
                data: JSON.stringify({
                    "permission": "contract:financeConfirm"
                })
            }).success(function(checkdata, status) {

                // success handle code
                if (checkdata.success) {
                    $scope.userRole = checkdata.msg == "true" ? userRoles[1] : userRoles[0];

                    // 线下转线上，还需支付金额和剩余可拆分金额为0，已支付金额=总金额，自动填充金额，且不可更改
                    //    if ($scope.studentRole.id === 2) {
                    //        $scope.shouldPayPrice = 0;
                    //        $scope.havePaidPrice = Number(data.should_pay_price) + Number(data.have_paid_price);
                    //        $scope.canSplitPrice = 0;
                    //    }

                    // 判断是否出现确认按钮
                
                    $scope.isConfirmBtnShow = function(studentRole, payTypeSelect) {
                            // console.log(payTypeSelect)
                            if (payTypeSelect) {
                                   if ($scope.userRole.id === 0) {
                            return false;
                        } else if (payTypeSelect.id === 0) {
                            return false;
                        } else if (payTypeSelect.id === 3) {
                            return true;
                        } else if (payTypeSelect.id === 1) {
                            return true;
                        } else if (payTypeSelect.id === 2) {
                            return true;
                        } else {
                            return false;
                        }
                            }
                     
                    };

                    //$scope.payTypeSelect = payTypeOptions[4];
                    $scope.payTypeOptions = payTypeOptions;

                    // 过滤订单数，对于线下转线上情况只有1单
                    //    $scope.payListLimit = function() {
                    //        if ($scope.studentRole.id === 2) {
                    //            return 1;
                    //        } else {
                    //            return null;
                    //        }
                    //    };

                    // 判断是否需要填写线下信息
                    //    $scope.isOfflineInfoShow = function(studentRole, payTypeSelect) {
                    //        //console.log(payTypeSelect);
                    //        if (studentRole.id === 2) {
                    //            return true;
                    //        } else 
                    //        if (payTypeSelect.id === 0) {
                    //            return false;
                    //        } else if (payTypeSelect.id === 3) {
                    //            return studentRole.id !== 0;
                    //        } else if (payTypeSelect.id === 1) {
                    //            return studentRole.id == 1;
                    //        } else if (payTypeSelect.id === 2) {
                    //            return studentRole.id !== 0;
                    //        } else {
                    //            return false;
                    //        }
                    //    };

                    //    // 判断是否需要填写银行交易信息
                    //    $scope.isPaybankInfoShow = function(studentRole, payTypeSelect) {
                    //        if (payTypeSelect.id === 0) {
                    //            return false;
                    //        } else if (payTypeSelect.id === 3) {
                    //            return studentRole.id !== 2;
                    //        } else if (payTypeSelect.id === 1) {
                    //            return studentRole.id !== 2;
                    //        } else if (payTypeSelect.id === 2) {
                    //            return false;
                    //        } else {
                    //            return false;
                    //        }
                    //    };

                    $scope.viewModel = getViewModel(dataModel);
                    // console.log(" $scope.viewModel ",$scope.viewModel )

                    // event
                    $scope.addPayItemBtn = function(obj) {
                        var newObj = {
                            "key_id": "",
                            "split_price": "0",
                            "split_status": "0",
                            "update_date": "",
                            "pay_from": "",
                            // 支付方式
                            "split_pay_type": "",
                            // 支付银行
                            "pay_bank": "",
                            // 支付流水号
                            "pay_success_sequence": "",
                            // 线下销售姓名
                            "pay_cc_name": "",
                            // 线下中心名称
                            "pay_center_name": "",
                            // 线下城市名称
                            "pay_city_name": "",
                            "payTypeSelect": payTypeOptions[0]
                        };
                        if ($scope.viewModel.length >= 20) {
                            alert("最多可拆分20笔订单");
                            return false;
                        }

                        //modified by ivan.mgh,2016年4月27日15:45:59
                        // --------------start----------------
                        if (crmflag == "true") {
                            newObj = initNewItemCcInfo(newObj);
                        }
                        // --------------end----------------

                        $scope.viewModel.push(newObj);
                        //$scope.canSplitPrice = getTotalCanSplit();

                        setTimeout(function() {
                            payListValidateFunc();
                        }, 800);
                    };
                    $scope.minusPayItemBtn = function(index) {
                        var delList = $scope.viewModel.filter(function(item) {
                            return item.split_status == "0" || item.split_status == "2"|| item.split_status == "4"|| item.split_status == "5"|| item.split_status == "6";
                        });
                        if (delList.length == 1) {
                            return;
                        } else {
                            $scope.viewModel.splice(index, 1);
                            $scope.canSplitPrice = getTotalCanSplit();
                            //delLastItem($scope.viewModel,"split_status","0");
                        }
                    };
                    // 数值变化更新剩余可拆分金额
                    $scope.inputChange = function() {
                        $scope.canSplitPrice = getTotalCanSplit();
                    };
                    var getTotalCanSplit = function() {

                        var delList = $scope.viewModel.filter(function(item) {
                            return item.split_status == "0" || item.split_status == "2"|| item.split_status == "4"|| item.split_status == "5"|| item.split_status == "7";
                        });
                        console.log(delList)
                        var totalInput = getTotalVal(delList, "split_price");
                        var canSplitPrice = $scope.shouldPayPrice - totalInput;

                        $scope.isOverflow = canSplitPrice < 0 ? true : false;

                        return Math.abs(canSplitPrice);
                    };
                    // select change
                    $scope.payTypeChange = function(index, payTypeSelect) {
                        switch (payTypeSelect.id) {
                            // 线上支付
                            case 0:
                                $scope.payTypeSelect = payTypeOptions[0];
                                $scope.viewModel[index].split_status = "0";
                                break;
                                // POS机
                            case 1:
                                $scope.payTypeSelect = payTypeOptions[1];
                                $scope.viewModel[index].split_status = "2";
                                break;
                                // 现金
                            case 2:
                                $scope.payTypeSelect = payTypeOptions[2];
                                $scope.viewModel[index].split_status = "2";
                                break;
                                // 个人转账
                            case 3:
                                $scope.payTypeSelect = payTypeOptions[3];
                                $scope.viewModel[index].split_status = "2";
                                break;
                                //百度分期
                            case 5:
                                $scope.payTypeSelect = payTypeOptions[4];
                                console.log( $scope.viewModel,index);
                                   $scope.viewModel[index].split_pay_type = "5";
                                $scope.viewModel[index].split_status = "4";
                                break;
								  //招联分期
                            case 6:
                                $scope.payTypeSelect = payTypeOptions[5];
                                console.log( "招联分期payTypeSelect",$scope.payTypeSelect);
                                $scope.viewModel[index].split_pay_type = "6";
                                $scope.viewModel[index].split_status = "4";
                                break;
                            default:
                                break;
                        }
                    };
                }
            }).error(function(data, status) {
                // error handle code
                alert(data.msg);
            });

            // 确认拆分信息
            $scope.confirmPaySplitBtn = function(index, key_id) {

                if ($scope.viewModel[index].split_price == 0) {
                    alert("金额不能为0!");
                    return false;
                }

                // 校验是否通过
                //if (!checkFormValidateList()) {
                //    alert("请填写符合要求信息~");
                //    return false;
                //}

                // 校验是否通过
                if (!$formValidateList[index].validate.form()) {
                    alert("请填写符合要求信息~");
                    return false;
                }

                $scope.viewModel[index].split_status = 2;

                if (getTotalVal($scope.viewModel, "split_price") != $scope.totalPayPrice) {
                    alert("总支付金额有误!");
                } else {
                    var confirmSuccessPayURL = basePath + "/admin/orderCourseSplit/confirmSuccessPay";
                    // modified by ivan.mgh,2016年4月26日14:26:47
                    if (crmflag == "true") {
                        confirmSuccessPayURL += "/crm?auth=" + auth;
                    }

                    var obj = {
                        "order_id": order.order_id,
                        "split_order_id": key_id.toString(),
                        "split_status": Number($scope.viewModel[index].split_status),
                        "split_price": $scope.viewModel[index].split_price,
                        "split_pay_type": Number($scope.viewModel[index].payTypeSelect.id),
                        "pay_bank": $scope.viewModel[index].pay_bank,
                        "pay_success_sequence": $scope.viewModel[index].pay_success_sequence,
                        "pay_cc_name": $scope.viewModel[index].pay_cc_name,
                        "pay_center_name": $scope.viewModel[index].pay_center_name,
                        "pay_city_name": $scope.viewModel[index].pay_city_name
                    };
                    $http({
                        method: "POST",
                        url: confirmSuccessPayURL,
                        dataType: "json",
                        headers: { 'Content-type': 'application/json;charset=UTF-8' },
                        data: obj
                    }).success(function(data, status) {
                        //console.log(data);
                        if (data.code == '200') {
                            $scope.viewModel[index].key_id = data.data;
                            $scope.viewModel[index].split_status = "3";
                        }
                        alert(data.msg);
                    }).error(function(data, status) {
                        // error handle code
                        //console.log(data);
                        alert(data.msg);
                    });
                }
            };

            $scope.sentFormBtn = function() {

                // 校验是否通过
                if (!checkFormValidateList()) {
                    alert("请填写符合要求信息~");
                    return false;
                }

                //console.log($scope.viewModel);
                if (getTotalVal($scope.viewModel, "split_price") != $scope.totalPayPrice) {
                    alert("总支付金额有误!");
                } else {
                    //alert("ok~");
                    var sendList = {};
                    var sendUpdate = {};
                    sendUpdate.order_id = order.order_id;
                    sendUpdate.order_view_list = [];
                    sendList.order_id = order.order_id;
                    sendList.order_split_list = [];
                    for (var item in $scope.viewModel) {
                        if ($scope.viewModel[item].split_price == 0) {
                            continue;
                        } else {
                            var viewObj = {
                                "key_id": $scope.viewModel[item].key_id.toString(),
                                "split_price": $scope.viewModel[item].split_price.toString(),
                                "split_status": $scope.viewModel[item].split_status.toString(),
                                "split_pay_type": $scope.viewModel[item].payTypeSelect.id.toString(),
                                "pay_bank": $scope.viewModel[item].pay_bank.toString(),
                                "pay_success_sequence": $scope.viewModel[item].pay_success_sequence.toString(),
                                "pay_cc_name": $scope.viewModel[item].pay_cc_name.toString(),
                                "pay_center_name": $scope.viewModel[item].pay_center_name.toString(),
                                "pay_city_name": $scope.viewModel[item].pay_city_name.toString(),
                                "payTypeSelect": $scope.viewModel[item].payTypeSelect
                            };
                            var obj = {
                                "key_id": $scope.viewModel[item].key_id.toString(),
                                "split_price": $scope.viewModel[item].split_price.toString(),
                                "split_status": $scope.viewModel[item].split_status.toString(),
                                "split_pay_type": $scope.viewModel[item].payTypeSelect.id.toString(),
                                "pay_bank": $scope.viewModel[item].pay_bank.toString(),
                                "pay_success_sequence": $scope.viewModel[item].pay_success_sequence.toString(),
                                "pay_cc_name": $scope.viewModel[item].pay_cc_name.toString(),
                                "pay_center_name": $scope.viewModel[item].pay_center_name.toString(),
                                "pay_city_name": $scope.viewModel[item].pay_city_name.toString()
                            };
                            sendList.order_split_list.push(obj);
                            sendUpdate.order_view_list.push(viewObj);
                        }
                    }
                    $scope.viewModel = sendUpdate.order_view_list;


                    // modified by ivan.mgh,2016年4月26日14:26:47
                    // ----------------start--------------------
                    // var submitOrderCourseSplitURL = basePath + "/admin/orderCourseSplit/saveOrderCourseSplit";
                    // if (crmflag == "true") {
                    //     submitOrderCourseSplitURL += "/crm?auth=" + auth;
                    // }
                    var submitOrderCourseSplitURL = basePath + "/admin/orderCourseSplit/saveOrderCourseSplit";
                    crmflag == "true" && (submitOrderCourseSplitURL += "/crm?auth=" + auth);
                    // ----------------end--------------------


                    $http({
                        method: "POST",
                        url: submitOrderCourseSplitURL,
                        dataType: "json",
                        headers: { 'Content-type': 'application/json;charset=UTF-8' },
                        data: JSON.stringify(sendList)
                    }).success(function(data, status) {
                        alert(data.msg);
                        //刷新合同列表
                        $('#dataGrid1').datagrid('reload');
                        //关闭拆分dialog
                        $('#splitOrderCourseDiv').dialog('close');
                        //关闭预览合同dialog
                        $('#lookContractDiv').dialog('close');
                        //关闭编辑合同dialog
                        $('#editContractDiv').dialog('close');
                        //关闭查看详情dialog
                        $('#lookUserDetailDiv').dialog('close');

                        // modified by ivan.mgh,2016年4月26日14:26:47
                        // ----------------start--------------------
                        if (crmflag == "true") {
                            // 关闭tab
                            window.close();
                        }
                        // ----------------end--------------------

                    }).error(function(data, status) {
                        // error handle code
                        if (data.code == '500') {
                            alert('系统内部错误！');
                        } else {
                            alert(data.msg);
                        }
                    });
                }
            };

            // 限制input只能输入数字
            $scope.validateNumber = function(e) {

                if ((e.shiftKey == false && e.keyCode >= 37 && e.keyCode <= 57) ||
                    (e.shiftKey == false && e.keyCode >= 96 && e.keyCode <= 105) ||
                    e.keyCode == 8 || e.keyCode == 9) {
                    window.event.returnValue = true;
                } else {
                    window.event.returnValue = false;
                }
            };

            // 获取所有input的总值
            var getTotalVal = function(list, a) {
                var totalResult = 0;
                for (var i = 0; i < list.length; i++) {
                    // 计算表单的总价
                    var curObj = list[i];
                    totalResult += Math.abs(Number(curObj[a]));
                }
                return totalResult;
            };
            //console.log(JSON.stringify($scope.viewModel));
            $scope.totalPayPrice = Number(data.should_pay_price) + Number(data.have_paid_price);
        };

    });
    angular.bootstrap($("#adminPayOrderList"), ["adminOrderList"]);

    //validate
    function payListValidateFunc() {
        var formList = [];
        var len = $("#payList form").length;
        for (var i = 0; i < len; i++) {
            $("#payList form").eq(i).attr("id", "adminPayListForm" + i);
            var tempObj = {
                "name": "adminPayListForm" + i,
                "validate": $("#payList form").eq(i).validate()
            };
            formList.push(tempObj);
        }
        $formValidateList = formList;
        return formList;
        //console.log(formList);
    }
    setTimeout(function() {
        payListValidateFunc();
    }, 800);
}

//modified by ivan.mgh,2016年4月27日15:45:59
// --------------start----------------
function initCcInfo(data) {
    var ccInfo = getCcInfo();
    var splitList = data.split_list;
    if (splitList.length > 0) {
        $.each(data.split_list, function(i, item) {
            item.pay_cc_name = ccInfo.ccName;
            item.pay_center_name = ccInfo.ccBelongCenter;
            item.pay_city_name = ccInfo.ccBelongCity;
        });
    } else {
        var newItem = {
            "key_id": "",
            "split_price": "0",
            "split_status": "0",
            "update_date": "",
            "pay_from": "",
            // 支付方式
            "split_pay_type": "",
            // 支付银行
            "pay_bank": "",
            // 支付流水号
            "pay_success_sequence": "",
            "pay_cc_name": ccInfo.ccName,
            "pay_center_name": ccInfo.ccBelongCenter,
            "pay_city_name": ccInfo.ccBelongCity,
            "payTypeSelect": {
                "id": 0,
                "name": "线上支付"
            }
        };

        splitList.push(newItem);
        data.split_list = splitList;
    }
    return data;
}

function initNewItemCcInfo(newObj) {
    var ccInfo = getCcInfo();
    newObj.pay_cc_name = ccInfo.ccName;
    newObj.pay_center_name = ccInfo.ccBelongCenter;
    newObj.pay_city_name = ccInfo.ccBelongCity;

    return newObj;
}

function getCcInfo() {
    var ccName = $("#hidden_cc_name").val();
    var ccBelongCity = $("#hidden_cc_belong_city").val();
    var ccBelongCenter = $("#hidden_cc_belong_center").val();

    var ccInfo = new CcInfo(ccName, ccBelongCity, ccBelongCenter);
    return ccInfo;
}

function CcInfo(ccName, ccBelongCity, ccBelongCenter) {
    this.ccName = ccName;
    this.ccBelongCity = ccBelongCity;
    this.ccBelongCenter = ccBelongCenter;
}
//--------------end----------------