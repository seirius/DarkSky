package darksky.controlador.helpers;

import java.util.ArrayList;
import java.util.List;

import darksky.modelo.bean.Comentario;
import darksky.modelo.bean.Respuesta;
import darksky.util.interfaces.Comment;

public class ComentarioHandler {

	/**
	 * 
	 * @author Andriy
	 * 
	 * Devuelve una lista de comentarios a partir de de otra lista de comments (Interfaz Comment)
	 * 
	 * @param comments
	 * @return 
	 */
	public static List<Comentario> getSoloComentarios(List<Comment> comments) {
		List<Comentario> comentarios = new ArrayList<Comentario>();
		
		for(int i = comments.size() - 1; i >= 0; i--) {
			Comment comment = comments.get(i);
			if (comment instanceof Comentario) {
				Comentario comentario = (Comentario) comment;
				comentarios.add(comentario);
			}
		}
		
		return comentarios;
	}
	
	/**
	 * 
	 * @author Andriy
	 * 
	 * Devuelve una lista de respuesta a partir de un comentario y una lista de comments (Interfaz Comment)
	 * 
	 * @param comentario
	 * @param comments
	 * @return 
	 */
	public static List<Respuesta> getRespuestasPorComentario(Comentario comentario, List<Comment> comments) {
		List<Respuesta> respuestas = new ArrayList<Respuesta>();
		
		for(Comment comment: comments) {
			if (comment instanceof Respuesta) {
				Respuesta respuesta = (Respuesta) comment;
				if (respuesta.getComentario() != null && respuesta.getComentario().getId() == comentario.getId()) {
					respuestas.add(respuesta);
				}
			}
		}
		
		return respuestas;
	}
	
	/**
	 * 
	 * @author Andriy
	 * 
	 * Devuelve una lista de respuesta de la lista de comments que respondan a la respuesta
	 * 
	 * @param respuesta
	 * @param comments
	 * @return
	 */
	public static List<Respuesta> getRespuestasPorRespuesta(Respuesta respuesta, List<Comment> comments) {
		List<Respuesta> respuestas = new ArrayList<Respuesta>();
		
		for(Comment comment: comments) {
			if (comment instanceof Respuesta) {
				Respuesta res = (Respuesta) comment;
				if (res.getRespuesta() != null && res.getRespuesta().getId() == respuesta.getId()) {
					respuestas.add(res);
				}
			}
		}
		
		return respuestas;
	}
}
