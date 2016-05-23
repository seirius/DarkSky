$(document).ready(function () {
	
	manageVotosButtons();

	$('.grid').masonry({
		  itemSelector: '.grid-item'
	});
	
	$(".comentario-popover").popover({
		placement: "auto"
	});
	
	/* SLIDER COMENTARIO->RESPUESTAS */
	
	$(".slides").height($("#slide1").height());
	$(".get-respuestas").click(function () {
		var idComentario = $(this).data("id");
		$.ajax({
			url: urls.RESPUESTAS_COMENTARIO,
			type: "POST",
			data: {
				idComentario: idComentario
			},
			success: function (data) {
				$("#contenidoExpandirComentario").html(data);
				$(".slides").height($("#slide2").height());
				$("#slide1").toggle("slide", {direction: "right"});
				$("#slide2").toggle("slide", {direction: "left"});
				recargarBotonesComentario();
			}
		});
	});
	
	$("#botonVolverAtras").click(function () {
		$(".slides").height($("#slide1").height());
		$("#slide2").toggle("slide", {direction: "left"});
		$("#slide1").toggle("slide", {direction: "right"});
	});
	
	/* FIN SLIDER COMENTARIO->RESPUESTAS */
	
	/* COMENTARIO */
	recargarBotonesComentario();
	
	$("#buttonConfirmarEdicionComentario").click(function () {
		editarComentario();
	});
	
	$("#botonEnviarComentario").click(function () {
		var idComment = $("#idComentario").html();
		var type =  $("#comentarioType").html();
		var textoComentario = $("#comentarioTexto").summernote("code");
		
		$.ajax({
			url: urls.COMENTAR,
			type: "POST",
			data: {
				type: type,
				idComment: idComment,
				textoComentario: textoComentario,
			},
			success: function (data) {
				if (isBadResponse(data)) {
					modalError("", getBadResponseMsg(data), "show");
				} else {
					location.reload();
				}
			}
		});
	});
	
	$("#botonComentarPost").click(function () {
		var type = "comentar";
		var textoComentario = $("#textoComentarioPost").summernote("code");
		
		$.ajax({
			url: urls.COMENTAR,
			type: "POST",
			data: {
				idComment: idPost,
				type: type,
				textoComentario: textoComentario,
			},
			success: function (data) {
				if (isRetOk(data)) {
					location.reload();
				}
			}
		});
	});
	/* FIN COMENTARIO */
	
	/* POST */
	
	$("#textBoxPostTitulo").summernote("code", $("#spanTitulo").html());
	$("#textBoxPostTexto").summernote("code", $("#spanTexto").html());
	
	$("#buttonConfirmarEdicion").click(function () {
		$.ajax({
			url: urls.EDITAR_POST,
			type: "POST",
			data: {
				idPost: idPost,
				titulo: $("#textBoxPostTitulo").summernote("code"),
				texto: $("#textBoxPostTexto").summernote("code"),
				type: "editarPost"
			},
			async: false,
			success: function (data) {
				if (isRetOk(data)) {
					location.reload();
				}
			}
		});
	});
	
	/* FIN POST */
	
	$(".capa-comentario-ayuda").click(function (e) {
		e.stopPropagation();
	});
	
	$("#buttonConfirmarDelete").click(function () {
		$.ajax({
			url: urls.ELIMINAR_POST,
			type: "POST",
			data: {
				idPost: idPost
			},
			async: false,
			success: function (data) {
				if (isRetOk(data)) {
					window.location.replace(urls.FORO);
				}
			}
		});
	});
	
	$("#buttonConfirmarDeleteComentario").click(function () {
		var idComment = $("#modalEliminarIdComment").html();
		var tipoComment = $("#modalEliminarTipoComentario").html();
		
		$.ajax({
			url: urls.ELIMINAR_COMENTARIO,
			type: "POST",
			data: {
				type: tipoComment,
				idComment: idComment,
			},
			success: function (data) {
				if (isRetOk(data)) {
					location.reload();
				}
			}
		});
	});
	
	$("#label-follow").hover(
		function () {
			var thisLabel = $(this);
			thisLabel.removeClass("label-primary");
			thisLabel.addClass("label-success");
			thisLabel.html("Empezar a seguir");
		}, 
		function () {
			var thisLabel = $(this);
			thisLabel.removeClass("label-success");
			thisLabel.addClass("label-primary");
			thisLabel.html("Seguir");
	});
	
	$("#label-follow").click(function () {
		var thisLabel = $(this);
		thisLabel.removeClass("label-success label-primary").addClass("label-default");
		thisLabel.html("<span class='glyphicon glyphicon-refresh glyphicon-refresh-animate label-size-refresh'></span> Cargando");
		seguirPost("followPost", idPost, thisLabel, $("#label-unfollow"));
	});
	
	$("#label-unfollow").hover(
		function () {
			var thisLabel = $(this);
			thisLabel.removeClass("label-success");
			thisLabel.addClass("label-danger");
			thisLabel.html("Dejar de seguir");
		}, 
		function () {
			var thisLabel = $(this);
			thisLabel.removeClass("label-danger");
			thisLabel.addClass("label-success");
			thisLabel.html("Siguiendo");
	});
	
	$("#label-unfollow").click(function () {
		var thisLabel = $(this);
		thisLabel.removeClass("label-danger label-success").addClass("label-default");
		thisLabel.html("<span class='glyphicon glyphicon-refresh glyphicon-refresh-animate label-size-refresh'></span> Cargando");
		seguirPost("unfollowPost", idPost, thisLabel, $("#label-follow"));
	});
});

function sumarVoto(voto, valor, valorVoto) {
	var postDiv = voto.parent().parent();
	$.ajax({
		url: urls.VOTAR,
		type: "POST",
		data: {
			idPost: idPost,
			valorVoto: valorVoto
		},
		async: false,
		success: function (data) {
			if (isRetOk(data)) {
				voto.html(data.CONTENIDO.VOTO);
			}
		}
	});
}

function editarComentario() {
	var idComment = $("#idComentarioModal").html();
	var texto = $("#textBoxComentarioTexto").summernote("code");
	var tipoEditor = $("#tipoComentarioModal").html();
	
	var type = "editarComentario";
	if (tipoEditor == "respuesta") type = "editarRespuesta";
	
	$.ajax({
		url: urls.EDITAR_POST,
		type: "POST",
		data: {
			idComment: idComment,
			texto: texto,
			type: type
		},
		success: function (data) {
			if (isRetOk(data)) {
				location.reload();
			}
		}
	});
}

function seguirPost(tipo, idPost, label, inverseLabel) {
	$.ajax({
		url: urls.SEGUIR,
		type: "POST",
		data: {
			tipo: tipo,
			idPost: idPost
		},
		success: function (data) {
			if (isRetOk(data)) {
				label.addClass("hidden");
				inverseLabel.removeClass("hidden");
			}
		}
	});
}

/**
 * Recarga los botones para poder responder, editar o eliminar un comentario en el nuevo html creado
 */
function recargarBotonesComentario() {
	//---------------------------------
	//Mostrar modal para editar comentario
	//---------------------------------
	$(".mostrarModalEditarComentario").click(function () {
		var idComment = $(this).data("id");
		var typeComment = $(this).data("type");
		
		$.ajax({
			url: urls.COMENTAR,
			type: "POST",
			data: {
				idComment: idComment,
				type: "getTextoComentario",
				tipoComment: typeComment,
			},
			success: function (data) {
				if (isRetOk(data)) {
					$("#textBoxComentarioTexto").summernote("code", data.CONTENIDO.TEXTO);
				}
			}
		});
		
		$("#idComentarioModal").html(idComment);
		$("#tipoComentarioModal").html(typeComment);
	});
	
	//---------------------------------
	//Mostrar modal para eliminar comentario
	//---------------------------------
	$(".mostrarModalEliminarComentario").click(function () {
		$("#modalEliminarIdComment").html($(this).data("id"));
		$("#modalEliminarTipoComentario").html($(this).data("tipocomment"));
	});
	
	//---------------------------------
	//Mostrar modal para responder a comentario
	//---------------------------------
	$(".showModalComentario").click(function () {
		$("#idComentario").html($(this).data("id"));
		$("#comentarioType").html($(this).data("comentariotype"));
	});
}
