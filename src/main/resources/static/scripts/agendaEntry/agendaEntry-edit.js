let attendanceToDelete = null;
let newAttendanceToDelete = null;

function setAttendanceHashToDelete(tableRow) {
    attendanceToDelete = $('#' + tableRow);
}

function setNewAttendanceHashToDelete(tableRow) {
    newAttendanceToDelete = $('#' + tableRow);
}

$(document).ready(function() {

    countMaxLength('name', 100, 'nameRemainingLength');
    countMaxLength('shortDescription', 100, 'shortDescriptionRemainingLength');
    countMaxLength('longDescription', 8192, 'longDescriptionRemainingLength');
    countMaxLength('placeDescription', 100, 'placeDescriptionRemainingLength');

    $('#startDateTime').mask('00/00/0000 00:00', {
	reverse : false
    });

    $('#endDateTime').mask('00/00/0000 00:00', {
	reverse : false
    });

    function clearModalForm() {
	$("#user").val($("#user option:first").val());
	$('#participated').prop('checked', false);
    }

    $("#addAttendance").on("hidden.bs.modal", function() {
	clearModalForm();
    });

    $('#btnConfirmNewAttendance').on('click', function() {
	if ($("#user").val().trim() === '') {
	    swal("Informação Incompleta", "Você precisa selecionar uma pessoa");
	} else {
	    let selectedOption = $("#user option:selected");
	    let hash = selectedOption.val();
	    let content = '<tr><td>' + selectedOption.text() + '</td>';
	    content += '<td>' + selectedOption.data('email') + '</td>';
	    content += '<td>' + ($("#participated").is(":checked") ? 'Sim' : 'Não') + '</td>';
	    content += '<td><a id="id' + hash + '" href="#" data-toggle="modal" data-target="#newAttendanceRemoval" onclick="setNewAttendanceHashToDelete(\'id' + hash + '\');" data-hash="' + hash + '"><span class="fa fa-trash text-danger"></span></a>';
	    content += '<input type="hidden" name="newAttendanceUserHashes" value="' + hash + '" /><input type="hidden" name="newAttendanceUserParticipated" value="' + $("#participated").is(":checked") + '" /></td></tr>';
	    $('#attendanceContainer').append(content);
	    $('#addAttendance').modal('toggle');
	    selectedOption.attr('disabled', 'disabled');

	    clearModalForm();
	}
    });

    $('#btnConfirmAttendanceDeletion').on('click', function() {
	$('#attendanceContainer').append('<input type="hidden" name="attendanceHashesToRemove" value="' + attendanceToDelete.data('hash') + '" />');
	attendanceToDelete.parent().parent().remove();
	$('#attendanceRemoval').modal('toggle');
	$('#user option[value="' + attendanceToDelete.data('hash') + '"]').removeAttr('disabled');
    });

    $('#btnConfirmNewAttendanceDeletion').on('click', function() {
	newAttendanceToDelete.parent().parent().remove();
	$('#newAttendanceRemoval').modal('toggle');
	$('#user option[value="' + newAttendanceToDelete.data('hash') + '"]').removeAttr('disabled');
    });

    $('#btnUpdateYes').on('click', function() {
	$('#updateCopiesAfter').val($('#updateCopiesAfterModal').val());
	$('#form').submit();
    });
    
    function clearModalForm() {
	$('#deleteCopiesAfter').prop('checked', false);
    }

    $("#confirmDeletion").on("hidden.bs.modal", function() {
	clearModalForm();
    });

});