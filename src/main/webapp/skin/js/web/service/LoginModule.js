(function () {
    var Login = angular.module('LoginModule', [])
        // 注销接口 basePath + "/logout"
        .factory('logoutService', ["$http", "$q", function ($http, $q) {
            var service = {

            };
            return service;
        }])
        // 查找用户信息 basePath + "/findSessionUser"
        .factory('findSessionUserService', ["$http", "$q", function ($http, $q) {
            var service = {

            };
            return service;
        }])
        // 登录加密接口 basePath + "/encodeLogin"
        .factory('encodeLoginService', ["$http", "$q", function ($http, $q) {
            var service = {
                requestURL: "http://" + (window.location + '')
                    .split('//')[1].split('/')[0] + "/encodeLogin",
                testingRequestData: {
                    "code": "13564560687"
                },
                testingResponseData: {
                    "phone": "13564560687",
                    "pwd": "", //128位加密后的密码
                },
                setURL: function (obj) {
                    this.requestURL = obj;
                },
                // get server data
                getResponseData: function (obj) {
                    var _this = this;
                    var result = {};
                    $http({
                            method: "POST",
                            url: _this.requestURL,
                            dataType: "json",
                            headers: {
                                'Content-type': 'charset=UTF-8'
                            },
                            params: obj
                        })
                        .success(function (data, status, headers, config) {
                            console.log(data);
                            result.data = data;
                            result.status = status;
                            result.headers = headers;
                            result.config = config;
                        })
                        .error(function (data, status, headers, config) {
                            console.log(data);
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

                    $http({
                            method: "POST",
                            url: _this.requestURL,
                            dataType: "json",
                            headers: {
                                'Content-type': 'charset=UTF-8'
                            },
                            params: obj
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
        // 登录接口 basePath + "/login",
        .factory('loginService', function ($http, $q) {
            var service = {
                // order_list: {},
                requestURL: "http://" + (window.location + '')
                    .split('//')[1].split('/')[0] + "/login",
                testingRequestData: {
                    "phone": "13564560687",
                    "pwd": "", //128位加密后的密码
                    "loginFrom": "weixin_student", //登录来源
                    "rememberMe": "true", //微信端默认为true，PC端根据用户选择
                },
                testingResponseData: {
                    "success": true, //操作成功或者失败
                    "msg": "XXXX", //提示信息
                    "data": {
                        "phone": "13564560687",
                        "is_student": "", // 是否是学员
                    }
                },
                setURL: function (obj) {
                    this.requestURL = obj;
                },
                // get server data
                getResponseData: function (obj) {
                    var _this = this;
                    var result = {};

                    $http({
                            method: "POST",
                            url: _this.requestURL,
                            dataType: "json",
                            headers: {
                                'Content-type': 'charset=UTF-8'
                            },
                            params: obj
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

                    $http({
                            method: "POST",
                            url: _this.requestURL,
                            dataType: "json",
                            headers: {
                                'Content-type': 'charset=UTF-8'
                            },
                            params: obj
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

//去往个人中心
// "uCenterURL": basePath + "/ucenter/index",
// //获取user信息
// "findSessionUserURL": basePath + "/findSessionUser",
// //登录接口
// "mctLoginURL": basePath + "/login",
// //注册接口
// "mctRegisterURL": basePath + "/register",
// //注销接口
// "mctLogoutURL": basePath + "/logout",
// //登录加密接口
// "mctEncodeLogin": basePath + "/encodeLogin",
// //注册加密接口
// "mctEncodeRegister": basePath + "/encodeRegister",
// //手机号验重接口
// "mctCheckRepeat": basePath + "/checkRepeat",
// //发送验证码接口
// "mctSendSms": basePath + "/sendSms",
