/* teaching philosophy logic
/***********************************************************/
$(function() {
	/* for pc */
	$("#preview").hover(function() {
		$("#class_img").attr("src", "skin/images/class.png");
		$("#oto_img").attr("src", "skin/images/oto.png");

		$("#preview_img").attr("src", "skin/images/preview_active.png");

		$(".teaching-info ul li").removeClass("teaching-info-active");
		$(".teaching-info ul li:eq(0)").addClass("teaching-info-active");
	});
	$("#class").hover(function() {
		$("#preview_img").attr("src", "skin/images/preview.png");
		$("#oto_img").attr("src", "skin/images/oto.png");

		$("#class_img").attr("src", "skin/images/class_active.png");

		$(".teaching-info ul li").removeClass("teaching-info-active");
		$(".teaching-info ul li:eq(1)").addClass("teaching-info-active");
	});
	$("#oto").hover(function() {
		$("#class_img").attr("src", "skin/images/class.png");
		$("#preview_img").attr("src", "skin/images/preview.png");

		$("#oto_img").attr("src", "skin/images/oto_active.png");

		$(".teaching-info ul li").removeClass("teaching-info-active");
		$(".teaching-info ul li:eq(2)").addClass("teaching-info-active");
	});

	/* for mobile */
	$("#preview_m").hover(function() {
		$("#class_m_img").attr("src", "skin/images/class.png");
		$("#oto_m_img").attr("src", "skin/images/oto.png");

		$("#preview_m_img").attr("src", "skin/images/preview_active.png");

		$(".teaching-info ul li").removeClass("teaching-info-active");
		$(".teaching-info ul li:eq(0)").addClass("teaching-info-active");
	});
	$("#class_m").hover(function() {
		$("#preview_m_img").attr("src", "skin/images/preview.png");
		$("#oto_m_img").attr("src", "skin/images/oto.png");

		$("#class_m_img").attr("src", "skin/images/class_active.png");

		$(".teaching-info ul li").removeClass("teaching-info-active");
		$(".teaching-info ul li:eq(1)").addClass("teaching-info-active");
	});
	$("#oto_m").hover(function() {
		$("#class_m_img").attr("src", "skin/images/class.png");
		$("#preview_m_img").attr("src", "skin/images/preview.png");

		$("#oto_m_img").attr("src", "skin/images/oto_active.png");

		$(".teaching-info ul li").removeClass("teaching-info-active");
		$(".teaching-info ul li:eq(2)").addClass("teaching-info-active");
	});
})
/* real time msg logic
/***********************************************************/
$(function() {

	/* slide news for pc */
	var newsTimer = setInterval(RunSlideNews, 4000);

	$(".slide-btn-top").click(function() {
		if ($(".msg-item-active").index() <= 0) {		
			var isNowActive = $(".msg-item-active").index();
			$(".msg-item li").removeClass("msg-item-active");
			$(".msg-item li:eq("+(isNowActive+9)+")").addClass("msg-item-active");

			$(".msg-item").css("top", $(".msg-item").position().top - 10*36);
			$(".msg-item").animate({top: $(".msg-item").position().top + 36}, 200);
		} else {
			var isNowActive = $(".msg-item-active").index();
			$(".msg-item li").removeClass("msg-item-active");
			$(".msg-item li:eq("+(isNowActive-1)+")").addClass("msg-item-active");
			$(".msg-item").animate({top: $(".msg-item").position().top + 36}, 200);
		}
	});
	
	$(".slide-btn-bottom").click(function() {
		RunSlideNews();
	});

	$(".msg-info").mouseenter(function() {
		clearInterval(newsTimer);
	});
	$(".msg-info").mouseleave(function() {
		newsTimer = setInterval(RunSlideNews, 4000);
	});

	function RunSlideNews() {
		if ($(".msg-item-active").index() >= 9) {
			var isNowActive = $(".msg-item-active").index();
			$(".msg-item li").removeClass("msg-item-active");
			$(".msg-item li:eq("+(isNowActive-9)+")").addClass("msg-item-active");

			$(".msg-item").css("top", $(".msg-item").position().top + 10*36);
			$(".msg-item").animate({top: $(".msg-item").position().top - 36}, 200);
		} else {
			var isNowActive = $(".msg-item-active").index();
			$(".msg-item li").removeClass("msg-item-active");
			$(".msg-item li:eq("+(isNowActive+1)+")").addClass("msg-item-active");

			$(".msg-item").animate({top: $(".msg-item").position().top - 36}, 200);
		}
	}

	/* slide news for mobile*/
	var newsMobileTimer = setInterval(RunSlideMobileNews, 4000);
	function RunSlideMobileNews() {
		if ($(".msg-item-active-m").index() >= 9) {
			var isNowActive = $(".msg-item-active-m").index();
			$(".msg-item-m li").removeClass("msg-item-active-m");
			$(".msg-item-m li:eq("+(isNowActive-9)+")").addClass("msg-item-active-m");

			$(".msg-item-m").css("top", $(".msg-item-m").position().top + 10*36*3);
			$(".msg-item-m").animate({top: $(".msg-item-m").position().top - 36*3}, 200);
		} else {
			var isNowActive = $(".msg-item-active-m").index();
			$(".msg-item-m li").removeClass("msg-item-active-m");
			$(".msg-item-m li:eq("+(isNowActive+1)+")").addClass("msg-item-active-m");

			$(".msg-item-m").animate({top: $(".msg-item-m").position().top - 36*3}, 200);
		}
	}
})