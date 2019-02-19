/**
 * 
 */
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(function() {
	$(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
	createSmartEditor();
});


var oEditors = [];
function createSmartEditor(){
	nhn.husky.EZCreator.createInIFrame({
		oAppRef : oEditors,
		elPlaceHolder : "content",
		sSkinURI : "/static/js/smarteditor2/dist/SmartEditor2Skin.html",
		htParams:{
			bUseVerticalResizer:false,
			bUseModeChanger:false
		},
		fCreator : "createSEditor2",
		// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
		bUseModeChanger : false,
		fOnAppLoad : function(){
	    }

	});
}