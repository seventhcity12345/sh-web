
/* document ready module
/*
/***********************************************************/
var teacher_time_id = null;//全局变量,老师上课时间，用于上课的参数
var coid = null;//全局变量，用于校验是否选择了正确的课程

//预定课程
function subscribeCourse(course_id,start_time,end_time, topic_name){
	if(course_id!=coid){
		alert("请您选择老师！");
		return;
	} 
            
    $.ajax({
	   type:"POST",   //post提交方式默认是get
	   dataType:'json', 
	   timeout : 8000, //超时时间8秒
	   url:basePath+'/ucenter/course1v1/saveLesson', 
	   data : {
		   course_id : course_id,
		   teacher_time_id : teacher_time_id,
		   topic_name : topic_name
	   },
	   beforeSend:BeginLoading,
	   error:function(jqXHR, textStatus, errorThrown){   
		   EndLoading();
	       if(textStatus=="timeout"){  
	           alert("请求超时，页面自动刷新！");
	       }else{   
	    	   alert('系统出现异常,请联系管理员');
	           alert(textStatus);
	       }
	   },
	   success:function(result) {//提交成功
		   EndLoading();
		   if(!result.success){
			   alert(result.msg);
			   refreshRedPoint();//取消预约需要刷新所有的红点数据
			   //预约某个老师失败了！不代表不能预约别的老师了！
		   }else{
			   //设置绿标 tp="day"
			   $('li[tp="day"][class="order-current"]').append('<div class="order-tips-green"></div>');
			   alert('恭喜您预约课程成功！');//预约成功
		   }
		   loadLessons(start_time.substr(0,10));
	   }
	});
}

//取消预约
function cancelCourse(key_id, start_time, lesson_count){
	$.ajax({
	   type:"POST",   //post提交方式默认是get
	   dataType:'json', 
	   timeout : 8000, //超时时间8秒
	   url:basePath+'/ucenter/course1v1/cancalLesson', 
	   data : {
		   key_id : key_id,
		   start_time : start_time
	   },
	   beforeSend:BeginLoading,
	   error:function(jqXHR, textStatus, errorThrown){   
		   EndLoading();
	       if(textStatus=="timeout"){  
	           alert("请求超时，页面自动刷新！");
	       }else{   
	    	   alert('系统出现异常,请联系管理员');
	           alert(textStatus);
	       }
	   },
	   success:function(result) {//提交成功
		   EndLoading();
		   if(result.success){
			   //取消成功
			   if(lesson_count==1){//1.去绿标:只有当我今天只预约 了1结课，取消了才没有绿标，如果我预约过2结课，取消掉一节课，还有一节预约的，所以绿标不能去掉
				   $('li[tp="day"][class="order-current"]').find('div[class="order-tips-green"]').remove();
			   }
			   
			   refreshRedPoint();//取消预约需要刷新所有的红点数据
			   
			   alert(result.msg);
			   
		   }else{
			   //失败
			   alert(result.msg);
		   }
		   loadLessons(start_time.substr(0,10));
	   }
	});
}

//更新红标数量
function refreshRedPoint(){
	//TODO 更新红标数量
	$.ajax({
		   type:"POST",   //post提交方式默认是get
		   dataType:'json', 
		   timeout : 8000, //超时时间8秒
		   url:basePath+'/ucenter/course1v1/ajaxRedpoint',
		   beforeSend:BeginLoading,
		   error:function(jqXHR, textStatus, errorThrown){   
			   EndLoading();
		       if(textStatus=="timeout"){  
		           alert("请求超时，页面自动刷新！");
		       }else{   
		    	   alert('系统出现异常,请联系管理员');
		           alert(textStatus);
		       }
		   },
		   success:function(result) {//提交成功
			   EndLoading();
			   if(result.success){
				   //把数据塞回到日历内
				   var dt = result.data;
				   for(var i in dt){
					   $("#"+i).html(dt[i]);
				   }
				   
			   }else{
				   //失败
				   alert(result.msg);
			   }
		   }
		});
	
}


$(function() {
    $(".vip-order-list-inner").css("width",$(".vip-order-list").width()-($(".order-prev-wrap").width()+31)*2 - 5 +"px");

    $(window).resize(function() {
        $(".vip-order-list-inner").css("width",$(".vip-order-list").width()-($(".order-prev-wrap").width()+31)*2 - 5 +"px");
    });
})

/* course order list module
/* image changing under the click or hover event
/***********************************************************/
$(function() {
    $("#course-order-list").delegate(".row","mouseenter", function() {
        $(this).addClass("active-area");
        $(".active-area").delegate(".photo-option", "mouseenter", function() {
            $(this).addClass("cursor-p");
            $(this).attr("src", basePath+"/skin/images/student_photo_cover_active.png");
        });
        $(".active-area").delegate(".photo-option", "mouseleave", function() {
            $(this).removeClass("cursor-p");
            $(this).attr("src", basePath+"/skin/images/student_photo_cover.png");
        });
        $(".active-area").delegate(".photo-option", "click", function() {
            $(".active-area").find(".photo-current").addClass("photo-option");
            $(".active-area").find(".photo-current").attr("src", basePath+"/skin/images/student_photo_cover.png");
            $(".active-area").find(".photo-current").removeClass("photo-current");
            $(".active-area").find(".photo-current").removeClass("cursor-p");
            $(this).addClass("photo-current");
            $(this).removeClass("photo-option");
            $(this).removeClass("cursor-p");
            $(this).attr("src", basePath+"/skin/images/student_photo_cover_active.png");
            
            //数据业务逻辑
            teacher_time_id = $(this).attr('tmid');
            coid = $(this).attr('coid');
            
        });
    });
    $("#course-order-list").delegate(".row","mouseleave", function() {
        $(this).removeClass("active-area");
    });
})

/* course order ready module
/* slide bg on vip-order-list
/***********************************************************/
$(function() {
    $(".vip-order-list-inner ul li:eq(3)").addClass("order-current").removeClass("order-future");
    $(".order-current").find(".order-tips-tag").hide();
    $(".order-current").find(".order-tips-green").hide();
    $(".order-current").find(".order-gray-bg").css("background-color", "transparent");
    $("#order-current").animate({
        left: $(".order-current").position().left
    }, 0);

    $(".vip-order-list").delegate(".order-future", "click", function(index) {
        setTimeout(function() {
            $(".order-current").find(".order-tips-tag").fadeIn(300);
            $(".order-current").find(".order-tips-green").fadeIn(300);
            $(".order-current").find(".order-gray-bg").css("background-color", "#bfbfbf");
            //$(".order-current").find(".order-gray-bg").find(".white-text").addClass("blue-text").removeClass("white-text");
            $(".order-current").addClass("order-future").removeClass("order-current");
        }, 100);
        var cur = $(this);
        var cLeft = cur.position().left;
        $("#order-current").animate({
            left: cLeft
        }, 300, function() {
            cur.addClass("order-current").removeClass("order-future");
            cur.find(".order-tips-tag").fadeOut(100);
            cur.find(".order-tips-green").fadeOut(100);
    //         if (cur.offset().left < $(".vip-order-list-inner").offset().left) {
    //         	$(".vip-order-slide").animate({
    //         		left: $(".vip-order-slide").offset().left-$(".order-current").width() * (cur.index())
    //         	}, 300);
    //         }
    //         if ((cur.offset().left+cur.width()) > ($(".vip-order-list-inner").offset().left+$(".vip-order-list-inner").width())) {
				// $(".vip-order-slide").animate({
    //         		left: $(".vip-order-slide").offset().left-$(".order-current").width() * (cur.index())
    //         	}, 300);
    //         };
        });
        setTimeout(function() {
            cur.find(".order-gray-bg").css("background-color", "transparent");
            cur.find(".order-gray-bg").find(".blue-text").addClass("white-text").removeClass("blue-text");
        }, 250);
        
    });

    var _vipOrderSlide = 0;
    var _slideDistance = $(".vip-order-list-inner").width()/2.6;
    $("ul").delegate(".order-next-active", "click", function() {
        _vipOrderSlide -= _slideDistance;
        if (_vipOrderSlide < 0) {
            $(".order-prev").addClass("order-prev-active").removeClass("order-prev");
            $(".order-prev-wrap").addClass("order-prev-active-wrap").removeClass("order-prev-wrap");
        };
        var _sWidth = $(".vip-order-list-inner").width() - 1792;
        if (_vipOrderSlide <= _sWidth) {
            _vipOrderSlide = _sWidth;
            $(this).addClass("order-next").removeClass("order-next-active");
            $(this).parent().addClass("order-next-wrap").removeClass("order-next-active-wrap");
        } else {
            $(this).addClass("order-next-active").removeClass("order-next");
            $(this).parent().addClass("order-next-active-wrap").removeClass("order-next-wrap");
        }
        $(".vip-order-slide").animate({
            left: _vipOrderSlide
        }, 800, "easeInOutSine", function() {});
    });
    $("ul").delegate(".order-prev-active", "click", function() {
        _vipOrderSlide += _slideDistance;
        if (_vipOrderSlide >= 0) {
            _vipOrderSlide = 0;
            $(".order-prev-active").addClass("order-prev").removeClass("order-prev-active");
            $(".order-prev-active-wrap").addClass("order-prev-wrap").removeClass("order-prev-active-wrap");
        };
        var _sWidth = $(".vip-order-list-inner").width() - 1792;
        if (_vipOrderSlide > _sWidth) {
            $(".order-next").addClass("order-next-active").removeClass("order-next");
            $(".order-next-wrap").addClass("order-next-active-wrap").removeClass("order-next-wrap");
        };
        $(".vip-order-slide").animate({
            left: _vipOrderSlide
        }, 800, "easeInOutSine", function() {});
    });
});

function loadLessons(dt){
	teacher_time_id = null;
	coid = null;
	$.ajax({
		dataType:"text",
		url:basePath+'/ucenter/course1v1/ajaxLessonList',
		data: { 
			selected_date : dt 
		},success: function(msg){
			$('#course-order-list').html(msg);
		}
	});
}
