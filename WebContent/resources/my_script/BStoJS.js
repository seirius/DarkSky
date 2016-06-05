function getDiv(divClass) {
	var div = $("<div></div>");
	if (typeof divClass == "string") {
		div.addClass(divClass);
	}
	
	return div;
}

function getRowCol(colClass) {
	var row = getDiv("row");
	var col = getDiv();
	
	if (typeof colClass == "undefined") {
		colClass = "col-xs-12";
	}
	
	col.addClass(colClass);
	
	row.append(col);
	
	var returnElements = {
			item: row,
			row: row,
			col: col
	};
	
	return returnElements;
}

function getFormGroup(labelText, inputType) {
	if (typeof inputType != "string") {
		inputType = "text";
	}
	
	var formGroup = $("<div class='form-group'></div>");
	var label = $("<label></label>");
	if (typeof labelText == "string") {
		label.html(labelText);
	}
	var input = $("<input type='" + inputType + "' class='form-control' />");
	formGroup.append(label);
	formGroup.append(input);
	
	var returnElements = {
		item: formGroup,
		formGroup: formGroup,
		label: label,
		input: input
	};
	
	return returnElements;
}

function getOption(value, text) {
	if (!isString(value)) {
		value = "";
	}
	if (isUndefined(text)) {
		text = "";
	}
	var option = $("<option></option>");
	option.val(value);
	option.html(text);
	return option;
}

function getSelectGroup(labelText, options) {
	if (!isString(labelText)) { labelText = ""; }
	if (Object.prototype.toString.call(options) !== "[object Array]") { options = []; }
	
	var formGroup = getDiv("form-group");
	var label = $("<label></label>");
	var select = $("<select class='form-control'></select>");
	label.html(labelText);
	select.append(options);
	formGroup.append(label);
	formGroup.append(select);
	
	var returnElements = {
		item: formGroup,
		label: label,
		select: select
	};
	
	return returnElements;
}

function getButton(buttonClass, contenido) {
	if (!isString) { buttonClass = ""; }
	if (isUndefined(contenido)) { contenido = ""; }
	
	var button = $("<button type='button'></button>");
	button.addClass(buttonClass);
	button.html(contenido);
	
	return button;
}

function getButtonGrp(grpClass, buttons) {
	if (!isString(grpClass)) { grpClass = ""; }
	if (Object.prototype.toString.call(buttons) !== "[object Array]") { buttons = []; }
	
	var grp = $("<div class='btn-group' role='group'></div>");
	grp.addClass(grpClass);
	grp.append(buttons);
	
	return grp;
}

















