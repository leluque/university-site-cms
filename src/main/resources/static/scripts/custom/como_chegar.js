function initialize() {
    var mapOptions = {
	zoom : 16, center : new google.maps.LatLng(-23.507568, -46.188811)
    };

    var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
}

function loadScript() {
    var script = document.createElement('script');
    script.type = 'text/javascript';
    script.src = 'https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&' + 'callback=initialize&key=<key here>';
    document.body.appendChild(script);
}

window.onload = loadScript;
