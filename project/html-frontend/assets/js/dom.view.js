/**
 * @module view
 * @description The view module can navigate between views and keeps track of the current view.
 */
(function () {
    'use strict';
    // Imports
    var view = window.view = window.view || {};
    var views = window.views = window.views || {};
    /**
     * @var buttonBack
     * @description Holds all the jQuery elements in this module.
     * @private
     */
    var buttonBack;
    /**
     * @var current
     * @description Holds the current view.
     * @private
     */
    var current;
    /**
     * @var stack
     * @description Holds the stack of views to go back.
     * @private
     */
    var viewStack = [];
    /**
     * @function buttonBack_click
     * @description Adds a view to the stack and updates the back button.
     * @param {Object} v The view to add to the stack.
     * @private
     */
    var buttonBack_click = function (e) {
        view.goBack();
        e.preventDefault();
    };
    /**
     * @function initializeViews
     * @description Calls initialize() on all the view modules in the views enumeration.
     * @static
     * @private
     */
    var initializeViews = function () {
        var name;
        for (name in views) {
            if (views.hasOwnProperty(name) && views[name].initialize !== undefined) {
                views[name].initialize();
            }
        }
    };
    /**
     * @function showView
     * @description Shows the first view.
     * @param {String} view - The view to show.
     * @static
     * @private
     */
    var showFirstView = function (first) {
        current = first;
        current.show();
        view.clearBackStack();
    };
    /**
     * @function clearBackStack
     * @description Clears all the items of the view stack and hides the back button.
     * @static
     */
    view.clearBackStack = function () {
        viewStack = [];
        view.showBackButton(false);
    };
    /**
     * @function goBack
     * @description Goes back one item and removes that of the view stack.
     * @static
     */
    view.goBack = function () {
        buttonBack.hide();
        current.hide(function () {
            current = viewStack.pop();
            current.show();
            if (viewStack.length !== 0) {
                buttonBack.show();
            }
        });
    };
    /**
     * @function getCurrent
     * @description Get the current visible View.
     * @returns The current View.
     * @static
     */
    view.getCurrent = function () {
        return current;
    };
    /**
     * @function initialize
     * @description Initializes the view.
     * @param {Object} config - The configuration for the View.
     * @param {String} config.first - The first View to show.
     * @static
     */
    view.initialize = function (config) {
        //initializeView();
        buttonBack = $('a.back').on("click", buttonBack_click);
        initializeViews();
        showFirstView(config.first);
    };
    /**
     * @function showBackButton
     * @description Shows or hides the back button.
     * @param visible - True to show, false to hide, the back button.
     * @static
     */
    view.showBackButton = function (visible) {
        if (visible) {
            buttonBack.show();
        } else {
            buttonBack.hide();
        }
    };
    /**
     * @function showNext
     * @description Hides the current view and when completed it shows the next.
     * @param {String} view - The view to show next.
     * @param {Boolean} [backVisible=true] - Make the back button visible, default true.
     * @static
     */
    view.showNext = function (next) {
        var showBack = arguments[1] || true;
        view.showBackButton(false);
        current.hide(function () {
            next.show(function () {
                view.showBackButton(showBack);
            });
        });
        viewStack.push(current);
        current = next;
    };
    /**
     * @property name
     * @description The name of this module.
     * @type {string}
     */
    view.name = "view";
}()); // End of file
