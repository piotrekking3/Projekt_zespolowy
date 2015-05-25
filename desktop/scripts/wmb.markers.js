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
//var cat = {1:category1, 2:category2, 3:category3, 4:category4};
//var colorIcon = {1:redIcon, 2:greenIcon, 3:blueIcon, 4:yellowIcon, 5:purpleIcon};

$.ajax({
	cache: false,
	url: "http://virt2.iiar.pwr.edu.pl/api/zgloszenia/getAll",
	dataType: "json",
	success: function(data) {
		$.each(data, function(i) {
			L.marker([data[i].zgloszenia.wspolrzedne.x, data[i].zgloszenia.wspolrzedne.y], {icon: idToIcon(data[i].zgloszenia.id_typu)}).bindPopup("<b>" + data[i].zgloszenia.adres + "</b><br>"+typ[data[i].zgloszenia.id_typu]+"<br>").addTo(idToCategory(data[i].zgloszenia.id_typu));
			//console.log("Zgloszenie #"+i+" - wspolrzedne: "+data[i].zgloszenia.wspolrzedne.x+" i "+data[i].zgloszenia.wspolrzedne.y+", ikonka: "+idToIcon(data[i].zgloszenia.id_typu)+", kategoria: "+idToCategory(data[i].zgloszenia.id_typu));
		});
	}
});

/*
$.getJSON("http://virt2.iiar.pwr.edu.pl/api/zgloszenia/getAll",
	function(data){
		$.each(data, function(i){
			L.marker([data[i].zgloszenia.wspolrzedne.x, data[i].zgloszenia.wspolrzedne.y], {icon: colorIcon[data[i].zgloszenia.id_typu]}).bindPopup("<b>Wrocław - Rynek</b><br>Kategoria 4").addTo(cat[data[i].zgloszenia.id_typu]);
			console.log("Zgloszenie #"+i+" - wspolrzedne: "+data[i].zgloszenia.wspolrzedne.x+" i "+data[i].zgloszenia.wspolrzedne.y+", ikonka: "+colorIcon[data[i].zgloszenia.id_typu]+", kategoria: "+cat[data[i].zgloszenia.id_typu]);
		});
});
*/