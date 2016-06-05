package darksky.controlador.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import darksky.modelo.service.UsuarioService;
import darksky.util.ValidJSON;

public class ComprobarDuplicado extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final String NICK_EXIST = "nick";
	private final String EMAIL_EXIST = "email";
       
    public ComprobarDuplicado() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tipo = request.getParameter("tipo");
		
		switch(tipo) {
		case NICK_EXIST:
			comprobarNick(request, response);
			break;
		case EMAIL_EXIST:
			comprobarEmail(request, response);
			break;
		}
	}
	
	/*COMPROBAR NICK*/
	public void comprobarNick(HttpServletRequest request, HttpServletResponse response) {
		String valor = request.getParameter("valor");
		try {
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			UsuarioService usuarioService = new UsuarioService();
			if (usuarioService.exist(valor)) {
				out.print(new Gson().toJson(new ValidJSON(false)));
			} else {
				out.print(new Gson().toJson(new ValidJSON(true)));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*COMPROBAR EMAIL*/
	public void comprobarEmail(HttpServletRequest request, HttpServletResponse response) {
		String valor = request.getParameter("valor");
		try {
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			UsuarioService usuarioService = new UsuarioService();
			if (usuarioService.existEmail(valor)) {
				out.print(new Gson().toJson(new ValidJSON(false)));
			} else {
				out.print(new Gson().toJson(new ValidJSON(true)));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
