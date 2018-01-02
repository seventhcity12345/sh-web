// ES v2++ 添加数据接口
(function() {
    
//    "dataConfig" : { //单位分钟
//        "timeOpeningClass" : 5,//上课前可提前进入教室的时间
//        "timeClosingClass" : 50//上课后可延续进入教室的时间
//    },
    // @param {eng_studio_status} 2:可进入教室;1:不可进入教室;0:数据已过期

    // example
    var englishStudioTestingList = [{
            "eng_studio_name" : "词汇课：Seafood",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-05-20 19:00",
            "eng_studio_start_time" : 1463742000000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/seafood.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Crystal",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Crystal.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/c97cbc8d42317d69b8dae159f28b5edb4f33ee2c"
        }, {
            "eng_studio_name" : "Western Food and Restaurants",
            "eng_studio_description" : "了解西方餐饮文化及风俗，做一顿丰盛美味的西餐。",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-05-21 19:00",
            "eng_studio_start_time" : 1463828400000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Western-Food-and-Restaurants.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Dylan",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Dylan.jpg",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/57bb5af2c93089651901f34ac6cd3f6411b33108"
        }, 
    ];

    var getGMTTime = function(systemTime) {// 单位时间戳毫秒级
        var tempDate = new Date();
        var temp = 6*60*60*1000;// 美国中部时间与gmt时间差,单位毫秒
        return new Date(systemTime).getTime() - temp + tempDate.getTimezoneOffset()*60*1000;
    };

    var englishStudioList = angular.module('englishStudioList', []);
    englishStudioList.controller("englishStudioListCtrl", ["$scope", "$http", "$q", function($scope, $http, $q) {

        //var order = {};
        //order.order_id = angular.element("#hidden_order_id_by_split").val();
        var ACTIVITY_NAME = "MONTH_EXPERIENCE1";

        var getServerTime = function() {
            var serverTime = angular.element("#engStudioServerDate").val();// java cst time
            return getGMTTime(serverTime);
        };

        $scope.timeOpeningMinute = 0;

        var systemTime = getServerTime() || Date.parse(new Date().toString().replace(/-/g, "/"));

        $scope.serverTime = systemTime;

        /**
         * 返回指定时间之后或之前指定分钟数的时间
         * ------------------------------------------------------
         * @param {startTime},单位为秒式时间戳
         * @param {classTime},单位为分钟数
         * @param {isPlus},true为加时间，false为减时间
         * @return {endTime},单位为秒式时间戳
        */
        var getTargetTime = function(startTime, classTime, isPlus) {
            var addingTime = classTime * 60 * 1000;
            var targetDate = new Date();
            if (isPlus) {
                targetDate.setTime(startTime + addingTime);
            } else {
                targetDate.setTime(startTime - addingTime);
            }
            var targetTime = Date.parse(targetDate.replace(/-/g, "/"));

            return targetTime;
        };

        var getLatestCountdown = function(time) {
            var oneHourTime = 1*3600*1000;
            var oneMinuteTime = 1*60*1000;
            var oneSecondTime = 1*1000;

            var latestCountdown = {};
            if (time <= 0 || time === null) {
                latestCountdown = {
                    "hour": "00",
                    "minute": "00",
                    "second": "00"
                };
                return latestCountdown;
            } else {
                var curDate = new Date(time);
                //console.log(time);
                var chour = Math.floor(time / oneHourTime);
                var cminute = Math.floor((time-Math.floor(chour)*3600*1000) / oneMinuteTime);
                var csecond = Math.floor((time - Math.floor(time / oneMinuteTime)*60*1000) / oneSecondTime);
                latestCountdown = {
                    "hour": chour.toString().length == 1 ? "0"+chour : chour,
                    "minute": cminute.toString().length == 1 ? "0"+cminute : cminute,
                    "second": csecond.toString().length == 1 ? "0"+csecond : csecond
                };
                // console.log(latestCountdown);
                return latestCountdown;
            }
        };


        // canGoToClass 上课提前X分钟进入教室
        var runningCountdown = function(obj) {
            
            var startTime = obj.eng_studio_start_time;
            var endTime = obj.eng_studio_end_time;
            var canGoToClass = obj.canGoToClass;
            var ESTimer = obj.timer;
            
            var closeDuringTime = Number(endTime - startTime);
            if (ESTimer !== undefined) {
                clearInterval(ESTimer);
            }

            $scope.timeOpeningMinute = canGoToClass;
            openTime = canGoToClass*60*1000;
            var time = (startTime - openTime) - systemTime;
            var beginTime = startTime - systemTime;

            var changeStatus = function() {
                if (time <= 0 && time > -(closeDuringTime+openTime)) {
                    $scope.$apply(function() {
                        obj.eng_studio_status = 2;
                    });
                } else if (time <= -(closeDuringTime+openTime)) {
                    clearInterval(ESTimer);
                    $scope.$apply(function() {
                        obj.eng_studio_status = 0;
                    });
                }
            };

            setTimeout(function(){
                $scope.$apply(function() {
                    obj.latestCountdown = getLatestCountdown(beginTime);
                });
                changeStatus();
            },0);
            ESTimer = setInterval(function() {
                time -= 1000;
                beginTime -= 1000;
//                countdownLogic(time, beginTime, closeDuringTime, ESTimer);
                $scope.$apply(function() {
                    obj.latestCountdown = getLatestCountdown(beginTime);
                });
                changeStatus();
            }, 1000);
            
            return obj;
        };

        // give class today
        var getLatestClass = function(objModelList) {
            var latestModelList = [];
            // console.log(objModel.eng_studio_name);
            for (var i=0,len=objModelList.length;i<len;i++) {
                var latestModel = {
                        "eng_studio_name": objModelList[i].eng_studio_name,
                        "eng_studio_img": objModelList[i].eng_studio_img,
                        "eng_studio_description": objModelList[i].eng_studio_description,
                        "eng_studio_status": objModelList[i].eng_studio_status,
                        "eng_studio_start_time": objModelList[i].eng_studio_start_time,
                        "eng_studio_end_time": objModelList[i].eng_studio_end_time,
                        "teacher_name": objModelList[i].teacher_name,
                        "teacher_type": objModelList[i].teacher_type,
                        "teacher_photo": objModelList[i].teacher_photo,
                        "eng_studio_link": objModelList[i].eng_studio_link,
                        "courseId": objModelList[i].courseId,
                        "keyId": objModelList[i].keyId,
                        "isSubscribe": objModelList[i].subscribeId == null ? false : true,
                        "subscribeId": objModelList[i].subscribeId,
                        "latestCountdown": null,
                        "timer": null,
                        "canGoToClass": objModelList[i].canGoToClass,
                };

                latestModelList.push(runningCountdown(latestModel));
            }
            $scope.latestModelList = latestModelList;
            $scope.isTodayHasClass = true;
        };

        var getViewModel = function(dataModel,name) {
            var list = [];
            var latestList = [];
            var hasOneClass = false;
            for (var item in dataModel) {
                var strDateS = new Date(dataModel[item].eng_studio_start_time).toDateString();
                var strDateE = new Date(systemTime).toDateString();
                var dateDiff = Date.parse(strDateS.replace(/-/g, "/"))-Date.parse(strDateE.replace(/-/g, "/"));
                var iDays = parseInt(Math.abs(dateDiff) / 1000 / 60 / 60 /24);

                if (iDays === 0) {
                    dataModel[item].iDays = iDays;
                    latestList.push(dataModel[item]);
                    hasOneClass = true;
                } else if (dateDiff > 0) {
                    dataModel[item].iDays = iDays;
                    list.push(dataModel[item]);
                } else {
                    continue;
                }
            }
            if (latestList.length > 0) {
                getLatestClass(latestList);
            }

            return list;
        };
        
        var promiseServiceList = {
                "getSubCoursePromise": function(keyId) {
                    var requestURL = "http://" + (window.location + '')
                    .split('//')[1].split('/')[0] + "/ucenter/subscribeCourse/courseEnglishStudio/subscribeEnglishStudioCourse";
                    var deferred = $q.defer(); // 声明延后执行，表示要去监控后面的执行
                    var sendData = {
                            "course_id": keyId
                    };
                    
                    $http({
                        method: "POST",
                        url: requestURL,
                        dataType: "json",
                        headers: {
//                        'Content-type': 'application/json;charset=UTF-8',
                            'Content-type': 'charset=UTF-8'
                        },
//                    data: JSON.stringify(sendData),
                        params: sendData,
                    })
                    .success(function (data, status, headers, config) {
                        deferred.resolve(data); // 声明执行成功，即http请求数据成功，可以返回数据了
                    })
                    .error(function (data, status, headers, config) {
                        deferred.reject(data); // 声明执行失败，即服务器返回错误
                    });
                    return deferred.promise; // 返回承诺，这里并不是最终数据，而是访问最终数据的API
                },
                "getUnsubCoursePromise": function(subscribeId) {
                    var requestURL = "http://" + (window.location + '')
                    .split('//')[1].split('/')[0] + "/ucenter/subscribeCourse/courseEnglishStudio/cancelEnglishStudioCourse";
                    var deferred = $q.defer(); // 声明延后执行，表示要去监控后面的执行
                    var sendData = {
                            "key_id": subscribeId
                    };
                    
                    $http({
                        method: "POST",
                        url: requestURL,
                        dataType: "json",
                        headers: {
//                        'Content-type': 'application/json;charset=UTF-8',
                            'Content-type': 'charset=UTF-8'
                        },
//                    data: JSON.stringify(sendData),
                        params: sendData,
                    })
                    .success(function (data, status, headers, config) {
                        deferred.resolve(data); // 声明执行成功，即http请求数据成功，可以返回数据了
                    })
                    .error(function (data, status, headers, config) {
                        deferred.reject(data); // 声明执行失败，即服务器返回错误
                    });
                    return deferred.promise; // 返回承诺，这里并不是最终数据，而是访问最终数据的API
                },
                "getCheckGoToClassPromise": function(subscribeId) {
                    var requestURL = "http://" + (window.location + '')
                    .split('//')[1].split('/')[0] + "/ucenter/subscribeCourse/go2Vcube4Class";
                    var deferred = $q.defer(); // 声明延后执行，表示要去监控后面的执行
                    var sendData = {
                            "key_id": subscribeId
                    };
                    
                    $http({
                        method: "POST",
                        url: requestURL,
                        dataType: "json",
                        headers: {
//                            'Content-type': 'application/json;charset=UTF-8',
                            'Content-type': 'charset=UTF-8'
                        },
//                        data: JSON.stringify(sendData),
                        params: sendData,
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

        // 预约课程
        $scope.subscribeESClass = function(keyId) {
            ConfirmBox("body", "250px", null, "确认预约当前课程？", null, confirmSubSubmit, null);
            function confirmSubSubmit() {
                // sub es course
                
                promiseServiceList.getSubCoursePromise(keyId).then(function(data) {
//            console.log(data);
                    if (data.success) {
                        alert(data.msg);
                        window.location.href = "http://" + (window.location + '')
                        .split('//')[1].split('/')[0] + "/ucenter/index";
                    } else {
                        alert(data.msg);
                    }
                }).then(function(data) {
//            console.log("error",data);
                });
            };
        };

        // 取消预约
        $scope.unsubscribeESClass = function(subscribeId) {
            ConfirmBox("body", "250px", null, "确定取消预约吗?", null, confirmUnsubSubmit, null);
            function confirmUnsubSubmit() {
                // sub es course
                promiseServiceList.getUnsubCoursePromise(subscribeId).then(function(data) {
                    if (data.success) {
                        alert(data.msg);
                        window.location.href = "http://" + (window.location + '')
                        .split('//')[1].split('/')[0] + "/ucenter/index";
                    } else {
                        alert(data.msg);
                    }
                }).then(function(data) {
//            console.log("error",data);
                });
            };
        };

        // 兑换码用户进入教室入口
        $scope.goToESClass2 = function(link) {
            if ($(window).width() < 640) {
                alert("为了保障授课效果，请使用电脑登录，进入教室上课");
                return false;
            }

            window.open(link,"_blank");
        };
        // 进入教室
        $scope.goToESClass = function(subscribeId,keyId) {
            if ($(window).width() < 640) {
                alert("为了保障授课效果，请使用电脑登录，进入教室上课");
                return false;
            }

            // sub es course
            promiseServiceList.getCheckGoToClassPromise(subscribeId).then(function(data) {
//                console.log(data);
                if (data.success) {
                    OpenWindow(data.msg);
                } else {
                    ConfirmBox("body", "250px", null, "确认预约当前课程？", null, confirmSubmit, null);

                    function confirmSubmit() {
                        // sub es course

                        promiseServiceList.getSubCoursePromise(keyId).then(function(subData) {
                            if (subData.success) {
                                promiseServiceList.getCheckGoToClassPromise(subData.data).then(function(dataRes) {
                                    console.log(dataRes);
                                    if (dataRes.success) {
                                        OpenWindow(dataRes.msg);
                                        setTimeout(function() {
                                            window.location.href = "http://" + (window.location + '')
                                            .split('//')[1].split('/')[0] + "/ucenter/index";
                                        }, 500);
                                    }
                                },function(dataRes) {
                                    alert(dataRes.msg);
//                                    console.log("error",data);
                                });
                            } else {
                                alert(subData.msg);
                            }
                        }).then(function(subData) {
//                    console.log("error",subData);
                        });
                    };
                }
            }).then(function(data) {
//            console.log("error",data);
            });
        };
        $scope.esLimit = function() {
            if ($scope.isClassMore) {
                return null;
            } else {
                return 3;
            }
        };
        // 课时显示更多
        $scope.esclassMore = function() {
            $scope.isClassMore = true;
            //$scope.esLimit();
        };
        // 课时收起
        $scope.esclassFold = function() {
            $scope.isClassMore = false;
            //$scope.esLimit();
        };
        $scope.loadViewModel = function(data) {

            // 判断非会员中心页面是否有用户有资格查看ES栏目
            $scope.isTicketsUser = false;

            // getbasePath;
            if (basePath === undefined) {
                var basePath = "http://" + (window.location+'').split('//')[1].split('/')[0];
            }

            $http({
                method: "POST",
                url: basePath + "/ucenter/redeemCode/findRedeemCode",
                dataType: "json",
                //headers: {'Content-type':'application/json;charset=UTF-8'},
                params: {
                    activity_name: ACTIVITY_NAME
                }
            }).success(function(data, status) {
                if (data.success && data.data != null) {
                    var curTimestamp = new Date().getTime();
                    var redeemEndTimestamp = new Date();
                    redeemEndTimestamp.setFullYear(new Date(data.data.redeem_end_time).getFullYear());
                    redeemEndTimestamp.setMonth(new Date(data.data.redeem_end_time).getMonth());
                    redeemEndTimestamp.setDate(new Date(data.data.redeem_end_time).getDate());
                    redeemEndTimestamp.setHours(0);
                    redeemEndTimestamp.setMinutes(0);
                    redeemEndTimestamp.setSeconds(0);
                    redeemEndTimestamp.setMilliseconds(0);
                    var resultEndTimestamp = redeemEndTimestamp.getTime() + 24 * 3600 * 1000;
                    if (curTimestamp > resultEndTimestamp) {
                        $scope.isTicketsUser = false;
                    } else {
                        $scope.isTicketsUser = true;
                        $scope.esTicketStart = {
                                year: new Date(data.data.redeem_start_time).getFullYear(),
                                month: new Date(data.data.redeem_start_time).getMonth() + 1,
                                date: new Date(data.data.redeem_start_time).getDate()
                        };
                        $scope.esTicketEnd = {
                                year: new Date(data.data.redeem_end_time).getFullYear(),
                                month: new Date(data.data.redeem_end_time).getMonth() + 1,
                                date: new Date(data.data.redeem_end_time).getDate()
                        };
                    }
                } else if (!data.success) {
                    $scope.isTicketsUser = false;
                }
            }).error(function(data, status) {
                $scope.isTicketsUser = false;
            });
            // 获取符合条件的课程列表
            var dataModel = getViewModel(data,"eng_studio_start_time");

            $scope.viewModel = dataModel;
            if (dataModel.length <= 3) {
                $scope.isShowMore = false;
            } else {
                $scope.isShowMore = true;
            }
        };
        
        // get es list from server
        var getPromiseQuery = function () {
            var requestURL = "http://" + (window.location + '')
            .split('//')[1].split('/')[0] + "/ucenter/scheduling/findCourseEnglishStidoInfo";
            var deferred = $q.defer(); // 声明延后执行，表示要去监控后面的执行

            $http({
                    method: "POST",
                    url: requestURL,
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

        var getFormatModel = function(dataModel) {
            var formatModel = [];

            for (var i=0,len=dataModel.length;i<len;i++) {
                var tempObj = {};
                tempObj.eng_studio_name = dataModel[i].courseTitle;
                tempObj.eng_studio_description = dataModel[i].courseDesc;
                tempObj.eng_studio_duration = Number(dataModel[i].endTime - dataModel[i].startTime)/1000/60;
                tempObj.eng_studio_start_time = dataModel[i].startTime;
                tempObj.eng_studio_end_time = dataModel[i].endTime;
                tempObj.eng_studio_img = dataModel[i].coursePic;
                tempObj.eng_studio_tag = "English Studio";
                tempObj.teacher_name = dataModel[i].teacherName;
                tempObj.teacher_type = dataModel[i].teacherNationality == '中国' ? 0 : 1;
                tempObj.teacher_photo = dataModel[i].teacherPhoto;
                tempObj.eng_studio_status = 1,
                tempObj.eng_studio_link = dataModel[i].studentUrl;

                tempObj.isSubscribe = dataModel[i].subscribeId == null ? false : true;
                tempObj.subscribeId = dataModel[i].subscribeId;
                tempObj.keyId = dataModel[i].keyId;
                tempObj.courseId = dataModel[i].courseId;
                tempObj.canGoToClass = dataModel[i].canGoToClass;

                formatModel.push(tempObj);
            }

            return formatModel;
        };

        var esPromiseData = getPromiseQuery();
        esPromiseData.then(function(data) {
//            console.log(data);
            if (data.success) {
                var formatModel = getFormatModel(data.data);
                $scope.loadViewModel(formatModel);
            } else {
                alert(data.msg);
            }
        }, function(data) {
            alert(data.msg);
        });

        $scope.$watch("isTicketsUser", function() {});
    }]);
    angular.bootstrap($("#englishStudioList"), ["englishStudioList"]);
})();
