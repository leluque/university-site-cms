function setHashToDelete(id) {
    $("#hashToDelete").val(id);
}

$(document).ready(function() {
    var contextPath = $('meta[name="_context_path"]').attr('content');
    var type = $('meta[name="_type"]').attr('content');

    $('#list').DataTable({
	'responsive' : true, 'order' : [ [ 1, 'asc' ] ], 'paging' : true, 'sPaginationType' : 'full_numbers', 'searching' : true, 'ordering' : true, 'processing' : true, 'serverSide' : true, 'ajax' : contextPath + 'posts/' + type + '/table', 'language' : {
	    'url' : contextPath + 'scripts/pt-br.json'
	}, 'columns' : [ {
	    'data' : 'hash', 'bSortable' : false, 'bSearchable' : false, 'render' : function(data, type, row) {
		if (type === 'display') {
		    return '<a href="' + contextPath + 'posts/' + row.hash + '"><i class="fa fa-pencil-square-o text-success" aria-hidden="true"></i></a>';
		}
		return data;
	    },
	}, {
	    'data' : 'name', 'bSortable' : true, 'render' : function(data, type, row) {
		return data;
	    },
	}, {
	    'data' : 'category', 'bSortable' : true, 'render' : function(data, type, row) {
		if (type === 'display') {
		    return '<a href="' + contextPath + 'posts/categories/' + row.categoryHash + '">' + data + '</a>';
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