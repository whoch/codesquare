/**
 * 
 * 
 */


$(function (){
	
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(function() {
	    $(document).ajaxSend(function(e, xhr, options) {
	        xhr.setRequestHeader(header, token);
	    });
	});

	

// 아이디 찾기 버튼 클릭시
	$("#findIdBar").click(function(){
	
	$.ajax({
		type: 'get',
		url : 'findId',
		dataType : 'html',
		async: false,
		success : function(data) {
			// alert("id");
			$("#content").html(data);
		}
	});
	})
	

	
	$("#findIdValidate").click(function(){
	
		var email = $("#email").val();
		var regExp = /[a-zA-Z0-9]{2,}@[a-zA-Z0-9]{2,}.[a-zA-Z0-9]{2,}/i;

		if (regExp.test(email)) {
			emailCheck();
		} else {
			$("#emailCheckMsg").css("color", "red");
			$("#emailCheckMsg").text("올바르지않은 이메일 양식입니다.");
			$("#email").focus();
		}

	});

	function emailCheck() {

<<<<<<< HEAD
		var email = $("#email").val();
		$.ajax({
			type : "post",
			data : email,
			url : "emailCheck",
			async: false,
			contentType : "application/json; charset=UTF-8",
			success : function(response) {

				if (response > 0) {

					$("#emailCheckMsg").css("color", "red");
					$("#emailCheckMsg").text("이미 존재하는 이메일 입니다.");
					$("#email").focus();

				} else {
					$("#emailCheckMsg").css("color", "blue");
					$("#emailCheckMsg").text("사용 가능한 이메일 입니다.");
					emailck = 1;

				}
			},
			error : function(error) {
				//alert(error);
=======
		
		var email = $("#email").val();
		$.ajax({
			type : "post",
			data : email,
			url : "emailCheck",
			async: false,
			dataType: "json",
			contentType : "application/json; charset=UTF-8",
			success : function(response) {

				if (response > 0) {

					$("#emailCheckMsg").css("color", "red");
					$("#emailCheckMsg").text("이미 존재하는 이메일 입니다.");
					$("#email").focus();

				} else {
					$("#emailCheckMsg").css("color", "blue");
					$("#emailCheckMsg").text("사용 가능한 이메일 입니다.");
					emailck = 1;

				}
			},
			error : function(error) {
>>>>>>> branch 'master' of https://github.com/catsbi/codesquare.git
				$("#emailCheckMsg").css("color", "black");
				$("#emailCheckMsg").text("error");
			}
		});

	};
	


});
