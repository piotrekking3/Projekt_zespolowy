// Definicje znaczników
// Definicje ikon znaczników
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

// Jednak potrzebujemy funkcji! Nie działało pobieranie, ponieważ indeks wychodził poza tablice! (Robert)
function idToCategory(id) {
	var cat;
	if (id >= 1 && id <= 4) { cat = category1; }
	else if (id >= 5 && id <= 6) { cat = category2; }
	else if (id >= 7 && id <= 9) { cat = category3; }
	else if (id >= 10 && id <= 12) { cat = category4; }
	else { cat = category5; }
	return cat;
}

function idToIcon(id) {
	var icon;
	if (id >= 1 && id <= 4) { icon = redIcon; }
	else if (id >= 5 && id <= 6) { icon = greenIcon; }
	else if (id >= 7 && id <= 9) { icon = blueIcon; }
	else if (id >= 10 && id <= 12) { icon = yellowIcon; }
	else { icon = purpleIcon; }
	return icon;
}

$.ajax({
	cache: false,
	url: "//bariery.wroclaw.pl/api/zgloszenia/getAll",
	dataType: "json",
	success: function(data) {
		$.each(data, function(i) {
			L.marker([data[i].zgloszenia.wspolrzedne.x, data[i].zgloszenia.wspolrzedne.y], {icon: idToIcon(data[i].zgloszenia.id_typu)}).bindPopup("<b>" + data[i].zgloszenia.adres + "</b><br><i>" + typ[data[i].zgloszenia.id_typu] + "</i><br>" + data[i].zgloszenia.opis + " <a style=\"text-decoration: none;\" href=\"#\" onclick=\"showMore(" + data[i].zgloszenia.id_zgloszenia + ");\">[więcej...]</a>" + "<span style=\"float:right\">" + data[i].zgloszenia.kalendarz + "</span><br>").addTo(idToCategory(data[i].zgloszenia.id_typu));
		});
	}
});

