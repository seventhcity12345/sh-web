(function () {
    var loadContractList = angular.module('SubscribeCourseListModule', [])
        // 加载已预约课程列表
        .factory('loadSubscribeCourseListService', function ($http, $q) {
            var service = {
                requestURL: "http://" + (window.location + '')
                    .split('//')[1].split('/')[0] + "/weixin/course/findSubscribeCourseList",
                testingRequestData: {},
                testingResponseData: {
                    "subscribe_list": {
                        "subscribe_id": "", // string 预约id
                        "teacher_id": "", // string 老师id
                        "teacher_name": "", // string 老师名称
                        "start_time": "", // date 开始时间
                        "end_time": "", // date 结束时间
                        "teacher_photo": "", // string 老师图像
                        "teacher_nationality": "", // string 老师国籍
                        "course_id": "", // string 课程id
                        "course_name": "", // string 课程名称
                        "course_pic": "", // string 课程图像
                        "course_type": "", // string 课程类别
                        "before_lesson_countdown": 0, //倒计时时间，毫秒
                        "before_lesson_time": 600000, //上课前多少时间进入教室，毫秒
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
        });
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
