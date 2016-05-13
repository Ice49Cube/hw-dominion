/**
 * @namespace dom.hand
 */
(function () {
    "use strict";

    // addCard, removeCard
    // findCard?
    // enableDrag/disableDrag

    /*global namespace*/
    var hand = namespace("hand");
    var dom = namespace;
    var elements = {};
    var mouseData = {origin:{x:0,y:0}};
    var draggingEnabled;

    /**
     * @param {Event} e
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
     */
    var document_mousemove = function (e) {
        if (mouseData.element) {
            mouseData.element.css({
                "left": e.clientX - mouseData.origin.x + 'px',
                "top": e.clientY - mouseData.origin.y + 'px'
            });
        }
    };
    var document_mouseup = function () {
        if (mouseData.element) {
            mouseData.element = null;
            mouseData.element.removeClass('dragging').attr('style','');
        }
    };
    var findCard = function (id) {
        return hand.elements.container.find('[data-id=' + id + ']');
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
        cards.sort(function (a, b) { return a.order - b.order; });
        for(i = 0; i < cards.length; i += 1) {
            dom.hand.addCard(cards[i]);
        }
        //images = elements.container.find("div");
    };
    /**
     * @param {PlayerCard} card
     */
    hand.addCard = function (card) {

        var cards = elements.container.find("div");
        elements.container.removeClass("fx-card" + cards.length);


        /*
        elements.container.removeClass("fx-card" + elements.container.find("div").length);
        images.addClass("fx-card" + images.length);

        dom.debug.log("Add card to hand: ", card);
        var gameCard = dom.deck.findCardById(card.cardId);
        var cards = elements.container.find("div");
        cards.each(function () {
            dom.debug.log($(this));
        });
        elements.container.append($('<div></div>')
                .attr(
                {
                    'class': gameCard.name.split('-').join('_'),
                    'data-id': card.id,
                    'data-cardId': card.cardId,
                    'data-order': card.order
                }
            ).on("mousedown", card_mousedown)
        );*/
    };
    /*
     * @param {number} id
     *
    hand.removeCard = function (id) {

        findCard(id).remove();
    };
    hand.findCardById = function (id) {

    };
    /*
     * @param {string} cardId
     * @returns {GameCard}
     *
    hand.findCardByGameCardId = function (cardId) {
        return hand.findCardById();
    };
*/
}());
