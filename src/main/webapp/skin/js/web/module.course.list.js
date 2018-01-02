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
        });
    });
    $("#course-order-list").delegate(".row","mouseleave", function() {
        $(this).removeClass("active-area");
    });

   //  $('#course-order-item').delegate(".photo-current", "click", function() {
   //  	alert(222);
   //  	var teacher_time_id = $(this).parent().parent().attr("data-teacher");
   //      ConfirmBox("body","400px","确认预约","确认预约当前课程？",null,subscribeOne2OneCourse(),null);
   //      function subscribeOne2OneCourse(){
   //  		$.ajax({
   //  			type : "POST",
   //  			dataType:'json',
   //  			timeout : 8000,
   //  			url : basePath + "/ucenter/subscribeCourse/courseOne2One/subscribeOne2OneCourse",
   //  			data:{
   //  				teacher_time_id : teacher_time_id,
   //   				course_id : course_id
   //  			},
   //  			beforeSend:BeginLoading,
   //  			error:function(jqXHR, textStatus, errorThrown){
   //  				EndLoading();
   //  				if(textStatus=="timeout"){
   //  					alert("请求超时，请重新查看！");
   //  				}else{
   //  			        alert('系统出现异常,请联系管理员!');
   //  			        alert(textStatus);
   //  		    	}
   //  		    },
   //  			success : function(result) {//提交成功
   //  				EndLoading();
   //  				if(result.success){
   //  			    	alert("预约成功！");
   //  			    	//预约成功后的页面显示（取消预约状态）
   //  				}else{
   //  					alert(result.msg);
   //  				}
   //  			},
   //  			complete : function(data) {
   //  			},
   //  		});
   //  	}
   //
   // });
   // 'body').delegate(".popup-closer", "click", function() {
   //      $(".photo-current").attr("src", basePath+"skin/images/student_photo_cover.png");
   //      $(".photo-current").addClass("photo-option").removeClass("photo-current");
   //  });
});

/* course order ready module
/* slide bg on vip-order-list
/***********************************************************/
$(function() {
    $(".vip-order-list-inner ul li:eq(1)").addClass("order-current").removeClass("order-future");
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
        });
        setTimeout(function() {
            cur.find(".order-gray-bg").css("background-color", "transparent");
            cur.find(".order-gray-bg").find(".blue-text").addClass("white-text").removeClass("blue-text");
        }, 250);

    });

    // swiper for vip-order-slide
    var mySwiper = new Swiper('.vol-swiper-container',{
        freeMode: true,
        freeModeSticky: true,
        slidesPerView: 'auto',
        prevButton: '.order-prev-active-wrap',
        nextButton: '.order-next-active-wrap'
    });
});
