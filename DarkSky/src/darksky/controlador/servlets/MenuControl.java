package darksky.controlador.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;

import darksky.controlador.helpers.SessionHandler;
import darksky.exceptions.ExceptionBEAN;
import darksky.modelo.service.MenuService;
import darksky.modelo.service.UsuarioService;
import darksky.util.ErrorMsgs;
import darksky.util.JsonReturn;

@WebServlet("/menu-control")
public class MenuControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MenuControl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionHandler sessionHandler = new SessionHandler(request);
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		JsonReturn ret = new JsonReturn();
		
		if (!sessionHandler.isInit()) {
			ret.setErrorMsg("Sesion no iniciada o caducada.");
			out.print(ret.getRet());
			return;
		}
		
		UsuarioService usuarioService = new UsuarioService();
		if (!usuarioService.permisoEditarMenu(sessionHandler.getUsuario())) {
			ret.setErrorMsg("Not on my guard, permission denegated.");
			out.print(ret.getRet());
			return;
		}

		String type = request.getParameter("type");
		
		switch(type) {
		
		case "getMenus":
			getMenus(request, response, ret);
		break;
		
		case "eliminar":
			eliminar(request, response, ret);
		break;
		
		case "addMenu":
			addMenu(request, response, ret);
		break;
		
		default:
			ret.setErrorMsg(ErrorMsgs.METHOD_NOT_FOUND + type);
		}
		
		out.print(ret.getRet());
	}
	

	private void getMenus(HttpServletRequest request, HttpServletResponse response, JsonReturn ret) {
		try {
			MenuService menuService = new MenuService();
			JsonArray jsonArray = menuService.getMenusJSON();
			ret.setData(jsonArray);
		} catch(Exception e) {
			e.printStackTrace();
			ret.setErrorMsg(ErrorMsgs.STANDARD_MSG);
		}
	}
	
	private void eliminar(HttpServletRequest request, HttpServletResponse response, JsonReturn ret) {
		try {
			MenuService menuService = new MenuService();
			String nombreMenu = request.getParameter("nombreMenu");
			menuService.eliminar(nombreMenu);
		} catch(Exception e) {
			e.printStackTrace();
			ret.setErrorMsg(ErrorMsgs.STANDARD_MSG);
		}
	}
	
	private void addMenu(HttpServletRequest request, HttpServletResponse response, JsonReturn ret) {
		try {
			String nombreMenu = request.getParameter("nombreMenu");
			String url = request.getParameter("url");
			String nombreMenuSuperior = request.getParameter("nombreMenuSuperior");
			Long nivel = Long.parseLong(request.getParameter("nivel"));
			Long orden = Long.parseLong(request.getParameter("orden"));
			
			
			MenuService menuService = new MenuService();
			try {
				menuService.insertar(nombreMenu, nombreMenuSuperior, nivel, url, orden);
			} catch(ExceptionBEAN e) {
				e.printStackTrace();
				ret.setErrorMsg(e.getMessage());
			}
			
			
		} catch(NumberFormatException e) {
			ret.setErrorMsg("No se ha podido procesar el campo nivel u orden.");
		} catch(Exception e) {
			e.printStackTrace();
			ret.setErrorMsg(ErrorMsgs.STANDARD_MSG);
		}
	}

}
