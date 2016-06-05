$(document).ready(function () {
	$("#imagen-post-subir").fileinput({
		browseClass: "btn btn-primary btn-block",
		allowedFileExtensions: ["jpg", "png", "gif"],
        showCaption: false,
        showRemove: false,
        showUpload: false
	});
	
	var formCrearEntrada = $("#formCrearEntrada").bootstrapValidator({
		feedbackIcons: {
			valid: 	    "glyphicon glyphicon-ok",
			invalid:    "glyphicon glyphicon-remove", 
			validating: "glyphicon glyphicon-refresh"
		},
		fields: {
			selectCategoria: {
				validators: {
					notEmpty: {
						message: "Hay que seleccionar al menos una categoria"
					}
				}
			},
			tituloPost: {
				validators: {
					notEmpty: {
						message: "Titulo requerido"
					},
					stringLength: {
						min: 2,
						message: "Tiene que tener al menos 2 caracteres"
					}
				}
			},
			textoPost: {
				validators: {
					notEmpty: {
						message: "Texto requerido"
					},
					stringLength: {
						min: 5,
						message: "Tiene que tener al menos 5 caracteres"
					}
				}
			}
		}
	});
});