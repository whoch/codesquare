var boardId = $("[name=boardId]").val();


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

// 후기 수정 버튼 감지
$("")



// ajax 댓글 목록 불러오기
function reviewList() {
	$.ajax({
		url:'/learn/list',
		type:'get',
		data:{'boardId' : boardId}
	}).done(function(data){
		var reviewContent =""; 
		 $.each(data, function(key, value){
		     	reviewContent+="<li class=\"list-group-item\" style=\"border: none;\">";
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
		     	reviewContent+="<div class=\"btn-group btn-review-control\" style=\"border-radius: 3px; border: 1px solid #d8d8d8; right: 5px;\">";
		     	reviewContent+="<button type=\"button\" class=\"btn btn-modify btn- btn-light\">✖</button>";
		     	reviewContent+="<button type=\"button\" class=\"btn btn-delete btn-light\">📝</button></div></div></div></li>";
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

//댓글 삭제
function deleteReview(id){
	
}



$(document).ready(function() {
	reviewList();
});