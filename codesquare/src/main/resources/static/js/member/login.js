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

	
	$("#userId").blur(function(){
		var userId = $("#userId").val();
		var regExp = /^[a-z0-9]{4,15}$/;

		if (!regExp.test(userId)) {
			$("#checkId").css("color", "#A7070B");
			$("#checkId").html(
					"<i class='fas fa-info-circle'></i> 유효하지 않은 아이디 형식입니다.");

		} 
	});
	
	

});
