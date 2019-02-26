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
	$('.chart-area .card-title').each(function () {
        $(this).prop('Counter',0).animate({
            Counter: $(this).text()
        }, {
            duration: 4000,
            easing: 'swing',
            step: function (now) {
                $(this).text(Math.ceil(now));
            }
        });
	})
	getDashboardBoard("ComFr","pane");//초기 자유게시판 불러오기
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


//2.todolist추가버튼
$("#todoContent").on({
	"keyup":function(e){
		if(e.keyCode==13){
			var content=$("#todoContent").val().trim();
			addTodoList(content);
		}
	}
})
function addTodoList(content){
	$.ajax({
		url:'/admin/todolist',
		type:'POST',
		data:{"content":content}
	}).done(function(data){
		if(data!=0){
			var tContent="";
			tContent+="<tr id=\"td-"+data.id+"\">";
			tContent+="<td><div class=\"form-check\">";
			tContent+="<label class=\"form-check-label\">";
			tContent+="<input class=\"form-check-input\" type=\"checkbox\" value=\"\">";
			tContent+="<span class=\"form-check-sign\">";
			tContent+="<span class=\"check\"></span></span></label></div></td>";
			tContent+="<td>"+data.content+"</td>";
			tContent+="<td>"+data.writeDateFomat+"</td>"
			tContent+="<td class=\"td-actions text-right\">";
			tContent+="<button type=\"button\" rel=\"tooltip\" title=\"Edit Task\" class=\"btn btn-primary btn-link btn-sm\">";
			tContent+="<i class=\"material-icons\">edit</i></button>";
			tContent+="<button type=\"button\" rel=\"tooltip\" title=\"Remove\" data-id=\""+data.id+"\" class=\"btn btn-danger btn-link btn-sm\">";
			tContent+="<i class=\"material-icons\">close</i></button></td></tr>";
			$("#todolist-tbody").append(tContent);
			$("#todoContent").val('');
		}
	}).fail(function(data){
			alert("Load Review Fail");
	});
}

//3.todolist 삭제버튼
$(document).on('click','.btn-danger',function(){
	var self=$(this);
	var id=self.data('id');
	deleteTodoList(id);
})
function deleteTodoList(id){
	$.ajax({
		url:'/admin/todolist',
		type:'DELETE',
		data:{"id":id}
	}).done(function(data){
		if(data!=0){
			$("#td-"+id).remove();
		}
	}).fail(function(data){
			alert("Load Review Fail");
	});
}


//4.관리자노트 저장하기
$("#btn-memo-save").click(function(){
	console.log("hi");
	oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
	var content=$("[name=content]").val();
	
	$.ajax({
		url:'/admin/memo',
		type:'PUT',
		data:{"content":content}
	}).done(function(data){
		if(data!=0){
			$("#btn-memo-save").text('저장완료').attr('disabled',true);
			setTimeout(function() {
				$("#btn-memo-save").text('저장').attr('disabled',false);
				}, 3000);
		}
	}).fail(function(data){
		alert("Save Memo Fail");
	})
})

function getAdminNoteContent(){
	
}

$(".quickBoard").click(function(){
	var tag=$(this).attr('id');
	var txt=getDashboardBoard(tag,"pane");
})

//5.분류별 게시판 글 불러오기
function getDashboardBoard(tag,pane){
	$.ajax({
		url:'Board/all',
		type:'GET',
		data:{"tag":tag},
		dataType:"json",
		success:function(data){
			var pTarget=document.getElementById(pane);
			var dContent="";
			$.each(data, function(key, value){
				
				var title=value.title;
				title=textLengthOverCut(title,17,'...');
				
				dContent+="<tr id=\"post-"+value.id+"\"><td class=\"text-center\">"+value.id+"</td><td title=\""+value.title+"\"style=\"text-align: left;\"><span>"+title+"</span></td><td>"+value.nickName+"</td><td>"+value.hit+"</td><td><span class=\"badge badge-primary\">N</span> <span class=\"badge badge-primary\">"+value.commentCount+"</span></td>";
				dContent+="<td class=\"td-actions text-right\">";
				dContent+="<button type=\"button\" rel=\"tooltip\" class=\"btn btn-info btn-view\"><i class=\"material-icons\">search</i></button>";
				dContent+="<button type=\"button\" rel=\"tooltip\" class=\"btn btn-danger\ btn-delete\"><i class=\"material-icons\">delete</i></button></td></tr>";

			})
			pTarget.innerHTML=dContent;
			//pane.append(BoardList);
		},
		error:function(){
			
		}
	})
}
/** @param txt<br/>
 *  @param len : 생략시 기본값 20<br/>
 *  @param lastTxt : 생략시 기본값 "..."<br/>
 *  @returns 결과값
 * <br/>
 * <br/>
 * 특정 글자수가 넘어가면 넘어가는 글자는 자르고 마지막에 대체문자 처리<br/>
 *  ex) 가나다라마바사 -> textLengthOverCut('가나다라마바사', '5', '...') : 가나다라마...<br/>
 */
function textLengthOverCut(txt, len, lastTxt) {
    if (len == "" || len == null) { // 기본값
        len = 20;
    }
    if (lastTxt == "" || lastTxt == null) { // 기본값
        lastTxt = "...";
    }
    if (txt.length > len) {
        txt = txt.substr(0, len) + lastTxt;
    }
    return txt;
}






