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

	$("#toSendMessage").click(function(){
		
		var data = {
				recipient : $("#senderId").text(),
				recipientNickName : $("#senderNickName").text()	
				
		}
		
		alert(data.recipient+","+data.recipientNickName);
		
		$.ajax({
			url: 'sendMessage',
			data : JSON.stringify(data),
			dataType: "json",
			contentType : "application/json; charset=UTF-8",
			success : function(data){
				alert("success")
			},
			error : function(data) {
			alert("error")
			}
		});
		
		

	});
	
	
	
	
	
	$("button[name='sendMessageBtn'").click(function() {
		var data = {
			sender : $("#sender").text(),
			senderNickName : $("#senderNickName").text(),
//			recipient : $("input[name='recipient']").val(),
//			recipientNickName : 'dff',
			messageTitle : $("input[name='messageTitle']").val(),
			messageContent : $("textarea[name='messageContent']").val()
		}

		$.ajax({
			type : 'post',
			url : 'sendMessage',
			data : JSON.stringify(data),
			contentType : "application/json; charset=UTF-8",
			async : false,
			success : function(response) {

				if (response > 0) {
					alert("성공");
				}

			},
			error : function(data) {
			}
		});

	});

	
	
	
	
}); // end function

