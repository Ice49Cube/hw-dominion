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
    /*
     *
     * @param {jQuery} $div
     * @returns {PlayerCard}

    var divToPlayerCard = function ($div) {
        return {
            id: $div.data('id'),
            order: $div.data('order'),
            cardId: $div.data('card-id')
        };
    };*/
    /**
     * @param {jQuery.Event} e
     * @param {Object} e.originalEvent
     * @param {Object} e.originalEvent.dataTransfer
     */
    var hand_dragStart = function (e) {
        e.stopPropagation();
        e.originalEvent.dataTransfer.setData("playing-id", $(this).data('id'));
    };
    /*
     *
     * @param id
     * @returns {jQuery|null}

    var findByPlayerCardId = function (id) {
        var $div = elements.hand.children().filter('[data-id=' + id + ']');
        return $div.length === 1 ? $div : null;
    };*/
    /**
     *
     * @param cardId
     * @returns {jQuery|null}
     */
    var findByGameCardId = function (cardId) {
        var $div = elements.hand.children().filter('[data-card-id=' + cardId + ']');
        return $div.length === 1 ? $div : null;
    };
    hand.initialize = function () {
        elements.hand = $('.hand > div');
    };
    /**
     * @param {PlayerCard[]} cards
     */
    hand.setCards = function (cards) {
        var i;
        cards.sort(dom.util.numberSorter("order"));
        elements.hand.html('');
        for (i = 0; i < cards.length; i += 1) {
            dom.hand.addCard(cards[i]);
        }
    };
    /**
     * @param {PlayerCard} card
     */
    hand.addCard = function (card) {
        //dom.debug.log("hand.addCard");
        var gameCard = dom.deck.findCardById(card.cardId);
        //dom.debug.log("GameCard: ", gameCard);
        var playerCard = findByGameCardId(card.cardId);
        if (playerCard === null) {
            //dom.debug.log("PlayerCard: (null) - first card");
            var divs = elements.hand.children();
            divs.removeClass('fx-hand' + divs.length);
            divs.addClass('fx-hand' + (divs.length + 1));
            elements.hand.append($('<div></div>')
                .attr({
                    "class": gameCard.name.split('-').join('_'),
                    "data-count": 1,
                    "data-id": card.id,
                    "data-card-id": card.cardId,
                    "data-order": card.order,
                    "draggable": true
                })
                .on("dragstart", hand_dragStart)
                .addClass('fx-hand' + (divs.length + 1)));
        } else {
            //dom.debug.log("PlayerCard: ", playerCard);
            playerCard.attr('data-count', parseInt(playerCard.attr('data-count'), 10) + 1);
        }
    };
}());

/*
.on("mousedown", card_mousedown)

 var mouseData = {origin: {x: 0, y: 0}};
 var dragging = false;
*elements.dragging = null;
 $(document.body)
 .on("mousemove", document_mousemove)
 .on("mouseup", document_mouseup);

*
 * @param {Event} e
 * @memberOf dom.hand
 * @private
 *
 var card_mousedown = function (e) {
 if (dragging) {
 var element = $(e.target).addClass('dragging');
 mouseData.origin.x = element.get(0).offsetLeft;
 mouseData.origin.y = element.get(0).offsetTop;
 mouseData.element = element;
 }
 };
 *
 * @param {Event} e
 * @memberOf dom.hand
 * @private
 *
 var document_mousemove = function (e) {
 if (mouseData.element) {
 mouseData.element.css({
 "left": e.clientX - mouseData.origin.x + 'px',
 "top": e.clientY - mouseData.origin.y + 'px'
 });
 }
 };
 *
 *
 * @memberOf dom.hand
 * @private
 *
 var document_mouseup = function () {
 if (mouseData.element) {
 mouseData.element = null;
 mouseData.element.removeClass('dragging').attr('style', '');
 }
 };*/