/**
 * The gameSettingsView is responsible for the settings.
 * @namespace dom.views.gameSettingsView
 * @extends IView
 */
(function () {
    "use strict";
    /*global namespace*/
    var settings = namespace("settings");
    // Private
    var elements = {};
    // Public
    settings.initialize = function () {
        elements.view = $('.settings');
        elements.view.show();
        elements.view.on("click", function () { alert('ok'); });
    };
    settings.getElement = function () {
        return elements.view;
    };
}());