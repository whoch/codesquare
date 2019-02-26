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
	  $("#input-searchbox").on("keyup", function() {
		var value = $(this).val().toLowerCase();
		$(".container-keyword-group span").filter(function() {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		});
	});
});

$(".keyword-item").click(function(){
	var self=$(this);
	var status=self.data('status');
	if(status=='unchecked'){
		self.removeClass('unchecked');
		self.addClass('checked');
		self.data('status','checked')
	}else if(status=='checked'){
		self.removeClass('checked');
		self.addClass('unchecked');
		self.data('status','unchecked')
	}
})
$("#btn-allcheck").click(function(){
	var self=$("#btn-allcheck");
	var status=self.data('status')	
	if(status=='on'){
		$(".keyword-item").removeClass("checked")
		$(".keyword-item").addClass("unchecked")
		$(".keyword-item").data('status','unchecked')
		self.data('status','off');
	}
	if(status=='off'){
		$(".keyword-item").removeClass("unchecked")
		$(".keyword-item").addClass("checked")
		$(".keyword-item").data('status','checked')
		self.data('status','on');
	}
})
function getKeywordObj(){
	var obj;
	var arr=new Array();
	var count=0;
	$(".container-keyword-group .checked").each(function(){
		var self=$(this);
		obj=new Object;
		obj.id=self.attr('id');
		obj.content=self.text();
		arr[count++]=obj;
	})
	console.log(arr);
	return arr;
}
$("#btn-keyword-delete").click(function(){
	var kList=getKeywordObj();
	var kContent="";
	if(kList.length<1){
		modalAlertMessage("Warning","1개 이상의 강의를 선택해 주세요.")
	}
	if(kList.length>=1){
		$.each(kList,function(index,item){
			kContent+="<li id=\"key-"+item.id+"\"  class=\"kList list-group-item list-group-item-action\" >"+item.content+"</li>";
		})
		$("#modal-checked-keywordlist").html(kContent)
		$("#modal-keyword-delete").modal('show');
	}
})
$("#btn-delete-yes").click(function(){
	var obj=getKeywordObj();
	deleteBlackKeyword(obj);
})
function deleteBlackKeyword(list){
	$.ajax({
		url : '/admin/delete/keyword',
		type: 'DELETE',
		traditional : true,
		contentType : "application/json; charset=utf-8",
		data : JSON.stringify(list)
	}).done(function(data) {
		if (data != null) {
			userModalHide();
			alertMessage('금칙어 삭제가 완료되었습니다.');
		}
	}).fail(function(data) {
		alert("Keyword Delete Failed");
	})
}

$(document).on('click','.list-group-item',function(){
	var self=$(this);
	var id=self.attr('id').split('-')[1];
	$("#"+id).removeClass("checked")
	$("#"+id).addClass("unchecked")
	$("#"+id).data('status','unchecked')
	self.remove();
})
$(document).on('click','.temp-kList',function(){
	$(this).remove();
})

$("#input-blackKeyword").on({
	"keyup":function(e){
		if(e.keyCode==13){
			var content=$("#input-blackKeyword").val().trim();
			var contentHTML= "<li class=\"temp-kList list-group-item list-group-item-action\">"+content+"</a>";
			$(".input-keyword-container").append(contentHTML);
			$("#input-blackKeyword").val('');
		}
	}
})
$("#btn-insert-clear").click(function(){
	$(".input-keyword-container").text('');
})

$("#btn-insert-yes").click(function(){
	var list=new Array();
	var count=0;
	$(".temp-kList").each(function(){
		list[count++]=$(this).text();
	})
	console.log(list);
	if(list.length<1){
		confirm("추가할 금칙어가 없습니다.")
	}else{
		insertKeywordList(list);
	}
})


function insertKeywordList(list){
	$.ajax({
		url : '/admin/insert/keyword',
		type: 'POST',
		traditional : true,
		contentType : "application/json; charset=utf-8",
		data : JSON.stringify(list)
	}).done(function(data) {
		if (data != null) {
			userModalHide();
			alertMessage('금칙어 추가가 완료되었습니다.');
		}
	}).fail(function(data) {
		alert("Keyword Insert Failed");
	})
}




















