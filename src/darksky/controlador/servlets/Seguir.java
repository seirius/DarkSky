package darksky.controlador.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import darksky.controlador.helpers.SessionHandler;
import darksky.modelo.bean.Usuario;
import darksky.modelo.service.SeguirService;
import darksky.util.ErrorMsgs;
import darksky.util.JsonReturn;

public class Seguir extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Seguir() {
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
			ret.setErrorMsg("Sesion no iniciada o caducada.");
			out.print(ret.getRet());
			return;
		}
		
		String tipo = request.getParameter("tipo");
		
		switch(tipo) {
		
		case "followPost":
			followPost(request, response, sessionHandler, ret);
		break;
		
		case "unfollowPost":
			unfollowPost(request, response, sessionHandler, ret);
		break;
		
		case "getPostNotificacion":
			getPostNotificacion(request, response, sessionHandler, ret);
		break;
		
		case "marcarVistaNotificacion":
			marcarVistaNotificacion(request, response, sessionHandler, ret);
		break;
		
		case "getNotificacionesCount":
			getNotificacionesCount(request, response, sessionHandler, ret);
		break;
		
		default:
			ret.setErrorMsg(ErrorMsgs.METHOD_NOT_FOUND + tipo);
		break;
		
		}
		
		out.print(ret.getRet());
	}
	
	private void followPost(HttpServletRequest request, HttpServletResponse response, SessionHandler sessionHandler, JsonReturn ret) throws IOException {
		try {
			int idPost = Integer.parseInt(request.getParameter("idPost"));
			
			SeguirService seguirS = new SeguirService();
		
			seguirS.followPost(sessionHandler.getUsuario().getNick(), idPost);
		} catch(Exception e) {
			e.printStackTrace();
			ret.setErrorMsg(ErrorMsgs.STANDARD_MSG);
		}
	}
	
	private void unfollowPost(HttpServletRequest request, HttpServletResponse response, SessionHandler sessionHandler, JsonReturn ret) throws IOException {
		try {
			int idPost = Integer.parseInt(request.getParameter("idPost"));
			
			SeguirService seguirS = new SeguirService();
		
			seguirS.unfollowPost(sessionHandler.getUsuario().getNick(), idPost);
		} catch(Exception e) {
			e.printStackTrace();
			ret.setErrorMsg(ErrorMsgs.STANDARD_MSG);
		}
	}
	
	private void getPostNotificacion(HttpServletRequest request, HttpServletResponse response, SessionHandler sessionHandler, JsonReturn ret) {
		try {
			SeguirService seguirService = new SeguirService();
			Usuario usuario = sessionHandler.getUsuario();
			int     limit   = Integer.parseInt(request.getParameter("limit"));
			String  estado  = request.getParameter("estado");
			
			JsonArray jsonArray = seguirService.getAllJsonNotificacionPorUsuario_Entrada(usuario.getNick(), estado, limit);
			ret.setData(jsonArray);
		} catch(Exception e) {
			e.printStackTrace();
			ret.setErrorMsg(ErrorMsgs.STANDARD_MSG);
		}
	}
	
	private void marcarVistaNotificacion(HttpServletRequest request, HttpServletResponse response, SessionHandler sessionHandler, JsonReturn ret) {
		try {
			SeguirService seguirService = new SeguirService();
			int idNotificacion = Integer.parseInt(request.getParameter("idNotificacion"));
			
			seguirService.marcarComoVistaNotificacion(idNotificacion);
		} catch(Exception e) {
			e.printStackTrace();
			ret.setErrorMsg(ErrorMsgs.STANDARD_MSG);
		}
	}
	
	private void getNotificacionesCount(HttpServletRequest request, HttpServletResponse response, SessionHandler sessionHandler, JsonReturn ret) {
		try {
			SeguirService seguirService = new SeguirService();
			String nick   = sessionHandler.getUsuario().getNick();
			String evento = request.getParameter("evento");
			String estado = request.getParameter("estado");
			
			String[] stringArray = JsonReturn.fromStringToJsonArray(evento).toArray(new String[0]);
			int count = seguirService.getNotificacionCountPorUsuario(nick, stringArray, estado);
			
			JsonObject json = new JsonObject();
			json.addProperty("numeroNotificacion", count);
			ret.setData(json);
		} catch(Exception e) {
			e.printStackTrace();
			ret.setErrorMsg(ErrorMsgs.STANDARD_MSG);
		}
	}

}
