jQuery(document).ready(function($) {

    /**
     * ToolTip
     */
    function toolTipInit() {
	$('.social-icons li a').tooltip({
	    placement : 'top'
	});
    }

    toolTipInit();

    /**
     * SuperFish Menu
     */

    function initSuperFish() {
	$(".sf-menu").superfish({
	    delay : 5, autoArrows : true, animation : {
		opacity : 'show'
	    }
	// cssArrows: true
	});

	// Replace SuperFish CSS Arrows to Font Awesome Icons
	$('nav > ul.sf-menu > li').each(function() {
	    $(this).find('.sf-with-ul').append('<i class="fa fa-angle-down"></i>');
	});
    }

    initSuperFish();


    // $('.sub-menu').addClass('animated fadeInRight');

    /**
     * Responsive Navigation
     */

    $('.menu-toggle-btn').click(function() {
	$('.responsive_menu').stop(true, true).slideToggle();
    });

    $('.thumb-small-gallery').addClass('closed');

    $('.thumb-small-gallery').hover(function() {
	var elem = $(this);
	elem.removeClass('closed');
	elem.css({
	    opacity : 1
	});
	$('.gallery-small-thumbs .closed').css({
	    opacity : 0.7
	});
    }, function() {
	var elem = $(this);
	elem.addClass('closed');
	$('.gallery-small-thumbs .closed').css({
	    opacity : 1
	});
    });

});