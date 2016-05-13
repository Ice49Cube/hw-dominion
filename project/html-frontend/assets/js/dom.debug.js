/**
 * @namespace dom.debug
 * @description Configurable debug script for the project.
 */
(function () {
    "use strict";
    /*global namespace*/
    var debug = namespace('debug');
    /* Patch the console for some browsers */
    window.console = console || {};
    console.log = console.log || function () { return undefined; };
    /**
     * A function that's installed when debugging is turned off.
     * @function dummy
     * @returns {undefined}
     * @memberOf dom.debug
     * @private
     */
    var dummy = function () {
        return undefined;
    };
    /**
     * A function that's installed when debugging is turned on.
     * @function hook
     * @memberOf dom.debug
     */
    var hook = function () {
        //
        // Add console.log hook code here...
        //
        return console.log.apply(console, arguments);
    };
    /**
     *
     * @param {boolean} assertion The assertions to check.
     * @ param {object} message The first argument to write.
     */
    debug.assert = function (assertion) {
        if (!assertion) {
            var args = Array.prototype.slice.call(arguments, 1);
            args.unshift("color: #F00; font-weight: bold;");
            args.unshift("%c Assertion failed!");
            debug.log.apply(console, args);
        }
    };
    /**
     * Behaves like console.log but can be disabled.
     * @function
     * @name log
     * @param {...Object} params The arguments to write to the log.
     * @memberOf dom.debug
     */
    debug.log = dummy;
    /**
     * Enables/disables the debug log function.
     * @function enable
     * @param {Boolean=} enabled To enable debug log true, else false. Doesn't change when no argument was given.
     * @returns {Boolean} True when logging is enabled, else false.
     * @memberOf dom.debug
     */
    debug.enable = function (enabled) {
        if (enabled !== undefined) {
            debug.log = enabled ? hook : dummy;
        }
        return debug.log === hook;
    };
    debug.initialize = function (enabled) {
        debug.enable(enabled);
        var css = "color: rgb(97, 97, 97); font-size: 50px; background-color: rgb(186, 186, 186); text-shadow: rgb(224, 224, 224) 1px 1px 0px;width:100%;text-align:center;display:block;";
        console.log("%cWelcome %s", css, 'to dominion');
    };
}());