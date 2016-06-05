package darksky.controlador.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import darksky.modelo.bean.Usuario;
import darksky.modelo.service.UsuarioService;
import darksky.util.JsonReturn;
import darksky.util.MyUtil;

public class IniciarSesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public IniciarSesion() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		JsonReturn ret = new JsonReturn();
		
		try {
			String nick = request.getParameter("usuario");
			String password = request.getParameter("password");
			
			if (MyUtil.isEmpty(nick) || MyUtil.isEmpty(password)) {
				ret.setErrorMsg("Usuario y/o contraseña no pueden estar vacios");
			} else {
				UsuarioService usuarioS = null;
				
				usuarioS = new UsuarioService();
				Usuario usuario = usuarioS.iniciarSesion(nick, password);
				
				JsonObject data = new JsonObject();
				ret.setData(data);
				
				if (usuario != null) {
					HttpSession session = request.getSession();
					session.setAttribute("usuario", usuario);
//				session.setMaxInactiveInterval(120);
					data.addProperty("ESTADO", true);
				} else {
					data.addProperty("ESTADO", false);
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			ret.setErrorMsg("Ha ocurrido un error mientras se intentaba crear usuario, vuelva a intentarlo mas tarde.");
		}
		
		out.print(ret.getRet());
	}

	
	
	
	
	
	
	
	
	
	
}
