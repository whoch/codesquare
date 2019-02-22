/**
 * 
 */

$(function() {

	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(function() {
		$(document).ajaxSend(function(e, xhr, options) {
			xhr.setRequestHeader(header, token);
		});
	});

});

$("input[name='uploadProfile']").change(function() {
	readURL(this);
});

function readURL(input) {
	if (input.files[0]) {
		var reader = new FileReader();

		reader.onload = function(e) {
			$("#profileImg").attr('src', e.target.result);
		}
		reader.readAsDataURL(input.files[0]);
	}
}

// 프로필 사진 업데이트

$("#uploadDone").click(function() {
	uploadProfile();
});

function uploadProfile() {

	var formData = new FormData();
	var uploadFile = $("input[name='uploadProfile']");
	var files = uploadFile[0].files;
	formData.append('uploadForm', files[0]);
	//
	$.ajax({
		type : 'post',
		url : 'uploadProfile',
		data : formData,
		processData : false,
		contentType : false,
		success : function(result) {
			alert("업로드 완료");
		},
		error : function(error) {
			// location.reload();
			alert("업로드 실패");
		}

	});

	// var frm = ("form[name='profileUpload']");
	// frm.method = 'POST';
	// frm.enctype = 'multipart/form-data';
	//
	// var fileFormValue = $("#uploadProfile").val();
	// var uploadProfile = new FormData(frm);
	//
	// console.log("fileData:" + fileFormValue);
	//
	// if (fileFormValue != null && fileFormValue != {} && fileFormValue != '')
	// {
	// $.ajax({
	//
	// url : 'upload',
	// type : 'POST',
	// data : uploadProfile,
	// async : false,
	// cache : false,
	// contentType : false,
	// processData : false
	// }).done(function(response) {
	//
	// alert("프로필변경이 완료되었습니다.");
	// location.reload();
	// });
	// } else {
	// alert("파일이 첨부되지 않았습니다.");
	// }
};

