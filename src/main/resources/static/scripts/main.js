(function($) {
	"use strict"; 
	
	var Core = {};
	NProgress.start();
	
	$(document).ready(function(){
		Core.module.init();
		Core.plugin.init();
		if ($('[data-toggle="tooltip"]').length) $('[data-toggle="tooltip"]').tooltip() //Enable tooltip
		return false;
	});

	$(window).on("load",function(){
		Core.plugin.isotope.init();
		Core.func.resizeNotice();
		NProgress.done();
		return false;
	});

	$(window).on("resize",function(){
		Core.func.resizeNotice();
		Core.func.getChart();
		return false;
	})

	$(".js__full_screen").on('click',function(){
		$(document).fullScreen(true);
	});
	
	Core.module = {
		init : function(){
			Core.module.accordion();
			Core.module.card();
			Core.module.css($(".js__width"),"width");
			Core.module.dropDown("js__drop_down",false);
			Core.module.logout();
			Core.module.menu();
			Core.module.tab(".js__tab","li");
			Core.module.toggle();
			Core.module.todo();
			return false;
		},
		accordion: function(){
			$(".js__accordion").each(function(){
				var selector = $(this);
				selector.find(".js__control").on("click",function(event){
					event.preventDefault();
					if ($(this).parent().hasClass("active")){
						$(this).parent().removeClass("active");
						$(this).next().stop().slideUp(400);
					}else{
						var current = $(this);
						selector.find(".active").children(".js__content").stop().slideUp(400);
						selector.find(".active").removeClass("active");
						$(this).parent().addClass("active");
						$(this).next(".js__content").slideDown(400,function(){
							if (selector.parents(".main-menu").length){
								/*$(".main-menu .content").mCustomScrollbar("scrollTo",current,{
									// scroll as soon as clicked
									timeout:0,
									// scroll duration
									scrollInertia:200,
								});*/
							}
						});
					}
				});
			});
			return false;
		},
		card: function(){
			$(".js__card").each(function(){
				var selector = $(this);
				selector.on("click",".js__card_minus",function(){
					selector.toggleClass("card-closed");
					selector.find(".js__card_content").stop().slideToggle(400);
				});
				selector.on("click",".js__card_remove",function(){
					selector.slideUp(400);
				});
			});
			return false;
		},
		css : function(selector,name,data){
			if (!data){
				data = name;
			}
			selector.each(function(){
				var raw = $(this).data(data);
				if (raw){
					var dict = {};
					dict[name] = raw
					$(this).css(dict);
				}
			});
			return false;
		},
		dropDown : function(selectorTxt,isMobile){
			var selector = $("." + selectorTxt);
			selector.each(function(){
				var current_selector = $(this);
				current_selector.on("click",".js__drop_down_button",function(event){
					event.preventDefault();
					if ($(window).width() < 1025 || isMobile === false){
						if (current_selector.hasClass("active")){
							current_selector.removeClass("active");
						}else{
							selector.removeClass("active");
							current_selector.addClass("active");
						}
					}
					return false;
				});
			});
			$("html").on("click",function(event){
				var selector = $(event.target);
				if (!(selector.hasClass(selectorTxt) || selector.parents("." + selectorTxt).length)){
					$("." + selectorTxt + ".active").removeClass("active");
				}
			});
			return false;
		},
		logout: function(){
			$(".js__logout").on("click",function(event){
				event.preventDefault();
				swal({   
					title: "Logout?",   
					text: "Are you sure you want to logout?",   
					type: "warning",   
					showCancelButton: true,   
					confirmButtonColor: "#DD6B55",   
					confirmButtonText: "Yes, I'm out!", 
					cancelButtonText: "No, stay plx!", 
					closeOnConfirm: false,
					closeOnCancel: true,
					confirmButtonColor: '#f60e0e',
				}, function(isConfirm){   
					if (isConfirm) {     
						swal({
							title : "Logout success", 
							text: "See you later!", 
							type: "success",
							confirmButtonColor: '#304ffe',
						});   
					} else {    
					} 
				});
				return false;
			});
		},
		menu: function(){
			$(".js__menu_mobile").on("click",function(){
				$("html").toggleClass("menu-active");
				$(window).trigger("resize");
			});
			$(".js__menu_close").on("click",function(){
				$("html").removeClass("menu-active");
			});
			$("body").on("click",function(event){
				if ($("html.menu-active").length && $(window).width() < 800){
					var selector = $(event.target);
					if (!(selector.hasClass("main-menu") || selector.hasClass("js__menu_mobile") || selector.parents(".main-menu").length || selector.parents(".js__menu_mobile").length)){
						$("html").removeClass("menu-active");
					}
				}
			});
			return false;
		},
		tab: function(name,index_name){
			$(".js__tab").each(function(){
				var selector = $(this);
				selector.on("click",".js__tab_control",function(event){
					var target = $(this).data("target");
					event.preventDefault();
					selector.find(".js__tab_content").removeClass("js__active");
					selector.find(".js__tab_control").removeClass("js__active");
					$(this).addClass("js__active");
					if (target){
						$(target).addClass("js__active");
					}else{
						var index;
						if (index_name){
							index = $(this).parents(index_name).first().index()
						}else{
							index = $(this).index()
						}
						
						selector.find(".js__tab_content").eq(index).addClass("js__active");
					}
					return false;
				});
			});
			return false;
		},
		todo: function(){
			$(".js__todo_widget").each(function(){
				var selector = $(this),
					list = $(this).find(".js__todo_list"),
					val = $(this).find(".js__todo_value"),
					button = $(this).find(".js__todo_button");
				button.on("click",function(){
					if (val.val() != ""){
						var rnd = Math.floor((Math.random() * 100000000) + 1);
						list.append('<div class="todo-item"><div class="checkbox"><input type="checkbox" id="todo-'+ rnd +'"><label for="todo-'+ rnd +'">' + val.val() +'</label></div></div>')
						val.val("");
					}else{
						alert("You must enter task name.")
					}
					return false;
				});
			});
			return false;
		},
		toggle: function(){
			$(".js__toggle_open").on("click",function(event){
				event.preventDefault();
				if ($($(this).data("target")).hasClass("active")){

				}else{
					$(".js__toggle").removeClass("active")
				}
				$($(this).data("target")).toggleClass("active");
				return false;
			});
			$(".js__toggle_close").on("click",function(event){
				event.preventDefault();
				$(this).parents(".js__toggle").removeClass("active");
				return false;
			});
			$("body").on("click",function(event){
				if ($(".js__toggle").hasClass("active")){
					var selector = $(event.target);
					if (!(selector.hasClass("js__toggle_open") || selector.hasClass("js__toggle") || selector.parents(".js__toggle_open").length || selector.parents(".js__toggle").length)){
						$(".js__toggle").removeClass("active")
					}
				}
			});
			return false;
		}
	}
	
	Core.func = {
		childReturnWidth : function(selector,current_width){
			if (selector.children("li").children(".sub-menu").length){
				var max_width = 0;
				selector.children("li").children(".sub-menu").each(function(){
					var this_width = Core.func.childReturnWidth($(this),current_width + $(this).outerWidth());
					if (this_width > max_width){
						max_width = this_width;
					}
				});
				return max_width;
			}else{
				return current_width;
			}
		},
		getResponsiveSettings: function(selector){
			var responsive = selector.data("responsive"),
				json = [];
			if (responsive){
				while(responsive.indexOf("'") > -1){
					responsive = responsive.replace("'",'"');
				}
				var json_temp = JSON.parse(responsive);
				$.each(json_temp, function (key, data) {
					json[json.length] = {
						breakpoint: key,
						settings: {
							slidesToShow: data,
							slidesToScroll: data,
						}
					}
				});
			}
			return json;
		},
		getChart: function(){
			$(".js__chart").each(function(){
				var selector = $(this),
					chart = selector.data("chart"),
					json = [],
					id = selector.attr("id"),
					type = selector.data("type"),
					options, dataTable,chart_draw,themes = ($(this).hasClass('black-chart') ? '#1b1c1c' : '#ffffff');
				if (chart){
					var json_temp = chart.split("|"),
						i,j;
					for (i = 0; i < json_temp.length; i++){
						json_temp[i] = json_temp[i].trim();
						json[i] = json_temp[i].split("/");
						for(j = 0; j < json[i].length; j++){
							if (json[i][j].indexOf("'") > -1){
								while(json[i][j].indexOf("'") > -1){
									json[i][j] = json[i][j].replace("'","");
								}
								json[i][j] = json[i][j].trim();
							}else{
								if (json[i][j].indexOf(".") > -1){								
									json[i][j] = parseFloat(json[i][j]);
								}else{
									json[i][j] = parseInt(json[i][j],10);
								}
							}
						}
					}
					dataTable = google.visualization.arrayToDataTable(json);
					if ($(this).hasClass('black-chart')){
						switch (type){
							case "circle": 
								options = {
									chartArea:{left:0,top:0,width:'100%',height:'75%'},
									colors: ["#304ffe", "#f60e0e","#ffa000"],
									fontName: 'Poppins',
									backgroundColor: themes,
									legend:{
										position: 'bottom',
										textStyle: {
											color: '#484848'
										}
									},
									vAxis: {
										baselineColor: '#484848',
										gridlines: {
											color: "#484848"
										},
										textStyle:{
											color: '#484848'
										}
									},
									hAxis: {
										textStyle:{
											color: '#484848'
										}
									}
								}
								chart_draw = new google.visualization.PieChart(document.getElementById(id));
								break;
							case "donut": 
								options = {
									pieHole: 0.3,
									chartArea:{left:0,top:0,width:'100%',height:'75%'},
									legend:{
										position: 'bottom',
										textStyle: {
											color: '#484848'
										}
									},
									colors: ["#304ffe", "#f60e0e","#ffa000"],
									fontName: 'Poppins',
									backgroundColor: themes
								}
								chart_draw = new google.visualization.PieChart(document.getElementById(id));
								break;
							case "column":
								options = {
									chartArea:{left:30,top:10,width:'100%',height:'80%'},
									colors: ["#304ffe"],
									fontName: 'Poppins',
									backgroundColor: themes,
									vAxis: {
										baselineColor: '#484848',
										gridlines: {
											color: "#484848"
										},
										textStyle:{
											color: '#484848'
										}
									},
									hAxis: {
										textStyle:{
											color: '#484848'
										}
									}
								}
								chart_draw = new google.visualization.ColumnChart(document.getElementById(id));
								break;
							case "curve":
								options = {
									chartArea:{left:30,top:10,width:'90%',height:'80%'},
									curveType: 'function',
									colors: ["#304ffe", "#f60e0e","#ffa000"],
									fontName: 'Poppins',
									backgroundColor: themes,
									vAxis: {
										baselineColor: '#484848',
										gridlines: {
											color: "#484848"
										},
										textStyle:{
											color: '#484848'
										}
									},
									hAxis: {
										textStyle:{
											color: '#484848'
										}
									}
								}
								chart_draw = new google.visualization.LineChart(document.getElementById(id));
								break;
							case "line":
								options = {
									chartArea:{left:30,top:10,width:'90%',height:'80%'},
									fontName: 'Poppins',
									backgroundColor: themes,
									vAxis: {
										baselineColor: '#484848',
										gridlines: {
											color: "#484848"
										},
										textStyle:{
											color: '#484848'
										}
									},
									hAxis: {
										textStyle:{
											color: '#484848'
										}
									}
								}
								chart_draw = new google.visualization.LineChart(document.getElementById(id));
								break;
							case "area":
								options = {
									chartArea:{left:50,top:20,width:'100%',height:'70%'},
									legend: {
										position: 'bottom'
									},
									fontName: 'Poppins',
									backgroundColor: themes,
									vAxis: {
										baselineColor: '#484848',
										gridlines: {
											color: "#484848"
										},
										textStyle:{
											color: '#484848'
										}
									},
									hAxis: {
										textStyle:{
											color: '#484848'
										}
									}
								}
								chart_draw = new google.visualization.AreaChart(document.getElementById(id));
								break;
						}
					}else{
						switch (type){
							case "circle": 
								options = {
									chartArea:{left:0,top:0,width:'100%',height:'75%'},
									legend:{
										position: 'bottom'
									},
									colors: ["#304ffe", "#f60e0e","#ffa000"],
									fontName: 'Poppins',
									backgroundColor: themes
								}
								chart_draw = new google.visualization.PieChart(document.getElementById(id));
								break;
							case "donut": 
								options = {
									pieHole: 0.3,
									chartArea:{left:0,top:0,width:'100%',height:'75%'},
									legend:{
										position: 'bottom',
									},
									colors: ["#304ffe", "#f60e0e","#ffa000"],
									fontName: 'Poppins',
									backgroundColor: themes
								}
								chart_draw = new google.visualization.PieChart(document.getElementById(id));
								break;
							case "column":
								options = {
									chartArea:{left:30,top:10,width:'100%',height:'80%'},
									colors: ["#304ffe"],
									fontName: 'Poppins',
									backgroundColor: themes
								}
								chart_draw = new google.visualization.ColumnChart(document.getElementById(id));
								break;
							case "curve":
								options = {
									chartArea:{left:30,top:10,width:'90%',height:'80%'},
									curveType: 'function',
									colors: ["#304ffe", "#f60e0e","#ffa000"],
									fontName: 'Poppins',
									backgroundColor: themes
								}
								chart_draw = new google.visualization.LineChart(document.getElementById(id));
								break;
							case "line":
								options = {
									chartArea:{left:30,top:10,width:'90%',height:'80%'},
									fontName: 'Poppins',
									backgroundColor: themes
								}
								chart_draw = new google.visualization.LineChart(document.getElementById(id));
								break;
							case "area":
								options = {
									chartArea:{left:50,top:20,width:'100%',height:'70%'},
									legend: {
										position: 'bottom'
									},
									fontName: 'Poppins',
									backgroundColor: themes
								}
								chart_draw = new google.visualization.AreaChart(document.getElementById(id));
								break;
						}
					}
	        		chart_draw.draw(dataTable, options);
				}
			});
		},
		resizeNotice : function(){
			$(".notice-popup").each(function(){
				var selector = $(this),
					space = (parseInt(selector.data("space"),10) > 0) ? parseInt(selector.data("space"),10) : 75,
					window_height = $(window).height() - space;
				selector.attr("style","");
				if (selector.height() > window_height){
					selector.css({
						"height" : window_height
					});
				}
			});
		}
	}
	
	Core.plugin = {
		init : function(){
			Core.plugin.chart();
			Core.plugin.mCustomScrollbar();
			Core.plugin.select2();
			Core.plugin.ui.accordion();
			Core.plugin.ui.slider();
			Core.plugin.ui.sortable();
			Core.plugin.ui.tabs();
			Core.plugin.waves();
			Core.plugin.isotope.filter();
			return false;
		},
		chart: function(){
			if ($(".js__chart").length){
				google.charts.load("current", {packages:["corechart"]});
				google.charts.setOnLoadCallback(Core.func.getChart);
			}
			return false;
		},
		isotope : {
			init : function(){
				setTimeout(function(){
					$(".js__filter_isotope").each(function(){
						var selector = $(this);
						selector.find(".js__isotope_items").isotope({
							itemSelector: ".js__isotope_item",
							layoutMode: 'cellsByRow'
						});
					});
				},100);
				return false;
			},
			filter : function(){
				$(".js__filter_isotope").each(function(){
					var selector = $(this);
					selector.on("click",".js__filter_control",function(event){
						event.preventDefault();
						if (!($(this).hasClass(".js__active"))){
							selector.find(".js__filter_control").removeClass("js__active");
							$(this).addClass("js__active");
							selector.find(".js__isotope_items").isotope({
								filter : $(this).data("filter")
							});
						}
						return false;
					});
				});
				return false;
			}
		},
		mCustomScrollbar:function(){
			if ($(".main-menu").length){
				$(".main-menu .content").mCustomScrollbar();
			}
			if ($(".notice-popup").length){
				$(".notice-popup .content").mCustomScrollbar();
			}
			return false;
		},
		select2 : function(){
			$(".js__select2").each(function(){
				var minResults = $(this).data("min-results"),
					classContainer = $(this).data("container-class");
				if (minResults){
					if (minResults === "Infinity"){
						$(this).select2({
							minimumResultsForSearch: Infinity,
						});
					}else{
						$(this).select2({
							minimumResultsForSearch: parseInt(minResults,10)
						});
					}
					if (classContainer){
						$(this).on("select2:open", function(){
							$(".select2-container--open").addClass(classContainer);
							return false;
						});
					}
				}else{
					$(this).select2();
				}
			});
			return false;
		},
		ui: {
			accordion: function(){
				if ($( ".js__ui_accordion" ).length){
					$( ".js__ui_accordion" ).accordion({
						heightStyle: "content",
						collapsible: true
					});
				}
				return false;
			},
			slider: function(){
				$(".js__ui_slider").each(function(){
					var selector = $(this),
						slider = selector.find(".js__slider_range"),
						amount = selector.find(".js__slider_amount"),
						min = parseInt(selector.data("min"),10),
						max = parseInt(selector.data("max"),10),
						start = parseInt(selector.data("value-1"),10),
						end = parseInt(selector.data("value-2"),10),
						range = selector.data("range");

					if (end > 0){
						slider.slider({
							range: true,
							min: min,
							max: max,
							values: [ start, end ],
							slide: function( event, ui ) {
								amount.val( "$" + ui.values[0] + " - $" + ui.values[1] );
							}
						});
						amount.val( "$" + slider.slider( "values", 0 ) + " - $" + slider.slider( "values", 1 ) );
					}else{
						slider.slider({
							range: range,
							min: min,
							max: max,
							value: start,
							slide: function( event, ui ) {
								amount.val( "$" +  ui.value );
							}
						});
						amount.val("$" + slider.slider( "value" ) );
					}
				});
				return false;
			},
			sortable: function(){
				if ($(".js__sortable").length){
					$(".js__sortable").sortable({
						revert: true,
						start: function(e, ui){
							ui.placeholder.height(ui.item.height() - 20);
							ui.placeholder.css('visibility', 'visible');
						}
					});
				}
				return false;
			},
			tabs : function(){
				if ($(".js__ui_tab").length){
					$(".js__ui_tab").tabs();
				}
				return false;
			}
		},
		waves: function(){
			if ($('.js__control').length){
				Waves.attach('.js__control');
				Waves.init();
			}
			return false;
		}
	}
	
})(jQuery);