package darksky.controlador.helpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import darksky.modelo.bean.Usuario;

public class SessionHandler {

	private HttpSession session;
	
	public SessionHandler(HttpServletRequest request) {
		session = request.getSession();
	}
	
	public boolean isInit() {
		if (session.getAttribute("usuario") != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public Usuario getUsuario() {
		return (Usuario) session.getAttribute("usuario");
	}
	
	public void close() {
		session.invalidate();
	}
	
	
}
