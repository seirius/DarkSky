<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title>Dark Sky</title>

<!-- Imports -->
<%@ include file="/includes/imports/default-imports.jsp" %>
<link rel="stylesheet" href="/DarkSky/resources/my_css/index.css" />
<link rel="stylesheet" href="/DarkSky/resources/elements/slick/slick.css" />
<link rel="stylesheet" href="/DarkSky/resources/elements/slick/slick-theme.css" />
<script src="/DarkSky/resources/elements/slick/slick.js"></script>

</head>
<body>
		<%@ include file="/includes/elements/titulo.jsp" %>
	<div class="container-fluid">
	
		
		<!-- Titulo -->
		<div class="row">
			<div class="text-center">
				<h1 style="color:white;">Dark Sky</h1>
			</div>
		</div>
		
		
		<!-- Contenido -->
		<div class="row">
		
		
			<!-- Bloque Video -->
			<div class="bloque margin-top-4">
				<div class="col-sm-10 col-sm-offset-1">
					<div class="embed-responsive embed-responsive-16by9">
						<iframe class="embed-responsive-item" src="https://www.youtube.com/embed/2o3Bot4x6rU"></iframe>
					</div>
				</div>
			</div>
			
			
			<!-- Bloque Capturas -->
			<div class="bloque margin-top-4">
				<div class="col-sm-10 col-sm-offset-1">
					<div class="slick">
						<div class="boxes">
							<img src="/DarkSky/assets/img/capturas_juego/cap_1.png" alt="captura_1" />
						</div>
						<div class="boxes">
							<img src="/DarkSky/assets/img/capturas_juego/cap_2.png" alt="captura_2" />
						</div>
						<div class="boxes">
							<img src="/DarkSky/assets/img/capturas_juego/cap_3.png" alt="captura_3" />
						</div>
						<div class="boxes">
							<img src="/DarkSky/assets/img/capturas_juego/cap_4.png" alt="captura_4" />
						</div>
					</div>			
				</div>
			</div>
			
			
			<!-- Bloque Descripcion -->
			<div class="bloque margin-top-4">
				<div class="col-sm-10 col-sm-offset-1">
					<div class="jumbotron">
						<h2>¿De que va el juego?</h2>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem et sint commodi
						 numquam a laboriosam maxime debitis unde. Expedita voluptatum sit esse fuga 
						 iste nisi non aliquam voluptatem facilis sequi veritatis amet quaerat quidem 
						 neque vitae vero officia quod dignissimos ea consequatur quisquam sapiente magnam 
						 laboriosam. Numquam ab excepturi aliquid.</p>
						 <div class="text-right">
							 <button type="button" class="btn btn-primary btn-lg">Prueba la DEMO ya!</button>
						 </div>
					</div>
				</div>
			</div>
			
			
		</div>
		
		
	</div>
	<script src="/DarkSky/resources/my_script/index.js"></script>
</body>
</html>