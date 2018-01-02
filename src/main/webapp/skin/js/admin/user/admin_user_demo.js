// 预约demo课相关
function openDemoDialog() {
	var userRows = $('#dataGrid1').datagrid('getSelections');
	if (userRows.length != 1 ) {
		$.messager.alert('提示', '请先选中一条数据！');
		return;
	}
	// 打开新的代订课的dialog
	$('#demoSubscribeDialog').dialog({ 
        closed: false,
        cache: false,
        modal: true
    });
	$('#courseType').combobox('setValue','course_type4');
	selectListener();
	setTimeout(function() {
		var data = $('#demoCourse').combobox('getData');
		 $("#demoCourse").combobox('select',data[0].keyId);
	},200);

	$('#dateTime').datebox('setValue', new Date().format("yyyy-MM-dd"));
	
	// 清空grid
	var item = $('#demoGridSubscribeCourse').datagrid('getRows');  
    if (item) {  
        for (var i = item.length - 1; i >= 0; i--) {  
            var index = $('#demoGridSubscribeCourse').datagrid('getRowIndex', item[i]);  
            $('#demoGridSubscribeCourse').datagrid('deleteRow', index);  
        }  
    }  
}

// 选择了课程类别后，级联限制 能够选择的 课程
function selectListener() {
	// 每次重新选择时候，清空course_id date_time
	$('#demoCourse').combobox('setValue', '');
	//$('#dateTime').datebox('setValue', '');
	
	var row = $('#dataGrid1').datagrid('getSelections');
	
    var courseType = $('#courseType').combobox('getValue');
	// 给全局变量courseType赋值
// courseType = course_type;
	// 1.获取课程列表
	$('#demoCourse').combobox(
			{
				url : basePath + '/admin/course/findListDemo?courseType=' + courseType,
				valueField : 'keyId',
				textField : 'courseTitle',
				panelHeight : 150,
				required : true,
				editable : false, // 不允许手动输入
				onLoadSuccess : function() { // 数据加载完毕事件
				}
			});
}

// 更具输入的条件，找合适的排课数据
function findTeacherTime() {
	var courseId = $('#demoCourse').combobox('getValue');
	if (courseId == null || courseId == '') {
		$.messager.alert('提示', '请选择课程！');
		return;
	}
	var selectDay = $('#dateTime').datetimebox('getValue');	
	var courseType = $('#courseType').combobox('getValue');	
	var webexRoomHostId = $('#webexRoomHostId').combobox('getValue');	
	
	if (webexRoomHostId == null || webexRoomHostId == '') {
		$.messager.alert('提示', '请选择房间！');
		return;
	}
	
	$('#demoGridSubscribeCourse').datagrid({
	    url:basePath + '/admin/teacherTime/findTimesAndTeachersByDayAndCourseType?selectDay='+selectDay
	    		+'&courseType='+courseType + '&webexRoomHostId=' + webexRoomHostId});
}


// 预约课程(潜客)
function userSubscribeDemoCourse() {
	var userRows = $('#dataGrid1').datagrid('getSelections');
	var courseId = $('#demoCourse').combobox('getValue');
	var courseType = $('#courseType').combobox('getValue');	
	
	var webexRoomHostId = $('#webexRoomHostId').combobox('getValue');	
	var subscribeRemark = $('#subscribeRemark').textbox('getValue');
	var subscribeType = $('input:radio[name="subscribeType"]:checked').val();
	
	if("course_type4" == courseType){
		if('' == webexRoomHostId){
			return;
		} else {
			webexRoomHostId = webexRoomHostId.replace(/\s/g, "");
		}
		if(!$('#subscribeRemark').textbox('isValid')){
			return;
		}
	}
	
	var rows = $('#demoGridSubscribeCourse').datagrid('getSelections');
	if (rows.length != 1 || userRows.length != 1 ) {
		$.messager.alert('提示', '请先选中一条数据！');
		return;
	}
	var obj = {
			courseId : courseId,
			userId : userRows[0].user_id,
			teacherTimeId : rows[0].keyId,
			courseType : courseType,
			webexRoomHostId : webexRoomHostId,
			subscribeRemark : subscribeRemark,
			subscribeType: subscribeType
		};
	$.ajax({
		type : "POST",
		dataType : 'json',
		timeout : 8000,
		contentType:'application/json;charset=UTF-8',
		url : basePath + "/admin/subscribeCourse/subscribeCourseDemo",
		data : JSON.stringify(obj),
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus == "timeout") {
				alert("请求超时，请重新查看！");
			} else {
				alert('系统出现异常,请联系管理员!');
				alert(textStatus);
			}
		},
		success : function(result) {// 提交成功
			if (result.code == 200) {
				$.messager.alert('demo预约成功', "demo时间:" +  
						formatDate(rows[0].startTime) + "<br/>demo会议号生成需要一分钟左右,请在列表中查看");
				$('#dataGrid1').datagrid(
				'reload');
				$('#demoSubscribeDialog').dialog('close');
				$('#demoInputRoomIdDialog').dialog('close');
             } else if(result.code == 20006) {
            	 if(rows[0].thirdFrom == 'huanxun'){
            		 $.messager.alert('提示', "该教师来自环迅，需要提前预约！");
            	 } else {
	        		 if(result.data != 0){
        				 $.messager.alert('提示', "该教师来自"+ rows[0].thirdFrom  + "，需要提前" +
        						 result.data/60 + "小时预约！");
	        		 } else {
	        			 $.messager.alert('提示', "该教师来自"+ rows[0].thirdFrom  + "，需要提前预约！");
	        		 }
            	 }
             } else if(result.code == 10001){
            	 $.messager.alert('提示', result.msg);
            	 
             }
             else {
            	 $.messager.alert('提示', errorCode[result.code]);
            	 
             }
			
		},
		complete : function(data) {
		},
	});
}

//预约课程(学员管理中预约demo：两个逻辑一样,预约成功后弹窗提示不一样)
function userSubscribeDemoCourseByStudent() {
	var userRows = $('#dataGrid1').datagrid('getSelections');
	var courseId = $('#demoCourse').combobox('getValue');
	var courseType = $('#courseType').combobox('getValue');	
	
	var webexRoomHostId = $('#webexRoomHostId').combobox('getValue');	
	var subscribeRemark = $('#subscribeRemark').textbox('getValue');
	var subscribeType = $('input:radio[name="subscribeType"]:checked').val();
	
	if("course_type4" == courseType){
		if('' == webexRoomHostId){
			return;
		} else {
			webexRoomHostId = webexRoomHostId.replace(/\s/g, "");
		}
		if(!$('#subscribeRemark').textbox('isValid')){
			return;
		}
	}
	
	var rows = $('#demoGridSubscribeCourse').datagrid('getSelections');
	if (rows.length != 1 || userRows.length != 1 ) {
		$.messager.alert('提示', '请先选中一条数据！');
		return;
	}
	var obj = {
			courseId : courseId,
			userId : userRows[0].user_id,
			teacherTimeId : rows[0].keyId,
			courseType : courseType,
			webexRoomHostId : webexRoomHostId,
			subscribeRemark : subscribeRemark,
			subscribeType: subscribeType
		};
	$.ajax({
		type : "POST",
		dataType : 'json',
		timeout : 8000,
		contentType:'application/json;charset=UTF-8',
		url : basePath + "/admin/subscribeCourse/subscribeCourseDemo",
		data : JSON.stringify(obj),
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus == "timeout") {
				alert("请求超时，请重新查看！");
			} else {
				alert('系统出现异常,请联系管理员!');
				alert(textStatus);
			}
		},
		success : function(result) {// 提交成功
			if (result.code == 200) {
				$.messager.alert('demo预约成功', "demo时间:" +  
						formatDate(rows[0].startTime) + "<br/>demo课预约成功,会议号生成大约需要一分钟");
				$('#dataGrid1').datagrid(
				'reload');
				$('#demoSubscribeDialog').dialog('close');
				$('#demoInputRoomIdDialog').dialog('close');
             } else if(result.code == 20006) {
            	 if(rows[0].thirdFrom == 'huanxun'){
            		 $.messager.alert('提示', "该教师来自环迅，需要提前预约！");
            	 } else {
	        		 if(result.data != 0){
        				 $.messager.alert('提示', "该教师来自"+ rows[0].thirdFrom  + "，需要提前" +
        						 result.data/60 + "小时预约！");
	        		 } else {
	        			 $.messager.alert('提示', "该教师来自"+ rows[0].thirdFrom  + "，需要提前预约！");
	        		 }
            	 }
             } else if(result.code == 10001){
            	 $.messager.alert('提示', result.msg);
            	 
             }
             else {
            	 $.messager.alert('提示', errorCode[result.code]);
            	 
             }
			
		},
		complete : function(data) {
		},
	});
}


// 取消预约课程
function userCancelSubscribeCourseDemo() {
	var userRows = $('#dataGrid1').datagrid('getSelections');
	if (userRows.length != 1 || userRows.length != 1 ) {
		$.messager.alert('提示', '请先选中一条数据！');
		return;
	}
	var subscribeId = userRows[0].subscribeId;
	// 判断之前是否有预约
	if (subscribeId == undefined || subscribeId == '' || subscribeId == null) {
		$.messager.alert('提示', '未找到该用户的demo信息！');
		return;
	}
	var obj = {
			subscribeId : subscribeId,
		};
	$.ajax({
		type : "POST",
		dataType : 'json',
		timeout : 8000,
		contentType:'application/json;charset=UTF-8',
		url : basePath + "/admin/subscribeCourse/cancelSubscribeCourseDemo",
		data : JSON.stringify(obj),
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus == "timeout") {
				alert("请求超时，请重新查看！");
			} else {
				alert('系统出现异常,请联系管理员!');
				alert(textStatus);
			}
		},
		success : function(result) {// 提交成功
			if (result.code == 200) {
				$.messager.alert('提示', "demo取消预约成功!");
				$('#dataGrid1').datagrid(
				'reload');
				$('#demoSubscribeDialog').dialog('close');
             } else if(result.code == 20011) {
        		 if(result.data != 0){
    				 $.messager.alert('提示', "该教师来自"+ result.data.thirdFrom  + "，需要提前" +
    						 result.data.courseTypeSubscribeTime/60 + "小时取消预约！");
        		 } else {
        			 $.messager.alert('提示', errorCode[result.code]);
        		 }
             } else if(result.code == 20012) {
            	 $.messager.alert('提示', "该教师来自环迅，需要提前取消预约！");
             } else {
            	 $.messager.alert('提示', errorCode[result.code]);
            	 
             }
		}
	});
}

/**
 * 默认选中第一项
 */
function gridOnloadListener(){
	$('#demoGridSubscribeCourse').datagrid('selectRow',0);
}


//预约课程(潜客)
function userSubscribePrecondition() {
	var userRows = $('#demoGridSubscribeCourse').datagrid('getSelections');
	if (userRows.length != 1 || userRows.length != 1 ) {
		$.messager.alert('提示', '请先选中一条数据！');
		return;
	}
	// 设置回显房间号
	var webexRoomHostId = $('#webexRoomHostId').combobox('getValue');	
	$('#webexRoomHostIdText').textbox('setValue',webexRoomHostId);
	
	
	// 设置回显会议号
	var rows = $('#demoGridSubscribeCourse').datagrid('getSelections');
	if (rows.length != 1 || userRows.length != 1 ) {
		$.messager.alert('提示', '请先选中一条数据！');
		return;
	}
	var obj = {
			teacherTimeId : rows[0].keyId,
			webexRoomHostId : webexRoomHostId,
	};
	
	$.ajax({
		type : "POST",
		timeout : 8000,
		url : basePath + "/admin/webex/findMeetingKey",
		data : obj,
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus == "timeout") {
				alert("请求超时，请重新查看！");
			} else {
				alert('系统出现异常,请联系管理员!');
				alert(textStatus);
			}
		},
		success : function(result) {// 提交成功
			$('#webexMeetingKeyText').textbox('setValue',result);
		}
	});
	
	var courseType = $('#courseType').combobox('getValue');	
	$('#subscribeRemark').textbox('clear');
	
	
	if('course_type4' == courseType){
		$('#demoInputRoomIdDialog').dialog('open');
	} else {
		userSubscribeDemoCourse();
	}
}


//预约课程(学员管理中预约demo课程)
function userSubscribePreconditionByStudent() {
	var userRows = $('#demoGridSubscribeCourse').datagrid('getSelections');
	if (userRows.length != 1 || userRows.length != 1 ) {
		$.messager.alert('提示', '请先选中一条数据！');
		return;
	}
	// 设置回显房间号
	var webexRoomHostId = $('#webexRoomHostId').combobox('getValue');	
	$('#webexRoomHostIdText').textbox('setValue',webexRoomHostId);
	
	
	// 设置回显会议号
	var rows = $('#demoGridSubscribeCourse').datagrid('getSelections');
	if (rows.length != 1 || userRows.length != 1 ) {
		$.messager.alert('提示', '请先选中一条数据！');
		return;
	}
	var obj = {
			teacherTimeId : rows[0].keyId,
			webexRoomHostId : webexRoomHostId,
	};
	
	$.ajax({
		type : "POST",
		timeout : 8000,
		url : basePath + "/admin/webex/findMeetingKey",
		data : obj,
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus == "timeout") {
				alert("请求超时，请重新查看！");
			} else {
				alert('系统出现异常,请联系管理员!');
				alert(textStatus);
			}
		},
		success : function(result) {// 提交成功
			$('#webexMeetingKeyText').textbox('setValue',result);
		}
	});
	
	var courseType = $('#courseType').combobox('getValue');	
	$('#subscribeRemark').textbox('clear');
	
	
	if('course_type4' == courseType){
		$('#demoInputRoomIdDialog').dialog('open');
	} else {
		userSubscribeDemoCourseByStudent();
	}
}

// 格式化中外教
function teacherNationalityFormater(teacherNationality){
	if(teacherNationality == "中国"){
		return "中教";
	} else {
		return "外教";
	}
	
}