package darksky.controlador.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import darksky.controlador.helpers.SessionHandler;
import darksky.modelo.service.PostService;
import darksky.util.JsonReturn;

public class Votar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Votar() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionHandler sHandler = new SessionHandler(request);
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		JsonReturn ret = new JsonReturn();
		
		if (!sHandler.isInit()) {
			ret.setErrorMsg("Sesion no iniciada o caducada.");
			out.print(ret.getRet());
			return;
		}
		
		try {
			int idPost = Integer.parseInt(request.getParameter("idPost"));
			String valorVoto = request.getParameter("valorVoto");
			String nick = sHandler.getUsuario().getNick();
			PostService postS = new PostService();
			int votos;
			
			if (Integer.parseInt(valorVoto) > 0)      valorVoto = "1";
			else if (Integer.parseInt(valorVoto) < 0) valorVoto = "-1";
			
			votos = postS.votar(nick, idPost, valorVoto);
			
			JsonObject data = new JsonObject();
			data.addProperty("VOTO", votos);
			ret.setData(data);
			
		} catch(Exception e) {
			e.printStackTrace();
			ret.setErrorMsg("Ha habido un problema, vuelva a intentarlo mas tarde.");
		}
		
		out.print(ret.getRet());
	}

}
