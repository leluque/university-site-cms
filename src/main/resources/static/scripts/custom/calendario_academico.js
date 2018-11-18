$(function() {
    $.fn.calendar.dates['pt'] = {
	days : [ "Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado" ], daysShort : [ "Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb" ], daysMin : [ "Do", "Se", "Te", "Qu", "Qu", "Se", "Sa" ], months : [ "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" ], monthsShort : [ "Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez" ], weekShort : 'S', weekStart : 0
    };

    var currentYear = 2018;

    $('#calendar').calendar({
	startYear : 2018, minDate : new Date(2018, 7, 1), maxDate : new Date(2018, 11, 31), language : 'pt', allowOverlap : false, displayWeekNumber : false, displayDisabledDataSource : false, displayHeader : true, alwaysHalfDay : false, style : 'border', enableRangeSelection : false, disabledDays : [], disabledWeekDays : [], hiddenWeekDays : [], roundRangeLimits : false, mouseOnDay : function(e) {
	    if (e.events.length > 0) {
		var content = '';

		for ( var i in e.events) {
		    content += '<div class="event-tooltip-content">' + '<div class="event-name" style="color:' + e.events[i].color + '">' + e.events[i].name + '</div>' + '<div class="event-location">' + e.events[i].location + '</div>' + '</div>';
		}

		$(e.element).popover({
		    trigger : 'manual', container : 'body', html : true, content : content
		});

		$(e.element).popover('show');
	    }
	}, mouseOutDay : function(e) {
	    if (e.events.length > 0) {
		$(e.element).popover('hide');
	    }
	}, dataSource : [ {
	    id : 0, name : '* Início do Semestre Letivo', location : ' ', startDate : new Date(currentYear, 6, 30), endDate : new Date(currentYear, 6, 30)
	}, {
	    id : 1, name : '* Semana de Planejamento e Aperfeiçoamento Pedagógico', location : ' ', startDate : new Date(currentYear, 6, 30), endDate : new Date(currentYear, 6, 31)
	}, {
	    id : 2, name : '* Início das Aulas do 2º Semestre Letivo de 2018', location : 'Presencial e EaD', startDate : new Date(currentYear, 7, 1), endDate : new Date(currentYear, 7, 1)
	}, {
	    id : 3, name : '* Prazo para Acomodação de Matrícula (art. 33 do Regulamento) ', location : 'Presencial e EaD', startDate : new Date(currentYear, 7, 1), endDate : new Date(currentYear, 7, 8)
	}, {
	    id : 4, name : '* Reposição Referente à Sexta-Feira ', location : 'Agronegócio, ADS, RH e Logística', startDate : new Date(currentYear, 7, 18), endDate : new Date(currentYear, 7, 18)
	}, {
	    id : 5, name : '* Reposição Referente à Sexta-Feira ', location : 'Agronegócio, ADS, RH e Logística', startDate : new Date(currentYear, 7, 25), endDate : new Date(currentYear, 7, 25)
	}, {
	    id : 6, name : '* Não haverá aula ', location : 'Aniversário de Mogi das Cruzes', startDate : new Date(currentYear, 8, 1), endDate : new Date(currentYear, 8, 1)
	}, {
	    id : 7, name : '* Não haverá aula ', location : ' Emenda de Feriado – Independência do Brasil', startDate : new Date(currentYear, 8, 7), endDate : new Date(currentYear, 8, 8)
	}, {
	    id : 8, name : '* Reposição Referente à Sexta-Feira ', location : 'Agronegócio, ADS, RH e Logística', startDate : new Date(currentYear, 8, 15), endDate : new Date(currentYear, 8, 15)
	}, {
	    id : 9, name : '* Aniversário de 10 Anos da FATEC MC ', location : '', startDate : new Date(currentYear, 8, 15), endDate : new Date(currentYear, 8, 15)
	}, {
	    id : 10, name : '* Reposição Referente à Sexta-Feira ', location : 'Agronegócio, ADS, RH e Logística', startDate : new Date(currentYear, 8, 22), endDate : new Date(currentYear, 8, 22)
	}, {
	    id : 11, name : '* Semana 09', location : 'Avaliações Presenciais do EAD', startDate : new Date(currentYear, 8, 24), endDate : new Date(currentYear, 8, 29)
	}, {
	    id : 12, name : '* Reposição Referente à Sexta-Feira ', location : 'Agronegócio, ADS, RH e Logística', startDate : new Date(currentYear, 8, 29), endDate : new Date(currentYear, 8, 29)
	}, {
	    id : 13, name : '* Reposição Referente à Sábado', location : 'Agronegócio, ADS, RH e Logística', startDate : new Date(currentYear, 9, 6), endDate : new Date(currentYear, 9, 6)
	}, {
	    id : 14, name : '* Prazo Final para Desistência de Disciplinas (at. 34 do Regulamento)', location : 'Exceto para alunos ingressantes', startDate : new Date(currentYear, 9, 8), endDate : new Date(currentYear, 9, 8)
	}, {
	    id : 15, name : '* Não haverá aula ', location : ' Emenda de Feriado – Nossa Senhora do Brasil', startDate : new Date(currentYear, 9, 12), endDate : new Date(currentYear, 9, 13)
	}, {
	    id : 16, name : '* Reposição Referente à Sábado', location : 'Agronegócio, ADS, RH e Logística', startDate : new Date(currentYear, 9, 20), endDate : new Date(currentYear, 9, 20)
	}, {
	    id : 17, name : '* Reposição Referente à Sábado', location : 'Agronegócio, ADS, RH e Logística', startDate : new Date(currentYear, 9, 27), endDate : new Date(currentYear, 9, 27)
	}, {
	    id : 18, name : '* Prazo Final de Trancamento de Matrícula', location : 'Exceto para alunos ingressantes (art. 35 do Regulamento.', startDate : new Date(currentYear, 9, 30), endDate : new Date(currentYear, 9, 30)
	}, {
	    id : 19, name : '* Não haverá aula ', location : ' Emenda de Feriado – Finados', startDate : new Date(currentYear, 10, 2), endDate : new Date(currentYear, 10, 3)
	}, {
	    id : 20, name : '* Reposição Referente à Sábado', location : 'Agronegócio, ADS, RH e Logística', startDate : new Date(currentYear, 10, 10), endDate : new Date(currentYear, 10, 10)
	},

	{
	    id : 21, name : '* Não haverá aula ', location : ' Emenda de Feriado – Proclamação da República', startDate : new Date(currentYear, 10, 15), endDate : new Date(currentYear, 10, 17)
	},

	{
	    id : 22, name : '* Inscrições para o Vagas Remanescentes e Transferência de Cursos / 1º 2019 ', location : ' ', startDate : new Date(currentYear, 10, 21), endDate : new Date(currentYear, 10, 30)
	},

	{
	    id : 23, name : '* Pré-matrícula Presencial', location : 'Agronegócio, ADS, RH e Logística', startDate : new Date(currentYear, 10, 26), endDate : new Date(currentYear, 11, 15)
	},

	{
	    id : 24, name : '* Abertura do sistema para lançamento das notas no SIGA', location : ' ', startDate : new Date(currentYear, 11, 3), endDate : new Date(currentYear, 11, 15)
	},

	{
	    id : 25, name : '* Semana 19', location : 'Avaliações Presenciais do EAD', startDate : new Date(currentYear, 11, 3), endDate : new Date(currentYear, 11, 8)
	},

	{
	    id : 26, name : '* Provas Finais', location : 'Apenas para EAD', startDate : new Date(currentYear, 11, 10), endDate : new Date(currentYear, 11, 12)
	},

	{
	    id : 27, name : '* Prazo para Solicitação de Erratas (Revisão de Notas e Faltas)', location : 'Apenas para alunos', startDate : new Date(currentYear, 11, 16), endDate : new Date(currentYear, 11, 19)
	},

	{
	    id : 28, name : '* Prazo para Responder Solicitação de Erratas (Revisão de Notas e Faltas)', location : 'Apenas para docentes', startDate : new Date(currentYear, 11, 19), endDate : new Date(currentYear, 11, 22)
	},

	{
	    id : 29, name : '* Término das aulas do 2º Semestre Letivo de 2018)', location : 'Apenas para docentes', startDate : new Date(currentYear, 11, 20), endDate : new Date(currentYear, 11, 20)
	} ], customDayRenderer : function(element, date) {}
    });

});