$(function(){
	var initial_bookmark_status = $('#bookmark-button').hasClass('checked');
	var status_change = false;
	
	
	$('#bookmark-button').click(function(e) {
         $(this).toggleClass('checked').children('i').toggleClass('fas far');
         $(this).find('span').text( $('#bookmark-button i').hasClass('far') ?'찜하기':'찜취소');
         $('#likeCount').text(  parseInt($('#likeCount').text())+($('#bookmark-button i').hasClass('far')?-1:1)   );
         status_change = !status_change;
	});//북마크 클릭 이벤트 끝
	
		
	$(window).on('beforeunload', function(){
		if( status_change ){
			var data = {
		 			boardId : $('#BOARD_ID').val(),
		 			userId : $('#USER_ID').val(),
		 			status : !initial_bookmark_status,
		 			likeCount : $('#likeCount').text()
			} 
			
			 $.ajax({
		  			type: 'post',
		  			url: '/clickBookmark',
		  			data : JSON.stringify(data),
					context : this,
					contentType:'application/json; charset=UTF-8',
		  			success: function (response) {
		            },
		            error: function (jqXHR, textStatus, errorThrown) {
		                  console.log("##### bookmark checked : Ajax ERROR #####");
		            }
		            
		  	});//ajax
		}//if
	});//페이지가 바뀔 때 북마크 변동사항 저장

	
	
});//익명function END
