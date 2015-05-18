var map;
var addMarkerFlag = 0;
// Obsługa geolokalizacji
function onLocationFound(e) {
	var radius = e.accuracy / 2;
	L.marker(e.latlng).addTo(map).bindPopup("Jesteś w pobliżu " + radius + " metrów").openPopup();
	L.circle(e.latlng, radius).addTo(map);
}


$(document).on('pageinit', '#page-map', function() {
	// Definicja mapy
	map = L.map('map', {center: [51.1101, 17.03], zoom: 14, layers: [OSMcycle, OSMgrayscale, OSMcolor], maxBounds: mapBounds});
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
	//map.addControl(drawMarkerControl);
	// Dodanie skali do mapy
	L.control.scale().addTo(map);
	// Dodanie toolbara z filtrowaniem warstw
	L.control.layers(baseLayers, overlays).addTo(map);
	var newMarkerLayer;
	map.on('draw:created', function (e) {
		if (e.layerType == 'marker') {
			// Zapamiętujemy warstwę nowego znacznika,
			// ponieważ dodamy ją do mapy dopiero po obsłudze formularza
			newMarkerLayer = e.layer;
			newMarkerDialog.dialog("open");
		}
	});
	
var myButton = L.control({ position: 'topleft' });

myButton.onAdd = function (map) {
	this._div = L.DomUtil.create('div', 'leaflet-draw-toolbar leaflet-bar leaflet-draw-toolbar-top');
	this._div.innerHTML = '<a href="#" id="control-add-marker" class="leaflet-draw-draw-marker"></a>';
	return this._div;
};
myButton.addTo(map);

$('#control-add-marker').click(function() {
	addMarkerFlag = 1;
});

$(document).on('mousemove', function(e){
	if (addMarkerFlag == 1) {
		$('#your_div_id').css({
			left: e.pageX,
			top: e.pageY
		});
	}
});

	setTimeout(function() { map.invalidateSize(); }, 1000);
	setTimeout(function() { map.invalidateSize(); }, 3000);
	map.on('contextmenu', function(e) {
		if (addMarkerFlag == 1) {
			$('#popup-add-marker').popup('open');
			addMarkerFlag = 0;
		}
	});
});

