// Definicje formularzy

// Formularz dodawania nowego znacznika
var newMarkerFields = $([]).add(category).add(description).add(image),
	tips = $(".validateTips");
var category, description, image;
$('#category').on('change', function() {
	category = parseInt(this.value);
})

$('#description').on('change', function() {
	description = $("#description").val();
})

function updateTips(t) {
	tips.text(t).addClass("ui-state-highlight");
	setTimeout(function() {
		tips.removeClass("ui-state-highlight", 1500);
	}, 500);
}

function newMarkerSubmit() {
	var punkt = newMarkerLayer.getLatLng();
	var tmp = {"zgloszenia": { }};
	tmp['zgloszenia']['adres'] = adres;
	tmp['zgloszenia']['id_typu'] = category;
	tmp['zgloszenia']['x'] = punkt.lat;
	tmp['zgloszenia']['y'] = punkt.lng;
	tmp['zgloszenia']['opis'] = description;
	tmp['zgloszenia']['email_uzytkownika'] = getCookie("emailwmb");
	tmp['zgloszenia']['token'] = getCookie("tokenwmb");
	var postData = JSON.stringify(tmp);
	image = $("#image").get(0).files[0];
	//console.log(image);
	$.ajax({
		type: "POST",
		url: "http://virt2.iiar.pwr.edu.pl/api/zgloszenia/post",
		data: postData,
		success: function (l) { 
			newMarkers.addLayer(newMarkerLayer);	// Dodanie nowego znacznika
			alert("Zgłoszenie zostało poprawnie dodane");
		},
		error: function (l) { 
			alert("Wystąpiły błędy, bądź nie jesteś zalogowany - zgłoszenie nie zostało dodane");
		},
		contentType: "application/json",
		accept: "application/json",
		dataType: "json"
	});
	newMarkerDialog.dialog("close");
}

function newMarkerCancel() {
	newMarkerDialog.dialog("close");
}

var newMarkerDialog = $("#add-marker").dialog({
	autoOpen: false,
	height: 500,
	width: 350,
	modal: true,
	buttons: {
		"Dodaj": newMarkerSubmit,
		"Anuluj": newMarkerCancel
	},
	close: function() {
		newMarkerForm[0].reset();
		newMarkerFields.removeClass("ui-state-error");
	}
});

var newMarkerForm = newMarkerDialog.find("form").on("submit", function(event) {
	event.preventDefault();
	newMarkerSubmit();
});

// Formularz logowania
var email,
	password = "",
	loginFields = $([]).add(email).add(password);
$('#email').on('change', function() {
	email = this.value;
})
$('#password').on('change', function() {
	password = this.value;
})

function loginSubmit() {
	if(validateEmail(email)){
		if(password == ""){
			alert("Wpisz hasło");
		}
		else{
			var tmp = {"uzytkownicy": { }};
			tmp['uzytkownicy']['email'] = email;
			tmp['uzytkownicy']['haslo'] = calcMD5(password);
			var postData = JSON.stringify(tmp);
			$.ajax({
					type: "POST",
					url: "http://virt2.iiar.pwr.edu.pl/api/uzytkownicy/login",
					data: postData,
					contentType: "application/json",
					accept: "application/json",
					dataType: "json",
					success: function(json) {
						value = json.token;
						document.cookie="tokenwmb="+value;
						document.cookie="emailwmb="+email;
						document.cookie="logwmb=1";
						$("#notlogin").css("display", "none");
						$("#islogin").css("display", "block");
						alert("Zalogowano pomyślnie!");
					},
					error: function() {
						alert("Nie zalogowano. Spróbuj ponownie");
					}
				});
				loginDialog.dialog("close");
		}
	}
	else{
		alert("Podaj prawidłowy email");
	}
}

function loginCancel() {
	loginDialog.dialog("close");
}

function registerForm() {
	registerDialog.dialog("open");
	loginDialog.dialog("close");
}

function loginOpen() {
	loginDialog.dialog("open");
}

var loginDialog= $("#login").dialog({
	autoOpen: false,
	height: 340,
	width: 310,
	modal: true,
	buttons: {
		"Zarejestruj się": registerForm,
		"Zaloguj": loginSubmit,
		"Anuluj": loginCancel
	},
	close: function() {
		loginForm[0].reset();
		loginFields.removeClass("ui-state-error");
	}
});

var loginForm = loginDialog.find("form").on( "submit", function(event) {
	 event.preventDefault();
	 loginSubmit();
});

//Formularz rejestracji
var regEmail,
	regPassword = "",
	regEmail2,
	regPassword2 = "",
	regFields = $([]).add(regEmail).add(regPassword);

$('#emailreg').on('change', function() {
	regEmail = this.value;
})
$('#passwordreg').on('change', function() {
	regPassword = this.value;
})
$('#reemailreg').on('change', function() {
	regEmail2 = this.value;
})
$('#repasswordreg').on('change', function() {
	regPassword2 = this.value;
})

function registerSubmit() {
	if(validateEmail(regEmail) || validateEmail(regEmail2)){
		if(regPassword == "" || regPassword2 ==""){
			alert("Wpisz hasło");
		}
		else{
			if(regEmail == regEmail2){
				if(regPassword == regPassword2){
					var tmp = {"uzytkownicy": { }};
					tmp['uzytkownicy']['email'] = regEmail;
					tmp['uzytkownicy']['haslo'] = calcMD5(regPassword);
					var postData = JSON.stringify(tmp);
					$.ajax({
						type: "POST",
						url: "http://virt2.iiar.pwr.edu.pl/api/uzytkownicy/register",
						data: postData,
						contentType: "application/json",
						accept: "application/json",
						dataType: "json"
					});
					registerDialog.dialog("close");
					alert("Dziękujemy za rejestrację");
				}
				else{
					alert("Podane hasła nie są identyczne");
				}
			}
			else{
				alert("Podane adresy email nie są identyczne");
			}
		}
	}
	else{
		alert("Podaj prawidłowe adresy email");
	}
}

function registerCancel() {
	registerDialog.dialog("close");
}

var registerDialog= $("#register").dialog({
	autoOpen: false,
	height: 270,
	width: 310,
	modal: true,
	buttons: {
		"Wyślij dane": registerSubmit,
		"Anuluj": registerCancel
	},
	close: function() {
		regForm[0].reset();
		regFields.removeClass("ui-state-error");
	}
});

var regForm = registerDialog.find("form").on( "submit", function(event) {
	 event.preventDefault();
	 registerSubmit();
});

$('#login').isHappy({
	fields: {
		'#email': {
			required: true,
			message: 'Wpisz poprawny email',
			test: happy.email
		},
		'#password': {
			required: true,
			message: 'Wymagane hasło'
		},
	}
});

$('#add-marker').isHappy({
	fields: {
		'#category': {
			required: true,
			message: 'Wybierz kategorię',
		},
		'#description': {
			required: true,
			message: 'Wpisz opis problemu'
		},
	}
});

function validateEmail(e) {
	var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	return re.test(e);
}

function logout(i) {
	var tmp = {"uzytkownicy": { }};
	tmp['uzytkownicy']['email'] = getCookie("emailwmb");
	tmp['uzytkownicy']['token'] = getCookie("tokenwmb");
	var postData = JSON.stringify(tmp);
	if(i==1){
		$.ajax({
			type: "POST",
			url: "http://virt2.iiar.pwr.edu.pl/api/uzytkownicy/logout",
			data: postData,
			contentType: "application/json",
			accept: "application/json",
			dataType: "json"
		});
		document.cookie="tokenwmb=";
		document.cookie="emailwmb=";
		document.cookie="validatorwmb=";
		document.cookie="logwmb=0";
		alert("Wylogowałeś się");
		$("#notlogin").css("display", "block");
		$("#islogin").css("display", "none");
		$("#adminPanel").css("display", "none");
	}
	if(i==2){
		signOut();
	}
	if(i==3){
		logoutFacebook();
	}
}

//Logowanie Google
function onSignIn(googleUser) {
	var profile = googleUser.getBasicProfile();
	var tmp = {"uzytkownicy": { }};
	tmp['uzytkownicy']['email'] = profile.getEmail();
	tmp['uzytkownicy']['google'] = profile.getId();
	var postData = JSON.stringify(tmp);
	$.ajax({
		type: "POST",
		url: "http://virt2.iiar.pwr.edu.pl/api/uzytkownicy/loginWithGoogle",
		data: postData,
		contentType: "application/json",
		accept: "application/json",
		dataType: "json",
		success: function(json) {
			value = json.token;
			document.cookie="tokenwmb="+value;
			document.cookie="emailwmb="+profile.getEmail();
			document.cookie="logwmb=2";
			alert("Zalogowano pomyślnie przez Google!");
			$("#notlogin").css("display", "none");
			$("#isloginGoogle").css("display", "block");
		},
	});
	
	loginDialog.dialog("close");
}

function signOut() {
	var auth2 = gapi.auth2.getAuthInstance();
	auth2.signOut().then(function () {
	var tmp = {"uzytkownicy": { }};
	tmp['uzytkownicy']['email'] = getCookie("emailwmb");
	tmp['uzytkownicy']['token'] = getCookie("tokenwmb");
	var postData = JSON.stringify(tmp);
	$.ajax({
		type: "POST",
		url: "http://virt2.iiar.pwr.edu.pl/api/uzytkownicy/logout",
		data: postData,
		contentType: "application/json",
		accept: "application/json",
		dataType: "json"
	});
	document.cookie="tokenwmb=";
	document.cookie="emailwmb=";
	document.cookie="validatorwmb=";
	document.cookie="logwmb=0";
	$("#notlogin").css("display", "block");
	$("#isloginGoogle").css("display", "none");
	$("#adminPanel").css("display", "none");
	alert("Pomyślnie wylogowano");
	});
}

//Logowanie Facebook
window.fbAsyncInit = function() {
	FB.init({
		appId	: '433627263481338',
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

function loginFb() {
	FB.api('/me', function(response) {
		var tmp = {"uzytkownicy": { }};
		tmp['uzytkownicy']['email'] = response.email;
		tmp['uzytkownicy']['facebook'] = response.id;
		var postData = JSON.stringify(tmp);
		$.ajax({
			type: "POST",
			url: "http://virt2.iiar.pwr.edu.pl/api/uzytkownicy/loginWithFacebook",
			data: postData,
			contentType: "application/json",
			accept: "application/json",
			dataType: "json",
			success: function(json) {
				value = json.token;
				document.cookie="tokenwmb="+value;
				document.cookie="emailwmb="+tmp['uzytkownicy']['email'];
				document.cookie="logwmb=3";
				$("#notlogin").css("display", "none");
				$("#isloginFb").css("display", "block");
				if(log != 3){
					alert("Zalogowano pomyślnie przez Facebook!");
				}
			},
			error: function(l) {
				$.ajax({
					type: "POST",
					url: "http://virt2.iiar.pwr.edu.pl/api/uzytkownicy/loginWithFacebook",
					data: postData,
					contentType: "application/json",
					accept: "application/json",
					dataType: "json",
					success: function(json) {
						value = json.token;
						document.cookie="tokenwmb="+value;
						document.cookie="emailwmb="+tmp['uzytkownicy']['email'];
						document.cookie="logwmb=3";
						$("#notlogin").css("display", "none");
						$("#isloginFb").css("display", "block");
						if(log != 3){
							alert("Zalogowano pomyślnie przez Facebook!");
						}
					},
				});
			}
		});
	});
	loginDialog.dialog("close");
}

function logoutFacebook(){
	FB.logout(function(response){
		var tmp = {"uzytkownicy": { }};
		tmp['uzytkownicy']['email'] = getCookie("emailwmb");
		tmp['uzytkownicy']['token'] = getCookie("tokenwmb");
		var postData = JSON.stringify(tmp);
		$.ajax({
			type: "POST",
			url: "http://virt2.iiar.pwr.edu.pl/api/uzytkownicy/logout",
			data: postData,
			contentType: "application/json",
			accept: "application/json",
			dataType: "json"
		});
		document.cookie="tokenwmb=";
		document.cookie="emailwmb=";
		document.cookie="validatorwmb=";
		document.cookie="logwmb=0";
		$("#notlogin").css("display", "block");
		$("#isloginFb").css("display", "none");
		$("#adminPanel").css("display", "none");
		alert("Pomyślnie wylogowano");
	});
}