$(function(){
	var initial_bookmark_status = $('#bookmark-button').hasClass('checked');
	var status_change = false;
	
	$(document).ready(function(){
		var status =  $('#STATUS').val();
		statusButtonView(status);
	});
	
	$('#bookmark-button').click(function(e) {
         $(this).toggleClass('checked').children('i').toggleClass('fas far');
         $(this).find('span').text( $('#bookmark-button i').hasClass('far') ?'찜하기':'찜취소');
         status_change = !status_change;
	});//북마크 클릭 이벤트 끝
	
	$('#submitApply').on('click',  function(){
		//신청양식 빈 값 체크
		$('#applicationform>input').each(function(index){
			if( !$(this).val() ){
				$(this).focus();
				return false;
			}
		});
		
		var applyContent = {};
		for(let i=0, size=$('#applicationform>input').length/2; i<size; i++){
			let key = $('.form-key[name="map'+i+'"]').val();
			let value = $('.form-value[name="map'+i+'"]').val();
			applyContent[key]=value;
		}
		
		var data = {
				boardId		: $('#BOARD_ID').val(),
				applyUserId : $('#USER_ID').val(),
				nickName	: $('#NICKNAME').val(),
				applyContent: JSON.stringify(applyContent)
		}
		
		$.ajax({
			type:'POST',
			url:'/studyWanted/submitApplication',
			data : JSON.stringify(data),
			contentType:'application/json; charset=UTF-8',
			success: function (response) {
					statusButtonView(response);
					$('#application-modal').modal('hide');
					
            },
            error: function (jqXHR, textStatus, errorThrown) {
                  console.log('##### submit wantedWrite : Ajax ERROR #####');
                  console.log('jqXHR.status : ' + jqXHR.status);
            }
	 	});//ajax 끝
	});//스터디 그룹 신청 APPLY
	
	
	var applyCancel = function(){
		var data = {
				boardId		: $('#BOARD_ID').val(),
				applyUserId	: $('#USER_ID').val()
		}
		$.ajax({
			type:'POST',
			url:'/studyWanted/cancelApplication',
			data : JSON.stringify(data),
			contentType:'application/json; charset=UTF-8',
			success: function (response) {
					statusButtonView(response);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                  console.log('##### submit wantedWrite : Ajax ERROR #####');
                  console.log('jqXHR.status : ' + jqXHR.status);
            }
	 	});//ajax
	}//그룹 신청 취소 Cancel
	
	var statusButtonView = function( status ){
		$('#status-button div:visible').hide()
		$('.'+status).show();
	}
	
	var wantedClose = function(){
		var data = {
				status	: 0,
				boardId : $('#BOARD_ID').val(),
				groupId : $('#GROUP_ID').val()
		}
		$.ajax({
			type:'POST',
			url:'/studyWanted/boardClose',
			data : JSON.stringify(data),
			contentType:'application/json; charset=UTF-8',
			success: function (response) {
					statusButtonView(response);
					$('#recruitmentCount').remove();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                  console.log('##### submit wantedWrite : Ajax ERROR #####');
                  console.log('jqXHR.status : ' + jqXHR.status);
            }
	 	});//ajax
	}//모집글 마감
	
	$('#submitClose').on('click',wantedClose);
	$('div[id$="wait-button"]').on('click',applyCancel);
		
	$(window).on('beforeunload', function(){
		if( status_change ){
			var data = {
		 			boardId : $('#BOARD_ID').val(),
		 			status : !initial_bookmark_status,
		 			userId : $('#USER_ID').val()
			} 
			
			 $.ajax({
		  			type: 'post',
		  			url: '/clickBookmark',
		  			data : JSON.stringify(data),
					context : this,
					contentType:'application/json; charset=UTF-8',
		  			success: function (response) {
		            },
		            error: function (jqXHR, textStatus, errorThrown) {
		                  console.log("##### bookmark checked : Ajax ERROR #####");
		            }
		            
		  	});//ajax
		}//if
	});//페이지가 바뀔 때 북마크 변동사항 저장

	
	
});//익명function END
