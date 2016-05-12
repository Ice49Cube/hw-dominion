(function () {
    "use strict";
    /*global namespace*/
    var module = namespace('players');
    var dom = namespace;
    var elements = {};


    module.initialize = function () {
        elements.players = $('div.players');
    };

    module.setPlayers = function (players){
        var player, i;
        for(i = 0; i < players.length; i+= 1){
            player = players[i];
            if (player.cards) {
                dom.player.setPlayer(player);
            } else {
                dom.debug.assert(false, player.name);
            }
        }
    };
}());
