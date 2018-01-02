// teacher center
(function () {
    /*
     * teacher scheduling page
     * need timezoneDataSource js
     *
     **********************************************************/
    var TeacherScheduling = function (options) {
        this.defaults = {
            // default page rows
            pagerows: 20,
            // ban the hours from limitHourStart(unit:hour)
            limitHourStart: 0,
            // ban the hours to limitHourEnd(unit:hour)
            limitHourEnd: 6,
            // target time zone(utc offset)
            targetTimezone: 8, // Asia/Beijing|Asia/Shanghai
            // current time zone
            localTimezone: moment.tz.guess(),
            // minutes size(unit: minute)
            minutesSize: 5,
        };
        // testing data
        this.schedulingData = [{
            "startTime": "2016-04-29 16:00:00",
            "endTime": "2016-04-29 18:00:00"
        }, {
            "startTime": "2016-04-29 20:30:00",
            "endTime": "2016-04-29 22:00:00"
        }, {
            "startTime": moment()
                .format(),
            "endTime": moment()
                .add(1, 'h')
                .format()
        }];
        this.init(options);
    };
    TeacherScheduling.prototype = {
        constructor: TeacherScheduling,
        testing: function () {
            var tt = this.schedulingData[0].startTime;

            console.log(moment(tt)
                .utc()
                .utcOffset(8)
                .format()); // 转化为指定时区
        },
        init: function (options) {
            var params = this.extend(options, this.defaults);

            this.initPickadate(0);
            this.initTimeZone(params);
            this.initHourMinute(params, -moment.tz.zone(params.localTimezone)
                        .offset(new Date())/60);
//            this.testing();
            this.running(params);
        },
        running: function (params) {
            // angular module
            var _this = this;
            var schedulingList = angular.module('schedulingList', []);
            schedulingList.controller("schedulingListCtrl", function ($scope, $http) {
                // getbasePath;
                if (basePath === undefined) {
                    var basePath = "http://" + (window.location + '')
                        .split('//')[1].split('/')[0];
                }

                // 1. Loading Scheduling Data
                $scope.runningViewModel = function (dataModel, timezone) {
                    // 2. transferToSelTimezone (params.localTimezone)
                    var localTimezone = -moment.tz.zone(params.localTimezone)
                        .offset(new Date());
                    // timezone we need finally
                    $scope.targetTimezone = params.targetTimezone;
                    // local timezone what is not changed
                    $scope.localTimezone = localTimezone / 60;
                    // timezone user had selected
                    //console.log($scope.selTimezone);
                    var objModel = _this.transferToTargetTimezone(dataModel);

                    // transferToSelTimezone (8)
                    if ($scope.selTimezone !== undefined) {
                        var viewModel = _this.getTargetModel(objModel, timezone);
                    } else {
                        $scope.selTimezone = localTimezone / 60;
                        var viewModel = _this.getTargetModel(objModel, localTimezone / 60);
                    }

                    $scope.viewModel = viewModel;

                    // add data event
                    $scope.addItem = function () {
                        if (!_this.getDate()) {
                            return false;
                        }
                        
                        var tempObj = _this.getDate()[0];
                        
                        layer.confirm('Are you sure to add?', {
                            btn: ['Yes','No'] //按钮
                        }, function(){
                            layer.closeAll();

                            $http({
                                method: "POST",
                                url: basePath + "/tcenter/timeSign/insertTeacherTimeSign",
                                dataType: "json",
                                headers: {
                                    'Content-type': 'application/json;charset=UTF-8'
                                },
                                data: JSON.stringify(tempObj)
                                
                            })
                            .success(function (data, status) {
                                // success handle code
                                //console.log(data);
                                if (data.success) {
                                    $scope.getTeacherSignList(1);
                                    $("#selDate").pickadate().pickadate('picker').clear();
                                    _this.initHourMinute(params, $scope.selTimezone);
                                }
                                alert(data.msg);
                            })
                            .error(function (data, status) {
                                // error handle code
                                alert(data.msg);
                            });
                        }, function(){
                            //layer.msg('No', {time: 2000});
                        })

                    };

                    // minus data event
                    $scope.delItem = function () {
                        if (!_this.getDate()) {
                            return false;
                        }
                        
                        var tempObj = _this.getDate()[0];
                        
                        layer.confirm('Are you sure to delete?', {
                            btn: ['Yes','No'] //按钮
                        }, function(){
                            layer.closeAll();

                            $http({
                                method: "POST",
                                url: basePath + "/tcenter/timeSign/deleteTeacherTimeSign",
                                dataType: "json",
                                headers: {
                                    'Content-type': 'application/json;charset=UTF-8'
                                },
                                data: JSON.stringify(tempObj)
                                
                            })
                            .success(function (data, status) {
                                // success handle code
                                // console.log(data);
                                if (data.success) {
                                    $scope.getTeacherSignList(1);
                                    $("#selDate").pickadate().pickadate('picker').clear();
                                    _this.initHourMinute(params, $scope.selTimezone);
                                }
                                alert(data.msg);
                            })
                            .error(function (data, status) {
                                // error handle code
                                alert(data.msg);
                            });
                        }, function() {
                          //layer.msg('No', {time: 2000});
                        })

                    };
                };

                $scope.getTeacherSignList = function (page) {
                    // console.log(page,rows);
                    var tempObj = {
                        "page": page.toString(),
                        "rows": params.pagerows.toString()
                    };
                    // get server data
                    $http({
                            method: "POST",
                            url: basePath + "/tcenter/timeSign/findTeacherTimeSignInfo",
                            dataType: "json",
                            headers: {
                                'Content-type': 'application/json;charset=UTF-8'
                            },
                            data: JSON.stringify(tempObj)
                        })
                        .success(function (data, status) {
                        	var timeList = data.data.timeList;
                        	for (var i=0,len=timeList.length;i<len;i++) {
                        		timeList[i].startTime = moment(timeList[i].startTime).utc().utcOffset(8).format('YYYY-MM-DD HH:mm:ss');
                        		timeList[i].endTime = moment(timeList[i].endTime).utc().utcOffset(8).format('YYYY-MM-DD HH:mm:ss');
                        	}
                        
                            // success handle code
                            if (data.success) {
                                //console.log(data.data.totalPage);
                                //console.log(data.data.timeList);
                                _this.initPage(Number(page),Number(data.data.totalPage));
                                $scope.runningViewModel(data.data.timeList, $scope.selTimezone);
                                //alert(data.msg);
                            } else {
                                alert(data.msg);
                            }
                            // alert(data);
                        })
                        .error(function (data, status) {
                            // error handle code
                            alert(data.msg);
                        });
                };
                $scope.getTeacherSignList(1);
                //$scope.runningViewModel(_this.schedulingData);
            });
            // 3. show the data list
            angular.bootstrap($("#schedulingList"), ["schedulingList"]);
        },
        // timezone(unit: timezone_name or offset)
        getTargetModel: function (targetModel, timezone) {
            var viewModel = [];
//            var isChinaFormat = isFormat ? isFormat : false;

            for (var i = 0, len = targetModel.length; i < len; i++) {
                var tempObj = {};
                var startOrigin = targetModel[i]["startTimeOrigin"] ? targetModel[i].startTimeOrigin : targetModel[i].startTime;
                var endOrigin = targetModel[i]["endTimeOrigin"] ? targetModel[i].endTimeOrigin : targetModel[i].endTime;

                tempObj.startTime = this.transferToSelTimezone(startOrigin, timezone, true);
                tempObj.endTime = this.transferToSelTimezone(endOrigin, timezone, true);
                tempObj.startTimeOrigin = this.transferToSelTimezone(startOrigin, timezone, false);
                tempObj.endTimeOrigin = this.transferToSelTimezone(endOrigin, timezone, false);

                viewModel.push(tempObj);
            }
            return viewModel;
        },
        // timezone(unit: timezone_name or offset)
        transferToSelTimezone: function (dateObj, timezone, isFormat) {
            // 'YYYY-MM-DD HH:mm:ss'
            // console.log(moment(tt)
            //     .utc()
            //     .utcOffset(8)
            //     .format());
            if (isFormat) {
                return moment(dateObj)
                    .utc()
                    .utcOffset(timezone)
                    .format('YYYY-MM-DD HH:mm:ss');
            } else {
                return moment(dateObj)
                    .utc()
                    .utcOffset(timezone)
                    .format();
            }
        },
        // transfer to params.targetTimezone (8)
        transferToTargetTimezone: function (targetModel) {
            var viewModel = [];
            for (var i = 0, len = targetModel.length; i < len; i++) {
                var tempObj = {};
                tempObj.startTime = targetModel[i].startTime.split(" ")
                    .join("T") + "+08:00";
                tempObj.endTime = targetModel[i].endTime.split(" ")
                    .join("T") + "+08:00";
                viewModel.push(tempObj);
            }
            return viewModel;
        },
        /**
         *
         */
        initPage: function (curr, pages) {
            var _this = this;
            //console.log(curr, pages);
            laypage({
                cont: "tsPaginator", //*
                pages: pages, //*
                curr: curr,
                groups: 5,
                first: "First",
                last: "Last",
                prev: "Prev",
                next: "Next",
                skin: "molv",
                jump: function (obj, first) {
                    //console.log('pageobj:', obj);
                    //console.log('isfirst:', first);
                    // update page data
                    if (first === undefined) {
                        var $scope = angular.element($("#schedulingList"))
                            .scope();
                        $scope.getTeacherSignList(obj.curr);
                        $scope.$apply(function () {});
                    }
                }
            });
        },
        /*
         * date init
         *
         **************************/
        initPickadate: function (diff) {
            var dayDiff = diff === 0 ? true : diff;
            //dayDiff = -1;
            var selDate = $("#selDate")
                .pickadate({
                    min: dayDiff,
                    max: 30,
                    selectYears: true,
                    selectMonths: true,
                    format: 'yyyy-mm-dd',
                    formatSubmit: 'yyyy-mm-dd',

                    onOpen: function () {
//                        this.set('min', dayDiff);
                    },
//                    onClose: function() {
//                        console.log('Closed now');
//                    },
//                    onRender: function () {
//                      console.log('Just rendered anew');
//                      console.log(this.get('select','yyyy-mm-dd'));
//                    },
//                     onStart: function () {
//                         console.log('Hello there :)');
//                     },
//                     onStop: function () {
//                         console.log('See ya');
//                     },
//                     onSet: function (thingSet) {
//                         console.log('Set stuff:', thingSet);
//                     }
                });
            $("#selDate")
            .pickadate()
            .pickadate('picker')
            .set('min', dayDiff);
            return selDate;
        },
        /*
         * select2 init hour,minute
         *
         **************************/
        initHourMinute: function (params, selTimezone) {
            var _this = this;
            var selOption = [];
            var $startMinuteData = [];
            var $startHourData = [];
            var $endMinuteData = [];
            var $endHourData = [];

            var standardHourData = [];
            var standardMinuteData = [];

            // normal hour data
            for (var i = 0; i <= 24; i++) {
                standardHourData.push(i);
            }
            // normal hour data
            for (var j = 0; j <= 60; j++) {
                if (j % params.minutesSize === 0) {
                    standardMinuteData.push(j);
                }
            }

            // get hour diff between cur offset and target offset
            var hourDiff = Math.abs(params.targetTimezone - selTimezone);
            var limitHourStart = params.limitHourStart;
            var limitHourEnd = params.limitHourEnd;

            // add '0' prefix
            var addPrefix = function (obj) {
                if (obj.toString()
                    .length === 1) {
                    return "0" + obj.toString();
                } else {
                    return obj;
                }
            };

            // filter invalid start hour
            var filterInvalidStartHour = function (obj) {
                var a = limitHourStart - hourDiff;
                var b = limitHourEnd - hourDiff;

                a2 = a + 24;
                b2 = b + 24;

                if (a == 0) {
                    selOption[0] = b + ",24";
                    return obj >= b;
                } else if (b == 0) {
                    selOption[0] = "0," + a2;
                    return obj <= a2;
                } else if (a2 < 24 && b2 > 24) {
                    selOption[0] = b + "," + a2;
                    return obj <= a2 && obj >= b;
                } else if (a2 < 24 && b2 < 24) {
                    selOption[0] = "0," + a2;
                    selOption[1] = b2 + ",24";
                    return obj <= a2 || obj >= b2;
                }
            };

            // filter invalid start minute
            var filterInvalidStartMinute = function (obj) {

            };

            // filter invalid end hour
            var filterInvalidEndHour = function (obj) {
                var sh = Number($selStartHour.val());
                if (selOption.length === 1) {
                    return obj >= sh;
                } else if (selOption.length > 1) {
                    var rangeBegin1 = Number(selOption[0].toString().split(",")[0]);
                    var rangeEnd1 = Number(selOption[0].toString().split(",")[1]);
                    var rangeBegin2 = Number(selOption[1].toString().split(",")[0]);
                    var rangeEnd2 = Number(selOption[1].toString().split(",")[1]);

                    if (sh >= rangeBegin1 && sh <= rangeEnd1) {
                        return obj >= sh && obj <= rangeEnd1;
                    } else if (sh >= rangeBegin2 && sh <= rangeEnd2) {
                        return obj >= sh && obj <= rangeEnd2;
                    }
                }
            };

            // filter invalid end minute
            var filterInvalidEndMinute = function (obj) {
                var sm = $selStartMinute.val();
                return obj >= Number(sm);
            };

            $startHourData = standardHourData.filter(filterInvalidStartHour);
            $startMinuteData = standardMinuteData;
            
            var initSelStartHour = function() {
                $startHourData = standardHourData.filter(filterInvalidStartHour);
                $startHourData.splice(0,0,'');
                var sel = $("#selStartHour")
                .empty()
                .select2({
                    placeholder: "Start Hour",
                    data: $startHourData.map(addPrefix),
                });
                return sel;
            };
            var initSelStartMinute = function() {
                $startMinuteData = standardMinuteData;
                $startMinuteData.splice(0,0,'');
                var sel = $("#selStartMinute")
                .empty()
                .select2({
                    placeholder: "Start Minute",
                    data: $startMinuteData.map(addPrefix),
                });
                return sel;
            };

            var $selStartHour = initSelStartHour();
            var $selStartMinute = initSelStartMinute();

            $endHourData = $startHourData.filter(filterInvalidEndHour);
            $endMinuteData = standardMinuteData;

//            $startHourData.splice(0,0,'');
//            $endHourData.splice(0,0,'');
//            $startMinuteData.splice(0,0,'');

            var initSelEndHour = function() {
                $endHourData = $startHourData.filter(filterInvalidEndHour);
                $endHourData.splice(0,0,'');
                var sel = $("#selEndHour")
                .empty()
                .select2({
                    placeholder: "End Hour",
                    data: $endHourData.map(addPrefix),
                });
                return sel;
            };
            var initSelEndMinute = function() {
                $endMinuteData = standardMinuteData;
                $endMinuteData.splice(0,0,'');
                var sel = $("#selEndMinute")
                .empty()
                .select2({
                    placeholder: "End Minute",
                    data: $endMinuteData.map(addPrefix),
                });
                return sel;
            };
            var $selEndHour = initSelEndHour();
            var $selEndMinute = initSelEndMinute();

            var cleartozero = function(sh) {
                $("#selStartMinute")
                .empty()
                .select2({
                    placeholder: "Start Minute",
                    data: ['00'],
                });
                $("#selEndHour")
                .empty()
                .select2({
                    placeholder: "End Hour",
                    data: [sh],
                });
                $("#selEndMinute")
                .empty()
                .select2({
                    placeholder: "End Minute",
                    data: ['00'],
                });
            };

            $selStartHour.on("select2:select", function (e) {
                var sh = Number($selStartHour.val());
                if (selOption.length === 1) {
                    var rangeBegin1 = Number(selOption[0].toString().split(",")[0]);
                    var rangeEnd1 = Number(selOption[0].toString().split(",")[1]);
                    if (sh == rangeEnd1) {
                        cleartozero(sh);
                    } else {
                        initSelStartMinute();
                        initSelEndHour();
                        initSelEndMinute();
                    }
                } else if (selOption.length > 1) {
                    var rangeBegin1 = Number(selOption[0].toString().split(",")[0]);
                    var rangeEnd1 = Number(selOption[0].toString().split(",")[1]);
                    var rangeBegin2 = Number(selOption[1].toString().split(",")[0]);
                    var rangeEnd2 = Number(selOption[1].toString().split(",")[1]);

                    if (sh == rangeEnd1 || sh == rangeEnd2) {
                        cleartozero(sh);
                    } else {
                        initSelStartMinute();
                        initSelEndHour();
                        initSelEndMinute();
                    }
                }
            });
            $selStartMinute.on("select2:select", function (e) {
                var sm = Number($selStartMinute.val());
                var sh = Number($selStartHour.val());
                var eh = Number($selEndHour.val());
                $endMinuteData = standardMinuteData;
                $endMinuteData = $endMinuteData.filter(function(obj) {
                    return obj >= sm;
                });
                if (sh == eh) {
                    $("#selEndMinute")
                    .empty()
                    .select2({
                        placeholder: "End Minute",
                        data: $endMinuteData.map(addPrefix)
                    });
                }
            });
            $selEndHour.on("select2:select", function (e) {
                var sm = Number($selStartMinute.val());
                var sh = Number($selStartHour.val());
                var eh = Number($selEndHour.val());
                if (selOption.length === 1) {
                    var rangeBegin1 = Number(selOption[0].toString().split(",")[0]);
                    var rangeEnd1 = Number(selOption[0].toString().split(",")[1]);

                    if (eh == rangeEnd1) {
                        $("#selEndMinute")
                        .empty()
                        .select2({
                            placeholder: "End Minute",
                            data: ['00']
                        });
                    } else if (eh == sh) {
                        $endMinuteData = standardMinuteData;
                        $endMinuteData = $endMinuteData.filter(function(obj) {
                            return obj >= sm;
                        });
                        $("#selEndMinute")
                        .empty()
                        .select2({
                            placeholder: "End Minute",
                            data: $endMinuteData.map(addPrefix)
                        });
                    } else {
                        $endMinuteData = standardMinuteData;
                        $("#selEndMinute")
                        .empty()
                        .select2({
                            placeholder: "End Minute",
                            data: $endMinuteData.map(addPrefix)
                        });
                    }
                } else if (selOption.length > 1) {
                    var rangeBegin1 = Number(selOption[0].toString().split(",")[0]);
                    var rangeEnd1 = Number(selOption[0].toString().split(",")[1]);
                    var rangeBegin2 = Number(selOption[1].toString().split(",")[0]);
                    var rangeEnd2 = Number(selOption[1].toString().split(",")[1]);

                    if (eh == rangeEnd1 || eh == rangeEnd2) {
                        $("#selEndMinute")
                        .empty()
                        .select2({
                            placeholder: "End Minute",
                            data: ['00']
                        });
                    } else if (eh == sh) {
                        $endMinuteData = standardMinuteData;
                        $endMinuteData = $endMinuteData.filter(function(obj) {
                            return obj >= sm;
                        });
                        $("#selEndMinute")
                        .empty()
                        .select2({
                            placeholder: "End Minute",
                            data: $endMinuteData.map(addPrefix)
                        });
                    } else {
                        $endMinuteData = standardMinuteData;
                        $("#selEndMinute")
                        .empty()
                        .select2({
                            placeholder: "End Minute",
                            data: $endMinuteData.map(addPrefix)
                        });
                    }
                }
            });
        },
        /*
         * select2 init timezone
         *
         **************************/
        initTimeZone: function (params) {
            var _this = this;

            // var los = "America/Los_Angeles";
            // var temp = moment.tz(new Date(), los);
            // console.log(temp.unix());
            // console.log(moment.tz(new Date(), los).format());
            // console.log(moment.tz.guess());

            var $timezoneData = this.JoinIdList(timezoneJsonSource);
            var $selTimeZone = null;


            var filterTimeZone = function (array, target) {
                var result = [];
                $.grep(array, function (obj, index) {
                    if (obj.text.toString()
                        .toUpperCase()
                        .indexOf(target.toUpperCase()) !== -1) {
                        result.push(obj.id);
                    } else if (obj.utc !== undefined && obj.utc.length > 0) {
                        for (var i = 0, utclist = obj.utc; i < utclist.length; i++) {
                            if (utclist[i].toString()
                                .toUpperCase()
                                .indexOf(target.toUpperCase()) !== -1) {
                                result.push(obj.id);
                            }
                        }
                    }
                });
                return result;
            };
            $timezoneData = $.map($timezoneData, function (obj) {
                obj.text = obj.text || obj.utc; // replace name with the property used for the text
                return obj;
            });
            $selTimeZone = $("#selTimeZone")
                .select2({
                    placeholder: "Time Zone",
                    data: $timezoneData,
                });
            $selTimeZone.val(filterTimeZone($timezoneData, params.localTimezone))
                .trigger("change");

            /* events
            /*****************************************/
            // selTimeZone change event
            $selTimeZone.on("select2:select", function (e) {
                var selOffset = e.params.data.offset;
                var utcNameList = e.params.data.utc;
                var selDate = moment(moment())
                    .utcOffset(selOffset)
                    .get('date');
                var localDate = moment()
                    .get('date');
                _this.initPickadate(selDate - localDate);

                var $scope = angular.element($("#schedulingList"))
                .scope();
                //console.log('此处存有东八区时间', $scope.viewModel);
                var targetSchedulingData = _this.getTargetModel($scope.viewModel, selOffset);
                $scope.utcNameList = utcNameList;
                $scope.selTimezone = selOffset;
                $scope.viewModel = targetSchedulingData;
                $scope.$apply(function () {});

                _this.initHourMinute(params, selOffset);
            });
        },
        /**
         *
         */
        getDate: function () {
            var res = [];
            var obj = {};
            var date = $("#selDate")
                .pickadate()
                .pickadate('picker')
                .get('select', 'yyyy-mm-dd');
            var startHour = $("#selStartHour")
                .val();
            var startMinute = $("#selStartMinute")
                .val();
            var endHour = $("#selEndHour")
                .val();
            var endMinute = $("#selEndMinute")
                .val();
            if (date === '' || startHour === '' || startMinute === '' || endHour === '' || endMinute === '' ||
                date === null || startHour === null || startMinute === null || endHour === null || endMinute === null) {
                layer.msg('Please select the correct date.', {
                    time: 2000
                });
                return false;
            }

            var $scope = angular.element($("#schedulingList"))
                .scope();
            var selTimezone = $scope.selTimezone;
            var utcNameList = $scope.utcNameList;

            var startTime = date + ' ' + startHour + ':' + startMinute + ":00";
            var endTime = date + ' ' + endHour + ':' + endMinute + ":00";
            
            var checkSixtyMinute = function(obj) {
                var timestamp = new Date(obj).getTime();
                var oneHour = 1 * 3600 * 1000;
                var newtime = new Date(timestamp+oneHour);
                var year = newtime.getFullYear();
                var month = Number(newtime.getMonth()+1);
                var daydate = Number(newtime.getDate());
                var hour = newtime.getHours();
                var minute = newtime.getMinutes();
                var second = newtime.getSeconds();
                
                var refineObj = function(obj) {
                    if (obj.toString().length === 1) {
                        return "0"+obj;
                    } else {
                        return obj;
                    }
                };
                var res = year+"-"+refineObj(month)+"-"+refineObj(daydate)+" "+refineObj(hour)+":"+refineObj(minute)+":"+refineObj(second);
                return res;
            };
            
            if (startMinute == 60 || startMinute == '60') {
                var stemp = date + ' ' + startHour + ':00:00';
                startTime = checkSixtyMinute(stemp);
            }
            if (endMinute == 60 || endMinute == '60') {
                var etemp = date + ' ' + endHour + ':00:00';
                endTime = checkSixtyMinute(etemp);
            }

            if (utcNameList) {
                for (var i = 0, len = utcNameList.length; i < len; i++) {
                    if (moment.tz(startTime, utcNameList[i])
                        .format()) {
                        startTime = moment.tz(startTime, utcNameList[i])
                            .format();
                        endTime = moment.tz(endTime, utcNameList[i])
                            .format();
                        break;
                    }
                }
            }
            // transfer to selected timezone;
            obj.startTime = this.transferToSelTimezone(startTime, selTimezone);
            obj.endTime = this.transferToSelTimezone(endTime, selTimezone);
            res.push(obj);

            // transfer to timezone we need (8)
            res = this.getTargetModel(res, $scope.targetTimezone);
            // console.log(res[0]);

            return res;
        },
        /**
         * JoinIdList
         * ---------------------------------------------------------
         * 在list中添加id
         * ---------------------------------------------------------
         *
         * @param a{Array} json合并最终的对象
         * @returns {Array} 返回合并后的json list
         *
         * @date 2015-12-11
         * @author phil.chr
         */
        "JoinIdList": function (a) {
            for (var i = 0; i < a.length; i++) {
                a[i].id = (i + 1)
                    .toString();
            }
            return a;
        },
        extend: function (options, defaults) {
            for (var name in options) {
                defaults[name] = options[name];
            }
            return defaults;
        },
    };
    var myScheduling = new TeacherScheduling();

})();
