// Komentarze
function getComments(i){
	var value;
	$.ajax({
		type: "GET",
		url: "//bariery.wroclaw.pl/api/komentarze/getById/" + i,
		contentType: "application/json",
		accept: "application/json",
		dataType: "json",
		success: function(data) {
			$.each(data, function(j){
				$("#comments").append(
					"<form>" +
					"<fieldset id=" + data[j].komentarze.id_zgloszenia + "#" + data[j].komentarze.id_komentarza + ">" +
						"<b>" + data[j].komentarze.login + "</b>: " + data[j].komentarze.komentarz + "<br>" +
						"<p style=\"float:right\">" + data[j].komentarze.data_nadania.substr(0,19) + "</p><br>" +
					"</fieldset></form>");
			})
		},
		error: function(data){
			alert("Coś poszło nie tak... Komentarzy nie załadowano");
		}
	});
}

function commentsExit() {
	commentsDialog.dialog("close");
}

function commentsOpen(i) {
	commentsDialog.dialog("open");
	getComments(i);
}

var commentsDialog= $("#comments").dialog({
	autoOpen: false,
	height: 400,
	width: 500,
	modal: true,
	buttons: {
		"Zamknij": commentsExit
	},
	close: function() {
		$("#comments").empty();
	}
});

var commentsForm = commentsDialog.find("form").on( "submit", function(event) {
	 event.preventDefault();
});

//Dodawanie komentarza
var komentarz, id;

$('#komentarz').on('change', function() {
	komentarz = $("#komentarz").val();
})

function newCommentSubmit(i) {
	var tmp = {"komentarze": { }};
	tmp['komentarze']['komentarz'] = komentarz;
	tmp['komentarze']['id_zgloszenia'] = id;
	tmp['komentarze']['email'] = getCookie("emailwmb");
	tmp['komentarze']['token'] = getCookie("tokenwmb");
	var postData = JSON.stringify(tmp);
	$.ajax({
		type: "POST",
		url: "//bariery.wroclaw.pl/api/komentarze/post",
		data: postData,
		success: function (l) {
			alert("Twój komentarz został poprawnie dodany");
		},
		error: function (l) {
				if(l.status == 200){
					alert("Twój komentarz został poprawnie dodany");
				}
				else{
					alert("Wystąpiły błędy, bądź nie jesteś zalogowany - komentarz nie został dodany");
				}
		},
		contentType: "application/json",
		accept: "application/json",
		dataType: "json"
	});
	newCommentDialog.dialog("close");
}

function newCommentOpen(i) {
	log = getCookie("logwmb");
	if(log != 0){
		newCommentDialog.dialog("open");
		id = i;
	}
	else{
		notLoginDialog.dialog("open");
	}
}

function newCommentCancel() {
	newCommentDialog.dialog("close");
}

var newCommentDialog = $("#postComment").dialog({
	autoOpen: false,
	width: 350,
	top: 200,
	modal: true,
	buttons: {
		"Wyślij": newCommentSubmit,
		"Anuluj": newCommentCancel
	},
		close: function() {
		newCommentForm[0].reset();
		newCommentForm.removeClass("ui-state-error");
	}
});

var newCommentForm = newCommentDialog.find("form").on( "submit", function(event) {
	 event.preventDefault();
	 newCommentSubmit();
});

//Użytkownik nie zalogowany - info okienko
function notLoginCancel() {
	notLoginDialog.dialog("close");
}

function notLoginOpen() {
	notLoginDialog.dialog("close");
	loginOpen();
}

var notLoginDialog = $("#notLoginUser").dialog({
	autoOpen: false,
	width: 350,
	top: 200,
	modal: true,
	buttons: {
		"Zaloguj": notLoginOpen,
		"Zamknij": notLoginCancel
	}
});

var notLoginForm = notLoginDialog.find("form").on( "submit", function(event) {
	 event.preventDefault();
});