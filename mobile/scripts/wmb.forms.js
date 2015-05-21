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

function setCookie(cname, cvalue, minutes) {
	var d = new Date();
	d.setTime(d.getTime() + (minutes * 60000));
	var expires = "expires="+d.toUTCString();
	document.cookie = cname + "=" + cvalue + "; " + expires;
}

function confirmTextBox(_textBox1, _textBox2) {
	if (document.getElementById(_textBox1).value != document.getElementById(_textBox2).value) {
		document.getElementById(_textBox2).setCustomValidity("Pola nie są identyczne!");
	} else {
		document.getElementById(_textBox2).setCustomValidity('');
	}
}

function newMarkerSubmit() {
	// TODO Walidacja formularza
	var tmp = {"zgloszenia": { }};
	tmp['zgloszenia']['id_uzytkownika'] = 1;
	tmp['zgloszenia']['id_typu'] = parseInt($("#category").val());
	tmp['zgloszenia']['x'] = newMarkerLatLng.lat;
	tmp['zgloszenia']['y'] = newMarkerLatLng.lng;
	tmp['zgloszenia']['opis'] = $("#description").val();
	tmp['zgloszenia']['adres'] = $("#address").val();;
	tmp['zgloszenia']['email_uzytkownika'] = getCookie("email");
	tmp['zgloszenia']['token'] = getCookie("token");
	var postData = JSON.stringify(tmp);
	alert(postData);
	// Wykorzystanie REST API
	$.ajax({
		type: "POST",
		url: "https://m.bariery.wroclaw.pl/api/zgloszenia/post",
		data: postData,
		success: function (e) {
			L.marker([newMarkerLatLng.lat, newMarkerLatLng.lng], {icon: idToIcon(parseInt($("#category").val()))}).bindPopup("<b>Adres</b><br>" + typ[parseInt($("#category").val())] + "<br><i>" + $("#description").val() + "</i> (<a href=\"#\">czytaj więcej</a>)").addTo(idToCategory(parseInt($("#category").val())));
			alert("Zgłoszenie zostało dodane poprawnie");
		},
		error: function (e) {
			alert("Nie udało się dodać zgłoszenia!");
		},
		contentType: "application/json",
		accept: "application/json",
		dataType: "json"
	});
	$('#popup-add-marker').popup('close');
}

function registerSubmit() {
	var newUser = {"uzytkownicy": {}};
	newUser['uzytkownicy']['email'] = $("#register-email").val();
	newUser['uzytkownicy']['haslo'] = CryptoJS.MD5($("#register-password").val()).toString();
	var postData = JSON.stringify(newUser);
	$.ajax({
		url: "https://m.bariery.wroclaw.pl/api/uzytkownicy/register",
		type: "POST",
		data: postData,
		cache: false,
		contentType: "application/json",
		dataType: "json",
		//success: function(data) { 
		//	alert("Zarejestrowano pomyślnie");
		//},
		error: function(e) {
			$('#popup-register-succes').popup('open');
			setTimeout(function() { $('#popup-register-succes').popup('close'); setTimeout(function() { location.reload(); }, 1000); }, 4000);
		}
	});
}

function loginSubmit() {
	var user = {"uzytkownicy": {}};
	user['uzytkownicy']['email'] = $("#login-email").val();
	user['uzytkownicy']['haslo'] = CryptoJS.MD5($("#login-password").val()).toString();
	var postData = JSON.stringify(user);
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
			document.cookie = "email=" + $("#login-email").val() + ";expires=" + now.toGMTString() + ";path=/";
			$('#popup-login-succes').popup('open');
			setTimeout(function() { $('#popup-login-succes').popup('close'); setTimeout(function() { location.reload(); }, 1000); }, 4000);
		},
		error: function(e) {
			alert("Błąd logowania!");
		}
	});
}

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
		error: function(e) {
			// Usuń pliki cookies
			setCookie("token", "", -15);
			setCookie("email", "", -15);
			$('#popup-logout-succes').popup('open');
			setTimeout(function() { $('#popup-logout-succes').popup('close'); setTimeout(function() { location.reload(); }, 1000); }, 4000);
		}
	});
}

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

function onSignIn(googleUser) {
	var profile = googleUser.getBasicProfile();
	var tok = "";
	var tmp = {"uzytkownicy": { }};
	tmp['uzytkownicy']['email'] = profile.getEmail();
	tmp['uzytkownicy']['google'] = parseInt(profile.getId());
	tok = getCookie("token");
	var _url;
	if( tok == ""){
		_url = "https://m.bariery.wroclaw.pl/api/uzytkownicy/registerWithGoogle";
	}
	else {
		tmp['uzytkownicy']['token'] = tok;
		_url = "https://m.bariery.wroclaw.pl/api/uzytkownicy/changeGoogle";
	}
	var postData = JSON.stringify(tmp);
	//alert(postData);
	$.ajax({
		url: _url,
		type: "POST",
		data: postData,
		cache: false,
		contentType: "application/json",
		dataType: "json",
		success: function(data) {
			var now = new Date(), time = now.getTime(), expireTime = time + 300000;
			now.setTime(expireTime);
			document.cookie = "token=" + data.token + ";expires=" + now.toGMTString() + ";path=/";
			document.cookie = "email=" + profile.getEmail() + ";expires=" + now.toGMTString() + ";path=/";
			$('#popup-login-succes').popup('open');
			setTimeout(function() { $('#popup-login-succes').popup('close'); setTimeout(function() { location.reload(); }, 1000); }, 4000);
		},
		error: function(e) {
			var t = JSON.stringify(e);
			//alert(t);
		}
	});
}

function signOut() {
	var auth2 = gapi.auth2.getAuthInstance();
	auth2.signOut().then(function () {
	var tmp = {"uzytkownicy": { }};
	tmp['uzytkownicy']['email'] = getCookie("email");
	tmp['uzytkownicy']['token'] = getCookie("token");
	var postData = JSON.stringify(tmp);
	alert(postData);
	$.ajax({
		type: "POST",
		url: "https://m.bariery.wroclaw.pl/api/uzytkownicy/logout",
		data: postData,
		contentType: "application/json",
		accept: "application/json",
		dataType: "json"
	});
		// Usuń pliki cookies
		setCookie("token", "", -15);
		setCookie("email", "", -15);
		$('#popup-logout-succes').popup('open');
		setTimeout(function() { $('#popup-logout-succes').popup('close'); setTimeout(function() { location.reload(); }, 1000); }, 4000);
	});
}