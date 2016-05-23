package darksky.controlador.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import darksky.controlador.helpers.SessionHandler;

public class CerrarSesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CerrarSesion() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionHandler sHandler = new SessionHandler(request);
		sHandler.close();
		
		String backUrl = request.getParameter("backUrl");
		response.sendRedirect(backUrl);
	}

}
