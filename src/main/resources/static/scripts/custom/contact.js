$(document).ready(function() {
    var contextPath = $('meta[name="_context_path"]').attr('content');

    countMaxLength('name', 100, 'nameRemainingLength');
    countMaxLength('email', 50, 'emailRemainingLength');
    countMaxLength('phone', 50, 'phoneRemainingLength');
    countMaxLength('message', 2000, 'messageRemainingLength');

    $('#phone').mask('00-00000-0000', {
	reverse : true
    });

    $('#name').focus();

});