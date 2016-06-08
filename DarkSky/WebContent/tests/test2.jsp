<!DOCTYPE html>
<%@page import="darksky.controlador.helpers.SessionHandler"%>
<%@page import="darksky.modelo.bean.Respuesta"%>
<%@page import="darksky.modelo.bean.Post"%>
<%@page import="darksky.modelo.bean.Comentario"%>
<%@page import="darksky.modelo.service.PostService"%>
<%@page import="darksky.modelo.bean.EventoNotificacion"%>
<%@page import="darksky.modelo.bean.Notificacion"%>
<%@page import="java.util.List"%>
<%@page import="darksky.modelo.service.SeguirService"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<%@ include file="/includes/imports/default-imports.jsp"%>
<script src="/DarkSky/resources/my_script/AjaxCall.js"></script>
<link rel="stylesheet" href="/DarkSky/resources/elements/font-awesome/css/font-awesome.css" />

<style>
</style>

</head>

<%

SeguirService seguirService = new SeguirService();

PostService postService = new PostService();

SessionHandler sessionHandler = new SessionHandler(request);

boolean isSessionInit = sessionHandler.isInit();

%>

<body>

	<script>
		var isSessionInit = <%= isSessionInit %>;
		
		$(document).ready(function () {
			$("#buttonTest").click(function () {
				var ajaxFabric = new AjaxFabric();
				
				var parametros = {
					type: "getTextoComentario",
					tipoComment: "comentario",
					idComment: 5
				};
				
				var ajax1 = ajaxFabric.getNewAjaxCall(urls.COMENTAR, parametros, "ajax1");
				var ajax2 = ajaxFabric.getNewAjaxCall(urls.COMENTAR, parametros, "ajax2");
				var ajax3 = ajaxFabric.getNewAjaxCall(urls.COMENTAR, parametros, "ajax3");
				var ajax4 = ajaxFabric.getNewAjaxCall(urls.COMENTAR, parametros, "ajax4");
				
				ajax1.functionToEx = function () {
					console.log("function to ex: ajax1");
				};
				
				ajax2.functionToEx = function () {
					console.log("function to ex: ajax2");
				};
				
				ajax3.functionToEx = function () {
					console.log("function to ex: ajax3");
				};
				
				ajax4.functionToEx = function () {
					console.log("function to ex: ajax4");
				};
				
				ajaxFabric.addSharedFunction(function () {
					console.log("shared function for: ajax1, ajax2");
				}, ["ajax1", "ajax2"]);
				
				ajaxFabric.addSharedFunction(function () {
					console.log("shared function for: ajax1, ajax3");
				}, ["ajax1", "ajax3"]);
				
				ajaxFabric.addSharedFunction(function () {
					console.log("shared function for: ajax1, ajax2, ajax3");
				}, ["ajax1", "ajax2", "ajax3"]);
				
				ajaxFabric.executeAll();
			});
		});
	</script>

	<div class="container">
		<button class="btn btn-primary" id="buttonTest">Test</button>
	</div>

</body>
</html>