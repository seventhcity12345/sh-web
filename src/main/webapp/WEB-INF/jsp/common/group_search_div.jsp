<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<style type="text/css">
    /*双击行时弹出框的表格样式*/
    .fieldTb{padding:3px;}
    .fieldTb td,.fieldTb th{height:26px;}
    .fieldTb th{width:100px; font-weight:normal; text-align:right;}
    .fieldTb td input{width:100px;}
    .fieldTb td input.address{width:563px;}
    
    /*组合查询时的样式*/
    .groupSearchInfo{height:22px; line-height:22px;}
    .divBorder{height:120px; width:556px; border:1px solid #ccc; padding:5px; position:relative;}
    #conditionDiv{height:90px; width:485px; position:absolute; overflow:auto;}
    ul.conditionItem{height:26px; margin:0; padding:0;}
    .conditionItem li{float:left; list-style:none; margin-right:10px;}
    .conditionItem li.colValue input{width:120px;}
    .conditionItem li.cb{padding-top:3px;}
    .conditionItem li.cb input{cursor:pointer;}
    
    #opDiv{width:60px; height:100px; position:absolute; right:10px; top:25px;}
</style>
<script type="text/javascript"> 
	var cons ;
	$(function () {
		AddUl();//初始化
	});
	
	//刷新组合查询弹框（用于一个页面可能有多个需要组合查询的dategrid）
	function groupSearchNew(newGridName){
		gridName = newGridName;
		AddUl();//初始化
		delSearchItemAll();
		groupSearch();
	}
	

	 function AddUl(){
		//弹出来的时候，要查找datagrid有多少列，把每一列的字段filed和title取出来组成下拉框
        var columnFields = $('#'+gridName).datagrid('getColumnFields').slice(1);   //注意这里把ids字段去掉         
        var tits = [];
    	var fields = [];
        var columnFieldCount = 0;
        if (columnFields) {
        	for (var i = 0; i < columnFields.length; i++) {
                var hid = $('#'+gridName).datagrid('getColumnOption', columnFields[i]).hidden; //列属性中的hidden值
                var unableGroupSearch = $('#'+gridName).datagrid('getColumnOption', columnFields[i]).unableGroupSearch; //列属性中的hidden值
                var ambiguous = $('#'+gridName).datagrid('getColumnOption', columnFields[i]).ambiguous; //列属性中的hidden值
                var dbColumnName = $('#'+gridName).datagrid('getColumnOption', columnFields[i]).dbColumnName; //对应表中列的名字
                if (hid != true && unableGroupSearch != true) {
                    var tit = $('#'+gridName).datagrid('getColumnOption', columnFields[i]).title;
                    var columnField = $('#'+gridName).datagrid('getColumnOption', columnFields[i]).field;
                    if(ambiguous != null && ambiguous != 'undefined' && ambiguous != ''){
                    	columnField = ambiguous + '.' + columnField;
                    }
                    if(dbColumnName != null && dbColumnName != 'undefined' && dbColumnName != ''){
                    	columnField = dbColumnName;
                    }
                    tits.push(tit);
                    fields.push(columnField);
                    columnFieldCount++;
                }
            }
        }
        var optionStr = '<select style="width:130px" name="selFieldName" class="easyui-combobox" data-options="editable:false">';
		optionStr += "<option value=''>请选择</option>";
        for (var m = 0; m < columnFieldCount; m++) {
            optionStr += '<option value="' + fields[m] + '">' + tits[m] + '</option>';
        }
        optionStr += '</select>';
        var itemStr = '<ul class="conditionItem"><li class="colName">' + optionStr + '</li><li class="calculate">';
        itemStr += '<select style="width:60px" name="selCalculate"><option value="=">等于</option><option value=">=">大于</option><option value="<=">小于</option><option value="like">模糊</option><option value="not">不等于</option></select>';
        itemStr += '</li><li class="colValue"><div name="divColValue"><input type="text" name="txtColValue" /></div><div name="dateDivColValue"><input id="dateColValue" name="dateColValue" type="text"/></div><div name="dateTimeDivColValue"><input id="dateTimeColValue" name="dateTimeColValue" type="text"/></div><input typt="text" name="hidColValue" style="width:134px" /></li><li class="chose">';
        itemStr += '<select style="width:60px" name="selChose"><option value="and">并且</option><option value="or">或者</option></select>';
        itemStr += '</li><li class="cb"><input type="checkbox" name="cbConditionItem" /></li></ul>';
        var index=$("#conditionDiv ul").length;
        $(itemStr).appendTo("#conditionDiv");
		var $curUl=$("#conditionDiv ul").eq(index);
		var $parentColValue=$curUl.find('div[name="divColValue"]');  //下拉框父级div
		var $parentDateColValue=$curUl.find('div[name="dateDivColValue"]');  //日期框父级div
		var $textInput=$curUl.find('input[name="hidColValue"]');  //文本框
		var $txtColValue=$curUl.find("input[name='txtColValue']");   //下拉框
		var $dateColValue=$curUl.find("input[name='dateColValue']");   //日期框
		
		var $parentDateTimeColValue=$curUl.find('div[name="dateTimeDivColValue"]');  //日期时间框父级div
		var $dateTimeColValue=$curUl.find("input[name='dateTimeColValue']");   //日期框
		
		$parentColValue.hide();
		$parentDateColValue.hide();
		$parentDateTimeColValue.hide();
		$textInput.show();
		
		$curUl.find("select[name='selFieldName']").combobox({
		    onSelect: function () {
		    	$txtColValue.combobox({
		    		url:'',
		            valueField: 'value',
		            textField: 'label',
		            editable: false,
		            width: 140
		        });
		    	//格式化查询框
		        var val = $(this).combobox('getValue');
		        if (val == "create_date") {//创建时间
		            $textInput.hide();
					$parentDateColValue.show();
		        }else if (val == "core.create_date") {//多表的创建时间
		            $textInput.hide();
					$parentDateColValue.show();
		        }else if (val == "start_order_time") {//支付时间(就是合同开始时间)
		            $textInput.hide();
					$parentDateColValue.show();
		        } else if (val == "order_status") {//订单状态
		            $textInput.hide();
					$parentColValue.show();
					//合同状态（1.已拟定,2.已发送,3.已确认,4.支付中,5.已支付,6.已过期,7.已终止）
					$txtColValue.combobox('loadData',[{"label":"已拟定","value":"1"},
					                                  {"label":"已发送","value":"2"},
					                                  {"label":"已确认","value":"3"},
					                                  {"label":"支付中","value":"4"},
					                                  {"label":"已支付","value":"5"},
					                                  {"label":"已过期","value":"6"},
					                                  {"label":"已终止","value":"7"}]);
		        } else if (val == "department_name") {//营业厅
		            $textInput.hide();
					$parentColValue.show();
					$txtColValue.combobox('reload','<%=request.getContextPath()%>/yytCombo');
		        } else if (val == "roomtype_name") {//住房类型
		            $textInput.hide();
					$parentColValue.show();
					$txtColValue.combobox('reload','<%=request.getContextPath()%>/roomTypeCombo');
		        }  else if (val=="is_student") {//是与否集合
		            $textInput.hide();
					$parentColValue.show();
					$txtColValue.combobox('loadData',[{"label":"否","value":"0"},
					                                  {"label":"是","value":"1"}]);
		        } else if (val == "status") {//住户状态
		            $textInput.hide();
					$parentColValue.show();
					$txtColValue.combobox('loadData',[{"label":"正常","value":"0"},
					                                  {"label":"暂停","value":"1"}]);
		        } else if(val == "noticeType"){//公告类型
		        	$textInput.hide();
					$parentColValue.show();
					$txtColValue.combobox('loadData',[{"label":"公告","value":"0"},
					                                  {"label":"banner","value":"1"}]);
		        } else if (val == "organtype_name") {//单位类型 
		            $textInput.hide();
					$parentColValue.show();
					$txtColValue.combobox('reload','<%=request.getContextPath()%>/organtypeCombo');
		        } else if (val == "parent_name") {//上级单位,一级单位
		            $textInput.hide();
					$parentColValue.show();
					$txtColValue.combobox('reload','<%=request.getContextPath()%>/organCombo');
		        } else if (val == "certificatetype_name") {//证件类型
		            $textInput.hide();
					$parentColValue.show();
					$txtColValue.combobox('reload','<%=request.getContextPath()%>/certificatetypeCombo');
		        } else if (val == "organ_name") {//二级单位
		            $textInput.hide();
					$parentColValue.show();
					$txtColValue.combobox('reload','<%=request.getContextPath()%>/organ2Combo');
		        } else if (val == "adminlevel_name") {//行政级别
		            $textInput.hide();
					$parentColValue.show();
					$txtColValue.combobox('reload','<%=request.getContextPath()%>/adminlevelCombo');
		        } else if (val == "title_name") {//职称
		            $textInput.hide();
					$parentColValue.show();
					$txtColValue.combobox('reload','<%=request.getContextPath()%>/titleCombo');
		        } else if (val == "jobstatus_name") {//在职情况
		            $textInput.hide();
					$parentColValue.show();
					$txtColValue.combobox('reload','<%=request.getContextPath()%>/jobstatusCombo');
		        } else if (val == "jobattribute_name") {//用工性质
		            $textInput.hide();
					$parentColValue.show();
					$txtColValue.combobox('reload','<%=request.getContextPath()%>/jobattributeCombo');
		        } else if (val == "stafftype_name") {//人员类别
		            $textInput.hide();
					$parentColValue.show();
					$txtColValue.combobox('reload','<%=request.getContextPath()%>/stafftypeCombo');
		        } else if (val == "hutype_name") {//户类型
		            $textInput.hide();
					$parentColValue.show();
					$txtColValue.combobox('reload','<%=request.getContextPath()%>/hutypeCombo');
		        } else if (val == "specialtype_name") {//特殊群体
		            $textInput.hide();
					$parentColValue.show();
					$txtColValue.combobox('reload','<%=request.getContextPath()%>/specialtypeCombo');
		        } else if (val == "maritalstatus_name") {//婚姻状况
		            $textInput.hide();
					$parentColValue.show();
					$txtColValue.combobox('reload','<%=request.getContextPath()%>/maritalstatusCombo');
		        } else if (val == "customertype_name") {//费用来源or费用类型
		            $textInput.hide();
					$parentColValue.show();
					$txtColValue.combobox('reload','<%=request.getContextPath()%>/customerCombo');
		        } else if (val == "startTime" || val == "start_time" || val == "end_order_time" || val == "lastFollowTime" || val == "startOrderTime") {//Enity 开始时间 2016-5-5 修改1v1排课组合查询用 seven.gz
		        	//val == "lastFollowTime" || val == "startOrderTime" Add By Felix 2017.6.15 在本月Follow情况页面，组合查询时若选择的查询条件是最后一次Follow时间或合同开始时间的话，要求弹出日历控件
		        	$textInput.hide();
		        	$parentColValue.hide();
					$parentDateTimeColValue.show();
				}else if (val == "endTime" || val == "tsc.createDate"){//Enity 结束时间
					$textInput.hide();
					$parentColValue.hide();
					$parentDateTimeColValue.show();
				}else if (val == "isSubscribe" || val == "subscribeStatus"){//Enity 是否已订
					$textInput.hide();
					$parentDateTimeColValue.hide();
					$parentColValue.show();
					$txtColValue.combobox('loadData',[{"label":"否","value":"0"},
					                                  {"label":"是","value":"1"}]);
				}else if (val == "isConfirm"){//Enity 确认出席
					$textInput.hide();
					$parentDateTimeColValue.hide();
					$parentColValue.show();
					$txtColValue.combobox('loadData',[{"label":"否","value":"0"},
					                                  {"label":"是","value":"1"}]);
					
				}else if (val == "courseType" || val == "course_type" || val == "tsc.courseType" || val == "teacherCourseType"){// 课程类型
					$textInput.hide();
					$parentDateTimeColValue.hide();
					$parentColValue.show();
					$txtColValue.combobox('setValue','');
					$txtColValue.combobox({ //课程类型type 不统一 所以先写死 日后看情况在改掉
						url:'${ctx}/courseType/findCourseTypeByParam',
							valueField: 'courseType',
				            textField: 'courseTypeChineseName',
					});
					
				}else if (val == "categoryType"){// 课程体系
					$textInput.hide();
					$parentDateTimeColValue.hide();
					$parentColValue.show();
					$txtColValue.combobox('setValue','');
					$txtColValue.combobox('loadData',[{"label":"商务英语","value":"category_type1"},
					                                  {"label":"通用英语","value":"category_type2"}]);
		        } else {
		            $parentColValue.hide();
		            $parentDateColValue.hide();
		            $txtColValue.combobox('setValue','');
		            $parentDateTimeColValue.hide();
		            $textInput.show();
		        }
		    }
		});
		
		$dateColValue.datebox({
			editable: false,
			width: 140
		});
		
		$dateTimeColValue.datetimebox({
			editable: true,
			width: 140
		});
		
	 }//AddUl结束
    	
	 function doGroupSearch() {   //组合查询确定按钮事件
	        var fieldNameArr = $("input[name='selFieldName']");   //字段名称数组
	        var calculateArr = $("select[name='selCalculate']");   //等于、大于、小于、模糊
	        var valueArr = $("input[name='txtColValue']");         //值(下拉框)
	        var textValueArr = $("input[name='hidColValue']");     //值(文本框)
	        var dateValueArr = $("input[name='dateColValue']");     //值(日期框)
	        var choseArr = $("select[name='selChose']");           //逻辑或、与
	        var dateTimeValueArr = $("input[name='dateTimeColValue']");     //值(日期框)
	        cons = "[";
	        
	        for (var i = 0; i < fieldNameArr.length; i++) {
	       	  if (fieldNameArr[i].value.length > 0 && calculateArr[i].value.length > 0 && (valueArr[i].value.length >0||textValueArr[i].value.length >0||dateValueArr[i].value.length >0||dateTimeValueArr[i].value.length >0) && choseArr[i].value.length > 0) {  
	       	  	 
	       		var tempValue = null;
	       		var conditionValue = $(calculateArr[i]).val();
	       		 
	       		if(valueArr[i].value.length!=0){
	       			tempValue = $(valueArr[i]).val();
	       		 }else if(textValueArr[i].value.length!=0){
	       			tempValue = $(textValueArr[i]).val();
	       		 }else if(dateValueArr[i].value.length!=0){
	       			if(conditionValue==">="){
	       				tempValue = $(dateValueArr[i]).val()+" 00:00:00";
	       			} else if (conditionValue=="<="){
	       				tempValue = $(dateValueArr[i]).val()+" 23:59:59";
	       			} else {
	       				tempValue = $(dateValueArr[i]).val();
	       			}
	       		 }else if(dateTimeValueArr[i].value.length!=0){
	       			tempValue = $(dateTimeValueArr[i]).val();
	       		 }
	       		 
	       		 cons+="{field:'"+$(fieldNameArr[i]).val()+"', ";
	       		 cons+=	"condition:'"+conditionValue+"', ";
	       		 cons+=	"value:'"+tempValue+"', ";
	       		 cons+=	"logicConn:'"+$(choseArr[i]).val()+"'}, ";
	            } else {
	            	//$.messager.alert('提示','请输入查询条件!','info');
	            	$.messager.alert('提示','请设置查询条件!','info');
	       			return;
	            }
	        }
	        cons += "]";
	        
			var queryParams = {};	
			queryParams["cons"] = cons; 
			$('#'+gridName).datagrid('load',queryParams); 
	        $('#'+gridName).datagrid('options').pageNumber = 1;
	        var page = $('#'+gridName).datagrid('getPager');
	        page.pagination({ pageNumber: 1 });

	        $('#groupSearchDiv').dialog('close');
	    }
	 
	 
     //组合查询
     function groupSearch() {
		//$("#conditionDiv").html("");
		
        $('#groupSearchDiv').dialog('open');
    }
     function addSearchItem() {    //组合查询弹出框的增加按钮事件
         AddUl();
     }
     function delSearchItem() {   //组合查询弹出框删除按钮事件
         var selectedItems = $("input[name='cbConditionItem'][type='checkbox']:checked", "#conditionDiv");
         if (selectedItems.length > 0) {
             for (var i = 0; i < selectedItems.length; i++) {
                 var parentUl = $(selectedItems[i]).parent().parent();
                 parentUl.remove();
             }
         }
     }
     //删除全部按钮事件
     function delSearchItemAll(){
    	 $("input[name='cbConditionItem']").each(function(){
    		 var parentUl = $(this).parent().parent();
    		 parentUl.remove();
    	 });
    	 doGroupSearch();
    	 addSearchItem();
     }
     
	</script> 
<!-- 组合查询窗口开始 -->
    	<div id="groupSearchDiv" name="groupSearchDiv" class="easyui-dialog" title="组合查询" 
    		style="width:630px;height:300px;padding:5px 10px 5px;" 
    		modal="true" resizable="false" closed="true"
    		data-options="iconCls:'icon-search',
    		buttons: [{
                    text:'确定',
                    iconCls:'icon-ok',
                    handler:function(){
                        doGroupSearch();
                    }
                },{
                    text:'取消',
                    iconCls:'icon-redo',
                    handler:function(){
                    	$('#groupSearchDiv').dialog('close');
                    }
                }] 
    		" >  
    		
    		<form id="groupSearchForm" name="groupSearchForm" method="post"> 
	            <div class="groupSearchInfo">1)请指定过滤条件，单击"增加"或"删除"按钮增减列表项;</div>
                <div class="groupSearchInfo">2)单击"确定"按钮以指定条件进行过滤</div>
                <div class="divBorder">
                    <div style="padding-bottom:3px">　　　　列名：　　　　　　运算：　　　　　　值：　　　　　　逻辑：</div>
                    <div id="conditionDiv">
                    </div>
                    <div id="opDiv">
                        <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addSearchItem()">增加</a>
						<div style="height:3px;"></div>
	                    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="delSearchItem()">删除</a>
	                    <div style="height:3px;"></div>
	                    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="delSearchItemAll()">清空</a>
						
                    </div>
                </div>
	        </form>  
    		<form id="exportForm" name="exportForm" method="post">
    			<input type="hidden" id="cons" name="cons"/>
    		</form><!-- 用于excel导出 -->
    	</div>  
        <!-- 组合查询窗口结束 --> 
        <!-- 通用详情页面开始 --> 
        <div id="defaultDetailDiv" name="defaultDetailDiv" class="easyui-dialog" title="详细信息查看"
              style="width:600px;height:363px;padding:10px;"
              modal="true" resizable="true" closed="true" maximizable="true"
              data-options="iconCls:'icon-edit'" >
        </div>
        <!-- 通用详情页面结束 --> 