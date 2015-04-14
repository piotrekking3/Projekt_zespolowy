// Definicje znaczników i ikon
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

var cat = {1:category1, 2:category2, 3:category3, 4:category4}
var colorIcon = {1:redIcon, 2:greenIcon, 3:blueIcon, 4:yellowIcon, 5:purpleIcon}

$.getJSON("http://virt2.iiar.pwr.edu.pl/api/zgloszenia/getAll",
	function(data){
		$.each(data, function(i){
			L.marker([data[i].zgloszenia.wspolrzedne.x, data[i].zgloszenia.wspolrzedne.y], {icon: colorIcon[data[i].zgloszenia.id_typu]}).bindPopup("<b>Wrocław - Rynek</b><br>Kategoria 4").addTo( cat[data[i].zgloszenia.id_typu] );
		});
});
