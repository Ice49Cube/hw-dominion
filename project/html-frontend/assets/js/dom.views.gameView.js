/**
 * The gameView holds the main components for the game.
 * @namespace dom.views.gameView
 * @extends IView
 */
(function () {
    "use strict";
    /*global namespace*/
    var gameView = namespace("views.gameView");
    var dom = namespace;
    var elements = {};
    var document_fullScreenChange = function () {
        if (dom.util.fullScreen.element) {
            elements.fullscreen.removeClass('reduced').addClass('enabled');
        } else {
            elements.fullscreen.removeClass('enabled').addClass('reduced');
        }
    };
    var document_keydown_F11 = function (e) {
        if ((e.which || e.keyCode) === 122) {
            e.preventDefault();
            dom.util.fullScreen.request();
        }
    };
    var divFullScreen_click = function () {
        if (dom.util.fullScreen.element) {
            dom.util.fullScreen.exit();
        } else {
            dom.util.fullScreen.request();
        }
    };
    // Public
    gameView.initialize = function () {
        dom.deck.initialize();
        elements.view = $('.view.game');
        elements.fullscreen = $('.full-screen').on("click", divFullScreen_click);
        $(document).on("keydown", document_keydown_F11).on(dom.util.fullScreen.changeEventName, document_fullScreenChange);
       /* elements.table = elements.view.find('.player.table');
        elements.hand = elements.view.find('.player.hand');
        elements.view.on("click", function () {
            var children = elements.hand.find('img');
            var count = children.length + 1;
            if (count <= 17) {
                var max_width = 1116 - 70;
                var card_width = 188;
                var total = count * card_width;
                var margin;
                if (total > max_width) {
                    margin = (max_width - card_width) / (count - 1);
                    margin = -(card_width - margin);
                } else margin = 0;
                console.log(margin);
                elements.hand.append('<img src="assets/images/cards/copper.jpg" style="margin-left:' + margin + 'px" />');
                children.css('margin-left', margin + 'px');
            } else {
                alert('Add counter to card, ...');
            }
        });*/
    };
    gameView.show = function (oncompleted) {
        elements.view.append(dom.settings.getElement());
        setTimeout(function () {
            elements.view.fadeIn(oncompleted);
        }, 500);
    };
    gameView.hide = function (oncompleted) {
        elements.view.slideUp(oncompleted);
    };
}());
