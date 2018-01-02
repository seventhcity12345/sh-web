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