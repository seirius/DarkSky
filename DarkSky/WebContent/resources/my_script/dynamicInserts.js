/**
 * Crea un modal de bootstrap dinamico, recordar que este solo se crea pero no se muestra
 * 
 * @param header Contenido del titulo, por defecto llevara el boton cerrar
 * @param bodyContent Contenido del body, por defecto, vacion
 * @param footer Contenido del footer
 * @returns Devuelve el div del modal para poder usarlo, en caso necesario, en JavaScript mas adelante
 */
function launchModal(header, bodyContent, footer) {
	var body = $("body");
	
	var divModal        = $("<div class='modal fade' role='dialog'></div>");
	var divModalDialog  = $("<div class='modal-dialog'></div>");
	var divModalContent = $("<div class='modal-content'></div>");
	var divModalHeader  = $("<div class='modal-header'></div>");
	var buttonClose     = $("<button type='button' class='close' data-dismiss='modal' aria-label='Cerrar'><span aria-hidden='true'>&times;</span></button>")
	var divModalBody    = $("<div class='modal-body'></div>");
	var divModalFooter  = $("<div class='modal-footer'></div>");
	
	divModalHeader.append(buttonClose);
	if (header) {
		divModalHeader.append(header);
	}
	divModalContent.append(divModalHeader);
	
	if (bodyContent) {
		divModalBody.append(bodyContent);
	}
	divModalContent.append(divModalBody);
	
	if (footer) {
		divModalFooter.append(footer);
		divModalContent.append(divModalFooter);
	}
	
	divModalDialog.append(divModalContent);
	divModal.append(divModalDialog);
	
	body.append(divModal);
	
	divModal.on('hidden.bs.modal', function (e) {
		divModal.remove();
	});

	return divModal;
}