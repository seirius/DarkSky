$(document).ready(function ($) {
	$(".clickable").click(function() {
		window.document.location = $(this).data("href");
	});
	
	$('.summernote').summernote({
		height: 100,
		maxHeight: 300,
		toolbar: [
			          ['style', [
			                     'bold', 'italic', 'underline', 'clear'
			                    ]
			          ],
			          ['color', [
			                     'color'
			                    ]
			          ],
		         ]
	});
});

//------------------------------------------------------------
//MODAL ERROR
//------------------------------------------------------------


/**
 * Muestra un modal de error
 * 
 * @param title String
 * @param text String
 * @param state Estado del modal: 'show', 'hide', ...
 */
function modalError(title, text, state) {
	$("body").append(getModalErrorHtml());
	
	if (title != "" && title) $("#modalErrTitle").html(title);
	if (text  != "" && text ) $("#modalErrText").html(text);
	if (state != "" && state) $("#modalError").modal(state);
	else if (!state)          $("#modalError").modal("show");
	
	$('#modalError').on('hidden.bs.modal', function () {
		$(this).remove();
	});
}

function getModalErrorHtml() {
	var html = "";
	
	$.ajax({
		url: urls.MODAL_ERROR,
		type: "POST",
		async: false,
		success: function (data) {
			html = data;
		}
	});
	
	return html;
}

/**
 * Hace lo mismo que los metodos isBadResponse y modalError
 * 
 * @param response
 */
function isRetOk(response) {
	try {
		if (isBadResponse(response)) {
			modalError("Error", response.ERROR_MSG);
			return false;
		}
		return true;
	} catch(e) {
		modalError("Fatal-Error", e);
		return false;
	}
}

function isBadResponse(response) {
	if (response.ERROR_CODE != 0) {
		return true;
	}
	
	return false;
}

function getBadResponseMsg(response) {
	return response.ERROR_MSG;
}

//------------------------------------------------------------
//FIN MODAL ERROR
//------------------------------------------------------------

//------------------------------------------------------------
//VOTAR
//------------------------------------------------------------

function manageVotosButtons() {
	
	$(".voto-positivo").click(function () {
		votar("1", $(this).closest(".capa-votos").find(".votosPostSpan"));
		
		var votoNegativo = $(this).closest(".capa-votos").find(".voto-negativo");
		if (votoNegativo.hasClass("select-negativo")) {
			votoNegativo.removeClass("select-negativo");
		}
		
		$(this).toggleClass("select-positivo");
	}); 
	
	$(".voto-negativo").click(function () {
		votar("-1", $(this).closest(".capa-votos").find(".votosPostSpan"));
		
		var votoPositivo = $(this).closest(".capa-votos").find(".voto-positivo");
		if (votoPositivo.hasClass("select-positivo")) {
			votoPositivo.removeClass("select-positivo");
		}
		
		$(this).toggleClass("select-negativo");
	});
}

function votar(valorVoto, votoCont, id) {
	try {
		if (!id) id = idPost; 
		
		$.ajax({
			url: urls.VOTAR,
			data: {
				idPost: id,
				valorVoto: valorVoto
			},
			type: "POST",
			success: function (data) {
				if (isRetOk(data)) {
					votoCont.html(data.CONTENIDO.VOTO);
				}
			}
		});
		
	} catch(e) {
		modalError("Error: 1", "Ha ocurrido algo inesperado intentado votar");
	}
}

//------------------------------------------------------------
//UTIL
//------------------------------------------------------------

/**
 * Comprueba si un elemento esta nulo, indefinido o es un string vacio ('').
 * 
 * @param Item
 * @param {Boolean} ret
 */
function isEmpty(item) {
	if (item == null || typeof item == "undefined" || item == "") {
		return true;
	} else {
		return false;
	}
}

function isString(item) {
	if (typeof item == "string") {
		return true;
	}
	return false;
}

function isUndefined(item) {
	if (typeof item == "undefined") {
		return true;
	}
	return false;
}

//------------------------------------------------------------
//DEBUG
//------------------------------------------------------------
function debugOnConsole(debugTarget, name, timeInterval) {
	setInterval(function () {
		console.log(name + ": " + debugTarget);
	}, timeInterval);
}





