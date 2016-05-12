/*global namespace*/
/**
 * @namespace dom.game
 * @description dom.game namespace
 */
(function () {
    "use strict";
    var game = namespace('game');
    //var dom = namespace;
    /**
     * Start the game
     * @function start
     * @param {Event} x the event object
     * @returns {number} some weird number
     * @memberOf dom.game
     */
    game.start = function (x) {
        return 12 + x.pageX;
    };
}());
