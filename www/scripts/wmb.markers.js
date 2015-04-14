// Definicje znaczników

$.getJSON("http://virt2.iiar.pwr.edu.pl/api/zgloszenia/getAll", 
	function(data){
		$.each(data, function(i){
			L.marker([data[i].zgloszenia.wspolrzedne.x, data[i].zgloszenia.wspolrzedne.y], {icon: setIcon(data[i].zgloszenia.id_typu)}).bindPopup("<b>Wrocław - Rynek</b><br>Kategoria 4").addTo( setCategory(data[i].zgloszenia.id_typu) );
		});
});

function setIcon(id){
	if(id == 1){
		return redIcon;
	}
	if(id == 2){
		return greenIcon;
	}
	if(id == 3){
		return blueIcon;
	}
	if(id == 4){
		return yellowIcon;
	}
	if(id == 5){
		return purpleIcon;
	}
}

function setCategory(id){
	if(id == 1){
		return category1;
	}
	if(id == 2){
		return category2;
	}
	if(id == 3){
		return category3;
	}
	if(id == 4){
		return category4;
	}
}
