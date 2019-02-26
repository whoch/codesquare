/**
 * 1.로드시 현재 나온 강의목록태그 태그박스에서 선택되도록 하는 메서드.
 * 2.태그박스 클릭시 해당 태그 영상만 나오게하는 기능.
 * 3.강의소개글 클릭시상세보기 이동
 */

var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(function() {
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});
});
//쿠키저장	
var setCookie = function(name, value, day) {
    var date = new Date();
    date.setTime(date.getTime() + day * 60 * 60 * 24 * 1000);
    document.cookie = name + '=' + value + ';expires=' + date.toUTCString() + ';path=/';
};
//쿠키조회
var getCookie = function(name) {
    var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
    return value? value[2] : null;
};
//쿠키삭제
var deleteCookie = function(name) {
    var date = new Date();
    document.cookie = name + "= " + "; expires=" + date.toUTCString() + "; path=/";
}



function modalAlertMessage(title, msg){
	$("#modal-alert-title").text(title);
	$("#modal-alert-area").text(msg);
	$("#modal-lecture-alert").modal('show');
}




function getId(content,index){
	return content.attr('id').split('-')[index];
}
//이벤트 처리 메세지 출력 메서드
function alertMessage(msg){
	$("#div-alert-area").text(msg);
}
//유저정보 상세보기 모달창 닫기
function userModalHide(){
	$(".main-panel .modal").modal('hide')
	setTimeout(function() {
		location.reload();
		alertMessage('');
	}, 2000);
}


	
	
	
	
	
	
	
	
	
	
	