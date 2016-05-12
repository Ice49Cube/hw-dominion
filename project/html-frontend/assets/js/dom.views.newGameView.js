/**
 * @namespace dom.views.newGameView
 * @extends IView
 */
(function () {
    "use strict";
    /*global namespace*/
    var newGameView = namespace("views.newGameView");
    var dom = namespace;
    var divNewGame;
    var a_click = function (e) {
        dom.views.gameInfoView.setPlayerCount($(this).index() + 2);
        dom.view.showNext(dom.views.gameInfoView);
        e.preventDefault();
    };
    newGameView.initialize = function () {
        divNewGame = $('.view.new-game');
        divNewGame.find('div>.img-button').on('click', a_click);
    };
    newGameView.hide = function (onCompleted) {
        divNewGame.slideUp(onCompleted);
    };
    newGameView.show = function (onCompleted) {
        divNewGame.slideDown(onCompleted);
    };
}()); // End of file