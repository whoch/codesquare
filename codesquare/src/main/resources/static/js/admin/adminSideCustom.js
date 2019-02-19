/**
 * 
 */

var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(function() {
	$(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
	selectSideTab();
});

function selectSideTab(){
	var pName=window.location.pathname.split('/')[2];
	$(".sidebar-wrapper ul>li").removeClass('active')
	switch(pName){
	case 'board':
		$("#board").addClass('active');
		break;
	case 'member':
		$("#member").addClass('active');
		break;
	case 'lecture':
		$("#lecture").addClass('active');
		break;
	case 'keyword':
		$("#keyword").addClass('active');
		break;
	default:
		$("#dashboard").addClass('active');
		break;
	
	}
}
