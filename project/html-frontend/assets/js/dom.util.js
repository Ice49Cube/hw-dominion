/**
 * Contains utility namespaces and methods.
 * @namespace dom.util
 */
(function () {
    "use strict";
    /*global namespace*/
    var util = namespace('util');
    /**
     * Filter function factory.
     * @param {String} name The property name on which to filter.
     * @param {Object} value The value to filter.
     * @returns {Function} A filter function.
     * @memberOf dom.util
     */
    util.equalsFilter = function (name, value) {
        return function (x) {
            return x[name] === value;
        };
    };
    /**
     * Sorter function factory.
     * @param {String} name The property name on which to filter.
     * @param {Boolean} [ascending=false] True for ascending.
     * @returns {Function}
     * @memberOf dom.util
     */
    util.numberSorter = function (name, ascending) {
        return function (a, b) {
            if (ascending) {
                return a[name] - b[name];
            }
            return b[name] - a[name];
        };
    };
    /**
     * Contains methods for full screen mode.
     * @namespace dom.util.fullScreen
     */
    Object.defineProperties(namespace('util.fullScreen'), {
        /**
         * Exit full screen mode.
         * @function exit
         * @memberOf dom.util.fullScreen
         */
        "exit": {
            get: function () {
                return function () {
                    return (document.exitFullscreen || document.webkitExitFullscreen || document.mozCancelFullScreen || document.msExitFullscreen).apply(document);
                };
            }
        },
        /**
         * The element property attribute tells you the element that's currently being displayed full screen. If this is non-null, the document is in full screen mode. If this is null, the document is not in full screen mode.
         * @property element
         * @type HTMLElement
         * @memberOf dom.util.fullScreen
         */
        "element": {
            get: function () {
                return document.fullscreenElement || document.mozFullScreenElement  || document.webkitFullscreenElement || document.msFullscreenElement;
            }
        },
        /**
         * The enabled property tells you whether or not the document is currently in a state that would allow full screen mode to be requested.
         * @property enabled
         * @type Boolean
         * @memberOf dom.util.fullScreen
         */
        "enabled": {
            get: function () {
                return document.fullscreenEnabled || document.webkitFullscreenEnabled || document.mozFullScreenEnabled || document.msFullscreenEnabled;
            }
        },
        /**
         * The browser specific event name.
         * @property changeEventName
         * @type String
         * @memberOf dom.util.fullScreen
         */
        "changeEventName": {
            get: function () {
                var i, names = ["webkitfullscreenchange", "mozfullscreenchange", "fullscreenchange", "MSFullscreenChange"];
                for (i = 0; i < names.length; i += 1) {
                    if (document["on" + names[i].toLowerCase()] !== undefined) {
                        return names[i];
                    }
                }
            }
        },
        /**
         * Request full screen mode. For an element as argument, or the body by default.
         * @function request
         * @param {HTMLElement} [element=document.body]
         * @memberOf dom.util.fullScreen
         */
        "request": {
            get: function () {
                return function (element) {
                    element = element || document.body;
                    return (element.requestFullscreen || element.webkitRequestFullScreen || element.mozRequestFullScreen || element.msRequestFullscreen).apply(element);
                };
            }
        }
    });
    /**
     * Methods to parse and build query strings.
     * @namespace dom.util.queryString
     */
    Object.defineProperties(namespace('util.queryString'), {
        /**
         * Enumerates the properties on an object and creates a query string
         * to be used in a url.
         *
         * @function build
         * @param {Object} data The object.
         * @returns {String} A url query string part.
         * @memberOf dom.util.queryString
         */
        "build": {
            get: function () {
                return function (data) {
                    var k, result = [];
                    for (k in data) {
                        if (data.hasOwnProperty(k)) {
                            result.push([ k, encodeURIComponent(data[k]) ]);
                        }
                    }
                    for (k = 0; k < result.length; k += 1) {
                        result[k] = result[k].join('=');
                    }
                    return result.join('&');
                };
            }
        },
        /**
         * Parses a query string and returns an object with the values
         *
         * @function parse
         * @param {String} query The query string.
         * @returns {Object} A object with the values as properties.
         * @memberOf dom.util.queryString
         */
        "parse": {
            get: function () {
                return function (query) {
                    if (query[0] === '?') {
                        query = query.substr(1);
                    }
                    var i, arg, offset, value, args = query.split('&'), result = {};
                    for (i = 0; i < args.length; i += 1) {
                        arg = args[i];
                        offset = arg.indexOf("=");
                        if (offset === -1) {
                            result[arg] = true;
                        } else {
                            value = arg.substr(offset + 1);
                            result[arg.substr(0, offset)] = decodeURIComponent(value);
                        }
                    }
                    return result;
                };
            }
        }
    });
}());