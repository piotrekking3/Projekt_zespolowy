window.fbAsyncInit = function() {
	FB.init({
		appId	: '457858824389193',
		xfbml	: true,
		version	: 'v2.3'
	});
};

(function(d, s, id) {
	var js, fjs = d.getElementsByTagName(s)[0];
	if (d.getElementById(id)) return;
	js = d.createElement(s); js.id = id;
	js.src = "//connect.facebook.net/pl_PL/sdk.js#xfbml=1&version=v2.3";
	fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

// Odczytanie danych z ciasteczka
function getCookie(cname) {
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for(var i=0; i<ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0)==' ') c = c.substring(1);
		if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
	}
	return "";
}

// Utworzenie ciasteczka
function setCookie(cname, cvalue, minutes) {
	var d = new Date();
	d.setTime(d.getTime() + (minutes * 60000));
	var expires = "expires="+d.toUTCString();
	document.cookie = cname + "=" + cvalue + "; " + expires;
}

// Walidacja formularza - powtarzanie hasła/e-maila
function confirmTextBox(_textBox1, _textBox2) {
	if (document.getElementById(_textBox1).value != document.getElementById(_textBox2).value) {
		document.getElementById(_textBox2).setCustomValidity("Pola nie są identyczne!");
	} else {
		document.getElementById(_textBox2).setCustomValidity('');
	}
}

// Dodawanie nowego zgłoszenia
function newMarkerSubmit() {
	
	if ($("#address").val().length < 4) {
		alert('Podany adres jest błędny!');
	}
	else if ($("#description").val().length < 32) {
		alert('Opis jest zbyt krótki!');
	}
	else {
		var tmp = {"zgloszenia": { }};
		//tmp['zgloszenia']['id_uzytkownika'] = 1;
		tmp['zgloszenia']['id_typu'] = parseInt($("#category").val());
		tmp['zgloszenia']['x'] = newMarkerLatLng.lat;
		tmp['zgloszenia']['y'] = newMarkerLatLng.lng;
		tmp['zgloszenia']['opis'] = $("#description").val();
		tmp['zgloszenia']['adres'] = $("#address").val();
		tmp['zgloszenia']['email_uzytkownika'] = getCookie("email");
		tmp['zgloszenia']['token'] = getCookie("token");
		var postData = JSON.stringify(tmp);
		// Wysłanie JSONa do REST API
		$.ajax({
			type: "POST",
			url: "https://m.bariery.wroclaw.pl/api/zgloszenia/post",
			data: postData,
			success: function (e) {
				L.marker([newMarkerLatLng.lat, newMarkerLatLng.lng], {icon: idToIcon(parseInt($("#category").val()))}).setOpacity(0.80).bindPopup("<b>Adres</b><br>" + typ[parseInt($("#category").val())] + "<br><i>" + $("#description").val() + "</i> (<a href=\"#\">czytaj więcej</a>)").addTo(idToCategory(parseInt($("#category").val())));
				$('#popup-add-marker-success').popup('open');
				setTimeout(function() { $('#popup-add-marker-success').popup('close'); }, 4000);
			},
			error: function (e) {
				$('#popup-add-marker-failure').popup('open');
				setTimeout(function() { $('#popup-add-marker-failure').popup('close'); }, 4000);
			},
			contentType: "application/json",
			accept: "application/json",
			dataType: "json"
		});
		$('#popup-add-marker').popup('close');
	}
}

// Dodawanie nowego komentarza
function newCommentSubmit() {
	if ($("#new-comment").val().length < 10 || $("#new-comment").val().length > 255) {
		alert('Komentarz musi mieć od 10-255 znaków.');
	}
	else {
		var tmp = {"komentarze": { }};
		tmp['komentarze']['komentarz'] = $("#new-comment").val();
		tmp['komentarze']['id_zgloszenia'] = parseInt($.urlParam('id'));
		tmp['komentarze']['email'] = getCookie("email");
		tmp['komentarze']['token'] = getCookie("token");
		var postData = JSON.stringify(tmp);
		// Wysłanie JSONa do REST API
		$.ajax({
			type: "POST",
			url: "https://m.bariery.wroclaw.pl/api/komentarze/post",
			data: postData,
			//success: function (e) {
			//	alert('Pomyślnie dodano komentarz.');
			//	$('#popup-add-comment').popup('close');
			//},
			error: function (e) {
				if (e.status == "200") {
					alert('Pomyślnie dodano komentarz.');
					setTimeout(function() { location.reload(); }, 1000);
				} else {
					alert('Nie udało się dodać komentarza.');
				}
				$('#popup-add-comment').popup('close');
			},
			contentType: "application/json",
			dataType: "json"
		});
	}
}
// Rejestracja w naszym systemie
function registerSubmit() {
	var newUser = {"uzytkownicy": {}};
	newUser['uzytkownicy']['email'] = $("#register-email").val().toLowerCase();
	newUser['uzytkownicy']['haslo'] = CryptoJS.MD5($("#register-password").val()).toString();
	var postData = JSON.stringify(newUser);
	$.ajax({
		url: "https://m.bariery.wroclaw.pl/api/uzytkownicy/register",
		type: "POST",
		data: postData,
		cache: false,
		contentType: "application/json",
		dataType: "json",
		// Nie wiedzieć czemu, koledzy z REST API zawsze zgłaszają błąd 500 - nawet jeśli poprawnie dodało użytkownika
		error: function(e) {
			if (e.status == "200") {
				$('#popup-register-succes').popup('open');
				setTimeout(function() { $('#popup-register-succes').popup('close'); setTimeout(function() { location.reload(); }, 1000); }, 4000);
			}
			else {
				$('#popup-register-failure').popup('open');
				setTimeout(function() { $('#popup-register-failure').popup('close'); }, 4000);
			}
		}
	});
}

// Logowanie w naszym systemie
function loginSubmit() {
	var user = {"uzytkownicy": {}};
	user['uzytkownicy']['email'] = $("#login-email").val().toLowerCase();
	user['uzytkownicy']['haslo'] = CryptoJS.MD5($("#login-password").val()).toString();
	var postData = JSON.stringify(user);
	// Wysłanie JSONa do REST API
	// Jeśli poprawnie zalogowano, otrzymujemy token
	$.ajax({
		url: "https://m.bariery.wroclaw.pl/api/uzytkownicy/login",
		type: "POST",
		data: postData,
		cache: false,
		contentType: "application/json",
		dataType: "json",
		success: function(data) {
			var now = new Date(), time = now.getTime(), expireTime = time + 300000;
			now.setTime(expireTime);
			document.cookie = "token=" + data.token + ";expires=" + now.toGMTString() + ";path=/";
			document.cookie = "email=" + $("#login-email").val().toLowerCase() + ";expires=" + now.toGMTString() + ";path=/";
			$('#popup-login-succes').popup('open');
			setTimeout(function() { $('#popup-login-succes').popup('close'); setTimeout(function() { location.reload(); }, 1000); }, 4000);
		},
		error: function(e) {
			$('#popup-login-failure').popup('open');
			setTimeout(function() { $('#popup-login-failure').popup('close'); }, 4000);
		}
	});
}

// Wylogowanie
function logoutSubmit() {
	var user = {"uzytkownicy": {}};
	user['uzytkownicy']['email'] = getCookie("email");
	user['uzytkownicy']['token'] = getCookie("token");
	var postData = JSON.stringify(user);
	$.ajax({
		url: "https://m.bariery.wroclaw.pl/api/uzytkownicy/logout",
		type: "POST",
		data: postData,
		cache: false,
		contentType: "application/json",
		dataType: "json",
		// Nie wiedzieć czemu, koledzy z REST API zawsze zgłaszają błąd 500 - nawet jeśli poprawnie wylogowano użytkownika
		error: function(e) {
			// Usuń pliki cookies
			setCookie("token", "", -15);
			setCookie("email", "", -15);
			FB.getLoginStatus(function(r) { if (response.status === 'connected') FB.logout(); }); 
			var auth2 = gapi.auth2.getAuthInstance();
			auth2.signOut();
			$('#popup-logout-succes').popup('open');
			setTimeout(function() { $('#popup-logout-succes').popup('close'); setTimeout(function() { location.reload(); }, 1000); }, 4000);
		}
	});
}

// Wykrycie zalogowania
function checkLogin() {
	if (document.cookie.indexOf("token") >= 0 && document.cookie.indexOf("email") >= 0) {
		// Odśwież pliki cookies
		setCookie("token", getCookie("token"), 15);
		setCookie("email", getCookie("email"), 15);
		return true;
	} else {
		return false;
	}
}

// Rejestracja/Logowanie z Google
function loginGoogle(authResult) {
	var profile = authResult.getBasicProfile();
	var user = {"uzytkownicy": {}};
	user['uzytkownicy']['email'] = profile.getEmail();
	user['uzytkownicy']['google'] = parseInt(profile.getId());
	var postData = JSON.stringify(user);
	//alert(postData);
	console.log(postData);
	// Wysłanie JSONa do REST API
	// Jeśli poprawnie zalogowano, otrzymujemy token
	$.ajax({
		url: "https://m.bariery.wroclaw.pl/api/uzytkownicy/loginWithGoogle",
		type: "POST",
		data: postData,
		cache: false,
		contentType: "application/json",
		dataType: "json",
		success: function(response) {
			var now = new Date(), time = now.getTime(), expireTime = time + 300000;
			now.setTime(expireTime);
			document.cookie = "token=" + response.token + ";expires=" + now.toGMTString() + ";path=/";
			document.cookie = "email=" + profile.getEmail() + ";expires=" + now.toGMTString() + ";path=/";
		},
		error: function(e) {
			console.log(JSON.stringify(e));
			//alert(JSON.stringify(e));
		}
	});
	//document.getElementById('google-button').setAttribute('style', 'display: none');
}

// Wylogowanie z Google
function logoutGoogle() {
	var auth2 = gapi.auth2.getAuthInstance();
	auth2.signOut().then(function () {
		var user = {"uzytkownicy": {}};
		user['uzytkownicy']['email'] = getCookie("email");
		user['uzytkownicy']['token'] = getCookie("token");
		var postData = JSON.stringify(user);
		$.ajax({
			url: "https://m.bariery.wroclaw.pl/api/uzytkownicy/logout",
			type: "POST",
			data: postData,
			cache: false,
			contentType: "application/json",
			dataType: "json",
			// Nie wiedzieć czemu, koledzy z REST API zawsze zgłaszają błąd 500 - nawet jeśli poprawnie wylogowano użytkownika
			error: function(e) {
				// Usuń pliki cookies
				setCookie("token", "", -15);
				setCookie("email", "", -15);
				$('#popup-logout-succes').popup('open');
				setTimeout(function() { $('#popup-logout-succes').popup('close'); setTimeout(function() { location.reload(); }, 1000); }, 4000);
			}
		});
	});
}

function loginFacebook() {
	FB.api('/me', function(response) {
		var tmp = {"uzytkownicy": { }};
		tmp['uzytkownicy']['email'] = response.email;
		tmp['uzytkownicy']['facebook'] = response.id;
		var postData = JSON.stringify(tmp);
		$.ajax({
			type: "POST",
			url: "https://m.bariery.wroclaw.pl/api/uzytkownicy/loginWithFacebook",
			data: postData,
			contentType: "application/json",
			accept: "application/json",
			dataType: "json",
			success: function(json) {
				var now = new Date(), time = now.getTime(), expireTime = time + 300000;
				now.setTime(expireTime);
				document.cookie = "token=" + json.token + ";expires=" + now.toGMTString() + ";path=/";
				document.cookie = "email=" + tmp['uzytkownicy']['email'] + ";expires=" + now.toGMTString() + ";path=/";
				$('#popup-login-succes').popup('open');
				setTimeout(function() { $('#popup-login-succes').popup('close'); setTimeout(function() { location.reload(); }, 1000); }, 4000);
			},
			error: function(e) {
				$('#popup-login-failure').popup('open');
				setTimeout(function() { $('#popup-login-failure').popup('close'); }, 4000);
			}
		});
	});
}

function logoutFacebook() {
	FB.logout(function(response) {
		var user = {"uzytkownicy": {}};
		user['uzytkownicy']['email'] = getCookie("email");
		user['uzytkownicy']['token'] = getCookie("token");
		var postData = JSON.stringify(user);
		$.ajax({
			url: "https://m.bariery.wroclaw.pl/api/uzytkownicy/logout",
			type: "POST",
			data: postData,
			cache: false,
			contentType: "application/json",
			dataType: "json",
			// Nie wiedzieć czemu, koledzy z REST API zawsze zgłaszają błąd 500 - nawet jeśli poprawnie wylogowano użytkownika
			error: function(e) {
				// Usuń pliki cookies
				setCookie("token", "", -15);
				setCookie("email", "", -15);
				$('#popup-logout-succes').popup('open');
				setTimeout(function() { $('#popup-logout-succes').popup('close'); setTimeout(function() { location.reload(); }, 1000); }, 4000);
			}
		});
	});
}