(function () {
    var loadContractList = angular.module('ContractModule', [])
        // 加载课程类型信息（一对一，Lecture，OC，ES，etc）
        .factory('loadCourseTypeListService', ["$http", "$q", function ($http, $q) {
            var service = {
                requestURL: "http://" + (window.location + '')
                    .split('//')[1].split('/')[0] + "/weixin/course/findCourseTypeList",
                testingRequestData: {
                    "order_id": "" //string(32) 合同id
                },
                testingResponseData: {
                    "course_type_list": [{
                        "course_type": "", //string 课程类别//category_type1,category_type2
                        "course_type_name": "", //一对一，Lecture
                        "course_count": 30, //int 课程类别的总剩余数量
                    }]
                },
                setURL: function (obj) {
                    this.requestURL = obj;
                },
                // get server data
                getResponseData: function (obj) {
                    var _this = this;
                    var sendData = null;
                    var result = {};
                    if (!!obj) {
                        sendData = JSON.stringify(obj);
                    }

                    $http({
                            method: "POST",
                            url: _this.requestURL,
                            dataType: "json",
                            headers: {
                                'Content-type': 'application/json;charset=UTF-8'
                            },
                            data: sendData
                        })
                        .success(function (data, status, headers, config) {
                            result.data = data;
                            result.status = status;
                            result.headers = headers;
                            result.config = config;
                        })
                        .error(function (data, status, headers, config) {
                            result.data = data;
                            result.status = status;
                            result.headers = headers;
                            result.config = config;
                        });
                    return result;
                },
                getPromiseQuery: function (obj) {
                    var _this = this;
                    var deferred = $q.defer(); // 声明延后执行，表示要去监控后面的执行
                    var sendData = null;
                    if (obj !== undefined) {
                        sendData = JSON.stringify(obj);
                    }

                    $http({
                            method: "POST",
                            url: _this.requestURL,
                            dataType: "json",
                            headers: {
                                'Content-type': 'application/json;charset=UTF-8'
                            },
                            data: sendData
                        })
                        .success(function (data, status, headers, config) {
                            deferred.resolve(data); // 声明执行成功，即http请求数据成功，可以返回数据了
                        })
                        .error(function (data, status, headers, config) {
                            deferred.reject(data); // 声明执行失败，即服务器返回错误
                        });
                    return deferred.promise; // 返回承诺，这里并不是最终数据，而是访问最终数据的API
                }
            };
            return service;
        }])
        // 加载合同列表数据
        .factory('loadContractListService', ["$http", "$q", function ($http, $q) {
            var service = {
                // order_list: {},
                requestURL: "http://" + (window.location + '')
                .split('//')[1].split('/')[0] + "/weixin/ordercourse/usingOrderList",
                testingResponseData: {
                    "order_list": [{
                        "key_id": "ac606c8995cc43fdbb721bc214c4bf22",
                        "course_package_name": "3个月标准课程",
                        "category_type": "category_type2",
                    }, {
                        "key_id": "ac606c8995cc43fdbb721bc214c4bf22",
                        "course_package_name": "6个月标准课程",
                        "category_type": "category_type2",
                    }]
                },
                setURL: function (obj) {
                    this.requestURL = obj;
                },

                // get server data
                getResponseData: function () {
                    var _this = this;
                    var result = {};

                    $http({
                            method: "POST",
                            url: _this.requestURL,
                            dataType: "json",
                            headers: {
                                'Content-type': 'application/json;charset=UTF-8'
                            },
                            data: {}
                        })
                        .success(function (data, status, headers, config) {
                            result.data = data;
                            result.status = status;
                            result.headers = headers;
                            result.config = config;
                        })
                        .error(function (data, status, headers, config) {
                            result.data = data;
                            result.status = status;
                            result.headers = headers;
                            result.config = config;
                        });
                    return result;
                },
                getPromiseQuery: function () {
                    var _this = this;
                    var deferred = $q.defer(); // 声明延后执行，表示要去监控后面的执行

                    $http({
                            method: "POST",
                            url: _this.requestURL,
                            dataType: "json",
                            headers: {
                                'Content-type': 'application/json;charset=UTF-8'
                            },
                            data: {}
                        })
                        .success(function (data, status, headers, config) {
                            deferred.resolve(data); // 声明执行成功，即http请求数据成功，可以返回数据了
                        })
                        .error(function (data, status, headers, config) {
                            deferred.reject(data); // 声明执行失败，即服务器返回错误
                        });
                    return deferred.promise; // 返回承诺，这里并不是最终数据，而是访问最终数据的API
                }
            };
            return service;
        }]);
    //angular.bootstrap($("#loadContractList"), ["loadContractList"]);

})();

// var promise = loadContractListService.query(); // 同步调用，获得承诺接口
// promise.then(function (data) { // 调用承诺API获取数据 .resolve
//     $scope.user = data;
// }, function (data) { // 处理错误 .reject
//     $scope.user = {
//         error: '用户不存在！'
//     };
// });
