let contextPath = $('meta[name="_context_path"]').attr('content');

Dropzone.options.myDropzone = {

    // Prevents Dropzone from uploading dropped files immediately
    autoProcessQueue : false, paramName : "images", // The name that will be
    // used to transfer the file
    maxFilesize : 10, // MB
    maxFiles : 50, uploadMultiple : true, parallelUploads : 10, maxFiles : 10,

    init : function() {
	myDropzone = this; // closure

	myDropzone.on("maxfilesexceeded", function(file) {
	    this.removeFile(file);
	});

	// First change the button to actually tell Dropzone to process the
	// queue.
	document.getElementById("upload").addEventListener("click", function(e) {
	    if (!myDropzone.files || !myDropzone.files.length) {
		// Just upload the form.
	    } else {
		// Make sure that the form isn't actually being sent.
		e.preventDefault();
		e.stopPropagation();
		myDropzone.processQueue();
	    }
	});

	// You might want to show the submit button only when
	// files are dropped here:
	this.on("addedfile", function() {
	// Show submit button here and/or inform user to click it.
	});
	// Listen to the sendingmultiple event. In this case, it's the
	// sendingmultiple event instead
	// of the sending event because uploadMultiple is set to true.
	this.on("sendingmultiple", function() {
	// Gets triggered when the form is actually being sent.
	// Hide the success button or the complete form.
	});
	this.on("successmultiple", function(files, response) {
	    // Gets triggered when the files have successfully been sent.
	    // Redirect user or notify of success.
	    window.location.href = contextPath + "admin/gallery/albums";
	});
	this.on("errormultiple", function(files, response) {
	    // Gets triggered when there was an error sending the files.
	    // Maybe show form again, and notify user of error
	    swal("Alerta", "Houve um problema na submissão do formulário. Tente novamente e contate o administrador se o problema persistir.");
	});

    }
};
