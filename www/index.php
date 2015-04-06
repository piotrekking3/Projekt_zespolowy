<?php
require_once('./vendor/autoload.php');

$app = new \Slim\Slim(array('debug' => true, 'templates.path' => './templates'));

$app->get('/contact', function () use ($app) {
	$data = array('title' => 'Kontakt - Wrocławska Mapa Barier', 'active' => 'contact');
	$app->render('header.html', $data);
	$app->render('contact.html');
	$app->render('forms.html');
	$app->render('footer.html');
});

$app->get('/about', function () use ($app) {
	$data = array('title' => 'O projekcie - Wrocławska Mapa Barier', 'active' => 'about');
	$app->render('header.html', $data);
	$app->render('about.html');
	$app->render('forms.html');
	$app->render('footer.html');
});

$app->get('/types', function () use ($app) {
	$data = array('title' => 'Rodzaje barier - Wrocławska Mapa Barier', 'active' => 'types');
	$app->render('header.html', $data);
	$app->render('types.html');
	$app->render('forms.html');
	$app->render('footer.html');
});

$app->get('/list', function () use ($app) {
	$data = array('title' => 'Lista barier - Wrocławska Mapa Barier', 'active' => 'list');
	$app->render('header.html', $data);
	$app->render('list.html');
	$app->render('forms.html');
	$app->render('footer.html');
});

$app->get('/map', function () use ($app) {
	$data = array('title' => 'Wrocławska Mapa Barier', 'active' => 'map');
	$app->render('header.html', $data);
	$app->render('map.html');
	$app->render('forms.html');
	$app->render('footer.html');
});

$app->get('/', function () use ($app) {
	$data = array('title' => 'Wrocławska Mapa Barier', 'active' => 'map');
	$app->render('header.html', $data);
	$app->render('map.html');
	$app->render('forms.html');
	$app->render('footer.html');
});

$app->run();
?>