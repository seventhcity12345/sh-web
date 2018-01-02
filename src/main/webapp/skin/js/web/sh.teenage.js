/* speakhi teenage js
/*********************************************************/
$(function() {
    /* mBanner swiper event
    /*****************************************************/
    var mBannerSwiper = new Swiper('.swiper-container', {
	        autoplay: 5000,//可选选项，自动滑动
            effect: 'fade',
            fade: {
                crossFade: false,
            },
            lazyLoading: true,
            loop: true,
            autoplayDisableOnInteraction : false
    });

    /* marketing QQ or livecom
    /*****************************************************/
    $("#teenageList li").on("click", function() {
        $("#lcServices a").trigger("click");
    });
});
