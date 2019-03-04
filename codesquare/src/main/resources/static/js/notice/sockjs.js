var stompClient = null;
var socket = null;
var messages = $("[name=messages]").val();

var notibell = "<img id='menuicon' src='/static/images/noticelist.png'/>"; 
//var notibell = null;
var aline;
$(function() {   

	 socket = new SockJS('/stomp');
	 stompClient = Stomp.over(socket);

	// 연결시점
	stompClient.connect({}, function(frame) {
		console.log("connect OK!");
		//알림갯수 메소드ddㅇㅇ
		counts();
		
		//DB List메소드
		noticeviewList();
		
		stompClient.subscribe("/topic/message", function(data) {
			var message = data.body;
			 
		}); //subscribe 닫음
	});//connect 닫음
}); //레디 function


//ajax 알림리스트 DB
function noticeviewList() {
	$.ajax({
		url:'noticeview',
		type:'GET',
		data:{"messages":messages}
	}).done(function(data){
		var noticeviewContent =""; 
		$.each(data, function(key, value){
			noticeviewContent += "<li>"+notibell+"<a href='" + value.noticeLink + "'>"+value.noticeContent
			+ "<p id=send>" + value.sendDateString + "</p>"+"</br>"+"</a>"+"</li>";
		});
		console.log("noticeviewList나옴")
		
		$("#messages").html(noticeviewContent);
	}).fail(function(data){
		console.log("noticeviewContent실패");
	})
}

//댓글 추가시
	function reply() {
		aline = window.location.href;   //경로
		
		//js 현재시간
		var Now = new Date();
		var getMonth = Now.getMonth()+1 ;
		var times = new Date(Now.getFullYear(),getMonth,Now.getDate() - 34  );

		times = times.getFullYear() + "-" + (times.getMonth() +1 < 10? "0" + 
				(times.getMonth() +1): times.getMonth() +1 ) + "-"  + Now.getDate();
		times += ' ' + 	(Now.getHours() < 10? "0"+(Now.getHours() ) : Now.getHours() );
		times += ':' + (Now.getMinutes() < 10? "0"+(Now.getMinutes() ) : Now.getMinutes());
		
		$.ajax({
			type : 'get',
			//dataType:"json", 
			url : '/send/message',
			data : {
				'qq' : "회원님의 게시글에 댓글이 달렸습니다.",
				'aline' : aline,
				'times' : times 
			},

			success : function() {
				counts();
				pushFunction();
				console.log("reply()작동 ok");
				console.log("현재시간 뽑기: " + aline)
				//허버 var ho = hover {background: #bbb;}
				//aline = window.location.href;
				console.log("경로: " +aline);
				
			$('#messages').prepend("<li>" + notibell + "<a href='" + aline + "'>"+ "회원님의 게시글에 댓글이 달렸습니다.(js)" 
					+"<p id = 'send'>"+ times +"</p>"+"</br>"+"</a>" + "</li>");
	
			//	$('#messages').prepend("<li>" + notibell + "회원님의 게시글에 댓글이 달렸습니다.(js)" +aline2 +"</li>");
			}//success 닫음
			
		}); //ajax 닫음
	}//reply() 닫음

	
//회원가입시 알림발송
	function join() {
		aline = null;   //경로
		
		//js 현재시간
		var Now = new Date();
		var getMonth = Now.getMonth()+1 ;
		var times = new Date(Now.getFullYear(),getMonth,Now.getDate() - 34  );

		times = times.getFullYear() + "-" + (times.getMonth() +1 < 10? "0" + 
				(times.getMonth() +1): times.getMonth() +1 ) + "-"  + Now.getDate();
		times += ' ' + 	(Now.getHours() < 10? "0"+(Now.getHours() ) : Now.getHours() );
		times += ':' + (Now.getMinutes() < 10? "0"+(Now.getMinutes() ) : Now.getMinutes());
		
		$.ajax({
			type : 'get',
			url : '/send/message',
			data : {
				
				'qq' : "코드스퀘어 가입을 환영합니다.^^*(DB) ",
				'aline' : aline,
				'times' : times
			},
			success : function(data) {
				//message = data.body; 
				counts();
				console.log("times: " + times);
				//시간 stlyl없을때 $('#messages').prepend("<li>" + notibell +"코드스퀘어 가입을 환영합니다^^*(js)"+ times +"</li>");
				$('#messages').prepend("<li>" + notibell + "<a href='" + aline + "'>"+ "코드스퀘어 가입을 환영합니다^^*(js)" +"<p id = 'send'>"+ times +"</p>"+"</br>"+"</a>" + "</li>");
			}
		});
	}//join() 닫음
	
//모두 삭제
	function allDelete() {
		$( "#messages" ).empty();
	}

//읽음삭제
	function readDelete() {
		//
	}
	
//닫힘
	function onclose() {
		alret("연결이 끊겼습니다");
	}
	
//연결끊기
	function disconnect() {
	    if (stompClient !== null) {
	        stompClient.disconnect();
	    }
	    console.log("Disconnected");
	}
	
	//카운트 에젝
	function counts2() { 
			var count = document.getElementById('messages').childNodes.length;
			console.log("count나옴" + count);
			document.getElementById("counts").innerText = count;
	}
	
	// 카운트- 쿼리, 컨트롤러 사용시
	function counts() {
		$.ajax({
			url:'coints1',
			type:'GET',
		}).done(function(data){
			document.getElementById("counts").innerHTML = data;
			
		}).fail(function(data){
			console.log("cc 실패");
		})
	}
	
	//push 팝업
	function pushFunction() {
		  var x = document.getElementById("pushbar");
		  x.className = "show";
		  setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
		}

