function updateRemainingLength(fieldId, maxLength, infoId) {
    var length = $('#' + fieldId).val().length;
    var length = maxLength - length;
    $('#' + infoId).text(length);
}

function countMaxLength(fieldId, maxLength, infoId) {

    if ($("#" + fieldId).length) {
	updateRemainingLength(fieldId, maxLength, infoId); // Used to show info

	$('#' + fieldId).keyup(function() {
	    updateRemainingLength(fieldId, maxLength, infoId);
	});
    }

}