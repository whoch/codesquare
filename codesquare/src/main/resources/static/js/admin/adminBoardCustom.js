
//즐겨찾기 모달창 관리 함수

//1.게시판명 클릭시 추가 이벤트
$("#list-boardKind .list-group-item").click(function(){
	var self=$(this);
	var targetContainer=$("#list-bookark-addedList");
	
	var appendItem="<span class=\"list-group-item list-group-item-action\" value=\""+self.attr('id').split('-')[1]+"\">"+self.text()+"</span>";
	targetContainer.append(appendItem)
	
})