var NO_NOTIS  = "No hay notificaciones nuevas";
var YES_NOTIS = "Hay notificaciones nuevas";

var notButtonText = NO_NOTIS;

var numeroNotificaciones = 0;
var numNotisUsuario = 0;
var numNotisEntrada = 0;


//NOTIFICACIONES XS
(function ($, viewport) {
	$(document).ready(function () {
		var currentSize = viewport.current();
		if (currentSize == "xs" || currentSize == "sm" || currentSize == "md") {
			var buttonUsuario = "<button class='btn btn-warning btn-block' id='botonXSUsuario'></button>";
			var buttonEntrada = "<button class='btn btn-warning btn-block' id='botonXSEntrada'></button>";
			
			$("#alertXSNotis").popover({
				html: true, 
				placement: "left",
				content: buttonUsuario + buttonEntrada
			});
			
			$("#alertXSNotis").click(function () {
				$("#botonXSUsuario").prop("disabled", true);
				$("#botonXSEntrada").prop("disabled", true);
				
				var eventoEntrada = [];
				eventoEntrada[0] = "POST_NEW_COMENTARIO";
				eventoEntrada[1] = "POST_NEW_RESPUESTA";
				
				getNumeroNotificaciones(eventoEntrada).done(function (data) {
					var numeroNotificacion = data.CONTENIDO.numeroNotificacion;
					if (numeroNotificacion > 0) {
						$("#botonXSEntrada").prop("disabled", false);
						$("#botonXSEntrada").html("Entrada <span class='badge'>" + numeroNotificacion + "</span>");
					} else {
						$("#botonXSEntrada").prop("disabled", true);
						$("#botonXSEntrada").html("Entrada");
					}
				});
				
				var eventoUsuario = [];
				eventoUsuario[0] = "USUARIO_NEW_COMENTARIO";
				eventoUsuario[1] = "USUARIO_NEW_POST";
				
				getNumeroNotificaciones(eventoUsuario).done(function (data) {
					var numeroNotificacion = data.CONTENIDO.numeroNotificacion;
					if (numeroNotificacion > 0) {
						$("#botonXSUsuario").prop("disabled", false);
						$("#botonXSUsuario").html("Usuario <span class='badge'>" + data.CONTENIDO.numeroNotificacion + "</span>");
					} else {
						$("#botonXSUsuario").prop("disabled", true);
						$("#botonXSUsuario").html("Usuario");
					}
					
					$("#botonXSEntrada").click(function () {
						$("body").css("cursor", "wait");
						var modal = launchModal("Lista de notificaciones");
						var ajaxNotificaciones = cargarNotificaciones("PEN", 0);
						ajaxNotificaciones.done(function (data) {
							if (isRetOk(data)) {
								var notificaciones = data.CONTENIDO;
								var ul = $("<ul class='list-group margin-top-1'></ul>");
								cargarListaNotificaciones(ul, notificaciones);
								cargarListaNotificacionesFuncionalidad(ul, notificaciones);
								modal.find(".modal-body").append(ul);
								modal.modal("show");
							}
							$("body").css("cursor", "auto");
						});
					});
					
					$("#botonXSUsuario").click(function () {
						var modal = launchModal("Lista de notificaciones");
						var ajaxNotificaciones = cargarNotificaciones("PEN", 0);
						ajaxNotificaciones.done(function (data) {
							if (isRetOk(data)) {
								var notificaciones = data.CONTENIDO;
								var ul = $("<ul class='list-group margin-top-1'></ul>");
								cargarListaNotificaciones(ul, notificaciones);
								cargarListaNotificacionesFuncionalidad(ul, notificaciones);
								modal.find(".modal-body").append(ul);
								modal.modal("show");
							}
						});
					});
				});
			});
			
//			if (typeof isSessionInit != "undefined" && isSessionInit) {
//				setNumeroNotificacionesXS();
//				setInterval(setNumeroNotificacionesXS, 5000);
//			}
			
			
		} else {
			var notis          = $("#capaNotificaciones");
			var buttonSlides   = $(".notification-slide-down, .notification-slide-up");
			var notisSlideUp   = $(".notification-slide-up");
			var notisSlideDown = $(".notification-slide-down");
			var slide1         = $("#slideNotificacion1");
			var slide2         = $("#slideNotificacion2");
			
			notis.height(slide1.height());
			
			buttonSlides.click(function () {
				if (slide2.css("display") == "none") {
					notis.height(slide2.height());
				} else {
					notis.height(slide1.height());
				}
				
				slide1.fadeToggle("fast");
				notisSlideUp.fadeToggle("fast");
				slide2.toggle("slide", {direction: "up"});
			});
			
			$("#abrirNotificaciones").hover(
				function () {
					$(this).html("<span class='glyphicon glyphicon-chevron-down'></span>");
				}, 
				function () {
					$(this).html(notButtonText);
				}
			);
			
			$("#cerrarNotificaciones").hover(
				function () {
					$(this).html("<span class='glyphicon glyphicon-chevron-up'></span>");
				}, 
				function () {
					$(this).html(notButtonText);
				}
			);
			
			$("#abrirNotificaciones").click(function () {
				var ajaxNotificaciones = cargarNotificaciones("PEN", 5);
				ajaxNotificaciones.done(function (data) {
					if (isRetOk(data)) {
						var notificaciones = data.CONTENIDO;
						cargarListaNotificaciones($("#notisPost").find("ul"), notificaciones);
						cargarListaNotificacionesFuncionalidad($("#notisPost").find("ul"), notificaciones);
					}
				});
			});
			
			if (typeof isSessionInit != "undefined" && isSessionInit) {
				setNumeroNotificaciones();
				setInterval(setNumeroNotificaciones, 5000);
			}

			$(".mostrarMasNotificaciones").click(function () {
				$("body").css("cursor", "wait");
				var modal = launchModal("Lista de notificaciones");
				var ajaxNotificaciones = cargarNotificaciones("PEN", 0);
				ajaxNotificaciones.done(function (data) {
					if (isRetOk(data)) {
						var notificaciones = data.CONTENIDO;
						var ul = $("<ul class='list-group margin-top-1'></ul>");
						cargarListaNotificaciones(ul, notificaciones);
						cargarListaNotificacionesFuncionalidad(ul, notificaciones);
						modal.find(".modal-body").append(ul);
						modal.modal("show");
						$("body").css("cursor", "auto");
					}
				});
			});
		}
		
	});
})(jQuery, ResponsiveBootstrapToolkit);

/**
 * Crea la lista de notificaciones
 * 
 * @param targetDiv Elemento donde situar la lista
 * @param notificaciones Array de notificaciones que tiene que desplegar
 * @param mostrarTodo si es true muestra el boton de mostrar todas las notificaciones (se despliegan en un modal)
 */
function cargarListaNotificaciones(contenedorNotificaciones, notificaciones) {
	contenedorNotificaciones.find(".notificacion").remove();
	
	var filaOptions = $("<button class='list-group-item actualizarListaNotificaciones notificacion'><div class='text-center'>Actualizar lista</div></button>");
	contenedorNotificaciones.prepend(filaOptions);
	
	for (var i = notificaciones.length - 1; i >= 0; i--) {
		var li    = $("<li class='list-group-item btn-group notificacion'></li>");
		var divRow = $("<div class='row'></div>");
		li.append(divRow);
		
		var divCol8 = $("<div class='col-xs-8'></div>")
		divRow.append(divCol8);
		
		var buttonPrincipal = $("<button type='button' class='btn btn-default btn-block showNotificacionText overflow-auto'></button>")
		buttonPrincipal.attr("title", "Entrada " + notificaciones[i].postTitulo);
		buttonPrincipal.data("content", notificaciones[i].commentText);
		buttonPrincipal.text("Comentario de " + notificaciones[i].usuTarget);
		
		divCol8.append(buttonPrincipal);		
		
		var divCol4 = $("<div class='col-xs-4'></div>");
		divRow.append(divCol4);
		
		var divBtnGroupJustified = $("<div class='btn-group btn-group-justified'></div>")
		divCol4.append(divBtnGroupJustified);
		
		var stringBtnGroup = "<div class='btn-group'></div>";
		var divBtnGroup1 = $(stringBtnGroup);
		var divBtnGroup2 = $(stringBtnGroup);
		divBtnGroupJustified.append(divBtnGroup1).append(divBtnGroup2);
		
		var buttonEstado = "";
		if (notificaciones[i].estado == "PEN") {
			buttonEstado = "btn-info";
		} else if (notificaciones[i].estado == "VISTO") {
			buttonEstado = "btn-default";
		}
		
		var buttonVisto  = $("<button type='button' class='btn marcarNotificacionVista'></button>");
		var glyphiconEye = $("<span class='glyphicon glyphicon-eye-open'></span>");
		buttonVisto.addClass(buttonEstado);
		buttonVisto.append(glyphiconEye);
		buttonVisto.data("idnotificacion", notificaciones[i].idNotificacion);
		divBtnGroup1.append(buttonVisto);
		
		var buttonEntrar = $("<button type='button' class='btn btn-success botonIrAEvento'></button>");
		var glyphiconArrow = $("<span class='glyphicon glyphicon-arrow-right'></span>");
		buttonEntrar.append(glyphiconArrow);
		buttonEntrar.data("href", urls.POST + "?idPost=" + notificaciones[i].idPost);
		divBtnGroup2.append(buttonEntrar);
		
		contenedorNotificaciones.prepend(li);
	}
}

function cargarListaNotificacionesFuncionalidad(contenedorNotificaciones, notificaciones) {
	contenedorNotificaciones.find(".actualizarListaNotificaciones").click(function () {
		var ajaxNotificaciones = cargarNotificaciones("PEN", notificaciones.length);
		ajaxNotificaciones.done(function (data) {
			if (isRetOk(data)) {
				var notificaciones = data.CONTENIDO;
				cargarListaNotificaciones(contenedorNotificaciones, notificaciones);
				cargarListaNotificacionesFuncionalidad(contenedorNotificaciones, notificaciones);
			}
		});
	});
	
	contenedorNotificaciones.find(".botonIrAEvento").click(function () {
		window.document.location = $(this).data("href");
	});
	
	contenedorNotificaciones.find(".showNotificacionText").popover({
		html:      true, 
		placement: "bottom" 
	});
	
	contenedorNotificaciones.find(".showNotificacionText").click(function () {
		$(this).toggleClass("active");
	});
	
	
	contenedorNotificaciones.find(".marcarNotificacionVista").click(function () {
		var idNotificacion = $(this).data("idnotificacion");
		$.ajax({
			url: urls.SEGUIR,
			type: "POST",
			data: {
				tipo: "marcarVistaNotificacion",
				idNotificacion: idNotificacion
			},
			success: function (data) {
				if (isRetOk(data)) {
					$(".actualizarListaNotificaciones").click();
				}
			}
		});
	});
}

/**
 * @param estado PEN | VISTO
 * @param limit
 * @returns
 */
function cargarNotificaciones(estado, limit) {
	return $.ajax({
		url: urls.SEGUIR,
		data: {
			tipo: "getPostNotificacion",
			limit: limit,
			estado: estado
		}
	});
}

function setNumeroNotificaciones() {
	var evento = [];
	evento[0] = "POST_NEW_COMENTARIO";
	evento[1] = "POST_NEW_RESPUESTA";
	evento[2] = "USUARIO_NEW_COMENTARIO";
	evento[3] = "USUARIO_NEW_POST";
	
	getNumeroNotificaciones(evento).done(function (data) {
		if (isRetOk(data)) {
			var contenido = data.CONTENIDO;
			numeroNotificaciones = parseInt(contenido.numeroNotificacion);
			var botonAbrir = $("#abrirNotificaciones");
			var botonCerrar = $("#cerrarNotificaciones");
			
			if (numeroNotificaciones > 0) {
				notButtonText = YES_NOTIS + " <span class='badge'>" + numeroNotificaciones + "</span>";
				botonAbrir.prop("disabled", false);
				botonCerrar.prop("disabled", false);
			} else {
				botonAbrir.prop("disabled", true);
				botonCerrar.prop("disabled", false);
				notButtonText = NO_NOTIS;
			}
			
			$("#abrirNotificaciones, #cerrarNotificaciones").html(notButtonText);
		}
	});
}

function setNumeroNotificacionesXS() {
	var evento = [];
	evento[0] = "POST_NEW_COMENTARIO";
	evento[1] = "POST_NEW_RESPUESTA";
	evento[2] = "USUARIO_NEW_COMENTARIO";
	evento[3] = "USUARIO_NEW_POST";
	
	getNumeroNotificaciones(evento).done(function (data) {
		var contenido = data.CONTENIDO;
		numeroNotificaciones = parseInt(contenido.numeroNotificacion);
		if (numeroNotificaciones > 0) {
			$("#capaNotificacionXSId").show();
			$("#capaNotificacionXSId").addClass("visible-xs visible-sm visible-md");
			$("#numeroXSNotis").html(numeroNotificaciones);
		} else {
			$("#capaNotificacionXSId").hide();
			$("#capaNotificacionXSId").removeClass("visible-xs visible-sm visible-md");
		}
	});
}

function getNumeroNotificaciones(eventos) {
	return $.ajax({
		url: urls.SEGUIR,
		dataType: "JSON",
		data: {
			evento: JSON.stringify(eventos),
			estado: "PEN",
			tipo: "getNotificacionesCount"
		}
	});
}















