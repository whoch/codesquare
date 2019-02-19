/**
 * 
 */

$("[name=keyword]").on({
		"keyup":function(e){
			if(e.keyCode==13){
				var obj=getSearchbox();
				lectureSearch(obj);
			}
		}
	})
	$("#btn-search").click(function(){
		var obj=getSearchbox();
		lectureSearch(obj);
	})
	$(".btn-lang-kind").click(function(){
		var obj= new Object();
		obj.langKind=$(this).text();
		lectureSearch(obj);
		
	})
	function getSearchbox(){
		var obj=new Object();
		obj.keyword=$("[name=keyword]").val();
		obj.searchKind=$("[name=searchKind]").val();
		return obj;
	}
	function lectureSearch(param){
		$.ajax({
			url:'/learn/search',
			type:'GET',
			data:param
		}).done(function(data){
			var sResultContent="";
			$.each(data, function(key, value){
				sResultContent+="<li class=\"list-group-item\" >";
				sResultContent+="<a href=\"learn/intro/"+value.id+"\"><div class=\"row lecture-list\">";
				sResultContent+="<div class=\"lecture-thumbnail col-sm-2 align-self-center\">";
				sResultContent+="<img src=\""+value.thumbnailPath+"\"alt=\"강좌썸네일\" /></div>";
				sResultContent+="<div class=\"lecture-body col-sm-7 align-self-center\">";
				sResultContent+="<h4 class=\"lecture-title\"><span>"+value.title+"</span></h4>";
				sResultContent+="<h6 class=\"lecture-tag\">TAG: <span>"+value.tag+"</span></h6>";
				sResultContent+="<h6 class=\"lecture-intro\"><span>"+value.introContent+"</span></h6></div>";
				sResultContent+="<div class=\"lecture-introduction col-sm-1 align-self-center\">";
				sResultContent+="<h5 class=\"introduction-name\"><span>"+value.nickName+"</span></h5></div>";
				sResultContent+="<div class=\"lecture-info col-sm-2 align-self-center\">";
				sResultContent+="<h4 class=\"lecture-info-price\"><span>"+value.priceInfo+"</span></h4><br>";
				sResultContent+="<div class=\"lecture-info-count\"><span class=\"lecture-info-studentCount\">";
				sResultContent+="<h6>수강인원: <span>"+value.studentCount+"</span></h6></span>";
				sResultContent+="<span class=\"lecture-info-completeCount\"><h6>완강인원: <span>"+value.completeCount+"</span></h6></span></div></div></div></a></li>";
			})
			$(".leucture-group").html(sResultContent);
		}).fail(function(data){
				alert("Load Search Fail");
		});
	}	