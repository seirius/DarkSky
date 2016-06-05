function Urls() {
	this.ROOT_PATH = "/DarkSky/";
	
	//---------------------------------------------------------------
	//Paginas
	//---------------------------------------------------------------
	this.CREAR_CUENTA     = this.ROOT_PATH + "app/crear-cuenta";
	this.CREAR_ENTRADA    = this.ROOT_PATH + "app/crear-entrada";
	this.ERROR            = this.ROOT_PATH + "error";
	this.FORO             = this.ROOT_PATH + "foro";
	this.PAGINA_PRINCIPAL = this.ROOT_PATH + "pagina-principal";
	this.POST             = this.ROOT_PATH + "post";
	
	//---------------------------------------------------------------
	//Servlets AJAX calls
	//---------------------------------------------------------------
	this.COMENTAR            = this.ROOT_PATH + "comentar";
	this.COMPROBAR_DUPLICADO = this.ROOT_PATH + "comprobar-duplicado";
	this.EDITAR_POST         = this.ROOT_PATH + "editar-post";
	this.ELIMINAR_COMENTARIO = this.ROOT_PATH + "eliminar-comentario";
	this.ELIMINAR_POST       = this.ROOT_PATH + "eliminar-post";
	this.SEGUIR              = this.ROOT_PATH + "seguir";
	this.VOTAR               = this.ROOT_PATH + "votar";
	this.INICIAR_SESION      = this.ROOT_PATH + "iniciar-sesion";
	this.USUARIO_RESPONSE    = this.ROOT_PATH + "usuario-response";
	this.MENU_CONTROL        = this.ROOT_PATH + "menu-control";
	
	//---------------------------------------------------------------
	//JSPs AJAX calls
	//---------------------------------------------------------------
	this.RESPUESTAS_COMENTARIO = this.ROOT_PATH + "elements/respuestas-comentario";
	
	//---------------------------------------------------------------
	//HTML AJAX calls
	//---------------------------------------------------------------
	this.EDITAR_MENU = this.ROOT_PATH + "includes/elements/injection_html/editar-menu.html";
	this.MODAL_ERROR = this.ROOT_PATH + "includes/elements/injection_html/modal-error.html";
}

var urls = new Urls();