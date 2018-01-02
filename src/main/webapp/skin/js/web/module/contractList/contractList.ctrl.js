
//define(["angular","jquery","utility","json!shconfig.json!bust"], function(angular,$,utility,shconfig) {
	var contractListTesting = {
		data: {
		    "order_list": [
		        {
		            "key_id": "e9fa2451bb8f4d498ce11695890d1bf2",
		            "course_package_name": "1个月标准课程",
		            "pay_time": 1452594554000,
		            "create_date": 1452569934000,
		            "order_status": 2
		        },
				{
		            "key_id": "e9fa2451bb8f4d498ce11695890d1bf3",
		            "course_package_name": "3个月标准课程",
		            "pay_time": 1452594554000,
		            "create_date": 1452569934000,
		            "order_status": 3
		        },
				{
		            "key_id": "e9fa2451bb8f4d498ce11695890d1bf4",
		            "course_package_name": "9个月标准课程",
		            "pay_time": 1452594554000,
		            "create_date": 1452569934000,
		            "order_status": 4
		        },
				{
		            "key_id": "e9fa2451bb8f4d498ce11695890d1bf5",
		            "course_package_name": "6个月标准课程",
		            "pay_time": 1452594554000,
		            "create_date": 1452569934000,
		            "order_status": 5
		        },
		    ]
		}
	};

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
//			$scope.viewModel = contractListTesting.data.order_list;
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
		}
	});
//	require(["contractListCtrl"], function(contractList){
	    angular.bootstrap($("#contractList"), ["contractList"]);
//	});

//	return contractList;
//});
