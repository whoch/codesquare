$(document).ready(function()
{
	ajaxGetBoard("ComFr","pane");//초기 자유게시판 불러오기
	ajaxGetBoard("NewIt","itpane");
	$(".quickBoard").click(function(){
		var tag=$(this).attr('id');
		var txt=ajaxGetBoard(tag,"pane");
	})

	
	function ajaxGetBoard(tag,pane){
		$.ajax({
			url:'Board/all',
			type:'GET',
			data:{"tag":tag},
			dataType:"json",
			success:function(data){
				var pTarget=document.getElementById(pane);
				var BoardList="";
				for(var i=0; i<data.length; i++){
					BoardList+="<a href=\"board/"+data[i].id+"\" class=\""+tag+" list-group-item list-group-item-action\"><span class=\"list_board_title\">"+data[i].title+
					"</span> <span class=\"badge badge-secondary\">N</span>";
					BoardList+=" <span class=\"badge badge-primary badge-pill\">"+data[i].commentCount+"</span></a>";
				}
				$('a').remove(".quickPost");
				pTarget.innerHTML=BoardList;
				//pane.append(BoardList);
			},
			error:function(){
				
			}
		})
	}
	
});