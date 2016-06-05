(function ($, viewport) {
	$(document).ready(function () {
		
		var currentSize = viewport.current();
		if (currentSize == "xs") {
			$(".capa-avatar").click(function () {
				
				if (!isSessionInit) {
					var header = $("<div class='text-center titulo-sesion'><span>Iniciar Sesion</span></div>");
					var form = $("<form></form>");
					var row = $("<div class='row'></div>")
					var colXS12 = $("<div class='col-xs-12'></div>");
					
					var formGroup = $("<div class='form-group'></div>");
					var inputUsuario = $("<input type='text' class='form-control' id='usuarioXS' name='usuario' placeholder='Usuario'>");
					
					var formGroupPW = $("<div class='form-group'></div>");
					var inputPassword = $("<input type='password' class='form-control' id='passwordXS' name='password' placeholder='Contraseña'>");
					
					var textCenter = $("<div class='col-xs-10 col-xs-offset-1'></div>");
					var buttonLogin = $("<button type='submit' class='btn btn-pixel btn-block'>Iniciar Sesión</button>");
					
					var linkWrapper = $("<div class='col-xs-12 text-center margin-top-1 margin-bot-1'></div>");
					var link = $("<a  href='/DarkSky/crear-cuenta'>Crear cuenta</a>");
					
					formGroup.append(inputUsuario);
					colXS12.append(formGroup);
					
					formGroupPW.append(inputPassword);
					colXS12.append(formGroupPW);
					
					textCenter.append(buttonLogin);
					colXS12.append(textCenter);
					
					linkWrapper.append(link);
					colXS12.append(linkWrapper);
					
					row.append(colXS12);
					form.append(row);
					
					
					var modal = launchModal(header, form);
					modal.modal("show");
					
					form.submit(function (e) {
						e.preventDefault();
						
						$.ajax({
							url: urls.INICIAR_SESION,
							type: "POST",
							data: {
								usuario: inputUsuario.val(),
								password: inputPassword.val()
							},
							success: function(data) {
								if (isRetOk(data)) {
									var estado = data.CONTENIDO.ESTADO;
									if (estado) {
										window.location.replace(thisPath);
									} else {
										modalError("Error de identificacion", "Usuario y/o contraseña, incorrectos.");
									}
								}
							}
						});
					});
				} else {
					var tituloSesion = $("<div class='text-center titulo-sesion'></div>");
					var tituloNick = $("<span></span>");
					tituloNick.append(nick);
					tituloNick.addClass("pointer");
					tituloNick.data("href", "/DarkSky/perfil?usuario=" + nick);
					tituloSesion.append(tituloNick);
					
					var textCenter = $("<div class='text-center margin-bot-1'></div>");
					var buttonLogout = $("<button class='btn btn-pixel btn-pixel-blue clickable' data-href='/DarkSky/cerrar-sesion?backUrl="+thisPath+"'>Cerrar sesion</button>");
					buttonLogout.click(function() {
						window.document.location = $(this).data("href");
					});
					textCenter.append(buttonLogout);
					
					var modal = launchModal(tituloSesion, textCenter);
					modal.modal("show");
					
					tituloNick.click(function () {
						window.document.location = $(this).data("href");
					});
				}
			});
		} else {
			$("#avatarImg").popover().popover("disable");
			
			$(".capa-avatar").click(function () {
				$(".capa-login").toggle("slide", {direction: "right"});
				
				if ($(this).css("right") === "15px") {
					$(this).animate({right: "+=295px"});
				}
				else {
					$(this).animate({right: "15px"});
				}
			});
			
			$("#formSesion").submit(function (e) {
				e.preventDefault();
				
				$.ajax({
					url: urls.INICIAR_SESION,
					type: 'POST',
					data: {
						usuario:  $("#usuario").val(),
						password: $("#password").val()
					},
					success: function(data) {
						if (isRetOk(data)) {
							var estado = data.CONTENIDO.ESTADO;
							if (estado) {
								window.location.replace(thisPath);
							} else {
								$(".initError").removeClass("hidden");
							}
						}
					}
				});
			});
		}
		
	});
})(jQuery, ResponsiveBootstrapToolkit);


function popoverAvatar(msg, time) {
	var avatar = $("#avatarImg");
	if (!avatar.next("div.popover").length) {
		avatar.popover("enable");
		avatar.attr("data-content", msg);
		avatar.popover("show");
		avatar.popover("disable");
		setTimeout(function () {
			avatar.popover("hide");
		}, time);
	}
}