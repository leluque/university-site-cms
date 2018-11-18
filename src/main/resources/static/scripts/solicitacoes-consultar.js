function setIdDelecao(id) {
	$("#idDelecao").val(id);
}

$(document)
		.ready(
				function() {
					var contextPath = $('meta[name="_context_path"]').attr(
							'content');

					$('#listagem')
							.DataTable(
									{
										'responsive' : true,
										'order' : [ [ 1, 'asc' ] ],
										'paging' : true,
										'sPaginationType' : 'full_numbers',
										'searching' : true,
										'ordering' : true,
										'processing' : true,
										'serverSide' : true,
										'ajax' : contextPath
												+ 'solicitacaos/tabela',
										'language' : {
											'url' : contextPath
													+ 'scripts/pt-br.json'
										},
										'columns' : [
												{
													'data' : 'ra',
													'bSortable' : true,
													'render' : function(data,
															type, row) {
														if (type === 'display') {
															return '<a href="'
																	+ contextPath
																	+ 'solicitacoes/editar/'
																	+ row.id
																	+ '">'
																	+ data
																	+ '</a>';
														}
														return data;
													},
												},
												'data' : 'nome',
												'bSortable' : true,
												'render' : function(data,
														type, row) {
													if (type === 'display') {
														return '<a href="'
																+ contextPath
																+ 'solicitacoes/editar/'
																+ row.id
																+ '">'
																+ data
																+ '</a>';
													}
													return data;
												},
												{
													'data' : 'tipo',
													'bSortable' : true,
													'render' : function(data,
															type, row) {
														if (type === 'display') {
															return row.tipo;
														}
														return data;
													},
												},
												{
													'data' : 'resolvida',
													'bSortable' : true,
													'render' : function(data,
															type, row) {
														if (type === 'display') {
															return true === row.resolvida ? 'Sim'
																	: 'Não';
														}
														return data;
													},
												},
												{
													'data' : 'id',
													'bSortable' : false,
													'render' : function(data,
															type, row) {
														if (type === 'display') {
															return '<a href="#" onclick="setIdDelecao('
																	+ row.id
																	+ ')" data-toggle="modal" data-target="#confirmarDelecao"><i class="mdi mdi-delete text-danger" aria-hidden="true"></i></a>';
														}
														return data;
													},
												} ]
									});
				});