function AjaxCall(url, parameters, functionToEx, functionOnError) {
	this.url = url;
	this.parameters = parameters;
	this.functionToEx = functionToEx;
	this.functionOnError = functionOnError;
	
	if (typeof functionOnError == "undefined" || functionOnError == "") {
		functionOnError = function () {};
	}
	
	this.__AJAX_ERROR = false;
	this.__AJAX_ERROR_MSG = "";
	
	this.execute = function() {
		var ajax = $.ajax({
			url: this.url,
			type: "POST",
			data: parameters,
			async: true,
			success: function(data) {
				try {
					if (typeof data == "undefined" || data == "") {
						functionOnError(data);
						modalError("Fatal-Error", "Ha ocurrido algo inesperado, vuelva a intentarlo mas tarde.");
					} else if (data.ERROR_CODE != 0) {
						functionOnError(data);
						modalError("Error", data.ERROR_MSG);
					} else {
						functionToEx(data);
					}
				} catch(e) {
					functionOnError(data);
					modalError("Fatal-Error", e);
				}
			},
			error: function(data) {
				functionOnError(data);
				__AJAX_ERROR = true;
				__AJAX_ERROR_MSG = data;
			}
		});
		
		return ajax;
	}
}