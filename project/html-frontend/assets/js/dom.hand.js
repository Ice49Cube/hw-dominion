/**
 * @namespace dom.hand
 */
(function () {
    "use strict";
    // addCard, removeCard
    // findCard?
    // enableDrag/disableDrag
    var hand = namespace("hand");
    var dom = namespace;
    var elements = {};
    var mouseData = {origin: {x: 0, y: 0}};
    var draggingEnabled;
    /**
     * @param {Event} e
     * @memberOf dom.hand
     * @private
     */
    var card_mousedown = function (e) {
        if (draggingEnabled) {
            var element = $(e.target).addClass('dragging');
            mouseData.origin.x = element.get(0).offsetLeft;
            mouseData.origin.y = element.get(0).offsetTop;
            mouseData.element = element;
        }
    };
    /**
     * @param {Event} e
     * @memberOf dom.hand
     * @private
     */
    var document_mousemove = function (e) {
        if (mouseData.element) {
            mouseData.element.css({
                "left": e.clientX - mouseData.origin.x + 'px',
                "top": e.clientY - mouseData.origin.y + 'px'
            });
        }
    };
    /**
     *
     * @param {Event} e
     * @memberOf dom.hand
     * @private
     */
    var document_mouseup = function () {
        if (mouseData.element) {
            mouseData.element = null;
            mouseData.element.removeClass('dragging').attr('style', '');
        }
    };
    /**
     *
     * @param {jQuery} $div
     * @returns {PlayerCard}
     */
    var divToPlayerCard = function ($div) {
        return {
            id: $div.data('id'),
            order: $div.data('order'),
            cardId: $div.data('card-id')
        };
    };
    /**
     *
     * @param id
     * @returns {jQuery|null}
     */
    var findByPlayerCardId = function (id) {
        var $div = elements.container.children().filter('[data-id=' + id + ']');
        return $div.length === 1 ? $div : null;
    };
    /**
     *
     * @param cardId
     * @returns {jQuery|null}
     */
    var findByGameCardId = function (cardId) {
        var $div = elements.container.children().filter('[data-card-id=' + cardId + ']');
        return $div.length === 1 ? $div : null;
    };
    hand.initialize = function () {
        elements.container = $('.player.hand > div');
        elements.dragging = null;
        $(document.body)
            .on("mousemove", document_mousemove)
            .on("mouseup", document_mouseup);
    };
    /**
     * @param {PlayerCard[]} cards
     */
    hand.setCards = function (cards) {
        var i;
        cards.sort(dom.util.numberSorter("order"));
        elements.container.html('');
        for (i = 0; i < cards.length; i += 1) {
            dom.hand.addCard(cards[i]);
        }
    };
    /**
     * @param {PlayerCard} card
     */
    hand.addCard = function (card) {
        dom.debug.log("hand.addCard");
        var gameCard = dom.deck.findCardById(card.cardId);
        dom.debug.log("GameCard: ", gameCard);
        var playerCard = findByGameCardId(card.cardId);
        if (playerCard === null) {
            dom.debug.log("PlayerCard: (null) - first card");
            var divs = elements.container.children();
            divs.removeClass('fx-card' + divs.length);
            divs.addClass('fx-card' + (divs.length + 1));
            elements.container.append($('<div></div>')
                .attr({
                    "class": gameCard.name.split('-').join('_'),
                    "data-count": 1,
                    "data-id": card.id,
                    "data-card-id": card.cardId,
                    "data-order": card.order
                })
                .on("mousedown", card_mousedown)
                .addClass('fx-card' + (divs.length + 1)));
        } else {
            dom.debug.log("PlayerCard: ", playerCard);
            playerCard.attr('data-count', parseInt(playerCard.attr('data-count'), 10) + 1);
        }
    };
}());
