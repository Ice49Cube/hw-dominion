/**
 * @namespace dom.views
 * @description dom.views namespace
 */
(function () {
    "use strict";
    var views = namespace('views');
    //var dom = namespace;
    /**
     * Wow, such comments...
     * @function suchComments
     * @param {Event} x the event object
     * @returns {number} some weird number
     * @memberOf dom.views
     */
    views.showView = function (x) {
        return 12 + x.pageX;
    };
}());