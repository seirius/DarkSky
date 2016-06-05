package darksky.controlador.servlets;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import darksky.controlador.helpers.SessionHandler;
import darksky.modelo.bean.CategoriaPost;
import darksky.modelo.bean.Imagen;
import darksky.modelo.bean.Post;
import darksky.modelo.service.PostService;

@MultipartConfig
public class CrearEntrada extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private int maxFileSize = 0;

	public CrearEntrada() {
		super();
	}
	
	public void init() {
		try {
			maxFileSize = Integer.parseInt(getServletContext().getInitParameter("maxFileSize"));
		} catch(NumberFormatException e) {
			this.log("Error al convertir la variable del contexto 'maxFileSize' a entero", e);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionHandler sessionHandler = new SessionHandler(request);
		
		if (!sessionHandler.isInit()) {
			response.sendRedirect("www.google.es");
			return;
		}
		
		String filePath = getServletContext().getInitParameter("filePath");
		Part uploadedFile = request.getPart("imagen-post-subir"); 
		
		String inputCategoria = request.getParameter("selectCategoria");
		String inputTitulo = request.getParameter("tituloPost");
		String inputTexto = request.getParameter("textoPost");
		String enPortada = "N";
		
		if (request.getParameter("enPortada") != null) {
			enPortada = request.getParameter("enPortada");
		}
		
		Imagen imagen = null;
		
		if (uploadedFile.getSize() > 0 && uploadedFile.getSize() < maxFileSize) {
			String fileName      = uploadedFile.getSubmittedFileName();
			String fileExtension = fileName.substring(fileName.length() - 4);
			if (fileExtension.equals(".jpg") || fileExtension.equals(".png") || fileExtension.equals(".gif")) {
				String finalUrl = filePath + File.separator + fileName;
				
				uploadedFile.write(finalUrl);
				
				imagen = new Imagen();
				imagen.setUrl("/DarkSky/data/post_img/"+fileName);
				
			} else {
				this.log("ERR: Solo se admiten formatos .jpg .png .gif o el tamaño de imagen excedido. |Creacion de nueva entrada|");
				return;
			}
		}
		
		CategoriaPost categoriaPost = new CategoriaPost(inputCategoria);
		
		Post post = new Post();
		post.setCategoriaPost(categoriaPost);
		post.setTitulo(inputTitulo);
		post.setTexto(inputTexto);
		post.setEnPortada(enPortada);
		post.setImagen(imagen);
		post.setUsuario(sessionHandler.getUsuario());
		
		PostService postService = new PostService();
		post = postService.insertarPost(post);
		
		response.sendRedirect(response.encodeRedirectURL("/DarkSky/post?idPost=" + post.getId()));
	}
	

}
