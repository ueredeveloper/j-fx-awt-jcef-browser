let map;

function initMap() {
	const myLatLng = { lat: -15.800341849307241, lng: -47.742635303776794 };
	map = new google.maps.Map(document.getElementById("map"), {
		zoom: 10,
		center: myLatLng,
		mapTypeId: 'hybrid'
	});

	new google.maps.Marker({
		position: myLatLng,
		map,
		title: "Hello World!",
	});
	
	map.addListener("click", function (event) {
		sendCoordinates(event.latLng.lat(), event.latLng.lng());
	});

}

	function setZoomIn() {
    	map.setZoom(map.getZoom() + 1);
	}

	function setZoomOut() {
	    map.setZoom(map.getZoom() - 1);
	}
	
	function sendCoordinates(lat, lng) {
    	window.cefQuery({
        request: JSON.stringify({
            action: "setData",  // Ensure this matches the Java handler
            latitude: lat,
            longitude: lng
        }),
        onSuccess: function(response) { console.log("Coordinates sent successfully:", response); },
        onFailure: function(error_code, error_message) { console.error("Error sending coordinates:", error_message); }
    	});
	}

	
window.initMap = initMap;
