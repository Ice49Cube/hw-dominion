/**
 * @module game
 * @description The game module.
 */
(function () {
    "use strict";
    // Imports
    var routing = window.routing = window.routing || {};
    var game = window.game = window.game || {};
    var view = window.view = window.view || {};
    var views = window.views = window.views || {};
    //
    game.initialize = function (config) {
        view.initialize(config.view);
        routing.initialize(game, config.routing);
        var search = location.search;
        if (!!search && search.substr(0, 1) === '?') {
            search = search.substr(1);
            alert('Continue game with id: ' + search);
        }
    };
    //
    game.onError = function () {
        window.alert('An error occurred. Check the game state and respond accordingly...');
        console.log('game.onError', arguments);
    };
    //
    game.startNewGame = function (playerNames) {
        routing.invoke("startNewGame", {'playerNames': playerNames });
    };
    game.startNewGameSuccess = function (data) {
        window.alert('Start New Game Success: ' + JSON.stringify(data));
    };
    game.startNewGameFailed = function (data) {
        window.alert('Start New Game Failed: ' + JSON.stringify(data));
    };
    //
    game.testServer = function () {
        routing.invoke("testServer", {success: false, code: 404});
    };
    game.testServerSuccess = function (data) {
        window.alert('Test Server Success: ' + JSON.stringify(data));
    };
    game.testServerFailed = function (data) {
        window.alert('Test Server Failed: ' + JSON.stringify(data));
    };
    //
    game.viewHighScores = function () {
        routing.invoke("viewHighScores");
    };
    game.viewHighScoresSuccess = function (data) {
        views.highScoresView.setHighScores(data);
        view.showNext(views.highScoresView);
        // BETTER!!!!
        //
        //view.showOverlay(views.highScoresView);
        // Removed
        //window.alert('View High Scores Success: ' + JSON.stringify(data));
    };
    game.viewHighScoresFailed = function (data) {
        window.alert('View High Scores Failed: ' + JSON.stringify(data));
    };

}()); // End of file
