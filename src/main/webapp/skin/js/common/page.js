var currentPage;
var totalPage; 

//前台通用分页js方法
function pageFun(pageType){
	if(currentPage==null || totalPage==null ){
		alert("请配置好分页相关参数！！！");
		return;
	}
	if(pageType=="previous"){//上一页
		if(currentPage>1){
			currentPage = Number(currentPage) - 1;
		} 
	}else if(pageType=="next"){//下一页
		if(currentPage<totalPage){
			currentPage = Number(currentPage) + 1;
		} 
	}else if(pageType=="first"){//首页
		currentPage = 1;
	}else if(pageType=="last"){//尾页
		currentPage = Number(totalPage) ;
	}
	
	var url = window.location.href;
	if(url.indexOf("page")!=-1){
		url = url.substring(0,url.indexOf("page"));
		url = url+"page="+currentPage; 	
	}else{
		if(url.indexOf("?")!=-1){//带问号
			url = url+"&page="+currentPage;
		}else{//不带问号
			url = url+"?page="+currentPage;
		}
	}
	//alert(url);
	window.location.href = url;
} 