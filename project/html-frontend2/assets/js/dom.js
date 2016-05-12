/**
 * The dom namespace.
 * @namespace dom
 */
////////////////////////////////////////////////////////////////////////////////
/**
 * A namespace factory and dictionary function.
 * @function namespace
 * @param {string} name Namespace name in dotted notation.
 * @returns {namespace} The namespace.
 */
function namespace(name) {
    "use strict";
    // Should be possible to make each namespace a jQuery object "  = value[fragments[index]] || $('');"
    var value = namespace, fragments = (name || "").split("."), index;
    for (index = 0; index < fragments.length; index += 1) {
        value = value[fragments[index]] = value[fragments[index]] || {};
    }
    return value;
}
////////////////////////////////////////////////////////////////////////////////
// Make dom global. (Access the namespace by the "dom" reference.)
window.dom = namespace;
////////////////////////////////////////////////////////////////////////////////
/**
 * The actual startup procedure.
 * @function startup
 */
$(function () {
    "use strict";
    var dom = namespace;
    //url: location.protocol + "//dom.node2.be/Server"
    //url: location.protocol + "//" + location.host + "/Server"
    var config = {
        routing: {
            client: dom.game,
            config: {timeout: 60000, url: location.protocol + "//localhost:8080/Server"}
        },
        views: {},
        debug: true
    };
    // Hierarchical recursive namespace initialization. (what's in a namespace?)
    var initialize = function (d, c) {
        var prop;
        for (prop in d) {
            if (d.hasOwnProperty(prop)) {
                if (prop === "views") {
                    if (c[prop] !== undefined) {
                        initialize(d[prop], c[prop]);
                    }
                } else if (typeof d[prop].initialize === "function") {
                    d[prop].initialize(c[prop]);
                }
            }
        }
    };
    // Everything is loaded, now ready to initialize all namespaces
    // that have an initialize method.
    initialize(dom, config);
    // Everything is initialized, now ready to show the first screen.
    dom.view.showFirstView(dom.views.homeView);
    var query = dom.util.queryString.parse(location.search);
    if (query.id) {
        dom.game.continueGame(query.id);
    }
});
////////////////////////////////////////////////////////////////////////////////
/**
 * Interface for modules that represent a View. The modules should reside in the
 * views namespace. The initialize function is called by the view module if the
 * view is a member of views. The show and hide methods are used if the view is
 * passed as an argument to the showNext method of the view module.
 *
 * @interface IView
 */
/**
 * The implementation may initialize the view.
 *
 * @function
 * @name initialize
 * @memberOf IView
 * @param {Object} [options=undefined] The options object, if any.
 */
/**
 * The implementation must show the view.
 *
 * @function
 * @name show
 * @param {Function} [callback=undefined] Callback called on completed.
 * @memberOf IView
 */
/**
 * The implementation must hide the view.
 *
 * @function
 * @name hide
 * @memberOf IView
 * @param {Function} [callback=undefined] Callback called on completed.
 */
////////////////////////////////////////////////////////////////////////////////
/**
 * Describes a card in the game.
 *
 * @typedef {Object} GameCard
 * @property {Number} id - The id of the card.
 * @property {String} name - The name of the card.
 * @property {Number} cost - The cost of the card.
 * @property {Number} value - The value of the card.
 * @property {Number} count - The number of cards in the game.
 * @property {Boolean} isAction - The card is an action card.
 * @property {Boolean} isCoin - The card is a coin.
 */
////////////////////////////////////////////////////////////////////////////////
/**
 * Describes a high score. Member of see {@link ViewHighScoresResult}.
 *
 * @typedef {Object} HighScore
 * @property {String} name - The name of the player.
 * @property {Number} score - The score of the player.
 */
////////////////////////////////////////////////////////////////////////////////
/**
 * The result of a high scores request.
 *
 * @typedef {Object} StartGameResult
 * @property {string} id - The id of the game.
 * @property {GameCard[]} cards - The cards in the game.
 * @ property {Player[]} players - The players in the game.
 */
////////////////////////////////////////////////////////////////////////////////
/**
 * The result of a high scores request.
 *
 * @typedef {Object} ViewHighScoresResult
 * @property {HighScore[]} scores - The high scores.
 */
////////////////////////////////////////////////////////////////////////////////