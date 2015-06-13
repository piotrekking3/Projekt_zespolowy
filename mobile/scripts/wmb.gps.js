/**************************************************
* Funkcje związane z geolokalizacją GPS
**************************************************/
WMB.GPS = {
	tracking: false, // Czy włączono śledzenie lokalizacji
	draw_position: true, // Czy należy narysować, czy przerysować marker w miejscu lokalizacji
	/**
	* Funkcja obsługująca śledzenie lokalizacji GPS
	*
	* @method onButtonClicked
	*/
	onButtonClicked: function() {
		if (WMB.GPS.tracking) {
			map.stopLocate();
			showToast('Wyłączono śledzenie aktualnej pozycji GPS.', 0.90, true, 1000, '50px', '170px');
			WMB.GPS.tracking = false;
			WMB.GPS.draw_position = true;
			map.removeLayer(WMB.GPS.marker);
			map.removeLayer(WMB.GPS.circle);
		} else {
			map.locate({ watch: true, setView: true, maximumAge: 1000, enableHighAccuracy: false });
			showToast('Włączono śledzenie aktualnej pozycji GPS.', 0.90, true, 1000, '50px', '170px');
			WMB.GPS.tracking = true;
		}
	},
	/**
	* Funkcja obsługująca zdarzenia geolokalizacji
	*
	* @method onSuccess
	* @param {Object} event Informacje o wywołniu zdarzenia
	*/
	onSuccess: function(event) {
		var radius = event.accuracy / 2;
		if (WMB.GPS.draw_position) {
			// Dodajemy nowy marker i rysujemy nowy okrąg na mapie
			WMB.GPS.marker = new L.marker(event.latlng).setOpacity(0.80).addTo(map).bindPopup("Jesteś w pobliżu " + Math.round(radius) + " metrów").openPopup();
			WMB.GPS.circle = new L.circle(event.latlng, radius).addTo(map);
			WMB.GPS.draw_position = false;
		}
		else {
			// Odświeżamy współrzędne markera i okręgu
			WMB.GPS.marker.setLatLng(event.latlng);
			WMB.GPS.marker.setPopupContent("Jesteś w pobliżu " + Math.round(radius) + " metrów");
			WMB.GPS.circle.setLatLng(event.latlng).setRadius(radius);
		}
		WMB.Marker.lat_lng = event.latlng;
	},
	/**
	* Funkcja obsługująca zdarzenia błędów geolokalizacji
	*
	* @method onError
	* @param {Object} event Informacje o wywołniu zdarzenia
	*/
	onError: function(event) {
		WMB.GPS.tracking = false;
		WMB.GPS.draw_position = true;
		setTimeout(function() { showToast('Błąd lokalizacji GPS. ' + event.message, 0.90, true, 5000, '50px', '170px'); }, 2000);
		WMB.Marker.lat_lng = null;
	}
}