$(document).ready(function() {
    var contextPath = $('meta[name="_context_path"]').attr('content');

    countMaxLength('registry', 30, 'registryRemainingLength');
    countMaxLength('name', 100, 'nameRemainingLength');
    countMaxLength('email', 50, 'emailRemainingLength');
    countMaxLength('phone', 50, 'phoneRemainingLength');
    countMaxLength('comments', 400, 'commentsRemainingLength');

    $('#phone').mask('00-00000-0000', {
	reverse : true
    });
    
    $('#registry').focus();
    
});