package darksky.controlador.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import darksky.controlador.helpers.SessionHandler;
import darksky.modelo.bean.Comentario;
import darksky.modelo.bean.Respuesta;
import darksky.modelo.bean.Usuario;
import darksky.modelo.service.PostService;
import darksky.modelo.service.UsuarioService;
import darksky.util.ErrorMsgs;
import darksky.util.JsonReturn;

public class EliminarComentario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EliminarComentario() {
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
		case "eliminarComentario":
			eliminarComentario(request, response, ret);
			break;
		case "eliminarRespuesta":
			eliminarRespuesta(request, response, ret);
			break;
		default:
			ret.setErrorMsg(ErrorMsgs.METHOD_NOT_FOUND + type);
		}
		
		out.print(ret.getRet());
	}
	
	private void eliminarComentario(HttpServletRequest request, HttpServletResponse response, JsonReturn ret) {
		try {
			SessionHandler sessionHandler = new SessionHandler(request);
			PostService postService = new PostService();
			UsuarioService usuarioService = new UsuarioService();
			
			Integer idComentario = Integer.parseInt(request.getParameter("idComment"));
			
			Usuario usuario = sessionHandler.getUsuario();
			Comentario comentario = postService.getComentario(idComentario);
			
			boolean permiso = false;
			if (usuarioService.permisoEditarForo(usuario)) {
				permiso = true;
			} else {
				permiso = usuarioService.propietarioComentario(usuario, comentario);
			}
			
			if (permiso) {
				postService.eliminarComentario(comentario);
			}
		} catch(Exception e) {
			ret.setErrorMsg("Ha ocurrido algo inesperado, vuelva a intentarlo mas tarde");
		}
	}
	
	private void eliminarRespuesta(HttpServletRequest request, HttpServletResponse response, JsonReturn ret) {
		try {
			SessionHandler sessionHandler = new SessionHandler(request);
			PostService postService = new PostService();
			UsuarioService usuarioService = new UsuarioService();
			
			Integer idRespuesta = Integer.parseInt(request.getParameter("idComment"));
			
			Usuario usuario = sessionHandler.getUsuario();
			Respuesta respuesta = postService.getRespuesta(idRespuesta);
			
			boolean permiso = false;
			if (usuarioService.permisoEditarForo(usuario)) {
				permiso = true;
			} else {
				permiso = usuarioService.propietarioRespuesta(usuario, respuesta);
			}
			
			if (permiso) {
				postService.eliminarRespuesta(respuesta);
			}
		} catch(Exception e) {
			ret.setErrorMsg("Ha ocurrido algo inesperado, vuelva a intentarlo mas tarde");
		}
	}

}
