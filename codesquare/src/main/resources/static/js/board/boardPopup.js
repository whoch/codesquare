/**
 * 
 */

        $(function() {
        var userId2=$("a").data("name");
        console.log(userId2)
        $("#sendMsg").click(function(){
   		 var popupX = (window.screen.width / 2) - (555 / 2);
   		 var popupY= (window.screen.height /2) - (570 / 2);
   		window.open( '/message/sendMessage/'+userId2, '내 쪽지함', 'width=555, height=555, status=no, location=no, scrollbars=yes, left='+ popupX + ', top='+ popupY + ', screenX='+ popupX + ', screenY= '+ popupY);

   	 	});
        $("#viewUser").click(function(){
        	
      		 var popupX = (window.screen.width / 2) - (555 / 2);
      		 var popupY= (window.screen.height /2) - (570 / 2);
      		window.open('/member/'+userId2, '내 쪽지함', 'width=555, height=555, status=no, location=no, scrollbars=yes, left='+ popupX + ', top='+ popupY + ', screenX='+ popupX + ', screenY= '+ popupY);

      	 });
        });