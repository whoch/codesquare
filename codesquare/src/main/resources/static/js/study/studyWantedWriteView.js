/*<![CDATA[*/
$(function(){
	/* 스마트에디터 생성 */
	var oEditors = [];
	function createSmartEditor(){
		nhn.husky.EZCreator.createInIFrame({
			oAppRef : oEditors,
			elPlaceHolder : "writeContent", //스마트 에디터 추가하기 위해 지정한 textarea
			sSkinURI : "/static/js/smarteditor2/dist/SmartEditor2Skin.html",
			fCreator : "createSEditor2",
			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
			bUseModeChanger : false,
			htParams : {
				bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
				bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
				bUseModeChanger : 'Editor',			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
				//bSkipXssFilter : true,		// client-side xss filter 무시 여부 (true:사용하지 않음 / 그외:사용)
				//aAdditionalFontList : aAdditionalFontSet,		// 추가 글꼴 목록
				SE2M_FontName: {
					htMainFont: {'id': 'Noto Sans KR','name': 'Noto Sans KR','size': '14','url': '','cssUrl': ''} // 기본 글꼴 설정
				},
				fOnBeforeUnload : function(){
					//alert("완료!");
				}
			},
			fOnAppLoad : function(){
		    }
		});
	};
	createSmartEditor();
	/* END 스마트에디터 생성  */
	
	var boardWrite=function(){
		oEditors.getById['writeContent'].exec('UPDATE_CONTENTS_FIELD', []);
		
		var $recruitmentCount = $('.card-body:visible').find('#recruitmentCount');
		var selectGroupId = $('[name="groupId"]').val();
		if( !selectGroupId ){
			$('#info-message').focus();
			$('#info-message').after('<button id="info-btn" type="button" class="btn btn-warning btn-sm">필수</button>');
			return;
		}else if( !(selectGroupId=='그룹없음') && !$recruitmentCount.val() ){
			$recruitmentCount.focus();
			return;
		}
		
		var applicationForm;
		if( $('#applicationform-area').is(':visible') && $('.question').length>0 ){
			applicationForm =  new Array();
			$('.question').each(function (index, item) {
				applicationForm.push($(this).text());
			});
		}
		
		var data = {
				board : $("#smartEditorResgistForm").serializeObject(),
				applicationForm : JSON.stringify(applicationForm)||'',
				recruitmentCount : $recruitmentCount.val()
		}
		
		$.ajax({
			type:'POST',
			url:'/studyWanted/write',
			data : JSON.stringify(data),
			contentType:'application/json; charset=UTF-8',
			async: false,
			success: function (response) {
					window.location.href = response;
            },
            error: function (jqXHR, textStatus, errorThrown) {
                  console.log('##### submit wantedWrite : Ajax ERROR #####');
                  console.log('jqXHR.status : ' + jqXHR.status);
            }
	 	});//ajax 끝
	 };//boardWrite 종료
	
	$('#stdMo_submit').on('click', boardWrite);
	
	
	var hideVisibleGroupAria = function(){
		$('#card-groups .card:visible').hide();
		$('#Info-groups .info-list:visible').hide();
	};
	
	var setSelectGroupId=function( gruopId ){
		$('[name="groupId"]').val( gruopId );
	};
	
	var notUseApplicationForm = function(){
		$('#applicationform-area').hide();
	};
	
	$('.group-list').on('click', function(){
		setSelectGroupId( $(this).find('.groupId').text() );
		var id = $(this).find('[id^="list_"]').attr('id').substr(5);
		hideVisibleGroupAria();
		$('#card_'+id).show();     
		$('#info_'+id).show(); 
	});
	
	$('#group-skip-button').on('click', function(){
		setSelectGroupId('그룹없음');
		hideVisibleGroupAria();
		$('#card_none').show();
		$('#info_none').show();
	});
	
	$('.return-button').on('click', function(){
		setSelectGroupId('');
		hideVisibleGroupAria();
		notUseApplicationForm();
		$('#card_main').show();
		$('#info_main').show();
	});
	
	$(document).on('click', '#form_used', function(){
		$('#applicationform-area').show();
	});
	
	$(document).on('click', '#form_none', function(){
		$('#applicationform-area').hide();
	});
	
	$(document).on('focus', '#applicationform input', function(){
		var $input = $('#applicationform input').not(this);
		var questionCnt = $('.question').length;
		
		if( questionCnt<4 ){
			for(let i=0; i<$input.length; i++){
				if(  !($input.get(i).value)   ){
					return;
				}
			}
			$('#applicationform').append('<input class="applyTag" type="text" name="question" required/>');
		}
	});
	
	$(document).on('blur', '#applicationform input', function(){
		if( $(this).val() ){
			$(this).replaceWith('<button type="button" class="question btn btn-purple applyTag">'+$(this).val()+'<i class="fas fa-window-close"></i></button>');
		}
	});
	
	$(document).on('click', '#applicationform button', function(){
		$(this).remove();
	});	
	

	
});//END script
/*]]>*/