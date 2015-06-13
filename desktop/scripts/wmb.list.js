function getFullText(i){
	var value;
	$.ajax({
		type: "GET",
		url: "//bariery.wroclaw.pl/api/zgloszenia/getById/" + i,
		contentType: "application/json",
		accept: "application/json",
		dataType: "json",
		success: function(data) {
			$("#fullText").append(
				"<form id=" + data.zgloszenia.id_zgloszenia + ">" +
					data.zgloszenia.opis + "<br>" +
				"</form>");
		},
		error: function(data){
			alert("Coś poszło nie tak... Opisu nie załadowano");
		}
	});
}

function fullTextExit() {
	fullTextDialog.dialog("close");
}

function showMore(i) {
	fullTextDialog.dialog("open");
	getFullText(i);
}

var fullTextDialog= $("#fullText").dialog({
	autoOpen: false,
	height: 400,
	width: 500,
	modal: true,
	buttons: {
		"Zamknij": fullTextExit
	},
	close: function() {
		$("#fullText").empty();
	}
});

var fullTextForm = fullTextDialog.find("form").on( "submit", function(event) {
	 event.preventDefault();
});