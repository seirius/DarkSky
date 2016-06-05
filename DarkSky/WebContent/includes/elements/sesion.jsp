<%@page import="darksky.modelo.bean.Item"%>
<%@page import="darksky.modelo.bean.Usuario"%>
<%@page import="darksky.controlador.helpers.SessionHandler"%>
<%
SessionHandler sessHandler = new SessionHandler(request);


String nick = "";
if (sessHandler.isInit()) {
	nick = sessHandler.getUsuario().getNick();
}
%>

<script type="text/javascript">
var nick = "<%= nick %>";
</script>

<!-- LOGIN -->
<div class="capa-login">
	
	<%
	Item avatar = null;
	if (sessHandler.isInit()) {
		
		Usuario usuario = sessHandler.getUsuario();
		avatar = usuario.getAvatar();
		%>
		<!-- TITULO LOGIN -->
		<div class="text-center titulo-sesion">
			<span class="clickable" data-href="/DarkSky/perfil?usuario=<%= usuario.getNick() %>"><%= usuario.getNick() %></span>
		</div>
		<div class="text-center margin-bot-1">
			<button class="btn btn-pixel btn-pixel-blue clickable" data-href="<%= "/DarkSky/cerrar-sesion?backUrl="+request.getParameter("backUrl") %>">Cerrar sesion</button>
		</div>
		<%
	} else {
		%>
	<!-- FORM LOGIN -->
	<form id="formSesion">

		<!-- TITULO LOGIN -->
		<div class="text-center titulo-sesion">
			<span>Iniciar Sesion</span>
		</div>
		
		<!-- CUERPO -->
		<div class="col-xs-12">
			<div class="form-group hidden initError">
				<div class="alert alert-danger">
					Usuario y/o contraseña, incorrectos.
				</div>
			</div>
			<div class="form-group">
				<input type="text" class="form-control" id="usuario" name="usuario" placeholder="Usuario" />
			</div>
			<div class="form-group">
				<input type="password" class="form-control" id="password" name="password" placeholder="Contrseña" />
			</div>
			<div class="text-center">
				<button type="submit" class="btn btn-pixel">Iniciar sesion</button>
			</div>
			<div class="text-center margin-top-1 margin-bot-1">
				<a href="/DarkSky/crear-cuenta">Crear cuenta</a>
			</div>
		</div> <!-- FIN CUERPO -->
	</form> <!-- FIN FORM LOGIN -->
		<%
	}
	%>
	
</div> <!-- FIN LOGIN -->

<!-- AVATAR -->
<div class="capa-avatar pointer">
	<%
	String avatarUrl = "/DarkSky/assets/img/avatares/rawAvatar.png"; 
	if (avatar != null) {
		avatarUrl = avatar.getImagen().getUrl();
	}
	%>
	
	<img src="<%= avatarUrl %>" id="avatarImg" alt="avatar" data-toggle="popover" data-content="Hola" data-placement="bottom" />
</div> <!-- FIN AVATAR -->












