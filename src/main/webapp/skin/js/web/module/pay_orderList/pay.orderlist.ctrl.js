
//define(["angular","jquery","utility","json!shconfig.json!bust"], function(angular,$,utility,shconfig) {
	var payOrderTesting = {
		data: {
		    "should_pay_price": 3000,
		    "have_paid_price": 5999,
		    "split_list": [
		        {
		            "key_id": "0ecda725e5324c6f990e2dc809ec700e",
		            "split_price": 3000,
		            "split_status": 0,
		            "update_date": 1452682401000,
		            "pay_from": ""
		        },
		        {
		            "key_id": "8273519af73d478495e4aea28c65c93d",
		            "split_price": 5999,
		            "split_status": 1,
		            "update_date": 1452667419000,
		            "pay_from": "支付宝"
		        }
		    ]
		}
	};
	
	var GetValueLength = function(a, b, c) {
	    var count = 0;
        for (var i = 0; i < a.length; i++) {
            if (a[i][b] == c) {
                count++;
            }
        }
        return count;
	};
	
	var JoinList = function(a, b) {
	    for (var i = 0; i < a.length; i++) {
            for (var item in b) {
                a[i][item] = b[item];
            }
            //console.log(a[i]);
        }
        return a;
	};
	
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
	    "id": 4,
	    "name": "线上支付(其他)"
	}];
	
	// 1.未支付 2.已支付，待确认 3.已支付 4.已支付，已确认
	var sortRule = function(prop) {
	    return function (a, b) {
	        if (a[prop] < b[prop] && a[prop] === 1) {
	            return 1;
	        } else if (a[prop] < b[prop]) {
	            return -1;
	        } else if (a[prop] > b[prop] && a[prop] === 2) {
	            return -1;
	        } else if (a[prop] > b[prop]) {
	            return 1;
	        } else if (a[prop] === b[prop]) {
	            return 0;
	        }
	    }
	};
	
	// 给出预计确认时间
	var getAdvisedTime = function(list, prop) {
	    var time = list[prop];
	    if (new Date(time).getDay() == 5 && new Date(time).getHours() >= 18) {
	        // 周五-下周一前
	        return "预计"+Number(new Date(time+3*24*3600*1000).getMonth()+1)+"月"+new Date(time+3*24*3600*1000).getDate()+"日前确认";
	    } else if (new Date(time).getDay() == 6) {
	        // 周六-下周一前
	        return "预计"+Number(new Date(time+2*24*3600*1000).getMonth()+1)+"月"+new Date(time+2*24*3600*1000).getDate()+"日前确认";
	    } else if (new Date(time).getDay() == 0) {
            // 周日-下周一前
	        return "预计"+Number(new Date(time+1*24*3600*1000).getMonth()+1)+"月"+new Date(time+1*24*3600*1000).getDate()+"日前确认";
        } else {
	        // 明天的情况
            return "预计"+Number(new Date(time+1*24*3600*1000).getMonth()+1)+"月"+new Date(time+1*24*3600*1000).getDate()+"日前确认";
	    }
	};

	// 根链接
	//var baseURL = shconfig.baseURL;
	var payOrderList = angular.module('orderList', []);
	payOrderList.controller("orderListCtrl", function($scope, $http) {
		
		var order = {};
		order.order_id = angular.element("#hiddenOrderPayId").val();
		
		var loadOrderCourseSplitURL = basePath + "/orderCourseSplit/preLoadOrderCourseSplit";

		$http({
			method: "POST",
		    url: loadOrderCourseSplitURL,
		    dataType: "json",
		    headers: {'Content-type':'application/json;charset=UTF-8'},
		    data: JSON.stringify(order)
		}).success(function(data, status) {
		    // success handle code
			$scope.runViewModel(data);
			// alert(data);
		}).error(function(data, status) {
		    // error handle code
			if (data.code.toString() == '500' ) {
        		alert('系统内部错误！');
        	} else {
        		alert(data.msg);
        	}
		});
		
		$scope.runViewModel = function(data) {
//			var dataModel = payOrderTesting.data.split_list;
			var dataModel = data.split_list;
			var invalidPayCount = GetValueLength(dataModel, "split_status", "0");
			var filterModel = function(dataModel) {
		        //console.log(dataModel);
		        var viewModel = [];
		        for (var i=0;list=dataModel,i < list.length;i++) {
		            switch (list[i].split_pay_type) {
		                case 0:
		                    list[i].pay_success_sequence = list[i].key_id;
		                    if (list[i].split_status == 1) {
		                        list[i].pay_from = payTypeOptions[0].name+"("+list[i].pay_from+")";
		                    } else {
		                        list[i].pay_from = payTypeOptions[0].name;
		                    }
		                    break;
		                case 1:
		                    list[i].pay_from = payTypeOptions[1].name;
                            break;
		                case 2:
		                    list[i].pay_from = payTypeOptions[2].name;
                            break;
		                case 3:
		                    list[i].pay_from = payTypeOptions[3].name;
		                    break;
		                case 4:
                            list[i].pay_from = payTypeOptions[4].name;
                            break;
		                default:
		                    break;
		            }
		            switch (list[i].split_status) {
		                case 0:
		                    list[i].payStatusName = "未支付";
		                    break;
		                case 1:
		                    list[i].payStatusName = "已支付";
		                    break;
		                case 2:
		                    list[i].payStatusName = "（待确认，"+getAdvisedTime(list[i], "update_date")+"）";
		                    break;
		                case 3:
		                    list[i].payStatusName = "（已确认）";
		                    break;
		                default:
		                    break;
		            }
		            viewModel.push(list[i]);
		        }
		        return viewModel;
		    };
			var viewObject = {
					"isMinus": true,
					"payBank": null,
					"payStatusName": ""
			};
			$scope.user_from_type = data.user_from_type;
			$scope.viewModel = filterModel(JoinList(dataModel, viewObject)).sort(sortRule("split_status"));
			$scope.invalidPayCount = invalidPayCount;
			
			// event
			$scope.packBtn = function(obj,index) {
				//console.log(obj);
				//console.log(obj.toElement);
				$scope.viewModel[index].isMinus = !$scope.viewModel[index].isMinus;
				$("#payOrderList").children("ul").children().eq(index).find(".paylist-info").slideToggle(300);
			};
			
			// 我要付款
			$scope.goPayBank = function(price,payBank,keyId) {
				// console.log(price+","+payBank);
				
				if(payBank=="支付宝"){
					$("#split_order_id").val(keyId);
					$("#alipaySubmitForm").submit();
				}else if(payBank=="快钱"){
					//alert('正在努力开发中...');
					$("#split_order_id_kuaiqian").val(keyId);
					$("#kuaiqianpaySubmitForm").submit();
				}else{
					alert("支付类型错误！！！！！！");
					return;
				}
				//alert(price+","+payBank+","+keyId);
			};

			// 查看我的合同
			$scope.checkOrder = function() {
				window.location.href = basePath+"/ordercourse/detail/"+order.order_id;
			};

			// 返回会员首页
			$scope.backUserCenter = function() {
				window.location.href = basePath+"/ucenter/index";
			};
		}
		
	});
	angular.bootstrap($("#payOrderList"), ["orderList"]);

//	return payOrderList;
//});
