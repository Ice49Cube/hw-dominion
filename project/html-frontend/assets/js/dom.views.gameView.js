
(function () {
    "use strict";

    // Imports

    var views = window.views = window.views || {};
    var gameView = views.gameView = views.gameView || {};

    // Private

    var divGameView;
    $('#divGame').show();
    var coins = 20;
    var buys = 5;
    var overlay = $('div.view>div.overlay');
    updateCardsForBuying(coins);
    $('div.deck figure').on("click", divDeckFigure_click);
    $('div.deck div.buy').on("click", divDeckDivBuy_click);

    // Hacks
    //
    // * Disable f5
    //   http://stackoverflow.com/questions/2482059/disable-f5-and-browser-refresh-using-javascript
    $(document).on("keydown", function (e) { if ((e.which || e.keyCode) == 116) e.preventDefault(); });
    //
    // * Disable dragging for everything
    $(document).bind("dragstart", function(e) { return false; });


    // Make the buy button for all cards costing less than 5 visible
    function updateCardsForBuying(coins) {
        var cards = $('div.deck figure');
        cards.find('.buy').hide();
        cards = cards.filter(function () {
            return parseInt($(this).data('cost')) <= coins && $(this).find('.overlay').length == 0;
        });
        cards.find('.buy').show();
    }


    function findCardOnDeckByName(name) {
        var card = $('.deck figure[data-name="' + name + '"]');
        var count = card.find('div.count');
        var cost = card.data('cost');
        return {
            "$": card,
            "$count": count,
            "count": parseInt(count.text()),
            "cost": cost
        };
    }

    // Remove a card from the one of the stacks,
    // adds overlay when empty,
    // returns true when a card was taken, else false...
    function removeCardFromDeck(name) {
        var card = $('.deck figure[data-name="' + name + '"]');
        var child = card.find('div.count');
        var count = parseInt(child.text());
        var taken;
        if (count > 0) {
            count = count - 1;
            child.text(count);
            taken = true;
        } else {
            child.text('0');
            taken = false;
        }
        if (count <= 0 && card.find('.overlay').length == 0) {
            var overlay = $('<div class="overlay"></div>');
            card.append(overlay);
            //overlay.on("click", function (e) {e.stopPropagation();});
        }
        return taken;
    }

    function divDeckFigure_click(e) {
        var image = 'assets/images/cards/' + $(this).data('name').replace('-', '_') + '.jpg';
        var html =
            '<div style="position:absolute;left:50%;top:50%;width:345px;height:552px;margin-left:-172px;margin-top:-276px;">' +
            '<img style="width:100%;height:100%;top:0;left:0;border-radius:20px;" src="' + image + '"/>' +
            '<div style="position:absolute;bottom:3px;left:3%;width:94%;height:14px;background-color:black;border-radius:10px;"></div>' +
            '</div>';
        showOverlay(html, true);
        e.stopPropagation();
    }

    function divDeckDivBuy_click(e) {
        e.stopPropagation();
        var element = $(this).parent();
        var name =  element.data('name');
        var cost = parseInt(element.data('cost'));
        if (removeCardFromDeck(name)) {
            coins -= cost;
            buys -= 1;
            if(buys > 0) {
                updateCardsForBuying(coins);
            } else {
                updateCardsForBuying(-1);
            }
            alert(["Bought: " + name, "Cost: " + cost, "Buys: " + buys, "Coins: " + coins].join("\n"));
        }
    }

    function divOverlay_click(e) {
        e.stopPropagation();
        $(document.body).css('cursor', 'default');
        hideOverlay();
    }

    function divOverlay_keyup(e) {
        if ((e.which || e.keyCode) === 27)
            hideOverlay();
    }

    function hideOverlay() {
        overlay.fadeOut();
        $(document)
            .off("click", divOverlay_click)
            .off("keyup", divOverlay_keyup)
        ;
    }

    function showOverlay(html, hideOnDocumentClick) {
        overlay.html(html).fadeIn();
        if (hideOnDocumentClick) {
            $(document.body).css('cursor', 'pointer');
            $(document)
                .on("click", divOverlay_click)
                .on("keyup", divOverlay_keyup)
            ;
        }
    }

    // Public

    gameView.initialize = function () {
        divGameView = $('#divGame');

    };

    gameView.show = function (oncompleted) {
        divGameView.slideDown(oncompleted);
    };

    gameView.hide = function (oncompleted) {
        divGameView.slideUp(oncompleted);

        $("#aBack").slideDown(600);
    };

    gameView.name = "gameView";
}());