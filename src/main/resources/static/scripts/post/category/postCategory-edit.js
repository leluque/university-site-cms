$(document).ready(function() {
    var contextPath = $('meta[name="_context_path"]').attr('content');

    countMaxLength('name', 100, 'nameRemainingLength');
    countMaxLength('description', 200, 'descriptionRemainingLength');
    countMaxLength('imageAlternativeDescription', 100, 'imageAlternativeDescriptionRemainingLength');

    $('#name').focus();

});