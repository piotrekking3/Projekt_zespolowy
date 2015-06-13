/**************************************************
* Funkcje związane ze zgłoszeniami
**************************************************/
WMB.Marker = {
	// Czy wciśnięco przycisk dodawania nowego znacznika
	add_button_clicked: false,
	/**
	* Funkcja wyświetlająca menu z wyborem metody dodawania znacznika (lokalizacja GPS/wskazanie na mapie)
	*
	* @method onButtonClicked
	*/
	onButtonClicked: function() {
		if (WMB.User.isLoggedIn()) {
			$('#popup-add-marker-method').popup('open');
		} else {
			showToast('Aby dodawać zgłoszenia, musisz się zalogować.', 0.90, true, 7500, '50px', '55px');
		}
	},
	/**
	* Funkcja obsługująca wybór metody dodawania znacznika z lokalizacji GPS
	*
	* @method onAddFromLocation
	*/
	onAddFromLocation: function() {
		$('#popup-add-marker-method').popup('close');
		WMB.Marker.add_button_clicked = false;
		if (WMB.GPS.tracking) {
			if (WMB.Marker.lat_lng) {
				setTimeout(function() { $('#popup-add-marker').popup('open'); }, 1000);
				// Tłumaczenie współrzednych na adres z wykorzystaniem API Google Maps
				$.getJSON('https://maps.googleapis.com/maps/api/geocode/json?latlng=' + WMB.Marker.lat_lng.lat + ',' + WMB.Marker.lat_lng.lng + '&key=AIzaSyChKgmA23ptQcz-6Pfn0_95X0o9CHWKggg',
				function(response) {
					// Wykorzystujemy jedynie nazwę ulicy/budynku oraz numer
					$('#address').val(response.results[0].address_components[1].long_name + ' ' + response.results[0].address_components[0].long_name);
				});
			}
			else {
				showToast('Błąd lokalizacji GPS. Spróbuj ponownie.', 0.90, true, 3500, '50px', '55px');
			}
		}
		else {
			showToast('Musisz włączyć śledzenie pozycji GPS!', 0.90, true, 3500, '50px', '55px');
		}
	},
	/**
	* Funkcja obsługująca wybór metody dodawania znacznika poprzez wskazanie na mapie
	*
	* @method onAddFromMap
	*/
	onAddFromMap: function() {
		WMB.Marker.add_button_clicked = true;
		$('#popup-add-marker-method').popup('close');
		showToast('Przytrzymaj palec na mapie w miejscu, w którym chcesz dodać znacznik.', 0.90, true, 7500, '50px', '55px');
	},
	/**
	* Funkcja obsługująca zdarzenie przytrzyminia palca/kliknięcia PPM na mapie
	*
	* @method onLongPress
	& @param {Object} event Informacje o wywołaniu zdarzenia
	*/
	onLongPress: function(event) {
		// Jeśli wciśnięto wcześniej przycisk "dodaj zgłoszenie"
		if (WMB.Marker.add_button_clicked) {
			// Odczytujemy współrzędne kliknięcia
			WMB.Marker.lat_lng = event.latlng;
			// Otwieramy okno dialogowe dla dodawania markera
			$('#popup-add-marker').popup('open');
			// Tłumaczenie współrzednych na adres z wykorzystaniem API Google Maps
			$.getJSON('https://maps.googleapis.com/maps/api/geocode/json?latlng=' + WMB.Marker.lat_lng.lat + ',' + WMB.Marker.lat_lng.lng + '&key=AIzaSyChKgmA23ptQcz-6Pfn0_95X0o9CHWKggg',
			function(response) {
				// Wykorzystujemy jedynie nazwę ulicy/budynku oraz numer
				$('#address').val(response.results[0].address_components[1].long_name + ' ' + response.results[0].address_components[0].long_name);
			});
			// Aby dodać kolejne zgłoszenie, należy ponownie kliknąć na przycisk
			WMB.Marker.add_button_clicked = false;
		}
	},
	/**
	* Funkcja dodająca nowe zgłoszenie
	*
	* @method onAddSubmit
	*/
	onAddSubmit: function() {
		if ($("#address").val().length < 4 || $("#address").val().length > 64) {
			alert('Podany adres jest zbyt krótki lub zbyt długi!');
		}
		else if ($("#description").val().length < 32 || $("#description").val().length > 1024) {
			alert('Podany opis jest zbyt krótki lub zbyt długi!');
		}
		else {
			var new_marker = {"zgloszenia": { }};
			new_marker['zgloszenia']['id_typu'] = parseInt($('#category').val());
			new_marker['zgloszenia']['x'] = WMB.Marker.lat_lng.lat;
			new_marker['zgloszenia']['y'] = WMB.Marker.lat_lng.lng;
			new_marker['zgloszenia']['opis'] = $('#description').val();
			new_marker['zgloszenia']['adres'] = $('#address').val();
			new_marker['zgloszenia']['email_uzytkownika'] = getCookie('email');
			new_marker['zgloszenia']['token'] = getCookie('token');
			var post_data = JSON.stringify(new_marker);
			// Wysłanie JSONa do REST API
			$.ajax({
				url: REST_API_URL + 'zgloszenia/post',
				type: 'POST',
				data: post_data,
				contentType: 'application/json',
				dataType: 'json',
				accept: 'application/json',
				success: function(response) {
					var form_data = new FormData(),
					file = $('#file').get(0).files[0];
					form_data.append('file', file);
					$.ajax({
						url: REST_API_URL + 'zdjecia/post',
						type: 'POST',
						beforeSend: function(request) {
							request.setRequestHeader('id', parseInt(response.id_zgloszenia));
						},
						data: form_data,
						processData: false,
						contentType: false,
						cache: false,
						success: function(img_r) {
							console.log('Sukces: ' + JSON.stringify(img_r));
						},
						error: function(img_r) {
							console.log('Błąd: ' + JSON.stringify(img_r));
						}
					});
					L.marker(WMB.Marker.lat_lng, {icon: idToIcon(parseInt($('#category').val()))}).setOpacity(0.80).bindPopup("<b>" + new_marker['zgloszenia']['adres'] + "</b><br>" + types[parseInt($('#category').val())] + "<br><i>" + new_marker['zgloszenia']['opis'] + "</i> (<a href=\"#page-details?id=" + response.id_zgloszenia + "\" data-ajax=\"false\">szczegóły</a>)<div style=\"text-align: right;\">" + getCurrentDate() + "</div>").addTo(idToCategory(parseInt($('#category').val())));
					$('#popup-add-marker-success').popup('open');
					setTimeout(function() { $('#popup-add-marker-success').popup('close'); }, 4000);
				},
				error: function (e) {
					$('#popup-add-marker-failure').popup('open');
					setTimeout(function() { $('#popup-add-marker-failure').popup('close'); }, 4000);
				}
			});

			$('#popup-add-marker').popup('close');
		}
	},
	/**
	* Funkcja pobierająca wszystkie zgłoszenia i dodająca ich znaczniki do mapy
	*
	* @method getAll
	*/
	getAll: function() {
		$.ajax({
			url: REST_API_URL + 'zgloszenia/getAll',
			type: 'GET',
			cache: false,
			dataType: 'json',
			success: function(data) {
				$.each(data, function(i) {
					// Nie pobieramy rozwiązanych i anulowanych zgłoszeń
					if (data[i].zgloszenia.id_statusu != 4 && data[i].zgloszenia.id_statusu != 3) {
						L.marker([data[i].zgloszenia.wspolrzedne.x, data[i].zgloszenia.wspolrzedne.y], {icon: idToIcon(data[i].zgloszenia.id_typu)}).setOpacity(0.80).bindPopup("<b>" + data[i].zgloszenia.adres + "</b><br>"+ types[data[i].zgloszenia.id_typu] + "<br><i>" + data[i].zgloszenia.opis + "</i> (<a href=\"#page-details?id=" + data[i].zgloszenia.id_zgloszenia + "\" data-ajax=\"false\">szczegóły</a>)<div style=\"text-align: right;\">" + data[i].zgloszenia.kalendarz + "</div>").addTo(idToCategory(data[i].zgloszenia.id_typu));
					}
				});
			}
		});
	},
	/**
	* Funkcja pobierająca szczegóły zgłoszenia
	*
	* @method getDetails
	*/
	getDetails: function() {
		var id = parseInt(getURLParam('id'));
		$.ajax({
			url: REST_API_URL + 'zgloszenia/getById/' + id,
			type: 'GET',
			contentType: 'application/json',
			dataType: 'json',
			accept: 'application/json',
			success: function(data) {
				$('#details').html(
					"<p><b>Adres</b>: " + data.zgloszenia.adres + "</p>" +
					"<p><b>Współrzędne</b>: " + Number(data.zgloszenia.wspolrzedne.x.toFixed(4)) + ", " + Number(data.zgloszenia.wspolrzedne.y.toFixed(4)) + "</p>" +
					"<p><b>Data dodania</b>: " + data.zgloszenia.kalendarz + "</p>" +
					"<p><b>Kategoria</b>: " + types[data.zgloszenia.id_typu] + "</p>" +
					"<p><b>Status</b>: " + statuses[data.zgloszenia.id_statusu] + "</p>" +
					"<p><b>Opis</b>: <br><i>" + data.zgloszenia.opis + "</i></p>");
				WMB.Comment.getAll(id);
			},
			error: function(e){
				alert('Brak zgłoszenia o takim ID.');
			}
		});
	},
	/**
	* Funkcja pobierająca wszystkie zgłoszenia i tworząca listę
	*
	* @method showList
	*/
	showList: function() {
		$.getJSON(REST_API_URL + 'zgloszenia/getAll',
			function(data) {
				$.each(data, function(i) {
					$('#marker-list').append(
						"<tr>" +
							"<td>" + data[i].zgloszenia.kalendarz + "</td>" +
							"<td>" + data[i].zgloszenia.adres + "</td>" +
							"<td>" + types[data[i].zgloszenia.id_typu] + "</td>" +
							"<td>" + statuses[data[i].zgloszenia.id_statusu] + "</td>" +
							"<td><a href=\"#page-details?id=" + data[i].zgloszenia.id_zgloszenia + "\" data-ajax=\"false\">Szczegóły</a></td>" +
						"</tr>");
				});
		});
	}
};