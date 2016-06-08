function AjaxCall(url, parameters) {
	this.url                    = url;
	this.parameters             = parameters;
	this.async                  = true;
	this.showServletError       = true;
	this.callType               = "POST";
	this.functionToEx       	= function () {};
	this.functionOnAjaxError    = function () {};
	this.functionOnServletError = function () {};
	this.functionBeforeSend 	= function () {};
	this.functionAlwaysEx;
	this.functionFabric;
	
	this.data;
	this.ajaxErrorMsg;
	this.contenido;
	this.errorCode = 0;
	this.errorMsg  = "";
	
	this.name;
	this.fabric;
	this.isDone = false;
	
	this.execute = function(functionToEx, functionOnServletError, functionOnAjaxError, functionBeforeSend) {
		if (!isEmpty(functionToEx)) {
			this.functionToEx = functionToEx;
		}
		
		if (!isEmpty(functionOnAjaxError)) {
			this.functionOnAjaxError = functionOnAjaxError;
		}

		if (!isEmpty(functionOnServletError)) {
			this.functionOnServletError = functionOnServletError;
		}
		
		if (!isEmpty(functionBeforeSend)) {
			this.functionBeforeSend = functionBeforeSend;
		}
		
		var ajax = $.ajax({
			ajaxCall:   this,
			url:        this.url,
			type:       this.callType,
			data:       this.parameters,
			async:      this.async,
			beforeSend: this.functionBeforeSend,
			success: [
			            this.checkResponseErrors,
			            this.getData,
			            this.getContenido,
			            this.functionToEx,
			            this.functionOnServletError,
			            this.showServletErrorMsg,
			            this.functionAlwaysEx,
			            this.functionFabric
			],
			error: [
			        	this.functionOnAjaxError,
			        	this.functionAlwaysEx,
			        	this.functionFabric
			]      
		});
		
		return ajax;
	}
	
	this.checkResponseErrors = function (data) {
		try {
			var ajaxCall = this.ajaxCall;
			if (typeof data == "undefined" || data == "") {
				ajaxCall.errorCode = -2;
				ajaxCall.errorMsg = "No se ha podido recuperar nada en la llamada de " + ajaxCall.url;
			} else if (!isUndefined(data.ERROR_CODE) && data.ERROR_CODE != 0) {
				ajaxCall.errorCode = data.ERROR_CODE;
				ajaxCall.errorMsg = data.ERROR_MSG;
			}
		} catch(e) {
			modalError("Fatal-Error", e);
		}
	};
	
	this.getData = function (data) {
		try {
			this.ajaxCall.data = data;
		} catch(e) {
			modalError("Fatal-Error", e);
		}
	};
	
	this.getContenido = function () {
		try {
			var ajaxCall = this.ajaxCall;
			if (ajaxCall.errorCode != -2 && ajaxCall.data.CONTENIDO && ajaxCall.data.CONTENIDO != "") {
				this.contenido = ajaxCall.data.CONTENIDO;
			}
		} catch(e) {
			modalError("Fatal-Error", e);
		}
	};
	
	this.showServletErrorMsg = function () {
		try {
			var ajaxCall = this.ajaxCall;
			if (ajaxCall.errorCode != 0 && ajaxCall.showServletError) {
				modalError("Error " + ajaxCall.errorCode, ajaxCall.errorMsg);
			}
		} catch(e) {
			modalError("Fatal-Error", e);
		}
	}
}

AjaxCall.prototype.functionAlwaysEx = function () {
	this.ajaxCall.isDone = true;
};

AjaxCall.prototype.functionFabric = function () {
	if (!isUndefined(this.ajaxCall.fabric)) {
		this.ajaxCall.fabric.executeSharedFunctions(this.ajaxCall.name);
	}
};

//-------------------------------------------------------------------
//AJAX FABRIC
//-------------------------------------------------------------------

function AjaxFabric() {
	//Variables
	this.ajaxs 			 = [];
	this.sharedFunctions = [];
	
	//Funciones
	this.getNewAjaxCall;
	this.executeSharedFunctions;
}

AjaxFabric.prototype.getNewAjaxCall = function (url, parameters, name) {
	var newAjax;
	try {
		newAjax = new AjaxCall(url, parameters);
		newAjax.name   = name;
		newAjax.fabric = this;
		this.ajaxs.push(newAjax);
	} catch(e) {
		modalError("Fatal-Error", "Fallo en la function AjaxFabric.getNewAjaxCall || " + e);
	}
	
	return newAjax;
};

AjaxFabric.prototype.getAjax = function (name) {
	var ajaxCall;
	try {
		var i = 0;
		for (i; i < this.ajaxs.length; i++) {
			if (this.ajaxs[i].name == name) {
				ajaxCall = this.ajaxs[i];
				i = this.ajaxs.length;
			}
		}
	} catch(e) {
		modalError("Fatal-Error", "Fallo en la funcion AjaxFabric.getAjax || " + e);
	}
	
	
	return ajaxCall;
};

AjaxFabric.prototype.addSharedFunction = function (sharedFunction, ajaxNames) {
	try {
		var objectSharedFunction = {
				sharedFunction: sharedFunction,
				ajaxNames:      ajaxNames
		}
		
		this.sharedFunctions.push(objectSharedFunction);
	} catch(e) {
		modalError("Fatal-Error", "Fallo en la funcion AjaxFabric.addSharedFunction || " + e);
	}
}

AjaxFabric.prototype.executeSharedFunctions = function (name) {
	try {
		var ajaxFabric = this;
		ajaxFabric.sharedFunctions.some(function (value, index, sharedFunctions) {
			var hisFunction = false;
			
			value.ajaxNames.some(function (value2, index2) {
				if (value2 == name) {
					hisFunction = true;
					return true;
				}
			});
			
			if (hisFunction) {
				var ajaxsDone = ajaxFabric.allAjaxDone(value.ajaxNames);
				if (ajaxsDone == -1) {
					value.executed = true;
				} else if (ajaxsDone == 1 && !value.executed) {
					value.sharedFunction(ajaxFabric);
					value.executed = true;
				}
			}
		});
	} catch(e) {
		modalError("Fatal-Error", "Fallo en la funcion AjaxFabric.executeSharedFunctions || " + e);
	}
};

AjaxFabric.prototype.allAjaxDone = function (names) {
	try {
		var ajaxsDone = 0;
		var i = 0;
		for (i; i < names.length; i++) {
			var ajaxCall = this.getAjax(names[i]);
			if (ajaxCall.isDone) {
				if (ajaxCall.errorCode == 0) {
					ajaxsDone++;
				} else if (ajaxCall.errorCode != 0) {
					return -1;
				}
			}
		}
		
		if (ajaxsDone == names.length) {
			return 1;
		} else {
			return 0;
		}
	} catch(e) {
		modalError("Fatal-Error", "Fallo en la funcion AjaxFabric.allAjaxDone || " + e);
	}
};

AjaxFabric.prototype.executeAll = function () {
	var i = 0;
	for (i; i < this.ajaxs.length; i++) {
		this.ajaxs[i].execute();
	}
};















