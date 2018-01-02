/* document ready module
/*
/***********************************************************/
$(function() {
    $("#comments").find(".t-to-me").css("position", "relative").css(
        "left",
        "0");
});

/* comments of course module
/*
/***********************************************************/
function teacherComment(lesson_id, teacher_id, user_id, courseType) {
    cur = this;
    $("#comments").undelegate(".popup-confirm", "click", confirmSubmit);
    //alert(lesson_id+","+teacher_id+","+user_id);

    $(".popup-wrap").css("width", "700px");
    PopupComments(true);
	
	
	
	 //modify by seven 2016年12月26日19:21:29加入demo评论,教室评论可反复修改
	 //modify by seven 2016年12月26日19:21:29加入demo评论,教室评论可反复修改
    if(courseType == 'course_type4'){
	    $("#commentsScoreArea").hide();
		$("#comments #total-rating b").html('5');
		$("#comments .crr-wrap ul li:eq(0)").find("span:eq(1)").addClass('rating5');
		$("#comments .crr-wrap ul li:eq(0)").find("span:eq(0)").html('5.0score');
		$("#comments .crr-wrap ul li:eq(1)").find("span:eq(1)").addClass('rating5');
		$("#comments .crr-wrap ul li:eq(1)").find("span:eq(0)").html('5.0score');
		$("#comments .crr-wrap ul li:eq(2)").find("span:eq(1)").addClass('rating5');
		$("#comments .crr-wrap ul li:eq(2)").find("span:eq(0)").html('5.0score');
		$("#comments .crr-wrap ul li:eq(3)").find("span:eq(1)").addClass('rating5');
		$("#comments .crr-wrap ul li:eq(3)").find("span:eq(0)").html('5.0score');
    }
	 
    $.ajax({
		type : "POST",
		dataType:'json', 
		timeout : 8000, 
		url : basePath +  "/tcenter/teacherCourseComment/queryTeacherComment",
		data:{
			from_user_id : teacher_id,
			subscribe_course_id : lesson_id,
			to_user_id : user_id
		},
		beforeSend:BeginLoading,
		error:function(jqXHR, textStatus, errorThrown){ 
			EndLoading(); 
			if(textStatus=="timeout"){ 
				alert("请求超时，请重新查看！"); 
			}else{ 
		        alert('系统出现异常,请联系管理员!'); 
		        alert(textStatus); 
	    	}
	    }, 
		success : function(result) {//提交成功
			EndLoading(); 
			 if(result.success){
		            //alert(result.msg);//后台需要使用 new JsonMessage()对象返回 
		            //alert(data.update_date);
		           if(result.data != null){
		            	if(courseType != 'course_type4'){
		            		$("#comments #total-rating b").html(result.data.show_score);
		            		$("#comments .crr-wrap ul li:eq(0)").find("span:eq(1)").addClass('rating'+result.data.pronouncation_score);
		            		$("#comments .crr-wrap ul li:eq(0)").find("span:eq(0)").html(result.data.pronouncation_score+'.0score');
		            		$("#comments .crr-wrap ul li:eq(1)").find("span:eq(1)").addClass('rating'+parseInt(result.data.grammer_score));
		            		$("#comments .crr-wrap ul li:eq(1)").find("span:eq(0)").html(result.data.grammer_score+'.0score');
		            		$("#comments .crr-wrap ul li:eq(2)").find("span:eq(1)").addClass('rating'+parseInt(result.data.vocabulary_score));
		            		$("#comments .crr-wrap ul li:eq(2)").find("span:eq(0)").html(result.data.vocabulary_score+'.0score');
		            		$("#comments .crr-wrap ul li:eq(3)").find("span:eq(1)").addClass('rating'+parseInt(result.data.listening_score));
		            		$("#comments .crr-wrap ul li:eq(3)").find("span:eq(0)").html(result.data.listening_score+'.0score');
		            		$("#commentsTextarea").html(result.data.comment_content);
		            	} else {
		            		$("#commentsTextarea").html(result.data.comment_content);
		            	}
		            }
		     }
		},
		complete : function(data) {
			
		},
	});
	
	

    var isPass = true;
    var confirmSubmit = function() {
//        if (!$("#comments .rating-board li").hasClass("red")) {
//            isPass = true;
//        }
        // validation
//        $("#comments .rating-board li").each(function() {
//            if ($(this).find("span:first").html() == "0.0分") {
//                $(this).find("label").addClass("red");
//                isPass = false;
//            };
//            if ($(this).parents(".crr-wrap").next().val().length <= 0) {
//                isPass = false;
//            };
//        });
//        if (isPass) {
            var subscribe_course_id = lesson_id;
            var from_user_id = teacher_id;
            var to_user_id = user_id;
            var create_user_id = user_id;
            var update_user_id = user_id;
            var pronouncation_score = parseInt($("#comments .crr-wrap ul li:eq(0)").find(
            "span:first").html());
            var grammer_score = parseInt($("#comments .crr-wrap ul li:eq(1)").find(
            "span:first").html());
            var vocabulary_score = parseInt($("#comments .crr-wrap ul li:eq(2)").find(
            "span:first").html());
            var listening_score = parseInt($("#comments .crr-wrap ul li:eq(3)").find(
            "span:first").html());
            var comment_content = $("#comments textarea").val();
            var show_score = getTotalRating();
            $.ajax({
                type: "POST",
                dataType: 'json',
                timeout: 8000,
                url: basePath + "/tcenter/teacherCourseCenter/insertComment",
                data: {
                    subscribe_course_id: subscribe_course_id,
                    from_user_id: from_user_id,
                    to_user_id: to_user_id,
                    pronouncation_score: pronouncation_score,
                    vocabulary_score: vocabulary_score,
                    grammer_score: grammer_score,
                    listening_score: listening_score,
                    show_score: show_score,
                    comment_content: comment_content,
                    create_user_id: create_user_id,
                    update_user_id: update_user_id
                },
                beforeSend: BeginLoading,
                error: function(jqXHR, textStatus, errorThrown) {
                    EndLoading();
                    if (textStatus == "timeout") {
                        alert("Timeout, please refresh pages again~");
                    } else {
                        alert('System error, please ontact the server administrator!');
                        alert(textStatus);
                    }
                },
                success: function(result) { //提交成功
                    EndLoading();
                    if (result.success) {
                        //						$(cur).removeClass("yellow-bg").addClass("gray-bg");
                        //						$(cur).css("cursor","default");
                        //						$(cur).click(function(){return false;});
                        //						$(cur).removeClass("active");
                        
                        ClosePopup();
                        location.href = basePath +
                        "/tcenter/teacherCourseCenter/subscribeCourseCenter";
                    } else {
                        alert(result.msg);
                    }
                }
            });
//        } else {
//            alert("Please give ratings~");
//            isPass = true;
//            return;
//        }
    };
    $("#comments").delegate(".popup-confirm", "click", confirmSubmit);
}

function PopupComments(canMove) {
	ShowTMask();
	var wrapWidth = $("#comments").width();
	var wrapHeight = $("#comments").height();
	$("#comments").css("left", "50%");
	$("#comments").css("top", "50%");
	$("#comments").css("margin-left", -wrapWidth / 2 + "px");
	$("#comments").css("margin-top", -wrapHeight / 2 + "px");

	$("#comments").fadeIn(300);

	// move module

	if (canMove) {
		$("#comments .popup-header").mousedown(function(event) {
			var isMoving = true;
			$(this).css("cursor", "move");
			var curLeft = event.clientX - $(this).parent().position()
				.left;
			var curTop = event.clientY - $(this).parent().position()
				.top;

			$(document).mousemove(function(event) {
				if (isMoving) {
					$("#comments").css({
						'left': event.clientX -
							curLeft,
						'top': event.clientY -
							curTop
					});
				}
			}).mouseup(function() {
				isMoving = false;
				$("#comments .popup-header").css(
					"cursor", "default");
			});
		});
	}
}

/* comments popup event
/***********************************************************/

/* close pop up
/***********************************************************/
$(function() {
    $("#comments").delegate(".popup-closer,.popup-cancel", "click",
        function() {
            $("#comments").fadeOut(300);
            CloseMask();
            clearRating();
        });

    function clearRating() {
        $("#comments .rating-board li").each(function() {
            $(this).find("span:first").html("0.0score");
            $(this).find("span:eq(1)").attr("class",
                "rating-icon rating0 active");
        });
        $("#comments #total-rating b").html("0.0");
    }
});

/* levels popup event
/***********************************************************/
$(".levels").click(function() {
    $(".popup-wrap").css("width", "450px");
    PopupLevels();
});

function PopupLevels() {
    ShowTMask();
    var wrapWidth = $("#levels").width();
    var wrapHeight = $("#levels").height();
    $("#levels").css("left", "50%");
    $("#levels").css("top", "50%");
    $("#levels").css("margin-left", -wrapWidth / 2 + "px");
    $("#levels").css("margin-top", -wrapHeight / 2 + "px");

    var levelLen = 18;
    for (var i = 1; i <= levelLen; i++) {
        $("#levels .popup-body select").append(
            "<option val='General&nbsp;Level&nbsp;" + i +
            "'>General&nbsp;Level&nbsp;" + i + "</option>");
    }

    $("#levels").fadeIn(300);
}

/* close levels pop up
/***********************************************************/
$(function() {
    $("#levels").delegate(".popup-closer,.popup-cancel", "click",
        function() {
            $("#levels").fadeOut(300);
            CloseMask();
        }
    );
});

/* give rating v2 鼠标点击选中，还可以再点击选中
/***********************************************************/
var runningRating = function(base, dis, cur) {
    // 1. 置空评分
    cur.removeClass(
        "rating0 rating1 rating2 rating3 rating4 rating5").addClass(
        "rating0");
    //console.log(Math.ceil(dis/base));
    var rating = Math.ceil(dis/base);

    cur.removeClass(
        "rating0 rating1 rating2 rating3 rating4 rating5").addClass(
        "rating"+rating);
    if (!cur.parent().parent().hasClass(
            "rating-tboard")) {
        cur.prev().html(rating+".0score");
    }
    getTotalRating();
};
$(".rating-icon").click(function(event) {
    var base = 15.6;
    var cur = $(this);
    var curLeft = cur.offset().left;
    var dis = event.pageX - curLeft;

    if (cur.hasClass("active")) {
        runningRating(base, dis, cur);
    }
});

///* give rating v1
///***********************************************************/
//$(".rating0").mouseenter(function() {
//    var cur = $(this);
//    var curLeft = cur.offset().left;
//
//	cur.next().removeClass("red");
//
//    cur.mousemove(function(event) {
//        if (cur.hasClass("active")) {
//            var base = 15.6;
//            var dis = event.pageX - curLeft;
//            if (dis <= 0) {
//                cur.removeClass(
//                    "rating0 rating1 rating2 rating3 rating4 rating5"
//                ).addClass(
//                    "rating0");
//                cur.prev().html("0.0分");
//            }
//            if (dis <= base * 1 && dis > 0) {
//                cur.removeClass(
//                    "rating0 rating1 rating2 rating3 rating4 rating5"
//                ).addClass(
//                    "rating1");
//                cur.prev().html("1.0分");
//            }
//            if (dis <= base * 2 && dis > base * 1) {
//                cur.removeClass(
//                    "rating0 rating1 rating2 rating3 rating4 rating5"
//                ).addClass(
//                    "rating2");
//                cur.prev().html("2.0分");
//            }
//            if (dis <= base * 3 && dis > base * 2) {
//                cur.removeClass(
//                    "rating0 rating1 rating2 rating3 rating4 rating5"
//                ).addClass(
//                    "rating3");
//                cur.prev().html("3.0分");
//            }
//            if (dis <= base * 4 && dis > base * 3) {
//                cur.removeClass(
//                    "rating0 rating1 rating2 rating3 rating4 rating5"
//                ).addClass(
//                    "rating4");
//                cur.prev().html("4.0分");
//            }
//            if (dis <= base * 5 && dis > base * 4) {
//                cur.removeClass(
//                    "rating0 rating1 rating2 rating3 rating4 rating5"
//                ).addClass(
//                    "rating5");
//                cur.prev().html("5.0分");
//            }
//
//            getTotalRating();
//        }
//    });
//}).mouseleave(function() {
//    if ($(this).hasClass("active")) {
//        $(this).removeClass(
//            "rating0 rating1 rating2 rating3 rating4 rating5").addClass(
//            "rating0");
//        $(this).prev().html("0.0分");
//
//        getTotalRating();
//    }
//}).click(function(event) {
//    var base = 15.6;
//    var cur = $(this);
//    var curLeft = cur.offset().left;
//    var dis = event.pageX - curLeft;
//    if (cur.hasClass("active")) {
//        if (dis <= 0) {
//            cur.removeClass(
//                "rating0 rating1 rating2 rating3 rating4 rating5").addClass(
//                "rating0");
//            cur.prev().html("0.0分");
//        }
//        if (dis <= base * 1 && dis > 0) {
//            cur.removeClass(
//                "rating0 rating1 rating2 rating3 rating4 rating5").addClass(
//                "rating1");
//            cur.prev().html("1.0分");
//
//            isCheck = true;
//            cur.removeClass("active");
//        }
//        if (dis <= base * 2 && dis > base * 1) {
//            cur.removeClass(
//                "rating0 rating1 rating2 rating3 rating4 rating5").addClass(
//                "rating2");
//            cur.prev().html("2.0分");
//
//            isCheck = true;
//            cur.removeClass("active");
//        }
//        if (dis <= base * 3 && dis > base * 2) {
//            cur.removeClass(
//                "rating0 rating1 rating2 rating3 rating4 rating5").addClass(
//                "rating3");
//            cur.prev().html("3.0分");
//
//            isCheck = true;
//            cur.removeClass("active");
//        }
//        if (dis <= base * 4 && dis > base * 3) {
//            cur.removeClass(
//                "rating0 rating1 rating2 rating3 rating4 rating5").addClass(
//                "rating4");
//            cur.prev().html("4.0分");
//
//            isCheck = true;
//            cur.removeClass("active");
//        }
//        if (dis <= base * 5 && dis > base * 4) {
//            cur.removeClass(
//                "rating0 rating1 rating2 rating3 rating4 rating5").addClass(
//                "rating5");
//            cur.prev().html("5.0分");
//
//            isCheck = true;
//            cur.removeClass("active");
//        }
//
//        getTotalRating();
//    }
//
//});

function getTotalRating() {
    var total = 0.0;
    var len = $("#comments .crr-wrap ul li").length;
    var basePoint = Math.pow(10, 1);

    $("#comments .crr-wrap ul li").each(function(index, ele) {
        total += (parseFloat($(ele).find("span:first").html()) *
                basePoint) /
            basePoint;
    });
    $("#total-rating b").html((total / len).toFixed(1));

    return (total / len).toFixed(1);
}

/* limit characters
/***********************************************************/
var maxLen = 500;
$(function() {
    $("#comments textarea").on({
        keyup: function(e) {
            limitBody($(this), e);
        },
        mouseleave: function(e) {
            limitBody($(this), e);
        },
        keydown: function(e) {
            var curSize = $(this).val().length;
            if (curSize >= maxLen && filterKeyCode(e.keyCode)) {
                return false;
            }
        }
    });

    function limitBody(obj, e) {
        var curSize = $(obj).val().length;
        if (curSize >= maxLen) {
            $(obj).next().find("#max-text").css("color", "red");
            $(obj).next().find("#max-text").html(500);
        } else if (curSize >= maxLen - 5) {
            $(obj).next().find("#max-text").css("color", "red");
            $(obj).next().find("#max-text").html(curSize);
        } else {
            $(obj).next().find("#max-text").css("color", "#bfbfbf");
            $(obj).next().find("#max-text").html(curSize);
        }

        if (curSize >= maxLen && filterKeyCode(e.keyCode || e.which)) {
            return false;
        }
    }

    function filterKeyCode(code) {
        switch (code) {
            case 8:
            case 37:
            case 38:
            case 39:
            case 40:
            case 46:
                return false;
            default:
                return true;
        }
    }
});


/* progress about student if there are OneToMany courses
/***********************************************************/
$(function() {
    var stuProgress = {
        init: function() {
            var list = stuProgress.method.getData();
            stuProgress.runningProgress(list);
        },
        data: {
            "time": 1000,
            "stupList": $(".stu-progress")
        },
        runningProgress: function(list) {
            $(list).each(function(index,item) {
                $(item).find("#stup-act").animate({
                	width: list[index].rate * 100 + "%"
                }, stuProgress.data.time);
                list[index].running = function() {
                	var curVal = 0;
                	var stuTimer = setInterval(function() {
                		$(item).find("[realNum]").html(curVal++);
                		if (curVal > list[index].stuProNum) {
                			clearInterval(stuTimer);
                		}
                	}, 100);
                };
                list[index].running();
            });
        },
        method: {
            "getData": function() {
                var list = stuProgress.data.stupList;
                $(list).each(function(index,item) {
                    list[index].stuProNum = $(item).find("[realNum]").attr("realNum");
                    list[index].stuTotalNum = $(item).find("[totalNum]").attr("totalNum");
                    var rate = list[index].stuProNum/list[index].stuTotalNum;
                    list[index].rate = rate;
                });
                return list;
            }
        }
    };
    stuProgress.init();
});

