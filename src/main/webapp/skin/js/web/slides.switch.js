/* document ready module
/* 
/****************************************/
/*  -- layout style --
/* 	<div id="slides">                 // position: relative --
		<div class="slides_container">
			<div class="slides-item slides-item-active"></div>     // position: absolute --
			<div class="slides-item"></div>
			<div class="slides-item"></div>
			<div class="slides-item"></div>
		</div>
		<ul class="slides-item-tag">
			<li><a class="slides-tag-aactive"></a></li>
			<li><a class></a></li>
			<li><a class></a></li>
			<li><a class></a></li>
		</ul>
	</div>
/***********************************************************/
$(function() {
    $(".slides-item").each(function() {
        $(".slides-item:eq(" + $(this).index() + ")").css("left", $(this).index() * 100 + "%");
    });

    $(".slides-item-tag").css("width",($(".slides-item-tag").children().length)*30+"px");
    $(".slides_container").css("height",($(".slides-item").height()+"px"));
    $(window).resize(function() {
    	$(".slides_container").css("height",($(".slides-item").height()+"px"));
    });
})

/* class slide module
/*
/***********************************************************/
$(function() {
	$(".slides-item:eq(0)").addClass("slides-item-active");
    $(".slides-item-tag li:eq(0) a").addClass("slides-tag-active");

    $(".slides-item-tag li a").click(function() {
        var curIndex = $(".slides-tag-active").parent().index();
        var targetIndex = $(this).parent().index();
        if (targetIndex == curIndex) {
            return;
        } else if (targetIndex > curIndex) {
            RunningSlides(curIndex, targetIndex, true);
        } else {
            RunningSlides(curIndex, targetIndex, false);
        }
    });

    $("#slides").on({
        mouseenter: function() {
            clearInterval(slidesTimer);
        },
        mouseleave: function() {
        	initValue = $(".slides-item-active").index();
        	AutoRunningSlides();
        }
    });

    var slidesTimer = null;
    var initValue = 0;

	AutoRunningSlides();

    function AutoRunningSlides() {
    	if ($(".slides_container").children().length == 1) {
    		return;
    	};
        var count = $(".slides-item-tag").children().length - 1;
        slidesTimer = setInterval(function() {
            if (initValue > count) {
                initValue = 0;
            };
            if (initValue == count) {
                var targetIndex = 0;
            } else {
                var targetIndex = initValue + 1;
            }

            RunningSlides(initValue, targetIndex, true);
            initValue++;
        }, 3000);
    }

    function RunningSlides(curIndex, targetIndex, isToRight) {
        var negValue = -1;
        var value = 1;

        if (isToRight) {
            value = -1;
        };
        $(".slides-item:eq(" + targetIndex + ")").css("left", value * negValue * 100 + "%");

        $(".slides-item:eq(" + targetIndex + ")").animate({
            left: 0
        }, 500);
        $(".slides-item-active").animate({
            left: value * 100 + '%'
        }, 500);

        $(".slides-tag-active").removeClass("slides-tag-active");
        $(".slides-item-active").removeClass("slides-item-active");
        $(".slides-item-tag li:eq(" + targetIndex + ") a").addClass("slides-tag-active");
        $(".slides-item:eq(" + targetIndex + ")").addClass("slides-item-active");
    }
})