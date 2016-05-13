/**
 * @namespace dom.overlay
 * @description Overlay namespace. (Should've been a jQuery plugin. No time left atm.)
 */
(function () {
    "use strict";
    /*global namespace*/
    var overlay = namespace("overlay");
    var elements = {};
    var options = {
        "default": { hide: true },
        "final": {}
    };
    var document_click, document_keyup, hTimeout = null;
    /**
     * Hides the overlay and removes the key press and click handlers.
     * @param {Function} callback The callback to call when hiding is completed.
     * @memberOf dom.overlay
     * @private
     */
    function hideOverlay(callback) {
        if (hTimeout !== null) {
            clearTimeout(hTimeout);
            hTimeout = null;
        }
        $(document)
            .off("mousedown", document_click)
            .off("keyup", document_keyup);
        elements.content.css('cursor', 'default');
        elements.cell.css('cursor', 'default');
        elements.overlay.animate({opacity: 0}, function () {
            $(this).css('display', 'none');
            if (callback) {
                callback();
            }
        });
    }
    /**
     * Click event handler.
     * @param {Event} e The event data.
     * @listens onclick
     * @memberOf dom.overlay
     * @private
     */
    document_click = function (e) {
        e.stopPropagation();
        hideOverlay(options.final.hideready);
    };
    /**
     * Key up event handler listens to a press on the escape key.
     * @param {Event} e The event data.
     * @listens onclick
     * @memberOf dom.overlay
     * @private
     */
    document_keyup = function (e) {
        if ((e.which || e.keyCode) === 27) {
            hideOverlay(options.final.hideready);
        }
    };
    /**
     * Hides the overlay.
     * @param [callback] The callback to call when hiding is completed. Note, doesn't override but replaces(overrides)
     * the options.hideready once.
     * @returns {*|jQuery|HTMLElement} The overlay element.
     */
    overlay.hide = function (callback) {
        hideOverlay(callback || options.final.hideready);
        return overlay.$;
    };
    /**
     * Initialises the overlay.
     * @memberOf dom.overlay
     */
    overlay.initialize = function () {
        elements.overlay = $('.table.overlay');
        elements.cell = elements.overlay.find('.cell');
        elements.content = elements.overlay.find('.view');
    };
    /**
     * Shows the overlay.
     * @param [html=""] The html to show inside the overlay.
     * @param [settings=options.default] The options for the overlay.
     * @param [settings.hide=true] Hide on a click or press on the escape key.
     * @param [settings.showready=undefined] Callback to call when showing is completed.
     * @param [settings.hideready=undefined] Callback to call when hiding is completed.
     * @param [settings.timeout=-1] The timeout after which the overlay hides itself .
     * @returns {*|jQuery|HTMLElement} The overlay element.
     * @memberOf dom.overlay
     * @example
     * // Shows the overlay with hide on click disabled and a button as content. Hides on the button click event.
     * var button = $('<button>click me</button>').on("click", function() { alert('ok'); overlay.hide(); });
     * overlay.show(button, {hide:false});
     * @example
     * // Shows the overlay and alerts a message on hide.
     * var options = { hideready: function () {
     *     alert('overlay is now hidden');
     * }};
     * overlay.show('<center style="background-color:white;color:black;"><b>don\'t use this tag</b></center>', options);
     */
    overlay.show = function (html, settings) {
        options.final = $.extend({}, options.default, settings || {});
        elements.content.html(html || "");
        elements.overlay.css('display', 'table').animate({opacity: 1});
        if (options.final.hide) {
            elements.content.css('cursor', 'pointer');
            elements.cell.css('cursor', 'pointer');
            $(document)
                .one("mousedown", document_click)
                .one("keyup", document_keyup);
        }
        if (typeof options.final.timeout === "number") {
            hTimeout = setTimeout(function () { hideOverlay(options.final.hideready); }, options.final.timeout);
        }
        return elements.content;
    };
    /**
     * Shows a card large on the screen.
     * @param name The name of the card.
     * @param options @see show.
     * @returns {*|jQuery|HTMLElement} The overlay element.
     * @memberOf dom.overlay
     */
    overlay.showCard = function (name, options) {
        var image = 'assets/images/cards/' + name.replace('-', '_') + '.jpg';
        var html =
            '<div style="position:absolute;left:50%;top:50%;width:345px;height:552px;margin-left:-172px;margin-top:-276px;">' +
            '<img style="width:100%;height:100%;top:0;left:0;border-radius:20px;" src="' + image + '"/>' +
            '<div style="position:absolute;bottom:3px;left:3%;width:94%;height:14px;background-color:black;border-radius:10px;"></div>' +
            '</div>';
        return overlay.show(html, options);
    };
    /**
     * @param {ViewHighScoresResult} data
     * @memberOf dom.overlay
     */
    overlay.showHighScores = function (data) {
        var score, i, html = [];
        for (i = 0; i < data.scores.length; i += 1) {
            score = data.scores[i];
            html.push(
                $('<p></p>')
                    .append($('<span></span>').text(score.name + ":"))
                    .append(document.createTextNode(score.score.toString()))
            );
        }
        overlay.show($('<div style="position:relative;top:40px;left:40px;width:1200px;height:880px;overflow-y:scroll;background-color:lightgoldenrodyellow;border:2px solid gold;border-radius:30px;color: black;"></div>').append(html), {hide:false});
    };
    /**
     * Shows an overlay with an error message.
     * @param message The error message to show.
     * @param options The options, @see show.
     * @returns {*|jQuery|HTMLElement} The overlay element.
     * @memberOf dom.overlay
     */
    overlay.showError = function (message, options) {
        var table = $('<div style="position:absolute;top:0;left:0;width:1280px;height:960px;display:table;"></div>');
        var cell = $('<div style="display:table-cell;vertical-align:middle;font-size:xx-large;color:red;"></div>');
        var paragraph = $('<div style="display:block;background-color:palegoldenrod;border-radius:10px; margin:auto;padding:40px;width:600px;border: 2px solid darkgoldenrod;"></div>');
        if (options.hide === undefined || options.hide) {
            cell.css('cursor', 'pointer');
        }
        return overlay.show(table.append(cell.append(paragraph.append(message))), options);
    };
}());