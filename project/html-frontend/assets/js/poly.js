
(function () {
    "use strict";
    var console = window.console || {};
    console.log = console.log || function () {
        return;
    };
}());


if (typeof console  != "undefined")
    if (typeof console.log != 'undefined')
        console.olog = console.log;
    else
        console.olog = function() {};

console.log = function(message) {
    console.olog(message);
    $('#DebugDiv').append('<p>' + message + '</p>');
};
console.error = console.debug = console.info =  console.log;