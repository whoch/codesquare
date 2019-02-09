<<<<<<< HEAD
/**
 * 
 */


var tag = document.createElement('script');
    tag.src = "https://www.youtube.com/iframe_api";
    var firstScriptTag = document.getElementsByTagName('script')[0];
    firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
    function onYouTubeIframeAPIReady(fileName) {
        var player = new YT.Player('lecture-video', {
            height: '100%',
            width: '100%',
            videoId: '유튜브영상 ID',
            rel : 0, //0으로 해놓아야 재생 후 관련 영상이 안뜸
            events: {
                'onStateChange': onPlayerStateChange
            }
        });
    }
=======
$(function() {
    
    onYouTubeIframeAPIReady();
    createSmartEditor();
});

function createSmartEditor(){
	var oEditors = [];
	nhn.husky.EZCreator.createInIFrame({
		oAppRef : oEditors,
		elPlaceHolder : "notepad",
		sSkinURI : "/static/js/smarteditor2/dist/SmartEditor2Skin.html",
		fCreator : "createSEditor2",
		// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
		bUseModeChanger : false
	});
}
// YOUTUBE
var tag = document.createElement('script');
tag.src = "https://www.youtube.com/iframe_api";
var firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
var player;

function onYouTubeIframeAPIReady() {
	player = new YT.Player('player', {
		height: '910.125px',
		width: '1618px',
		videoId: '2fnpPUs79MQ',
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
function onPlayerReady(event) {
}

$(".lecture-link").click(function(){
	var txt=$(this).text();
	
	if(txt=='강의내용'){
	}else if(txt=='qna'){
	}else{
	}
	
})




















>>>>>>> branch 'master' of https://github.com/catsbi/codesquare.git
