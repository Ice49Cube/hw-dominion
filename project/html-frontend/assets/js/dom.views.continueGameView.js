
(function () {
    "use strict";
    var views = window.views = window.views || {};
    var continueGameView = views.continueGameView = views.continueGameView || {};
    var divContinueGame;

    continueGameView.initialize = function () {
        divContinueGame = $('#divContinueGame');
    };

    continueGameView.show = function (oncompleted) {
        divContinueGame.slideDown(function () {
            oncompleted();
            divContinueGame.find('input')[0].focus();
        });
    };

    continueGameView.hide = function (oncompleted) {
        divContinueGame.slideUp(oncompleted);
    };

    continueGameView.name = "continueGameView";
}());
