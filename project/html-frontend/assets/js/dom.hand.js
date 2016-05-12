/**
 * @namespace dom.hand
 */
(function () {
    "use strict";
    var hand = namespace("hand");
    var dom = namespace;
    var elements = {};
    var position = null;

    var card_mousedown = function (e) {
        elements.dragging = $(e.target).css('cursor', 'move').css('transform', 'rotateZ(0deg)');
        position = elements.dragging.pos();
    };

    var document_mousemove = function (e) {
        if (elements.dragging) {
            elements.dragging.offset({top: e.pageY - elements.dragging.outerHeight() / 2, left: e.pageX - elements.dragging.outerWidth() / 2});
            console.log("ok");
        }
    };

    var document_mouseup = function (e) {
        elements.dragging.attr('style', '');
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
        var i, images;
        elements.container.removeClass("fx-card" + elements.container.find("div").length);
        cards.sort(function (a, b) { return a.order - b.order; });
        for(i = 0; i < cards.length; i += 1) {
            dom.hand.addCard(cards[i]);
        }
        images = elements.container.find("div");
        images.addClass("fx-card" + images.length);
    };

    /**
     *
     * @param {PlayerCard} card
     */
    hand.addCard = function (card) {
        dom.debug.log("Add card to hand: ", card);
        var cards = elements.container.find("div");
        cards.each(function () {
            dom.debug.log($(this));
        });
        elements.container.append($('<div></div>')
            .attr(
                {
                    'class': dom.deck.findCardById(card.cardId).name.split('-').join('_'),
                    'data-id': card.id,
                    'data-cardId': card.cardId,
                    'data-order': card.order
                }
            ).on("mousedown", card_mousedown)
        );
    };

    hand.removeCard = function (cardId) {

    };
}());
