jQuery(function($) {

    var $body = $("body"), $heroA = $("#banner-image-a img"), $heroB = $("#banner-image-b img"), $heroC = $("#banner-image-c img");

    TweenMax.set($heroA, {
	transformStyle : 'preserve-3d'
    });
    TweenMax.set($heroB, {
	transformStyle : 'preserve-3d'
    });
    TweenMax.set($heroC, {
	transformStyle : 'preserve-3d'
    });

    $body.mousemove(function(e) {

	var sxPos = e.pageX / $body.width() * 100 - 50;
	var syPos = e.pageY / $body.height() * 100 - 50;
	console.log("x:" + sxPos + ", y:" + syPos);
	TweenMax.to($heroA, 1, {
	    rotationY : 0.05 * sxPos, rotationX : 0.20 * syPos, rotationZ : '-0.1', transformPerspective : 500, transformOrigin : 'center center'
	});
	TweenMax.to($heroB, 1, {
	    rotationY : 0.10 * sxPos, rotationX : 0.15 * syPos, rotationZ : 0, transformPerspective : 500, transformOrigin : 'center center'
	});
	TweenMax.to($heroC, 1, {
	    rotationY : 0.15 * sxPos, rotationX : 0.10 * syPos, rotationZ : 0.10, transformPerspective : 500, transformOrigin : 'center center'
	});

    });

});
