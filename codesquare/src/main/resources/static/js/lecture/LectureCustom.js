var boardId = $("[name=boardId]").val();



// ajax 댓글 목록 불러오기
function reviewList() {
	$.ajax({
		url:'/learn/list',
		type:'get',
		data:{'boardId' : boardId}
	}).done(function(data){
		var reviewContent =""; 
		 $.each(data, function(key, value){
		     	reviewContent+="<li id=\""+value.id+"\" class=\"list-group-item\" style=\"border: none;\">";
		     	reviewContent+="<input type=\"hidden\" name=\"id\" value=\""+value.id+"\"/>";
		     	reviewContent+="<div class=\"row review-container\">";
		     	reviewContent+="<div class=\"col-sm-2 review-writerInfo\">";
		     	reviewContent+="<img class=\"review-writer-thumbnail\" src=\""+value.thumbnail+"\" alt=\"유저썸네일\" />";
		     	reviewContent+="<div class=\"review-writer\">";
		     	reviewContent+="<h5 class=\"name\" >"+value.nickName+"</h5>";
		     	reviewContent+="<h6 class=\"writeDate\" >"+value.writeDate+"</h6></div></div>";
		     	reviewContent+="<div class=\"col-sm-8 review-content\">";
		     	reviewContent+="<p class=\"review-content-text\">"+value.content+"</p></div>";
		     	reviewContent+="<div class=\"col-sm-2 review-heart\">";
		     	reviewContent+="<img src=\"/static/images/lectureImages/reviewHeart.png\"alt=\"좋아요\"> ";
		     	reviewContent+="<label class=\"heart-count\" >"+value.like+"</label>";
		     	reviewContent+="<div id=\""+value.id+"btn\" class=\"btn-group btn-review-control\" style=\"border-radius: 3px; border: 1px solid #d8d8d8; right: 5px;\">";
		     	reviewContent+="<button type=\"button\" value=\""+value.id+"\" class=\"btn btn-modify btn- btn-light\">📝</button>";
		     	reviewContent+="<button type=\"button\" value=\""+value.id+"\" class=\"btn btn-delete btn-light\">✖</button></div></div></div></li>";
		     });
		 
//		 $(".review-container").append(reviewContent);
		 $(".review-container").html(reviewContent);
		 $("[name=boardKindId]").val(data[0].boardKindId);
	}).fail(function(data){
		alert("실패");
	})

}
//댓글 작성
function insertReview(review){
	$.ajax({
		url:'Comment/insert',
		type:'POST',
		data:review
	}).done(function(data){
		if(data==1){
			reviewList();
			$("[name=content]").val('');
		}
	}).fail(function(data){
		if(data!=1){
			alert("Load Review Fail");
		}
	});
}

//수정 댓글 업데이트
function updateReview(id,content){
	$.ajax({
		url:'Comment/update',
		type:'POST',
		data:{"id":id,"content":content}
	}).done(function(data){
			reviewList();
	}).fail(function(data){
			alert("Load Review Update Fail");
	});
}



//댓글 삭제
function deleteReview(id){
	$.ajax({
		url:'Comment/delete',
		type:'GET',
		data:{"id":id}
	}).done(function(data){
			reviewList();
	}).fail(function(data){
			alert("Load Review Fail");
	});
}
//


$(document).ready(function() {
	reviewList();
	// 후기작성부분 Heart Rating 기능
	$("[name=like]").click(function() {
		var count = $(this).val();
		$(".review-regist>.heart-count").text(count);
	})
	$(".review-regist>img").click(function() {
		$(".review-regist>.heart-count").text("0");
	})

	// 후기작성 등록 기능
	$(".btn-regist").click(function() {
		var review = $("[name=reviewForm]").serialize();
		insertReview(review);
	})
	//후기 수정 등록 버튼 기능
	$(document).on('click','.btn-modify-regist', function(){
		var id=$(this).data('id');
		var content=$("[name=modify-content]").val();
		updateReview(id,content)
	})
	
	// 후기 삭제&수정 버튼 감지
	$(document).on('click', '.btn-delete, .btn-modify', function(){
		var id=$(this).val();
		var btn=$(this).attr('class');
		if(btn.indexOf('btn-delete')!=-1){
			deleteReview(id);
		}else{
			var txt=$("#"+id+" .review-content-text").text();
			var txtarea="<textarea rows=\"3\" cols=\"20\" name=\"modify-content\" maxlength=\"300\" required=\"required\" placeholder=\"강의를 들으셨다면 후기를 남겨주세요!\">"+txt+"</textarea>"
			$("#"+id+" .review-content").html(txtarea);
			$("#"+id+"btn").html("<button data-id=\""+id+"\" type=\"button\" class=\"btn btn-modify-regist btn-primary\" style=\"height: 40px;font-size: 19px;\">작성하기</button>");
		}
	})
	

});