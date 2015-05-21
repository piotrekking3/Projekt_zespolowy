// Zmienne globalne
var map,
	newMarkerLatLng,
	addMarkerFlag = false,
	gpsTracking = false,
	gpsTrackingFirstTime = true,
	gpsTrackingMarker,
	gpsTrackingCircle;

// Powiadomienia typu toast
function showToast(_message, _opacity, _delay, _x, _y) {
	var $toast = $("<div class=\"ui-loader ui-overlay-shadow ui-body-e ui-corner-all\"><p style=\"font-size: 90%;\">" + _message + "</p></div>");
	$toast.css({
		display: 'block', 
		background: '#fff',
		opacity: _opacity, 
		position: 'fixed',
		"text-align": 'center',
		"margin": 'auto auto auto auto',
		"padding-left": '1%',
		"padding-right": '1%',
		"max-width": '50%',
		left: _x,
		top: _y
	});
	var removeToast = function(){ $(this).remove(); };
	$toast.click(removeToast);
	$toast.appendTo($.mobile.pageContainer).delay(_delay);
	$toast.fadeOut(1000, removeToast);
}

// Obsługa geolokalizacji
function onLocationFound(e) {
	var radius = e.accuracy / 2;
	if (gpsTrackingFirstTime) {
		// Dodanie nowego markera oraz nowego narysowanie okręgu
		gpsTrackingMarker = new L.marker(e.latlng).setOpacity(0.80).addTo(map).bindPopup("Jesteś w pobliżu " + Math.round(radius) + " metrów").openPopup();
		gpsTrackingCircle = new L.circle(e.latlng, radius).addTo(map);
		gpsTrackingFirstTime = false;
	} else {
		// Odswieżenie istniejącego markera i okręgu
		gpsTrackingMarker.setLatLng(e.latlng); 
		gpsTrackingCircle.setLatLng(e.latlng).setRadius(radius);
	}
}

// Obsługa błędów geolokalizacji
function onLocationError(e) {
	gpsTracking = false;
	gpsTrackingFirstTime = true;
	setTimeout(function() { showToast('Błąd lokalizacji GPS. ' + e.message, 0.90, 5000, '50px', '170px'); }, 2000);
}

// Obsługa inicjalizacji podstrony z mapą - inicjalizacja Leafleta
$(document).on('pageinit', '#page-map', function() {
	// Definicja mapy
	map = L.map('map', {center: [51.107885, 17.038538], zoom: 14, layers: [OSMcycle, OSMgrayscale, OSMcolor], maxBounds: mapBounds});
	map.on('locationfound', onLocationFound);
	map.on('locationerror', onLocationError);
	// Dodanie warstw (kategorii)
	map.addLayer(category1);
	map.addLayer(category2);
	map.addLayer(category3);
	map.addLayer(category4);
	map.addLayer(category5);
	map.addLayer(newMarkers);

	// Dodanie skali do mapy
	L.control.scale().addTo(map);
	// Dodanie toolbara z filtrowaniem warstw
	L.control.layers(baseLayers, overlays).addTo(map);

	// Przycisk dodawania nowego markera
	if (checkLogin()) {
		L.easyButton('fa-map-marker',
		function () {
			// TODO: Sprawdzenie zalogowania
			addMarkerFlag = true;
			showToast('Przytrzymaj palec na mapie w miejscu, w którym chcesz dodać znacznik.', 0.90, 7000, '50px', '55px');
		},'Dodanie nowego zgłoszenia');
	}

	// Przycisk wyśrodkowania mapy
	L.easyButton('fa-dot-circle-o',
	function () {
		showToast('Wyśrodkowanie mapy.', 0.90, 500, '50px', '115px');
		map.panTo(new L.LatLng(51.107885,17.038538));
		map.setZoom(14);
	},'Wyśrodkowanie mapy mapę');

	// Przycisk śledzenia pozycji GPS
	L.easyButton('fa-compass',
	function () {
		if (gpsTracking) {
			map.stopLocate();
			showToast('Wyłączono śledzenie aktualnej pozycji GPS.', 0.90, 1000, '50px', '170px');
			gpsTracking = false;
			gpsTrackingFirstTime = true;
			map.removeLayer(gpsTrackingMarker);
			map.removeLayer(gpsTrackingCircle);
		} else {
			map.locate({watch: true, setView: true, maximumAge: 1000, enableHighAccuracy: false});
			showToast('Włączono śledzenie aktualnej pozycji GPS.', 0.90, 1000, '50px', '170px');
			gpsTracking = true;
		}
	}, 'Śledzenie pozycji GPS');

	// Rozwiązanie konfliktu jQuery Mobile i Leaflet'a - poprawianie rozmiarów mapy po wyświetleniu podstrony, z pewnym opóźnieniem
	$('#page-map').on('pageshow', function(e) { 
		setTimeout(function() { map.invalidateSize(); }, 1000);
		// Na niektórych urządzeniach czas 1 sekundy okazał się zbyt mały, stąd należy powtórzyć operacje
		setTimeout(function() { map.invalidateSize(); }, 5000);
	});

	// Obsługa zdarzenia przytrzymania palca (po uprzednim aktywowaniu przycisku)
	map.on('contextmenu', function(e) {
		// Jeśli wciśnięto wcześniej przycisk "dodaj zgłoszenie"
		if (addMarkerFlag) {
			newMarkerLatLng = e.latlng;
			$('#popup-add-marker').popup('open');
			$.getJSON("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + newMarkerLatLng.lat +"," + newMarkerLatLng.lng + "&key=AIzaSyChKgmA23ptQcz-6Pfn0_95X0o9CHWKggg",
				function(revGeoCode) {
					$("#address").val(revGeoCode.results[0].address_components[1].long_name + " " + revGeoCode.results[0].address_components[0].long_name);
			});
			addMarkerFlag = false;
		}
	});
});