/**
 * 
 * 
 */

$(function() {

	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(function() {
		$(document).ajaxSend(function(e, xhr, options) {
			xhr.setRequestHeader(header, token);
		});
	});

	$(".nav-tabs a").click(function() {
		$(this).tab('show');
	});

	$("#findIdValidate").click(function() {

		var email = $("#email").val();
		var regExp = /[a-zA-Z0-9]{2,}@[a-zA-Z0-9]{2,}.[a-zA-Z0-9]{2,}/i;

		if (regExp.test(email)) {
			emailCheck();
		} else {
			$("#findIdMsg").css("color", "red");
			$("#findIdMsg").text("올바르지않은 이메일 양식입니다.");
			$("#email").focus();
		}
	});

	function emailCheck() {

		var email = $("#email").val();
		$.ajax({
			type : "post",
			data : email,
			url : "emailCheck",
			async : false,
			contentType : "application/json; charset=UTF-8",
			success : function(response) {

				if (response > 0) {
					// 아이디가 있을 경우
					findId();

				} else {
					$("#findIdMsg").css("color", "red");
					$("#findIdMsg").text("입력하신 이메일과 일치하는 정보가 없습니다.");
					$("#email").focus();

				}
			},
			error : function(error) {
				$("#emailCheckMsg").css("color", "black");
				$("#emailCheckMsg").text("error");
			}
		});

	}
	;

	function findId() {

		var email = $("#email").val();

		$.ajax({
			type : "post",
			data : email,
			url : "findId",
			async : false,
			contentType : "application/json; charset=UTF-8",
			success : function(response) {

				$("#findIdMsg").text(response);
			},
			error : function(error) {
				$("#findIdMsg").css("color", "black");
				$("#findIdMsg").text("error");
			}
		});
	}
	;

	$("#findPwValidate").click(function() {

		var userId = $("#userId").val();
		var regExp = /^[a-z0-9]{4,10}$/;
		var email = $("#email2").val();
		var regExp2 = /[a-zA-Z0-9]{2,}@[a-zA-Z0-9]{2,}.[a-zA-Z0-9]{2,}/i;

		if (regExp2.test(email) && regExp.test(userId)) {
			idEmailCheck();
		} else if (!regExp.test(userId)) {
			$("#findIdPwMsg").css("color", "red");
			$("#findIdPwMsg").text("올바르지 않은 아이디 형식입니다.");
			$("#userId").focus();
		} else if (!regExp2.test(email)) {
			alert(email);
			$("#findIdPwMsg").css("color", "red");
			$("#findIdPwMsg").text("올바르지 않은 이메일 형식입니다.");
			$("#email").focus();
		}
	});

	function idEmailCheck() {

		var data = {
			userId : $("#userId").val(),
			email : $("#email2").val()
		}

		$.ajax({
			type : "post",
			data : JSON.stringify(data),
			url : "findPw",
			contentType : "application/json; charset=UTF-8",
			success : function(response) {
				if (response > 0) {

					mail();
				} else {
					$("#findIdPwMsg").text("없습니다.");
				}
			},
			error : function(error) {
				$("#findIdMsg").css("color", "black");
				$("#findIdMsg").text("error");
			}
		});
	}
	

	function mail(){
		
		var userId = $("#userId").val();
		
		$.ajax({
			type : "post",
			data : userId,
			url : "findPwMail",
			async : false,
			contentType : "application/json; charset=UTF-8",
			success : function(data) {
				$("#findIdPwMsg").text("메일발송완료");
			},
			error : function(error) {
				$("#findIdMsg").css("color", "black");
				$("#findIdMsg").text("error");
			}
		});
		
	}
	
	

});
