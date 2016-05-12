/**
 * The gameInfoView is responsible for input of the player names.
 * @namespace dom.views.gameInfoView
 * @extends IView
 */
(function () {
    "use strict";
    /*global namespace*/
    var gameInfoView = namespace("views.gameInfoView");
    var dom = namespace;
    var elements = {};
    var aGameSettings_click = function (e) {
        alert('settings');
        //view.showNext(views.gameSettingsView);
        e.preventDefault();
    };
    var aStartGame_click = function (e) {
        var i, player, playerName, focusPlayer = function () { player.focus(); };
        var playerNames = [];
        var cardSet = elements.view.find("select").val();
        var players = elements.view.find("label:visible").splice(1);
        for (i = 0; i < players.length; i += 1) {
            player = $(players[i]).find("input");
            playerName = player.val();
            if (!/(?:^\w{1,20}$)/g.test(playerName)) {
                dom.overlay.showError('The name for player ' + (i + 1) + ', "' + playerName + '", is invalid.', {timeout: 2000, hideready: focusPlayer});
                return;
            }
            if (playerNames.indexOf(playerName) > -1) {
                dom.overlay.showError('Can\'t use the same player more than once.', {timeout: 2000, hideready: focusPlayer });
                return;
            }
            playerNames.push(playerName);
        }
        dom.views.gameIdView.setIsNewGame(true);
        dom.view.showNext(dom.views.gameIdView, {callback: function () {
            dom.game.startGame(playerNames, cardSet);
        }});
        e.preventDefault();
    };
    gameInfoView.initialize = function () {
        elements.view = $('.view.game-info');
        elements.view.find('.game-settings').on('click', aGameSettings_click);
        elements.view.find('.start-game').on('click', aStartGame_click);
    };
    gameInfoView.show = function (oncompleted) {
        elements.view.slideDown(function () {
            elements.view.find("input").get(0).focus();
            if (oncompleted) {
                oncompleted();
            }
        });
    };
    gameInfoView.hide = function (oncompleted) {
        elements.view.slideUp(oncompleted);
    };
    gameInfoView.setPlayerCount = function (playerCount) {
        var label = elements.view.find("label");
        label.find('input').val('');
        label.show().slice(playerCount + 1).hide();
    };
}());