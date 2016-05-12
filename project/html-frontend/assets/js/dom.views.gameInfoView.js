    (function () {
    "use strict";

    // Imports

    var views = window.views = window.views || {};
    var gameInfoView = views.gameInfoView = views.gameInfoView || {};

    // Private

    var divGameInfo;

    var aGameSettings_click = function (e) {
        view.showNext(views.gameSettingsView);
        e.preventDefault();
    };
    var aStartGame_click = function (e) {
        //window.alert(divGameInfo.find("label:visible").length + ' players');
        view.showNext(views.gameIdView);
        e.preventDefault();
    };

    // Public

    gameInfoView.initialize = function () {
        divGameInfo = $('#divGameInfo');
        $('#aGameSettings').on('click', aGameSettings_click);
        $('#aStartGame').on('click', aStartGame_click);
    };

    gameInfoView.show = function(oncompleted) {
        divGameInfo.slideDown(oncompleted);
    };

    gameInfoView.hide = function(oncompleted) {
        divGameInfo.slideUp(oncompleted);
    };

    gameInfoView.setPlayerCount = function (playerCount) {
        divGameInfo.find("label").show().slice(playerCount).hide();
    };

    gameInfoView.name = "gameInfoView";

}());