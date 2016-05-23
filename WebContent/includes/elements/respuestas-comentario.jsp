<%@page import="darksky.modelo.bean.Usuario"%>
<%@page import="darksky.modelo.service.UsuarioService"%>
<%@page import="darksky.controlador.helpers.SessionHandler"%>
<%@page import="darksky.modelo.bean.Respuesta"%>
<%@page import="darksky.util.MyUtil"%>
<%@page import="darksky.modelo.bean.Comentario"%>
<%@page import="java.util.ArrayList"%>
<%@page import="darksky.util.interfaces.Comment"%>
<%@page import="java.util.List"%>
<%@page import="darksky.modelo.service.PostService"%>


<%
SessionHandler sessionHandler = new SessionHandler(request);
Integer idComentario = Integer.parseInt(request.getParameter("idComentario"));

UsuarioService usuarioService = new UsuarioService();
PostService postService = new PostService();
Comentario comentario = postService.getComentario(idComentario);
List<Comment> listComments = postService.getAllRespuestasPorComentario(comentario);

boolean isAdmin = false;
Usuario usuario = null;
if (sessionHandler.isInit()) {
	usuario = sessionHandler.getUsuario();
	isAdmin = usuarioService.permisoEditarForo(usuario);
}
%>

<div class="panel panel-black">
	<div class="panel-heading">
		<div class="row">
			<div class="col-xs-12">
				<div class="capa-texto">
					Usuario <%= comentario.getUsuario().getNick() %>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<span class="capa-texto"><%= comentario.getTexto() %></span>
			</div>
		</div>
	</div>
	<div class="panel-body">
		<%
		for (Comment comment: listComments) {
			Respuesta respuesta = (Respuesta) comment;
			String nickRespondido = "";
			if (respuesta.getComentario() != null) {
				nickRespondido = respuesta.getComentario().getUsuario().getNick();
			} else if (respuesta.getRespuesta() != null) {
				nickRespondido = respuesta.getRespuesta().getUsuario().getNick();
			}
			
			%>
			<div class="row">
				<div class="col-xs-12">
				
					<div class="panel panel-default">
						<div class="panel-body">
						
							<div class="row">
								
								
								<!-- CAPA TEXTO -->
								<div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
									<p class="capa-texto"><%= comment.getTexto() %></p>
								</div>
								<!-- FIN CAPA TEXTO -->
							
								<!-- CAPA INFO -->
								<div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
									<div class="panel panel-info capa-texto">
										<div class="panel-heading">
											<div class="text-center">
												<%= comment.getUsuario().getNick() %>
											</div>
										</div>
										<ul class="list-group">
										  	<li class="list-group-item">
												Respuesta a <%= nickRespondido %>
											</li>
											<%
											if (sessionHandler.isInit()) {
												if (isAdmin || usuarioService.propietarioRespuesta(usuario, respuesta)) {
											%>	
											<li class="list-group-item">
										  		<div class="row">
									  				<div class="col-xs-6 text-center">
											  			<button class="btn btn-icon btn-icon-black mostrarModalEditarComentario" title="Editar comentario" data-toggle="modal" data-target="#modalEditarComentario"
											  			data-id="<%=respuesta.getId() %>" data-type="respuesta">
															<i class="fa fa-pencil-square-o"></i>
														</button>
									  				</div>
									  				<div class="col-xs-6 text-center">
														<button class="btn btn-icon btn-icon-black mostrarModalEliminarComentario" title="Eliminar comentario" data-toggle="modal" data-target="#modalEliminarComentario"
														data-id="<%=respuesta.getId()%>" data-tipocomment="eliminarRespuesta">
															<i class="fa fa-trash-o"></i>
														</button>
									  				</div>
									  			</div>
										  	</li>
										  	<%
												}
											}
										  	%>
										</ul>
										<div class="panel-footer">
											<%= MyUtil.getFechaFormateada(comment.getFechaCreacion(), "dd-MM-yyyy HH:mm:ss") %>
										</div>
									</div>
								</div>
								<!-- FIN CAPA INFO -->
								
							</div>
							
						</div>
						
						<%
						if (sessionHandler.isInit()) {
						%>
						<div class="panel-footer">
							<div class="row">
								<div class="col-sm-4">
									<button class="btn btn-primary btn-block showModalComentario" data-comentariotype="responderRespuesta" data-id="<%= respuesta.getId() %>" 
							  		data-toggle="modal" data-target="#modalComentario">Responder</button>
								</div>
							</div>
						</div>
						<%
						}
						%>
						
					</div>
				</div>
			</div>
			<%
		}
		%>
	
	</div>
</div>
