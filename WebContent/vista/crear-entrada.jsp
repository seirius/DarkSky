<%@page import="darksky.modelo.bean.Usuario"%>
<%@page import="darksky.controlador.helpers.SessionHandler"%>
<%@page import="darksky.modelo.bean.CategoriaPost"%>
<%@page import="darksky.modelo.bean.Post"%>
<%@page import="java.util.List"%>
<%@page import="darksky.modelo.service.PostService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Crear nueva entrada</title>

<%
String thisPath = "/DarkSky/pagina-principal";

SessionHandler sessionHandler = new SessionHandler(request);

PostService postService = new PostService();
List<CategoriaPost> categorias = postService.getCategorias();
%>

<!-- SCRIPT -->
<script>
	var thisPath = "<%= thisPath %>";
</script>

<!-- Imports -->
<%@ include file="/includes/imports/default-imports.jsp"%>
<%@ include file="/includes/imports/file-input-imports.jsp"%>
<link rel="stylesheet" href="/DarkSky/resources/my_css/crear-entrada.css" />
<script src="/DarkSky/resources/my_script/crear-entrada.js"></script>
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
					<div id="capa-izquierda"></div>
					<!-- FIN CAPA IZQUIERDA -->


					<!-- CAPA DERECHA -->
					<div id="capa-derecha">

						<div class="grass">
							<div class="borde-grass">

								<!-- Titulo entrada nueva -->
								<div class="titulo-entrada-nueva text-center">
									<span>Crea nueva entrada</span>
								</div>

								<form action="/DarkSky/app/crear-entrada" method="POST" enctype="multipart/form-data" id="formCrearEntrada">
									<div class="row">
										<div class="col-lg-8">

											<!-- SELECCION CATEGORIA -->
											<div class="select-categoria col-lg-6">
												<div class="form-group">
													<label for="select-categoria" class="control-label">Categoria</label> 
													<select name="selectCategoria" id="selectCategoria" class="form-control">
														<option value="">- Elige una categoria -</option>
														<%
															for (CategoriaPost categoria : categorias) {
														%>
														<option value="<%=categoria.getCategoria()%>"><%=categoria.getCategoria()%></option>
														<%
															}
														%>
													</select>
												</div>
											</div>

											<!-- EN PORTADA -->
											<%
												if (sessionHandler.isInit()) {
													Usuario usuario = sessionHandler.getUsuario();
													if (usuario.getRol().getRol().equals("ADMIN") || usuario.getRol().getRol().equals("GOD")) {
											%>
											<div class="en-portada col-lg-6">
												<div class="form-group">
													<label class="control-label">¿En portada?</label>
													<div class="btn-group" data-toggle="buttons">
														<label class="btn btn-default">
															<input type="radio" name="enPortada" id="option1" value="S" autocomplete="off"/> Si
														</label>
														<label class="btn btn-default active">
															<input type="radio" name="enPortada" id="option2" value="N" autocomplete="off" checked/> No
														</label>
													</div>
												</div>
											</div>
											<%
													}
												}
											%>

											<!-- Titulo POST -->
											<div class="titulo-post col-lg-12">
												<div class="form-group">
													<label for="titulo-post" class="control-label">Titulo</label>
													<textarea type="text" class="form-control no-resize" id="titulo-post" name="tituloPost" rows="5" max-length="255"></textarea>
												</div>
											</div>

											<!-- Texto POST -->
											<div class="texto-post col-lg-12">
												<div class="form-group">
													<label for="texto-post" class="control-label">Texto</label>
													<textarea name="textoPost" id="texto-post" rows="10" class="form-control no-resize" max-length="2000"></textarea>
												</div>
											</div>

										</div> <!-- FIN DIV COL-LG-8 -->

										<div class="col-lg-4">

											<!-- SUBIDA IMAGEN -->
											<div class="subir-imagen col-xs-12">
												<div class="form-group">
													<label for="imagen-post-subir" class="control-label">Seleccion una imagen (opcional)</label> 
													<input id="imagen-post-subir" type="file" class="file-loading" name="imagen-post-subir" accept="image/*">
												</div>
											</div>
										</div>

									</div> <!-- FIN ROW -->
									
									<div class="row">
										<div class="col-lg-8">
											<div class="col-sm-6 col-sm-offset-3">
												<div class="form-group">
													<button type="submit" class="btn btn-success btn-lg btn-block" id="buttonCrearEntrada">Crear entrada</button>
												</div>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>

					</div> <!-- FIN CAPA DERECHA -->

				</div> <!-- FIN CAPA GENERAL -->
			</div> <!-- FIN CAPA MOLDE GENERAL -->
		</div> <!-- FIN CONTAINER-FLUID -->
	</div> <!-- FIN FONDO -->
</body>
</html>