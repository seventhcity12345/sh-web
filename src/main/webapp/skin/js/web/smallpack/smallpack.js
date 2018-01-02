$(function(){
	//1.页面加载完毕，去异步加载该学员可预约的日期列表
	loadDateList();
	
});

function loadDateList(){
	$.ajax({
	   type:"POST", //post提交方式默认是get
	   dataType:'text', 
	   timeout : 8000, //超时时间8秒
	   url:basePath+'/ucenter/courseSmallpack/getDateList',
	   data:{ 
		   subscribeEndTime : $('#subscribeEndTime').val()
	   },
	   error:function(jqXHR, textStatus, errorThrown){
		   /**
			 *modify by athrun.cw 
			 *	bug257 登录超时的提示有误
			 */
			
			alert("您已登录超时，请重新登录"); 
			location.href = basePath + '/ucenter';
	       /*if(textStatus=="timeout"){
	           alert("请求超时，页面自动刷新！");
	       }else{
	    	   alert('系统出现异常,请联系管理员');
	       }*/
	   },
	   success:function(result) {//提交成功
		   $('li[class=vip-order-list-inner]').html(result);
	   }
	}); 
}


/**
 * 加载某天的日期数据
 * day : yyyy-MM-dd的格式日期
 * */
function loadTimeList(day){
	$.ajax({
	   type:"GET", 
	   dataType:'text',
	   timeout : 8000, //超时时间8秒
	   url:basePath+'/ucenter/courseSmallpack/ajaxTimeList',
	   data : { 
		   day : day,
		   subscribeEndTime : $('#subscribeEndTime').val() 
	   },
	   success:function(result) {
		  $('#time-table .time-table').html(result);
	   }
	});
}

/**
 * 确认预约
 */
$("#time-table").delegate(".popup-confirm", "click", function() {
	if($("#time-table li[class=active]").length == 0){
		alert('请选择上课时间！');
		return false;
	}
	var cid = $("#time-table").attr("data-cid");
	//否则就异步提交数据！
	subscribeSmallpack(cid)//预约小包课
});

/**
 * 异步-预约小包课
 */
function subscribeSmallpack(cid){
	$("#time-table").fadeOut(300);
	CloseMask();
	$.ajax({
	   type:"POST",   //post提交方式默认是get
	   dataType:'json', 
	   timeout : 8000, //超时时间8秒
	   url:basePath+'/ucenter/subscribeCourse/smallpack/subscribeSmallpack',
	   data : {
		   teacher_time_id : $('#time-table li[class=active]').attr('tmid'),
		   course_id : cid
	   },
	   beforeSend:BeginLoading,
	   error:function(jqXHR, textStatus, errorThrown){
		   EndLoading();
		   /**
			 *modify by athrun.cw 
			 *	bug257 登录超时的提示有误
			 */
			
			alert("您已登录超时，请重新登录"); 
			location.href = basePath + '/ucenter';
	       /*if(textStatus=="timeout"){
	           alert("请求超时，页面自动刷新！");
	       }else{
	    	   alert('系统出现异常,请联系管理员');
	       }*/
	   },
	   success:function(result) {//提交成功
		   EndLoading();
		   if(result.success){
			   //成功消息提示！！！
			   alert('预约成功：'+result.msg);
			   //成功了需要刷下页面：
			   location.href = basePath+'/ucenter/course/main/default/default';
			   
		   }else{
			   alert('预约失败：'+result.msg);
		   }
		   
	   }
	}); 
}

//小包课 去上课
function smallpackGotoClass(subscribe_id){
	$.ajax({
	   type:"POST",   //post提交方式默认是get
	   dataType:'json', 
	   async: false,
	   timeout : 8000, //超时时间8秒 modify by athrun.cw 2015年9月30日16:10:40（）必须是8s
	   //url:basePath+'/ucenter/courseSmallpack/goToClass/'+subscribeId, 
	   url : basePath + "/ucenter/subscribeCourse/go2Vcube4Class",
	   data:{
			key_id : subscribe_id
	   },
	   beforeSend:BeginLoading,
	   error:function(jqXHR, textStatus, errorThrown){   
		   EndLoading();
		   /**
			 *modify by athrun.cw 
			 *	bug257 登录超时的提示有误
			 */
			
			alert("您已登录超时，请重新登录"); 
			location.href = basePath + '/ucenter';
	       /*if(textStatus=="timeout"){  
	           alert("请求超时，页面自动刷新！");
	       }else{   
	    	   alert('系统出现异常,请联系管理员');
	       }*/
	   },success:function(result) {//提交成功
		   EndLoading();
		   if(result.success){
			   OpenWindow(result.msg);
		   }else{
			   alert(result.msg);
		   }
	   }
	});
}