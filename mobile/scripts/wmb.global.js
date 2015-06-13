/**************************************************
* Zmienne globalne
**************************************************/
var REST_API_URL = '//m.bariery.wroclaw.pl/api/'; // Adres do serwera REST API
var WMB = WMB || {}; // Główna przestrzeń nazw
var map, // Identyfikator mapy
	auth2, // Sesja Google+
	// Ograniczenia mapy
	south_west = L.latLng(51.210, 17.379),
	north_east = L.latLng(51.031, 16.763),
	map_bounds = L.latLngBounds(south_west, north_east),
	// Mapy
	osm_color = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
	{
		minZoom: 12, maxZoom: 18,
		bounds: map_bounds,
		attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
	}),
	osm_grayscale = L.tileLayer('http://{s}.tiles.wmflabs.org/bw-mapnik/{z}/{x}/{y}.png',
	{
		minZoom: 12, maxZoom: 18,
		bounds: map_bounds,
		attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
	}),
	osm_cycle = L.tileLayer('http://{s}.tile.opencyclemap.org/cycle/{z}/{x}/{y}.png',
	{
		minZoom: 12, maxZoom: 18,
		bounds: map_bounds,
		attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
	}),
	base_layers = {
		"Rowerowa": osm_cycle,
		"Czarno-biała": osm_grayscale,
		"Kolorowa": osm_color
	},
	// Kategorie
	category1 = new L.FeatureGroup(),
	category2 = new L.FeatureGroup(),
	category3 = new L.FeatureGroup(),
	category4 = new L.FeatureGroup(),
	category5 = new L.FeatureGroup(),
	new_markers = new L.FeatureGroup(),
	overlays = {
		"Sygnalizacja świetlna": category1,
		"Komunikacja publiczna": category2,
		"Niepełnosprawni ruchowo": category3,
		"Rowerzyści": category4,
		"Pozostałe": category5
	},
	// Typy zgłoszeń, nazwy kategorii, statusy
	types = {1:"Zbyt długie oczekiwanie na światłach", 2:"Zbyt krótkie zielone światło", 3:"Brak możliwości przejścia dwóch jezdni na raz",
		4:"Awaria sygnalizacji świetlnej", 5:"Zbyt niski peron przystanku", 6:"Potrzeba zmiany lokalizacji przystanku",
		7:"Wysoki krawężnik", 8:"Brak podjazdu/rampy dla niepełnosprawnych", 9:"Niedziałająca winda", 10:"Brak możliwości zaparkowania roweru",
		11:"Utrudnione/niemożliwe przejechanie", 12:"Brak możliwości zjazdu/wjazdu z/na ścieżkę rowerową",
		13:"Konieczne uspokojenie ruchu", 14:"Nieuporządkowana zieleń", 15:"Zniszczona nawierzchnia",
		16:"Miejsce często blokowane przez parkujące samochody", 17:"Brak widoczności uczestników ruchu", 18:"Niebezpieczne miejsce",
		19:"Źle zaparkowane auto", 20:"Niedziałające oświetlenie uliczne"},
	categories = {1:"Sygnalizacja świetlna", 2:"Komunikacja publiczna", 3:"Niepełnosprawni ruchowo", 4:"Rowerzyści", 5:"Pozostałe"},
	statuses = {1:"Nowe", 2:"Zweryfikowane", 3:"Anulowane", 4:"Zamknięte"},
	// Ikony znaczników
	red_icon = L.icon({
		iconUrl:	'scripts/images/marker_red.png',
		shadowUrl:	'scripts/images/marker-shadow.png',
		iconSize:	[21, 34],
		shadowSize:	[0, 0],
		iconAnchor:	[10, 34],
		shadowAnchor:	[0, 0],
		popupAnchor:	[0, -24]
	}),
	green_icon = L.icon({
		iconUrl:	'scripts/images/marker_green.png',
		shadowUrl:	'scripts/images/marker-shadow.png',
		iconSize:	[21, 34],
		shadowSize:	[0, 0],
		iconAnchor:	[10, 34],
		shadowAnchor:	[0, 0],
		popupAnchor:	[0, -24]
	}),
	blue_icon = L.icon({
		iconUrl:	'scripts/images/marker_blue.png',
		shadowUrl:	'scripts/images/marker-shadow.png',
		iconSize:	[21, 34],
		shadowSize:	[0, 0],
		iconAnchor:	[10, 34],
		shadowAnchor:	[0, 0],
		popupAnchor:	[0, -24]
	}),
	yellow_icon = L.icon({
		iconUrl:	'scripts/images/marker_yellow.png',
		shadowUrl:	'scripts/images/marker-shadow.png',
		iconSize:	[21, 34],
		shadowSize:	[0, 0],
		iconAnchor:	[10, 34],
		shadowAnchor:	[0, 0],
		popupAnchor:	[0, -24]
	}),
	purple_icon = L.icon({
	iconUrl:	'scripts/images/marker_purple.png',
	shadowUrl:	'scripts/images/marker-shadow.png',
	IconSize:	[21, 34],
	shadowSize:	[0, 0],
	iconAnchor:	[10, 34],
	shadowAnchor:	[0, 0],
	popupAnchor:	[0, -24]
});