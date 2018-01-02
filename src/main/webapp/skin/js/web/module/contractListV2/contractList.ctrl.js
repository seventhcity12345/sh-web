var contractListTesting = {
		data: {
		    "order_list": [
		        {
		            "key_id": "e9fa2451bb8f4d498ce11695890d1bf2",
		            "course_package_name": "1个月标准课程",
		            "pay_time": 1452594554000,
		            "create_date": 1452569934000,//拟定合同日期

					//合同有效期start
					"start_order_time": 1452569934000,
					//合同有效期end
					"end_order_time": 1452569934000,
					//原价
					"total_show_price": 8288,
					//优惠价
					"total_real_price": 6288,
					//实付价
					"total_final_price": 6288,

					//合同状态
					"contract_status": 2,//1.待确认,2.执行中,3.已结束
					//订单状态
		            "order_status": 5,//1.已拟定,2.已发送,3.已确认,4.支付中,5.已支付,6.已过期,7.已终止

					//是否是续约合同
					"order_original_type": 1//合同类型(0:普通合同,1:续约合同)
		        },
				{
		            "key_id": "e9fa2451bb8f4d498ce11695890d1bf2",
		            "course_package_name": "1个月标准课程",
		            "pay_time": 1452594554000,
		            "create_date": 1452569934000,//拟定合同日期

					//合同有效期start
					"start_order_time": 1452569934000,
					//合同有效期end
					"end_order_time": 1452569934000,
					//原价
					"total_show_price": 8288,
					//优惠价
					"total_real_price": 6288,
					//实付价
					"total_final_price": 6288,

					//合同状态
					"contract_status": 3,//1.待确认,2.执行中,3.已结束
					//订单状态
		            "order_status": 6,//1.已拟定,2.已发送,3.已确认,4.支付中,5.已支付,6.已过期,7.已终止

					//是否是续约合同
					"order_original_type": 0//合同类型(0:普通合同,1:续约合同)
		        },
				{
		            "key_id": "e9fa2451bb8f4d498ce11695890d1bf2",
		            "course_package_name": "1个月标准课程",
		            "pay_time": 1452594554000,
		            "create_date": 1452569934000,//拟定合同日期

					//合同有效期start
					"start_order_time": 1452569934000,
					//合同有效期end
					"end_order_time": 1452569934000,
					//原价
					"total_show_price": 8288,
					//优惠价
					"total_real_price": 6288,
					//实付价
					"total_final_price": 6288,

					//合同状态
					"contract_status": 1,//1.待确认,2.执行中,3.已结束
					//订单状态
		            "order_status": 2,//1.已拟定,2.已发送,3.已确认,4.支付中,5.已支付,6.已过期,7.已终止

					//是否是续约合同
					"order_original_type": 0//合同类型(0:普通合同,1:续约合同)
		        },
				{
		            "key_id": "e9fa2451bb8f4d498ce11695890d1bf2",
		            "course_package_name": "1个月标准课程",
		            "pay_time": 1452594554000,
		            "create_date": 1452569934000,//拟定合同日期

					//合同有效期start
					"start_order_time": 1452569934000,
					//合同有效期end
					"end_order_time": 1452569934000,
					//原价
					"total_show_price": 8288,
					//优惠价
					"total_real_price": 6288,
					//实付价
					"total_final_price": 6288,

					//合同状态
					"contract_status": 1,//1.待确认,2.执行中,3.已结束
					//订单状态
		            "order_status": 2,//1.已拟定,2.已发送,3.已确认,4.支付中,5.已支付,6.已过期,7.已终止

					//是否是续约合同
					"order_original_type": 1//合同类型(0:普通合同,1:续约合同)
		        },
				{
		            "key_id": "e9fa2451bb8f4d498ce11695890d1bf2",
		            "course_package_name": "1个月标准课程",
		            "pay_time": 1452594554000,
		            "create_date": 1452569934000,//拟定合同日期

					//合同有效期start
					"start_order_time": '',
					//合同有效期end
					"end_order_time": '',
					//原价
					"total_show_price": 8288,
					//优惠价
					"total_real_price": 6288,
					//实付价
					"total_final_price": 6288,

					//合同状态
					"contract_status": 1,//1.待确认,2.执行中,3.已结束
					//订单状态
		            "order_status": 2,//1.已拟定,2.已发送,3.已确认,4.支付中,5.已支付,6.已过期,7.已终止

					//是否是续约合同
					"order_original_type": 1//合同类型(0:普通合同,1:续约合同)
		        },
		    ]
		}
	};

	// getbasePath;
	if (basePath === undefined) {
		var basePath = "http://" + (window.location+'').split('//')[1].split('/')[0];
	}

	// 根链接
	//var baseURL = shconfig.baseURL;
	var contractList = angular.module('contractList', []);
	contractList.controller("contractListCtrl", function($scope, $http) {

		var loadContractListURL = basePath + "/ucenter/ordercourse/userOrderList";

		$http({
			method: "POST",
		    url: loadContractListURL,
		    headers: {'Content-type':'application/json;charset=UTF-8'},
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
			//$scope.viewModel = contractListTesting.data.order_list;
			$scope.viewModel = data.order_list;

			// 查看合同详情
			$scope.checkOrderDetail = function(keyId) {
				//console.log(keyId);
				window.location.href = basePath+"/ordercourse/detail/"+keyId;
			};

			// 查看支付详情
            $scope.goToPayDetail = function(keyId) {
                //console.log(keyId);
                window.location.href = basePath+"/ucenter/ordercourse/myPayDetailList/"+keyId;
            };

			// 待确认
			$scope.confirmContract = function(keyId) {
				//console.log(keyId);
				window.location.href = basePath+"/ordercourse/detail/"+keyId;
			};

			// 待付款
			$scope.waitPaying = function(keyId) {
				//console.log(keyId);
				window.location.href = basePath+"/ucenter/kuaiqian/pay/ordercourse/"+keyId;
			};
		};
		//$scope.runViewModel(null);
	});
	angular.bootstrap($("#contractList"), ["contractList"]);
