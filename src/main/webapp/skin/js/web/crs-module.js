
/* main module
/* 
/***********************************************************/
$(function() {
	$('.mpanel').hide();
	$('.crs-main').each(function(){
		 var $crsC = $(this).find('.crs-order');
		 $crsC.click(function(){;
             	$('.mpanel .course-order-item').html('');
			   var $that=$(this);
			   $that.parent().find('.mpanel').slideDown();
			   $that.parent().siblings().find('.mpanel').slideUp();
			   $('#slideAll').show();
		 });
		 $(this).delegate('.lips',"click",function(){
             $('.mpanel').slideUp('fast');
             $('.mpanel .course-order-item').html('');
             $('#slideAll').hide();
         });
	});
})
