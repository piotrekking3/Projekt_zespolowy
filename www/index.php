<!doctype html>
<html>
	<head lang="pl">
		<meta charset="UTF-8">
		<meta name="viewport" content="initial-scale=0.75, width=device-width, user-scalable=yes, minimum-scale=0.5, maximum-scale=2.0">
		<link rel="stylesheet" href="./styles/jquery.ui.css">
		<link rel="stylesheet" href="./styles/leaflet.css">
		<link rel="stylesheet" href="./styles/leaflet.draw.css">
		<link rel="stylesheet" href="./styles/design.css">
		<script src="./scripts/jquery.js"></script>
		<script src="./scripts/jquery.ui.js"></script>
		<script src="./scripts/leaflet.js"></script>
		<script src="./scripts/leaflet.draw.js"></script>
		<!--[if lt IE 9]>
		<script src="./scripts/html5shiv.js"></script>
		<![endif]-->
		<title>Wrocławska Mapa Barier</title>
	</head>
	<body>
		<header role="banner">
			<div id="title">
				<h1>Wrocławska Mapa Barier</h1>
				<h2>Identyfikacja miejskich utrudnień ruchu</h2>
			</div>
		</header>
		<nav role="navigation">
			<ul>
				<li><a href="#" rel="nofollow">Kontakt</a></li>
				<li><a href="#" rel="author">O projekcie</a></li>
				<li><a href="#" rel="help">Rodzaje barier</a></li>
				<li><a href="#">Lista barier</a></li>
				<li><a href="index.php" rel="index">Mapa barier</a></li>
			</ul>
		</nav>
		<div id="map" role="main"></div>
		<div id="add-marker" title="Nowe zgłoszenie">
			<p class="validateTips">Uzupełnij poniższe pola</p>
			<form>
				<fieldset>
					<label for="category">Kategoria zgłoszenia*</label>
					<select name="category" id="category" required>
					<option value="">Wybierz</option>
						<optgroup label="Grupa 1">
							<option value="kategoria11">Kategoria 1.1</option>
							<option value="kategoria12">Kategoria 1.2</option>
							<option value="kategoria13">Kategoria 1.3</option>
						</optgroup>
						<optgroup label="Grupa 2">
							<option value="kategoria21">Kategoria 2.1</option>
							<option value="kategoria22">Kategoria 2.2</option>
							<option value="kategoria23">Kategoria 2.3</option>
						</optgroup>
					</select>
					<label for="description">Opis problemu*</label>
					<textarea name="description" id="description" class="text ui-widget-content ui-corner-all" required></textarea>
					<label for="image">Zdjęcie</label>
					<input type="file" name="image" id="image" class="text ui-widget-content ui-corner-all">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
				</fieldset>
			</form>
		</div>
		<div id="login" title="Logowanie">
			<form>
				<fieldset>
					<label for="email">Adres e-mail</label>
					<input type="email" name="email" id="email" class="text ui-widget-content ui-corner-all" required>
					<label for="password">Hasło</label>
					<input type="password" name="password" id="password" class="text ui-widget-content ui-corner-all" required>
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
				</fieldset>
			</form>
		</div>
		<footer>
			<ul>
				<li><a href="#" onclick="loginOpen()" rel="nofollow">Panel administracyjny</a></li>
				<li><a href="http://jigsaw.w3.org/css-validator/check/referer" rel="nofollow">CSS 3</a></li>
				<li><a href="http://validator.w3.org/check/referer" rel="nofollow">HTML 5</a></li>
			</ul>
		</footer>
		<script src="./scripts/wmb.layers.js"></script>
		<script src="./scripts/wmb.controls.js"></script>
		<script src="./scripts/wmb.markers.icons.js"></script>
		<script src="./scripts/wmb.markers.js"></script>
		<script src="./scripts/wmb.map.js"></script>
		<script src="./scripts/wmb.forms.js"></script>
	</body>
</html>