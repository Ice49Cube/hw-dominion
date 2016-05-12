
// "Javascript namespace pattern"
Game = window.Game || {};

// Singleton, there is only one view object
View = Game.View = (function() {
	
	var screens;
	var currentScreen; // private met underscore starten? misschien beter...
	
	var initialize = function() {
		currentScreen = -1;
		screens = [];
		loadScreens();
		nextScreen();
	};

	var getCurrent = function () {
		return screens[currentScreen];
	};
	
	var loadScreens = function () {
		screens.push(new Game.HomeScreen());
		screens.push(new Game.SecondScreen());	
	};
	
	var nextScreen = function () {
		if(currentScreen >= 0)
		{
			var next = (currentScreen + 1) >= screens.length ? 0 : currentScreen + 1;
			setScreen(currentScreen, next);
		}
		else
		{
			currentScreen = 0;
			screens[0].show();
		}
	};
	
	var setScreen = function (current, next) {
		screens[current].hide(function() {
			screens[next].show();
		});
		currentScreen = next;
	};
	
	// View public members, "interface"
	return {
		getCurrent: getCurrent,
		initialize: initialize,
		nextScreen: nextScreen
	};	

}());


Game.HomeScreen = function() {
	// private
	var screen = $("#screenHome");
	var buttonAdd = $("#buttonAddPlayer");
	var buttonStart = $("#buttonStartGame");
	
	// event handlers
	buttonAdd.on("click", function() {
		Game.addPlayer();
	});
	
	buttonStart.on("click", function() {
		Game.start();
	});
	
	// public
	this.show = function(oncompleted) {
		screen.show(oncompleted);
	};
	
	this.hide = function(oncompleted) {
		screen.hide(oncompleted);
	};
};

Game.SecondScreen = function () {
	// private
	var screen = $("#screenTwo");
	var result = $('#result');
	
	screen.on('click', function(){
		//alert("somesomesome");
		Controller.send({name: "Mike", age: 25});
	});
	
	
	// public
	
	this.hide = function(oncompleted) {
		screen.hide(oncompleted);
	};

	this.setResult = function (html) {
		result.html(html);
	};
	
	this.show = function(oncompleted) {
		screen.show(oncompleted);
	};
};