<%@page import="darksky.util.MyUtil"%>
<%@page import="darksky.modelo.service.SeguirService"%>
<%@page import="darksky.modelo.service.UsuarioService"%>
<%@page import="darksky.modelo.bean.Usuario"%>
<%@page import="darksky.modelo.bean.Rol"%>
<%@page import="darksky.controlador.helpers.ComentarioHandler"%>
<%@page import="darksky.util.interfaces.Comment"%>
<%@page import="darksky.controlador.helpers.SessionHandler"%>
<%@page import="darksky.modelo.bean.Respuesta"%>
<%@page import="darksky.modelo.bean.Comentario"%>
<%@page import="darksky.modelo.bean.Post"%>
<%@page import="java.util.List"%>
<%@page import="darksky.modelo.service.PostService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<%
SessionHandler sessionHandler = new SessionHandler(request);

PostService postService = new PostService();
SeguirService seguirService = new SeguirService();
UsuarioService usuarioService = new UsuarioService();

int idPost = Integer.parseInt(request.getParameter("idPost"));
Post post = postService.getPost(idPost);
postService.visitPost(post);

List<Comment> listaComment = postService.getComentarios(post);

List<Comentario> listaComentarios = ComentarioHandler.getSoloComentarios(listaComment);

String thisPath = "/DarkSky/post?idPost=" + idPost;

Usuario usuario = sessionHandler.getUsuario();
boolean mostrarOpcionesPost = false;
boolean isADMIN = false;
String siguiendo = "hidden";
String seguir = "hidden";
boolean isSessionInit = false;
if (sessionHandler.isInit()) {
	usuario = sessionHandler.getUsuario();
	isSessionInit = true;
	
	if (usuarioService.permisoEditarForo(usuario)) {
		isADMIN = true;
	}
	
	if (usuarioService.propietarioPost(usuario, post)) {
		mostrarOpcionesPost = true;
	}
	
	if (seguirService.siguiendoPost(usuario.getNick(), idPost)) {
		siguiendo = "";
	} else {
		seguir = "";
	}
	
	seguirService.marcarComoVistasNotificaciones_UsuarioPost(usuario.getNick(), idPost);
}
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= post.getTitulo() %></title>

<!-- SCRIPT -->
<script>
	var thisPath = "<%= thisPath %>";
	var isSessionInit = <%= isSessionInit %>;
	var idPost = <%= post.getId() %>;
</script>

<!-- Imports -->
<link rel="stylesheet" href="/DarkSky/resources/elements/font-awesome/css/font-awesome.css" />
<%@ include file="/includes/imports/default-imports.jsp"%>
<link rel="stylesheet" href="/DarkSky/resources/my_css/post.css" />
<link rel="stylesheet" href="/DarkSky/resources/my_css/tipo-background-ayuda.css" />
<script src="/DarkSky/resources/my_script/post.js"></script>
<script src="/DarkSky/resources/elements/masonry/masonry.js"></script>
</head>
<body>
	
	<!-- LOGIN -->
	<jsp:include page="/includes/elements/sesion.jsp">
		<jsp:param name="backUrl" value="<%= thisPath %>" />
	</jsp:include>

	<!-- TITULO -->
	<%@ include file="/includes/elements/titulo.jsp"%>


	<!-- MENU -->
	<%@ include file="/includes/elements/menu-bar.jsp"%>

	<%
	if (isSessionInit) {
	%>
	<!-- NOTIFICATIONS XS -->
	<%@ include file="/includes/elements/notificaciones-xs.jsp"%>
	<%
	}
	%>

	<!-- CONTENIDO -->
	<div id="fondo">
		<div class="container-fluid">
			<div class="col-sm-12" id="capa-molde-general">

				<!-- CAPA GENERAL -->
				<div id="capa-general">

					<!-- CAPA IZQUIEDA -->
					<div id="capa-izquierda">
					
					<%
					if (isSessionInit) {
					%>
						<!-- NOTIFICACIONES -->
						<%@ include file="/includes/elements/notificaciones.jsp"%>
					<%
					}
					%>
					
					</div> <!-- FIN CAPA IZQUIERDA -->
					
					
					<!-- CAPA DERECHA -->
					<div id="capa-derecha">
						<div class="bloque-post">
						
							<div class="row">
								<div class="col-sm-1 hidden-xs">
								
								
								
									<!-- CAPA VOTOS -->
									<%
									String selectedPos = "";
									String selectedNeg = "";
									if (isSessionInit) {
										int valorVoto = postService.getVotoPorPost(post.getId(), usuario.getNick());
										if (valorVoto != -2) {
											if (valorVoto == 1) {
												selectedPos = "select-positivo";
											} else if (valorVoto == -1) {
												selectedNeg = "select-negativo";
											}
										}
									}
									%>
									<div class="row">
										<div class="col-xs-12">
											<div class="capa-votos">
												<div class="capa-contador-voto text-center">
													<span class="votosPostSpan"><%=post.getVotos()%></span>
												</div>
												<div class="capa-voto text-center">
													<i class="fa fa-arrow-up pointer voto-positivo <%= selectedPos %>"></i>
												</div>
												<div class="capa-voto text-center">
													<i class="fa fa-arrow-down pointer voto-negativo <%= selectedNeg %>"></i>
												</div>
											</div> 
										</div>
									</div>
									<!-- FIN CAPA VOTOS -->
									
									<!-- CAPA OPCIONES -->
									<%
									if (mostrarOpcionesPost || isADMIN) {
									%>
									<div class="row">
										<div class="col-xs-12">
											<div class="capa-boton-opcion text-center">
												<button class="btn btn-icon" title="Editar entrada" data-toggle="modal" data-target="#modalEditarPost">
													<i class="fa fa-pencil-square-o"></i>
												</button>
												<button class="btn btn-icon" title="Eliminar entrada" data-toggle="modal" data-target="#modalEliminar">
													<i class="fa fa-trash-o"></i>
												</button>
											</div>
										</div>
									</div>
									<%
									}
									%>
									<!-- FIN CAPA OPCIONES -->
									
								</div>
								
								<div class="col-xs-12 col-sm-11">
									
									<!-- CAPA SEGUIR -->
									<div class="row margin-bot-1">
										<div class="col-xs-4">
											<span class="label label-success pointer <%= siguiendo %>" id="label-unfollow">Siguiendo</span>
											<span class="label label-primary pointer <%= seguir %>" id="label-follow">Seguir</span>
										</div>
									</div>
									<!-- FIN CAPA SEGUIR -->
								
									<div class="row">
										<div class="col-xs-12">
											
											<!-- CAPA IMAGEN -->
											<%
											if (post.getImagen() != null) {
											%>
											<div class="capa-imagen text-center pull-right hidden-xs">
												<a href="<%= post.getImagen().getUrl() %>" class="thumbnail">
													<img src="<%= post.getImagen().getUrl() %>" alt="imagen post" />
											    </a>
											</div>
											<%
											}
											%>
											<!-- FIN CAPA IMAGEN -->
											
											<div class="capa-titulo">
												<span id="spanTitulo"><%= post.getTitulo() %></span>
											</div>
											
											<div class="capa-texto">
												<span id="spanTexto"><%= post.getTexto() %></span>
											</div>
										</div>
									</div>
								</div>
							</div>
							
							<!-- Informacion del creador -->
							<div class="row">
								<div class="col-xs-12 text-right">
									<span>Creador por <%= post.getUsuario().getNick() %></span><br>
									<span><%= MyUtil.getFechaFormateada(post.getFechaCreacion(), "dd-MM-yyyy HH:mm:ss") %></span>
								</div>
							</div>
							<!-- Fin informacion del creado -->
							
						</div> <!-- FIN BLOQUE POST -->
						
						<%
							if (isSessionInit) {
								%>
								<!-- CAPA COMENTAR -->
								<div class="capa-comentar margin-bot-2">
									<div class="row">
										<div class="col-xs-12">
											<div class="row">
												<div class="col-xs-10 col-xs-offset-1">
													<textarea id="textoComentarioPost" name="textoComentario" class="summernote"></textarea>
												</div>
											</div>
											<div class="row margin-top-1">
												<div class="col-xs-11 col-xs-offset-1">
													<button type="button" id="botonComentarPost" class="btn btn-pixel btn-pixel-lg btn-pixel-blue" data-idpost="<%= idPost %>">
														Comentar
													</button>
												</div>
											</div>
										</div>
									</div>
								</div> <!-- FIN CAPA COMENTAR -->
								<%
							}
						%>
						<div class="slides">
							<div id="slide1">
							
								<!-- COMENTARIOS -->
								<div class="row">
									<div class="col-xs-12">
										<div class="grid">
											<%
											int contadorComentarios = 0;
											for (Comentario comentario: listaComentarios) {
												%>
												<div class="col-xs-12 col-sm-6 col-md-4 grid-item">
													<div class="panel panel-default">
														<div class="panel-heading">
															<div class="row">
																<div class="col-xs-12">
																	<span class="pull-right capa-texto"><%= comentario.getUsuario().getNick() %></span>
																</div>
															</div>
														</div>
														<div class="panel-body">
															<span class="capa-texto">
																<%= comentario.getTexto() %>
															</span>
														</div>
														<%
														int numRespuestas = postService.getAllRespuestasPorComentario(comentario).size();
														if (numRespuestas > 0) {
														%>
														<div class="panel-footer">
															<div class="row">
																<div class="col-xs-12">
																	<a class="pull-right pointer get-respuestas" data-id="<%= comentario.getId() %>">
																		<span class="capa-texto">
																			<span class="badge"><%= numRespuestas %></span> Respuestas
																		</span>
																	</a>
																</div>
															</div>
														</div>
														<%
														}
														%>
														<!-- List group -->
													  	<ul class="list-group">
													  		<%
													  		if (isSessionInit) {
													  		%>
													  		<!-- SECCION COMENTARIO RESPONDER -->
													  		<li class="list-group-item pointer showModalComentario" data-comentariotype="responderComentario" data-id="<%= comentario.getId() %>" 
													  		data-toggle="modal" data-target="#modalComentario">
													  			<div class="capa-titulo text-center">
													  				<span style="font-size: 15px;">Responder</span>
													  			</div>
													  		</li>
													  		<!-- FIN SECCION COMENTARIO RESPONDER -->
													  		<%
													  		
													  			if (isADMIN || usuarioService.propietarioComentario(usuario, comentario)) {
													  				%>
													  				
													  		<!-- SECCION COMENTARIO OPCIONES -->
													  		<li class="list-group-item">
													  			<div class="row">
													  				<div class="col-xs-6 text-center">
															  			<button class="btn btn-icon btn-icon-black mostrarModalEditarComentario" title="Editar comentario" data-toggle="modal" data-target="#modalEditarComentario"
															  			data-id="<%=comentario.getId() %>" data-type="comentario">
																			<i class="fa fa-pencil-square-o"></i>
																		</button>
													  				</div>
													  				<div class="col-xs-6 text-center">
																		<button class="btn btn-icon btn-icon-black mostrarModalEliminarComentario" title="Eliminar comentario" data-toggle="modal" data-target="#modalEliminarComentario"
																		data-id="<%=comentario.getId()%>" data-tipocomment="eliminarComentario">
																			<i class="fa fa-trash-o"></i>
																		</button>
													  				</div>
													  			</div>
													  		</li>
													  		<!-- FIN SECCION COMENTARIO OPCIONES -->
													  				
													  				<%
													  			}
													  		
													  		}
													  		%>
													  		<!-- SECCION COMENTARIO INFO -->
														    <li class="list-group-item pointer comentario-popover"
														    data-toggle="popover" title="Fecha de creación" data-content="<%= MyUtil.getFechaFormateada(comentario.getFechaCreacion(), "dd-MM-yyyy HH:mm:ss") %>">
														    	<div class="row">
														    		<div class="col-xs-12">
																    	<span class="glyphicon glyphicon-info-sign pull-left"></span>
																    	<span class="pull-right informacion-comentario">
																    		Informacion sobre el comentario
																    	</span>
														    		</div>
														    	</div>
														    </li>
														    <!-- FIN SECCION COMENTARIO INFO -->
													  	</ul>
													</div>
												</div>
												<%
											}
											%>
										</div>
									</div>
								</div>
								<!-- FIN COMENTARIOS -->
								
							</div>
							<div id="slide2">
								<!-- EXPANDIR COMENTARIO -->
								<div id="expandirComentario">
									<div class="row margin-bot-2">
										<div class="col-xs-10 col-xs-offset-1 col-sm-5">
											<button id="botonVolverAtras" class="btn btn-pixel"><span class="glyphicon glyphicon-arrow-left"></span> Todos comentarios</button>
										</div>
									</div>
									<div id="contenidoExpandirComentario"></div>
								</div>
								<!-- FIN EXPANDIR COMENTARIO -->
							</div>
						</div>
						
					</div> <!-- FIN CAPA DERECHA -->
					
				</div> <!-- FIN CAPA GENERAL -->
			</div> <!-- FIN CAPA MOLDE GENERAL -->
		</div> <!-- FIN CONTAINER-FLUID -->
	</div> <!-- FIN FONDO -->
	
	
	<!-- MODAL CONFIRMAR DELETE -->
	<div id="modalEliminar" class="modal fade" role="dialog">
	  <div class="modal-dialog">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title text-center">¿Estas seguro de eliminar la entrada?</h4>
	      </div>
	      <div class="modal-footer">
	      	<div class="col-sm-4 col-sm-offset-1">
	      		<div class="form-group">
			      	<button type="button" class="btn btn-success btn-block" id="buttonConfirmarDelete">Si</button>
	      		</div>
	      	</div>
	      	<div class="col-sm-4 col-sm-offset-2">
	      		<div class="form-group">
			        <button type="button" class="btn btn-danger btn-block" data-dismiss="modal">No</button>
	      		</div>
	      	</div>
	      </div>
	    </div>
	
	  </div>
	</div> <!-- FIN MODAL CONFIRMAR DELETE -->
	
	<!-- MODAL EDITAR POST -->
	<div id="modalEditarPost" class="modal fade" role="dialog">
	  <div class="modal-dialog">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Edicion de entrada</h4>
	      </div>
	      <div class="modal-body">
	      	<label for="">Titulo</label>
	      	<div class="summernote" id="textBoxPostTitulo"></div>
	      	<label for="">Texto</label>
	      	<div class="summernote" id="textBoxPostTexto"></div>
	      </div>
	      <div class="modal-footer">
	      	<div class="col-sm-4 col-sm-offset-1">
	      		<div class="form-group">
			      	<button type="button" class="btn btn-success btn-block" id="buttonConfirmarEdicion">Si</button>
	      		</div>
	      	</div>
	      	<div class="col-sm-4 col-sm-offset-2">
	      		<div class="form-group">
			        <button type="button" class="btn btn-danger btn-block" data-dismiss="modal">No</button>
	      		</div>
	      	</div>
	      </div>
	    </div>
	
	  </div>
	</div> <!-- FIN MODAL EDITAR POST -->
	
	<!-- MODAL EDITAR COMENTARIO -->
	<div id="modalEditarComentario" class="modal fade" role="dialog">
	  <div class="modal-dialog">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Edicion de comentario</h4>
	      </div>
	      <div class="modal-body">
	      	<label for="">Texto</label>
	      	<div class="hidden" id="idComentarioModal"></div>
	      	<div class="hidden" id="tipoComentarioModal"></div>
	      	<div class="summernote" id="textBoxComentarioTexto"></div>
	      </div>
	      <div class="modal-footer">
	      	<div class="col-sm-4 col-sm-offset-1">
	      		<div class="form-group">
			      	<button type="button" class="btn btn-success btn-block" id="buttonConfirmarEdicionComentario">Si</button>
	      		</div>
	      	</div>
	      	<div class="col-sm-4 col-sm-offset-2">
	      		<div class="form-group">
			        <button type="button" class="btn btn-danger btn-block" data-dismiss="modal">No</button>
	      		</div>
	      	</div>
	      </div>
	    </div>
	
	  </div>
	</div> <!-- FIN MODAL EDITAR COMENTARIO -->
	
	<!-- MODAL CONFIRMAR DELETE COMENTARIO -->
	<div id="modalEliminarComentario" class="modal fade" role="dialog">
	  <div class="modal-dialog">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title text-center">¿Estas seguro de eliminar este comentario?</h4>
	      </div>
	      <div class="modal-body hidden">
	      	<div id="modalEliminarIdComment"></div>
	      	<div id="modalEliminarTipoComentario"></div>
	      </div>
	      <div class="modal-footer">
	      	<div class="col-sm-4 col-sm-offset-1">
	      		<div class="form-group">
			      	<button type="button" class="btn btn-success btn-block" id="buttonConfirmarDeleteComentario">Si</button>
	      		</div>
	      	</div>
	      	<div class="col-sm-4 col-sm-offset-2">
	      		<div class="form-group">
			        <button type="button" class="btn btn-danger btn-block" data-dismiss="modal">No</button>
	      		</div>
	      	</div>
	      </div>
	    </div>
	
	  </div>
	</div> <!-- FIN MODAL CONFIRMAR DELETE COMENTARIO -->
	
	<!-- MODAL COMENTAR -->
	<div id="modalComentario" class="modal fade" role="dialog">
		<!-- MODAL DIALOG -->
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title text-center">Escribe tu comentario</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-xs-12">
							<div class="hidden">
								<div id="comentarioType"></div>
								<div id="idComentario"></div>
							</div>
							<div id="comentarioTexto" class="summernote"></div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="col-sm-4 col-sm-offset-4">
						<div class="form-group">
							<button type="button" class="btn btn-success btn-block" id="botonEnviarComentario">Enviar comentario</button>
						</div>
					</div>
				</div>
			</div>
			<!-- FIN MODAL CONTENT -->
		</div>
		<!-- FIN MODAL DIALOG -->
	</div>
	<!-- FIN MODAL COMENTAR -->
</body>
</html>