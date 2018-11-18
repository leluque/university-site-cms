let documentToDelete = null;
let statusToDelete = null;
let documentId = 1;
let hashString = $('meta[name="_hashString"]').attr('content');

function setDocumentHashToDelete(tableRow) {
    documentToDelete = $('#' + tableRow);
}

function setStatusHashToDelete(tableRow) {
    statusToDelete = $('#' + tableRow);
}

function showStatus(statusHash) {
    $.get(contextPath + 'posts/' + hashString + "/status/" + statusHash).done(function(object) {
	$('#viewStatusDate').html(object.date);
	$('#viewStatusStatus').html(object.statusName);
	$('#viewStatusDescription').html(object.description);
	$('#viewStatusFile').html(true === object.hasFile ? "<a href='" + contextPath + 'occurrences/' + hashString + "/status/" + statusHash + "/file' target='_blank'>Visualizar arquivo</a>" : "Não existem arquivos associados");
	$('#viewStatus').modal('show');
    }).fail(function(xhr, status, error) {
	swal("Alerta", "Não foi possível exibir as informações da situação. Se isso persistir, contate o administrador do sistema.");
    });
}

$(document).ready(function() {
    var contextPath = $('meta[name="_context_path"]').attr('content');
    var type = $('meta[name="_type"]').attr('content');

    countMaxLength('name', 100, 'nameRemainingLength');
    countMaxLength('shortDescription', 100, 'shortDescriptionRemainingLength');
    countMaxLength('longDescription', 8096, 'longDescriptionRemainingLength');
    countMaxLength('place', 100, 'placeRemainingLength');
    countMaxLength('politicianPositioningExplanation', 1024, 'politicianPositioningExplanationRemainingLength');
    countMaxLength('projectNumberExplanation', 50, 'projectNumberRemainingLength');
    countMaxLength('projectURL', 100, 'projectURLRemainingLength');
    countMaxLength('imageAlternativeDescription', 100, 'imageAlternativeDescriptionRemainingLength');

    $('#category').focus();

    function clearModalForm() {
	$('#statusChangeDescription').val('');
	$("#status").val($("#status option:first").val());
	$('#statusChangeFile').val('');
    }

    $("#addStatus").on("hidden.bs.modal", function() {
	clearModalForm();
    });

    $('#btnConfirmNewStatus').on('click', function() {
	if ($("#status").val().trim() === '') {
	    swal("Informação Incompleta", "Você precisa selecionar uma situação");
	} else if ($("#statusChangeDescription").val().trim() === '') {
	    swal("Informação Incompleta", "Você precisa justificar a mudança de situação");
	} else {
	    const content = '<tr><td>' + $("#status :selected").text() + '</td><td><a href="#" onclick="$(this).parent().parent().remove()"><span class="fa fa-trash text-danger"></span></a><input type="hidden" name="newStatusHashes" value="' + $("#status").val() + '" />' + '<input type="hidden" name="newStatusDescriptions" value="' + $('#statusChangeDescription').val() + '" /></td></tr>';
	    $('#statusContainer').append(content);
	    $('#addStatus').modal('toggle');

	    // Clone the "real" input file element.
	    var real = $("#fileContainer");
	    var cloned = real.clone(true);
	    // Put the cloned element directly after the real element
	    // (the cloned element will take the real input element's place in
	    // your UI after you move the real element in the next step)
	    real.hide();
	    cloned.insertAfter(real);
	    // Move the real element to the hidden form - you can then submit it
	    real.appendTo("#statusContainer");
	    real.prop('id', "fileContainer" + (fileCounter++));

	    clearModalForm();
	}
    });

    $('#btnConfirmStatusDeletion').on('click', function() {
	$('#statusContainer').append('<input type="hidden" name="statusHashesToRemove" value="' + statusToDelete.data('hash') + '" />');
	statusToDelete.parent().parent().remove();
	$('#statusRemoval').modal('toggle');
    });

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