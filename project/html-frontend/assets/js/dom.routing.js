/**
 * @module routing
 * @description The routing module is used for dispatching messages between the server and game.
 */
(function () {
    "use strict";
    // Imports
    var routing = window.routing = window.routing || {};
    /** 
     * @function doDispatchData
     * @description Dispatches the data to the client method.
     * @param data The data coming from the client.
     * @param statusCode The HTTP response status code.
     * @param statusText The HTTP response status text.
     * @param statusCode The HTTP response code.
     * @private
     * @static
     */
    var doDispatchData = function(data, statusCode, statusText, jqXHR) {
    	if (isDataValid(data)) {
    		var data = jqXHR.responseJSON;
    		var method = data.method + (data.success ? "Success" : "Failed");
    		if (typeof routing.client[method] === 'function') {
    			routing.client[method](data, statusCode, statusText, jqXHR);
    			return true;
    		} else {
    			routing.log('\t !!! Method "' + method + '" missing on routing.client !!!');
    		}
    	} else {
    		routing.log('\t !!! Can\'t dispatch this data. !!!', data);
    	}
    	return false;
    };
    /**
     * @function enableLogging
     * @description Enabled logging for the routing module.
     * @param {Boolean} enabled - True to enable, false to disable, logging.
     * @static
     * @private
     */
    var enableLogging = function (enabled) {
        if (enabled) {
            routing.log = function () {
                console.log.apply(console, arguments);
            };
        } else {
            routing.log = function () { return; };
        }
    };
    /**
     * @function isDataValid
     * @description Checks if the received data is a valid response in order to dispatch.
     * @param data The received data to check.
     * @returns {boolean} True when valid, else false.
     * @private
     */
    var isDataValid = function (data) {
        return !!data
            && data.success !== undefined
            && data.method !== undefined;
    };
    /**
     * @function onComplete
     * @description A callback function called by jQuery when the request finishes
     * (after success and error callback's are executed). More {@link http://api.jquery.com/jquery.ajax/}
     * @param jqXHR jQuery XMLHttpRequest object. More {@link http://api.jquery.com/Types/#jqXHR}
     * @param textStatus The status of the request. Can be null.
     * @private
     */
    var onComplete = function (jqXHR, textStatus) {
        routing.log('routing.onComplete(jqXHR, textStatus)');
        routing.log('\tjqXHR: ', jqXHR);
        routing.log('\ttextStatus: ' + (textStatus === null ? '(null)' : textStatus));
        if (typeof routing.client.onComplete === 'function') {
            routing.client.onComplete.apply(routing.client, arguments);
        }
    };
    /**
     * @function onError
     * @description A function called by jQuery when the request fails. More {@link http://api.jquery.com/jquery.ajax/}
     * @param jqXHR jQuery XMLHttpRequest object. More {@link http://api.jquery.com/Types/#jqXHR}
     * @param textStatus The status of the request. Can be null.
     * @param errorThrown Probably the error thrown, if any. (Described as object but marked as string on jQuery website)
     * @private
     */
    var onError = function (jqXHR, textStatus, errorThrown) {
        routing.log('routing.onError(jqXHR, textStatus, errorThrown)');
        routing.log('\tjqXHR: ', jqXHR);
        routing.log('\ttextStatus: ' + (textStatus === null ? '(null)' : textStatus));
        routing.log('\terrorThrown: ' + (errorThrown === null ? '(null)' : errorThrown.toString()));
        routing.log('\tjqXHR.statusCode: ' + jqXHR.statusCode());
        var dispatched = doDispatchData(jqXHR.responseJSON, jqXHR.status, textStatus, jqXHR);
        if (!dispatched && typeof routing.client.onError === 'function') {
            routing.client.onError.apply(routing.client, arguments);
        }
    };
    /**
     * @function onSuccess
     * @description A function called by jQuery when the request succeeds. More {@link http://api.jquery.com/jquery.ajax/}
     * @param textStatus The status of the request. Can be null.
     * @param jqXHR jQuery XMLHttpRequest object. More {@link http://api.jquery.com/Types/#jqXHR}
     * @private
     */
    var onSuccess = function (data, textStatus, jqXHR) {
        routing.log('routing.onSuccess(data, textStatus, jqXHR)');
        routing.log('\tdata: ' + (data === null ? '(null)' : JSON.stringify(data)));
        routing.log('\ttextStatus: ' + (textStatus === null ? '(null)' : textStatus));
        routing.log('\tjqXHR: ', jqXHR);
        doDispatchData(data, jqXHR.status, textStatus, jqXHR);
    };
    /**
     * @property {Object} config - The configuration for the routing.
     * @description See {@link http://api.jquery.com/jquery.ajax/}
     * @private
     */
    routing.config = {
        accept: {json: 'application/json'},
        complete: onComplete,
        contentType: 'application/json',
        dataType: 'json',
        error: onError,
        method: 'POST',
        mimeType: 'application/json',
        processData: false,
        success: onSuccess,
        timeout: 3000,
        xhrFields: {
            withCredentials: false
        }
    };
    /**
     * @function initialize
     * @description Initializes the routing.
     * @param client The client that handles the responses.
     * @param config The configuration for the routing. See {@link http://api.jquery.com/jquery.ajax/}
     */
    routing.initialize = function (client, config, logging) {
        routing.client = client;
        routing.config = $.extend(routing.config, config || {});
        enableLogging(logging ||  false);
    };
    /**
     * @function invoke
     * @description Invokes a method on the server with data as argument.
     * @param {String} method - The method to call.
     * @param {Object} [data={}] - The data argument.
     */
    routing.invoke = function (method, data) {
        data = data || {};
        routing.config.data = JSON.stringify($.extend({_method_: method}, data));
        $.ajax(routing.config);
    };
    /**
     * @property The name of this module.
     * @type {string}
     */
    routing.name = "routing";
}()); // End of file
