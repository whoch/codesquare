/**
 * 1.로드시 현재 나온 강의목록태그 태그박스에서 선택되도록 하는 메서드.
 * 2.태그박스 클릭시 해당 태그 영상만 나오게하는 기능.
 * 3.강의소개글 클릭시상세보기 이동
 */

var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(function() {
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});
	onLoadChecktab();
	onLoadSwitchStatus();
});

function onLoadSwitchStatus(){
	var status=getCookie("isPLV");
	if(status=='true'){
		$("#switch-sm").prop("checked","checked")
		$("[data-isPending=1]").addClass('visible');
		$("[data-isPending=1]").removeClass('invisible');
	}
}

function onLoadChecktab(){
	var tag=window.location.href.split('=')[1];
	if(tag!=undefined&&tag!='All'){
		$("#"+tag).addClass('active')
	}else{
		$("#All").addClass('active');
	}
}

//2.태그박스 클릭시 해당 태그 강의 검색 기능.
$(".btn-lang-kind").click(function() {
	var langKind = $(this).attr('id');
	location.href = "/admin/lecture/sort?langKind=" + langKind;

})
//검색어 입력내용 뽑아내는 메서드
function getSearchbox() {
	var keyword = $("[name=keyword]").val();
	return keyword;
}

//검색어(keyword) 컨트롤러에 전달하는 메서드
function lectureIntroSearch(keyword){
	location.href="/admin/lecture/search?keyword="+keyword;
}


//3.강의소개글 클릭시 상세보기 이동
$(".area-lectureintro").click(function(){
	alert("hi")
	var id=$(this).closest('tr').attr('id').split('-')[1];
	location.href="/admin/lecture/intro/"+id;
})

//all check
$("#btn-allcheck").click(function(){
	var self=$("#btn-allcheck");
	var status=self.data('status')	
	if(status=='on'){
		$(".visible input[name=form-check-list]:checkbox").prop("checked","");
		self.data('status','off');
	}
	if(status=='off'){
		$(".visible input[name=form-check-list]:checkbox").prop("checked", "checked");
		self.data('status','on');
	}
})

//4.스위치버튼 클릭시 보류글 보이도록 액션
$(".switch").click(function(e){
	e.preventDefault();
	e.stopPropagation();
	var self=$(e)
	var status=self.parent().val();
	pendingLectureViewCalc();
})

//isPLV:isPendingLectureView: 강의글중 보류상태의 글을 볼지 말지 체크여부값
function pendingLectureViewCalc(){
	var status=getCookie("isPLV");
	var isCheck=$("#switch-sm").prop("checked")
		if(isCheck==true&&status=='true'){
		$("[data-isPending=1]").addClass('invisible');
		$("[data-isPending=1]").removeClass('visible');
		$("#switch-sm").prop("checked","")
		setCookie("isPLV","false",1);
	}
	if(isCheck==false&&(status=='false'||status==null)){
		$("[data-isPending=1]").addClass('visible');
		$("[data-isPending=1]").removeClass('invisible');
		setCookie("isPLV","true",1);
		$("#switch-sm").prop("checked","checked")
	}
}

//선택된 강의 삭제 이벤트
function getCheckedTitles(){
	var arr=new Array();
	var obj=new Object();
	var count=0;
	var tmp;
	$('.form-check-input').each(function() {
        if($(this).is(':checked')){
        	tmp=$(this).closest("tr").attr('id');
        	obj=new Object();
        	obj.boardId=tmp.split('-')[1];
        	obj.title=$("#"+tmp+" a").text();
        	arr[count++]=obj;
        	
        }
     });
	return arr;
}
$("#btn-lecture-delete").click(function(){
	var lList=getCheckedTitles();
	
	var rContent="";
	if(lList.length<1){
		modalAlertMessage("Warning","1개 이상의 강의를 선택해 주세요.")
	}
	if(lList.length>=1){
		$.each(lList,function(index,item){
			rContent+="<li id=\""+item.boardId+"\"  class=\"list-group-item\" >"+item.title+"</li>";
		})
		$("#modal-checked-lecturelist").html(rContent)
		$("#modal-lecture-delete").modal('show');
	}
})

$("#btn-delete-yes").click(function(){
	var lList = getCheckedTitles();
	deleteLectureList(lList);
});

function deleteLectureList(lList){
	$.ajax({
		url : '/admin/lecture',
		type : 'DELETE',
		traditional : true,
		contentType : "application/json; charset=utf-8",
		data : JSON.stringify(lList)
	}).done(function(data) {
		if (data != null) {
			userModalHide();
			alertMessage('삭제가 완료되었습니다.');
		}
	}).fail(function(data) {
		alert("Lecture Delete Failed");
	})
}


//강의 pending 버튼 감지
$("#btn-lectureStatus-change").click(function(){
	var lList=getCheckedTitles();
	var rContent="";
	if(lList.length<1){
		modalAlertMessage("Warning","1개 이상의 강의를 선택해 주세요.")
	}
	if(lList.length>=1){
		$.each(lList,function(index,item){
			rContent+="<li id=\""+item.boardId+"\"  class=\"list-group-item\" >"+item.title+"</li>";
		})
		$("#modal-checked-pendinglecturelist").html(rContent)
		$("#modal-lecture-pending").modal('show');
	}
})

//강의 수정 버튼 감지
$("#btn-lecture-modify").click(function(){
	var lList=getCheckedTitles();
	var rContent="";
	if(lList.length<1){
		modalAlertMessage("Warning","1개 이상의 강의를 선택해 주세요.")
	}
	if(lList.length>=1){
		$.each(lList,function(index,item){
			rContent+="<li id=\""+item.boardId+"\"  class=\"list-group-item\" >"+item.title+"</li>";
		})
		$("#modal-checked-pendinglecturelist").html(rContent)
		$("#modal-lecture-pending").modal('show');
	}
})

//보류&보류해제 버튼 이벤트
$("#btn-pending-clear,#btn-pending-yes").click(function(){
	var self=$(this);
	var id=getId(self,2);
	var lList=getCheckedTitles();
	if(id=='clear'){
		$.each(lList,function(index,item){
			item.status=0;
		})
	}else{
		$.each(lList,function(index,item){
			item.status=1;
		})
	}
	changeLecturePendingStatus(lList);
	
})
function changeLecturePendingStatus(lList){
	$.ajax({
		url : '/admin/lecture/pending',
		type : 'PUT',
		traditional : true,
		contentType : "application/json; charset=utf-8",
		data : JSON.stringify(lList)
	}).done(function(data) {
		if (data != 0) {
			userModalHide();
			alertMessage('보류 상태 변경이 완료되었습니다.');
		}
	}).fail(function(data) {
		alert("LectureStatus Change Failed");
	})
}
	
	
//강의수정번튼 클릭
$("#lecture-modify").click(function(){
	location.href="/admin/lecture/intro/post/"+$("[name=boardId]").val();
})
	
	
	
	
	
	
	
	