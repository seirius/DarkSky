package darksky.controlador.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import darksky.modelo.bean.Item;
import darksky.modelo.bean.Usuario;
import darksky.modelo.service.UsuarioService;
import darksky.util.JsonReturn;

public class CrearCuenta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CrearCuenta() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		JsonReturn ret = new JsonReturn();
		
		String nick = request.getParameter("nick");
		String nombre = request.getParameter("nombre");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String itemName = request.getParameter("itemName");
		
		try {
			Usuario usuario = new Usuario();
			usuario.setNick(nick);
			usuario.setNombre(nombre);
			usuario.setEmail(email);
			usuario.setPassword(password);
			Item item = new Item();
			item.setNombre(itemName);
			usuario.setAvatar(item);
			
			UsuarioService usuarioService = new UsuarioService();
			usuarioService.insertarUsuario(usuario, item);
			
		} catch(Exception e) {
			ret.setErrorMsg("Ha ocurrido un error mientras se intentaba crear usuario, vuelva a intentarlo mas tarde.");
			e.printStackTrace();
		} finally {
			out.print(ret.getRet());
		}
	}

}
