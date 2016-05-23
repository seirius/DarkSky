package darksky.controlador.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import darksky.controlador.helpers.SessionHandler;
import darksky.modelo.bean.Post;
import darksky.modelo.bean.Usuario;
import darksky.modelo.service.PostService;
import darksky.modelo.service.UsuarioService;
import darksky.util.JsonReturn;

public class EliminarPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EliminarPost() {
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
			ret.setErrorMsg("Sesion no iniciada o caducada.");
			out.print(ret.getRet());
			return;
		}
		
		try {
			PostService postService = new PostService();
			UsuarioService usuarioService = new UsuarioService();
			
			Integer idPost = Integer.parseInt(request.getParameter("idPost"));
			
			Post post = postService.getPost(idPost);
			Usuario usuario = sessionHandler.getUsuario();
			
			if (usuarioService.permisoEditarForo(usuario) || usuarioService.propietarioPost(usuario, post)) {
				postService.eliminarPost(post);
			}
		} catch(Exception e) {
			e.printStackTrace();
			ret.setErrorMsg("Ha ocurrido un error mientras se intentaba crear usuario, vuelva a intentarlo mas tarde.");
		}
		
		out.print(ret.getRet());
	}

}
