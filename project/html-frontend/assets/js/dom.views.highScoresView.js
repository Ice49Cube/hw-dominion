/**
 * @namespace dom.views.highScoresView
 * @extends IView
 */
(function () {
    "use strict";
    /*global namespace*/
    var highScoresView = namespace("views.highScoresView");
    var divHighScores;
    highScoresView.initialize = function () {
        divHighScores = $('#divHighScores');
    };
    highScoresView.show = function (oncompleted) {
        divHighScores.slideDown(oncompleted);
    };
    highScoresView.hide = function (oncompleted) {
        divHighScores.slideUp(oncompleted);
    };
    /**
     *
     * @param {ViewHighScoresResult} data
     */
    highScoresView.setHighScores = function (data) {
    };
}());