/**************************************************
* Funkcje pomocnicze
**************************************************/

/**
* Funkcja pobierająca kategorię znacznika na podstawie jej ID
*
* @method idToCategory
* @param {Integer} id ID typu zgłoszenia
* @return {Object} referencja do obiektu typu FeatureGroup danej kategorii
*/
function idToCategory(id) {
	var cat;
	if (id >= 1 && id <= 4) { cat = category1; }
	else if (id >= 5 && id <= 6) { cat = category2; }
	else if (id >= 7 && id <= 9) { cat = category3; }
	else if (id >= 10 && id <= 12) { cat = category4; }
	else { cat = category5; }
	return cat;
}

/**
* Funkcja pobierająca ikonę znacznika na podstawie ID kategorii
*
* @method idToIcon
* @param {Integer} id ID typu zgłoszenia
* @return {Object} referencja do obiektu typu FeatureGroup danej ikony
*/
function idToIcon(id) {
	var icon;
	if (id >= 1 && id <= 4) { icon = red_icon; }
	else if (id >= 5 && id <= 6) { icon = green_icon; }
	else if (id >= 7 && id <= 9) { icon = blue_icon; }
	else if (id >= 10 && id <= 12) { icon = yellow_icon; }
	else { icon = purple_icon; }
	return icon;
}

/**
* Funkcja wyświetlająca powiadomienia typu toast
*
* @method showToast
* @param {String} p_message Wiadomość do wyświetlenia
* @param {Float} p_opacity Przeźroczystość od 0.00 - 1.00
* @param {Boolean} p_temp Czy wiadomość ma znikać po czasie p_delay
* @param {Integer} p_delay Liczba milisekund po których wiadomość zostanie zamknięta jeśli p_temp == true
* @param {String} left_margin Lewy margines, określony zgodnie ze standardem CSS
* @param {String} top_margin Górny margines, określony zgodnie ze standardem CSS
*/
function showToast(p_message, p_opacity, p_temp, p_delay, left_margin, top_margin) {
	var $toast = $("<div class=\"ui-loader ui-overlay-shadow ui-body-e ui-corner-all\" style=\"cursor: pointer\"><p style=\"font-size: 90%;\">" + p_message + "</p></div>");
	$toast.css({
		display: 'block',
		background: '#fff',
		opacity: p_opacity,
		position: 'fixed',
		"text-align": 'center',
		"margin": 'auto auto auto auto',
		"padding-left": '1%',
		"padding-right": '1%',
		"max-width": '50%',
		left: left_margin,
		top: top_margin
	});
	var removeToast = function() {
		$(this).remove();
	};
	$toast.click(removeToast);
	if (p_temp) {
		$toast.appendTo($.mobile.pageContainer).delay(p_delay);
		$toast.fadeOut(1000, removeToast);
	} else {
		$toast.appendTo($.mobile.pageContainer);
	}
}

/**
* Funkcja wyświetlająca powiadomienia (HTML 5 Notification API)
*
* @method showNotification
* @param {String} title Tytuł powiadomienia
* @param {String} message Treść powiadomienia
*/
function showNotification(title, message) {
	var notification = window.Notification || window.mozNotification || window.webkitNotification;
	if ('undefined' === typeof notification)
		return false;
	if (notification.permission === 'granted') {
		var push_notification = new notification(title, {
			body: message,
			dir: 'auto',
			lang: 'PL',
			tag: 'notificationPopup',
			icon: 'https://m.bariery.wroclaw.pl/notification.ico'
		});
	}
}

/**
* Funkcja pobierająca dzisiejszą datę
*
* @method getCurrentDate
* @return {String} Dzisiejsza data w formacie RRRR-MM-DD
*/
function getCurrentDate() {
	var today = new Date(),
	dd = today.getDate(),
	mm = today.getMonth() + 1,
	yyyy = today.getFullYear();
	if (dd < 10) dd = '0' + dd;
	if (mm < 10) mm = '0' + mm;
	return yyyy + '-' + mm + '-' + dd;
}

/**
* Funkcja pobierająca wartość parametru o zadanej nazwie z adresu URL
*
* @method getURLParam
* @param {String} param_name Nazwa parametru w adresie URL
* @return {String} Wartość parametru
*/
function getURLParam(param_name) {
	var results = new RegExp('[\?&]' + param_name + '=([^&#]*)').exec(window.location.href);
	if (results == null) return null;
	else return results[1] || 0;
}

/**
* Funkcja pobierająca wartość ciasteczka
*
* @method getCookie
* @param {String} cname Nazwa ciasteczka
* @return {String} Wartość przechowywana w ciasteczku
*/
function getCookie(cname) {
	var name = cname + "=", ca = document.cookie.split(';'), i, c;
	for(i = 0; i < ca.length; i++) {
		c = ca[i];
		while (c.charAt(0) == ' ') c = c.substring(1);
		if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
	}
	return '';
}

/**
* Funkcja tworząca ciasteczko
*
* @method setCookie
* @param {String} cname Nazwa ciasteczka
* @param {String} cvalue Wartość ciasteczka
* @param {Integer} minutes Liczba minut przez jaką ciasteczko będzie ważne (wymaga odświeżania)
*/
function setCookie(cname, cvalue, minutes) {
	var d = new Date();
	d.setTime(d.getTime() + (minutes * 60000));
	var expires = "expires=" + d.toUTCString();
	document.cookie = cname + "=" + cvalue + "; " + expires;
}

/**
* Funkcja do walidacji powtórzonych pól tekstowych w formularzu
*
* @method setCookie
* @param {Object} textBox1 Referencja na główne pole tekstowe
* @param {Object} textBox2 Referencja na pole tekstowe, które jest powtórzeniem głównego
*/
function validateConfirm(textBox1, textBox2) {
	if (document.getElementById(textBox1).value != document.getElementById(textBox2).value)
		document.getElementById(textBox2).setCustomValidity('Pola nie są identyczne!');
	else
		document.getElementById(textBox2).setCustomValidity('');
}