(function () {
    "use strict";

    // Imports

    var views = window.views = window.views || {};
    var highScoresView = views.highScoresView = views.highScoresView || {};

    // Private

    var divHighScores;

    // Public
    highScoresView.initialize = function () {
        divHighScores = $('#divHighScores');
    };

    highScoresView.show = function(oncompleted) {
        divHighScores.slideDown(oncompleted);
    };

    highScoresView.hide = function(oncompleted) {
        divHighScores.slideUp(oncompleted);
    };

    highScoresView.setHighScores = function (data) {
        var html = [];
        for(var i = 0; i < data.scores.length; i++) {
            var score = data.scores[i];
            html.push(
                $('<p></p>')
                    .append($('<span></span>').text(score.name + ":"))
                    .append(document.createTextNode(score.score))
            );
        }
        divHighScores.find('div').html(html);
    };

    highScoresView.name = "highScoresView";

}());