// Pokaż pasek ładowania przy zapytaniach AJAX
$.ajaxSetup({
	beforeSend: function() {
		$.mobile.loading('show');
	},
	complete: function() {
		$.mobile.loading('hide');
	}
});

// Inicjalizacja Facebook JavaScript SDK i Google Plus API
$(document).ready(function() {
	$.getScript('//connect.facebook.net/pl_PL/sdk.js', function() {
		FB.init({
			appId: '457858824389193',
			channelUrl: '//m.bariery.wroclaw.pl/channel.html',
			status: true,
			cookie: true,
			xfbml: true,
			version: 'v2.3'
		});
	});
});

// Wysuwanie menu po przeciągnięciu w lewo/prawo
$(document).on('pagecreate', function() {
	$(document).on('swipeleft swiperight', function(e) {
		if ($('.ui-page-active').jqmData('panel') !== 'open') {
			if (e.type === 'swipeleft') {
				if (WMB.User.isLoggedIn()) {
					$('#panel-user').panel('open');
				}
				else {
					$('#panel-login').panel('open');
				}
			} else if (e.type === 'swiperight') {
				$('#panel-menu').panel('open');
			}
		}
	});
});

// Pobranie zgłoszeń po otworzeniu podstrony z listą
$(document).on('pagebeforecreate', '#page-list', function(event) {
	WMB.Marker.showList();
});

// Pobranie szczegółów to otworzeniu strony ze zgłoszeniem
$(document).on('pageshow', '#page-details', function(event) {
	WMB.Marker.getDetails();
});

// Inicjalizacja Leaflet'a po otworzeniu podstrony z mapą
$(document).on('pageinit', '#page-map', function() {
	map = L.map('map', {center: [51.107885, 17.038538], zoom: 14, layers: [osm_cycle, osm_grayscale, osm_color], maxBounds: map_bounds});

	// Dodanie warstw (kategorii)
	map.addLayer(category1);
	map.addLayer(category2);
	map.addLayer(category3);
	map.addLayer(category4);
	map.addLayer(category5);
	map.addLayer(new_markers);

	// Dodanie skali do mapy
	L.control.scale().addTo(map);
	// Dodanie toolbara z filtrowaniem warstw
	L.control.layers(base_layers, overlays).addTo(map);
	// Przycisk dodawania nowego markera
	L.FAButton(map, 'fa-map-marker', WMB.Marker.onButtonClicked, 'Dodanie nowego zgłoszenia');
	// Przycisk wyśrodkowania mapy
	L.FAButton(map, 'fa-dot-circle-o',
	function () {
		showToast('Wyśrodkowanie mapy.', 0.90, true, 500, '50px', '115px');
		map.panTo(new L.LatLng(51.107885,17.038538));
		map.setZoom(14);
	}, 'Wyśrodkowanie mapy');
	// Przycisk śledzenia pozycji GPS
	L.FAButton(map, 'fa-compass', WMB.GPS.onButtonClicked, 'Śledzenie pozycji GPS');

	// Obsługa geolokalizacji
	map.on('locationfound', WMB.GPS.onSuccess);
	// Obsługa błędów geolokalizacji
	map.on('locationerror', WMB.GPS.onError);
	// Obsługa przytrzymania palca/kliknięcia PPM (po uprzednim aktywowaniu przycisku)
	map.on('contextmenu', WMB.Marker.onLongPress);

	// Pobranie znaczników z REST API
	WMB.Marker.getAll();
});

// Rozwiązanie (czy dobre?) konfliktu jQuery Mobile i Leaflet'a
$(document).on('pageshow', '#page-map', function(event) {
	// Poprawianie rozmiarów mapy po wyświetleniu podstrony, z pewnym opóźnieniem
	// Na niektórych urządzeniach czas 1 sekundy okazał się zbyt mały, dlatego należy powtórzyć operację kilkukrotnie
	setTimeout(function() { map.invalidateSize(); }, 1000);
	setTimeout(function() { map.invalidateSize(); }, 5000);
	setTimeout(function() { map.invalidateSize(); }, 10000);
});

// Zewnętrzne panele i okna dialogowe (z zachowaniem stylów)
$(function() {
	$('body>[data-role="panel"]').panel().enhanceWithin();
	$('body>[data-role="popup"]').popup().enhanceWithin();
});

// Sprawdzenie obsługi powiadomień i prośba o nadanie uprawnień
var notification = window.Notification || window.mozNotification || window.webkitNotification;
if (('undefined' === typeof notification))
	 notification.requestPermission(function () {});

// Sprawdzenie obsługi geolokalizacji i prośba o nadanie uprawnień
if (navigator.geolocation)
	navigator.geolocation.getCurrentPosition(function() {});

// Test HTML5 Notification API
showNotification('Notification', 'Hello world!');