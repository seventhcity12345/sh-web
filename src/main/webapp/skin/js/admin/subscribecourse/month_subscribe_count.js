	gridName = 'dataGrid1';
	
	$(function(){
		var currentTime = new Date();
		var endTime =  currentTime.format("yyyy-MM-dd hh:mm:ss");   
		//获取31*3天前的时间
		var oneMonthAgo = new Date(currentTime.getTime() - 31 * 24 * 60 * 60 * 1000 * 3);
		var  startTime = oneMonthAgo.format("yyyy-MM-dd hh:mm:ss");
		
		$('#startTime').datetimebox('setValue', startTime);
		$('#endTime').datetimebox('setValue', endTime);
		reloadDataGrid();
	});
	
	//加载数据
	function reloadDataGrid(){
		var startTime = $('#startTime').datetimebox('getValue');	
		var endTime = $('#endTime').datetimebox('getValue');	
		var studentShow;
		if(document.getElementById("studentShow").checked==true){
			studentShow = '1';
		} else {
			studentShow = '';
		}
		$('#dataGrid1').datagrid({
		    url:basePath + '/admin/subscribeCourse/statisticsMonthSubscribeCount?startTime='+startTime
		    		+'&endTime='+endTime+'&studentShow='+studentShow});
	}