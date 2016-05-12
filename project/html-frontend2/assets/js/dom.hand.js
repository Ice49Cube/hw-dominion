(function () {
    "use strict";
    var hand = namespace("hand");
    var dom = namespace;
    var elements = {};

    var container_mousedown = function (e) {
        elements.dragging = $(e.target);
    };

    var document_mousemove = function (e) {
        if (elements.dragging) {
            $dragging.offset({
                top: e.pageY,
                left: e.pageX
            });
        }
    };

    var document_mouseup = function (e) {
        elements.dragging = null;
    };

    hand.initialize = function () {
        elements.container = $('.player.hand > div');
        elements.dragging = null;
        $(document.body)
            .on("mousemove", document_mousemove)
            .on("mouseup", document_mouseup);
    };



    /**
     *
     * @param {PlayerCard[]} cards
     */
    hand.setCards = function (cards) {
        var i, info, images;
        elements.container.removeClass("fx-card" + elements.container.find("div").length);
        cards.sort(function (a, b) { return a.order - b.order; });
        for(i = 0; i < cards.length; i += 1) {
            info = dom.deck.findCardById(cards[i].cardId);
            elements.container.append($('<div></div>').addClass(info.name.split('-').join('_')));
            alert(cards[i].id + " " + info.name);
        }
        var images = elements.container.find("div");
        images.addClass("fx-card" + images.length);
    };
}());
