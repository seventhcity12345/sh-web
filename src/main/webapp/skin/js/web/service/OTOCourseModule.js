(function () {
    var loadContractList = angular.module('OTOCourseModule', [])
        // 加载一对一课程信息表
        .factory('loadOTOCourseInfoService', ["$http", "$q", function ($http, $q) {
            var service = {
                requestURL: "http://" + (window.location + '')
                    .split('//')[1].split('/')[0] + "/weixin/course/findOne2OneCourseInfoList",
                testingRequestData: {
                    "category_type": "category_type2", // string 体系类别（从合同中获取）
                },
                testingResponseData: {
                    "course_list": [{
                        "key_id": "", // string 课程id
                        "category_type": "", // string 课程类别
                        "course_title": "", // string 课程名称
                        "course_pic": "", // stirng 课程图片
                        "courseRate": 100, // int 课程进度
                        "ratesLimit": 20, // 可预约课界限
                        "status": 2, // 课程状态
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
        // 加载一对一可预约时间项
        .factory('loadOTOCourseDateService', ["$http", "$q", function ($http, $q) {
            var service = {
                requestURL: "http://" + (window.location + '')
                    .split('//')[1].split('/')[0] + "/weixin/course/loadCourseOne2OneDateList",
                testingRequestData: {},
                testingResponseData: {
                    "date_list": {
                        "date": "", // string
                        "flag": true, // 是否存在课程
                    }
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
        }])
        // 加载一对一教师及对应时间表
        .factory('loadOTOTeacherTimeService', ["$http", "$q", function ($http, $q) {
            var service = {
                requestURL: "http://" + (window.location + '')
                    .split('//')[1].split('/')[0] + "/weixin/course/findTeacherTimeList",
                testingRequestData: {
                    "start_time": "", // string 开始时间
                    "end_time": "", // string 结束日期
                    "request_from": "weixin" // string 请求来源（只有微信端有标识，值为"weixin"，原来PC端不用管）
                },
                testingResponseData: {
                    "19:00~19:30": [{ //course_list
                        "teacher_time_id": "", //string 排课id
                        "teacher_id": "", //string 老师id
                        "course_type": "", // string 课程类别
                        "vcube_room_id": "", // string 微立方房间ID
                        "start_time": "", // date 允许上课开始时间
                        "end_time": "", // date  允许上课结束时间
                        "teacher_name": "", // string 老师名称
                        "teacher_nationality": "", // string 老师国籍
                        "teacher_photo": "", // string 老师图像
                    }],
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
        // 预约一对一课程
        .factory('subscribeOTOCourseService', ["$http", "$q", function ($http, $q) {
            var service = {
                requestURL: "http://" + (window.location + '')
                    .split('//')[1].split('/')[0] + "/weixin/course/subscribeCourseOne2One",
                testingRequestData: {
                    "order_id": "", // string 合同id
                    "course_type": "", // string 课程类别
                    "course_id": "", // string 课程id
                    "teacher_time_id": "", // string 排课id
                    "subscribe_from": "weixin", // string 从哪里预约（只有微信端才给标识，值为"weixin"，原来PC端不用管）
                },
                testingResponseData: {
                    "code": "", // string 返回码
                    "msg": "", // string 提示信息
                    "data": {
                        "course": {
                            "start_time": "", //date 允许上课开始时间
                            "end_time": "", // date 允许上课结束时间
                            "teacher_id": "", // 老师id
                            "teacher_name": "", // 老师名称
                            "course_type": "", // 课程类别
                            "course_id": "", // 课程id
                            "course_name": "", // 课程名称
                            "subscribe_id": "", // 预约id
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
        // 取消预约一对一课程
        .factory('unsubscribeOTOCourseService', function ($http, $q) {
            var service = {
                requestURL: "http://" + (window.location + '')
                    .split('//')[1].split('/')[0] + "/weixin/course/cancelSubscribeCourseOne2One",
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
