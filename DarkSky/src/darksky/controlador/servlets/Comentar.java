package darksky.controlador.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import darksky.controlador.helpers.SessionHandler;
import darksky.modelo.bean.Comentario;
import darksky.modelo.bean.Post;
import darksky.modelo.bean.Respuesta;
import darksky.modelo.bean.Usuario;
import darksky.modelo.service.PostService;
import darksky.util.ErrorMsgs;
import darksky.util.JsonReturn;

public class Comentar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Comentar() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		JsonReturn ret = new JsonReturn();
		SessionHandler sessionHandler = new SessionHandler(request);
		
		if (!sessionHandler.isInit()) {
			ret.setErrorMsg(ErrorMsgs.NO_SESSION);
			out.print(ret.getRet());
			return;
		}
		
		String type = request.getParameter("type");
		
		switch(type) {
		case "comentar":
			comentar(request, response, ret);
			break;
		case "responderComentario":
			responderComentario(request, response, ret);
			break;
		case "responderRespuesta":
			responderRespuesta(request, response, ret);
			break;
		case "getTextoComentario":
			getTextoComentario(request, response, ret);
			break;
		default:
			ret.setErrorMsg(ErrorMsgs.METHOD_NOT_FOUND + type);
		}
		
		out.print(ret.getRet());
	}
	
	private void comentar(HttpServletRequest request, HttpServletResponse response, JsonReturn ret) {
		
		try {
			String id = request.getParameter("idComment");
			String textoComentario = request.getParameter("textoComentario");
			String nick = new SessionHandler(request).getUsuario().getNick();
			
			PostService postService = new PostService();
			Post post = new Post();
			post.setId(Integer.parseInt(id));
			Usuario usuario = new Usuario();
			usuario.setNick(nick);
			
			Comentario comentario = new Comentario();
			comentario.setPost(post);
			comentario.setUsuario(usuario);
			comentario.setTexto(textoComentario);
			
			postService.comentar(comentario);
		} catch(Exception e) {
			e.printStackTrace();
			ret.setErrorMsg(ErrorMsgs.STANDARD_MSG);
		}
		
	}
	
	private void responderComentario(HttpServletRequest request, HttpServletResponse response, JsonReturn ret) {

		try {
			String id = request.getParameter("idComment");
			String textoComentario = request.getParameter("textoComentario");
			String nick = new SessionHandler(request).getUsuario().getNick();
			
			PostService postService = new PostService();
			Comentario comentario = new Comentario();
			comentario.setId(Integer.parseInt(id));
			Usuario usuario = new Usuario();
			usuario.setNick(nick);
			
			Respuesta respuesta = new Respuesta();
			respuesta.setComentario(comentario);
			respuesta.setUsuario(usuario);
			respuesta.setTexto(textoComentario);
			
			postService.responder(respuesta);
		} catch(Exception e) {
			e.printStackTrace();
			ret.setErrorMsg(ErrorMsgs.STANDARD_MSG);
		}
		
	}
	
	private void responderRespuesta(HttpServletRequest request, HttpServletResponse response, JsonReturn ret) {

		try {
			int id = Integer.parseInt(request.getParameter("idComment"));
			String textoComentario = request.getParameter("textoComentario");
			String nick = new SessionHandler(request).getUsuario().getNick();
			
			PostService postService = new PostService();
			Respuesta respuesta = new Respuesta();
			Respuesta anteriorRespuesta = new Respuesta();
			Usuario usuario = new Usuario();
			
			anteriorRespuesta.setId(id);
			usuario.setNick(nick);
			respuesta.setRespuesta(anteriorRespuesta);
			respuesta.setUsuario(usuario);
			respuesta.setTexto(textoComentario);
			
			postService.responder(respuesta);
		} catch(Exception e) {
			e.printStackTrace();
			ret.setErrorMsg(ErrorMsgs.STANDARD_MSG);
		}
		
	}
	
	private void getTextoComentario(HttpServletRequest request, HttpServletResponse response, JsonReturn ret) throws IOException {
		try {
			JsonObject data = new JsonObject();
			
			String tipoComment = request.getParameter("tipoComment");
			int idComment = Integer.parseInt(request.getParameter("idComment"));
			
			PostService postService = new PostService();
			
			if (tipoComment.equals("comentario")) {
				Comentario comentario = postService.getComentario(idComment);
				data.addProperty("TEXTO", comentario.getTexto());
				ret.setData(data);
			} else if (tipoComment.equals("respuesta")) {
				Respuesta respuesta = postService.getRespuesta(idComment);
				data.addProperty("TEXTO", respuesta.getTexto());
				ret.setData(data);
			} else {
				ret.setErrorMsg(ErrorMsgs.METHOD_NOT_FOUND + tipoComment);
			}
		} catch(Exception e) {
			e.printStackTrace();
			ret.setErrorMsg(ErrorMsgs.STANDARD_MSG);
		}
	}

}
