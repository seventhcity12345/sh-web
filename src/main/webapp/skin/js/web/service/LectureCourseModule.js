(function () {
    var loadContractList = angular.module('LectureCourseModule', [])
        // 加载Lecture课程信息表
        .factory('loadLectureCourseInfoService', ["$http", "$q", function ($http, $q) {
            var service = {
                requestURL: "http://" + (window.location + '')
                    .split('//')[1].split('/')[0] + "/weixin/course/findLectureCourseInfoList",
                testingRequestData: {
                    "category_type": "", //string 体系类别（从合同中获取）
                    "day": "", // string 选择预约的日期
                },
                testingResponseData: {
                    "course_list": [{
                        "teacher_id": "", // string 老师 id
                        "teacher_name": "", // string 老师名称
                        "teacher_photo": "", // string 老师图像
                        "teacher_nationality": "", // string 老师国籍
                        "start_time": "", // string 上课开始时间
                        "end_time": "", // stirng 上课结束时间
                        "course_pic": "", // string 课程图片
                        "course_title": "", // string 课程名称
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
        // 加载Lecture可预约课程时间表
        .factory('loadLectureCourseDateService', ["$http", "$q", function ($http, $q) {
            var service = {
                requestURL: "http://" + (window.location + '')
                    .split('//')[1].split('/')[0] + "/weixin/course/loadCourseLectureDateList",
                testingRequestData: {
                    "category_type": ""
                },
                testingResponseData: {
                    "date_list": [{
                        "date": "",
                        "flag": true, //boolean 该天是否有课程可约
                    }]
                },
                setURL: function (obj) {
                    this.requestURL = obj;
                },
                // get server data
                getResponseData: function (obj) {
                    var _this = this;
                    var result = {};
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
        // 确认预约Lecture课程表
        .factory('subscribeLectureCourseService', ["$http", "$q", function ($http, $q) {
            var service = {
                requestURL: "http://" + (window.location + '')
                    .split('//')[1].split('/')[0] + "/weixin/course/subscribeCourseLecture",
                testingRequestData: {
                    "order_id": "", // string 合同id
                    "course_type": "", // string 课程类别
                    "course_id": "", // string 课程id
                    "teacher_id": "", // string 老师id
                    "subscribe_from": "weixin", // string 从哪里预约（只有微信端才给标识,值为“weixin”），原来PC端不用管
                },
                testingResponseData: {
                    "code": "", // string 返回码
                    "msg": "", // string 提示信息
                    "data": {
                        "course": {
                            "start_time": "", // date 允许上课开始时间
                            "end_time": "", // date 允许上课结束时间
                            "teacher_id": "", // string 老师id
                            "teacher_name": "", // string 老师名称
                            "course_type": "", // string 课程类别
                            "course_id": "", // string 课程id
                            "course_name": "", // string 课程名称
                            "subscribe_id": "", // string 预约id
                        }
                    }
                },
                setURL: function (obj) {
                    this.requestURL = obj;
                },
                // get server data
                getResponseData: function (obj) {
                    var _this = this;
                    var sendData = null;
                    var result = {};
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
        // 取消预约Lecture课程表
        .factory('unsubscribeLectureCourseService', function ($http, $q) {
            var service = {
                requestURL: "http://" + (window.location + '')
                    .split('//')[1].split('/')[0] + "/weixin/course/cancelSubscribeCourseLecture",
                testingRequestData: {
                    "subscribe_id": "", //string 预约id
                },
                testingResponseData: {
                    "code": "", // string 返回码
                    "msg": "", // string 提示信息
                    "data": "obj", // Object 返回数据
                },
                setURL: function (obj) {
                    this.requestURL = obj;
                },
                // get server data
                getResponseData: function (obj) {
                    var _this = this;
                    var sendData = null;
                    var result = {};
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
        });
})();

// var promise = loadContractListService.query(); // 同步调用，获得承诺接口
// promise.then(function (data) { // 调用承诺API获取数据 .resolve
//     $scope.user = data;
// }, function (data) { // 处理错误 .reject
//     $scope.user = {
//         error: '用户不存在！'
//     };
// });
