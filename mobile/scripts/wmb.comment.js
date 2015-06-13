/**************************************************
* Funkcje związane z geolokalizacją GPS
**************************************************/
WMB.Comment = {
	/**
	* Funkcja dodająca nowy komentarz do zgłoszenia
	*
	* @method onAddCommentSubmit
	*/
	onAddCommentSubmit: function() {
		if ($('#new-comment').val().length < 10 || $('#new-comment').val().length > 255) {
			alert('Komentarz musi mieć od 10-255 znaków.');
		}
		else {
			var comment = {"komentarze": { }};
			comment['komentarze']['komentarz'] = $('#new-comment').val();
			comment['komentarze']['id_zgloszenia'] = parseInt(getURLParam('id'));
			comment['komentarze']['email'] = getCookie('email');
			comment['komentarze']['token'] = getCookie('token');
			var post_data = JSON.stringify(comment);
			$.ajax({
				url: REST_API_URL + 'komentarze/post',
				type: 'POST',
				data: post_data,
				contentType: 'application/json',
				dataType: 'json',
				error: function (e) {
					if (e.status == '200') {
						$('#popup-add-comment-success').popup('open');
						setTimeout(function() { $('#popup-add-comment-success').popup('close'); setTimeout(function() { location.reload(); }, 1000); }, 3000);
					} else {

						$('#popup-add-comment-failure').popup('open');
						setTimeout(function() { $('#popup-add-comment-failure').popup('close'); }, 4000);
					}
				}
			});
		}
		$('#popup-add-comment').popup('close');
	},
	/**
	* Funkcja pobierająca wszystkie komentarze dla danego zgłoszenia
	*
	* @method getAll
	* @param {Integer} id ID zgłoszenia
	*/
	getAll: function(id) {
		$.ajax({
			type: 'GET',
			url: REST_API_URL + 'komentarze/getById/' + id,
			contentType: 'application/json',
			accept: 'application/json',
			dataType: 'json',
			success: function(data) {
				$.each(data, function(j){
					$('#comment-list').append(
						"<tr>" +
							"<td>" + data[j].komentarze.login + "</td>" +
							"<td>" + data[j].komentarze.data_nadania.substr(0,19) + "</td>" +
							"<td>" + data[j].komentarze.komentarz + "</td>" +
						"</tr>");
				});
			},
			error: function(data) {
				alert('Błąd komentarzy');
			}
		});
	},
	/**
	* Funkcja wyświetlająca przycisk dodania komentarza, jeśli użytkownik jest zalogowany
	*
	* @method showCommentButton
	*/
	showCommentButton: function() {
		if (WMB.User.isLoggedIn()) {
			$('.login-to-comment').append('<a href="#popup-add-comment" class="ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-comment ui-btn-icon-left ui-mini" data-transition="fade" data-rel="popup" data-role="button">Dodaj komentarz</a>');
		}
	}
}