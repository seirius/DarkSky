<%@page import="darksky.modelo.service.UsuarioService"%>
<%@page import="darksky.modelo.bean.Usuario"%>
<%@page import="darksky.util.MyUtil"%>
<%@page import="darksky.controlador.helpers.SessionHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%
SessionHandler sessionHandler = new SessionHandler(request);
UsuarioService usuarioService = new UsuarioService();

boolean isSessionInit = sessionHandler.isInit();
String perfilUsuario = request.getParameter("usuario");


if (MyUtil.isEmpty(perfilUsuario)) {
	throw new Exception("Perfil de usuario no puede ser nulo");
}

Usuario usuario = null;
usuario = usuarioService.getUsuario(perfilUsuario);

if (usuario == null) {
	throw new Exception("No existe usuario con este nick");
}

String thisPath = "/DarkSky/perfil?usuario=" + perfilUsuario;
%>
<title>Perfil de <%= usuario.getNick() %></title>

<!-- SCRIPT -->
<script>
	var thisPath = "<%= thisPath %>";
	var isSessionInit = <%= isSessionInit %>;
	var perfilNick = "<%= usuario.getNick() %>";
</script>

<!-- Imports -->
<%@ include file="/includes/imports/default-imports.jsp"%>
<link rel="stylesheet" href="/DarkSky/resources/my_css/crear-cuenta.css" />
<link rel="stylesheet" href="/DarkSky/resources/my_css/perfil.css" />
<script src="/DarkSky/resources/my_script/perfil.js"></script>
<script src="/DarkSky/resources/my_script/AjaxCall.js"></script>
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
				<div id="capa-general-borde">
					<div id="capa-general">
						<div class="container">
							<div class="row">
								<div class="col-xs-12">
								
									<div class="row">
										<div class="col-xs-12">
											<div class="nick-titulo text-center">
												<img src="<%= usuario.getAvatar().getImagen().getUrl() %>" alt="Avatar" width="50" />
												<span><%= usuario.getNick() %></span>
											</div>
										</div>
									</div>
									
									<!-- NOMBRE -->
									<div class="row margin-top-1">
										<div class="col-xs-12">
											<div class="input-group">
												<input type="text" name="" class="form-control" id="usuarioNombre" data-oldvalue="<%= usuario.getNombre() %>" value="<%= usuario.getNombre() %>" disabled/>
												<span class="input-group-btn">
													<button type="button" class="btn btn-warning id_editarCampo" data-target="#usuarioNombre"><span class="glyphicon glyphicon-pencil"></span> Nombre</button>
													<button type="button" class="btn btn-success id_guardarCambios hidden" data-method="modificarNombre" data-target="#usuarioNombre" data-loading-text="<span class='glyphicon glyphicon-refresh glyphicon-refresh-animate'></span> Guardando">Guardar</button>
												</span>
											</div>
										</div>
									</div>
									
									<!-- EMAIL -->
									<div class="row margin-top-1">
										<div class="col-xs-12">
											<div class="input-group">
												<input type="text" name="" class="form-control" id="usuarioEmail" data-oldvalue="<%= usuario.getEmail() %>" value="<%= usuario.getEmail() %>" disabled/>
												<span class="input-group-btn">
													<button type="button" class="btn btn-warning id_editarCampo" data-target="#usuarioEmail"><span class="glyphicon glyphicon-pencil"></span> Email</button>
													<button type="button" class="btn btn-success id_guardarCambios hidden" data-method="modificarEmail" data-target="#usuarioEmail" data-loading-text="<span class='glyphicon glyphicon-refresh glyphicon-refresh-animate'></span> Guardando">Guardar</button>
												</span>
											</div>
										</div>
									</div>
									
									<!-- PASSWORD -->
									<div class="row margin-top-1">
										<div class="col-xs-12">
											<div class="input-group" id="groupoPWActual">
												<input type="password" name="" class="form-control" id="usuarioPassword" placeholder="Introduce la contraseña actual"/>
												<span class="input-group-btn">
													<button type="button" class="btn btn-warning" id="comprobarPassword" data-method="comprobarPassword"><span class="glyphicon glyphicon-pencil"></span> Contraseña</button>
												</span>
											</div>
											<div class="input-group hidden" id="groupoPWNuevo">
												<input type="password" name="" id="usuarioNuevaPassword" class="form-control" placeholder="Introduce la nueva contraseña" />
												<span class="input-group-btn">
													<button type="button" class="btn btn-success" id="cambiarPassword" data-method="cambiarPassword" data-loading-text="<span class='glyphicon glyphicon-refresh glyphicon-refresh-animate'></span> Guardando">Guardar</button>
												</span>
											</div>
										</div>
									</div>
									
								</div>
							</div>
						</div>
					</div>
				</div>
				
			</div> <!-- FIN CAPA MOLDE GENERAL -->
		</div> <!-- FIN CONTAINER-FLUID -->
	</div> <!-- FIN FONDO -->
</body>
</html>