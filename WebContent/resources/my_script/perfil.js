$(document).ready(function () {
	$(".id_editarCampo").click(function () {
		var target = $($(this).data("target"));
		$(this).addClass("hidden");
		target.prop("disabled", false);
		
		var parent = $(this).parent();
		parent.find(".id_guardarCambios").removeClass("hidden");
	});
	
	$(".id_guardarCambios").click(function () {
		var button = $(this);
		
		var method = button.data("method");
		var newValue = $(button.data("target")).val();
		
		var parameters = {
				method: method,
				newValue: newValue,
				nickTarget: perfilNick
		};
		
		var functionToEx = function(data) {
			button.addClass("hidden");
			button.parent().find(".id_editarCampo").removeClass("hidden");
			$(button.data("target")).prop("disabled", true);
			button.button("reset");
			$(button.data("target")).data("oldvalue", $(button.data("target")).val());
		};
		
		var functionError = function(data) {
			button.addClass("hidden");
			button.parent().find(".id_editarCampo").removeClass("hidden");
			$(button.data("target")).prop("disabled", true);
			button.button("reset");
			$(button.data("target")).val($(button.data("target")).data("oldvalue"));
		};
		
		var functionBeforeSend = function() {
			button.button("loading");
		}
		
		var ajaxCall = new AjaxCall(urls.USUARIO_RESPONSE, parameters);
		ajaxCall.execute(functionToEx, functionError, null, functionBeforeSend);
	});
	
	$("#comprobarPassword").click(function () {
		var button = $(this);
		
		var method = button.data("method");
		var value = $(button.data("target")).val();
		
		if (isEmpty(value)) {
			modalError("Error", "La contraseña no puede ser vacia");
			return;
		}
		
		var parameters = {
				method:     method,
				value:      value,
				nickTarget: perfilNick
		};
		
		var functionToEx = function () {
			button.button("reset");
			if (this.ajaxCall.errorCode == 0) {
				$(button.data("target")).val("");
				$("#groupoPWActual").addClass("hidden");
				$("#groupoPWNuevo").removeClass("hidden");
				$("#groupoPWNuevoR").removeClass("hidden");
				$("#grupoBotonPassword").removeClass("hidden");
			}
		};
		
		var functionBeforeSend = function () {
			button.button("loading");
		};
		
		var ajaxCall = new AjaxCall(urls.USUARIO_RESPONSE, parameters);
		ajaxCall.execute(functionToEx, null, null, functionBeforeSend);
		
	});
	
	$("#cambiarPassword").click(function () {
		var button = $(this);
		
		var method = button.data("method");
		var value = $("#usuarioNuevaPassword").val();
		var valueR = $("#usuarioNuevaPasswordR").val();
		
		if (isEmpty(value)) {
			modalError("No se puede seguir.", "La contraseña no puede ser vacia.");
			return;
		}
		
		if (isEmpty(valueR)) {
			modalError("No se puede seguir.", "La contraseña no puede ser vacia.");
			return;
		}
		
		if (value != valueR) {
			modalError("No se puede seguir.", "Las contraseñas no coinciden.");
			return;
		} 
		
		var parameters = {
			method:     method,
			value:      value,
			nickTarget: perfilNick
		};
		
		var functionBeforeSend = function () {
			button.button("loading");
		};
		
		var functionToEx = function () {
			button.button("reset");
			cancelarCambiarPassword();
		};
		
		var ajaxCall = new AjaxCall(urls.USUARIO_RESPONSE, parameters);
		ajaxCall.execute(functionToEx, null, null, functionBeforeSend);
	});
	
	$("#cancelarCambiarPasswordR").click(function () {
		cancelarCambiarPassword();
	});
});

function cancelarCambiarPassword() {
	$("#groupoPWActual").removeClass("hidden");
	$("#groupoPWNuevo").addClass("hidden");
	$("#groupoPWNuevoR").addClass("hidden");
	$("#grupoBotonPassword").addClass("hidden");
	$("#usuarioNuevaPassword").val("");
	$("#usuarioNuevaPasswordR").val("");
}