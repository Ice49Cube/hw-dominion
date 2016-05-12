/**
 * @module homeView
 * @description The home view module.
 * @memberOf views
 */
(function () {
    "use strict";
    // Imports
    var game = window.game = window.game || {};
    var view = window.view = window.view || {};
    var views = window.views = window.views || {};
    var homeView = views.homeView = views.homeView || {};
    /**
     * @var divHome
     * @description Holds the home view container.
     * @private
     */
    var divHome;
    /**
     * @function initialize
     * @description Initializes the home view.
     */
    homeView.initialize = function () {
        divHome = $("#divHome");
        /**
         * @function aNewGame_click
         * @description Handles the click on aNewGame.
         * @private
         */
        $("#aNewGame").on("click", function (e) {
            view.showNext(views.newGameView);
            e.preventDefault();
        });
        /**
         * @function aContinueGame_click
         * @description Handles the click on aContinueGame
         * @private
         */
        $("#aContinueGame").on("click", function (e) {
            view.showNext(views.continueGameView);
            e.preventDefault();
        });
        /**
         * @function aViewHighScores_click
         * @description Handles the click on aViewHighScores
         * @private
         */
        $("#aViewHighScores").on("click", function (e) {
            //view.showNext(views.highScoresView);
            game.viewHighScores();
            e.preventDefault();
        });
    };
    /**
     * @function show
     * @description Shows the view.
     * @param {callback} oncompleted Called on completed.
     * @static
     */
    homeView.show = function (oncompleted) {
        divHome.slideDown(oncompleted);
    };
    /**
     * @function hide
     * @description Hides the view.
     * @param {callback} oncompleted Called on completed.
     * @static
     */
    homeView.hide = function (oncompleted) {
        divHome.slideUp(oncompleted);
    };
    /**
     * @property name
     * @description Name of this module.
     * @type {string}
     */
    homeView.name = "homeView";
}());
