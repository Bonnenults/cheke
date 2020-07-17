(function(exports, undefined){
	'use strict';
	var document = exports.document;
	function createDiv(class_name){
		var div = document.createElement('div');
		div.setAttribute('class', class_name);
		document.body.appendChild(div);
		return div;
	}
	function Alert(){
		if(!(this instanceof Alert)) return;
		return this;
	}
	Alert.prototype = {
		init: function(){
			this.mask = this.mask || createDiv('mask');
			this.alert = this.alert || createDiv('alert');
			this.initEvents.call(this);
			return this;
		},
		show: function(message){
			this.alert.innerHTML = message || '';
			this.alert.style.display = 'block';
			var _this = this;
			setTimeout(function(){
				_this.alert.style.display = 'none';
			},2000)
		},
		hide: function(){
			this.alert.style.display = 'none';
			exports.event && (exports.event.stopPropagation(), exports.event.preventDefault());
		},
		initEvents: function(){
			this.hideHandler = this.hideHandler || this.hide.bind(this);
			this.alert.addEventListener('touchend', this.hideHandler, false);
		},
		removeEvents: function(){
			this.hideHandler = null;
			this.alert.removeEventListener('touchend', this.hideHandler, false);
		},
		reset: function(){
			this.removeEvents.call(this);
			this.alert.style.display = 'none';
		}
	}
	var alert = new Alert().init();
	exports.showAlert = alert.show.bind(alert);
	exports.hideAlert = alert.hide.bind(alert);
})(window);