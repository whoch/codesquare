/**
 * 
 */

var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(function() {
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
    
});
Dropzone.autoDiscover = false;
var result;
//$(".btn-lecture-regist").click(function(){
//	registLectureIntro();
//});

//강의소개등록 함수
var registLectureIntro=function(){
	var result;
	oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
	$.ajax({
		url:'intro/regist',
		type:'POST',
		dataType:'json',
		data:JSON.stringify($("[name=lectureintroResgistForm]").serializeObject()),
		async: false,
		contentType:'application/json; charset=utf-8'			
	}).done(function(data){
		if(data!=0){
			result=data;
		}else
			alert("insert fail");
	}).fail(function(data){
		if(data!=1){
			alert("Load Review Fail");
		}
	})
	return result;
}
$("#lectureImg-dropzone").dropzone({
	url : "/cFile/upload",
	paramName:"thumbnail",
	addRemoveLinks : true,
	method:"POST",
	maxFiles : 1,
	acceptedFiles : ".jpeg,.jpg,.png,.JPEG,.JPG,.PNG",
	addRemoveLinks: true,
	autoProcessQueue: false,
	uploadMultiple: false,
    parallelUploads: 1,
	previewsContainer:'#lectureImg-dropzone',
	headers: {"X-CSRF-TOKEN": token}, 
	init : function() {
		var dZone=this;
		
        this.on("addedfile", function() {
            if (dZone.files[1]!=null){
            	dZone.removeFile(dZone.files[0]);
            }
          });
        $(".btn-lecture-regist").click(function(e){
        	e.preventDefault();
            e.stopPropagation();
            result=registLectureIntro();
            if(result!=0){
            	dZone.processQueue();
            }else{
            	console.log("강의소개글 등록 실패");
            }
        })
        this.on("sending", function(file, xhr, formData) {
            formData.append("id", result);
         });
	},
	success : function(file,response) {
			/*var imgName=response.originalName;
			var adClass = imgName.replace('.', "");
			file.previewElement.classList.add("dz-success");
			file.previewElement.classList.add(adClass);
			var html = '<input type="hidden" name="severFileNm" value="'+ imgName + '">';
			html += '<input type="hidden" name="oriFileNm" value="' + oriName+ '">';
			$("." + adClass).append(html);*/
		location.href="/learn/intro/"+result;
	},
	error : function(file, response) {
		file.previewElement.classList.add("dz-maxsize-error");
		this.removeFile(this.files[0]);
	}
});
