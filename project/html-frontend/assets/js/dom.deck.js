/**
 * @namespace dom.deck
 * @description Contains methods to update and get info from the deck and triggers events on changes.
 */
(function () {
    "use strict";
    /*global namespace*/
    var deck = namespace("deck");
    var dom = namespace;
    var elements = { };
    var divDeckFigure_click, divDeckDivBuy_click;
    /**
     * Adds a card to the deck.
     * @param {*|jQuery|HTMLElement} deck The specific deck to add the card.
     * @param {GameCard} card The card to add to the deck.
     * @memberOf dom.deck
     * @private
     * @example
     * // elements can be queried by the find() method
     * var element = elements.data('figure[data-id=' + id);
     * @example
     * // id, name, cost, action and coin can be accessed by the data() method
     * var name = element.data('name');
     * @example
     * // count can be accessed by the find() and text() method
     * var id = parseInt(element.find('.count').text());
     */
    var addCardToDeck = function (deck, card) {
        var name = card.name.split('_').join(' ');
        var csName = card.name.split('_').join('-');
        var figure = $('<figure></figure>')
            .attr(
                {
                    'class': csName,
                    'data-id': card.id,
                    'data-name': card.name,
                    'data-cost': card.cost,
                    'data-action': card.isAction,
                    'data-coin': card.isCoin,
                    'data-value': card.value,
                    'title': name
                }
            );
        figure.append($('<img/>').addClass('art').attr('src', "assets/images/cards/art/" + card.name + "_art.jpg").attr('alt', name).attr('title', name))
            .append($('<div></div>').addClass('count').text(card.count))
            .append($('<div></div>').addClass('buy').addClass('hide').attr('title', 'buy'))
            .append($('<figcaption></figcaption>').text(name))
            .append($('<img/>').addClass('coin').attr('src', "assets/images/cards/coins/coin" + card.cost + ".png").attr('title', 'costs ' + card.cost));
        figure.appendTo(deck);
    };
    /**
     * @function addCardsToDeck
     * @param {*|jQuery|HTMLElement} deck The specific deck to add the card.
     * @param {Array.<GameCard>} cards The cards to add to the deck.
     * @param {Number} start The start position in the cards array.
     * @param {Number} end The end position in the cards array.
     * @memberOf dom.deck
     * @private
     */
    var addCardsToDeck = function (deck, cards, start, end) {
        var i;
        if (start < end) {
            for (i = start; i < end; i += 1) {
                addCardToDeck(deck, cards[i]);
            }
        } else {
            for (i = start; i >= end; i -= 1) {
                addCardToDeck(deck, cards[i]);
            }
        }
    };
    var attachEvents = function () {
        elements.buys.on("click", divDeckDivBuy_click);
        elements.figures.on("click", divDeckFigure_click);
    };
    var detachEvents = function () {
        elements.buys.off("click", divDeckDivBuy_click);
        elements.figures.off("click", divDeckFigure_click);
    };
    divDeckFigure_click = function (e) {
        e.stopPropagation();
        dom.overlay.showCard($(this).data('name'));
    };
    divDeckDivBuy_click = function (e) {
        e.stopPropagation();
        elements.game.trigger($.Event("buycard", {id: $(this).parent().data('id') }));
        dom.deck.updateCards(-1);
    };
    var equalsFilter = function (name, value) {
        return function (x) {
            return x[name] === value;
        };
    };
    /**
     * @param {*|jQuery|HTMLElement} figure
     * @returns {GameCard}
     */
    var figureToGameCard = function (figure) {
        return {
            "id": figure.data('id'),
            "name": figure.data('name'),
            "count": parseInt(figure.find('div.count').text(), 10),
            "cost": parseInt(figure.data('cost'), 10),
            "isAction": figure.data('isAction'),
            "isCoin": figure.data('isCoin')
        };

    };
    var loadElements = function () {
        elements.figures = elements.decks.find('figure');
        elements.buys = elements.decks.find('div.buy');
    };
    var numberSorter = function (name, ascending) {
        return function (a, b) {
            if (ascending) {
                return a[name] - b[name];
            }
            return b[name] - a[name];
        };
    };
    /**
     *
     * @param {number|string} id
     * @returns {*}
     * @memberOf dom.deck
     */
    deck.buyCard = function (id) {
        var card = $('.deck figure[data-id="' + id + '"]');
        var child = card.find('div.count');
        var count = parseInt(child.text(), 10);
        var taken;
        if (count > 0) {
            count = count - 1;
            child.text(count);
            taken = true;
        } else {
            child.text('0');
            taken = false;
        }
        if (count <= 0 && card.find('.overlay').length === 0) {
            var overlay = $('<div class="overlay"></div>');
            card.append(overlay);
            //overlay.on("click", function (e) {e.stopPropagation();});
        }
        return taken;
    };
    /**
     * @param {string|number} id
     * @returns {{id, name, count, cost, isAction, isCoin}|GameCard}
     * @memberOf dom.deck
     */
    deck.findCardById = function (id) {
        return figureToGameCard(elements.decks.find('figure[data-id="' + id + '"]'));
    };
    /**
     * @param {string} name
     * @returns {{id, name, count, cost, isAction, isCoin}|GameCard}
     * @memberOf dom.deck
     */
    deck.findCardByName = function (name) {
        return figureToGameCard(elements.decks.find('figure[data-name="' + name + '"]'));
    };
    /**
     * @memberOf dom.deck
     */
    deck.initialize = function () {
        elements.decks = $('div.deck');
        elements.game = $('.game');
        elements.kingdom = elements.decks.filter('.deck.kingdoms');
        elements.treasure = elements.decks.filter('.deck.treasures');
        elements.victory = elements.decks.filter('.deck.victories');
        loadElements();
    };
    /**
     * Builds the deck.
     * @param {GameCard[]} cards See the GameCard class.
     * @memberOf dom.deck
     */
    deck.setCards = function (cards) {
        var victories = cards.filter(equalsFilter("deck", "Victory")).sort(numberSorter('cost', true));
        var kingdoms = cards.filter(equalsFilter("deck", "Kingdom")).sort(numberSorter('cost', true));
        var treasures = cards.filter(equalsFilter("deck", "Treasure")).sort(numberSorter('cost', true));
        treasures.reverse();
        elements.decks.html('');
        addCardsToDeck(elements.victory, victories, 2, 4);
        addCardsToDeck(elements.victory, victories, 1, 0);
        addCardsToDeck(elements.kingdom, kingdoms, 5, 10);
        addCardsToDeck(elements.kingdom, kingdoms, 0, 5);
        addCardsToDeck(elements.treasure, treasures, 0, 3);
        detachEvents();
        loadElements();
        attachEvents();
    };
    /**
     *
     * @param {number} coins - From zero to the available coins. -1 to hide the buy buttons.
     * @memberOf dom.deck
     */
    deck.updateCards = function (coins) {
        var cards = $('div.deck figure');
        cards.find('.buy').hide();
        cards = cards.filter(function () {
            return parseInt($(this).data('cost'), 10) <= coins && $(this).find('.overlay').length === 0;
        });
        cards.find('.buy').show();
    };
}());