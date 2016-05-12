/*global namespace */
/**
 * @namespace dom.views.two
 * @description Foo fighting
 */
(function () {
    "use strict";
    var two = namespace('views.two');
    var dom = namespace;
    /**
     * Get the foo.
     * @returns {*} The foo.
     * @memberOf dom.views.two
     * @private
     */
    function fooGetter() {
        return dom.game.start(null);
    }
    /**
     * Sets the foo.
     * @param {Number} v The value for foo.
     * @returns {Number} The foo value again.
     * @memberOf dom.views.two
     * @private
     */
    function fooSetter(v) {
        return v + 10;
    }
    /**
     * Gets the foo.
     * @function getFoo
     * @returns {{Object}} The foo.
     * @memberOf dom.views.two
     */
    two.getFoo = function () {
        return fooGetter();
    };
    /**
     * Sets the foo.
     * @function setFoo
     * @param v The value for foo.
     * @returns {*} The foo value again.
     * @memberOf dom.views.two
     */
    two.setFoo = function (v) {
        return fooSetter(v);
    };
}());
