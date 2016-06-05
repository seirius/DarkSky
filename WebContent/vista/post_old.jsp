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

String thisPath = "/DarkSky/vista/post.jsp?idPost=" + idPost;

Usuario usuario = sessionHandler.getUsuario();
boolean esPropietarioPost = false;
if (sessionHandler.isInit()) {
	usuario = sessionHandler.getUsuario();
	esPropietarioPost = usuarioService.propietarioPost(usuario, post);
}
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= post.getTitulo() %></title>

<!-- SCRIPT -->
<script>
	var thisPath = "<%= thisPath %>";
	var sessionInit = <%= sessionHandler.isInit() %>;
	var idPost = <%= post.getId() %>;
</script>

<!-- Imports -->
<%@ include file="/includes/imports/default-imports.jsp"%>
<link rel="stylesheet" href="/DarkSky/resources/my_css/post.css" />
<link rel="stylesheet" href="/DarkSky/resources/elements/font-awesome/css/font-awesome.css" />
<link rel="stylesheet" href="/DarkSky/resources/elements/summernote/summernote.css" />
<script src="/DarkSky/resources/my_script/post.js"></script>
<script src="/DarkSky/resources/elements/summernote/summernote.js"></script>
<link rel="stylesheet" href="/DarkSky/resources/my_css/tipo-background-ayuda.css" />
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


	<!-- CONTENIDO -->
	<div id="fondo">
		<div class="container-fluid">
			<div class="col-sm-12" id="capa-molde-general">

				<!-- CAPA GENERAL -->
				<div id="capa-general">

					<!-- CAPA IZQUIEDA -->
					<div id="capa-izquierda">
						<%
						String mostrarSeguir = "hidden";
						String seguirBoton = "", dejarSeguirBoton = "";
						if (sessionHandler.isInit()) {
							if (!esPropietarioPost) {
								mostrarSeguir = "";
								if (seguirService.siguiendoPost(usuario.getNick(), idPost)) {
									seguirBoton = "hidden";
								} else {
									dejarSeguirBoton = "hidden";
								}
							}
						}
						%>
						<div class="row">
							<div class="col-sm-12 text-center">
								<div class="capa-boton-seguir <%= mostrarSeguir %>">
									<button type="button" class="btn btn-default <%= dejarSeguirBoton %>" id="dejarSeguirBoton" data-loading-text="Espere...">Dejar de seguir entrada</button>
									<button type="button" class="btn btn-default <%= seguirBoton %>" id="seguirBoton" data-loading-text="Espere...">Seguir entrada</button>
								</div>
							</div>
						</div>
						
						<div class="capa-post-ayuda">
							<div id="titulo-post-ayuda" class="text-center tipo-0 pointer"><%= post.getTitulo() %></div>
							
							<!-- COMENTARIO AYUDA -->
							<%
							for (Comentario comentario: listaComentarios) {
								%>
								<div data-href="#comentario-link-<%= comentario.getId() %>-<%= comentario.getNivel() %>" class="capa-comentario-ayuda tipo-1 clickable padding-right-5" style="width: 100%">
									<div class="capa-texto-comentario-ayuda"><%= comentario.getUsuario().getNick() %></div>
									
									<%
									List<Respuesta> listaRespuestasComentario = ComentarioHandler.getRespuestasPorComentario(comentario, listaComment);
									for (Respuesta respuesta: listaRespuestasComentario) {
										%>
										<div data-href="#comentario-link-<%= respuesta.getId() %>-<%= respuesta.getNivel() %>" class="capa-comentario-ayuda tipo-2 clickable right margin-bot-2 padding-right-5" style="width: 95%">
											<div class="capa-texto-comentario-ayuda"><%= respuesta.getUsuario().getNick() %></div>
											
											<%
											List<Respuesta> listaRespuestasRespuesta = ComentarioHandler.getRespuestasPorRespuesta(respuesta, listaComment);
											for (Respuesta res: listaRespuestasRespuesta) {
												%>
												<div data-href="#comentario-link-<%= res.getId() %>-<%= res.getNivel() %>" class="capa-comentario-ayuda tipo-1 clickable right margin-bot-2" style="width: 90%">
													<div class="capa-texto-comentario-ayuda"><%= res.getUsuario().getNick() %></div>
												</div>
												<%
											}
											%>
										</div>
										<%
									}
									%>
								</div>
								<hr>
								<%
							}
							%>
						</div> <!-- FIN CAPA POST AYUDA -->
					</div> <!-- FIN CAPA IZQUIERDA -->
					
					
					<!-- CAPA DERECHA -->
					<div id="capa-derecha">
						<div class="bloque-post">

							<!-- Contenedor votos -->
							<div class="capa-votos left">
								<div class="hidden"><%= post.getId() %></div>
								<div class="capa-contador-voto text-center">
									<span><%=post.getVotos()%></span>
								</div>
								<div class="capa-voto text-center">
									<i class="fa fa-arrow-up pointer voto-positivo"></i>
								</div>
								<div class="capa-voto text-center">
									<i class="fa fa-arrow-down pointer voto-negativo"></i>
								</div>
							</div> <!-- FIN CAPA VOTOS -->
							
							<%
							if (post.getImagen() != null) {
							%>
							<div class="capa-imagen right text-center">
								<img class="clickable" data-href="<%= post.getImagen().getUrl() %>" src="<%= post.getImagen().getUrl() %>" alt="imagen post" />
							</div>
							<%
							}
							%>
							
							<div class="capa-titulo">
								<%
								if (sessionHandler.isInit()) {
									if (usuarioService.permisoEditarForo(usuario) || esPropietarioPost) {
									%>
										<button class="btn btn-icon" title="Eliminar entrada" data-toggle="modal" data-target="#modalEliminar">
											<i class="fa fa-trash-o"></i>
										</button>
										<button class="btn btn-icon" title="Editar entrada" data-toggle="modal" data-target="#modalEditarPost">
											<i class="fa fa-pencil-square-o"></i>
										</button>
									<%
										
									}
								}
								%>
								
								<div class="pull-right">
								
								
								</div>
								
								
								
								<span id="spanTitulo"><%=post.getTitulo()%></span>
							</div>
							
							<div class="capa-texto margin-top-2">
								<span id="spanTexto"><%=post.getTexto()%></span>
							</div>
							
							<div class="capa-datos">
								Creado por <%= post.getUsuario().getNick() %> el <%= post.getFechaCreacion() %>
							</div>
							
							<%
							if (sessionHandler.isInit()) {
								%>
								<div class="boton-abrir-comentar">
									<button type="button" class="btn btn-pixel" data-toggle="collapse" data-target=".capa-comentar">Comentar</button>
								</div>
								<%
							}
							%>
							
							
							
						</div> <!-- FIN BLOQUE POST -->
						
						<%
							if (sessionHandler.isInit()) {
								%>
								<div class="collapse capa-comentar">
									<div class="capa-comentar-padding">
										<form action="/DarkSky/comentar" method="POST">
											<input type="text" class="hidden" name="nick" value="<%= sessionHandler.getUsuario().getNick() %>" />
											<input type="text" class="hidden" name="idComment" value="<%= idPost %>" />
											<input type="text" class="hidden" name="backUrl" value="<%= thisPath %>" />
											<input type="text" class="hidden" name="type" value="0" />
											<textarea name="textoComentario" class="summernote"></textarea>
											<button type="submit" class="btn btn-pixel btn-pixel-blue">Enviar</button>
										</form>
									</div>
								</div> <!-- FIN CAPA COMENTAR -->
								<%
							}
						%>

							<%
							int contadorComentarios = 0;
							for (Comment comment: listaComment) {
								%>
								<div id="comentario-link-<%= comment.getId() %>-<%= comment.getNivel() %>" class="comentario" style="width: <%= 99 - comment.getNivel() * 4 %>%">
									<div class="bloque-comentario">
										<div class="row">
											<div class="col-xs-3">
												<div class="capa-datos-comentario">
													Por <%= comment.getUsuario().getNick() %>
												</div>
											</div>
											<div class="col-xs-9">
												<%
												boolean showButtons = false;
												String tipoComment = "";
												
												if (comment instanceof Comentario) {
													tipoComment = "Comentario";
												} else {
													tipoComment = "Respuesta";
												}
												
												if (sessionHandler.isInit()) {
													if (usuarioService.permisoEditarForo(usuario)) {
														showButtons = true;
													} else {
														if (comment instanceof Comentario) {
															showButtons = usuarioService.propietarioComentario(usuario, (Comentario) comment);
														} else if (comment instanceof Respuesta) {
															showButtons = usuarioService.propietarioRespuesta(usuario, (Respuesta) comment);
														}
													}
													
													if (showButtons) {
												%>
													<button type="button" class="btn btn-icon pull-right" title="Editar comentario" 
														onClick='rellenarEdicionComentario(<%= contadorComentarios %>)'
														data-toggle="modal" data-target="#modalEditarComentario">
														<i class="fa fa-pencil-square-o"></i>
													</button>
													<button class="btn btn-icon pull-right" title="Eliminar comentario" onClick='enviarContador(<%= contadorComentarios %>)'
														data-toggle="modal" data-target="#modalEliminarComentario">
														<i class="fa fa-trash-o"></i>
													</button>
												<%
													}
												}
												%>
											</div>
										</div>
										<div class="hidden" id="idComentario<%= contadorComentarios %>"><%= comment.getId() %></div>
										<div class="hidden" id="tipoComentario<%= contadorComentarios %>"><%= tipoComment %></div>
										<div class="capa-comentario-texto" id="textoComentario<%= contadorComentarios %>">
											<%= comment.getTexto() %>
										</div>
										<%
										if (sessionHandler.isInit()) {
										%>
										<span class="pointer responder-toggle" data-toggle="collapse" 
											data-target="#responder-respuesta-<%= comment.getId()%>-<%= comment.getNivel() %>">Responder</span>	
										<%
										}
										%>
										<div class="capa-datos-comentario right">
											Publicado el <%= comment.getFechaCreacion() %>
										</div>
									</div>
									<%
									if (sessionHandler.isInit()) {
										String type = "1";
										if (comment.getNivel() > 0) {
											type = "2";
										}
										%>
										<div class="collapse" id="responder-respuesta-<%= comment.getId()%>-<%= comment.getNivel() %>">
											<div class="capa-comentar-padding">
												<form action="/DarkSky/comentar" method="POST">
													<input type="text" class="hidden" name="nick" value="<%= sessionHandler.getUsuario().getNick() %>" />
													<input type="text" class="hidden" name="idComment" value="<%= comment.getId() %>" />
													<input type="text" class="hidden" name="backUrl" value="<%= thisPath %>" />
													<input type="text" class="hidden" name="type" value="<%= type %>" />
													<textarea name="textoComentario" class="summernote"></textarea>
													<button type="submit" class="btn btn-pixel btn-pixel-blue">Enviar</button>
												</form>
											</div>
										</div> <!-- FIN CAPA COMENTAR -->
										<%
									}
									%>
								</div> <!-- FIN BLOQUE COMENTARIOS -->
								<%
								contadorComentarios++;
							}
							%>
						
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
	      	<div id="idComentarioModal" class="hidden"></div>
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
	      <div class="modal-footer">
	      	<div class="col-sm-4 col-sm-offset-1">
	      		<div class="form-group">
	      			<div class="hidden" id="contadorComentarioModal"></div>
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
</body>
</html>