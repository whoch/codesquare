 var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(function() {
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});

var boardId = $("[name=boardId]").val();
var nickName=$("[name=nickName]").val();
var userId=$("#userId").val();
var writerThumbnail=$(".commnet-writer-thumbnail").attr('src');
// ajax 댓글 목록 불러오기
function reviewList() {
	$.ajax({
		url:'/Comment/review/list',
		type:'get',
		data:{'boardId' : boardId}
	}).done(function(data){
		var reviewContent =""; 
		 $.each(data, function(key, value){
		     	reviewContent+="<li id=\""+value.id+"\" class=\"list-group-item\" style=\"border: none;\">";
		     	reviewContent+="<input type=\"hidden\" name=\"id\" value=\""+value.id+"\"/>";
		     	reviewContent+="<div class=\"row review-container\">";
		     	reviewContent+="<div class=\"col-sm-2 review-writerInfo\">";
		     	reviewContent+="<img class=\"review-writer-thumbnail\" src=\""+value.profileImagePath+"\" alt=\"유저썸네일\" />";
		     	reviewContent+="<div class=\"review-writer\">";
		     	reviewContent+="<h5 class=\"name\" >"+value.nickName+"</h5>";
		     	reviewContent+="<h6 class=\"writeDate\" >"+value.writeDate+"</h6></div></div>";
		     	reviewContent+="<div class=\"col-sm-8 review-content\">";
		     	reviewContent+="<p class=\"review-content-text\">"+value.content+"</p></div>";
		     	reviewContent+="<div class=\"col-sm-2 review-heart\">";
		     	reviewContent+="<img src=\"/static/images/lectureImages/reviewHeart.png\"alt=\"좋아요\"> ";
		     	reviewContent+="<label class=\"heart-count\" >"+value.like+"</label>";
		     	reviewContent+="<div id=\""+value.id+"btn\" class=\"btn-group btn-review-control\" style=\"border-radius: 3px; border: 1px solid #d8d8d8; right: 5px;\">";
		     	if(value.userId==userId){
			     	reviewContent+="<button type=\"button\" value=\""+value.id+"\" class=\"btn btn-modify btn- btn-light\">📝</button>";
			     	reviewContent+="<button type=\"button\" value=\""+value.id+"\" class=\"btn btn-delete btn-light\">✖</button></div></div></div></li>";
		     	}
		     });
		 
// $(".review-container").append(reviewContent);
		 $(".review-container").html(reviewContent);
	}).fail(function(data){
		alert("실패");
	})

}
// 댓글 작성
function insertReview(review){
	$.ajax({
		url:'/Comment/review/insert',
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

// 수정 댓글 업데이트
function updateReview(id,content){
	$.ajax({
		url:'/Comment/review/update',
		type:'POST',
		data:{"id":id,"content":content}
	}).done(function(data){
			reviewList();
	}).fail(function(data){
			alert("Load Review Update Fail");
	});
}



// 댓글 삭제
function deleteReview(id){
	$.ajax({
		url:'/Comment/review/delete',
		type:'GET',
		data:{"id":id}
	}).done(function(data){
			reviewList();
	}).fail(function(data){
			alert("Load Review Fail");
	});
}





// QNA댓글 불러오기
function qnaCommentList(){
	$.ajax({
		url:'/Comment/qna/list',
		type:'get',
		data:{'boardId' : boardId}
	}).done(function(data){
		var cContent="<ul id=\"comments-list\" class=\"comments-list \">";
		$.each(data,function(key,value){
			if(!value.parentId){
		        cContent+="<li id=\"lrnQa-"+value.id+"\"data-parentId=\"0\" ><!--메인 댓글--><div class=\"comment-main-level\">";
			}else{
				cContent+="<li id=\"lrnQa-"+value.id+"\"data-parentId=\""+value.parentId+"\"><!--메인 댓글--><div class=\"comment-main-level reply-list\">";
			}
		        cContent+="<!-- Avatar --><div class=\"comment-avatar\"><img src=\""+value.profileImagePath+"\" alt=\"유저썸네일\"></div>";
		        cContent+="<div class=\"comment-box\">";
		        cContent+="<div class=\"comment-head\">";
		        cContent+="<h6 class=\"comment-name";
		        if(value.userId==userId){
		        	cContent+=" by-author";
		        }
		        cContent+="\"><a href=\"member/"+value.id+"\">"+value.nickName+"</a></h6><span>"+value.writeDate+"</span>";
		        if(value.deleteStatus==0){
		        	cContent+="<span class=\"comment-icon\"><i class=\"fa fa-reply\" data-status=\"true\" title=\"댓글달기\"></i>";
		        	if(value.userId==userId){
		        		cContent+="<i class=\"fas fa-edit\" title=\"수정하기\"></i><i class=\"fa fa-trash\" title=\"삭제하기\"></i>";
		        	}
		        }		        
		        cContent+="</span></div><div class=\"comment-content\"><p class=\"comment-content-text\">"+value.content+"</p></div></div></div>";
		        cContent+="</li>";
		})
		cContent+="</ul>";
		 
		 $(".comments-container").html(cContent);
	}).fail(function(data){
		alert("실패");
	})
}

// QNA댓글 작성하기
function insertQNAComment(comment){
	$.ajax({
		url:'/Comment/qna/insert',
		type:'POST',
		data:comment
	}).done(function(data){
		if(data==1){
			$("[name=content]").val('');
			qnaCommentList();
		}
	}).fail(function(data){
		if(data!=1){
			alert("Load Review Fail");
		}
	});
}

// QNA댓글 수정하기
function updateQNAComment(id,content){
	$.ajax({
		url:'/Comment/qna/update',
		type:'POST',
		data:{"id":id,"content":content}
	}).done(function(data){
			qnaCommentList();
	}).fail(function(data){
			alert("Load Review Fail");
	});
	
}

// QNA댓글 삭제하기
function deleteQNAComment(id){
	$.ajax({
		url:'/Comment/qna/delete',
		type:'GET',
		data:{"id":id}
	}).done(function(data){
		if(data==2){
			$("#lrnQa-"+id+" .comment-icon").remove();
		}
		qnaCommentList();
	}).fail(function(data){
			alert("Load Review Fail");
	});
}

//찜하기 메서드
function addBookmark(){
	$.ajax({
		url:'/learn/bookmark',
		type:'POST',
		data:{"userId":userId, "boardId":boardId}
	}).done(function(data){
		if(data==1){
			$(".modal-body>span").text("찜하기가 완료되었습니다.");
		}else{
			$(".modal-body>span").text("이미 찜하신 강의입니다.");
		}
		$("#myModal").modal();
	}).fail(function(data){
			alert("Load Review Fail");
	});
}




$(document).ready(function() {
	reviewList();
	qnaCommentList();
	// 후기작성부분 Heart Rating 기능
	$("[name=like]").click(function() {
		var count = $(this).val();
		$(".review-regist>.heart-count").text(count);
	})
	$(".review-regist>img").click(function() {
		$(".review-regist>.heart-count").text("0");
	})

	// 후기작성 등록 기능
	$(".btn-review-regist").click(function() {
		var review = $("[name=reviewForm]").serialize();
		insertReview(review);
	})
	// 후기 수정 등록 버튼 기능
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
	
	//댓글 등록버튼 클릭
	$(".btn-comment-regist").click(function(){
		/*var comment=$("[name=commentForm]").serialize();
		insertQNAComment(comment);*/
		var cContent=$(".comment-content>[name=content]").val();
		var result=checkKeywordFilter(cContent);
		if(result.length==0){
			var comment=$("[name=commentForm]").serialize();
			insertQNAComment(comment);
		}else if(result.length>0){
			alert(result+" 단어는 적절치 못합니다. 바르고 고운말을 작성해 주세요.");
		}
	})
	
	/**
	 * 대댓글 작성(fa-reply),수정(fa-edit),삭제(fa-trash) 감지 이벤트 리스너 
	 */
	$(document).on('click','.fa-edit,.fa-trash, .fa-reply',function(){
		var self=$(this);
		var obj=self.closest('.comment-box').children('.comment-content');
		var objClass=self.attr('class');
		var id=self.closest('li').attr('id').split('-')[1];
		
		if(objClass.indexOf('fa-edit')!=-1){//수정
			var txt=obj.children('p').text();
			var txtarea="<textarea rows=\"3\" cols=\"20\" name=\"modify-content\" maxlength=\"300\" required=\"required\" placeholder=\"바르고 고운말을 사용해요!!\">"+txt+"</textarea>";
			var icon="<i data-id class=\"far fa-check-square fa-lg btn-comment-modify\" title=\"등록\" aria-hidden=\"true\"></i><i class=\"fa fa-ban fa-lg\" title=\"취소\" aria-hidden=\"true\"></i>";
			obj.html(txtarea);
			$(obj).prev().children('.comment-icon').html(icon);
		}
		if(objClass.indexOf('fa-trash')!=-1){//삭제
			deleteQNAComment(id);
		}
		if(objClass.indexOf('fa-reply')!=-1){//리플
			var parentId=$("#lrnQa-"+id).data('parentid');
			if(parentId==0){
				parentId=id;
			}
			var replyContent="";
			replyContent+="<li id=\"reply\"><!--메인 댓글--><form name=\"comment-reply-form\"><div class=\"comment-main-level reply-list\">";
			replyContent+="<input type=\"hidden\" name=\"boardId\" value=\""+boardId+"\" />";
			replyContent+="<input type=\"hidden\" name=\"boardKindId\" value=\"LrnQa\" />";
			replyContent+="<input type=\"hidden\" name=\"userId\" value=\""+userId+"\" /> ";
			replyContent+="<input type=\"hidden\" name=\"nickName\"	value=\""+nickName+"\" />";
			replyContent+="<input type=\"hidden\" name=\"parentId\"	value=\""+parentId+"\" />";
	        replyContent+="<!-- Avatar --><div class=\"comment-avatar\"><img src=\""+writerThumbnail+"\" alt=\"유저썸네일\"></div>";
	        replyContent+="<div class=\"comment-box\">";
	        replyContent+="<div class=\"comment-head\">";
	        replyContent+="<h6 class=\"comment-name\"><a href=\"member/\" >"+userId+"</a></h6>";
	        replyContent+="<span class=\"comment-icon\"><i class=\"far fa-check-square fa-lg btn-regist\" title=\"등록\" aria-hidden=\"true\"></i><i class=\"fa fa-ban fa-lg\" title=\"취소\" aria-hidden=\"true\"></i></span></div>";
	        replyContent+="<div class=\"comment-content\"><textarea rows=\"3\" cols=\"20\" name=\"content\" maxlength=\"300\" required=\"required\" placeholder=\"바르고 고운말을 사용해요!!\"></textarea></div></div></div>";
	        replyContent+="</form></li>";
	        $(this).closest("li").append(replyContent);
	        self.remove();
		}

	})
	
	/**
	 * 대댓글 수정,취소,등록 이벤트 리스너
	 */
	$(document).on('click', '.btn-comment-modify, .fa-ban, .btn-regist', function(){
		var objClass=$(this).attr('class');
		if(objClass.indexOf('btn-regist')!=-1){//리플 등록
			var comment=$("[name=comment-reply-form]").serialize();
			insertQNAComment(comment);
		}else if(objClass.indexOf('btn-comment-modify')!=-1){//리플 수정 등록
			var id=$(this).closest('li').attr('id').split('-')[1];
			var content=$("[name=modify-content]").val();
			updateQNAComment(id,content);
		}else{//취소
			$("#reply").remove();
			qnaCommentList();
			
		}
		
	})
	
	//강의듣기버튼클릭 이벤트 리스너
	$("#lecture-view, #lecture-bookmark").click(function(){
		var obj=$(this).attr('id');
		console.log(obj);
		if(obj=='lecture-view'){
			var parentId;
			/**
			 * 강의듣기버튼 눌렀을 경우의 로직
			 * 1.유저아이디로 db의 UserLearningLog테이블을 조회해서 어디까지 봤는지 검색(parentId)
			 * 2.최초 강의듣기일 경우 첫번째 강의로 이동(LrnVo이며 ParentId를 통해 목록을 뽑아내서 최초row)
			 * 3.최초가 아닐경우 가장 마지막 parentId와 userid로 조회해서 나온 결과중 isRecent가 1인 row값 반환
			 */
			$.ajax({
				url:'/learn/get/boardId',
				type:'GET',
				data:{"userId":userId, "parentId":boardId}
			}).done(function(data){
				if(data!=null){
					childId=data;
					var pathKind=window.location.pathname.split('/')[1]
					/*location.href="/learn/intro/"+boardId+"/course/"+childId;*/
					location.href="/"+pathKind+"/intro/"+boardId+"/course/"+childId;
				}
				
			}).fail(function(data){
					alert("Load Review Fail");
			});
			
			
		}else{
			addBookmark();
		}
		
	})
	$("#lecture-write").click(function(){
		location.href="/learn/intro/"+boardId+"/course";
	})
	
});



//window.onbeforeunload = function() {
//	$.ajax({
//		url:'/learn/course/update',
//		type:'PUT',
//		data:{"boardId":boardId}
//	}).done(function(data){
//		alert("조회수 증가");
//	}).fail(function(data){
//			alert("Load Review Fail");
//	});
//}




