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
        var score, i, html = [];
        for (i = 0; i < data.scores.length; i += 1) {
            score = data.scores[i];
            html.push(
                $('<p></p>')
                    .append($('<span></span>').text(score.name + ":"))
                    .append(document.createTextNode(score.score.toString()))
            );
        }
        divHighScores.find('div').html(html);
    };
}());