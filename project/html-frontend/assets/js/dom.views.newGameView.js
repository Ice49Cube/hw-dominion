/**
 * @module newGameView
 * @description The newGameView view module.
 */
(function () {
    "use strict";
    // Import other modules (and one MUST import this one too...)
    var view = window.view = window.view || {};
    var views = window.views = window.views || {};
    var newGameView = views.newGameView = views.newGameView || {};
    /**
     * @var divNewGame
     * @description Holds the html element of this module's view.
     * @private
     */
    var divNewGame;

    var a_click = function (e) {

        views.gameInfoView.setPlayerCount($(this).index() + 1);
        view.showNext(views.gameInfoView);
        e.preventDefault();
    };
    /**
     * @function initialize
     * @description Initializes this module and loads jQuery elements and attaches all events.
     */
    newGameView.initialize = function () {
        divNewGame = $('#divNewGame');
        divNewGame.find('a').on('click', a_click);
    };
    /**
     * @function hide
     * @description Hides the view.
     * @param onCompleted Called once the element is finished hiding.
     */
    newGameView.hide = function (onCompleted) {
        divNewGame.slideUp(onCompleted);
    };
    /**
     * @function show
     * @description Shows the view.
     * @param onCompleted Called once the element is finished showing.
     */
    newGameView.show = function (onCompleted) {
        divNewGame.slideDown(onCompleted);
    };
    /**
     * @property name
     * @description The name of this module.
     * @type {string}
     */
    newGameView.name = "newGameView";
}()); // End of file