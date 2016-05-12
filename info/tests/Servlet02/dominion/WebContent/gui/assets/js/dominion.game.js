
/**
 * Game singleton object.
 */
 
// "Javascript namespace pattern"
Game = window.Game || {};

Game.addPlayer = function () {
	var number = Game.players.length + 1;
	var name = "player " + number;
// for demo: 
	Game.players.push(name);
// normal:
//	Game.players.push(Game.createPlayer(name));
};

Game.initialize = function () {
	Game.View.initialize();
	Game.players = [];
};

Game.start = function () {
	Game.View.nextScreen();
	var screen = Game.View.getCurrent();
	screen.setResult(Game.players.join("<br/>"));
};


