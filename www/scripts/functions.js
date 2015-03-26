			var redIcon = L.icon({
				iconUrl: './scripts/images/marker_red.png',
				shadowUrl: './scripts/images/marker-shadow.png',
				iconSize:     [21, 34],
				shadowSize:   [0, 0],
				iconAnchor:   [10, 17],
				shadowAnchor: [0, 0],
				popupAnchor:  [0, 0]
			});
			var greenIcon = L.icon({
				iconUrl: './scripts/images/marker_green.png',
				shadowUrl: './scripts/images/marker-shadow.png',
				iconSize:     [21, 34],
				shadowSize:   [0, 0],
				iconAnchor:   [10, 17],
				shadowAnchor: [0, 0],
				popupAnchor:  [0, 0]
			});
			var blueIcon = L.icon({
				iconUrl: './scripts/images/marker_blue.png',
				shadowUrl: './scripts/images/marker-shadow.png',
				iconSize:     [21, 34],
				shadowSize:   [0, 0],
				iconAnchor:   [10, 17],
				shadowAnchor: [0, 0],
				popupAnchor:  [0, 0]
			});
			var yellowIcon = L.icon({
				iconUrl: './scripts/images/marker_yellow.png',
				shadowUrl: './scripts/images/marker-shadow.png',
				iconSize:     [21, 34],
				shadowSize:   [0, 0],
				iconAnchor:   [10, 17],
				shadowAnchor: [0, 0],
				popupAnchor:  [0, 0]
			});
			var purpleIcon = L.icon({
				iconUrl: './scripts/images/marker_purple.png',
				shadowUrl: './scripts/images/marker-shadow.png',
				iconSize:     [21, 34],
				shadowSize:   [0, 0],
				iconAnchor:   [10, 17],
				shadowAnchor: [0, 0],
				popupAnchor:  [0, 0]
			});
	var map = L.map('map', {drawControl: true}).setView([51.1101, 17.03], 15);
	/*var grayscale =*/ L.tileLayer('https://{s}.tiles.mapbox.com/v3/{id}/{z}/{x}/{y}.png', {
		maxZoom: 18, minZoom: 13,
		attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
			'<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
			'Imagery © <a href="http://mapbox.com">Mapbox</a>',
		id: 'examples.map-i875mjb7'
	}).addTo(map);
	 
	var category1 = new L.LayerGroup();
	var category2 = new L.LayerGroup();
	var category3 = new L.LayerGroup();
	var category4 = new L.LayerGroup();
	var category5 = new L.LayerGroup();

	L.marker([51.110121, 17.032020], {icon: redIcon}).bindPopup("<b>Wrocław - Rynek</b><br>Kategoria 1").addTo(category1);
	L.marker([51.110902, 17.032225], {icon: greenIcon}).bindPopup("<b>Wrocław - Rynek</b><br>Kategoria 2").addTo(category2);
	L.marker([51.110000, 17.032425], {icon: blueIcon}).bindPopup("<b>Wrocław - Rynek</b><br>Kategoria 3").addTo(category3);
	L.marker([51.109200, 17.030254], {icon: purpleIcon}).bindPopup("<b>Wrocław - Rynek</b><br>Kategoria 5").addTo(category5);
	L.marker([51.109950, 17.030954], {icon: yellowIcon}).bindPopup("<b>Wrocław - Rynek</b><br>Kategoria 4").addTo(category4),
	L.marker([51.111150, 17.030444], {icon: yellowIcon}).bindPopup("<b>Wrocław - Rynek</b><br>Kategoria 4").addTo(category4),
	L.marker([51.109255, 17.011154], {icon: yellowIcon}).bindPopup("<b>Wrocław - Rynek</b><br>Kategoria 4").addTo(category4);

	/*var baseLayers = {
		"Mapa1": grayscale
	};*/

	var overlays = {
		"Kategoria 1": category1,
		"Kategoria 2": category2,
		"Kategoria 3": category3,
		"Kategoria 4": category4,
		"Kategoria 5": category5
	};
	map.addLayer(category1);
	map.addLayer(category2);
	map.addLayer(category3);
	map.addLayer(category4);
	map.addLayer(category5);
	L.control.layers(null, overlays).addTo(map).setPosition('topleft');

/*new marker*/
	// Initialise the FeatureGroup to store editable layers
	var drawnItems = new L.FeatureGroup();
	map.addLayer(drawnItems);

	// Initialise the draw control and pass it the FeatureGroup of editable layers
	var drawControl = new L.Control.Draw({
		edit: {
			featureGroup: drawnItems
		}
	});
	//map.addControl(drawControl);
	var layer;
	map.on('draw:created', function (e) {
	var type = e.layerType;
	layer = e.layer;
	if (type === 'marker') {
		m = layer;
 		dialog.dialog( "open" );
	}
	// Do whatever else you need to. (save to db, add to map etc)
	drawnItems.addLayer(layer);
	});

	map.on('draw:edited', function () {
	// Update db to save latest changes.
	});

	map.on('draw:deleted', function () {
	// Update db to save latest changes.
	});

/*form*/
	var dialog, form,
	category = $( "#category" ),
	data = $( "#data" ),
	email = $( "#email" ),
	image = $( "#image" ),
	allFields = $( [] ).add( category ).add( data ).add( email ).add( image ),
	tips = $( ".validateTips" );

	function updateTips( t ) {
	tips
		.text( t )
		.addClass( "ui-state-highlight" );
	setTimeout(function() {
		tips.removeClass( "ui-state-highlight", 1500 );
	}, 500 );
}

	function submit() {
		dialog.dialog( "close" );
		//temporary close the form, we can add here sending to server and then then the map state will automaticaly refresh
	}

	function cancel() {
		dialog.dialog( "close" );
		map.removeLayer(layer);
	}

	dialog = $( "#dialog-form" ).dialog({
	autoOpen: false,
	height: 340,
	width: 350,
	modal: true,
	buttons: {
		"Wyślij": submit,
		Anuluj: cancel
	},
	close: function() {
		//map.removeLayer(m); //we can uncomment it when we will have submit function
		form[ 0 ].reset();
		allFields.removeClass( "ui-state-error" );
	}
	});

	form = dialog.find( "form" ).on( "submit", function( event ) {
	  event.preventDefault();
	});
