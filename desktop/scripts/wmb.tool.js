// Dodatkowe stałe
var typ = {1:"Zbyt długie oczekiwanie na światłach", 2:"Zbyt krótkie zielone światło", 3:"Brak możliwości przejścia dwóch jezdni na raz",
	4:"Awaria sygnalizacji świetlnej", 5:"Zbyt niski peron przystanku", 6:"Potrzeba zmiany lokalizacji przystanku",
	7:"Wysoki krawężnik", 8:"Brak podjazdu/rampy dla niepełnosprawnych", 9:"Niedziałająca winda", 10:"Brak możliwości zaparkowania roweru",
	11:"Utrudnione/niemożliwe przejechanie", 12:"Brak możliwości zjazdu/wjazdu z/na ścieżkę rowerową",
	13:"Konieczne uspokojenie ruchu", 14:"Nieuporządkowana zieleń", 15:"Zniszczona nawierzchnia",
	16:"Miejsce często blokowane przez parkujące samochody", 17:"Brak widoczności uczestników ruchu", 18:"Niebezpieczne miejsce",
	19:"Źle zaparkowane auto", 20:"Niedziałające oświetlenie uliczne"};
var kategoria = {1:"Sygnalizacja świetlna", 2:"Komunikacja publiczna", 3:"Niepełnosprawni ruchowo", 4:"Rowerzyści", 5:"Pozostałe"};
var statusy = {1:"Nowe", 2:"Zweryfikowane", 3:"Anulowane", 4:"Zamknięte"};
var log, validation;
function isLogin(){
	log = getCookie("logwmb");
	validation = getCookie("validatorwmb");
	if(log==0){
		$("#notlogin").css("display", "block");
		$("#islogin").css("display", "none");
		$("#isloginGoogle").css("display", "none");
		$("#isloginFb").css("display", "none");
		$("#adminPanel").css("display", "none");
		$(".leaflet-draw.leaflet-control").css("display", "none");
		$("#who").empty();
	}
	if(log==1){
		$("#notlogin").css("display", "none");
		$("#islogin").css("display", "block");
		$("#isloginGoogle").css("display", "none");
		$("#isloginFb").css("display", "none");
		$(".leaflet-draw.leaflet-control").css("display", "block");
		$("#who").append("Zalogowany jako " + getCookie("emailwmb") + "  ");
	}
	if(log==2){
		$("#notlogin").css("display", "none");
		$("#islogin").css("display", "none");
		$("#isloginGoogle").css("display", "block");
		$(".leaflet-draw.leaflet-control").css("display", "block");
		$("#who").append("Zalogowany jako " + getCookie("emailwmb") + "  ");
	}
	if(log==3){
		$("#notlogin").css("display", "none");
		$("#islogin").css("display", "none");
		$("#isloginGoogle").css("display", "none");
		$("#isloginFb").css("display", "block");
		$(".leaflet-draw.leaflet-control").css("display", "block");
		$("#who").append("Zalogowany jako " + getCookie("emailwmb") + "  ");
	}
	if(validation == 1){
		$("#adminPanel").css("display", "block");
	}
	if(validation == 0){
		$("#adminPanel").css("display", "none");
	}
}

function validate(){
	var tmp = {"uzytkownicy": { }};
	tmp['uzytkownicy']['email'] = getCookie("emailwmb");
	tmp['uzytkownicy']['token'] = getCookie("tokenwmb");
	var postData = JSON.stringify(tmp);
	$.ajax({
			type: "POST",
			url: "//bariery.wroclaw.pl/api/uzytkownicy/valid",
			data: postData,
			contentType: "application/json",
			accept: "application/json",
			dataType: "json",
			success: function(json) {
				value = json.valid;
				document.cookie="validatorwmb="+value;
			}
		});
}

function getCookie(cname){
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for(var i=0; i<ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0)==' ') c = c.substring(1);
		if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
	}
	return "";
}

function changeStat(id){
	var tmp = {"zgloszenia": { }};
	var from = "#changeStatus"+id;
	var newStatus = $(from).val();
	tmp['zgloszenia']['id_zgloszenia'] = id.toString();
	tmp['zgloszenia']['id_statusu'] = newStatus;
	tmp['zgloszenia']['email'] = getCookie("emailwmb");
	tmp['zgloszenia']['token'] = getCookie("tokenwmb");
	var postData = JSON.stringify(tmp);
	$.ajax({
		type: "POST",
		url: "//bariery.wroclaw.pl/api/zgloszenia/updateStatusZgloszenia",
		data: postData,
		success: function (l) {
			alert("Status wybranego zgłoszenia został zaktualizowany");
		},
		error: function (l) {
			if(l.status == 200){
				alert("Status wybranego zgłoszenia został zaktualizowany");
			}
			else{
				alert("Wystąpiły błędy - status nie został zmieniony");
			}
		},
		contentType: "application/json",
		accept: "application/json",
		dataType: "json"
	});
}

var nazwa = "", nadawca = "", wiadomosc = "";
$('#nazwa').on('change', function() {
	nazwa = $("#nazwa").val();
})

$('#nadawca').on('change', function() {
	nadawca = $("#nadawca").val();
})

$('#wiadomosc').on('change', function() {
	wiadomosc = $("#wiadomosc").val();
})

/*function sendContactForm(){
	if(nazwa != ""){
		if(nadawca != ""){
			if(wiadomosc != ""){
				var tresc = "Wiadomość ze strony Wrocławskiej Mapy Barier<br><br>Od: " + nazwa +
				"<br>Adres e-mail: " + nadawca +
				"<br>Treść zapytania:<br>" + wiadomosc +
				"<br><br>WMB";
				$("#sukces").css("display","block");
}*/
$(function(){
	$("#contactformpro").submit(function(e){
		var info=$("#sukces");
		var form=$(this);
		$.ajax({
			url: "mail.php",
			dataType: "JSON",
			type: "post",
			data: form.serialize(),
			success: function(obj){
				if (obj.type=="ok"){
					info.append("Dziękujemy za wysłanie zgłoszenia.");
					form.get(0).reset();
				}
				else{
					info.append("Przepraszamy, ale wystąpił błąd podczas wysyłania wiadomości.");
				}
			},
			error : function(){
				info.append("Przepraszamy, ale wystąpił błąd podczas wysyłania wiadomości.");
			}
		});
		e.preventDefault();
	})
})

function del(id){
	var tmp = {"zgloszenia": { }};
	tmp['zgloszenia']['id_zgloszenia'] = id.toString();
	tmp['zgloszenia']['admin_email'] = getCookie("emailwmb");
	tmp['zgloszenia']['token'] = getCookie("tokenwmb");
	var postData = JSON.stringify(tmp);
	$.ajax({
		type: "POST",
		url: "//bariery.wroclaw.pl/api/zgloszenia/deleteZgloszenie",
		data: postData,
		success: function (l) {
			alert("Zgłoszenie usunięto");
			location.reload();
		},
		error: function (l) {
			alert("Wystąpiły błędy - zgłoszenia nie usunięto");
		},
		contentType: "application/json",
		accept: "application/json",
		dataType: "json"
	});
}