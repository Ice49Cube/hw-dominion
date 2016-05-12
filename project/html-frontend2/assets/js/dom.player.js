

(function () {
    "use strict";
    /*global namespace*/
    var player = namespace("player");
    var dom = namespace;
    var elements = {};

    player.setPlayer = function (data) {
        dom.hand.setCards(data.cards);
    };

    player.initialize = function () {

    };
}());