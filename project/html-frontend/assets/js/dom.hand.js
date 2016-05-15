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
     * @param {jQuery.Event} e
     * @param {Object} e.originalEvent
     * @param {Object} e.originalEvent.dataTransfer
     * @memberOf dom.hand
     */
    var hand_dragStart = function (e) {
        e.stopPropagation();
        e.originalEvent.dataTransfer.setData("Text", JSON.stringify({hand: divToPlayerCard($(this))}));
    };
    /**
     *
     * @param id
     * @returns {jQuery|null}
     * @memberOf dom.hand
     */
    var findByPlayerCardId = function (id) {
        var $div = elements.hand.children().filter('[data-id=' + id + ']');
        return $div.length === 1 ? $div : null;
    };
    /**
     *
     * @param cardId
     * @returns {jQuery|null}
     * @memberOf dom.hand
     */
    var findByGameCardId = function (cardId) {
        var $div = elements.hand.children().filter('[data-card-id=' + cardId + ']');
        return $div.length === 1 ? $div : null;
    };
    /**
     *
     * @param {PlayerCard} card
     * @memberOf dom.hand
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
                    "class": gameCard.name.split('-').join('_') + " fx-hand" + (divs.length + 1),
                    "data-count": 1,
                    "data-id": JSON.stringify([card.id]),
                    "data-card-id": card.cardId,
                    "data-order": card.order,
                    "data-is-action": gameCard.isAction,
                    "data-is-coin": gameCard.isCoin,
                    "draggable": false,
                    "title": gameCard.name.replace('_', ' ')
                })
                .on("dragstart", hand_dragStart));
        } else {
            //dom.debug.log("PlayerCard: ", playerCard);
            var ids = JSON.parse(playerCard.attr("data-id"));
            ids.push(card.id);
            playerCard
                .attr("data-id", JSON.stringify(ids))
                .attr('data-count', parseInt(playerCard.attr('data-count'), 10) + 1);
        }
    };
    /**
     * @memberOf dom.hand
     */
    hand.initialize = function () {
        elements.hand = $('.hand > div');
    };
    /**
     * @param {Number} cardId
     * @memberOf dom.hand
     */
    hand.removeCard = function (cardId) {
        var playerCard = findByPlayerCardId(cardId);
        if (playerCard !== null) {
            var count = parseInt(playerCard.attr('data-count'), 10) - 1;
            if (count <= 0) {
                var divs = elements.hand.children();
                divs.removeClass('fx-hand' + divs.length);
                playerCard.remove();
                divs.addClass('fx-hand' + (divs.length - 1));
            } else {
                playerCard.attr('data-count', count);
            }
            return count;
        }
        throw "IllegalArgument";
    };
    /**
     * @param {PlayerCard[]} cards
     * @memberOf dom.hand
     */
    hand.setCards = function (cards) {
        var i;
        cards.sort(dom.util.numberSorter("order"));
        elements.hand.html('');
        for (i = 0; i < cards.length; i += 1) {
            dom.hand.addCard(cards[i]);
        }
    };
    hand.disableCards = function () {
        elements.hand.find("[data-is-action=true],[data-is-coin=true]")
            .removeClass("glow")
            .each(function () { this.draggable = false; });
    };
    hand.enableActions = function () {
        elements.hand.find("[data-is-action=true]")
            .each(function () { this.draggable = true; })
            .addClass("glow");
    };
    hand.enableCoins = function () {
        elements.hand.find("[data-is-coin=true]")
            .each(function () { this.draggable = true; })
            .addClass("glow");
    };
}());