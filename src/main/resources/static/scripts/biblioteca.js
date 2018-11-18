$(document).ready(function() {

    $("#search_form").submit(function(e) {
	e.preventDefault();
	var url = "http://www.biblioceeteps.com.br/#";
	window.open(url + $("#search_field").val(), '_blank');
    });

});