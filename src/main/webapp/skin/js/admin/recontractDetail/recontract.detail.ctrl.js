function adminRecontractDetail() {

    var adminRecontractDetailTesting = {
            data: [{
                //合同id
                "key_id": "e9fa2451bb8f4d498ce11695890d1bf2",
                //课程包名称
                "course_package_name": "1个月标准课程",
                //合同开始时间
                "start_order_time": 1452594554000,
                //合同结束时间
                "end_order_time": 1452594554000,
                //原价
                "total_show_price": 8828,
                //优惠价
                "total_real_price": 6628,
                //实际付款金额（补差金额）
                "total_final_price": 1000
            }, {
                //合同id
                "key_id": "e9fa2451bb8f4d498ce11695890d1bf2",
                //课程包名称
                "course_package_name": "1个月标准课程",
                //合同开始时间
                "start_order_time": 1452594554000,
                //合同结束时间
                "end_order_time": 1452594554000,
                //原价
                "total_show_price": 8828,
                //优惠价
                "total_real_price": 6628,
                //实际付款金额（补差金额）
                "total_final_price": 1000
            }, {
                //合同id
                "key_id": "e9fa2451bb8f4d498ce11695890d1bf2",
                //课程包名称
                "course_package_name": "1个月标准课程",
                //合同开始时间
                "start_order_time": 1452594554000,
                //合同结束时间
                "end_order_time": 1452594554000,
                //原价
                "total_show_price": 8828,
                //优惠价
                "total_real_price": 6628,
                //实际付款金额（补差金额）
                "total_final_price": 1000
            }]
    };

    // getbasePath;
    if (basePath === undefined) {
        var basePath = "http://" + (window.location+'').split('//')[1].split('/')[0];
    }

    var adminRecontractDetail = angular.module('adminRecontractDetail', []);
    adminRecontractDetail.controller("adminRecontractDetailCtrl", function($scope, $http) {
        
        var url = basePath + "/admin/orderCourse/findRenewalOrderCourseDetail";
        var dataObj = {
                "order_id": $("#hiddenRenewalContract").val()
        };

        $http({
            method: "POST",
            url: url,
            headers: {'Content-type':'application/json;charset=UTF-8'},
            data: JSON.stringify(dataObj)
        }).success(function(data, status) {
            // success handle code
            // var dataModel = adminRecontractDetailTesting.data;
            if (data.code == '500' && data.order_list == undefined) {
                alert(data.msg);
            } else if (data.order_list != undefined) {
                $scope.viewModel = data.order_list;
            } else {
                alert(data.msg);
            }
        }).error(function(data, status) {
            // error handle code
            alert("error:"+status+","+data);
        });
        
    });

    angular.bootstrap($("#adminRecontractDetail"), ["adminRecontractDetail"]);

}
