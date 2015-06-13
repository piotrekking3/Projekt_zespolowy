// Definicja mapy
var map = L.map('map', {center: [51.1101, 17.03], zoom: 14, layers: [OSMcycle, OSMgrayscale, OSMcolor], maxBounds: mapBounds});

// Obsługa geolokalizacji
function onLocationFound(e) {
	var radius = e.accuracy / 2;
	L.marker(e.latlng).addTo(map).bindPopup("Jesteś w pobliżu " + radius + " metrów").openPopup();
	L.circle(e.latlng, radius).addTo(map);
}
map.on('locationfound', onLocationFound);
// TODO Śledzenie geolokalizacji na żądanie - np. po wciśnięciu przycisku
map.locate({setView: true, maxZoom: 12, enableHighAccuracy: true});

// Dodanie warstw (kategorii)
map.addLayer(category1);
map.addLayer(category2);
map.addLayer(category3);
map.addLayer(category4);
map.addLayer(category5);
map.addLayer(newMarkers);

// Dodanie toolbara z dodawaniem markera
map.addControl(drawMarkerControl);
// Dodanie skali do mapy
L.control.scale().addTo(map);
//Dodanie przycisku do centralizacji mapy
L.easyButton('fa-compass',
	function (){
		map.panTo(new L.LatLng(51.1101, 17.03));
	}, 'Centruj mapę');
// Dodanie toolbara z filtrowaniem warstw
L.control.layers(baseLayers, overlays).addTo(map);

var newMarkerLayer;
var adres;
map.on('draw:created', function (e) {
	if (e.layerType == 'marker') {
		// Zapamiętujemy warstwę nowego znacznika,
		// ponieważ dodamy ją do mapy dopiero po obsłudze formularza
		newMarkerLayer = e.layer;
		var geocoder = new google.maps.Geocoder();
		var latlng = new google.maps.LatLng(newMarkerLayer.getLatLng().lat, newMarkerLayer.getLatLng().lng);
		geocoder.geocode({'latLng': latlng}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK) {
			if (results[0]) {
				adres = results[0].address_components[1].long_name + " " + results[0].address_components[0].long_name;
				//adres = results[0].formatted_address;
			} else {
				adres = "Wrocław";
			}
		} else {
			adres = "Wrocław";
		}
		});
		newMarkerDialog.dialog("open");
	}
});