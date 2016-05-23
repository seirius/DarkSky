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
		
		var parameters = {
			item1: "item1",
			item2: "item2"
		};
		
		var functionToEx = function(data) {
			console.log(data);
		}
		
		$(document).ready(function () {
			$("#buttonTest").click(function () {
				var ajaxCall = new AjaxCall("/DarkSky/TestServlet", parameters, functionToEx);
				ajaxCall.execute();
			});
		});
	</script>

	<div class="container">
		<button class="btn btn-primary" id="buttonTest">Test</button>
	</div>

</body>
</html>