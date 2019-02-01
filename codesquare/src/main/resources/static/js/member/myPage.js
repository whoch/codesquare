/**
 * 
 */
$(function(){

	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(function() {
	    $(document).ajaxSend(function(e, xhr, options) {
	        xhr.setRequestHeader(header, token);
	    });
	});
	
	
	// 내정보 수정 버튼 클릭 시
	$("#modifyMyInfo").click(function() {
		$("#modifyMyInfo").addClass('active');
		$("#myBoardList").removeClass('active');
		$("#myWantedList").removeClass('active');
		$("#myAppliedList").removeClass('active');
		$("#myReservedList").removeClass('active');
		$.ajax({
			type : 'get',
			url : 'modifyMyInfo',
			dataType : 'html',
			async: false,
			success : function(data) {
				// alert("id");
				$("#contentcontainer").html(data);
			},
			error : function(data){
				alert("에러");
			}
		});
	});

	// 나의 예약 및 결제 내역 클릭시
	$("#myReservedList2").click(function() {
		$("#myReservedList2").addClass('active');
		$("#myBoardList").removeClass('active');
		$("#myWantedList").removeClass('active');
		$("#myAppliedList").removeClass('active');
		$("#modifyMyInfo").removeClass('active');
		$.ajax({
			type : 'get',
			url : 'myReservedList',
			dataType : 'html',
			async: false,
			success : function(data) {
				// alert("id");
				$("#contentcontainer").html(data);
			},
			error : function(data){
				alert("에러");
			}
		});
	});

	// 나의 신청 내역 클릭시
	$("#myAppliedList").click(function() {
		$("#myAppliedList").addClass('active');
		$("#myReservedList2").removeClass('active');
		$("#myWantedList").removeClass('active');
		$("#myBoardList").removeClass('active');
		$("#modifyMyInfo").removeClass('active');
		$.ajax({
			type : 'get',
			url : 'myAppliedList',
			dataType : 'html',
			async: false,
			success : function(data) {
				// alert("id");
				$("#contentcontainer").html(data);
			},
			error : function(data){
				alert("에러");
			}
		});
	});
	// 나의 모집 내역 클릭시
	$("#myWantedList").click(function() {
		$("#myWantedList").addClass('active');
		$("#myReservedList2").removeClass('active');
		$("#myAppliedList").removeClass('active');
		$("#myBoardList").removeClass('active');
		$("#modifyMyInfo").removeClass('active');
		$.ajax({
			type : 'get',
			url : 'myWantedList',
			dataType : 'html',
			async: false,
			success : function(data) {
				// alert("id");
				$("#contentcontainer").html(data);
			},
			error : function(data){
				alert("에러");
			}
		});
	});

	// 내가 쓴 글 클릭시
	$("#myBoardList").click(function() {
		$("#myBoardList").addClass('active');
		$("#myReservedList2").removeClass('active');
		$("#myWantedList").removeClass('active');
		$("#myAppliedList").removeClass('active');
		$("#modifyMyInfo").removeClass('active');
		$.ajax({
			type : 'get',
			url : 'myBoardList',
			dataType : 'html',
			async: false,
			success : function(data) {
				// alert("id");
				$("#contentcontainer").html(data);
			},
			error : function(data){
				alert("에러");
			}
		});
	});
	// 나의 예약 및 결제 내역 클릭시
	$("#myReservedList").click(function() {
		$("#myReservedList2").addClass('active');
		$("#myBoardList").removeClass('active');
		$("#myWantedList").removeClass('active');
		$("#myAppliedList").removeClass('active');
		$("#modifyMyInfo").removeClass('active');
		$.ajax({
			type : 'get',
			url : 'myReservedList',
			dataType : 'html',
			async: false,
			success : function(data) {
				// alert("id");
				$("#contentcontainer").html(data);
			},
			error : function(data){
				alert("에러");
			}
		});
	});

	// 강사 전환 신청하기 클릭시
	$("#toInstructor").click(function() {
		$("#myReservedList2").removeClass('active');
		$("#myBoardList").removeClass('active');
		$("#myWantedList").removeClass('active');
		$("#myAppliedList").removeClass('active');
		$("#modifyMyInfo").removeClass('active');
		$.ajax({
			type : 'get',
			url : 'toInstructor',
			dataType : 'html',
			async: false,
			success : function(data) {
				// alert("id");
				$("#contentcontainer").html(data);
			},
			error : function(data){
				alert("에러");
			}
		});
	});

	// 강사전환신청하기 완료시

	$("#toInstructorDone").click(function() {

		// var data = {
		// introContent : $("introContent").val(),
		// userId : $("userId").val()
		// }
		var introContent = $("#introContent").val();

		//		
		$.ajax({
			type : 'post',
			url : 'toInstructor',
			data : introContent,
			contentType : "application/json; charset=UTF-8",
			async: false,
			success : function(response) {

				// $("#toInstructorForm").attr('action',
				// "toInstructor").submit();
				$("#contentcontainer").html(response);
				// $(location).attr('href', data);

			},
			error : function(data){
				alert("에러");
			}
		});
	});

	// 비밀번호 바꾸기 버튼 클릭 시
	$("#changePw").click(function() {
		$("#myReservedList2").removeClass('active');
		$("#myBoardList").removeClass('active');
		$("#myWantedList").removeClass('active');
		$("#myAppliedList").removeClass('active');
		$("#modifyMyInfo").removeClass('active');

		$.ajax({
			type : 'get',
			url : 'changePw',
			dataType : 'html',
			async: false,
			success : function(data) {
				// alert("id");
				$("#contentcontainer").html(data);
			},
			error : function(data){
				alert("에러");
			}
		});
	});

	// 강사 정보 수정하기 클릭시

	$("#modifyInstructorInfo").click(function() {

		$("#myReservedList2").removeClass('active');
		$("#myBoardList").removeClass('active');
		$("#myWantedList").removeClass('active');
		$("#myAppliedList").removeClass('active');
		$("#modifyMyInfo").removeClass('active');

		$.ajax({
			type : 'get',
			url : 'modifyInstructorInfo',
			dataType : 'html',
			async: false
		})
			.done (function(data) {
				// alert("id");
				$("#contentcontainer").html(data);
			})
			.fail( function(data){
				alert("에러");
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
			contentType: 'application/json; charset=UTF-8',
			async: false
		})
			.done (function(response) {
				 alert("수정완료");
				$("#contentcontainer").html(response);
			})
			.fail(function(response){
				alert("에러");
			})
		
	});
	
	$(".detail").hide();
	//content 클래스를 가진 div를 표시/숨김(토글)
	  $(".heading").click(function()
	  {
	    $(".detail").not($(this).next(".detail").slideToggle()).hide();
	  });
	



}); // end function
// 비밀번호 찾기 버튼 클릭시
// $("#findPwBar").click(function(){
// $.ajax({ㄴ
// type: 'post',
// url : 'findPw',
// dataType : 'html',
// success : function(data) {
// //alert("pw");
// $("#content").html(data);
// }
// });
// })
//
//	
// function emailValidate() {
//
// var email = $("#email").val();
// var regExp = /[a-zA-Z0-9]{2,}@[a-zA-Z0-9]{2,}.[a-zA-Z0-9]{2,}/i;
//
// if (regExp.test(email)) {
// findIdDone();
// } else {
// $("#emailCheckMsg").css("color", "red");
// $("#emailCheckMsg").text("올바르지 않는 이메일 형식입니다.");
// $("#email").focus();
// }
//
// };
// function findIdDone() {
//	
// }
//

