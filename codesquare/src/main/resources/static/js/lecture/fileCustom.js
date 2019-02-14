var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(function() {
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});
var myDropzone = new Dropzone("#lectureImg-dropzone", {
	url : "file/",
	addRemoveLinks : true,
	maxFiles : 1,
	acceptedFiles : ".jpeg,.jpg,.png,.JPEG,.JPG,.PNG",
	addRemoveLinks: true,
	autoProcessQueue: false,
	init : function() {
        var myDropzone = this;

        // first set autoProcessQueue = false
        $('#upload-button').on("click", function(e) {
              myDropzone.processQueue();
        });

        // customizing the default progress bar
        this.on("uploadprogress", function(file, progress) {

              progress = parseFloat(progress).toFixed(0);

              var progressBar = file.previewElement.getElementsByClassName("dz-upload")[0];
              progressBar.innerHTML = progress + "%";
        });
	},
	success : function(file, response) {
		var imgName = response.serFileNm;
		var oriName = response.orgFileNm;
		if (imgName != '') {
			var adClass = imgName.replace('.', "");
			file.previewElement.classList.add("dz-success");
			file.previewElement.classList.add(adClass);
			var html = '<input type="hidden" name="severFileNm" value="'+ imgName + '">';
			html += '<input type="hidden" name="oriFileNm" value="' + oriName+ '">';
			$("." + adClass).append(html);
		}
	},
	error : function(file, response) {
		filepreviewElement.classList.add("dz-maxsize-error");
		alert("파일은 최대 5개까지만 업로드 가능합니다.");
		$(".dz-maxsize-error").empty();
	}
});
Dropzone.prototype.defaultOptions.init = function () {
    this.hiddenFileInput.removeAttribute('multiple');
    this.on("maxfilesexceeded", function (file) {
        this.removeAllFiles();
        this.addFile(file);
    });
};