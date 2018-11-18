$(document).ready(function() {
    var contextPath = $('meta[name="_context_path"]').attr('content');

    countMaxLength('name', 100, 'nameRemainingLength');
    countMaxLength('curriculum', 2000, 'curriculumRemainingLength');
    countMaxLength('lattes', 100, 'lattesRemainingLength');
    countMaxLength('homepage', 100, 'homepageRemainingLength');
    countMaxLength('imageAlternativeDescription', 100, 'imageAlternativeDescriptionRemainingLength');

    $('#name').focus();

});