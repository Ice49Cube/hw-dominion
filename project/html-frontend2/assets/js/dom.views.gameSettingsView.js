/**
 * The gameSettingsView is responsible for the settings.
 * @namespace dom.views.gameSettingsView
 * @extends IView
 */
(function () {
    "use strict";
    /*global namespace*/
    var gameSettingsView = namespace("views.gameSettingsView");
    // Private
    var elements = {};
    // Public
    gameSettingsView.initialize = function () {
        elements.view = $('#divGameSettings');
    };
    gameSettingsView.show = function (oncompleted) {
        elements.view.slideDown(oncompleted);
    };
    gameSettingsView.hide = function (oncompleted) {
        elements.view.slideUp(oncompleted);
    };
}());