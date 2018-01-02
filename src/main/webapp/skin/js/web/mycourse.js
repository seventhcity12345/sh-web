$(function(){
      var $liS = $(".crs-left li");
      var $bgC =  $('.bgcurrent');
      var $liW = $(".crs-left li").width();
      $liS.each(function(index){
         if($(window).width()>=768){
               if($(this).hasClass('crs-current')){
                     $bgC.animate({top:index*44+'px'}); 
                   };
                    $(this).hover(function(){
                        $bgC.stop().animate({
                                  top:index*44+'px'
                             },300);
                    },function(){
                        var $curC = $(".crs-current").index();
                        var $curP =$curC-1;
                        if($curC == 0){
                            $bgC.stop().animate({
                            top:0+'px'
                            },300)
                        }else{
                           $bgC.stop().animate({
                            top:$curP*44+'px'
                            },300)
                        }
                    });
         }
         if($(window).width()<768){
                 $('.bgcurrent').css("left","-200px");
                 if($(this).hasClass('crs-current')){
                       $bgC.animate({top:0+'px'}); 
                       $bgC.animate({left:index*$liW-10+'px'}); 
                     };
                      $(this).hover(function(){
                          $bgC.stop().animate({
                                    top:0+'px',
                                    left:index*$liW-10+'px'
                               },300);
                      },function(){
                          var $curC = $(".crs-current").index()-1;
                              $bgC.stop().animate({
                                  top:0+'px',
                                  left:$curC*$liW-10+'px'
                              },300)                          
                      });
            }
      });
    })






/************jugde****************/


 $('.crs-order').click(function(){
      clearN.clicked();   
  });

var clearN ={
    clicked: function(){
//         $('.vip-order-slide').animate({
//                        left: 0
//                    },400, "easeInOutSine", function() {
//                         $(".order-prev-active").addClass("order-prev").removeClass("order-prev-active");
//                         $(".order-prev-active-wrap").addClass("order-prev-wrap").removeClass("order-prev-active-wrap");
//                         $(".order-next").addClass("order-next-active").removeClass("order-next");
//                         $(".order-next-wrap").addClass("order-next-active-wrap").removeClass("order-next-wrap");
//                    });

                $('.order-currents').animate({
                       left:0
                },100,function(){
                      $(".olds").find('.order-gray-bg').css('background-color','transparent');
                });
                $(".olds").siblings().find('.order-gray-bg').css('background-color','#BFBFBF')
    } 
    
}
/************jugde over****************/
$('#slideAll').click(function(){
      $('.mpanel').slideUp('fast');
      $('#slideAll').hide();
      clearN.clicked();
  });

