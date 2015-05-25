// Definicje toolbarów pomocniczych

var drawMarkerControl = new L.Control.Draw({
	draw: {
		position: 'topleft',
		polyline: false,
		polygon: false,
		rectangle: false,
		circle: false,
		marker: true,	// Dodajemy jedynie znaczniki
	},
	edit: {
		featureGroup: newMarkers,	// Warstwa dla nowych znaczników
		edit: false,
		remove: false	// Opcje edycji i usuwania wyłączone
	}
});

//Dodanie przycisku do centralizacji mapy
L.easyButton('fa-compass', 
	function (){
		map.panTo(new L.LatLng(51.1101, 17.03));
	}, 'Centruj mapę');