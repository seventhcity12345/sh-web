//因为新增训练营成员时需要训练营id，但是新增完又要刷新训练营列表，所以这个id需要保存一下
var trainingCampId = '';

//格式化是否生效
function formatIsCanUsed(value, row) {
	if (new Date(row.trainingCampStartTime).getTime() <= new Date().getTime()
			&& new Date(row.trainingCampEndTime).getTime() >= new Date()
					.getTime()) {
		return "是";
	}
	return "否";
}

gridName = 'trainingCampGrid';
var oldGridName = 'trainingCampGrid';

//组合查询（因为重新刷新一次组合查询的框，就会之前的记录，而且还要再掉一次列表接口，浪费）
function groupSearchTraining(value) {
	if(value == oldGridName){
		//和之前的一样，就还是调用原来的组合查询方法
		groupSearch();
	}
	else{
		//不一样，则刷新这个组合查询弹框
		groupSearchNew(value);
		oldGridName = value;
	}
}

// 新增训练营
function addTrainingCamp() {

	$("#keyId").val("");
	$("#trainingCampTitle").textbox('setValue', "");
	$("#trainingCampStartTime").datebox({ disabled: false });
	$("#trainingCampEndTime").datebox({ disabled: false });
	$("#trainingCampStartTime").datebox('setValue', "");
	$("#trainingCampEndTime").datebox('setValue', "");
	$("#trainingCampDesc").textbox('setValue', "");
	
	$("#trainingCampPic").val("");
	$('#fileId').filebox('setText',"");
	$('#fileId').filebox('setValue', "");

	$('#trainingCampDiv').dialog('open');
}

// 编辑训练营
function updateTrainingCamp() {

	var row = $('#trainingCampGrid').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请先选中一条数据！');
		return;
	}

	$("#keyId").val(row[0].keyId);
	$("#trainingCampTitle").textbox('setValue', row[0].trainingCampTitle);
	
	$("#trainingCampStartTime").datebox({ disabled: true });
	$("#trainingCampEndTime").datebox({ disabled: true });
	$("#trainingCampStartTime").datebox('setValue',
			formatDateDay(row[0].trainingCampStartTime));
	$("#trainingCampEndTime").datebox('setValue',
			formatDateDay(row[0].trainingCampEndTime));
	
	$("#trainingCampDesc").textbox('setValue', row[0].trainingCampDesc);
	
	$("#trainingCampPic").val(row[0].trainingCampPic);
	$('#fileId').filebox('setValue', "");
	$('#fileId').filebox('setText', row[0].trainingCampPic);

	$('#trainingCampDiv').dialog('open');
}

// 提交 新增/编辑训练营
function updateTrainingCampSubmit() {
	
	if($('#fileId').filebox('getValue') != ''){
		if (!isfile($('#fileId').filebox('getValue'), 1)) {
			$.messager.alert('提示', "图片类型只能为jpg、gif、bmp、png、jpeg");
			return;
		}
	}
	
	if (new Date($("#trainingCampStartTime").datebox("getValue")).getTime() > new Date(
			$("#trainingCampEndTime").datebox("getValue")).getTime()) {
		$.messager.alert('提示', "生效开始时间必须小于生效结束时间！");
		return;
	}

	$('#trainingCampForm').form('submit', {
		onSubmit : function() {
			// easyui的校验
			if ($(this).form('enableValidation').form('validate')) {
				$('#trainingCampDiv').dialog('close');
				return true;
			} else {
				return false;
			}
		},
		success : function(result) {
			result = eval("(" + result + ")");
			if (result.code == 200) {
				$('#trainingCampGrid').datagrid('reload');
				$.messager.alert('提示', "更新成功");
			} else {
				$.messager.alert('提示', errorCode[result.code]);
			}
		},
		error : function(status) {
			$.messager.alert('提示', '系统出现异常,请联系管理员', 'error' + status);
		}
	});
}

// 批量删除训练营
function deleteTrainingCamp() {
	var row = $('#trainingCampGrid').datagrid('getSelections');
	if (row.length < 1) {
		$.messager.alert('提示', '请先选中至少一条数据！');
		return;
	}
	$.messager.confirm("确认", "确认要删除这 " + row.length + " 条数据吗？", function(r) {
		if (r) {
			// key的字符串集合
			var keys = "";
			for (var i = 0; i < row.length; i++) {
				if (i == row.length - 1) {
					keys = keys + row[i].keyId;
					break;
				}
				keys = keys + row[i].keyId + ",";
			}

			$.ajax({
				type : "POST", // post提交方式默认是get
				dataType : 'json',
				url : basePath + '/admin/deleteTrainingCamp',
				data : {
					keys : keys
				},
				error : function(data) { // 设置表单提交出错
					$('#loading-mask').hide();
					$.messager.alert('提示', '系统出现异常,请联系管理员', 'error');
				},
				success : function(result) {
					$('#loading-mask').hide();

					if (result.code == 200) {
						$.messager.alert('提示', "成功删除" + result.data + "条数据");
						$('#trainingCampGrid').datagrid('reload');
					} else {
						$.messager.alert('提示', "删除失败");
					}
				}
			});
		}
	});
}

// 管理训练营学员，打开弹窗
function manageTrainingMember() {
	var row = $('#trainingCampGrid').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请先选中一条数据！');
		return;
	}
	
	delSearchItemAll();

	$('#trainingMemberGrid').datagrid(
			{
				url : basePath + '/api/speakhi/v1/admin/trainingMemberList/'
						+ row[0].keyId,
			})

	trainingCampId = row[0].keyId;
	$('#trainingMemberDiv').dialog('open');
}

//新增训练营学员，打开弹窗
function openUserNotInTrainingMemberDiv() {
	//这里之后需要拿训练营id，用来插入学员信息的
	if (trainingCampId == '') {
		$.messager.alert('提示', '请先选中一条训练营数据！');
		return;
	}
	
	delSearchItemAll();

	$('#userNotInTrainingMemberGrid').datagrid(
			{
				url : basePath + '/api/speakhi/v1/admin/notTrainingMemberList'
			})

	$('#userNotInTrainingMemberDiv').dialog('open');
}

//增加训练营成员
function addTrainingMember() {
	//这里之后需要拿训练营id，用来插入学员信息的
	if (trainingCampId == '') {
		$.messager.alert('提示', '请先选中一条训练营数据！');
		return;
	}
	
	var row = $('#userNotInTrainingMemberGrid').datagrid('getSelections');
	if (row.length < 1) {
		$.messager.alert('提示', '请先选中至少一条学员数据！');
		return;
	}
	
	// key的字符串集合
	var keys = "";
	for (var i = 0; i < row.length; i++) {
		if (i == row.length - 1) {
			keys = keys + row[i].keyId;
			break;
		}
		keys = keys + row[i].keyId + ",";
	}

	$.ajax({
		type : "POST", // post提交方式默认是get
		dataType : 'json',
		url : basePath + '/api/speakhi/v1/admin/trainingMember',
		data : {
			trainingCampId:trainingCampId,
			keys : keys
		},
		error : function(data) { // 设置表单提交出错
			$('#loading-mask').hide();
			$.messager.alert('提示', '系统出现异常,请联系管理员', 'error');
		},
		success : function(result) {
			$('#loading-mask').hide();

			if (result.code == 200) {
				$('#trainingCampGrid').datagrid('reload');
				$('#trainingMemberGrid').datagrid('reload');
				$('#userNotInTrainingMemberDiv').dialog('close');
				$.messager.alert('提示', "新增成功");
			} else if (result.code == 10001){
				$.messager.alert('提示', result.msg);
			}
			else{
				$.messager.alert('提示', errorCode[result.code]);
			}
		}
	});
}

//批量删除数据
function deleteTrainingMember() {
	
	var row = $('#trainingMemberGrid').datagrid('getSelections');
	if (row.length < 1) {
		$.messager.alert('提示', '请先选中至少一条学员数据！');
		return;
	}
	$.messager.confirm("确认", "确认要删除这 " + row.length + " 条数据吗？", function(r) {
		if (r) {
			// key的字符串集合
			var keys = "";
			for (var i = 0; i < row.length; i++) {
				if (i == row.length - 1) {
					keys = keys + row[i].trainingMemberUserId;
					break;
				}
				keys = keys + row[i].trainingMemberUserId + ",";
			}

			$.ajax({
				type : "POST", // post提交方式默认是get
				dataType : 'json',
				url : basePath + '/api/speakhi/v1/admin/trainingMemberDeletion',
				data : {
					trainingCampId:row[0].trainingCampId,
					keys : keys
				},
				error : function(data) { // 设置表单提交出错
					$('#loading-mask').hide();
					$.messager.alert('提示', '系统出现异常,请联系管理员', 'error');
				},
				success : function(result) {
					$('#loading-mask').hide();

					if (result.code == 200) {
						$('#trainingCampGrid').datagrid('reload');
						$('#trainingMemberGrid').datagrid('reload');
						$.messager.alert('提示', "删除成功");
					} else if (result.code == 10001){
						$.messager.alert('提示', result.msg);
					}
					else{
						$.messager.alert('提示', errorCode[result.code]);
					}
				}
			});
		}
	});
}

// 加分/扣分
function addScore(type) {
	var row = $('#trainingMemberGrid').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.alert('提示', '请先选中一条数据！');
		return;
	}

	if (type == 1) {
		$('#trainingMemberOptionDiv').dialog({
			title : "加分"
		});
		$('#trainingMemberOptionReasonTitle').html("加分事由:");
		$('#trainingMemberOptionScoreTitle').html("加分分值:");
		$("#trainingMemberOptionType").val(1);
	} else if (type == 0) {
		$('#trainingMemberOptionDiv').dialog({
			title : "扣分"
		});
		$('#trainingMemberOptionReasonTitle').html("扣分事由:");
		$('#trainingMemberOptionScoreTitle').html("扣分分值:");
		$("#trainingMemberOptionType").val(0);
	}

	$("#trainingCampId").val(row[0].trainingCampId);
	$("#trainingMemberUserId").val(row[0].trainingMemberUserId);

	$("#trainingMemberOptionReason").textbox('setValue', "");
	$("#trainingMemberOptionScore").textbox('setValue', "");

	$('#trainingMemberOptionDiv').dialog('open');
}


// 提交 加分/扣分
function addScoreSubmit() {
	$('#trainingMemberOptionForm').form('submit', {
		onSubmit : function() {
			// easyui的校验
			if ($(this).form('enableValidation').form('validate')) {
				$('#trainingMemberOptionDiv').dialog('close');
				return true;
			} else {
				return false;
			}
		},
		success : function(result) {
			result = eval("(" + result + ")");
			if (result.code == 200) {
				$('#trainingMemberGrid').datagrid('reload');
				$.messager.alert('提示', "更新成功");
			} else {
				$.messager.alert('提示', errorCode[result.code]);
			}
		},
		error : function(status) {
			$.messager.alert('提示', '系统出现异常,请联系管理员', 'error' + status);
		}
	});
}