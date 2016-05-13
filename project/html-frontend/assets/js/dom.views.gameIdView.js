/**
 * The gameIdView offers the user the url with the game id to restart the game.
 * @namespace dom.views.gameIdView
 * @extends IView
 */
(function () {
    "use strict";
    /*global namespace, Clipboard */
    var gameIdView = namespace("views.gameIdView");
    var dom = namespace;
    var clipboard, elements = {};
    // Public
    gameIdView.initialize = function () {
        elements.view = $('.view.game-id');
        elements.title = elements.view.find('.img-title');
        elements.a = elements.view.find('a.game-id-ok');
        elements.input = elements.view.find('input[type=text]');
        elements.label = elements.view.find('label');
        elements.a.on("click", function () {
            if (dom.settings.fullScreen) {
                dom.util.fullScreen.request(document.body);
            }
            dom.view.showNext(dom.views.gameView);
        });
        elements.input.on("focus", function () {
            elements.input.select();
        }).on("change", function () {
            elements.input.val(elements.input.data('url'));
        });
        clipboard = new Clipboard('.clipboard');
        clipboard.on('success', function () {
            if (elements.input.data('url') !== '') {
                elements.label.text('The url was copied to the clipboard.');
            }
            elements.input.blur();
        }).on("error", function () {
            window.prompt("Please press CTRL+C to copy the url.", elements.input.data('url'));
        });
    };
    gameIdView.show = function (oncompleted) {
        elements.label.text('Please wait for a response of the server...');
        elements.a.hide();
        elements.input.data('url', '').val('');
        elements.view.slideDown(oncompleted);
    };
    gameIdView.hide = function (oncompleted) {
        elements.view.slideUp("slow", function () {
            if (oncompleted) {
                oncompleted();
            }
        });
    };
    gameIdView.setGameId = function (id) {
        var url = location.href.split(/[?#]/)[0] + '?id=' + id;
        elements.input.data('url', url).val(url).select();
        elements.a.show();
        elements.label.text('Save this url if you wish to continue later.');
        window.history.pushState({ id: id }, "Dominion Game " + id, location.pathname + '?id=' + id);
    };
    gameIdView.setIsNewGame = function (isNewGame) {
        elements.title.css('background-image', (isNewGame ? 'url(assets/images/game_id.png)' : 'url(assets/images/continue_game.png)'));
    };
}());
