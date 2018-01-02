
require.config({
	baseUrl : "skin/js/web",
    paths : {
        "jquery" : ["jquery-1.11.2","jquery-1.9.1.min","http://apps.bdimg.com/libs/jquery/1.11.3/jquery.min.js"],
        "angular" : "angular.min"
    },
	shim : {
		"angular" : {
			exports : "angular"
		}
	},
	deps : ["jquery","angular"]
});

// var config = {
// 	baseUrl : "skin/js/web",
//     paths : {
//         "jquery" : ["jquery-1.11.2","jquery-1.9.1.min","http://apps.bdimg.com/libs/jquery/1.11.3/jquery.min.js"],
//         "angular" : "angular.min"
//     },
// 	shim : {
// 		"angular" : {
// 			exports : "angular"
// 		}
// 	},
// 	deps : ["jquery","angular"]
// };
