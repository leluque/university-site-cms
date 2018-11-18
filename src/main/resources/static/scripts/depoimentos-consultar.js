function setIdDelecao(id) {
    $("#idDelecao").val(id);
}

$(document).ready(function() {
    var contextPath = $('meta[name="_context_path"]').attr('content');

    $('#listagem').DataTable({
	'responsive' : true, 'order' : [ [ 1, 'asc' ] ], 'paging' : true, 'sPaginationType' : 'full_numbers', 'searching' : true, 'ordering' : true, 'processing' : true, 'serverSide' : true, 'ajax' : contextPath + 'depoimentos/tabela', 'language' : {
	    'url' : contextPath + 'scripts/pt-br.json'
	}, 'columns' : [ {
	    'data' : 'nome', 'bSortable' : true, 'render' : function(data, type, row) {
		if (type === 'display') {
		    return '<a href="' + contextPath + 'depoimentos/editar/' + row.id + '">' + data + '</a>';
		}
		return data;
	    },
	}, {
	    'data' : 'posicao', 'bSortable' : true
	}, {
	    'data' : 'exibir', 'bSortable' : true, 'render' : function(data, type, row) {
		if (type === 'display') {
		    return true === row.exibir ? 'Sim' : 'Não';
		}
		return data;
	    },
	}, {
	    'data' : 'id', 'bSortable' : false, 'render' : function(data, type, row) {
		if (type === 'display') {
		    return '<a href="#" onclick="setIdDelecao(' + row.id + ')" data-toggle="modal" data-target="#confirmarDelecao"><i class="mdi mdi-delete text-danger" aria-hidden="true"></i></a>';
		}
		return data;
	    },
	} ]
    });
});