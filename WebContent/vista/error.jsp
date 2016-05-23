<%@page import="darksky.exceptions.ExceptionDAO"%>
<%@page import="darksky.controlador.helpers.SessionHandler"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@page import="darksky.modelo.bean.Post"%>
<%@page import="java.util.List"%>
<%@page import="darksky.modelo.service.PostService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error</title>

<%
String thisPath = "/DarkSky/error";

SessionHandler sHandler = new SessionHandler(request);
%>

<!-- SCRIPT -->
<script>
	var thisPath = "<%= thisPath %>";
</script>

<!-- Imports -->
<%@ include file="/includes/imports/default-imports.jsp"%>
</head>
<body>
	<!-- TITULO -->
	<%@ include file="/includes/elements/titulo.jsp"%>


	<!-- CONTENIDO -->
	<div id="fondo">
		<div class="container-fluid">
			<div class="col-sm-12" id="capa-molde-general">

				<!-- CAPA GENERAL -->
				<div id="capa-general">
					
					<div class="col-xs-12">
						<div class="row">
							<div class="col-xs-6 col-xs-offset-3">
								<div class="alert alert-danger">
									<strong><span class="glyphicon glyphicon-exclamation-sign"></span></strong> Ups! Ha ocurrido algo inesperado.
								</div>
							</div>
						</div>
						
						<%
						if (exception instanceof ExceptionDAO) {
							ExceptionDAO e = (ExceptionDAO) exception;
							if (!e.getMessage().equals("Error interno")) {
								%>
								<div class="row">
									<div class="col-xs-6 col-xs-offset-3">
										<div class="alert alert-warning">
											<strong><span class="glyphicon glyphicon-warning-sign"></span></strong> <%= e.getMessage() %>
										</div>
									</div>
								</div>
								<%
							}
						}
						%>
						
						<%
						if (sHandler.isInit()) {
							String rol = sHandler.getUsuario().getRol().getRol();
							if (rol.equals("ADMIN") || rol.equals("GOD")) {
						%>
						
						<div class="row">
							<%
							if (exception.getMessage() != null) {
							%>
							<div class="col-xs-12">
								<div class="panel panel-default">
									<div class="panel-heading pointer" data-toggle="collapse" data-target="#exceptionMsg">
										Mensaje de la excepción
									</div>
									<div class="collapse" id="exceptionMsg">
										<div class="panel-body">
											<%= exception.getMessage() %>
										</div>
									</div>
								</div>
							</div>
							<%
							}
							%>
							<div class="col-xs-12">
								<div class="panel panel-default">
									<div class="panel-heading pointer" data-toggle="collapse" data-target="#exceptionTrace">
										Rastro de la excepción
									</div>
									<div class="collapse" id="exceptionTrace">
										<div class="panel-body">
											<%
											StringWriter errors = new StringWriter();
											exception.printStackTrace(new PrintWriter(errors));
											%>
											<%= errors.toString() %>
										</div>
									</div>
								</div>
							</div>
						</div>
						<%
							}
						}
						%>
						
						<div class="row">
							<div class="col-xs-4 col-xs-offset-4">
								<div class="form-group">
									<button class="btn btn-pixel btn-block clickable" data-href="/DarkSky/pagina-principal">Volver al principio</button>
								</div>
							</div>
						</div>
					</div>
					
				</div> <!-- FIN CAPA GENERAL -->
			</div> <!-- FIN CAPA MOLDE GENERAL -->
		</div> <!-- FIN CONTAINER-FLUID -->
	</div> <!-- FIN FONDO -->
</body>
</html>