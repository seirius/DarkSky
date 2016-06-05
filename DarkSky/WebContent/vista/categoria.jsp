<%@page import="darksky.modelo.bean.CategoriaPost"%>
<%@page import="darksky.modelo.service.PostService"%>
<%@page import="darksky.modelo.bean.Post"%>
<%@page import="java.util.List"%>
<%@page import="darksky.controlador.helpers.SessionHandler"%>
<%@page import="darksky.util.MyUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%
String categoria = request.getParameter("categoria");
if (MyUtil.isEmpty(categoria)) {
	throw new Exception("Categoria no definida");
}

SessionHandler sessionHandler = new SessionHandler(request);
boolean isSessionInit = sessionHandler.isInit();
%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= categoria %></title>

<%
String thisPath = "/DarkSky/categoria?categoria=" + categoria;
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
					</div> <!-- FIN CAPA IZQUIERDA -->
					
					
					<!-- CAPA DERECHA -->
					<div id="capa-derecha">
						<div class="bloque-categoria">
							<!-- TITULO CATEGORIA -->
							<div class="titulo-categoria">
								<span><%= categoria %></span>
							</div>
							
							<%
							PostService postS = new PostService();
							CategoriaPost categoriaPost = new CategoriaPost();
							categoriaPost.setCategoria(categoria);
							List<Post> posts = postS.getPostPorCategoria(categoriaPost, 0, "fechaCreacion");
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
										if (isSessionInit) {
											int valorVoto = postS.getVotoPorPost(post.getId(), sessionHandler.getUsuario().getNick());
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
					</div> <!-- FIN CAPA DERECHA -->
					
				</div> <!-- FIN CAPA GENERAL -->
			</div> <!-- FIN CAPA MOLDE GENERAL -->
		</div> <!-- FIN CONTAINER-FLUID -->
	</div> <!-- FIN FONDO -->
</body>
</html>