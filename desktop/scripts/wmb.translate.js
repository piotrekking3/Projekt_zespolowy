// Przykład tłumaczenia z wykorzystaniem i18next
$(document).ready(function() {
	language_complete = navigator.language.split("-");
	language = (language_complete[0]);
	i18n.init({ lng: language, debug: true }, function() {
		$("#header").i18n();
		$("#nav").i18n();
		$("#footer").i18n();
	});
});