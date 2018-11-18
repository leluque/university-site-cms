$(document).ready(function() {

    var contextPath = $('meta[name="_context_path"]').attr('content');

    countMaxLength('registry', 14, 'registryRemainingLength');
    countMaxLength('firstName', 50, 'firstNameRemainingLength');
    countMaxLength('lastName', 50, 'lastNameRemainingLength');
    countMaxLength('name', 100, 'nameRemainingLength');
    countMaxLength('description', 200, 'descriptionRemainingLength');
    countMaxLength('neighborhood', 50, 'neighborhoodRemainingLength');
    countMaxLength('password', 30, 'passwordRemainingLength');
    countMaxLength('repeatPassword', 30, 'repeatPasswordRemainingLength');

    $('#registry').mask('000.000.000-00', {
	reverse : true
    });

    function loadCities(stateHash, callback) {
	$(".loader-backdrop").fadeIn('normal');
	$.ajax({
	    url : contextPath + "states/" + stateHash + "/cities", type : 'get', success : function(result) {
		$("#city").find("option:gt(0)").remove();
		$.each(result, function(index, city) {
		    var option = new Option(city.name, city.hashString);
		    $(option).html(city.name);
		    $("#city").append(option);
		});
		$(".loader-backdrop").fadeOut('normal');
		if (callback) {
		    callback();
		}
	    }, error : function(result) {
		$(".loader-backdrop").fadeOut('normal');
	    }
	});
    }

    $('#state').change(function() {
	loadCities($(this).find(':selected').val())
    });

});