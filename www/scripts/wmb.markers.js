// Definicje znaczników

var cat = {1:category1, 2:category2, 3:category3, 4:category4}
var colorIcon = {1:redIcon, 2:greenIcon, 3:blueIcon, 4:yellowIcon, 5:purpleIcon}
$.getJSON("http://virt2.iiar.pwr.edu.pl/api/zgloszenia/getAll", 
	function(data){
		$.each(data, function(i){
			L.marker([data[i].zgloszenia.wspolrzedne.x, data[i].zgloszenia.wspolrzedne.y], {icon: colorIcon[data[i].zgloszenia.id_typu]}).bindPopup("<b>Wrocław - Rynek</b><br>Kategoria 4").addTo( cat[data[i].zgloszenia.id_typu] );
		});
});
