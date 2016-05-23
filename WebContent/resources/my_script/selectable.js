$(document).ready(function () {
	$("#selectable").selectable({
		filter: "> li",
		selecting: function(event, ui) {
            if( $(".ui-selected, .ui-selecting").length > 1){
                  $(ui.selecting).removeClass("ui-selecting");
            }
        }
	});
});