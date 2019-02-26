var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var boardId=$("[name=id]").val()
$(function() {
	$(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
    createSmartEditor();
});
//전송버튼 클릭이벤트


var oEditors = [];
function createSmartEditor(){
	nhn.husky.EZCreator.createInIFrame({
		oAppRef : oEditors,
		elPlaceHolder : "content",
		sSkinURI : "/static/js/smarteditor2/dist/SmartEditor2Skin.html",
		fCreator : "createSEditor2",
		// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
		bUseModeChanger : false,
		fOnAppLoad : function(){
	    }
	});
}
$("#savebutton").click(function(){
	var txt=$(this).text();
	if(txt=='확인'){
		alert("등록")
		oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
		var content=$("[name=freeform]").serialize();
		insert(content);
		$("#frm").submit();
	}else if(txt=='수정확인') {
		alert("수정완료")
		oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
		var content=$("[name=freeform]").serialize();
		insert(content);
		$("#frm").submit();
	}
})

var tag = document.createElement('script');
tag.src = "https://www.youtube.com/iframe_api";
var firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
var player;

function onYouTubeIframeAPIReady() {
	
	player = new YT.Player('player', {
		height: '100%',
		width: '100%',
		playerVars: {
			'autoplay': 0, // 자동재생
			'controls': 1, // 재생컨트롤 노출여부
			'autohide': 1, // 재생컨트롤이 자동으로 사라질지의 여부
			'rel': 0, // 동영상 재생완료 후 유사동영상 노출여부
			'playsinline': 1, // 현페이지에서 재생
			'wmode': 'transparent'
		},
		events: {
			'onReady': onPlayerReady,
			'onStateChange': onPlayerStateChange
		}
	});
}
var playerState;
var playNum = 0;
function onPlayerStateChange(event) {
	playerState = event.data == YT.PlayerState.ENDED ? 'end' :
		event.data == YT.PlayerState.PLAYING ? 'playing' :
			event.data == YT.PlayerState.PAUSED ? 'stop' :
				event.data == YT.PlayerState.BUFFERING ? 'buffering' :
					event.data == YT.PlayerState.CUED ? 'playready' :
						event.data == -1 ? 'not start' : 'error';
	if (playerState == "end") {
		$(".video-finish").css('display','block');
				
	}
}
function onPlayerReady(){
	if($("body").attr('id')!='write-page'){
	    var data=$("#player").data('modifyVideoid');
		videoLoad(data);
	}
}

function youtubeId(url) {
    var tag = "";
    if(url)  {
        var regExp = /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/;
        var matchs = url.match(regExp);
        if (matchs) {
            tag +=matchs[7];
        }
        return tag;
    }
}
function videoLoad(url){
	var content="";
	$(".lecture-video *:not(#player)").remove();
	$("#player").css('display','block');
	player.cueVideoById(url);
	content+="<input type=\"url\"  id=\"url\"  placeholder=\"https://example.com\"  pattern=\"https://.*\" size=\"30\"  required>";
	content+="<button type=\"button\" id=\"btn-video-change\" class=\"btn btn-secondary btn-block\" style=\"margin-bottom:3px;\">변경</button>";
	$(".video-change-box").html(content);
	$("#player").data('modifyVideoid',url);
}








