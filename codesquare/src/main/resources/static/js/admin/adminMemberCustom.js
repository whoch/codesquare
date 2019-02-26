/**
 *1.ID,POINT,BAN순으로 정렬기능
 *2.회원정보 상세보기 모달창.
 *3.회원정보 수정하기 모달창.
 *4.회원탈퇴시키기 모달창.
 *5.CHECKBOX로 회원 제한기능 
 */
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(function() {
	$(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
//	onLoadModalSelect("authorSelector","AuthorInfo");
//	onLoadModalSelect("restrictSelector","RestrictStatusInfo");
});
/*function onLoadModalSelect(content,table){
	var target=$("#"+content);
	$.ajax({
		url:'/admin/member/detail/select',
		type:'GET',
		data:{"table":table}
	}).done(function(data){
		oContent="";
		$.each(data,function(key,value){
			$.each(value,function(key,value){
				console.log(value.key);
			})
			
//			oContent+="<option value=\""++"\">";
		})
		
	}).fail(function(data){
		
	})
}*/

//1.정렬
$(document).on('click','#btn-category-UserId,#btn-category-BanCount,#btn-category-Point',function(){
	var id=getId($(this),2)
	location.href="/admin/member/sort?tag="+id;
})

//2~4.회원정보 상세보기,수정하기,탈퇴시키기 모달창
$(document).on('click','#btn-control-detail,#btn-control-modify,#btn-control-ban',function(){
	var self=$(this);
	var key=getId(self,2);
	var userId=self.closest("tr").attr('id').split('-')[1];
	switch(key){
	case 'detail':
		showMemberModal(userId);
		break;
	case 'modify':
		modifyMemberModal(userId);
		break;
	case 'ban':
		$("#modal-userId").text(userId);
		break;
	}

})
$("#btn-userout-yes").click(function(){
	var userId=$("#modal-userId").text();
	banMemberModal(userId);
})
$("#btn-modal-save").click(function(){
	var obj=new Object();
	obj.UserId=$("#modal-detail-userId").text();
	obj.NickName=$("#modail-detail-nickname").val();
	obj.AuthorId=$("#authorSelector option:selected").val();
	obj.RestrictId=$("#restrictSelector option:selected").val();
	alert("hihi")
	$.ajax({
		url:'/admin/member/detail',
		type:'PUT',
		data:obj
	}).done(function(data){
		if(data!=0){
			userModalHide();
			alertMessage('회원정보 변경이 완료되었습니다.');
		}
	}).fail(function(data){
		alert("MemberInfo Update Failed");
	})
	
})
//2.회원정보 상세보기
function showMemberModal(userId){
	$.ajax({
		url:'/admin/member/detail',
		type:'GET',
		data:{"userId":userId}
	}).done(function(data){
		if(data!=null){
			$("[name=profileImagePath]").val(data.profileImagePath);
			$("#modal-detail-userId").text(data.userId);
			$("#modal-detail-point").text(data.point);
			$("#modail-detail-nickname").val(data.nickName);
			$("#modail-detail-username").val(data.name);
			$("#authorSelector").val(data.authorId).prop("selected",true);
			changeSelectbox("authorbox");
			$("#restrictSelector").val(data.restrictId).prop("selected",true);
			changeSelectbox("restrictbox");
			$("#modal-detail-email").val(data.email);
			$("#modal-detail-signupdate").val(data.signUpDate);
			$("#modal-detail-birth").val(data.dateOfBirth);
		}
	}).fail(function(data){
		alert("Memberinfo Load Failed");
	})
}
//회원정보 상세보기 값 변경 메서드
function changeSelectbox(cName){
	var value=$("."+cName+" select option:selected").text();
	$("."+cName+" .filter-option-inner-inner").text(value);
	$("."+cName+" button").attr('title',value);
}

//3.회원정보 수정하기
function modifyMemberModal(userId){
	
}
//4.회원 탈퇴시키기
function banMemberModal(userId){
	$.ajax({
		url:'/admin/member/out',
		type:'DELETE',
		data:{"userId":userId}
	}).done(function(data){
		if(data!=null){
			userModalHide();
			alertMessage('추방이 완료되었습니다.');
		}
	}).fail(function(data){
		alert("Memberinfo Load Failed");
	})
}

//5.모든 항목 체크
$("#btn-allcheck").click(function(){
	var self=$("#btn-allcheck");
	var status=self.data('status')	
	if(status=='on'){
		$("input[name=form-check-list]:checkbox").prop("checked","");
		self.data('status','off');
	}
	if(status=='off'){
		$("input[name=form-check-list]:checkbox").prop("checked", "checked");
		self.data('status','on');
	}
})
function getCheckedIds(){
	var obj=new Array();
	var count=0;
	$('.form-check-input').each(function() {
        if($(this).is(':checked'))
           obj[count++]=$(this).closest("tr").attr('id').split('-')[1];
     });
	return obj;
}
//6.체크된 유저 등급 변경
$("#btn-change-author").click(function(){
	var userList=getCheckedIds();
	var uContent="";
	
	$.each(userList,function(index,item){
		uContent+="<li class=\"list-group-item\">"+item+"</li>";
	})
	$("#modal-checked-authorlist").html(uContent)
	
})
//7.체크된 유저 제한 변경
$("#btn-change-restrict").click(function(){
	$("#modal-checked-restrcitlist")
	var userList=getCheckedIds();
	var uContent="";
	
	$.each(userList,function(index,item){
		uContent+="<li class=\"list-group-item\">"+item+"</li>";
	})
	$("#modal-checked-restrcitlist").html(uContent)
})

//체크된 유저 권한 및 제한 변경 확인 이벤트 감지
$("#btn-changerestrict-yes,#btn-changeauthor-yes").click(function(){
	var self=$(this);
	var id=getId(self,1);
	var list=getCheckedIds();//체크된 아이디 배열
	var count=0;
	var sBasket;
	var aList=new Array();
	if(id=='changeauthor'){//등급 조정
		var authorId=$("#selectAuthor option:selected").val();
		$.each(list,function(index,item){
			var sBasket={
					id:authorId,
					strId:item
			};
			aList[count++]=sBasket;
		})
		updateUserAuthor(aList);
	}else{//제한 조건 조정
		var restrictId=$("#selectRestrict option:selected").val();
		$.each(list,function(index,item){
			var sBasket={
					id:restrictId,
					strId:item
			};
			aList[count++]=sBasket;
		})
		updateUserRestrict(aList);
	}
			
})
function updateUserAuthor(list){
	$.ajax({
		url:'/admin/member/authors',
		type:'PUT',
		/*traditional: true,*/
		contentType : "application/json; charset=utf-8",
		data:JSON.stringify(list)
	}).done(function(data){
		if(data!=null){
			userModalHide();
			alertMessage('등급 조정이 완료되었습니다.');
		}
	}).fail(function(data){
		alert("Member Authorinfo Update Failed");
	})
}
function updateUserRestrict(list){
	$.ajax({
		url:'/admin/member/restricts',
		type:'PUT',
		traditional: true,
		contentType : "application/json; charset=utf-8",
		data:JSON.stringify(list)
	}).done(function(data){
		if(data!=null){
			userModalHide();
			alertMessage('제제가 완료되었습니다.');
		}
	}).fail(function(data){
		alert("Member Restrictinfo Update Failed");
	})
}









$("#btn-control-detail").click