/**
 * 
 */
var boardId = $("[name=boardId]").val();
var boardKindId = $("[name=boardKindId]").val();
var nickName=$(".comment-writerInfo>[name=nickName]").val();
var userId=$(".comment-writerInfo>[name=userId]").val();
var writerThumbnail=$(".commnet-writer-thumbnail").attr('src');
//댓글 불러오기
function getCommentList(){
	$.ajax({
		url:'Comment/list',
		type:'get',
		data:{'boardId' : boardId,'boardKindId':boardKindId}
	}).done(function(data){
		var cContent="<ul id=\"comments-list\" class=\"comments-list\">";
		$.each(data,function(key,value){
			if(!value.parentId){
		        cContent+="<li id=\""+value.boardKindId+"-"+value.id+"\"><!--메인 댓글--><div class=\"comment-main-level\">";
			}else{
				cContent+="<li id=\""+value.boardKindId+"-"+value.id+"\"><!--메인 댓글--><div class=\"comment-main-level reply-list\">";
			}
		        cContent+="<!-- Avatar --><div class=\"comment-avatar\"><img src=\""+value.thumbnail+"\" alt=\"유저썸네일\"></div>";
		        cContent+="<div class=\"comment-box\">";
		        cContent+="<div class=\"comment-head\">";
		        cContent+="<h6 class=\"comment-name\"><a href=\"member/"+value.id+"\">"+value.nickName+"</a></h6>";
		        cContent+="<span>"+value.writeDate+"</span>";
		        if(value.deleteStatus==0){
		        	cContent+="<span class=\"comment-icon\"><i class=\"fa fa-reply\" title=\"댓글달기\"></i><i class=\"fas fa-edit\" title=\"수정하기\"></i><i class=\"fa fa-trash\" title=\"삭제하기\"></i></span>";
		        }
		        cContent+="</div><div class=\"comment-content\"><p class=\"comment-content-text\">"+value.content+"</p></div></div></div>";
		        cContent+="</li>";
		})
		cContent+="</ul>";
		 
		 $(".comments-container").html(cContent);
	}).fail(function(data){
		alert("실패");
	})
}

// 댓글 작성하기
function insertComment(comment){
	$.ajax({
		url:'Comment/insert',
		type:'POST',
		data:comment
	}).done(function(data){
		if(data==1){
			$("[name=content]").val('');
			getCommentList();
		}
	}).fail(function(data){
		if(data!=1){
			alert("Load Review Fail");
		}
	});
}

// 댓글 수정하기
function updateComment(id,content){
	$.ajax({
		url:'Comment/update',
		type:'POST',
		data:{"id":id,"content":content}
	}).done(function(data){
			getCommentList();
	}).fail(function(data){
			alert("Load Review Fail");
	});
	
}

// 댓글 삭제하기
function deleteComment(id){
	$.ajax({
		url:'Comment/delete',
		type:'GET',
		data:{"id":id}
	}).done(function(data){
		if(data==2){
			$("#"+boardKindId+"-"+id+" .comment-icon").remove();
		}
		getCommentList();
	}).fail(function(data){
			alert("Load Review Fail");
	});
}


$(document).ready(function() {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(function() {
	    $(document).ajaxSend(function(e, xhr, options) {
	        xhr.setRequestHeader(header, token);
	    });
	});
	getCommentList();
	
	//댓글 등록버튼 클릭
	$(".btn-comment-regist").click(function(){
		var comment=$("[name=commentForm]").serialize();
		insertComment(comment);
	})
	
	/**
	 * 대댓글 작성(fa-reply),수정(fa-edit),삭제(fa-trash) 감지 이벤트 리스너 
	 */
	$(document).on('click','.fa-edit,.fa-trash, .fa-reply',function(){
		var obj=$(this).closest('.comment-box').children('.comment-content');
		var objClass=$(this).attr('class');
		var id=$(this).closest('li').attr('id').split('-')[1];
		if(objClass.indexOf('fa-edit')!=-1){//댓글수정
			var txt=obj.children('p').text();
			var txtarea="<textarea id=\"123\" rows=\"3\" cols=\"20\" name=\"modify-content\" maxlength=\"300\" required=\"required\" placeholder=\"바르고 고운말을 사용해요!!\">"+txt+"</textarea>";
			var icon="<i id=\"btn-comment-modify\" class=\"far fa-check-square fa-lg\" title=\"등록\" aria-hidden=\"true\"></i><i id=\"btn-reply-cancel\" class=\"fa fa-ban fa-lg\" title=\"취소\" aria-hidden=\"true\"></i>";
			obj.html(txtarea);
			$(obj).prev().children('.comment-icon').html(icon);
		}
		if(objClass.indexOf('fa-trash')!=-1){//댓글삭제
			deleteComment(id);
		}
		if(objClass.indexOf('fa-reply')!=-1){//대댓글 작성
			var replyContent="";
			replyContent+="<li id=\"reply\"><!--메인 댓글--><form name=\"comment-reply-form\"><div class=\"comment-main-level reply-list\">";
			replyContent+="<input type=\"hidden\" name=\"boardId\" value=\""+boardId+"\" />";
			replyContent+="<input type=\"hidden\" name=\"boardKindId\" value=\""+boardKindId+"\" />";
			replyContent+="<input type=\"hidden\" name=\"userId\" value=\""+userId+"\" /> ";
			replyContent+="<input type=\"hidden\" name=\"nickName\"	value=\""+nickName+"\" />";
			replyContent+="<input type=\"hidden\" name=\"parentId\"	value=\""+id+"\" />";
	        replyContent+="<!-- Avatar --><div class=\"comment-avatar\"><img src=\""+writerThumbnail+"\" alt=\"유저썸네일\"></div>";
	        replyContent+="<div class=\"comment-box\">";
	        replyContent+="<div class=\"comment-head\">";
	        replyContent+="<h6 class=\"comment-name\"><a href=\"member/\" >"+nickName+"</a></h6>";
	        replyContent+="<span class=\"comment-icon\"><i id=\"btn-reply-regist\" class=\"far fa-check-square fa-lg\" title=\"등록\" aria-hidden=\"true\"></i><i id=\"btn-reply-cancel\" class=\"fa fa-ban fa-lg\" title=\"취소\" aria-hidden=\"true\"></i></span></div>";
	        replyContent+="<div class=\"comment-content\"><textarea rows=\"3\" cols=\"20\" name=\"content\" maxlength=\"300\" required=\"required\" placeholder=\"바르고 고운말을 사용해요!!\"></textarea></div></div></div>";
	        replyContent+="</form></li>";
	        $(this).closest("li").append(replyContent);
		}
	})
	
	/**
	 * 대댓글 수정,취소,등록 이벤트 리스너
	 */
	$(document).on('click', '#btn-comment-modify, #btn-reply-cancel, #btn-reply-regist', function(){
		var index=$(this).attr('id');
		var obj=$(this);
		if(index=='btn-reply-regist'){//리플 등록
			var comment=$("[name=comment-reply-form]").serialize();
			insertComment(comment);
		}
		if(index=='btn-comment-modify'){//리플 수정 등록
			var id=$("#btn-comment-modify").closest('li').attr('id').split('-')[1];
//			alert($("[name=modify-content]").val());
			var content=$("[name=modify-content]").val();
			updateComment(id,content);
		}
		if(index=='btn-reply-cancel'){//취소
			$("#reply").remove();
			getCommentList();
			
		}
		
	})
	
	
})