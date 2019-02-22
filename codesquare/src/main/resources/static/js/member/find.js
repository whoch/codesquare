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

	  $("input[type=radio]").click(function () {
	    	 if($("input[id='id']").prop("checked")) { 
	    		 $("#findIdTab").addClass("active");
	    		 $("#findIdTab").removeClass('hide');
	    		 $("#findPwTab").addClass("hide");
	    		 $("#findPwTab").removeClass("active");
	    		 
	    	} else if ($("input[id='pw']").prop("checked")){
	    		 $("#findIdTab").addClass("hide");
	    		 $("#findIdTab").removeClass('active');
	    		 $("#findPwTab").addClass("active");
	    		 $("#findPwTab").removeClass("hide");
	    	}
	    });
	
	
	
	
	$("#findIdValidate").click(function() {

		var email = $("#email").val();
		var regExp = /[a-zA-Z0-9]{2,}@[a-zA-Z0-9]{2,}.[a-zA-Z0-9]{2,}/i;

		if (regExp.test(email)) {
			emailCheck();
			$("#findIdMsg").text("")
		} else {
			$("#findIdMsg").css("color",  "#A7070B");
			$("#findIdMsg").html("<i class='fas fa-exclamation-triangle'></i> 유효하지 않은 이메일 양식입니다.");
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
					$("#findIdMsg").text("")
					findId();

				} else {
					
					$("#findIdMsg").css("color", "#A7070B");
					$("#findIdMsg").html("<i class='fas fa-exclamation-triangle'></i> 입력하신 이메일과 일치하는 회원 정보가 없습니다.");
					$("#email").focus();

				}
			},
			error : function(error) {
				$("#findIdMsg").css("color", "black");
				$("#findIdMsg").html("<i class='fas fa-exclamation-triangle'></i>error");
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
				$("#findId-dialog").toggle();
				$("#findId-dialog-content").html("가입한 아이디는<br />"+response+"<br />입니다.<br /><br /><a href='/member/login'>로그인</a><br />");
				$("#findId-close").click(function(){
					$("#findId-dialog").toggle();
				});

			},
			error : function(error) {
				$("#findIdMsg").css("color", "black");
				$("#findIdMsg").html("<i class='fas fa-exclamation-triangle'></i> error");
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
			$("#findPwMsg").css("color", "#A7070B");
			$("#findPwMsg").html("<i class='fas fa-exclamation-triangle'></i> 유효하지 않은 아이디 형식입니다.");
			$("#userId").focus();
		} else if (!regExp2.test(email)) {
			$("#findPwMsg").css("color", "#A7070B");
			$("#findPwMsg").html("<i class='fas fa-exclamation-triangle'></i> 유효하지 않은 이메일 형식입니다.");
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
					$("#findPw-dialog").toggle();
					$("#findPw-dialog-content").html("가입 시 입력했던 이메일로 <br />초기화된 비밀번호가 전송되었습니다. <br /> <a href='/member/login'>로그인</a><br />");
					$("#findPw-close").click(function(){
						$("#findPw-dialog").toggle();
					});

				} else {
					$("#findPwMsg").css("color", "#A7070B");
					$("#findPwMsg").html("<i class='fas fa-exclamation-triangle'></i> 일치하는 정보가 없습니다.");
				}
			},
			error : function(error) {
				$("#findIdMsg").css("color", "black");
				$("#findIdMsg").html("<i class='fas fa-exclamation-triangle'></i> error");
			}
		});
	}
	

//	function mail(){
//		
//		var userId = $("#userId").val();
//		
//		$.ajax({
//			type : "post",
//			data : userId,
//			url : "findPwMail",
//			async : false,
//			contentType : "application/json; charset=UTF-8",
//			success : function(data) {
//				$("#findIdMsg").text("메일발송완료");
//			},
//			error : function(error) {
//				$("#findIdMsg").css("color", "black");
//				$("#findIdMsg").html("<i class='fas fa-exclamation-triangle'></i> error");
//			}
//		});
//		
//	}
//	
//	

});
