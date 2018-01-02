//选择价格策略下拉框触发
function selectRedeemPackagePriceName(){
	//清空课程包id
	$('#coursePackageId').combobox('setValue', '');
	
	var packagePriceNameId = $('#coursePackagePriceId').combobox('getValue');
	
	//获取课程包
	var listUrl = getUrl(basePath + '/coursePackage/selectlist','keyId='+packagePriceNameId);
	$('#coursePackageId').combobox('reload', listUrl);	
}

//批量生成兑换码
function submitAndCreateRedeemCode(){
	//判断一下生效日期是否有效
	var startTime = $('#activityStartTime').datetimebox('getValue');
	var endTime = $('#activityEndTime').datetimebox('getValue');
	if(new Date(startTime).getTime() >= new Date(endTime).getTime()){
		$.messager.alert('提示','生效结束日期必须大于生效开始日期！');
		return;
	}
	//提交生成兑换码信息，并下载生成的兑换码
	$('#createRedeemCodeForm').form('submit',{
        onSubmit:function(){
            //easyui的校验
            if($(this).form('enableValidation').form('validate')){
                $('#createRedeemCodeDiv').dialog('close');
                return true;
            }else{
                return false;
            }
        },
        success:function(result) {
        	result = eval("("+result+")");
        	if(result.code == 10001){
        		$.messager.alert('提示',result.msg);
        	}
        	else if(result.code == 200){
        		$.messager.alert('提示','请求成功，生成的兑换码将下载至本地！');
        		var blob = new Blob([result.data], {type: 'text/csv'});
           	 	var fileName = "兑换码.csv";
           	 	if (window.navigator.msSaveOrOpenBlob) {
           	        navigator.msSaveBlob(blob, fileName);
           	    } else {
           	        var link = document.createElement('a');
           	        link.href = window.URL.createObjectURL(blob);
           	        link.download = fileName;
           	        link.click();
           	        window.URL.revokeObjectURL(link.href);
           	    }
        	}
        	else{
        		$.messager.alert('提示',errorCode[result.code]);
        	}
        	 
        },
        error:function(status){
            $.messager.alert('提示','系统出现异常,请联系管理员','error'+status);
        }
    });
}

//打开生成兑换码弹窗
function createRedeemCode() {
	$('#activityName').textbox('setValue','');
	$('#redeemCodeNum').textbox('setValue','');
	$('#coursePackagePriceId').combobox('setValue','');
	$('#coursePackagePriceId').combobox('reload');
	$('#coursePackageId').combobox('setValue','');
	$('#activityStartTime').datetimebox('setValue','');
	$('#activityEndTime').datetimebox('setValue','');
	$('#createRedeemCodeDiv').dialog('open');
}