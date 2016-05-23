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
		
		var ajaxCall = new AjaxCall(urls.USUARIO_RESPONSE, parameters, functionToEx, functionError);
		button.button("loading");
		ajaxCall.execute();
	});
	
	$("#comprobarPassword").click(function () {
		$("#groupoPWActual").addClass("hidden");
		$("#groupoPWNuevo").removeClass("hidden");
	});
});