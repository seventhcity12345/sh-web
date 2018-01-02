(function () {
    var EnglishStudioList = angular.module('EnglishStudioModule', [])
        // 加载课程类型信息（一对一，Lecture，OC，ES，etc）
        .factory('loadEnglishStudioListService', ["$http", "$q", function ($http, $q) {
            var service = {
                requestURL: "http://" + (window.location + '')
                    .split('//')[1].split('/')[0] + "/ucenter/scheduling/findCourseEnglishStidoInfo",
                testingRequestData: {},
                testingResponseData: {
                    "success": true,
                    "msg": "",
                    "data": [{
                        "keyId": null,//（不用）
                        "categoryType": null,//（不用）
                        "courseType": null,//（不用）
                        "courseId": null,//（不用）
                        "courseTitle": "66得到",
                        "courseLevel": null,//（不用）
                        "coursePic": "http://webi-hwj-test.oss-cn-hangzhou.aliyuncs.com/images/course/one2many/English Studio/9c255732a634486b9da55bc784e303be.jpg",
                        "teacherId": null,//（不用）
                        "teacherName": "4656",
                        "teacherTimeId": null,//（不用）
                        "courseCourseware": null,//（不用）
                        "documentId": null,//（不用）
                        "startTime": 1464075000000,
                        "endTime": 1464075300000,
                        "courseDesc": "sdfsdfsdfsdfsdfsdfsf",
                        "teacherUrl": null,//（不用）
                        "studentUrl": "as dfas dfas dfas dfas df",
                        "teacherPhoto": "http://webi-hwj-test.oss-cn-hangzhou.aliyuncs.com/images/course/one2many/course_type8/148874a7170947cda705f6d2294353cb.jpg",
                        "alreadyPersonCount": null,//（不用）
                        "createDate": null,//（不用）
                        "updateDate": null,//（不用）
                        "createUserId": null,//（不用）
                        "updateUserId": null,//（不用）
                        "isUsed": null,//（不用）
                        "isSubscribe": null,//（不用）
                        "isConfirm": null,//（不用）
                        "teacherNationality": "巴哈马"
                      },
                   ]
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
})();

// var promise = loadContractListService.query(); // 同步调用，获得承诺接口
// promise.then(function (data) { // 调用承诺API获取数据 .resolve
//     $scope.user = data;
// }, function (data) { // 处理错误 .reject
//     $scope.user = {
//         error: '用户不存在！'
//     };
// });
