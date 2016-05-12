

// "Javascript namespace pattern"
Game = window.Game = window.Game || {};

/**
 * Controller
 */
Controller = window.Game.Controller = {};

Controller.configuration = {
	url: "http://localhost:8080/dominion/dominion"
};

Controller.error = function () {
	// Display communication error full screen
	// Set timeout for retry
	// Reconnect (first just call Game.initialize and continue game)
};

Controller.dispatch = function(name, data) {
	Game[name].apply(Game, data);
};

Controller.send = function(/* args */) {
	var data = Array.prototype.slice.call(arguments);
	$.post(Controller.configuration.url, 
		arguments[0]
	).done(function() {
		Controller.dispatch.apply(Controller, JSON.parse(arguments));
	}).fail(function() {
		Controller.error();
	});
};