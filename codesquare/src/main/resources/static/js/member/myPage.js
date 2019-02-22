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

	var userId = $("#userId").val();
	if (userId.indexOf("@") == -1) {
		$(".social").removeAttr('style');
	}

	var checkInstructor = $("#checkInstructor").val();
	if (checkInstructor == 0) {
		// 신청한 내용이 없을 경우
		$("#applyIC").removeAttr('style');
	} else {
		$("#appliedIC").removeAttr('style');
	}

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
				// 고쳐야함
				$('#toInstructorForm').html(data);

			},
			error : function(data) {
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
			// 수정해야함
		}).fail(function(response) {
		})

	});

	// 비밀번호 변경 클릭시

	$("#pwValidate")
			.click(
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
								$("#pwCheckMsg").css("color", "#A7070B");
								$("#pwCheckMsg")
										.html(
												"<i class='fas fa-info-circle'></i> 비밀번호가 일치하지 않습니다.");
								$("#rePassword").focus();
							}
						} else {
							$("#pwCheckMsg").css("color", "#A7070B");
							$("#pwCheckMsg")
									.html(
											"<i class='fas fa-info-circle'></i> 비밀번호는 영 대, 소문자, 숫자 로 이루어진 4자 이상 15자 미만");
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
					// 수정해ㅑ함
					location.reload();
					// $("#pwMsg").css("color", "black");
					// $("#pwMsg").text("비밀번호 변경 완료");

				} else {

					$("#pwMsg").css("color", "#A7070B");
					$("#pwMsg").html(
							"<i class='fas fa-info-circle'></i> 비밀번호 변경 실패");
					// 아이디가 중복하지 않으면 idck = 1

				}
			},
			error : function(error) {
				$("#nickCheckMsg").css("color", "black");
				$("#nickCheckMsg").html(
						"<i class='fas fa-info-circle'></i> error");
			}
		});

	}

	// 닉네임 변경 버튼

	$("#nickValidate")
			.click(
					function() {

						var nickName = $("#nickName").val();
						var regExp = /^[a-zA_Z0-9ㄱ-힣]{2,10}$/;

						if (regExp.test(nickName)) {
							nickChange();
						} else {
							$("#nickCheckMsg").css("color", "#A7070B");
							$("#nickCheckMsg")
									.html(
											"<i class='fas fa-info-circle'></i> 닉네임은 영대소문자,한글,숫자로 이루어진 2자이상 10자 미만");
							$("#nickName").focus();
						}

					});

	function nickChange() {

		var nickName = $("#nickName").val();
		$
				.ajax({
					type : "post",
					data : nickName,
					url : "nickChange",
					contentType : "application/json; charset=UTF-8",
					success : function(response) {

						if (response > 0) {

							$("#nickCheckMsg").css("color", "#A7070B");
							$("#nickCheckMsg")
									.html(
											"<i class='fas fa-info-circle'></i> 이미 존재하는 닉네임 입니다.");
							$("#nickName").focus();

						} else {
							// var a=nickName;
							// '<a onclick="changeNick(' + data + ');">수정</a>';
							// $("#changeNickContent").hide();
							// $("#changeNick").show();
							// location.reload();
							$("#nickCheckMsg").text("");
							$("#openCN").show();
							$("#changeNickForm").hide();
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

	// 이메일 변경 버튼
	$("#emailValidate")
			.click(
					function() {

						var email = $("#email").val();
						var regExp = /[a-zA-Z0-9]{2,}@[a-zA-Z0-9]{2,}.[a-zA-Z0-9]{2,}/i;

						if (regExp.test(email)) {
							emailChange();
						} else {
							$("#emailCheckMsg").css("color", "#A7070B");
							$("#emailCheckMsg")
									.html(
											"<i class='fas fa-info-circle'></i> 올바르지 않은 이메일 양식입니다.");
							$("#email").focus();
						}

					});

	function emailChange() {

		var email = $("#email").val();

		$
				.ajax({
					type : "POST",
					data : email,
					url : "emailChange",
					contentType : "application/json; charset=UTF-8",
					success : function(response) {

						if (response > 0) {

							$("#emailCheckMsg").css("color", "#A7070B");
							$("#emailCheckMsg")
									.html(
											"<i class='fas fa-info-circle'></i> 이미 존재하는 이메일 입니다.");
							$("#email").focus();

						} else {
							$("#openCE").show();
							$("#changeEmailForm").hide();
							$("#emailCheckMsg").text("");
							$("#emailField").html(email);
						}
					},
					error : function(error) {
						$("#emailCheckMsg").css("color", "black");
						$("#emailCheckMsg").html(
								"<i class='fas fa-info-circle'></i> error");
					}
				});
	}
	;

	$("input[name='uploadProfile']").change(function() {
		readURL(this);

	});

	function readURL(input) {
		if (input.files && input.files[0]) {

			var reader = new FileReader();
			reader.onload = function(e) {
				$("#profileImg").attr('src', e.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}

	// 프로필 사진 업데이트

	$("#uploadDone").click(function() {
		uploadProfile();
	});

	function uploadProfile() {

		var formData = new FormData();
		var uploadFile = $("input[name='uploadProfile']");
		var files = uploadFile[0].files;
		formData.append('uploadForm', files[0]);
		//
		$.ajax({
			type : 'post',
			url : 'uploadProfile',
			data : formData,
			processData : false,
			contentType : false,
			success : function(result) {
				location.reload();
			},
			error : function(error) {
				// location.reload();
			}

		});
	}
	;

	$("#introContentForm").hide();

	var ICcount = 0;

	$("#openIC").click(
			function() {
				$("#introContentForm").not(
						$(this).next("#introContentForm").slideToggle())
						.toggle();
				HScount++;
				if (HScount % 2 == 0) {

					$("#openIC").removeClass(" fa-minus");
					$("#openIC").addClass(" fa-chevron-down");
				} else {
					$("#openIC").addClass(" fa-minus");
					$("#openIC").removeClass("fa-chevron-down");
				}
			});

	$("#instructorHistoryForm").hide();

	var HScount = 0;

	$("#openHS").click(
			function() {
				$("#instructorHistoryForm").not(
						$(this).next("#instructorHistoryForm").slideToggle())
						.toggle();
				// $("#openHS").removeClass("fa-chevron-down");
				HScount++;
				if (HScount % 2 == 0) {

					$("#openHS").removeClass(" fa-minus");
					$("#openHS").addClass(" fa-chevron-down");
				} else {
					$("#openHS").addClass(" fa-minus");
					$("#openHS").removeClass("fa-chevron-down");
				}
			});

	$("#changeNickForm").hide();

	$("#openCN")
			.click(
					function() {
						$("#changeNickForm").not(
								$(this).next("#changeNickForm").slideToggle())
								.toggle();
						$("#openCN").hide();
					});

	$("#changeEmailForm").hide();

	$("#openCE").click(function() {
				$("#changeEmailForm").not(
						$(this).next("#changeEmailForm").slideToggle())
						.toggle();
				$("#openCE").hide();
			});

	// 수락 모달창
	$("#acceptMo,  #accept-close").click(function() {
		$("#accept-dialog").toggle();
	});


	// 수락 버튼 클릭시
	
	$("#acceptMoBtn").click(function() {
			var data = {
				applyUserNick : $("#pApplyUserNick").text(),
				applyUserId : $("#pApplyUserId").text(),
				boardId : $("#pBoardId").text()
				}
						$.ajax({
									type : "POST",
									data : data,
									url : "acceptMo",
									data : JSON.stringify(data),
									contentType : "application/json; charset=UTF-8",
									success : function(response) {

										if (response > 0) {
											$("#accept-dialog-content")
													.html("<br /><i class='far fa-check-circle'></i> "
																	+ data.applyUserNick
																	+ " 님의 <br /> 신청 수락 완료<br /><br />");
											 //$("#moStaus").html('수락');
										} else {
										}
									},
									error : function(error) {
										$("#accept-dialog-content")
												.html(
														"<i class='fas fa-exclamation-triangle'></i> 에러 ");
									}
								});
					});

	// 거절 모달창
	$("#declineMo, #decline-close").click(
			function() {
				$("#decline-dialog").toggle();
			});

	// 거절버튼 클릭시

	$("#declineMoBtn").click(function() {
						var boardId = $("#pBoardId").text();
						var declineContent = $("input[name='declineContent'")
								.val();
						if (declineContent.length == 0) {
							$("#declineMsg").css("color", "#A7070B");
							$("#declineMsg")
									.html(
											"<br /><i class='fas fa-exclamation-triangle'></i> 거절사유를 입력해주세요.<br />");
							$("#declineMsg").focus();
						} else {
							declineMo();
						}

					});

	function declineMo() {

		var data = {
			applyUserNick : $("#pApplyUserNick").text(),
			applyUserId : $("#pApplyUserId").text(),
			boardId : $("#pBoardId").text(),
			declineContent : $("input[name='declineContent'").val()
		}

		$.ajax({
			type : "POST",
			data : data,
			url : "declineMo",
			data : JSON.stringify(data),
			contentType : "application/json; charset=UTF-8",
			success : function(response) {

				if (response > 0) {
					$("#decline-dialog-content").html(
							"<br /><i class='far fa-check-circle'></i> "
									+ data.applyUserNick
									+ " 님의 <br /> 신청 거절 완료<br /><br />");
					// $("#moStaus").html('거절');
				} else {
				}
			},
			error : function(error) {
				$("#decline-dialog-content").html(
						"<i class='fas fa-exclamation-triangle'></i> 에러 ");
			}
		});

	}
	
	
	//취소 모달창   
	$("#cancelApply,  #cancel-close").click(function() {
		$("#cancel-dialog").toggle();
	});

	// 취소 버튼 클릭시
	$(".cancelAppBtn").click(function() {
		var data = {
				applyUserNick : $("#aNickName").text(),
				applyUserId : $("#aApplyUserId").text(),
				boardId : $("#aBoardId").text(),
				title : $("#aTitle").text(),
				applyContent : $("#aApplyContent").text()
			};
		
		$.ajax({
			type : "POST",
			data : data,
			url : "cancelApply",
			data: JSON.stringify(data),
			contentType : "application/json; charset=UTF-8",
			success : function(response) {
				if (response > 0) {
					//alert(response);
					$("#cancel-dialog-content").html("<i class='far fa-check-circle'></i> "+data.title+"<br />"+data.applyContent+"<br /> 신청 취소 완료<br /><br />");
					//$("#moStaus").html('수락');
				} else {
				}
			},
			error : function(error) {
				$("#cancel-dialog-content").html("<i class='fas fa-exclamation-triangle'></i> 에러 ");
			}
		});
	})
	;
	//거절 팝오버
	 $('[data-toggle="popover"]').popover();   
	 
	 $("#myMessage").click(function(){
		 var popupX = (window.screen.width / 2) - (555 / 2);
		 var popupY= (window.screen.height /2) - (570 / 2);
		window.open('/message/receivedMessage', '내 쪽지함', 'width=555, height=555, status=no, location=no, scrollbars=yes, left='+ popupX + ', top='+ popupY + ', screenX='+ popupX + ', screenY= '+ popupY);
	 });

	
}); // end function

