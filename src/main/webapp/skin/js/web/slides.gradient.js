/* document ready module
/* banner sliding js
/*
/*	-- layout style --
/*	<div class="banner">       // position:relative --
		<div class="prev"></div>          // position:absolute --
		<ul>					//  position:absolute --
			<li class="banner-active">anything</li>    // position: absolute --
			<li class>anything</li>
			<li class>anything</li>
			<li class>anything</li>
		</ul>
		<div class="next"></div>
	</div>
/*
/***********************************************************/

// $(function() {
// 	getBannerHeight();
// })
$(".banner .prev,.banner .next").css("visibility", "hidden");
setTimeout(function() {
    getBannerHeight();
}, 500);

function getBannerHeight() {
	var imgHeight = $(".banner-mobile ul li.banner-m-active img").height();
	var aHeight = $(".banner-mobile ul li.banner-m-active a").outerHeight(true);
	$(".banner-mobile ul").height(imgHeight + aHeight);

	var bannerHeight = $(".banner-active>img").height();
	//var bannerMHeight = $(".banner-m-active").height();

	var bannerBtnTop = (bannerHeight - $(".banner .prev").height()) / 2;
	var bannerMBtnTop = ($(".banner-m-active>img").height() - $(".banner-mobile .prev").height()) / 2;
	$(".banner .prev,.banner .next").css("top", bannerBtnTop);
	$(".banner-mobile .prev,.banner-mobile .next").css("top", bannerMBtnTop);
	$(".banner .prev,.banner .next").css("visibility", "visible");
}

$(window).resize(function() {
	setTimeout(getBannerHeight(), 200);
});

$(function() {
	var count = $(".banner ul").children().length;

	if (count == 1) {
		return;
	}

	var curIndex = 0;
	var bannerTimer = null;

	$(".banner-active").css("display", "block");
	$(".banner-m-active").css("display", "block");

	RunningBanner();

	$(".banner,.banner-mobile").on({
		mouseenter: function() {
			clearInterval(bannerTimer);
			$(this).stop();
			$(".prev,.next").stop();
			$(".prev,.next").fadeTo(500, .5);
		},
		mouseleave: function() {
			RunningBanner();
			$(this).stop();
			$(".prev,.next").stop();
			$(".prev,.next").fadeOut(500);
		}
	});
	$(".prev,.next").on({
		mouseenter: function() {
			$(this).stop();
			$(this).fadeTo(500, 1);
		},
		mouseleave: function() {
			$(this).stop();
			$(".prev,.next").fadeTo(500, .5);
		}
	});

	$(".prev").click(function() {
		RunningChange(false);
	});
	$(".next").click(function() {
		RunningChange(true);
	});

	function RunningBanner() {
		curIndex = $(".banner-active").index() || $(".banner-m-active").index();
		curIndex += 1;
		bannerTimer = setInterval(function() {
			if (curIndex >= count - 1) {
				curIndex = -1;
			}
			RunningChange(true);
		}, 5000);
	}

	function RunningChange(isPlus) {
		if (isPlus) {
			if (curIndex >= count - 1) {
				curIndex = -1;
			}
			//console.log("banner"+Number(curIndex+1));
			$(".banner-active").fadeOut(1000);
			$(".banner-m-active").fadeOut(1000);
			$(".banner-active").removeClass("banner-active");
			$(".banner-m-active").removeClass("banner-m-active");
			$(".banner ul li:eq(" + (curIndex + 1) + ")").fadeIn(1000).addClass("banner-active");
			$(".banner-mobile ul li:eq(" + (curIndex + 1) + ")").fadeIn(1000).addClass("banner-m-active");
			curIndex++;
		} else {
			if (curIndex <= 0) {
				curIndex = count;
			}
			//console.log("banner"+Number(curIndex-1));
			$(".banner-active").fadeOut(1000);
			$(".banner-m-active").fadeOut(1000);
			$(".banner-active").removeClass("banner-active");
			$(".banner-m-active").removeClass("banner-m-active");
			$(".banner ul li:eq(" + (curIndex - 1) + ")").fadeIn(1000).addClass("banner-active");
			$(".banner-mobile ul li:eq(" + (curIndex - 1) + ")").fadeIn(1000).addClass("banner-m-active");
			curIndex--;
		}
	}

});
