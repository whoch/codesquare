/**
 * 
 */



$(".nav .col").mouseenter(function(){
	
	  $("ul.dropdown").css("display", "block");

	});
$(".nav .col").mouseleave(function(){
	  $("ul.dropdown").css("display", "none");

	});

$("#myMessage").click(function(){
	 var popupX = (window.screen.width / 2) - (555 / 2);
	 var popupY= (window.screen.height /2) - (570 / 2);
	window.open('/message/receivedMessage', '내 쪽지함', 'width=555, height=555, status=no, location=no, scrollbars=yes, left='+ popupX + ', top='+ popupY + ', screenX='+ popupX + ', screenY= '+ popupY);

});

