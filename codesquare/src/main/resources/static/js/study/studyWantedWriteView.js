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
	}
	createSmartEditor();
	/* END 스마트에디터 생성  */

	$('#group-skip-button').on('click', function(){
		$('#group-skip-button').toggleClass('select-group');
		console.log(this);
	});
	
	
	
	var boardWrite=function(){
/*		var result;*/
		oEditors.getById["writeContent"].exec("UPDATE_CONTENTS_FIELD", []);
		$.ajax({
			type:'POST',
			url:'/studyWanted/write',
			data:JSON.stringify($("#smartEditorResgistForm").serializeObject()),
			contentType:'application/json; charset=UTF-8',
			success: function (response) {
					console.log('sucess');
            },
            error: function (jqXHR, textStatus, errorThrown) {
                  console.log("##### submit wantedWrite : Ajax ERROR #####");
                  console.log("jqXHR.status : " + jqXHR.status);
                  console.log("jqXHR.statusText : " + jqXHR.statusText);
                  console.log("jqXHR.responseText : " + jqXHR.responseText);
                  console.log("jqXHR.readyState : " + jqXHR.readyState);
            }
	 	});//ajax 끝
	 }       
	 
	
	$('#stdMo_submit').on('click', boardWrite);
	
//	jquery show hide
//	$("ul.topnav li:hidden").length
	
	var groupNotSelected = function(){
		$('#card-groups .card:visible').hide();
		$('#Info-groups .info-list:visible').hide();
		$('#card_none').show();
		$('#info_none').show();
	}
	
	var cardGroupsReturn = function(){
		$('#card-groups .card:visible').hide();
		$('#Info-groups .info-list:visible').hide();
		$('#card_main').show();
		$('#info_main').show();
	}
	
	var clickGroupList = function(){
		var id = $(this).attr('id').substr(5);
		$('#card-groups .card:visible').hide();
		$('#Info-groups .info-list:visible').hide();
		$('#card_'+id).show();     
		$('#info_'+id).show();   
	}
	
	$('#group-skip-button').on('click', groupNotSelected);
	$('.return-button').on('click', cardGroupsReturn);
	$('.group-list .media-body').on('click', clickGroupList);
	
});//END script
/*]]>*/