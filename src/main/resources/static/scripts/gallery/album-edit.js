var contextPath = $('meta[name="_context_path"]').attr('content');

function setHashToDelete(id) {
    $("#hashToDelete").val(id);
}

function removeImage(imageHash) {
    $('#galleryImages').append('<input type="hidden" name="imagesHashesToRemove" value="' + imageHash + '" />');
    $('#id' + imageHash).remove();
}

$(document).ready(function() {

    countMaxLength('name', 100, 'nameRemainingLength');
    countMaxLength('description', 200, 'descriptionRemainingLength');
    countMaxLength('coverAlternativeDescription', 100, 'coverAlternativeDescriptionRemainingLength');

    $('#name').focus();

});