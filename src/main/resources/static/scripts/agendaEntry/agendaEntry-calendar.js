function setHashToDelete(id) {
    $("#hashToDelete").val(id);
}

$(document).ready(function() {
    var contextPath = $("meta[name='_context_path']").attr("content");

    $('#newEvent').on('keydown', function(event) {
	if (13 === event.which) {
	    event.preventDefault();
	    $("#addNewEvent").trigger("click");
	}
    });

    /**
     * Initialize the events that are not allocated yet.
     */
    function ini_events(ele) {
	ele.each(function() {

	    // create an Event Object
	    // (http://arshaw.com/fullcalendar/docs/event_data/Event_Object/)
	    // it doesn't need to have a start or end
	    var eventObject = {
		title : $.trim($(this).text()), // use the element's text as the
		// event title
		stick : true
	    // maintain when user navigates (see docs on the renderEvent method)
	    // 
	    };

	    // store the Event Object in the DOM element so we can get to it
	    // later
	    $(this).data('eventObject', eventObject);

	    // make the event draggable using jQuery UI
	    $(this).draggable({
		zIndex : 999, revert : true, // will cause the event to go
		// back to its
		revertDuration : 0
	    // original position after the drag
	    });

	});
    }

    ini_events($('#external-events .fc-event'));

    /**
     * Initialize the calendar.
     */
    // Date for the calendar events (today).
    let date = new Date();
    let day = date.getDate();
    let month = date.getMonth();
    let year = date.getFullYear();

    var defaultEventBackgroundColor = "#ffbb00";
    var defaultEventTextColor = "#000000";

    $('#calendar').fullCalendar({
	lang : "pt-br", events : contextPath + 'admin/agenda/agendaEntries/filter', header : {
	    left : 'month,agendaWeek,agendaDay', center : 'title', right : 'prev,next today'
	}, editable : true, droppable : true, // This allows things to be
	nextDayThreshold : '00:00:00', eventBackgroundColor : '#ff8d00', eventTextColor : '#ffffff',
	// dropped onto the
	// calendar !!!
	drop : function(date, allDay) { // This function is called when
	    // something is dropped.

	    // Retrieve the dropped element's stored Event Object.
	    var originalEventObject = $(this).data('eventObject');
	    originalEventObject.hashString = $(this).data("serverhash");
	    originalEventObject.start = date;
	    originalEventObject.allDay = true;
	    // originalEventObject.backgroundColor =
	    // $(this).css("background-color");
	    // originalEventObject.borderColor = $(this).css("border-color");

	    // Create a reference to the original object.
	    var droppedObject = $(this);

	    $.ajax({
		type : "post", url : contextPath + "admin/agenda/agendaEntries/" + originalEventObject.hashString + "/allocate", data : {
		    startDate : date.format(),
		}, success : function(data) {
		    // Render the event on the calendar.
		    $('#calendar').fullCalendar('renderEvent', originalEventObject, true);

		    droppedObject.remove();
		}, error : function() {
		    alert("Erro ao alocar o evento");
		}
	    });
	}, // ./drop
	eventDrop : function(event, delta) {
	    $.ajax({
		type : "post", url : contextPath + "admin/agenda/agendaEntries/" + event.hashString + "/reallocate", data : {
		    difference : delta.asDays(),
		}, success : function() {
		    $('#list-event').DataTable().ajax.reload();
		}, error : function() {
		    alert("Erro ao realocar o evento");
		}
	    });
	}, // ./eventDrop
	// eventClick
	eventClick : function(calEvent, jsEvent, view) {
	    $(location).attr('href', contextPath + "admin/agenda/agendaEntries/" + calEvent.hashString);
	}, // ./eventClick
	// eventResize
	eventResize : function(event, delta) {
	    $.ajax({
		type : "post", url : contextPath + "admin/agenda/agendaEntries/" + event.hashString + "/adjustDuration", data : {
		    difference : delta.asDays(),
		}, success : function() {
		    $('#list-event').DataTable().ajax.reload();
		}, error : function() {
		    alert("Erro ao ajustar o evento");
		}
	    });
	} // ./eventResize

    });

    $("#addNewEvent").click(function(e) {
	e.preventDefault();
	// Get value and make sure it is not null.
	var newEventTitle = $("#newEvent").val();
	if (newEventTitle.length == 0) {
	    return;
	}

	// Create event.
	$.ajax({
	    type : "post", url : contextPath + "admin/agenda/agendaEntries", data : {
		title : newEventTitle,
	    }, success : function(data) {
		var event = $("<div />");
		event.css({
		    "background-color" : defaultEventBackgroundColor, "border-color" : defaultEventBackgroundColor, "color" : defaultEventTextColor
		}).addClass("fc-event");
		event.html(newEventTitle);
		// $('#external-events').prepend(event);
		$('#event-container').prepend(event);

		// Store the event hash locally.
		event.data("serverhash", data);

		// Add draggable functionality.
		ini_events(event);

		// Remove event title from text input.
		$("#newEvent").val("");

		$(location).attr('href', contextPath + "admin/agenda/agendaEntries/" + data);
	    }, error : function(data) {
		alert("Não foi possível adicionar o evento:" + data.responseText + " - " + data.responseJSON);
	    }
	});
    });

    $('#list-event').DataTable({
	'responsive' : true, 'order' : [ [ 2, 'asc' ] ], 'paging' : true, 'sPaginationType' : 'full_numbers', 'searching' : true, 'ordering' : true, 'processing' : true, 'serverSide' : true, 'ajax' : contextPath + 'admin/agenda/agendaEntries/table', 'language' : {
	    'url' : contextPath + 'scripts/pt-br.json'
	}, 'columns' : [ {
	    'data' : 'hash', 'bSortable' : false, 'bSearchable' : false, 'render' : function(data, type, row) {
		if (type === 'display') {
		    return '<a href="' + contextPath + 'admin/agenda/agendaEntries/' + row.hash + '"><i class="fa fa-pencil-square-o text-success" aria-hidden="true"></i></a>';
		}
		return data;
	    },
	}, {
	    'data' : 'hash', 'bSortable' : false, 'bSearchable' : false, 'render' : function(data, type, row) {
		if (type === 'display') {
		    return '<a href="' + contextPath + 'admin/agendaEntries/repetition/' + row.hash + '"><i class="fa fa-clone text-success" aria-hidden="true"></i></a>';
		}
		return data;
	    },
	}, {
	    'data' : 'name', 'bSortable' : true, 'render' : function(data, type, row) {
		return data;
	    },
	}, {
	    'data' : 'startDate', 'bSortable' : true, 'render' : function(data, type, row) {
		return data;
	    },
	}, {
	    'data' : 'startTime', 'bSortable' : true, 'render' : function(data, type, row) {
		return data;
	    },
	}, {
	    'data' : 'endDate', 'bSortable' : true, 'render' : function(data, type, row) {
		return data;
	    },
	}, {
	    'data' : 'endTime', 'bSortable' : true, 'render' : function(data, type, row) {
		return data;
	    },
	}, {
	    'data' : 'hash', 'bSortable' : false, 'render' : function(data, type, row) {
		if (type === 'display') {
		    return '<a href="#" onclick="setHashToDelete(\'' + row.hash + '\')" data-toggle="modal" data-target="#confirmDeletion"><i class="fa fa-trash text-danger" aria-hidden="true"></i></a>';
		}
		return data;
	    },
	} ]
    });

    function clearModalForm() {
	$('#deleteCopiesAfter').prop('checked', false);
    }

    $("#confirmDeletion").on("hidden.bs.modal", function() {
	clearModalForm();
    });

});