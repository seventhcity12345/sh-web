/* document ready
/***********************************************************/
$(function() {

    /* init ready
    /***********************************************************/
    $(".crs-left ul li").on("click", function() {
        $(".crs-left ul li").removeClass("crs-current");
        $(this).addClass("crs-current");
    });

    /* event part
    /***********************************************************/
    // var $liS = $(".crs-left li");
    // var $bgC = $('.bgcurrent');
    // var $liW = $(".crs-left li").width();
    // var sHeight = $(".crs-left li").height();
    //
    // $liS.each(function(index) {
    //     if ($(window).width() >= 768) {
    //         if ($(this).hasClass('crs-current')) {
    //             $bgC.animate({
    //                 top: $(".crs-left li:eq(" + index + ")").position().top +
    //                     'px'
    //             });
    //         }
    //         $(this).hover(function() {
    //             $bgC.stop().animate({
    //                 top: $(".crs-left li:eq(" + index + ")").position()
    //                     .top +
    //                     'px'
    //             }, 300);
    //         }, function() {
    //             var $curC = $(".crs-current").index() - 1;
    //             //var $curP = $curC;
    //             $bgC.stop().animate({
    //                 top: $(".crs-left li:eq(" + $curC + ")").position()
    //                     .top +
    //                     'px'
    //             }, 300);
    //         });
    //     }
    //     if ($(window).width() < 768) {
    //         $('.bgcurrent').css("left", "-200px");
    //         if ($(this).hasClass('crs-current')) {
    //             $bgC.animate({
    //                 top: 0 + 'px'
    //             });
    //             $bgC.animate({
    //                 left: index * $liW - 10 + 'px'
    //             });
    //         }
    //         $(this).hover(function() {
    //             $bgC.stop().animate({
    //                 top: 0 + 'px',
    //                 left: index * $liW - 10 + 'px'
    //             }, 300);
    //         }, function() {
    //             var $curC = $(".crs-current").index() - 1;
    //             $bgC.stop().animate({
    //                 top: 0 + 'px',
    //                 left: $curC * $liW - 10 + 'px'
    //             }, 300);
    //         });
    //     }
    // });

});
