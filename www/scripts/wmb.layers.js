// Definicje warstw

var southWest = L.latLng(51.210, 17.379),
	northEast = L.latLng(51.031, 16.763),
	mapBounds = L.latLngBounds(southWest, northEast),
	OSMcolor = L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
	{
		minZoom: 12, maxZoom: 18,
		bounds: mapBounds,
		attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
	}),
	OSMgrayscale = L.tileLayer('http://{s}.tiles.wmflabs.org/bw-mapnik/{z}/{x}/{y}.png',
	{
		minZoom: 12, maxZoom: 18,
		bounds: mapBounds,
		attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
	}),
	OSMcycle = L.tileLayer('http://{s}.tile.opencyclemap.org/cycle/{z}/{x}/{y}.png',
	{
		minZoom: 12, maxZoom: 18,
		bounds: mapBounds,
		attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
	});

var baseLayers = {
	"Rowerowa": OSMcycle,
	"Czarno-biała": OSMgrayscale,
	"Kolorowa": OSMcolor
};

var category1 = new L.FeatureGroup(),
	category2 = new L.FeatureGroup(),
	category3 = new L.FeatureGroup(),
	category4 = new L.FeatureGroup(),
	category5 = new L.FeatureGroup(),
	newMarkers = new L.FeatureGroup();

var overlays = {
	"Kategoria 1": category1,
	"Kategoria 2": category2,
	"Kategoria 3": category3,
	"Kategoria 4": category4,
	"Kategoria 5": category5
};