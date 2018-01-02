//异步加载下面的上课信息列表
function loadInfos(dt, type){
	$.ajax({
		dataType:"text",
		url:basePath+'/tcenter/ajaxLessonList',
		data: {
			dt : dt ,
			type : type
		},success: function(msg){
			//列表数据页面渲染
			$('#course-order-list').html(msg);
		}
	});
}

function refreshSings(){
	$.ajax({
		dataType:"json",
		url:basePath+'/tcenter/ajaxPoints',
		success: function(jm){
			//把日期的数据全部赋值一遍！
			if(jm.success){
				for(var i in jm.data.green_dates){
					
					if( $('#gp'+i).length == 0){
						var gp = $("<div class=\"class-tips-green\" onclick=\"loadInfos('"+i+"','1')\" id=\"gp"+i+"\">"+jm.data.green_dates[i]+"</div>");
						$('#day'+i).after(gp[0]);
						
					}else{
						$('#gp'+i).text(jm.data.green_dates[i]);
					}
					
					//如果红标的值是1，说明是最后一次了！就可以删除红标了！
					if( $('#rp'+i).text() == '1' ){
						$('#rp'+i).remove();
					}
				}
				
				//将查询到红标的值进行赋值
				for(var i in jm.data.red_dates){
					$('#rp'+i).text(jm.data.red_dates[i]);
				}
				
			}
		}
	});
}

//确认预约
function confirmSub(key_id, img, dt){
	$.ajax({
		dataType:"text",
		url:basePath+'/tcenter/confirmSub',
		data: {
			key_id : key_id
		},success: function(msg){
			$(img).attr('src', basePath+'/skin/images/checkbox_active.png');
			$(img).removeAttr('click');
			
			//再发送异步请求更新红标，绿标的数据！
			refreshSings();
		}
	});
}


function timer(id, intDiff){
	//激活上课按钮
	var idx = id.replace('djs', '');//只留下数字编号！
	
	var act_id = window.setInterval(function(){
		
		var day=0,
			hour=0,
			minute=0,
			second=0;//时间默认值		
		if(intDiff > 0){
			day = Math.floor(intDiff / (60 * 60 * 24));
			hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
			minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
			second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
		}
		if (minute <= 9) minute = '0' + minute;
		if (second <= 9) second = '0' + second;
		
		$('#'+id).find('span[tk="day_show"]').html(day+"天");
		$('#'+id).find('strong[tk="hour_show"]').html(hour+":");
		$('#'+id).find('strong[tk="minute_show"]').html(minute+":");
		$('#'+id).find('strong[tk="second_show"]').html(second+":");
		
		intDiff--;
		
		if(intDiff<=0){
			//样式
			$("#goTo"+idx).removeClass('gray-bg').addClass('blue-bg');
			//从缓存获取上课地址
			/*$('#goTo'+idx).attr('href', basePath+'/tcenter/goToClass/'+lessonCache.data('lesson'+idx));*/
			$('#goTo'+idx).attr('href', basePath+'/tcenter/goToClassByTeacher/'+lessonCache.data('lesson'+idx));
			//清除
			clearInterval(act_id);
		}
		
	}, 1000);
	
} 