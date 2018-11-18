$(document).ready(function() {
    var contextPath = $('meta[name="_context_path"]').attr('content');

    countMaxLength('name', 100, 'nameRemainingLength');
    countMaxLength('shortDescription', 150, 'shortDescriptionRemainingLength');
    countMaxLength('longDescription', 30000, 'longDescriptionRemainingLength');
    countMaxLength('imageAlternativeDescription', 100, 'imageAlternativeDescriptionRemainingLength');
    countMaxLength('highlightImageAlternativeDescription', 100, 'highlightImageAlternativeDescriptionRemainingLength');

    $('#name').focus();

    tinyMCE.baseURL = "/plugins/tinymce"

    tinymce.init({
	selector : '#longDescription', height : 300, plugins : [ 'advlist autolink lists link charmap preview anchor', 'searchreplace visualblocks code fullscreen', 'insertdatetime media table contextmenu paste code' ], toolbar : 'insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link', content_css : '/plugins/tinymce/content.min.css'
    });

});