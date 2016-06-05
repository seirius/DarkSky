package darksky.controlador.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import darksky.controlador.helpers.SessionHandler;
import darksky.modelo.bean.Comentario;
import darksky.modelo.bean.Post;
import darksky.modelo.bean.Respuesta;
import darksky.modelo.bean.Usuario;
import darksky.modelo.service.PostService;
import darksky.modelo.service.UsuarioService;
import darksky.util.ErrorMsgs;
import darksky.util.JsonReturn;

public class EditarPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditarPost() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionHandler sessionHandler = new SessionHandler(request);
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		JsonReturn ret = new JsonReturn();
		
		if (!sessionHandler.isInit()) {
			ret.setErrorMsg(ErrorMsgs.NO_SESSION);
			out.print(ret.getRet());
			return;
		}
		
		String type = request.getParameter("type");
		
		switch(type) {
		case "editarPost":
			editarPost(request, response, ret);
			break;
		case "editarComentario":
			editarComentario(request, response, ret);
			break;
		case "editarRespuesta":
			editarRespuesta(request, response, ret);
			break;
		default:
			ret.setErrorMsg(ErrorMsgs.METHOD_NOT_FOUND + type);
		}
		
		out.print(ret.getRet());
	}
	
	private void editarPost(HttpServletRequest request, HttpServletResponse response, JsonReturn ret) {
		try {
			Integer idPost = Integer.parseInt(request.getParameter("idPost"));
			String titulo = request.getParameter("titulo");
			String texto = request.getParameter("texto");
			
			PostService postService = new PostService();
			SessionHandler sessionHandler = new SessionHandler(request);
			UsuarioService usuarioService = new UsuarioService();
			
			Usuario usuario = sessionHandler.getUsuario();
			Post post = postService.getPost(idPost);
			
			boolean permiso = false;
			if (usuarioService.permisoEditarForo(usuario)) {
				permiso = true;
			} else {
				permiso = usuarioService.propietarioPost(usuario, post);
			}
			
			if (permiso) {
				post.setTitulo(titulo);
				post.setTexto(texto);
				postService.actualizarPost(post);
			}
		} catch(Exception e) {
			e.printStackTrace();
			ret.setErrorMsg(ErrorMsgs.STANDARD_MSG);
		}
	}
	
	private void editarComentario(HttpServletRequest request, HttpServletResponse response, JsonReturn ret) {
		try {
			Integer idComentario = Integer.parseInt(request.getParameter("idComment"));
			String texto = request.getParameter("texto");
			
			PostService postService = new PostService();
			SessionHandler sessionHandler = new SessionHandler(request);
			UsuarioService usuarioService = new UsuarioService();
			
			Usuario usuario = sessionHandler.getUsuario();
			Comentario comentario = postService.getComentario(idComentario);
			
			boolean permiso = false;
			if (usuarioService.permisoEditarForo(usuario)) {
				permiso = true;
			} else {
				permiso = usuarioService.propietarioComentario(usuario, comentario);
			}
			
			if (permiso) {
				comentario.setTexto(texto);
				postService.editarComentario(comentario);
			}
		} catch(Exception e) {
			e.printStackTrace();
			ret.setErrorMsg(ErrorMsgs.STANDARD_MSG);
		}
		
	}
	
	private void editarRespuesta(HttpServletRequest request, HttpServletResponse response, JsonReturn ret) {
		try {
			Integer idRespuesta = Integer.parseInt(request.getParameter("idComment"));
			String texto = request.getParameter("texto");
			
			PostService postService = new PostService();
			SessionHandler sessionHandler = new SessionHandler(request);
			UsuarioService usuarioService = new UsuarioService();
			
			Usuario usuario = sessionHandler.getUsuario();
			Respuesta respuesta = postService.getRespuesta(idRespuesta);
			
			boolean permiso = false;
			if (usuarioService.permisoEditarForo(usuario)) {
				permiso = true;
			} else {
				permiso = usuarioService.propietarioRespuesta(usuario, respuesta);
			}
			
			if (permiso) {
				respuesta.setTexto(texto);
				postService.editarRespuesta(respuesta);
			}
		} catch(Exception e) {
			e.printStackTrace();
			ret.setErrorMsg(ErrorMsgs.STANDARD_MSG);
		}
		
	}
}
