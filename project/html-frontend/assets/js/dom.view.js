/**
 * @namespace dom.view
 * @description The view module can navigate between views and keeps track of the current view.
 */
(function () {
    'use strict';
    /*global namespace*/
    var view = namespace("view");
    var dom = namespace;
    var buttonBack;
    var current;
    var viewStack = [];
    /**
     * Adds a view to the stack and updates the back button.
     * @param e Event args.
     * @memberOf dom.view
     * @private
     */
    var buttonBack_click = function (e) {
        view.goBack();
        e.preventDefault();
    };
    /**
     * Clears all the items of the view stack and hides the back button.
     * @function clearViewStack
     * @memberOf dom.view
     */
    view.clearViewStack = function () {
        viewStack = [];
        view.showBackButton(false);
    };
    /**
     * Goes back one item and removes that of the view stack.
     * @function goBack
     * @memberOf dom.view
     */
    view.goBack = function () {
        view.showBackButton(false);
        current.hide(function () {
            current = viewStack.pop();
            current.show();
            if (viewStack.length !== 0) {
                view.showBackButton(true);
            }
        });
    };
    /**
     * Get the view stack.
     * @function getViewStack
     * @returns {IView[]} The view stack.
     * @memberOf dom.view
     */
    view.getViewStack = function () {
        return viewStack;
    };
    /**
     * Get the current visible View.
     * @function getCurrent
     * @returns {IView} The current View.
     * @memberOf dom.view
     */
    view.getCurrent = function () {
        return current;
    };
    /**
     *
     * @function getViewName
     * @param {IView} view The view of which to get the name. The view must reside in the views namespace!
     * @returns {null|string} The name of the view when found, else null.
     * @memberOf dom.view
     */
    view.getViewName = function (view) {
        var prop;
        for (prop in dom.views) {
            if (dom.views.hasOwnProperty(prop) && dom.views[prop] === view) {
                return prop;
            }
        }
        return prop;
    };
    /**
     * Initializes the view.
     * @function initialize
     * @memberOf dom.view
     */
    view.initialize = function () {
        // * Disable f5
        if (!dom.debug.enable()) {
            $(document).on("keydown", function (e) {
                if ((e.which || e.keyCode) === 116) {
                    e.preventDefault();
                }
            });
        }
        // * Disable dragging
        // * Disable right click
        $(document).bind("dragstart", function () {
            return false;
        }).on("contextmenu", function () {
            return false;
        });
            // * Allow context menu on input
        $('input[type=text]').on("contextmenu", function (e) {
            e.stopPropagation();
            return true;
        });
        buttonBack = $('a.button-back').on("click", buttonBack_click);
        $(window).resize(function () {
            // todo: http://stackoverflow.com/questions/1713771/how-to-detect-page-zoom-level-in-all-modern-browsers
            /*var wnd = $(window);
            var ow = 1280, oh = 960;
            var w = wnd.width();
            var h = wnd.height();
            var zoom = (w / 4) * 3 > h ? h / oh : w / ow;
            $('.view').css({
                "zoom": Math.floor(zoom * 100) + "%"
            });*/
        });
    };
    /**
     * Shows or hides the back button.
     * @function showBackButton
     * @param {Boolean} visible - True to show, false to hide, the back button.
     * @memberOf dom.view
     */
    view.showBackButton = function (visible) {
        if (visible) {
            buttonBack.show();
        } else {
            buttonBack.hide();
        }
    };
    /**
     * Shows the first view.
     * @param {IView} first The view to show.
     * @memberOf dom.view
     */
    view.showFirstView = function (first) {
        // php storm doesn't want to accept @param {IView}
        current = first;
        current.show();
        view.clearViewStack();
    };
    /**
     * Hides the current view and when completed it shows the next.
     * @function showNext
     * @param {IView} next - The view to show next.
     * @param {Object} [options=undefined]
     * @param {Boolean} [options.button=true] Show the back button.
     * @param {Function} [options.callback=undefined] A callback method.
     * @memberOf dom.view
     */
    view.showNext = function (next, options) {
        if (!next || next.show === undefined || next.hide === undefined) {
            throw "IllegalViewException";
        }
        var final = $.extend({ button: true }, options || {});
        view.showBackButton(false);
        current.hide(function () {
            next.show(function () {
                view.showBackButton(final.button);
                if (final.callback) {
                    final.callback();
                }
            });
        });
        viewStack.push(current);
        current = next;
    };
}()); // End of file
