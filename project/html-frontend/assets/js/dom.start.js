/**
 * @namespace Dominion
 * @description <b>The Dominion game.</b>
 */
$(function () {
    "use strict";
    // Import the game module.
    var game = window.game; // Not needed here -> = window.game || {};
    var views = window.views; // Not needed here -> = window.views || {};
    // Game configuration...
    var config = {
        routing: {
            timeout: 3000,
            url: location.protocol + "//localhost:8080/Server"
            //url: location.protocol + "//" + location.host + "/Server"
            //url: location.protocol + "//dom.node2.be/Server"
        },
        view: {
            first: views.homeView
        }

    };
    game.initialize(config);
});
