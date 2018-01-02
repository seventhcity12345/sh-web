
define(function() {

	/**
	 * ---------------------------------------------------------
	 * 工具函数
	 * for angularjs,ctrl,services etc.
	 * ---------------------------------------------------------
	 *
	 * @returns Object
	 *
	 * @date 2016-01-11
	 * @author phil.chr
	*/
	var shUtilities = {
		/**
		 * JoinList
		 * ---------------------------------------------------------
		 * 将两个json格式的数据合并起来
		 * ---------------------------------------------------------
		 *
		 * @param a{Array} json合并最终的对象
		 * @param b{Array} json待合并项
		 * @returns {Array} 返回合并后的json list
		 *
		 * @date 2015-12-11
		 * @author phil.chr
		*/
		"JoinList" : function(a, b) {
			for (var i = 0; i < a.length; i++) {
				for (var item in b) {
					a[i][item] = b[item];
				}
				//console.log(a[i]);
			}
			return a;
		},

		/**
		 * GetValueLength
		 * ---------------------------------------------------------
		 * 获取json数据中指定key对应value值的个数
		 * ---------------------------------------------------------
		 *
		 * @param a{json} 目标json
		 * @param b{String} key值
		 * @param c{Object} 指定value
		 * @returns {Number} 指定值个数
		 *
		 * @date 2015-12-11
		 * @author phil.chr
		*/
		"GetValueLength" : function(a, b, c) {
			var count = 0;
			for (var i = 0; i < a.length; i++) {
				if (a[i][b] == c) {
					count++;
				}
			}
			return count;
		},
		
		/**
		 * DelLastItem
		 * ---------------------------------------------------------
		 * 删除指定类型的最后一项
		 * ---------------------------------------------------------
		 *
		 * @param a{json} 目标json
		 * @param b{String} key值
		 * @param c{Object} 指定value
		 *
		 * @date 2015-12-11
		 * @author phil.chr
		*/
		"DelLastItem" : function(a, b, c) {
			var len = a.length;
			
			for (var i = len-1; i >= 0; i--) {
				if (a[i][b] == c) {
					a.splice(i ,1);
					break;
				}
			}
		},
	};

	return shUtilities;

});
