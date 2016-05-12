// Minimum View Implementation...

(function () {
    "use strict";

    // Imports

    var views = window.views = window.views || {};
    var gameIdView = views.gameIdView = views.gameIdView || {};

    // Private

    var divGameId;

    var a_click = function (e) {


    };

    // Public

    gameIdView.initialize = function () {
        divGameId = $('#divGameId');
        divGameId.find('a').on('click', a_click);
    };

    gameIdView.show = function(oncompleted) {
        divGameId.slideDown(oncompleted);
    };

    gameIdView.hide = function(oncompleted) {
        divGameId.slideUp(oncompleted);
    };

    gameIdView.name = "gameIdView";
}());
