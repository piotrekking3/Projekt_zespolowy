// Definicja ikon znaczników
var redIcon = L.icon({
	iconUrl: 'scripts/images/marker_red.png',
	shadowUrl: 'scripts/images/marker-shadow.png',
	iconSize:     [21, 34],
	shadowSize:   [0, 0],
	iconAnchor:   [10, 17],
	shadowAnchor: [0, 0],
	popupAnchor:  [0, 0]
});

var greenIcon = L.icon({
	iconUrl: 'scripts/images/marker_green.png',
	shadowUrl: 'scripts/images/marker-shadow.png',
	iconSize:     [21, 34],
	shadowSize:   [0, 0],
	iconAnchor:   [10, 17],
	shadowAnchor: [0, 0],
	popupAnchor:  [0, 0]
});

var blueIcon = L.icon({
	iconUrl: 'scripts/images/marker_blue.png',
	shadowUrl: 'scripts/images/marker-shadow.png',
	iconSize:     [21, 34],
	shadowSize:   [0, 0],
	iconAnchor:   [10, 17],
	shadowAnchor: [0, 0],
	popupAnchor:  [0, 0]
});

var yellowIcon = L.icon({
	iconUrl: 'scripts/images/marker_yellow.png',
	shadowUrl: 'scripts/images/marker-shadow.png',
	iconSize:     [21, 34],
	shadowSize:   [0, 0],
	iconAnchor:   [10, 17],
	shadowAnchor: [0, 0],
	popupAnchor:  [0, 0]
});

var purpleIcon = L.icon({
	iconUrl: 'scripts/images/marker_purple.png',
	shadowUrl: 'scripts/images/marker-shadow.png',
	IconSize:     [21, 34],
	shadowSize:   [0, 0],
	iconAnchor:   [10, 17],
	shadowAnchor: [0, 0],
	popupAnchor:  [0, 0]
});

// Konwersja z ID do kategorii
function idToCategory(id) {
	var cat;
	if (id >= 1 && id <= 4) { cat = category1; }
	else if (id >= 5 && id <= 6) { cat = category2; }
	else if (id >= 7 && id <= 9) { cat = category3; }
	else if (id >= 10 && id <= 12) { cat = category4; }
	else { cat = category5; }
	return cat;
}

// Konwersja z ID do ikony
function idToIcon(id) {
	var icon;
	if (id >= 1 && id <= 4) { icon = redIcon; }
	else if (id >= 5 && id <= 6) { icon = greenIcon; }
	else if (id >= 7 && id <= 9) { icon = blueIcon; }
	else if (id >= 10 && id <= 12) { icon = yellowIcon; }
	else { icon = purpleIcon; }
	return icon;
}

// Pobieranie znaczników
$.ajax({
	url: "https://m.bariery.wroclaw.pl/api/zgloszenia/getAll",
	cache: false,
	type: 'GET',
	crossDomain: true,
	dataType: 'json',
	success: function(data) {
		$.each(data, function(i) {
			// Nie pobieramy rozwiązanych i anulowanych zgłoszeń
			if (data[i].zgloszenia.id_statusu != 4 && data[i].zgloszenia.id_statusu != 3) { 
				L.marker([data[i].zgloszenia.wspolrzedne.x, data[i].zgloszenia.wspolrzedne.y], {icon: idToIcon(data[i].zgloszenia.id_typu)}).setOpacity(0.80).bindPopup("<b>" + data[i].zgloszenia.adres + "</b><br>"+ typ[data[i].zgloszenia.id_typu] + "<br><i>" + data[i].zgloszenia.opis + "</i> (<a href=\"#page-details?id=" + data[i].zgloszenia.id_zgloszenia + "\" data-ajax=\"false\">czytaj więcej</a>)").addTo(idToCategory(data[i].zgloszenia.id_typu));
			}
		});
	}
});