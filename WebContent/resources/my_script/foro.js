var idPost;

$(document).ready(function () {
	$('.grid').masonry({
		itemSelector: '.grid-item'
	});
	
	
	$(".voto-positivo, .voto-negativo").click(function () {
		idPost = $(this).closest(".contenedor-lista-post").find(".postID").html();
	});
	
	manageVotosButtons();

});

