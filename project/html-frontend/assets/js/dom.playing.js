/**
 * Contains the cards currently playing or on the table...
 * @namespace dom.playing
 */
(function () {
    "use strict";
    var playing = namespace("playing");
    var dom = namespace;
    var elements = {};
    /**
     * @param {Object|Event} e
     * @param {Event} e.originalEvent
     * @memberOf dom.playing
     * @private
     */
    var playing_dragOver = function (e) {
        e.preventDefault();
        e.originalEvent.dpEffect = "move";
        return false;
    };
    /**
     * @param {Object|Event} e
     * @param {Event} e.originalEvent;
     * @memberOf dom.playing
     * @private
     */
    var playing_drop = function (e) {
        e.preventDefault();
        var data = e.originalEvent.dataTransfer.getData("Text");
        try {
            data = JSON.parse(data);
        } catch (ex) {
            dom.debug.log("playing.playing_drop", ex);
            return;
        }
        if (data.hand === undefined) {
            return;
        }
        dom.hand.removeCard(parseInt(data.hand.id, 10));
    };
    /**
     * @memberOf dom.playing
     */
    playing.initialize = function () {
        elements.hand = $('.playing')
            .on("dragover", playing_dragOver)
            .on("drop", playing_drop);
    };
    // todo: write the function
    playing.setCards = function () {
        return undefined;
    };
}());
