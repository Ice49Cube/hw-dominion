/**
 * @namespace dom.game
 * @description Contains all the methods between the routing and views. Handles game events.
 */
(function () {
    "use strict";
    /*global namespace*/
    var game = namespace("game");
    var dom = namespace;
    var showFail = function (message) {
        dom.overlay.showError(message, {hide: false});
        dom.debug.log(message);
    };
    /**
     * @param {Event} e
     * @param {number} e.id
     * @memberOf dom.game
     * @private
     */
    var game_buycard = function (e) {
        dom.debug.log(dom.deck.findCardById(e.id));
        dom.deck.buyCard(e.id);
    };
    /**
     * @param id
     * @memberOf dom.game
     */
    game.continueGame = function (id) {
        var options = { callback: function () {
            dom.game.startGame(id);
        }};
        dom.views.gameIdView.setIsNewGame(false);
        dom.view.showNext(dom.views.gameIdView, options);
    };
    /**
     * @memberOf dom.game
     */
    game.initialize = function () {
        $('.view.game')
            .on("buycard", game_buycard);
    };
    /**
     * Callback called by jQuery when on an error when processing an ajax request. Can be both a response
     * from the server as a response from jQuery locally. Only called when there's no method name or no
     * "Failed" callback for the method.
     * @memberOf dom.game
     */
    game.onError = function () {
        var html = 'An error occurred, please refresh the page.<br>If the problem persists, contact the service provider.';
        dom.debug.log(arguments);
        showFail(html);
    };
    ////////////////////////////////////////////////////////////////////////////
    /**
     * @param {String[]|Number} codeOrPlayerNames Either the game code or player names..
     * @param {String} [cardSet=undefined] If the first argument are the player names the card set, else undefined.
     * @memberOf dom.game
     */
    game.startGame = function (codeOrPlayerNames, cardSet) {
        var data;
        if (cardSet === undefined) {
            data = {"code": codeOrPlayerNames};
        } else {
            data = {"playerNames": codeOrPlayerNames, "cardSet": cardSet};
        }
        dom.routing.invoke("startGame", data);
    };
    /**
     * @param {StartGameResult} data See the server...
     * @memberOf dom.game
     */
    game.startGameSuccess = function (data) {
        var i;
        dom.views.gameIdView.setGameId(data.id);
        dom.deck.setCards(data.cards);
        dom.players.setPlayers(data.players);
        //dom.players.setPlayers(data);
        //for (i = 0; i < data.players.length; i += 1) {
        //    if(data.players[i].cards)
        //        console.log(data.players[i], data.players[i].cards);
        //}
    };
    game.startGameFailed = function (data) {
        if(data.error && data.error.message && data.error.message === "No game data.") {
            dom.overlay.showError(data.error.message, {hide:true,timeout:3000});
            dom.view.getCurrent().hide(function () {
                dom.view.clearViewStack();
                dom.view.showFirstView(dom.views.homeView);
                });
            if (dom.util.queryString.parse(location.search).id) {
                window.history.pushState(null, "Dominion Game", location.pathname);
            }
        } else {
            showFail('Start Game Failed: ' + JSON.stringify(data));
        }
    };
    ////////////////////////////////////////////////////////////////////////////
    game.testServer = function (data) { dom.routing.invoke("testServer", $.extend({success: false, code: 404}, data || {})); };
    game.testServerSuccess = function (data) { alert('Test Server Success: ' + JSON.stringify(data)); };
    game.testServerFailed = function (data) { alert('Test Server Failed: ' + JSON.stringify(data)); };
    ////////////////////////////////////////////////////////////////////////////
    game.viewHighScores = function () {
        dom.routing.invoke("viewHighScores");
    };
    game.viewHighScoresSuccess = function () {
        return undefined;
        //dom.views.highScoresView.setHighScores(data);
        //dom.view.showNext(dom.views.highScoresView);
        // BETTER!!!!
        //view.showOverlay(views.highScoresView);
    };
    game.viewHighScoresFailed = function (data) {
        showFail('View High Scores Failed: ' + JSON.stringify(data));
    };
    /*
     * @function
     * @name onComplete
     * @memberOf client
     */
}()); // End of file

