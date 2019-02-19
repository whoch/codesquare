/*<![CDATA[*/
$(function(){
	var BOARD_ID = $('#BOARD_ID').val();
	var initial_bookmark_status = $('#bookmark-button').hasClass('checked');
	
/*	checkUnload*/
	console.log(initial_bookmark_status);
	
		$('#bookmark-button').click(function(e) {

	             $(this).toggleClass('checked');
	             $(this).children().toggleClass('fa-heart');
	             $(this).children().toggleClass('fa-heart-o');

			var data = {
		 			boardId : BOARD_ID,
		 			boardKind : 'BookmarkList',
		 			id : ''
		 		/*	userId : userId*/
			} 
			
			 $.ajax({
		  			type: 'post',
		  			url: '/clickBookmark',
		  			data : JSON.stringify(data),
					context : this,
		  			contentType: 'application/json; charset=UTF-8',
		  			success: function (response) {
 		                  $(this).toggleClass('checked');
		                  console.log("##### ajax 성공 #####");
		      			console.log(this);
		                  console.log(response);
		            },
		            error: function (jqXHR, textStatus, errorThrown) {
		                  console.log("##### bookmark checked : Ajax ERROR #####");
		            }
			  	})//ajax 끝
		});//북마크 클릭 이벤트 끝
		
	$(window).on('beforeunload', function(){
			return "ON beforeunload";
		});

/*	var checkUnload = true;
    $(window).on("beforeunload", function(){
        if(checkUnload) return "이 페이지를 벗어나면 작성된 내용은 저장되지 않습니다.";
    });
*/
	
		$(window).off('beforeunload', function(){
			return  alert("OFF beforeunload");
		});

		
		
});//익명function END
/*]]>*/