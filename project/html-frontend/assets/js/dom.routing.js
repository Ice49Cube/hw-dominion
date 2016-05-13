/**
 * @namespace dom.routing
 * @description Contains methods for dispatching messages between the server and client.
 */
(function () {
    "use strict";
    /*global namespace*/
    var routing = namespace("routing");
    var dom = namespace;
    /**
     * @function isDataValid
     * @description Checks if the received data is a valid response in order to dispatch.
     * @param data The received data to check.
     * @returns {boolean} True when valid, else false.
     * @memberOf dom.routing
     * @private
     */
    var isDataValid = function (data) {
        return !!data
            && data.success !== undefined
            && data.method !== undefined;
    };
    /**
     * @function doDispatchData
     * @description Dispatches the data to the client method.
     * @param data The data coming from the client.
     * @param statusCode The HTTP response status code.
     * @param statusText The HTTP response status text.
     * @param statusCode The HTTP response code.
     * @param jqXHR jQuery XMLHttpRequest object.
     * @memberOf dom.routing
     * @private
     */
    var doDispatchData = function (data, statusCode, statusText, jqXHR) {
        if (isDataValid(data)) {
            var method = data.method + (data.success ? "Success" : "Failed");
            if (typeof routing.client[method] === 'function') {
                routing.client[method](data, statusCode, statusText, jqXHR);
                return true;
            }
            dom.debug.log('\t !!! Method "' + method + '" missing on routing.client !!!');
        } else {
            dom.debug.log('\t !!! Can\'t dispatch this data. !!!', data);
        }
        return false;
    };
    /**
     * @function onComplete
     * @description A callback function called by jQuery when the request finishes
     * (after success and error callback's are executed). More {@link http://api.jquery.com/jquery.ajax/}
     * @param jqXHR jQuery XMLHttpRequest object. More {@link http://api.jquery.com/Types/#jqXHR}
     * @param textStatus The status of the request. Can be null.
     * @memberOf dom.routing
     * @private
     */
    var onComplete = function (jqXHR, textStatus) {
        dom.debug.log('routing.onComplete(jqXHR, textStatus)');
        dom.debug.log('\tjqXHR: ', jqXHR);
        dom.debug.log('\ttextStatus: ' + (textStatus === null ? '(null)' : textStatus));
        if (typeof routing.client.onComplete === 'function') {
            routing.client.onComplete.apply(routing.client, arguments);
        }
    };
    /**
     * A function called by jQuery when the request fails. More {@link http://api.jquery.com/jquery.ajax/}
     * @param {object} jqXHR jQuery XMLHttpRequest object. More {@link http://api.jquery.com/Types/#jqXHR}
     * @param {object} jqXHR.responseJSON JSON response object.
     * @param {object} jqXHR.status The http status code, if any.
     * @param textStatus The status of the request. Can be null.
     * @param errorThrown Probably the error thrown, if any. (Described as object but marked as string on jQuery website)
     * @memberOf dom.routing
     * @private
     */
    var onError = function (jqXHR, textStatus, errorThrown) {
        dom.debug.log('routing.onError(jqXHR, textStatus, errorThrown)');
        dom.debug.log('\tjqXHR: ', jqXHR);
        dom.debug.log('\ttextStatus: ', textStatus);
        dom.debug.log('\terrorThrown: ', errorThrown);
        dom.debug.log('\tjqXHR.statusCode: ' + jqXHR.status);
        if (!doDispatchData(jqXHR.responseJSON, jqXHR.status, textStatus, jqXHR) && typeof routing.client.onError === 'function') {
            routing.client.onError.apply(routing.client, arguments);
        }
    };
    /**
     * A function called by jQuery when the request succeeds. More {@link http://api.jquery.com/jquery.ajax/}
     * @param data The data object.
     * @param textStatus The status of the request. Can be null.
     * @param jqXHR jQuery XMLHttpRequest object. More {@link http://api.jquery.com/Types/#jqXHR}
     * @memberOf dom.routing
     * @private
     */
    var onSuccess = function (data, textStatus, jqXHR) {
        dom.debug.log('routing.onSuccess(data, textStatus, jqXHR)');
        dom.debug.log('\tdata: ' + (data === null ? '(null)' : JSON.stringify(data, null, "\t")));
        dom.debug.log('\ttextStatus: ', textStatus);
        dom.debug.log('\tjqXHR: ', jqXHR);
        doDispatchData(data, jqXHR.status, textStatus, jqXHR);
    };
    /**
     * See {@link http://api.jquery.com/jquery.ajax/}
     * @property {Object} config - The configuration for the routing.
     * @memberOf dom.routing
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
     * Initializes the routing.
     * @param client The client that handles the responses.
     * @param config The configuration for the routing. See {@link http://api.jquery.com/jquery.ajax/}
     * @memberOf dom.routing
     */
    routing.initialize = function (config) {
        routing.client = config.client;
        routing.config = $.extend({}, routing.config, config.config || {});
    };
    /**
     * Invokes a method on the server with data as argument.
     * @param {String} method - The method to call.
     * @param {Object} [data={}] - The data argument.
     * @memberOf dom.routing
     */
    routing.invoke = function (method, data) {
        routing.config.data = JSON.stringify($.extend({"_method_": method}, data || {}));
        $.ajax(routing.config);
    };
}()); // End of file
