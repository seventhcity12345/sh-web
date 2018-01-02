/* course order ready module
/* slide bg on vip-order-list
/***********************************************************/
var eventobj = {
    vip:function(){
        $('.vip-order-list').each(function(){
            var $curC = $(this).find('.order-future');
            var $lis = $(this).find('.vip-order-list-inner li');
            
            var $curO = $(this).find('.order-currents');
            var $liW = $lis.width()+50;

            $lis.each(function(index){
                 //$(".vip-order-list-inner ul li:eq(1)").addClass("olds");
                 $(this).parent().find("li:eq(1)").addClass("olds");
                 var $that = $(this);
                 $that.click(function(){
                         if($that.hasClass('order-future')){
                              // $that.addClass('order-current');
                              // if($that.hasClass('order-current')){
                              //        $curO.animate({
                              //              left:(index-1)*$liW+'px'
                              //           },300); 
                              //        $that.siblings('.order-future').find('.order-gray-bg').css("background-color", "#bfbfbf");
                              //        setTimeout(sets,240);
                              //   }  
                              $curO.animate({
                                     left:(index-1)*$liW+'px'
                                  },300); 
                               $that.siblings('.order-future').find('.order-gray-bg').css("background-color", "#bfbfbf");
                               setTimeout(sets,240); 
                         };
                 });
                 function sets(){
                    $that.find('.order-gray-bg').css("background-color", "transparent");
                 };
                 
            });
         });
    },
    girl: function(){
    	$(".course-order-list").delegate(".row","mouseenter", function() {
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
                
                $('.crs-sure').css('display','block');
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

        
        $(".course-order-list").delegate(".row","mouseleave", function() {
            $(this).removeClass("active-area");
        });    
    }
   
};

$(function() {
    $('.course-order-item').delegate(".photo-current", "click", function() {
    	var teacher_time_id = $(this).parent().parent().attr("data-teacher");
    	if ($(this).parents(".crs-main").find(".crs-btn").hasClass("js-reorder")){
    		ConfirmBox("body", "280px", null, "再次预约该节课将会<br>消耗一个课时，是否确认再次预约？", null, confirmSubmit, null);
    	} else {
    		ConfirmBox("body", "250px", null, "确认预约当前课程？", null, confirmSubmit, null);
    	}
    	
        function confirmSubmit() {
             /* alert(course_id);
              alert(teacher_time_id);*/
              $.ajax({
              type : "POST",
              dataType:'json', 
              timeout : 8000, 
              url : basePath + "/ucenter/subscribeCourse/courseOne2One/subscribeOne2OneCourse",
              data:{
                teacher_time_id : teacher_time_id,
                course_id : course_id
              },
              beforeSend:BeginLoading,
              error:function(jqXHR, textStatus, errorThrown){ 
                EndLoading();
                if(textStatus=="timeout"){ 
                  alert("请求超时，请重新查看！"); 
                }else{ 
                      alert('您已登陆超时，请重新登陆'); 
                      alert(textStatus); 
                  }
              }, 
              success : function(result) {//提交成功
                EndLoading(); 
                if(result.success){
                    alert("预约成功！");
                    //预约成功后的页面显示（取消预约状态）
                }else{
                  alert(result.msg);
                }
                location.href = basePath + '/ucenter/course/main/default/default';
              },
              complete : function(data) {
              },
            });
           };
            
       });
    
    $('body').delegate(".popup-closer,.popup-cancel", "click", function() {
             $(".photo-current").attr("src", basePath+"/skin/images/student_photo_cover.png");
             $(".photo-current").addClass("photo-option").removeClass("photo-current");
        });
    
})
