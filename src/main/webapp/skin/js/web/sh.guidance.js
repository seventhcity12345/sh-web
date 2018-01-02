/* speakhi guidance js
/*********************************************************/
$(function() {
    var shGui = {
        init: function() {
            //shGui.guiStepBtnEvent();
            shGui.guiRunningSlide();
        },
        initData: {
            "curIndex": 0,
            "totalItem": 5, //总步骤数目
            "itemHeight": 480, //步骤所占单位高度
            "winHeight": $(window).height(),
            "isBottom": false
        },
        guiRunning: function(index) {
            var winHeight = shGui.initData.winHeight;
            var itemHeight = shGui.initData.itemHeight;
            var value = $("#shGuiContent li:eq("+index+")").offset().top;
            $("body,html").animate({
                scrollTop: value - (winHeight-itemHeight)/2
            }, "ease-in");
        },
        guiRunningSlide: function() {
            var guiSwiper = new Swiper('.swiper-container', {
                setWrapperSize :true,
            	autoplay: 6000,//可选选项，自动滑动
                mousewheelControl : true,
                autoplayDisableOnInteraction : false,
                keyboardControl : true,
                pagination : '.swiper-pagination',
                paginationClickable :true,
                onInit: function(guiSwiper) {
                    $("#shGuiBtn li:eq(0)").addClass("curItem");
                },
                onSlideChangeStart: function(guiSwiper) {
                    $("#shGuiBtn li").removeClass("curItem");
                    $("#shGuiBtn li:eq("+guiSwiper.activeIndex+")").addClass("curItem");
                }
            });

            $("#shGuiBtn li").on("click", function() {
                var index = $(this).index();
                curIndex = index;
                $("#shGuiBtn li").removeClass("curItem");
                $(this).addClass("curItem");
                guiSwiper.slideTo(index, 500, false);//切换到指定slide，速度为1秒
            });
        },
        guiStepBtnEvent: function() {
            var curIndex = shGui.initData.curIndex;
            var totalItem = shGui.initData.totalItem;

            $("#shGuiBtn li").on("click", function() {
                var index = $(this).index();
                curIndex = index;
                shGui.guiRunning(index);
            });

            // var totalVal = 0;
            // var times = 3;
            // $('body').on('mousewheel', function(event) {
            //     //console.log(event.deltaX, event.deltaY, event.deltaFactor);
            //     totalVal += event.deltaFactor;
            //     if (totalVal == event.deltaFactor * times) {
            //         times += 3;
            //
            //         if (event.deltaY > 0) {     //往上滚动页面
            //             shGui.initData.isBottom = false;
            //             if (curIndex === 0) {
            //                 return;
            //             }
            //             shGui.guiRunning(--curIndex);
            //             //console.log("上："+curIndex);
            //         } else {                    //往下滚动页面
            //             if (!shGui.initData.isBottom) {
            //                 if (curIndex === totalItem - 1) {
            //                     shGui.guiRunning(curIndex);
            //                     shGui.initData.isBottom = true;
            //                 } else {
            //                     shGui.guiRunning(curIndex++);
            //                 }
            //             }
            //             //console.log("下："+curIndex);
            //         }
            //     } else {
            //         return false;
            //     }
            // });
        }
    };

    shGui.init();
});
