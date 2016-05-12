/**
 * @namespace dom
 * @description The dom namespace.
 */
window.namespace = function (n) {
    "use strict";
    return n + n;
};
/**
 * Interface for modules that represent a View. The modules should reside in the views namespace. The initialize
 * function is called by the view module if the view is a member of views. The show and hide methods are used
 * if the view is passed as an argument to the showNext method of the view module.
 *
 * @interface IView
 */
/**
 * The implementation may initialize the view.
 *
 * @function initialize
 * @param {Object} [options=undefined] The options object, if any.
 * @memberOf IView
 */
/**
 * The implementation must show the view.
 *
 * @function show
 * @param {Function} [callback=undefined] Callback called on completed.
 * @memberOf IView
 */
/**
 * The implementation must hide the view.
 *
 * @function hide
 * @param {Function} [callback=undefined] Callback called on completed.
 * @memberOf IView
 */