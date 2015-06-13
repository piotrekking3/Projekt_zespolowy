/**************************************************
* Funkcje wspomagające Leaflet'a
**************************************************/
// Przycisk do Leaflet'a z wykorzystaniem Font Awesome
L.Control.FAButton = L.Control.extend({
	options: {
		icon: 'fa-plus-circle',
		title: '',
		position: 'topleft',
		id: ''
	},
	onAdd: function() {
		var div = L.DomUtil.create('div', 'leaflet-bar leaflet-control');
		this.a = L.DomUtil.create('a', 'leaflet-bar-part', div);
		this.a.href = '#';
		this.a.title = this.options.title;
		this.span = L.DomUtil.create('span', 'fa fa-lg ' + this.options.icon, this.a);
		this.span.id = this.options.id;
		L.DomEvent.on(this.a, 'click', this.onClick, this);
		return div;
	},
	callback: function() {},
	onClick: function(event) {
		L.DomEvent.stopPropagation(event);
		L.DomEvent.preventDefault(event);
		this.callback();
		this.a.blur();
	}
});

/**
* Metoda tworząca nowy przycisk na mapie
*
* @method showCommentButton
* @return {Object} Referencja na nowo utworzony obiekt przycisku
*/
L.FAButton = function(p_map, p_icon, p_callback, p_title, p_position, p_id) {
	var new_button = new L.Control.FAButton();
	if (p_id) new_button.options.id = p_id;
	if (p_position) new_button.options.position = p_position;
	if (p_title) new_button.options.title = p_title;
	if (typeof p_callback === 'function') new_button.callback = p_callback;
	if (p_icon) new_button.options.icon = p_icon;
	if (!(p_map === '')) p_map.addControl(new_button);
	return new_button;
};