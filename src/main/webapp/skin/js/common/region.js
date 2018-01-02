function initFirstRegion(province,city){
	$.ajax({          
	   type:"POST",   //post提交方式默认是get
	   dataType:'json', 
	   url:basePath+'/region/firstRegionList', 
	   data : {
		   
	   },
	   error:function(data) {      // 设置表单提交出错 
		   alert('系统出现异常,请联系管理员');
	   },
	   success:function(result) {//提交成功
		   for(var i = 0;i< result.data.length;i++){
			   $("#location_province").append("<option value='"+result.data[i].region_id+"'>"+result.data[i].region_name+"</option>");
		   } 
		   
	   	   if(province!=null&&province!=""&&province!="null"){
	   		   $("#location_province").val(province);
	   	   }
	   	   //$("#location_province_temp").val($("#location_province").find("option:selected").text());
	   	   
	   	   selectSecondRegion(city,1);
	   }            
	});
}


//选中默认项
function selectSecondRegion(city,is_default){
	//alert($("#location_province").find("option:selected").val());
	var selectVal = $('select[name=location_province]').val(); 
	if(selectVal==""){ 
		$("#location_city").empty(); 
	}else{ 
		$.ajax({ 
			type:"POST",//post提交方式默认是get 
			dataType:'json', 
			url:basePath+'/region/secondRegionList', 
			data : { 
				region_id :$("#location_province").find("option:selected").val()
			}, error:function(data) {// 设置表单提交出错 
				alert("系统出现异常,请联系管理员"); 
			}, success:function(result) { 
				var data = result.data; 
				$("#location_city").empty(); 
				//$("#location_city_temp").val(""); 
				
				for(var i = 0 ; i < data.length ;i++){ 
					$("#location_city").append("<option value='"+data[i].region_id+"'>"+data[i].region_name+"</option>"); 
				} 
				
				if(is_default=='1'){
					if(city!=null&&city!=""&&city!="null"){
						$("#location_city").val(city);//
					}
				}
				//$("#location_city_temp").val($("#location_city").find("option:selected").text());
			} 
		}); 
	} 
}