/*<![CDATA[*/
$(function(){
	var BOARD_ID = $('#BOARD_ID').val();
	var initial_bookmark_status = $('#bookmark-button').hasClass('checked');
	var current_bookmark_status = $('#bookmark-button').hasClass('checked');
	
		$('#bookmark-button').click(function(e) {
	             $(this).toggleClass('checked');
	             $(this).children('i').toggleClass('fas far'); // 꽉찬 하트
	             $(this).find('span').text(current_bookmark_status?'찜하기':'찜취소');
	             current_bookmark_status = !current_bookmark_status;
		});//북마크 클릭 이벤트 끝
		
	$(window).on('beforeunload', function(){
		if(initial_bookmark_status!==current_bookmark_status){
			var data = {
		 			boardId : BOARD_ID,
		 			boardKind : 'BookmarkList',
		 			id : '',
		 			status : current_bookmark_status 
		 		/*	userId : userId*/
			} 
			
			 $.ajax({
		  			type: 'post',
		  			url: '/clickBookmark',
		  			data : JSON.stringify(data),
					context : this,
					contentType:'application/json; charset=UTF-8',
		  			success: function (response) {
 		                  $(this).toggleClass('checked');
		            },
		            error: function (jqXHR, textStatus, errorThrown) {
		                  console.log("##### bookmark checked : Ajax ERROR #####");
		            }
		            
			  	});//ajax 끝
		}//if문 끝
	});

		
		
});//익명function END
/*]]>*/