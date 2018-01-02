//通用图片上传,photoType为图片的类别，fieldName为回传的field的名字
function uploadPhoto(iWidth,iHeight,photoType,fieldName){ 
	window.open('/jsp/common/photo_upload.jsp?photoType='+photoType+'&fieldName='+fieldName,'photo-upload','height='+iHeight+',width='+iWidth+',top='+(window.screen.availHeight-30-iHeight)/2+',left='+ (window.screen.availWidth-10-iWidth)/2+',toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no');
}