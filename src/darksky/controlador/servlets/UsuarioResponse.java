package darksky.controlador.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import darksky.controlador.helpers.SessionHandler;
import darksky.modelo.bean.Usuario;
import darksky.modelo.service.UsuarioService;
import darksky.util.ErrorMsgs;
import darksky.util.JsonReturn;

/**
 * Servlet implementation class UsuarioResponse
 */
@WebServlet("/usuario-response")
public class UsuarioResponse extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public UsuarioResponse() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		String type = request.getParameter("method");
		
		switch(type) {
		
		case "modificarNombre":
			modificarNombre(request, response, sessionHandler, ret);
		break;
		
		case "modificarEmail":
			modificarEmail(request, response, sessionHandler, ret);
		break;
		
		default:
			ret.setErrorMsg(ErrorMsgs.METHOD_NOT_FOUND + type);
		}
		
		out.print(ret.getRet());
	}
	
	private void modificarNombre(HttpServletRequest request, HttpServletResponse response, SessionHandler sessionHandler, JsonReturn ret) {
		try {
			
			UsuarioService usuarioService = new UsuarioService();
			
			String nickTarget = request.getParameter("nickTarget");
			String newName = request.getParameter("newValue");
			
			Usuario origin = sessionHandler.getUsuario();
			Usuario target = usuarioService.getUsuario(nickTarget);
			
			if (usuarioService.permisoEditarUsuario(nickTarget, origin.getNick())) {
				target.setNombre(newName);
				usuarioService.updateUsuario(target);
			} else {
				ret.setErrorMsg(ErrorMsgs.NO_PERMISSION_USER);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			ret.setErrorMsg(ErrorMsgs.STANDARD_MSG);
		}
	}
	
	private void modificarEmail(HttpServletRequest request, HttpServletResponse response, SessionHandler sessionHandler, JsonReturn ret) {
		try {
			UsuarioService usuarioService = new UsuarioService();
			
			String nickTarget = request.getParameter("nickTarget");
			String newEmail = request.getParameter("newValue");
			
			Usuario origin = sessionHandler.getUsuario();
			Usuario target = usuarioService.getUsuario(nickTarget);
			
			if (usuarioService.permisoEditarUsuario(nickTarget, origin.getNick())) {
				target.setEmail(newEmail);
				usuarioService.updateUsuario(target);
			} else {
				ret.setErrorMsg(ErrorMsgs.NO_PERMISSION_USER);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			ret.setErrorMsg(ErrorMsgs.STANDARD_MSG);
		}
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
