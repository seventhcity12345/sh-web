$.extend($.messager.defaults,{
    ok:"确定",  
    cancel:"取消"  
}); 
        
Date.prototype.format = function(format) {
	/*   
	 * format="yyyy-MM-dd hh:mm:ss";   
	 */
	var o = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S" : this.getMilliseconds()
	}

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}

$(document).ready(function(){ 
	
});

//查看详情通用页面
function showDefaultDetailDiv(detailUrl,gridName){
	if(gridName==null){
		gridName = 'dataGrid1';
	}
	
	var row = $('#'+gridName).datagrid('getSelections');
	if(row.length!=1){
		$.messager.alert('提示','请选中一条数据','info');
		return;
	}
	
	var key_id = row[0].key_id;
	
	$('#defaultDetailDiv').dialog({
	    href: basePath+"/admin/"+detailUrl+"/"+key_id
	    //href: basePath+"/admin/config/detail/"+key_id
	});
	
	$('#defaultDetailDiv').dialog('open');
}

function formatDateyyyyMMdd(val,rec) {
    var date = new Date(val);
    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
 }

function formatDate(val,rec){
	if(val==null || val==''){
		return '';
	}
	return new Date(val).format("yyyy-MM-dd hh:mm");
}

function formatFloat(src, pos){
	return Math.round(src*Math.pow(10, pos))/Math.pow(10, pos);
}

//变更类型类型
function formatSex(type){
	var result = "";
	if(type==0){
		result = "女";
	}else if(type==1){
		result = "男";
	}
	return result;
}  

//格式化是，否
function formatYesOrNo(type){
	var result = "";
	if(type){
		result = "是";
	}else{
		result = "否";
	}
	return result;
}

//格式化是，否
function formatShowOrNoShow(type){
	var result = "";
	if(type){
		result = "是";
	}else{
		result = "未上";
	}
	return result;
}

//日期格式化(yyyy-MM-dd)
function formatDateDay(val, rec) {
	if (val == null || val == '') {
		return '';
	}
	return new Date(val).format("yyyy-MM-dd");
}

//datagrid中转换图片格式
function formatterImg(value,row,index){
	if('' != value && null != value)
  	value = '<img style="width:100px; height:100px" src="' + value + '">';
  	return  value;
}

//全选反选
//参数:selected:传入this,表示当前点击的组件
//treeMenu:要操作的tree的id；如：id="userTree"
function treeChecked(selected, treeMenu) {
	if (selected.checked) {
		var nodes = $('#'+treeMenu).tree('getChecked','unchecked');
		for ( var i = 0; i < nodes.length; i++) {
			$('#' + treeMenu).tree('check', nodes[i].target);//将得到的节点选中
		}
	} else {
		cleanTreeSelected(treeMenu);
	}
}


//GRID加载回调函数
function successLoad(){
	// 按钮初期化
	$(".easyui-linkbutton").each(function(){				
		$(this).linkbutton();				
	})
	
	$(".easyui-menubutton").each(function(){		
		$(this).menubutton();		
	}) 
}

//清空tree选中数据
function cleanTreeSelected(treeMenu){
	var nodes = $('#'+treeMenu).tree('getChecked');
	for ( var i = 0; i < nodes.length; i++) {
		$('#' + treeMenu).tree('uncheck', nodes[i].target);
	}
}

//统一的导出方法
function exportExcel(gridName){
	if(gridName==null||gridName==''||gridName=='null'){
		gridName = "dataGrid1";
	}
	
	if ($("#"+gridName).datagrid("getData").rows.length == 0) {
		$.messager.alert('提示','没有数据，无法导出！','info');
		return;
	} 
	
	// 确认导出
	$.messager.confirm("操作提示", "如果数据很多，导出会非常慢，是否继续？", function (data) {
        if (data) {
        	var tempUrl = $('#'+gridName).datagrid('options').url;
        	$("#exportForm").attr("action", basePath+tempUrl+'Export');
        	$("#cons").val(cons);
        	$('#exportForm').submit();
        } else {
       	 	
        }
	});
}

//loading遮罩
function ajaxLoading(){   
    $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");   
    $("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});   
}
    	
function ajaxLoadEnd(){   
     $(".datagrid-mask").remove();   
     $(".datagrid-mask-msg").remove();               
}

//表单校验规则
$.extend(
$.fn.validatebox.defaults.rules,
{
    //密码验证
    equals : {
        validator : function(value, param) {
            return value == $(param[0]).val();
        },
        message : '密码输入不一致.'
    },
    // 移动手机号码验证
    mobile : {// value值为文本框中的值
        validator : function(value) {
            var reg = /^1[3|4|5|8|9]\d{9}$/;
            return reg.test(value);
        },
        message : '输入手机号码格式不准确.'
    },
    combo : {
        validator : function(value) {
            if (value) {
                return true;
            } else {
                return false;
            }
        },
        message : '不能为空'
    },
    // 验证电话号码  
    phone : {
        validator : function(value) {
            return /^\d{3}-\d{4,10}$|^\d{4}-\d{3,9}$|^1\d{10}$|^\d{8,14}$/i
                    .test(value);
        },
        message : '请输入8-14位数字或者-'
    },
    email : {// 验证邮箱
		validator : function(value) {
			return  /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/i
					.test(value);
		},
		message : '不是有效邮箱，邮箱示例：webi@webi.com.cn'
	},
    // 电话号码或手机号码  
    phoneAndMobile : {
        validator : function(value) {
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i
                    .test(value)
                    || /^(13|15|18)\d{9}$/i.test(value);
        },
        message : '电话号码或手机号码格式不正确'
    },
    // 验证整数或小数  
    intOrFloat : {
        validator : function(value) {
            return /^\d+(\.\d+)?$/i.test(value);
        },
        message : '请输入数字，并确保格式正确'
    },
    // 验证整数  
    integer : {
        validator : function(value) {
            return /^[+]?[1-9]+\d*$/i.test(value);
        },
        message : '请输入整数'
    },
    length : {
		validator : function(value, param) {
			var len = $.trim(value).length;
			return len >= param[0] && len <= param[1];
		},
		message : '输入内容长度必须介于{0}和{1}之间'
	},
	range : {
		validator : function(value, param) {
			return value >= param[0] && value <= param[1];
		},
		message : '输入的数字必须介于{0}和{1}之间'
	},
    // 验证是否包含空格和非法字符  
    unnormal : {
        validator : function(value) {
            return /.+/i.test(value);
        },
        message : '输入值不能为空和包含其他非法字符'
    },
    //验证车牌号码
    carNo : {
        validator : function(value) {
            return /^[\u4E00-\u9FA5][\da-zA-Z]{6}$/.test(value);
        },
        message : '车牌号码无效（例：粤B12350）'
    },
    // 用户密码验证(只能包括 _ 数字 字母)
    account : {// param的值为[]中值
        validator : function(value, param) {
            if (value.length < param[0]
                    || value.length > param[1]) {
                $.fn.validatebox.defaults.rules.account.message = '长度必须在'
                        + param[0] + '至' + param[1] + '范围';
                return false;
            } else {
                if (!/(?=.*?[a-zA-Z])(?=.*?[0-9])[a-zA-Z0-9]{8,}$/
                        .test(value)) {
                    $.fn.validatebox.defaults.rules.account.message = '必须包含数字和字母.';
                    return false;
                } else {
                    return true;
                }
            }
        },
        message : '只能数字、字母、下划线组成'
    },
    // 汉字验证
    CHS : {
        validator : function(value) {
            return /^[\u0391-\uFFE5]+$/.test(value);
        },
        message : '只能输入汉字'
    },
    // 验证身份证  
    idcard : {
    	validator : function(value) {
            return /^(\d{18,18}|\d{15,15}|\d{17,17}(x|X))$/.test(value);
        },
        message : '请输入合法的身份证号'
    },
    englishName : {// 验证英语
		validator : function(value) {

			return /^([A-Za-z]+\s?)*[A-Za-z]$/i.test(value);
		},
		message : '请输入英文'
	},
    //韦博专用，用户名
    username : {
    	validator : function(value) {
            return /^[\u4e00-\u9fa5]{0,15}$/.test(value);
        },
        message : '请输入合法的名字'
    }
});


var errorCode = { 
		/* common code */
	    //请求成功
	    200: '请求成功',
	    //内部服务器错误
	    500: '内部服务器错误',

	    /* 系统错误（11001-11000） */
	    //参数验证错误（此code需要弹出msg的错误信息）
	    10001: '',

	    /* 后台管理错误（11001-15000） */
	    //公告栏（11001-11100）
	    //公告更新失败
	    11001: '更新失败',
	    //公告删除失败
	    11002: '公告删除失败',
	    //公告查询失败
	    11003: '公告查询失败',

	    /* 前台学生操作错误（20001-未定） */
	    //预约相关（20001-30000）
	    //预约课程:已上过该topic的lecture课程，是否再次出席
	    20001: '已上过该topic的lecture课程，是否再次出席',
	    //预约课程:类型不正确出错
	    20002: '类型不正确出错',
	    //预约课程:您已在该时段预约了其他课程，请重新选择时间
	    20003: '您已在该时段预约了其他课程，请重新选择时间',
	    //预约课程:您当前可预约课程数量已满，请完成课程后再进行预约!
	    20004: '您当前可预约课程数量已满，请完成课程后再进行预约!',
	    //预约课程:没有英文名
	    20005: '没有英文名',
	    //预约课程:必须在预约时间范围内才能预约
	    20006: '必须在预约时间范围内才能预约',
	    //预约课程:抱歉，该节课预约人数已满，请预约其他课程！
	    20008: '抱歉，该节课预约人数已满，请预约其他课程！',
	    //预约课程:环讯老师被占用
	    20009: '环讯老师被占用',
	    //取消预约:预约数据不存在
	    20010: '预约数据不存在',
	    //取消预约:非法取消预约,取消预约时间不正确!
	    20011: '非法取消预约,取消预约时间不正确!',
	    //取消预约:取消环讯老师出错
	    20012: '取消环讯老师出错',
	    20013: 'WebEx房间不存在',
	    //预约课程:未购买课程类型,暂无法进入
	    21001: '未购买课程类型,暂无法进入',

	    /* 老师（30001-31000） */
	    //老师:老师数据不存在
	    30001: '老师数据不存在',

	    /* 老师排课时间（31001-32000） */
	    //老师时间:老师时间数据不存在
	    31001: '老师时间数据不存在',
	    //老师时间:老师已经被预约
	    31002: '老师已经被预约',

	    /* 老师签课时间（32001-33000） */
	    //老师签课时间：新增老师签课时间错误
	    32001: '新增老师签课时间错误',
	    //老师签课时间：删除老师签课时间错误
	    32002: '删除老师签课时间错误',
	    //老师签课时间：第三方来源教师暂时不支持签课
	    32003: '第三方来源教师暂时不支持签课',

	    /* 课程相关（40001-41000） */
	    //课程数据不存在！
	    40001: '课程数据不存在！',

	    //课程课件：41001-42000
	    //课件数据错误，没有课件完成记录！
	    41001: '课件数据错误，没有课件完成记录！',

	    //合同相关（50001-60000）
	    //合同：合同相关信息不存在
	    50001: '合同相关信息不存在',
	    //合同：您没有该课程的可用课时数，无法预约！
	    50002: '您没有该课程的可用课时数，无法预约！',
	    
	    70003:'已经升到最高级别！',

	    70004:'您的操作已超时，请重新登录后操作',
	    	
		70006:'该身份证号已存在！'
};
