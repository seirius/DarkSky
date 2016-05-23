<%@page import="java.util.List"%>
<%@page import="darksky.modelo.bean.Imagen"%>
<%@page import="darksky.modelo.bean.CategoriaItem"%>
<%@page import="darksky.modelo.bean.Item"%>
<%@page import="darksky.modelo.service.ItemService"%>
<%@page import="darksky.modelo.bean.Post"%>
<%@page import="darksky.modelo.service.PostService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Creacion de cuenta</title>

<%
String thisPath = "/DarkSky/pagina-principal";
%>

<!-- SCRIPT -->
<script>
	var thisPath = "<%= thisPath %>";
</script>

<!-- Imports -->
<%@ include file="/includes/imports/default-imports.jsp"%>
<link rel="stylesheet" href="/DarkSky/resources/elements/font-awesome/css/font-awesome.css" />
<link rel="stylesheet" href="/DarkSky/resources/my_css/crear-cuenta.css" />
<link rel="stylesheet" href="/DarkSky/resources/my_css/selectable.css" />
<script src="/DarkSky/resources/my_script/crear-cuenta.js"></script>
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
				<div id="capa-general-borde">
					<div id="capa-general">
					
						<form action="/DarkSky/app/crear-cuenta" id="formCrearCuenta" class="form-horizontal" method="POST">
							<div class="col-sm-12 col-lg-6">
							
								<!-- ERROR INTERNO -->
								<div class="col-sm-6 col-sm-offset-3">
									<div class="form-group hidden" id="errorInterno">
										<div class="alert alert-danger">Error interno, vuelve a intentarlo</div>
									</div>
								</div>
							
								<!-- NICK -->
								<div class="form-group">
									<label for="nick" class="control-label col-sm-3 col-lg-4">Usuario</label>
									<div class="col-sm-8 col-lg-8">
										<input type="text" class="form-control" name="nick" id="nick" maxLength="30"  />
									</div>
								</div>
								
								<!-- NOMBRE -->
								<div class="form-group">
									<label for="nombre" class="control-label col-sm-3 col-lg-4">Nombre (opcional)</label>
									<div class="col-sm-8 col-lg-8">
										<input type="text" class="form-control" name="nombre" id="nombre"  />
									</div>
								</div>
								
								<!-- EMAIL -->
								<div class="form-group">
									<label for="email" class="control-label col-sm-3 col-lg-4">Email</label>
									<div class="col-sm-8 col-lg-8">
										<input type="email" class="form-control" name="email" id="email"  />
									</div>
								</div>
								
								<!-- PASSWORD -->
								<div class="form-group">
									<label for="password" class="control-label col-sm-3 col-lg-4">Contraseña</label>
									<div class="col-sm-8 col-lg-8">
										<input type="password" class="form-control" name="password" id="password"  />
									</div>
								</div>
								
								<!-- REPEAT PASSWORD -->
								<div class="form-group">
									<label for="passwordR" class="control-label col-sm-3 col-lg-4">Repetir contraseña</label>
									<div class="col-sm-8 col-lg-8">
										<input type="password" class="form-control" name="passwordR" id="passwordR"  />
									</div>
								</div>
								
							</div>
							
							<div class="col-sm-12 col-lg-6">
								<div class="form-group">
									<label class="control-label col-sm-3 col-lg-4">Elige un avatar</label><br>
									
									<!-- AVATARS -->
									<div class="col-xs-offset-1 col-sm-offset-0 selectable-container col-sm-8 col-lg-8">
										<ol id="selectable">
									<%
									ItemService itemService = new ItemService();
									List<Item> items = itemService.getItemPorCategoria(new CategoriaItem("Avatar"));
									boolean isFirst = true;
									Item firstItem = new Item();
									for (Item item: items) {
										Imagen imagen = item.getImagen();
										String selected = "";
										if (isFirst) {
											firstItem = item;
											selected = "ui-selected";
											isFirst = false;
										}
										%>
										<li class="selectable-avatar <%= selected %> text-center">
											<img src="<%= imagen.getUrl() %>" alt="<%= item.getNombre() %>" />
										</li>
										<%
											}
										%>
										</ol>
									</div> <!-- FIN AVATARS -->
								</div>

							</div>
							
							<input type="text" class="hidden" name="itemName" id="itemName" value="<%= firstItem.getNombre() %>"/>

							<!-- SUBMIT -->
							<div class="col-sm-12 margin-bot-1 margin-top-1">
								<div class="text-center">
									<button type="submit" class="btn btn-pixel btn-pixel-lg">Enviar</button>
								</div>
							</div>

						</form> <!-- FIN FORM -->
						
						<div class="col-sm-6 col-sm-offset-3 hidden" id="creadoExito">
							<div class="alert alert-success text-center">
								<strong><i class="fa fa-check"></i> Exito!</strong> Inicia sesion aqui<br>
								<i class="fa fa-sign-in pointer" id="sesionAlt"></i>
							</div>
						</div>
						
					</div> <!-- FIN CAPA GENERAL -->
				</div> <!-- FIN CAPA GENERAL BORDE -->
			</div> <!-- FIN CAPA MOLDE GENERAL -->
		</div> <!-- FIN CONTAINER FLUID -->
	</div> <!-- FIN FONDO -->
</body>
</html>