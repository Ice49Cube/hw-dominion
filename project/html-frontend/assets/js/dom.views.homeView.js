/**
 * @namespace dom.views.homeView
 * @extends IView
 */
(function () {
    "use strict";
    /*global namespace*/
    var homeView = namespace("views.homeView");
    var dom = namespace;
    var elements = {};
    homeView.initialize = function () {
        elements.view = $(".view.home");
        elements.view.find(".new-game").on("click", function (e) {
            e.preventDefault();
            dom.view.showNext(dom.views.newGameView);
        });
        elements.view.find(".view-high-scores").on("click", function (e) {
            e.preventDefault();
            dom.game.viewHighScores();
        });
    };
    homeView.show = function (oncompleted) {
        elements.view.append(dom.settings.getElement());
        elements.view.fadeIn(oncompleted);
    };
    homeView.hide = function (oncompleted) {
        elements.view.slideUp(oncompleted);
    };
}());