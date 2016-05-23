<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pagina Principal</title>

<%
String thisPath = "/DarkSky/pagina-principal";
%>

<!-- SCRIPT -->
<script>
	var thisPath = "<%= thisPath %>";
</script>

<!-- Imports -->
<%@ include file="/includes/imports/default-imports.jsp"%>
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


	<!-- CONTENIDO -->
	<div id="fondo">
		<div class="container-fluid">
			<div class="col-sm-12" id="capa-molde-general">

				<!-- CAPA GENERAL -->
				<div id="capa-general">

					<!-- CAPA IZQUIEDA -->
					<div id="capa-izquierda">
					</div> <!-- FIN CAPA IZQUIERDA -->
					
					
					<!-- CAPA DERECHA -->
					<div id="capa-derecha">

					</div> <!-- FIN CAPA DERECHA -->
					
				</div> <!-- FIN CAPA GENERAL -->
			</div> <!-- FIN CAPA MOLDE GENERAL -->
		</div> <!-- FIN CONTAINER-FLUID -->
	</div> <!-- FIN FONDO -->
</body>
</html>