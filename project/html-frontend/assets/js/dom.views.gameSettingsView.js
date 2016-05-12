(function () {
    "use strict";

    // Imports

    var views = window.views = window.views || {};
    var gameSettingsView = views.gameSettingsView = views.gameSettingsView || {};

    // Private

    var divGameSettings;

    // Public

    gameSettingsView .initialize = function () {
        divGameSettings = $('#divGameSettings');
    };

    gameSettingsView .show = function(oncompleted) {
        divGameSettings.slideDown(oncompleted);
    };

    gameSettingsView .hide = function(oncompleted) {
        divGameSettings.slideUp(oncompleted);
    };

    gameSettingsView.name = "gameSettingsView";
}());