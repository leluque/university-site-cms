$(document).ready(function() {
    var contextPath = $('meta[name="_context_path"]').attr('content');

    countMaxLength('name', 100, 'nameRemainingLength');
    countMaxLength('description', 200, 'descriptionRemainingLength');

    $('#name').focus();

});