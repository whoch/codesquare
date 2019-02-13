/**
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

	$(".nav-link").click(function() {
		$(this).tab('show');
	});// 탭 클릭 이벤트

	// 강사전환신청하기 완료시

	$("#toInstructorDone").click(function() {

		 var data = {
		 introContent : $("textarea[name='introContent']").val(),
		 userId : $("input[name='userId']").val()
		 }
		 
		$.ajax({
			type : 'post',
			url : 'toInstructor',
			data : JSON.stringify(data),
			contentType : "application/json; charset=UTF-8",
			async : false,
			success : function(response) {
				

			},
			error : function(data) {
				alert("에러");
			}
		});
	});

	// 강사정보 수정하기 완료클릭시
	$("#modifyInstructorInfoDone").click(function() {

		var data = {
			introContent : $("#introContent").val(),
			history : $("#history").val(),
			userId : $("#userId").val()
		}

		$.ajax({
			type : 'post',
			url : 'modifyInstructorInfo',
			data : JSON.stringify(data),
			contentType : 'application/json; charset=UTF-8',
			async : false
		}).done(function(response) {
			alert("수정완료");
			$("#contentcontainer").html(response);
		}).fail(function(response) {
			alert("에러");
		})

	});

	$("#nickValidate").click(function() {

		var nickName = $("#nickName").val();
		var regExp = /^[a-zA_Z0-9ㄱ-힣]{2,10}$/;

		if (regExp.test(nickName)) {
			nickChange();
		} else {
			$("#nickCheckMsg").css("color", "red");
			$("#nickCheckMsg").text("닉네임은 영대소문자,한글,숫자로 이루어진 2자이상 10자 미만");
			$("#nickName").focus();
		}

	});

	function nickChange() {

		var nickName = $("#nickName").val();
		$.ajax({
			type : "post",
			data : nickName,
			url : "nickChange",
			contentType : "application/json; charset=UTF-8",
			success : function(response) {

				if (response > 0) {
					$("#nickCheckMsg").css("color", "red");
					$("#nickCheckMsg").text("이미 존재하는 닉네임 입니다.");
					$("#nickName").focus();

				} else {
					// var a=nickName;
					// '<a onclick="changeNick(' + data + ');">수정</a>';
					$("#changeNickContent").hide();
					$("#changeNick").show();
					// location.reload();
					$("#nickCheckMsg").text("");
					$("#headerNickName").html(nickName);
					$("#nickField").html(nickName);
					$("#myNickName").html(nickName);

					// 아이디가 중복하지 않으면 idck = 1

				}
			},
			error : function(error) {
				$("#nickCheckMsg").css("color", "black");
				$("#nickCheckMsg").text("error");
			}
		});

	}
	;

	$("#pwValidate").click(
			function() {

				var password = $("#password").val();
				var rePassword = $("#rePassword").val();
				var regExp = /^[a-zA_Z0-9]{4,15}$/;

				if (regExp.test(rePassword)) {
					if ((password != null && rePassword != null)
							&& password == rePassword) {
						pwChange();
					} else {
						$("#pwMsg").text("");
						$("#pwCheckMsg").css("color", "red");
						$("#pwCheckMsg").text("비밀번호가 일치하지 않습니다.");
						$("#rePassword").focus();
					}
				} else {
					$("#pwCheckMsg").css("color", "red");
					$("#pwCheckMsg").text(
							"비밀번호는 영 대, 소문자, 숫자 로 이루어진 4자 이상 15자 미만");
					$("#rePassword").focus();
				}

			});

	function pwChange() {

		var data = {
			password : $("#password").val(),
			userId : $("#userId").val()
		}

		$.ajax({
			type : "post",
			data : JSON.stringify(data),

			url : "changePw",
			contentType : "application/json; charset=UTF-8",
			success : function(response) {

				if (response > 0) {
					location.reload();
					alert("비밀번호 변경 완료");
					// $("#pwMsg").css("color", "black");
					// $("#pwMsg").text("비밀번호 변경 완료");

				} else {

					$("#pwMsg").css("color", "red");
					$("#pwMsg").text("비밀번호 변경 실패");
					alert("실패" + response);
					// 아이디가 중복하지 않으면 idck = 1

				}
			},
			error : function(error) {
				$("#nickCheckMsg").css("color", "black");
				$("#nickCheckMsg").text("error");
			}
		});

	}

	$("#changeNickContent").hide();

	$("#changeNick").click(
			function() {
				$("#changeNickContent").not(
						$(this).next("#changeNickContent").slideToggle())
						.hide();
				$("#changeNick").not($(this).next("#changeNick")).hide();
				// var a = '<a onclick="nickChange()"> 수정하기 </a>'
				// $("#nickChange").html(a);
			});

	// 닉네임 변경 버튼

	$("#nickValidate").click(function() {

		var nickName = $("#nickName").val();
		var regExp = /^[a-zA_Z0-9ㄱ-힣]{2,10}$/;

		if (regExp.test(nickName)) {
			nickChange();
		} else {
			$("#nickCheckMsg").css("color", "red");
			$("#nickCheckMsg").text("닉네임은 영대소문자,한글,숫자로 이루어진 2자이상 10자 미만");
			$("#nickName").focus();
		}

	});

	function nickChange() {

		var nickName = $("#nickName").val();
		$.ajax({
			type : "post",
			data : nickName,
			url : "nickChange",
			contentType : "application/json; charset=UTF-8",
			success : function(response) {

				if (response > 0) {

					$("#nickCheckMsg").css("color", "red");
					$("#nickCheckMsg").text("이미 존재하는 닉네임 입니다.");
					$("#nickName").focus();

				} else {
					// var a=nickName;
					// '<a onclick="changeNick(' + data + ');">수정</a>';
					$("#changeNickContent").hide();
					$("#changeNick").show();
					// location.reload();
					$("#nickCheckMsg").text("");
					$("#headerNickName").html(nickName);
					$("#nickField").html(nickName);
					$("#myNickName").html(nickName);

					// 아이디가 중복하지 않으면 idck = 1

				}
			},
			error : function(error) {
				$("#nickCheckMsg").css("color", "black");
				$("#nickCheckMsg").text("error");
			}
		});

	}
	;

	$("#changeEmailContent").hide();

	$("#changeEmail").click(
			function() {
				$("#changeEmailContent").not(
						$(this).next("#changeEmailContent").slideToggle())
						.hide();
				$("#changeEmail").not($(this).next("#changeEmail")).hide();
			});

	// 이메일 변경 버튼
	$("#emailValidate").click(function() {

		var email = $("#email").val();
		var regExp = /[a-zA-Z0-9]{2,}@[a-zA-Z0-9]{2,}.[a-zA-Z0-9]{2,}/i;

		if (regExp.test(email)) {
			emailChange();
		} else {
			$("#emailCheckMsg").css("color", "red");
			$("#emailCheckMsg").text("올바르지 않은 이메일 양식입니다.");
			$("#email").focus();
		}

	});

	function emailChange() {

		var email = $("#email").val();

		$.ajax({
			type : "POST",
			data : email,
			url : "emailChange",
			contentType : "application/json; charset=UTF-8",
			success : function(response) {

				if (response > 0) {

					$("#emailCheckMsg").css("color", "red");
					$("#emailCheckMsg").text("이미 존재하는 이메일 입니다.");
					$("#email").focus();

				} else {

					$("#changeEmailContent").hide();
					$("#changeEmail").show();
					// $("#nickField").html(data);
					$("#emailCheckMsg").text("");
					$("#emailField").html(email);
					// location.reload();
					// 아이디가 중복하지 않으면 idck = 1
				}
			},
			error : function(error) {
				$("#emailCheckMsg").css("color", "black");
				$("#emailCheckMsg").text("error");
			}
		});
	}
	;

}); // end function

