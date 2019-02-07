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