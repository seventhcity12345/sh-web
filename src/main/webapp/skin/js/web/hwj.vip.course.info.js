/* document ready module
/*
/***********************************************************/

/* course info module
/*
/***********************************************************/
$(function() {
    $("#course-info-ul li").on({
    	mouseenter: function() {
    		$(this).find("#course-info-list").stop();
        	$(this).find("#course-info-list").animate({
        		top: -200
        	}, 200, function() {});
    	},
    	mouseleave: function() {
    		$(this).find("#course-info-list").stop();
			$(this).find("#course-info-list").animate({
        		top: 0
        	}, 200, function() {});
    	}
    });
})