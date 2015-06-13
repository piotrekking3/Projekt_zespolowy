/**************************************************
* Funkcje związane z użytkownikiem
**************************************************/
WMB.User = {
	/**
	* Funkcja sprawdzająca, czy użytkownik jest zalogowany
	*
	* @method isLoggedIn
	* @return {Boolean} TRUE gdy zalogowany, w przeciwnym wypadku FALSE
	*/
	isLoggedIn: function() {
		if (document.cookie.indexOf('token') >= 0 && document.cookie.indexOf('email') >= 0 && document.cookie.indexOf('type') >= 0) {
			// Odświeżamy pliki cookies, aby nie wygasły
			setCookie('token', getCookie('token'), 30);
			setCookie('email', getCookie('email'), 30);
			setCookie('type', getCookie('type'), 30);
			return true;
		} else return false;
	},
	/**
	* Funkcja wyświetlająca link do panelu użytkownika, jeśli jest on zalogowany
	*
	* @method showPanelLink
	*/
	showPanelLink: function() {
		if (WMB.User.isLoggedIn()) {
			$('.is-logged-in').append('<a href="#panel-user" data-role="button" data-icon="user" data-iconpos="notext" data-i18n="global.userpanel">Panel użytkownika</a>');
		} else {
			$('.is-logged-in').append('<a href="#panel-login" data-role="button" data-icon="user" data-iconpos="notext" class="ui-focus" data-i18n="global.loginpanel">Panel logowania</a>');
		}
	},
	/**
	* Funkcja rejestrująca użytkownika w naszym systemie
	*
	* @method onRegisterSubmit
	*/
	onRegisterSubmit: function() {
		var new_user = {"uzytkownicy": {}};
		new_user['uzytkownicy']['email'] = $('#register-email').val().toLowerCase();
		new_user['uzytkownicy']['haslo'] = CryptoJS.MD5($('#register-password').val()).toString();
		var post_data = JSON.stringify(new_user);
		$.ajax({
			url: REST_API_URL + 'uzytkownicy/register',
			type: 'POST',
			data: post_data,
			cache: false,
			contentType: 'application/json',
			dataType: 'json',
			// REST API zawsze zwraca HTTP 500, nawet jeśli pomyślnie dodano :(
			error: function(e) {
				if (e.status == '200') {
					$('#popup-register-succes').popup('open');
					setTimeout(function() { $('#popup-register-succes').popup('close'); setTimeout(function() { location.reload(); }, 1000); }, 3000);
				}
				else {
					$('#popup-register-failure').popup('open');
					setTimeout(function() { $('#popup-register-failure').popup('close'); }, 4000);
				}
			}
		});
	},
	/**
	* Funkcja logująca użytkownika mającego konto w naszym systemie
	*
	* @method onLoginSubmit
	*/
	onLoginSubmit: function() {
		var user = {"uzytkownicy": {}};
		user['uzytkownicy']['email'] = $('#login-email').val().toLowerCase();
		user['uzytkownicy']['haslo'] = CryptoJS.MD5($('#login-password').val()).toString();
		var post_data = JSON.stringify(user);
		// Jeśli poprawnie zalogowano, otrzymujemy token
		$.ajax({
			url: REST_API_URL + 'uzytkownicy/login',
			type: 'POST',
			data: post_data,
			cache: false,
			contentType: 'application/json',
			dataType: 'json',
			success: function(response) {
				setCookie('token', response.token, 30);
				setCookie('email', $('#login-email').val().toLowerCase(), 30);
				setCookie('type', 'restapi', 30);
				$('#popup-login-succes').popup('open');
				setTimeout(function() { $('#popup-login-succes').popup('close'); setTimeout(function() { location.reload(); }, 1000); }, 3000);
			},
			error: function(e) {
				$('#popup-login-failure').popup('open');
				setTimeout(function() { $('#popup-login-failure').popup('close'); }, 4000);
			}
		});
	},
	/**
	* Funkcja służąca do wylogowania użytkownika z naszego systemu, Facebooka, Google+
	*
	* @method onLoginSubmit
	*/
	onLogoutSubmit: function() {
		var user = {"uzytkownicy": {}};
		user['uzytkownicy']['email'] = getCookie('email');
		user['uzytkownicy']['token'] = getCookie('token');
		var post_data = JSON.stringify(user);
		$.ajax({
			url: REST_API_URL + 'uzytkownicy/logout',
			type: 'POST',
			data: post_data,
			cache: false,
			contentType: 'application/json',
			dataType: 'json',
			// REST API zawsze zwraca HTTP 500, nawet jeśli pomyślnie wylogowano :(
			error: function(e) {
				// Wylogowanie z Facebooka
				if (getCookie('type') == 'facebook') FB.logout();
				// Wylogowanie z Google+
				if (getCookie('type') == 'google') gapi.auth2.getAuthInstance().signOut();
				// Usuń pliki cookies
				setCookie('token', '', -30);
				setCookie('email', '', -30);
				setCookie('type', '', -30);
				$('#popup-logout-succes').popup('open');
				setTimeout(function() { $('#popup-logout-succes').popup('close'); setTimeout(function() { location.reload(); }, 1000); }, 3000);
			}
		});
	},
	/**
	* Funkcja pobierająca dane z profilu Facebooka
	*
	* @method getFacebookInfo
	*/
	getFacebookInfo: function() {
		FB.api('/me', function(profile) {
			var user = {"uzytkownicy": {}};
			user['uzytkownicy']['email'] = profile.email;
			user['uzytkownicy']['facebook'] = profile.id;
			var post_data = JSON.stringify(user);
			// Jeśli poprawnie zalogowano, otrzymujemy token
			$.ajax({
				url: REST_API_URL + 'uzytkownicy/loginWithFacebook',
				type: 'POST',
				data: post_data,
				cache: false,
				contentType: 'application/json',
				dataType: 'json',
				success: function(response) {
					setCookie('token', response.token, 30);
					setCookie('email', profile.email, 30);
					setCookie('type', 'facebook', 30);
					$('#popup-login-succes').popup('open');
					setTimeout(function() { $('#popup-login-succes').popup('close'); setTimeout(function() { location.reload(); }, 1000); }, 3000);
				},
				error: function(e) {
					$('#popup-login-failure').popup('open');
					setTimeout(function() { $('#popup-login-failure').popup('close'); }, 4000);
				}
			});
		});
	},
	/**
	* Funkcja logująca użytkownika z wykorzystaniem konta Facebook
	*
	* @method onFacebookSignUp
	*/
	onFacebookSignUp: function() {
		FB.getLoginStatus(function(response) {
			if (response.status === 'connected') WMB.User.getFacebookInfo();
			else {
				FB.login(function(response_login) {
					if (response_login.authResponse) WMB.User.getFacebookInfo();
				}, {scope: 'public_profile,email'});
			}
		});
	},
	/**
	* Funkcja logująca użytkownika z wykorzystaniem konta Google+
	*
	* @method onGooglePlusSignUp
	* @param {Object] google_user Informacje o użytkowniku pobrane z Google+
	*/
	onGooglePlusSignUp: function(google_user) {
		var user = {"uzytkownicy": {}};
		user['uzytkownicy']['email'] = google_user.getBasicProfile().getEmail();
		user['uzytkownicy']['google'] = google_user.getBasicProfile().getId();
		var post_data = JSON.stringify(user);
		// Jeśli poprawnie zalogowano, otrzymujemy token
		$.ajax({
			url: REST_API_URL + 'uzytkownicy/loginWithGoogle',
			type: 'POST',
			data: post_data,
			cache: false,
			contentType: 'application/json',
			dataType: 'json',
			success: function(response) {
				setCookie('token', response.token, 30);
				setCookie('email', google_user.getBasicProfile().getEmail(), 30);
				setCookie('type', 'google', 30);
				$('#popup-login-succes').popup('open');
				setTimeout(function() { $('#popup-login-succes').popup('close'); setTimeout(function() { location.reload(); }, 1000); }, 3000);
			},
			error: function(e) {
				$('#popup-login-failure').popup('open');
				setTimeout(function() { $('#popup-login-failure').popup('close'); }, 4000);
			}
		});
	},
	/**
	* Funkcja inicjalizująca Google+ API
	*
	* @method initGooglePlusAPI
	*/
	initGooglePlusAPI: function() {
		gapi.load('auth2', function() {
			auth2 = gapi.auth2.init({
				client_id: '510674898388-tu9qim42c5n1q9i20lcuqbkopp5oajck.apps.googleusercontent.com',
				cookiepolicy: 'single_host_origin',
				scope: 'profile'
			});
			auth2.attachClickHandler(document.getElementById('google-signin-1'), {}, WMB.User.onGooglePlusSignUp);
			auth2.attachClickHandler(document.getElementById('google-signin-2'), {}, WMB.User.onGooglePlusSignUp);
		});
	}
};