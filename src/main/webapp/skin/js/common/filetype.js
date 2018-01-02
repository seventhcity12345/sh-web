//判断文件类型是否正确 fileType - 1 图片 2 课件类型 
function isfile(fileName, fileType) {
	var supportFile;
	if (1 == fileType) {
		supportFile = picSupportFile;
	} else if (2 == fileType) {
		supportFile = courseSupportFile;
	}

	if (fileName != null && fileName != "") {
		// lastIndexOf如果没有搜索到则返回为-1
		if (fileName.lastIndexOf(".") != -1) {
			var fileType = (fileName.substring(fileName.lastIndexOf(".") + 1,
					fileName.length)).toLowerCase();

			for (var i = 0; i < supportFile.length; i++) {
				if (supportFile[i] == fileType) {
					return true;
				} else {
					continue;
				}
			}
			return false;
		} else {
			return false;
		}
	} else {
		return true;
	}
}

// 图片类型
var picSupportFile = new Array();
picSupportFile[0] = "jpg";
picSupportFile[1] = "gif";
picSupportFile[2] = "bmp";
picSupportFile[3] = "png";
picSupportFile[4] = "jpeg";
// 课件类型
var courseSupportFile = new Array();
courseSupportFile[0] = "ppt";
courseSupportFile[1] = "pptx";
courseSupportFile[2] = "pdf";