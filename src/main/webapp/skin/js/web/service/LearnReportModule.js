(function () {
    var learnReport = angular.module('LearnReportModule', [])
        // 加载课程类型信息（一对一，Lecture，OC，ES，etc）
        .factory('learnReportService', ["$http", "$q", function ($http, $q) {
            var service = {
                requestURL: "http://" + (window.location + '')
                    .split('//')[1].split('/')[0] + "/ucenter/user/findUserLearningProgress",
                testingRequestData: {
                    "orderId": "" //string(32) 合同id
                },
                testingResponseData: {
                    "consumeOrderDays": 100, //合同进行天数
                    "remainOrderDays": 20, //剩余合同天数
                    "totalCourseCount": 100, //总课时数
                    "consumeCourseCount": 20, //已消耗课时数
                    "remainCourseCount": 80, //剩余课时数
                    "noShowCourseCount": 1, //未出席课时数
                    "expectedLearningProgress": 50, //应有学习进度
                    "actualLearningProgress": 50, //实际学习进度
                    "currentLevel": 2, //当前级别
                    "currentLevelOne2OneCourseCount": 8, //本级已上1v1课程数
                    "currentLevelOne2ManyCourseCount": 8, //本级已上1vN课程数
                    "levelupTotalCourseCount": 18, //升级需上课程数
                    "toNextLevelOne2OneCourseCount": 1, //达到下一级别需要完成的1v1课程数
                    "toNextLevelOne2ManyCourseCount": 1, //达到下一级别需要完成的1vN课程数
                    "effectiveLearningDays": ["2016-06-06", "2016-06-07"], //课件学习记录（学习N分钟以上算有效，一个月内有效的）
                    "effectiveExerciseTime": 30, //课件有效练习时间
                    "encouragementWords": "好的学习习惯将会让你事半功倍！", //小嗨要说的话
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
        //angular.bootstrap($("#loadContractList"), ["loadContractList"]);

})();