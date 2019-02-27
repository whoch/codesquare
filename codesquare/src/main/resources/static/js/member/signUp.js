/**
 * 회원가입, 회원정보 수정 시 정보 체크
 */



var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(function() {
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});
});


	
	
var idck = 0;
var emailck = 0;
var pwck = 0;

$("#userId").blur(function() {

	var userId = $("#userId").val();
	var regExp = /^[a-z0-9]{4,15}$/;

	if (regExp.test(userId)) {
		idCheck();
	} else {
		$("#idCheckMsg").css("color", "#A7070B");
		$("#idCheckMsg").html("<i class='fas fa-info-circle'></i> 아이디는 영문자, 숫자 이루어진 4자이상 15자 미만");
		
	}

});

function idCheck() {

	var userId = $("#userId").val();
	
	$.ajax({
		type : "post",
		data : userId,
		url : "/member/idCheck",
		contentType : "application/json; charset=UTF-8",
		success : function(response) {
			if ( response > 0) {

				$("#idCheckMsg").css("color", "#A7070B");
				$("#idCheckMsg").html("<i class='fas fa-info-circle'></i> 이미 존재하는 아이디 입니다.");

			} else {
				$("#idCheckMsg").text("");

				// 아이디가 중복하지 않으면
				idck = 1

			}
		},
		error : function(error) {
			$("#idCheckMsg").css("color", "black");
			$("#idCheckMsg").text("error");
		}
	});

};



$("input[name='email'").blur(function() {

	var email = $("input[name='email'").val();
	var regExp = /^[a-zA-Z0-9-_.]{2,}@[a-zA-Z0-9]{2,}.[a-zA-Z0-9]{2,}$/i;

	if (regExp.test(email)) {
		emailCheck();
	} else {
		$("#emailCheckMsg").css("color", "#A7070B");
		$("#emailCheckMsg").html("<i class='fas fa-info-circle'></i> 올바르지 않은 이메일 양식입니다.");
	}

});

function emailCheck() {

	
	var email = $("input[name='email'").val();
	
	
	$.ajax({
		type : "post",
		data : email,
		url : "emailCheck",
		contentType : "application/json; charset=UTF-8",
		success : function(response) {
			if (response > 0) {

				$("#emailCheckMsg").css("color", "#A7070B");
				$("#emailCheckMsg").html("<i class='fas fa-info-circle'></i> 이미 존재하는 이메일 입니다.");

			} else {
				$("#emailCheckMsg").text("");
				emailck = 1;

			}
		},
		error : function(error) {
			$("#emailCheckMsg").css("color", "black");
			$("#emailCheckMsg").html("<i class='fas fa-info-circle'></i>error");
		}
	});

};

// 비밀번호 체크

$("#password").blur(function() {
	
	var password = $("#password").val();
	var regExp = /^[a-zA_Z0-9]{6,15}$/;
	
	if (regExp.test(password)) {
		$("#pwCheckMsg").text("");
	} else {
		$("#pwCheckMsg").css("color", "#A7070B");
		$("#pwCheckMsg").html("<i class='fas fa-info-circle'></i> 비밀번호는 영문자, 숫자를 포함한 6자 이상 15자 미만");
	}

});

$("#rePassword").blur(function() {
	
	var password = $("#password").val();
	var regExp = /^[a-zA_Z0-9]{6,15}$/;
	var rePassword = $("#rePassword").val();

	if ((password != null && rePassword != null) && regExp.test(password) && password == rePassword) {
		$("#pwCheckMsg").text("");
		pwck = 1;
	} else if((password != null && rePassword != null) && !regExp.test(password)){
		$("#pwCheckMsg").css("color", "#A7070B");
		$("#pwCheckMsg").html("<i class='fas fa-info-circle'></i> 비밀번호는 영문자, 숫자를 포함한 6자 이상 15자 미만");
	} else {
		$("#pwCheckMsg").css("color", "#A7070B");
		$("#pwCheckMsg").html("<i class='fas fa-info-circle'></i> 비밀번호가 일치하지 않습니다.");
	}
});

// 회원가입 버튼
$("#signUp").click(function() {

	if (idck == 1 && emailck == 1 && pwck == 1) {

			$("#signUpForm").attr('action', "signUp").submit();

	} else if (idck != 1){
		$("input[name='userId'").focus();
		$("#idCheckMsg").css("color", "#A7070B");
		$("#idCheckMsg").html("<i class='fas fa-info-circle'></i> 아이디를 입력해주세요.");

	} else if (emailck != 1){
		$("input[name='email'").focus();
		$("#emailCheckMsg").css("color", "#A7070B");
		$("#emailCheckMsg").html("<i class='fas fa-info-circle'></i> 이메일을 입력해주세요.");
// $('body').scrollTo("input[name='email'");
		
	} else if (pwck != 1 ){
		$("input[name='rePassword'").focus();
		$("#emailCheckMsg").css("color", "#A7070B");
		$("#emailCheckMsg").html("<i class='fas fa-info-circle'></i> 비밀번호를 입력해주세요.");
// $('body').scrollTo("input[name='rePassword'");
	} else {
		$("#signUpCheckMsg").css("color", "#A7070B");
		$("#signUpCheckMsg").html("<i class='fas fa-exclamation-triangle'></i> error");
	}
});





