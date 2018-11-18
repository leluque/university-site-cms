let documentToDelete = null;
let documentId = 1;

function setDocumentHashToDelete(tableRow) {
    documentToDelete = $('#' + tableRow);
}

$(document).ready(function() {
    var contextPath = $('meta[name="_context_path"]').attr('content');

    countMaxLength('number', 50, 'numberRemainingLength');
    countMaxLength('description', 200, 'descriptionRemainingLength');
    countMaxLength('discipline', 100, 'disciplineRemainingLength');
    countMaxLength('imageAlternativeDescription', 100, 'imageAlternativeDescriptionRemainingLength');
    countMaxLength('highlightImageAlternativeDescription', 100, 'highlightImageAlternativeDescriptionRemainingLength');

    $('#startDateTime').mask('00/00/0000 00:00', {
	reverse : false
    });

    $('#endDateTime').mask('00/00/0000 00:00', {
	reverse : false
    });
    
    $('#number').focus();
    
    $('#addNewDocument').on('click', function() {
	var newDocument = $('#documentModel').clone();

	let firstLabel = newDocument.find('.documentLabel');
	let newDocumentLabel = 'newDocuments' + (documentId);
	firstLabel.prop('for', newDocumentLabel);
	let inputFile = newDocument.find('input[type=file]');
	inputFile.prop('id', newDocumentLabel)

	let secondLabel = newDocument.find('.documentDescriptionLabel');
	let newDocumentDescriptionLabel = 'newDocuments' + (documentId);
	secondLabel.prop('for', newDocumentDescriptionLabel);
	let inputText = newDocument.find('input[type=text]');
	inputText.prop('id', newDocumentDescriptionLabel)

	newDocument.find('.remove-current-document').removeClass('invisible');

	documentId++;

	$("#documentsContainer").append(newDocument);
    });

    $('#btnConfirmDocumentDeletion').on('click', function() {
	$('#documentContainer').append('<input type="hidden" name="documentsHashesToRemove" value="' + documentToDelete.data('hash') + '" />');
	documentToDelete.parent().parent().remove();
	$('#documentRemoval').modal('toggle');
    });

});