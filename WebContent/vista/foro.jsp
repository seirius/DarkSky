<%@page import="darksky.util.MyUtil"%>
<%@page import="darksky.exceptions.ExceptionDAO"%>
<%@page import="darksky.controlador.helpers.SessionHandler"%>
<%@page import="darksky.modelo.bean.Post"%>
<%@page import="darksky.modelo.bean.CategoriaPost"%>
<%@page import="java.util.List"%>
<%@page import="darksky.modelo.service.PostService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Foro</title>

<%
SessionHandler sHandler = new SessionHandler(request);

String thisPath = "/DarkSky/foro";
boolean isSessionInit = sHandler.isInit();
%>

<!-- SCRIPT -->
<script>
	var thisPath = "<%= thisPath %>";
	var isSessionInit = <%= isSessionInit %>;
</script>

<!-- Imports -->
<link rel="stylesheet" href="/DarkSky/resources/elements/font-awesome/css/font-awesome.css" />
<%@ include file="/includes/imports/default-imports.jsp"%>
<link rel="stylesheet" href="/DarkSky/resources/my_css/foro.css" />
<script src="/DarkSky/resources/elements/masonry/masonry.js"></script>
<script src="/DarkSky/resources/my_script/foro.js"></script>

</head>
<body>

<!-- LOGIN -->
<jsp:include page="/includes/elements/sesion.jsp">
	<jsp:param name="backUrl" value="<%=thisPath%>" />
</jsp:include>

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
		<div>
			<div id="capa-molde-general">

				<!-- CAPA GENERAL -->
				<div id="capa-general">

					<!-- CAPA IZQUIEDA -->
					<div id="capa-izquierda">
						<%
						if (isSessionInit) {
							%>
							<!-- NOTIFICACIONES -->
							<%@ include file="/includes/elements/notificaciones.jsp"%>
							<div class="row margin-top-3">
								<div class="col-xs-12">
									<div class="bloque-boton-crear">
										<button class="btn btn-pixel btn-pixel-blue btn-block clickable" data-href="/DarkSky/crear-entrada">Crear Entrada</button>
									</div>
								</div>
							</div>
							<%
						}
						%>
					</div>
					
					
					<!-- CAPA DERECHA -->
					<div id="capa-derecha">
						<div class="grid">
						
							<%
							PostService postS = new PostService();
							List<CategoriaPost> categorias = postS.getCategorias();
							
							for (CategoriaPost categoria: categorias) {
								
								%>
							<div class="grid-item">
								<div class="bloque-categoria">
									<!-- TITULO CATEGORIA -->
									<div class="titulo-categoria">
										<span class="clickable" data-href="/DarkSky/categoria?categoria=<%= categoria.getCategoria() %>"><%= categoria.getCategoria() %></span>
									</div>
									
									<%
									List<Post> posts = postS.getPostPorCategoria(categoria, 5, "fechaCreacion");
									for (Post post: posts) {
										%>
		
		
									<!-- Post -->
									<div class="contenedor-lista-post">
									
										<!-- Hidden stuff -->
										<div class="hidden">
											<span class="postID"><%= post.getId() %></span>
										</div>
		
										<!-- Contenedor votos -->
										<div class="bloque left">
											<div class="capa-votos">
												<div class="capa-contador-voto text-center">
													<span class="votosPostSpan"><%= post.getVotos() %></span>
												</div>
												
												<%
												String selectedPos = "";
												String selectedNeg = "";
												if (sHandler.isInit()) {
													int valorVoto = postS.getVotoPorPost(post.getId(), sHandler.getUsuario().getNick());
													if (valorVoto != -2) {
														if (valorVoto == 1) {
															selectedPos = "select-positivo";
														} else if (valorVoto == -1) {
															selectedNeg = "select-negativo";
														}
													}
												}
												%>
												<div class="capa-voto text-center">
													<i class="fa fa-arrow-up pointer voto-positivo <%= selectedPos %>"></i>
												</div>
												<div class="capa-voto text-center">
													<i class="fa fa-arrow-down pointer voto-negativo <%= selectedNeg %>"></i>
												</div>

											</div>
										</div>
		
										<!-- Contenedor visitas -->
										<div class="bloque right datos-post">
											<div class="text-center">
												<div style="height: 17px;"></div>
												<span><%= post.getVisitas() %></span><br> <span class="texto-comtit">Visitas</span>
											</div>
										</div>
		
										<!-- Contenedor comentarios -->
										<div class="bloque right datos-post">
											<div class="text-center">
												<div style="height: 17px;"></div>
												<span><%= postS.getComentarios(post).size() %></span><br> <span class="texto-comtit">Comentarios</span>
											</div>
										</div>
		
										<!-- Contenedor texto -->
										<div class="bloque-texto">
		
											<!-- Contenedor titulo -->
											<div class="bloque-texto-titulo">
												<a href="/DarkSky/post?idPost=<%= post.getId() %>"><%= post.getTitulo() %></a>
											</div>
		
											<!-- Espacio entre titulo y descripcion -->
											<div style="height: 15px;"></div>
		
											<!-- Contenedor descripcion -->
											<div class="bloque-texto-descripcion">
												<span style="font-size: 0.95rem">
													Creado por 
													<span class="clickable" data-href="/DarkSky/perfil?usuario=<%= post.getUsuario().getNick() %>">
														<%= post.getUsuario().getNick() %>
													</span> 
													<span> y enviado el <%= MyUtil.getFechaFormateada(post.getFechaCreacion(), "dd-MM-yyyy HH:mm:ss") %></span>
												</span>
											</div>
										</div>
									</div>
		
									<%
										}
									%>
									
								</div>							
							</div> <!-- Fin Grid-item -->
								<%
							}
							%>
						</div> <!-- Fin Grid -->
									
					</div> <!-- Fin Capa derecha -->
					
				</div> <!-- Fin capa-general -->
			</div> <!-- Fin Capa molde general -->
		</div> <!-- Fin Container-fluid -->
	</div> <!-- Fin fondo -->
</body>
</html>