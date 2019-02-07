/**

 * 
 */



$(document).ready(function() {
//
//	$.ajax({
//		type : 'post',
//		url : 'findId',
//		dataType : 'html',
//		success : function(data) {
			//$("#content").html(findId);
//		}
//	});
//
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(function() {
	    $(document).ajaxSend(function(e, xhr, options) {
	        xhr.setRequestHeader(header, token);
	    });
	});

$(function () {
// 아이디 찾기 버튼 클릭시
	$("#findIdBar").click(function(){
	$.ajax({
		type: 'post',
		url : 'member/findId',
		dataType : 'html',
		success : function(data) {
			//alert("id");
			$("#content").html(data);
		}
	});
	})
	
// 비밀번호 찾기 버튼 클릭시
	$("#findPwBar").click(function(){
	$.ajax({
		type: 'post',
		url : 'member/indPw',
		dataType : 'html',
		success : function(data) {
			//alert("pw");
			$("#content").html(data);
		}
	});
	})

	
	function emailValidate() {

	var email = $("#email").val();
	var regExp = /[a-zA-Z0-9]{2,}@[a-zA-Z0-9]{2,}.[a-zA-Z0-9]{2,}/i;

	if (regExp.test(email)) {
		findIdDone();
	} else {
		$("#emailCheckMsg").css("color", "red");
		$("#emailCheckMsg").text("올바르지 않는 이메일 형식입니다.");
		$("#email").focus();
	}

};
function findIdDone() {
	
}



});

});