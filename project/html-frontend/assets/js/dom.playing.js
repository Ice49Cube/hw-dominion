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
        if (e.originalEvent.dataTransfer.types.indexOf("playing-id") === -1) {
            e.originalEvent.dataTransfer.dropEffect = "none";
            return true;
        }
        e.preventDefault();
        e.originalEvent.dataTransfer.dropEffect = "move";
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
        var data = e.originalEvent.dataTransfer.getData("playing-id");
        alert(data);
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
