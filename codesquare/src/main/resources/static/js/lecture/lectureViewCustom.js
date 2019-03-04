
var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
$(function() {
	$(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
    createSmartEditor();
    
    
});
var boardId = $("[name=boardId]").val();
var boardKindId = $("[name=boardKindId]").val();
var nickName=$(".comment-writerInfo>[name=nickName]").val();
var userId=$(".comment-writerInfo>[name=userId]").val();
var parentId=$("#parent-link").data('parentid');
var duartion;
var videoCurrentTime=$("#currentTime").val()
var oEditors = [];

//YOUTUBE
var tag = document.createElement('script');
tag.src = "https://www.youtube.com/iframe_api";
var firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
var player;
var btnContent;
function createSmartEditor(){
	nhn.husky.EZCreator.createInIFrame({
		oAppRef : oEditors,
		elPlaceHolder : "notepad",
		sSkinURI : "/static/js/smarteditor2/dist/SmartEditor2Skin.html",
		fCreator : "createSEditor2",
		// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
		bUseModeChanger : false,
		fOnAppLoad : function(){
			addNoteContent();
	    }

	});
}


function onYouTubeIframeAPIReady() {
	var videoUrl=$("#player").data('modifyVideoid');	
	player = new YT.Player('player', {
		height: '910.125px',
		width: '1618px',
		videoId: videoUrl,
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
	if (playerState == "playready"){
		
	}
	if (playerState == "end") {
		$(".video-overaybox").css('display','block');
		btnContent="";
		btnContent+="<button type=\"button\" id=\"nextLecture\"class=\"btn btn-lg btn-secondary btn-yt\">다음 강의듣기</button>";
		btnContent+="<button type=\"button\" id=\"replay\"class=\"btn btn-lg btn-secondary btn-yt\">다시 보기</button>";
		btnContent+="<button type=\"button\" id=\"goQna\"class=\"btn btn-lg btn-secondary btn-yt\">질문하러가기</button>";
		$("#modal-container").html(btnContent);
	}
}
function onPlayerReady(event) {
	$(".video-overaybox").css('display','block');
	btnContent="";
	if(videoCurrentTime!=0){
		btnContent+="<button type=\"button\" id=\"btn-continue\"class=\"btn btn-lg btn-secondary btn-yt\">이어보기</button>";
	}
	btnContent+="<button type=\"button\" id=\"btn-begin\"class=\"btn btn-lg btn-secondary btn-yt\">처음부터보기</button>";
	$("#modal-container").html(btnContent);
}

$(".lecture-link").click(function(){
	var txt=$(this).text();
	
	if(txt=='강의내용'){
	}else if(txt=='qna'){
	}else{
	}
	
})

//노트 정보 가져와서 붙혀넣기
function addNoteContent(){
	$.ajax({
		url:'/learn/note',
		type:'GET',
		data:{"boardId":boardId, "userId":userId}
	}).done(function(data){
		var sHTML=data;
		oEditors.getById["notepad"].exec("SET_IR", [""]); //내용초기화
		oEditors.getById["notepad"].exec("PASTE_HTML", [sHTML]); //내용밀어넣기
	}).fail(function(data){
		if(data!=1){
			alert("Load Review Fail");
		}
	});
}

//필기정보 저장
function saveNoteContent(content){
	$.ajax({
		url:'/learn/note',
		type:'PUT',
		data:{"boardId":boardId, "userId":userId,"content":content}
	}).done(function(data){
		var sHTML=data;
		console.log(data);
		alert("저장완료");
	}).fail(function(data){
		if(data!=1){
			alert("Load Review Fail");
		}
	});
}
function playYoutube() {
    // 플레이어 자동실행 (주의: 모바일에서는 자동실행되지 않음)
    player.playVideo();
}
function pauseYoutube() {
    player.pauseVideo();
}
function waypointStartYoutube(time) {
	if(player){
	    player.seekTo(time, true);     // 영상의 시간을 0초로 이동시킨다.
//	    player.playVideo();
	}
}
$(document).on('click','#btn-begin, #btn-continue, #replay, #goQna, #nextLecture', function(){
	var eventId=$(this).attr('id');
	switch(eventId){
		case 'btn-begin': waypointStartYoutube(0);
			$(".video-overaybox").css('display','none');
			break;
		case 'btn-continue':waypointStartYoutube(videoCurrentTime);
			$(".video-overaybox").css('display','none');
			break;
		case 'replay':waypointStartYoutube(0);
			$(".video-overaybox").css('display','none');
			break;
		case 'goQna':
			$("#collapseTwo").collapse('show')
			var offset = $("#collapseTwo").offset();
	        $('html, body').animate({scrollTop : offset.top}, 400);
			break;
		case 'nextLecture':
	}
})




$("#save").click(function() {
	oEditors.getById["notepad"].exec("UPDATE_CONTENTS_FIELD", []);
	saveNoteContent($("#notepad").val());
});

/*우측상단 이전,목록,다음강의 버튼 구성
기능1. 강의목록을 가져온다.
기능2. 지금 강의가 첫강의인지 마지막강의인지 반환한다.
function getLectureListInfo(){
	$.ajax({
		url:'lectureBtn',
		type:'PUT',
		data:{"boardId":boardId, "userId":userId,"content":content}
	}).done(function(data){
		var sHTML=data;
		console.log(data);
		alert("저장완료");
	}).fail(function(data){
		if(data!=1){
			alert("Load Review Fail");
		}
	});
}*/

//목록 토글 기능
$("#btn-lectureList").click(function(){
	var lContainer=$(".lecture-list-container");
	var status=lContainer.data('status');
	if(status=='invisible'){
		lContainer.slideDown(1000);
		lContainer.data('status','visible');
	}else{
		lContainer.slideUp(1000);
		lContainer.data('status','invisible');
	}

})
//top으로 가기 waypoint
// ===== Scroll to Top ==== 
$(window).scroll(function() {
    if ($(this).scrollTop() >= 310) {    // If page is scrolled more than 50px
        $('#top').fadeIn("fast");       // Fade in the arrow
    } else {
        $('#top').fadeOut("fast");      // Else fade out the arrow
    }
});
$('#top').click(function() {            // When arrow is clicked
    $('body,html').animate({
        scrollTop : 0                   // Scroll to top of body
    }, 500);
});

//강의내용 수정하기 버튼 
$(document).on('click','#lecture-content-modify',function(){
	location.href="/learn/intro/"+parentId+"/course/post/"+boardId;
})

//$("#btn-nextLecture").click(function(){
$(window).on("beforeunload",function(){
	getDuration();
	var llInfo=new Object()
	llInfo.userId=userId;
	llInfo.parentId=parentId;
	llInfo.boardId=boardId;
	llInfo.currentTime=videoCurrentTime;
	llInfo.duration=duration;
	updateLearningLogInfo(llInfo);
})
function updateLearningLogInfo(llInfo){
	$.ajax({
		url:'/learn/learninglog/update',
		type:'PUT',
		data:llInfo
	}).done(function(data){
		console.log("로그저장완료");
		return;
	}).fail(function(data){
			alert("Log Save Fail");
	});
}
window.onYouTubePlayerReady = function(playerId) {
    window.player = document.getElementById("player");
    getDuration();
}


function getDuration() {
    if (player) {
        duration = player.getDuration();
        videoCurrentTime=player.getCurrentTime();
    }
}


