$(document).ready(function () {
	var formCrearCuenta = $("#formCrearCuenta").bootstrapValidator({
		feedbackIcons: {
			valid: "glyphicon glyphicon-ok",
			invalid: "glyphicon glyphicon-remove", 
			validating: "glyphicon glyphicon-refresh"
		},
		fields: {
			nick: {
				validators: {
					notEmpty: {
						message: "Usuario requerido"
					},
	    			stringLength: {
	    				min: 3,
	    				message: "Tiene que tener al menos 3 caracteres"
	    			},
	    			remote: {
	    				url: urls.COMPROBAR_DUPLICADO,
	    				data: {
	    					tipo: "nick",
	    					valor: function() {
	    						return $("#nick").val();
	    					}
	    				},
	    				type: 'POST',
	    				delay: 2000,     // Send Ajax request every 2 seconds
	    				message: "Nick ya existe"
	    			}
				}
			},
			nombre: {
				validators: {
					stringLength: {
						min: 5,
						message: "Tiene que tener al menos 5 caracteres"
					}
				}
			},
			email: {
				message: "Formato de email incorrecto",
				validators: {
					notEmpty: {
						message: "Email requerido"
					},
	    			remote: {
	    				url: urls.COMPROBAR_DUPLICADO,
	    				data: {
	    					tipo: "email",
	    					valor: function() {
	    						return $("#email").val();
	    					}
	    				},
	    				type: 'POST',
	    				delay: 2000,     // Send Ajax request every 2 seconds
	    				message: "Usuario con este correo ya existe"
	    			}
				}
			},
			password: {
        		validators: {
        			notEmpty: {
        				message: "Contraseña requerida"
        			},
        			stringLength: {
        				min: 8,
        				message: "Contraseña tiene que tener al menos 8 caracteres"
        			}
        		}
        	},
        	passwordR: {
        		validators: {
        			notEmpty: {
        				message: "Repetir contraseña requerida"
        			},
        			identical: {
        				field: "password",
        				message: "Constraseñas no coinciden"
        			}
        		}
        	},
		}
	});
	
	formCrearCuenta.on("success.form.bv", function (e) {
		//Prevenir el submit de form
		e.preventDefault();
		
		//Enviar el formulario
		
		var $form = $(e.target),
		fv    = $form.data('formValidation');
		$.ajax({
			url: $form.attr('action'),
			type: 'POST',
			data: $form.serialize(),
			success: function(data) {
				if (isRetOk(data)) {
					$("#formCrearCuenta").addClass("hidden");
					$("#creadoExito").removeClass("hidden");
				}
			}
		});
		
	});
	
	$("#sesionAlt").click(function () {
		$(".capa-login").toggle("slide", {direction: "right"});

		if ($(".capa-avatar").css("right") === "15px") {
			$(".capa-avatar").animate({right: "+=295px"});
		}
		else {
			$(".capa-avatar").animate({right: "15px"});
		}
	});
	
	$("#selectable").selectable({
		filter: "> li",
		selecting: function(event, ui) {
            if( $(".ui-selected, .ui-selecting").length > 1){
                  $(ui.selecting).removeClass("ui-selecting");
            }
        },
        selected: function( event, ui ) {
        	$("#itemName").val($(".ui-selected").find("img").attr("alt"));
        }
	});
});