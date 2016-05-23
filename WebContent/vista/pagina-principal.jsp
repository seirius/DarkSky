<%@page import="darksky.controlador.helpers.SessionHandler"%>
<%@page import="darksky.modelo.bean.Post"%>
<%@page import="java.util.List"%>
<%@page import="darksky.modelo.service.PostService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pagina Principal</title>

<%
String thisPath = "/DarkSky/pagina-principal";

SessionHandler sessionHandler = new SessionHandler(request);

boolean isSessionInit = sessionHandler.isInit();
%>

<!-- SCRIPT -->
<script>
	var thisPath = "<%= thisPath %>";
	var isSessionInit = <%= isSessionInit %>;
</script>

<!-- Imports -->
<link rel="stylesheet" href="/DarkSky/resources/elements/font-awesome/css/font-awesome.css" />
<%@ include file="/includes/imports/default-imports.jsp"%>
<link rel="stylesheet" href="/DarkSky/resources/my_css/pagina-principal.css" />
<script src="/DarkSky/resources/elements/masonry/masonry.js"></script>
<script src="/DarkSky/resources/my_script/pagina-principal.js"></script>
</head>
<body>
	
	<!-- LOGIN -->
	<jsp:include page="/includes/elements/sesion.jsp">
		<jsp:param name="backUrl" value="<%=thisPath%>" />
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
					</div>
					
					
					<!-- CAPA DERECHA -->
					<div id="capa-derecha">
						<div class="grid">
						
							<%
							PostService postS = new PostService();
							List<Post> noticias = postS.getNoticias();
							for (Post post: noticias) {
								
								if (post.getImagen() != null) {
								%>
								
							<div class="grid-item con-imagen">
								<button class="btn-gba boton-leer-noticia clickable" data-href="/DarkSky/post?idPost=<%= post.getId() %>">Ver</button>
								<div class="capa-imagen">
									<img src="<%=post.getImagen().getUrl()%>" alt="<%= post.getTitulo() %>" class="img-noticia" />
								</div>
								<div class="capa-titulo">
									<span><%= post.getTitulo() %></span>
								</div>
								<div class="capa-texto">
									<p><%= post.getTexto() %></p>
								</div>
							</div>
									
								<%
								} else {
								%>
								
							<div class="grid-item">
								<button class="btn-gba boton-leer-noticia clickable" data-href="/DarkSky/post?idPost=<%= post.getId() %>">Ver</button>
								<div class="capa-titulo">
									<span><%= post.getTitulo() %></span>
								</div>
								<div class="capa-texto">
									<p><%= post.getTexto() %></p>
								</div>
							</div>
								
								<%
								}
							}
							%>
						</div>
					</div>


				</div>
			</div>
		</div>
	</div>
</body>
</html>