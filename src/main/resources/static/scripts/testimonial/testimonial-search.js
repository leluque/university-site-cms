function setHashToDelete(id) {
    $("#hashToDelete").val(id);
}

$(document).ready(function() {
    var contextPath = $('meta[name="_context_path"]').attr('content');

    $('#list').DataTable({
	'responsive' : true, 'order' : [ [ 1, 'asc' ] ], 'paging' : true, 'sPaginationType' : 'full_numbers', 'searching' : true, 'ordering' : true, 'processing' : true, 'serverSide' : true, 'ajax' : contextPath + 'admin/testimonials/table', 'language' : {
	    'url' : contextPath + 'scripts/pt-br.json'
	}, 'columns' : [ {
	    'data' : 'hash', 'bSortable' : false, 'bSearchable' : false, 'render' : function(data, type, row) {
		if (type === 'display') {
		    return '<a href="' + contextPath + 'admin/testimonials/' + row.hash + '"><i class="fa fa-pencil-square-o text-success" aria-hidden="true"></i></a>';
		}
		return data;
	    },
	}, {
	    'data' : 'name', 'bSortable' : true, 'render' : function(data, type, row) {
		return data;
	    },
	}, {
	    'data' : 'show', 'bSortable' : true, 'render' : function(data, type, row) {
		if (type === 'display') {
		    return true === data ? 'Sim' : 'Não';
		}
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
});