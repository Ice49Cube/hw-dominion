
// Minimum View Implementation...

(function () {
    "use strict";

    // Imports

    var views = window.views = window.views || {};
    var templateView = views.templateView = views.templateView || {};

    // Private

    var divTemplate;

    // Public

    templateView .initialize = function () {
        divTemplate = $('#divTemplate');
    };

    templateView .show = function(oncompleted) {
        divTemplate.slideDown(oncompleted);
    };

    templateView .hide = function(oncompleted) {
        divTemplate.slideUp(oncompleted);
    };

    templateView.name = "templateView";
}());