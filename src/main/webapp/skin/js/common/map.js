var marker,map,zoom_local = 12;
 
//创建地图
function createMap(lon,lat){
	//初始化地图对象，加载地图
	if(lon!=null&&lon!=""&&lat!=null&&lat!=""){
		map = new AMap.Map("mapContainer",{
			resizeEnable: true,
			//二维地图显示视口
			view: new AMap.View2D({
				center:new AMap.LngLat(lon,lat),//地图中心点
				zoom:zoom_local //地图显示的缩放级别
			})
		});	
	}else{
		map = new AMap.Map("mapContainer", {
		resizeEnable: true,
		view: new AMap.View2D({
			center:new AMap.LngLat(123.421978,41.813400),//地图中心点
			zoom:zoom_local //地图显示的缩放级别
		})
	});
	}
	 
	//地图中添加地图操作ToolBar插件
	map.plugin(["AMap.ToolBar"],function(){		
		var toolBar = new AMap.ToolBar(); 
		map.addControl(toolBar);		
	});
	
	//创建右键菜单
	var contextMenu = new AMap.ContextMenu();
	//右键放大
	contextMenu.addItem("放大一级",function(){
	map.zoomIn();	
	},4);
	//右键缩小
	contextMenu.addItem("缩小一级",function(){
		map.zoomOut();
	},3);
	 
	 
	//右键添加Marker标记
	contextMenu.addItem("添加标记",function(e){
		if(marker!=null&&marker.getPosition().getLng()!=null&&marker.getPosition().getLng()!=""){
			alert("无法添加两个标记！");
		}else{
			marker = new AMap.Marker({
			map:map,
			draggable:true,
			cursor:'move',
			raiseOnDrag:true,
			position: contextMenuPositon, //基点位置
			icon:"http://webapi.amap.com/images/marker_sprite.png", //marker图标，直接传递地址url
			offset:{x:-8,y:-34} //相对于基点的位置
		});
		}
	},1);
	
	contextMenu.addItem("删除标记",function(e){
		marker.setMap(null);
		marker = null;
	},2);
	
	//地图绑定鼠标右击事件——弹出右键菜单
	AMap.event.addListener(map,'rightclick',function(e){
		contextMenu.open(map,e.lnglat);
		contextMenuPositon = e.lnglat;
	});
}

//设置标记经纬度
function setMarkerLocation(lon,lat){
	clearMarker();
	if(lon!=null&&lon!=""&&lat!=null&&lat!=""){
		createMap(lon,lat);
		
		marker = new AMap.Marker({
			icon:"http://webapi.amap.com/images/marker_sprite.png",
			draggable:true,
			cursor:'move',
			raiseOnDrag:true,
			position:new AMap.LngLat(lon,lat)
		});
		marker.setMap(map);  //在地图上添加点
	} else {
		createMap();
	}
}

function getMarkerLocation(){
	if(marker!=null&&marker.getPosition()!=null){
		return marker.getPosition().getLng()+","+marker.getPosition().getLat();
	}else{
		alert("没有标记！");
		return null;
	} 
}

function clearMarker(){
	if(marker!=null){
		marker.setMap(null);
	}
	marker = null;
}