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
var log;
function isLogin(){
	log = getCookie("logwmb");
	if(log==0){
		$("#notlogin").css("display", "block");
		$("#islogin").css("display", "none");
		$("#isloginGoogle").css("display", "none");
		$("#isloginFb").css("display", "none");
		$("#adminPanel").css("display", "none");
	}
	if(log==1){
		$("#notlogin").css("display", "none");
		$("#islogin").css("display", "block");
		$("#isloginGoogle").css("display", "none");
		$("#isloginFb").css("display", "none");
		$("#adminPanel").css("display", "block");
	}
	if(log==2){
		$("#notlogin").css("display", "none");
		$("#islogin").css("display", "none");
		$("#isloginGoogle").css("display", "block");
		$("#adminPanel").css("display", "none");
	}
	if(log==3){
		$("#notlogin").css("display", "none");
		$("#islogin").css("display", "none");
		$("#isloginGoogle").css("display", "none");
		$("#isloginFb").css("display", "block");
		$("#adminPanel").css("display", "none");
	}
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
		url: "http://virt2.iiar.pwr.edu.pl/api/zgloszenia/updateStatusZgloszenia",
		data: postData,
		success: function (l) {
			alert("Status wybranego zgłoszenia został zaktualizowany");
		},
		error: function (l) {
			alert("Wystąpiły błędy - status nie został zmieniony");
		},
		contentType: "application/json",
		accept: "application/json",
		dataType: "json"
	});
}