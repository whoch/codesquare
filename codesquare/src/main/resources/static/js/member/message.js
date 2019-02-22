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

	if (location.pathname.indexOf('receivedMessage') != -1) {
		// receivedMessage 가 들어갈
		} else {
			$('#sentMessageListH').addClass('active');
			$('#receivedMessageListH').removeClass('active');
			
		}
	
	
	
	
// $(".toSendMessage").click(function() {
//
// var recipient = $(this).parents('.dropright').find('#senderId').text();
//
// location.href = 'sendMessage/'+recipient;
//		
//
// });


	// 전체선택
	$("#selectAll").click(function() {
		if ($("#selectAll").prop("checked")) {
			$("input[name=checkboxes]").prop("checked", true);
		} else {
			$("input[name=checkboxes]").prop("checked", false);
		}
	})

	$("input[name='checkboxes'").click(function() {

		var checkboxNum = $("input[name=checkboxes]").length;
		var checkedNum = $("input[name=checkboxes]:checked").length;
		// alert(checkboxNum);
		// alert(checkedNum);
		if (checkedNum > 0) {
			$("#selectAll").prop("checked", false);
			if (checkedNum == checkboxNum) {
				$("#selectAll").prop("checked", true);
			}
		}
		// if($("input[name=checkboxes]").prop("checked")){
		// $("#selectAll").prop("checked", true);
		// } else {
		// $("#selectAll").prop("checked", false);
		// }

	});


	
	
	
//	$("#deleteBtn").click(function() {
//		var ids = [];
//
//		$("input[name='checkboxes']:checked").each(function() {
//
//			ids.push(Number($(this).val()));
//
//		});
//
//		$.ajax({
//			type : 'post',
//			url : 'deleteMessage',
//			data : JSON.stringify(ids),
//			traditional : true,
//			contentType : "application/json; charset=UTF-8",
//			async : false,
//			success : function(response) {
//
//				if (response > 0) {
//
//					location.reload();
//				}
//
//			},
//			error : function(data) {
//			}
//		});
//
//	});
//	
	
	
	
	// 읽기 뷰에서 답장
	$("input[name='replyMessageBtn'").click(function() {
		
		var recipient =$("#sender").text();
		location.href = '/message/sendMessage/'+recipient;

	});
	
	// 읽기 뷰에서 삭제
	
	$("#deleteMsgMo").click(function() {
		$("#delete-dialog").toggle();
	
	})
	
	
	$("#deleteMessageBtn").click(function() {
	
		var ids = [];
		ids.push(Number($("#messageId").text()));
		
		
		$.ajax({
			type : 'post',
			url : '/message/deleteMessage',
			data : JSON.stringify(ids),
			traditional : true,
			contentType : "application/json; charset=UTF-8",
			async : false,
			success : function(response) {

				if (response > 0) {

					location.href = '/message/receivedMessage';
				}

			},
			error : function(data) {
			}
		});

	});
	

	// 읽기 뷰에서 목록으로
	$("input[name='toListMessageBtn'").click(function() {
		
		location.href = '/message/receivedMessage';
	
	});
	
	// 보낸메지함. 읽기 뷰에서 목록으로
	$("input[name='toSListMessageBtn'").click(function() {
		
		location.href = '/message/sentMessage';
	
	});
	// 새로 쪽지 보내기
	$("#newMessageBtn").click(function() {

		location.href = 'sendMessage';

		
	});
	
	// 새로 쪽지 보내기
	
	var idck = 0;

	$("input[name='recipient']").blur(function() {

		var userId = $("input[name='recipient']").val();
		
		$.ajax({
			type : "post",
			data : userId,
			url : "/member/idCheck",
			contentType : "application/json; charset=UTF-8",
			success : function(response) {
				if ( response > 0) {

					$("#idCheckMsg").text("");
					idck = 1 ;

				} else {
					
					$("#idCheckMsg").css("color", "#A7070B");
					$("#idCheckMsg").html("<i class='fas fa-info-circle'></i> 존재하지 않는 아이디입니다.");
				}
			},
			error : function(error) {
				$("#idCheckMsg").css("color", "black");
				$("#idCheckMsg").html("<i class='fas fa-info-circle'></i> 유효하지 않은 형식의 아이디입니다.");
			}
		});

	});
	
	
	
	
	$("input[name='sendNewMessageBtn']").click(function() {
		var data = {
				sender : $("#sender").text(),
				senderNickName : $("#senderNickName").text(),
				recipient : $("input[name='recipient']").val(),
				messageContent : $("textarea[name='messageContent']").val()
			}
		
			var mcck = $("textarea[name='messageContent']").val().length;
			
		if( idck == 1 && mcck > 0) {
			$.ajax({
				type : 'post',
				url : 'sendMessage',
				data : JSON.stringify(data),
				contentType : "application/json; charset=UTF-8",
				async : false,
				success : function(response) {

					if (response > 0) {
						$("#done-dialog").toggle();
						$("#done-dialog-content").css("color", "#552796");
						$("#done-dialog-content").html(" <i class='far fa-check-circle fa-3x'></i> <div class='small'> <br /><br />완료 <br /></div>")
						$("#done-close").click(function(){
							location.href = '/message/receivedMessage';
						})
					} else {
						// 실패시
						history.go(-1) 
					}

				},
				error : function(data) {
				}
			});
		} else if( idck == 0 ){
			$("#done-dialog").toggle();
			$("#done-dialog-content").css("color", "#A7070B");
			$("#done-dialog-content").html("<i class='fas fa-exclamation-triangle fa-3x'></i> <br /> <br /><div class='small'>존재하지 않는 아이디 입니다.  <br />아이디를 확인해주세요.</div>");
		} else if( mcck == 0 ){
			$("#done-dialog").toggle();
			$("#done-dialog-content").css("color", "#A7070B");
			$("#done-dialog-content").html("<i class='fas fa-exclamation-triangle fa-3x'></i> <br /> <br /><div class='small'>내용을 입력해주세요.</div>");
			
		}
	});
//	
	
	
	// 닉네임 클릭하여 쪽지 보내기
	$("input[name='sendMessageBtn']").click(function() {
		
		var mcck = $("textarea[name='messageContent']").val().length;
		var data = {
			sender : $("#sender").text(),
			senderNickName : $("#senderNickName").text(),
			recipient : $("#recipient").text(),
			messageContent : $("textarea[name='messageContent']").val()
		}

		if(mcck > 0) {
		$.ajax({
			type : 'post',
			url : '/message/sendMessage',
			data : JSON.stringify(data),
			contentType : "application/json; charset=UTF-8",
			async : false,

			success : function(response) {

				if (response > 0) {
					$("#done-dialog").toggle();
					$("#done-dialog-content").css("color", "#552796");
					$("#done-dialog-content").html("<i class='far fa-check-circle fa-3x'></i><br /> <br />완료<br /> <br />");
					$("#done-close").click(function(){
						$("#done-dialog").toggle();
						
						history.go(-1);
					});
					
				}

			},
			error : function(data) {
			}
		});
		} else {
			$("#done-dialog").toggle();
			$("#done-dialog-content").css("color", "#A7070B");
			$("#done-dialog-content").html("<i class='fas fa-exclamation-triangle fa-3x'></i> <br /> <br />내용을 입력해주세요.");
			
		}
		

	});

	$("input[name='searhBtn']").click(function(){
		$("#recdivedMessageForm").attr('action', 'receivedMessage');
	});
	
	// 삭제 모달창
	$("#deleteMsg").click(function() {
		var ids = [];

		$("input[name='checkboxes']:checked").each(function() {

			ids.push(Number($(this).val()));

		});
		
		if(ids.length == 0 ){
			$("#delete-dialog").toggle();
			$("#delete-dialog-content").css("color", "#A7070B");
			$("#delete-dialog-content").html("<i class='fas fa-exclamation-triangle fa-3x'></i><br /><br />선택된 항목이 없습니다.");	
		} else {
			$("#delete-dialog").toggle();
			$("#delete-dialog-content").css("color", "#552796");
			$("#delete-dialog-content").html("<i class='far fa-trash-alt fa-3x'></i><br /><br />선택한 항목을 삭제하시겠습니까?<br /> <br /> " +
					"<input id='deleteBtn' class='mybtn text-center' value='삭제'> <br />")	
			$("#deleteBtn").click(function() {
			$.ajax({
				type : 'post',
				url : 'deleteMessage',
				data : JSON.stringify(ids),
				traditional : true,
				contentType : "application/json; charset=UTF-8",
				async : false,
				success : function(response) {

					if (response > 0) {

						location.reload();
					}

				},
				error : function(data) {
				}
			}); // ajax end
		}); // inner click end
			} // else end
		});
	
	$("#delete-close, #done-close").click(function() {
		
		$("#delete-dialog, #done-dialog").toggle();
		
	});

	

}); // end function

