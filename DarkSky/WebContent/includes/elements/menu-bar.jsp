
<%@page import="darksky.modelo.service.UsuarioService"%>
<%@page import="darksky.controlador.helpers.SessionHandler"%>
<%@page import="darksky.modelo.bean.Menu"%>
<%@page import="java.util.List"%>
<%@page import="darksky.modelo.service.MenuService"%>
<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
		</div>
		<div class="collapse navbar-collapse" id="myNavbar">
			<ul class="nav navbar-nav">
				<%
				SessionHandler sessionHandlerAux = new SessionHandler(request);
				boolean showMenuEdit = false;
				if (sessionHandlerAux.isInit()) {
					showMenuEdit = new UsuarioService().permisoEditarMenu(sessionHandlerAux.getUsuario());
				}
				
				MenuService menuService = new MenuService();
				List<Menu> menus = menuService.getMenusPorNivel(new Long(0));
				for (Menu menu: menus) {
					List<Menu> subMenus = menuService.getMenusPorMenu(menu.getNombreMenu());
					String url = menu.getUrl();
					String nombre = menu.getNombreMenu();
					if (subMenus.size() == 0) {
						%>
						<li><a href="<%= url %>"><%= nombre %></a></li>
						<%	
					} else {
						%>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">
								<%= nombre %>
								<span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
							<%
							if (nombre.equals("Foro")) {
								%>
								  <li><a href="<%= menu.getUrl() %>">Todos</a></li>
								<%
							}
							
							for (Menu submenu: subMenus) {
							%>
								<li><a href="<%= submenu.getUrl() %>"><%= submenu.getNombreMenu() %></a></li>
							<%
							}
							%>
					        </ul>
						</li>
						<%
					}
				}
				%>
				<!-- 
				<li><a href="/DarkSky/pagina-principal">Pagina principal</a></li>
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						Foro
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="/DarkSky/foro">Todos</a></li>
			          <li><a href="/DarkSky/categoria?categoria=General">General</a></li>
			          <li><a href="/DarkSky/categoria?categoria=Bugs">Bugs</a></li>
			          <li><a href="/DarkSky/categoria?categoria=Offtopic">Offtopic</a></li>
			        </ul>
				</li>
				 -->
			</ul>
			<%
			if (showMenuEdit) {
			%>
			<ul class="nav navbar-nav navbar-right">
				<li>
					<a id="editarMenu" href="#"><span class="glyphicon glyphicon-option-horizontal"></span></a>
				</li>
			</ul>
			<%
			}
			%>
		</div>
	</div>
</nav>
