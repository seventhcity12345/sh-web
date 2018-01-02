// OC
(function() {

    // oc data testing
    // @param {eng_studio_status} 3:倒计时状态;2:可进入教室;1:不可进入教室;0:数据已过期
    var ocInfoTestingList = {
        "dataConfig" : { //单位分钟
            "timeStartingCountdown" : 12 * 60,//倒计时开始
            "timeOpeningClass" : 5,//上课前可提前进入教室的时间
            //"timeClosingClass" : 50//上课后可延续进入教室的时间
        },
        "ocInfoList" : [
        {
            "courseTitle" : "Selfie Sticks 自拍杆",
            "teacherName" : "Steve",
            "teacherPhoto" : "skin/images/shteacher_Steve.jpg",
            "startTime" : 1462509020000,
            "endTime" : 1462509920000,
            "courseDesc" : "自拍杆是多人拍照必备良品！你知道吗？自拍杆的起源很早，早在手机都还没有摄像头之前，自拍杆就已经存在了！",
            "coursePic" : "skin/images/engstudio/Selfie_Sticks.jpg",
            "studentUrl" : "http://live-seminar.vcubeone.net/live/ap/78518e3b7e02074fe0d7ff69f251e427a35c9d3d",
            "eng_studio_status" : 1,
        }, {
            "courseTitle" : "发音课：Liaison 连读",
            "teacherName" : "Matt",
            "teacherPhoto" : "skin/images/shteacher_Matt.png",
            "startTime" : 1462358760000,
            "endTime" : 1462358770000,
            "courseDesc" : "",
            "coursePic" : "skin/images/engstudio/Liaison.jpg",
            "studentUrl" : "http://live-seminar.vcubeone.net/live/ap/98f84e40768ac482741b4ec387446612bf924baf",
            "eng_studio_status" : 1,
        },
    ]};

    var getGMTTime = function(systemTime) {// 单位时间戳毫秒级
        var tempDate = new Date();
        var temp = 6*60*60*1000;// 美国中部时间与gmt时间差,单位毫秒
        return new Date(systemTime).getTime() - temp + tempDate.getTimezoneOffset()*60*1000;
    };

    var OCList = angular.module('OCList', []);
    OCList.controller("OCListCtrl", function($scope, $http) {

        var getServerTime = function() {
            var serverTime = angular.element("#engStudioServerDate").val();// java cst time
            return getGMTTime(serverTime);
        };

        var openTime = ocInfoTestingList.dataConfig.timeOpeningClass*60*1000;
        var closeTime = ocInfoTestingList.dataConfig.timeClosingClass*60*1000;

        // console.log(getServerTime());
        var systemTime = getServerTime() || Date.parse(new Date().replace(/-/g, "/"));

        $scope.timeOpeningClass = ocInfoTestingList.dataConfig.timeOpeningClass;
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

        var runningCountdown = function(obj) {
            var dateDiff = obj.startTime - systemTime;

            var startingCountdownMin = ocInfoTestingList.dataConfig.timeStartingCountdown;
            var startingOpeningMin = ocInfoTestingList.dataConfig.timeOpeningClass;
            
            var changeStatus = function() {
                if (dateDiff >= 0) {
                    if (dateDiff >= startingCountdownMin * 60 * 1000) {
                        $scope.$apply(function() {
                            obj.eng_studio_status = 1;
                        });
                    } else if (dateDiff < startingCountdownMin * 60 * 1000 && dateDiff >= startingOpeningMin * 60 * 1000) { // 大于开始倒计时时间
                        $scope.$apply(function() {
                            obj.eng_studio_status = 3;
                        });
                    } else if (dateDiff < startingOpeningMin * 60 * 1000) {
                        $scope.$apply(function() {
                            obj.eng_studio_status = 2;
                        });
                    }
                } else if (dateDiff < 0) {
                    if (-dateDiff >= (obj.endTime-obj.startTime)) {
                        clearInterval(obj.timer);
                        $scope.$apply(function() {
                            obj.eng_studio_status = 0;
                        });
                    } else {
                        $scope.$apply(function() {
                            obj.eng_studio_status = 2;
                        });
                    }
                }
            };

            setTimeout(function(){
                $scope.$apply(function() {
                    obj.latestCountdown = getLatestCountdown(dateDiff);
                });
                changeStatus();
            },0);
            obj.timer = setInterval(function() {
                $scope.$apply(function() {
                    obj.latestCountdown = getLatestCountdown(dateDiff);
                });
                changeStatus();
                dateDiff -= 1000;
            }, 1000);
            return obj;
        };

        var getViewModel = function(dataModel) {
            var list = [];
            for (var item in dataModel) {
                dataModel[item].timer = null;
                list.push(runningCountdown(dataModel[item]));
            }
            //console.log(list);
            return list;
        };

        // 进入教室
        $scope.goToOCClass = function(link) {
            if ($(window).width() < 640) {
                alert("为了保障授课效果，请使用电脑登录，进入教室上课");
                return false;
            }
            window.open(link,"_blank");
        };

        $scope.loadViewModel = function(data) {
            // getbasePath;
            if (basePath === undefined) {
                var basePath = "http://" + (window.location+'').split('//')[1].split('/')[0];
            }

            // 获取符合条件的课程列表
            var dataModel = data.ocInfoList;

            $scope.viewModel = getViewModel(dataModel);
        };

        $scope.canShowOCClass = false;
        $http({
            method: "POST",
            url: basePath + "/findSessionUser",
            dataType: "json",
            headers: {'Content-type':'application/json;charset=UTF-8'},
        }).success(function(data, status) {
             if (!!data.data.courseTypes.course_type5) {
                 $scope.canShowOCClass = true;
                 $http({
                     method: "POST",
                     url: basePath + "/ucenter/scheduling/findCourseOcInfo",
                     dataType: "json",
                     headers: {'Content-type':'application/json;charset=UTF-8'},
                 }).success(function(data, status) {
                     if (status == 200 && data.success) {
//                         console.log(data.data);
//                         console.log(data.data.ocInfoList);
//                         console.log(data.data.isTop);
                         $scope.isTop = data.data.isTop && $scope.canShowOCClass;
//                         console.log("data.isTop",data.data.isTop);
//                         console.log("canShow",$scope.canShowOCClass);
//                         console.log("isTop",$scope.isTop);
//                         console.log("*************************");
                         $scope.loadViewModel(data.data);
                     } else {
                         alert(data.msg);
                     }
                 }).error(function(data, status) {
                     alert(data.msg);
                 });
             } else {
                 $scope.isTop = false;
             }
        }).error(function(data, status) {
            alert(data.msg);
        });
    });
    angular.bootstrap($("#OCList"), ["OCList"]);
    angular.bootstrap($("#OCList2"), ["OCList"]);

})();
