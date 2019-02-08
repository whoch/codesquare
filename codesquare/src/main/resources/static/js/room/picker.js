/**
 * 
 */


$(function() {
    $( "#testDatepicker" ).datepicker({
    	language: 'en',
    	dateFormat: 'yyyy-mm-dd',
        minDate: new Date()
    });
});
  // 콤보박스가 변경될 때 
  $('#lstFavorites').change(function () {
      // 드롭다운리스트에서 선택된 값을 텍스트박스에 출력
      var selectedText = // $("#lstFavorites option:selected").text();
          // $("option:selected").text();
          $(":selected").text();  // 드롭다운리스트가 하나밖에 없다면 이렇게 써도 됨
      $('#txtFavorite').val(selectedText);
  });
