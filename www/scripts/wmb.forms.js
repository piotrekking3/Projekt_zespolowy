// Definicje formularzy

// Formularz dodawania nowego znacznika
var category = $("#category"),
	description = $("#description"),
	image = $("#image"),
	newMarkerFields = $([]).add(category).add(description).add(image),
	tips = $(".validateTips");

function updateTips(t) {
	tips.text(t).addClass("ui-state-highlight");
	setTimeout(function() {
		tips.removeClass("ui-state-highlight", 1500);
	}, 500);
}

function newMarkerSubmit() {
	// TODO Walidacja formularza
	newMarkers.addLayer(newMarkerLayer);	// Dodanie nowego znacznika
	// TODO Wykorzystanie REST API
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
var email = $("#email"),
	password = $("#password"),
	loginFields = $([]).add(email).add(password);

function loginSubmit() {
	// TODO Walidacja formularza
	// TODO Logowanie z wykorzystaniem OAuth i PHP
	loginDialog.dialog("close");
}

function loginCancel() {
	loginDialog.dialog("close");
}

function loginOpen() {
	loginDialog.dialog("open");
}

var loginDialog= $("#login").dialog({
	autoOpen: false,
	height: 225,
	width: 200,
	modal: true,
	buttons: {
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