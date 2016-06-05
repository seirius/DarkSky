$(document).ready(function () {
	$("#editarMenu").click(function () {
		$("body").css("cursor", "wait");
		
		var modal = launchModal($("<div class='text-center titulo-sesion'><span>Editar menu</span></div>"));
		
		var fabric = new AjaxFabric();
		var ajaxCall = fabric.getNewAjaxCall(urls.MENU_CONTROL, { type: "getMenus" }, "call1");
		var ajaxHtml = fabric.getNewAjaxCall(urls.EDITAR_MENU, {}, "call2");
		
		var sharedFun = function (fabric) {
			var menus = ajaxCall.data.CONTENIDO;
			var html  = ajaxHtml.data;
			modal.find(".modal-body").append(html);
			cargarFuncionalidadMenuEditar(modal, menus);
			$("body").css("cursor", "auto");
		};
		
		fabric.addSharedFunction(sharedFun, ["call1", "call2"]);
		fabric.executeAll();
	});
	
});

function cargarFuncionalidadMenuEditar(modal, menus) {
	
	var lista = $("#listaMenuEditar");
	var i = menus.length - 1;
	for (i; i >= 0; i--) {
		var nombreMenu = menus[i].nombreMenu;
		
		var item = $("<li class='list-group-item'></li>");
		
		item.append(nombreMenu);
		var botonEditar = getButton("btn btn-default", "<span class='glyphicon glyphicon-repeat .color-251758'></span>");
		botonEditar.data("target", "#collapsibleContenidoMenuEditar");
		botonEditar.data("menu", menus[i]);
		botonEditar.infoMenu = menus[i];
		var botonEliminar = getButton("btn btn-default", "<span class='glyphicon glyphicon-remove .color-red'></span>");
		botonEliminar.data("nombremenu", nombreMenu);
		var btnGroup = getButtonGrp("btn-group-sm pull-right", [botonEditar, botonEliminar]);
		
		botonEliminar.click(function () {
			var parameters = {
				type: "eliminar",
				nombreMenu: $(this).data("nombremenu")
			};
			var ajaxEliminar = new AjaxCall(urls.MENU_CONTROL, parameters);
			
			var funSuccess = function () {
				window.document.location = thisPath;
			};
			
			ajaxEliminar.execute(funSuccess);
		});
		
		botonEditar.click(function () {
			$($(this).data("target")).collapse("show");
			var menu = $(this).data("menu");
			$("#nombreMenuEditar").val(menu.nombreMenu);
			$("#nombreMenuEditar").prop("title", "Si se cambia el nombre se creara un nuevo menu con este nombre");
			$("#nombreMenuEditar").data("toggle", "tooltip");
			$("#nombreMenuEditar").tooltip({
				placement: "bottom"
			});
			$("#urlEditar").val(menu.url);
			$("#nombreMenuSuperiorMenuEditar").val(menu.nombreMenuSuperior);
			$("#nivelMenuEditar").val(menu.nivel);
			$("#ordenMenuEditar").val(menu.orden);
		});
		
		item.append(btnGroup);
		lista.prepend(item);
	}
	
	$("#collapsibleContenidoMenuEditar").on("shown.bs.collapse", function () {
		$("#botonAddMenuEditar").addClass("hidden");
		$("#botonAceptarMenuEditar").removeClass("hidden");
	});
	
	$("#collapsibleContenidoMenuEditar").on("hidden.bs.collapse", function () {
		$("#botonAddMenuEditar").removeClass("hidden");
		$("#botonAceptarMenuEditar").addClass("hidden");
		$("#nombreMenuEditar").val("");
		$("#urlEditar").val("");
		$("#nombreMenuSuperiorMenuEditar").val("");
		$("#nivelMenuEditar").val(0);
		$("#ordenMenuEditar").val(0);
	});
	
	modal.modal("show");
	
	$("#botonAceptarMenuEditar").click(function () {
		var boton = $(this);
		boton.button("loading");
		var parametros = {
			type: 			    "addMenu",
			nombreMenu: 	    $("#nombreMenuEditar").val(),
			nombreMenuSuperior: $("#nombreMenuSuperiorMenuEditar").val(),
			url: 				$("#urlEditar").val(),
			nivel: 				$("#nivelMenuEditar").val(),
			orden: 				$("#ordenMenuEditar").val()
		};
		
		var ajaxAddMenu = new AjaxCall(urls.MENU_CONTROL, parametros);
		
		var funSuccess = function () {
			if (this.ajaxCall.errorCode == 0) {
				window.document.location = thisPath;
			} else {
				boton.button("reset");
			}
		};
		
		ajaxAddMenu.execute(funSuccess);
	});
}