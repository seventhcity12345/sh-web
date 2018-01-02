/* document ready module
/*
/***********************************************************/
$(function() {
    $("#service-protocol").click(function() {
        $(".service-protocol").slideToggle(500);
    });
})

/* protocol is active or not
/*
/***********************************************************/
$(function() {
    actBehave();
    $("#p-protocol-cbox").find("input[type='checkbox']").on("click",function() {
    	$("#p-protocol-cbox").find("input[type='checkbox']").trigger("click");
        actBehave();
    });
    $("#p-protocol-cbox").on("click",function() {
    	$("#p-protocol-cbox").find("input[type='checkbox']").trigger("click");
        actBehave();
    });

    function actBehave() {
        if (!$("#p-protocol-cbox").find("input[type='checkbox']").prop("checked")) {
            $("#buy-course").addClass("dark-gray-bg").removeClass("yellow-bg").addClass("cursor-clear");
        } else {
            $("#buy-course").addClass("yellow-bg").removeClass("dark-gray-bg").removeClass("cursor-clear");
        }
    }
})