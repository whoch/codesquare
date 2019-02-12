
$(function() {
    onYouTubeIframeAPIReady();
    createSmartEditor();
    
    
});
var boardId = $("[name=boardId]").val();
var boardKindId = $("[name=boardKindId]").val();
var nickName=$(".comment-writerInfo>[name=nickName]").val();
var userId=$(".comment-writerInfo>[name=userId]").val();
var oEditors = [];
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

//노트 정보 가져와서 붙혀넣기
function addNoteContent(){
	$.ajax({
		url:'note',
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
		url:'note',
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
	var status=$(".lecture-list-container").data('status');
	if(status=='invisible'){
	  $(".lecture-list-container").animate({
	    left:'-50%'
	  },"slow")
	  $(".lecture-list-container").data('status','visible');
	}else{
		$(".lecture-list-container").animate({
		    left:'100%'
		  },"slow")
		  $(".lecture-list-container").data('status','invisible');
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


$(document).on('click','#lecture-content-modify',function(){
	location.href="/learn/course?boardId="+boardId;
})




