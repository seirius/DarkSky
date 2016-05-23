package darksky.modelo.dao;

import org.hibernate.Session;

import darksky.exceptions.ExceptionDAO;
import darksky.modelo.bean.Comentario;
import darksky.modelo.bean.Respuesta;

public class ComentarioDAO {

	private Session session;
	
	public ComentarioDAO(Session session) {
		this.session = session;
	}
	
	public Comentario getComentario(int idComentario) {
		Comentario comentario = null;
		
		try {
			comentario = session.get(Comentario.class, idComentario);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
		
		return comentario;
	}
	
	public Respuesta getRespuesta(int idRespuesta) {
		Respuesta respuesta = null;
		
		try {
			respuesta = session.get(Respuesta.class, idRespuesta);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
		
		return respuesta;
	}
	
	public void comentar(Comentario comentario) {

		try {
			session.save(comentario);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
	}

	public void responder(Respuesta respuesta) {
		try {
			session.save(respuesta);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
		
	}
	
	public void updateComentario(Comentario comentario) {
		try {
			session.update(comentario);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
	}
	
	public void updateRespuesta(Respuesta respuesta) {

		try {
			session.update(respuesta);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
	}
	
	public void deleteComentario(Comentario comentario) {
		try {
			session.delete(comentario);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
	}
	
	public void deleteRespuesta(Respuesta respuesta) {
		try {
			session.delete(respuesta);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
	}
	
	public Integer getIdPostFromRespuesta(Respuesta respuesta) {
		Integer idPost = null;
		
		try {
			if (respuesta.getComentario() == null) {
				Respuesta auxRespuesta = getRespuesta(respuesta.getRespuesta().getId());
				idPost = getIdPostFromRespuesta(auxRespuesta);
			} else {
				Comentario comentarioF = respuesta.getComentario();
				comentarioF = getComentario(comentarioF.getId());
				idPost = comentarioF.getPost().getId();
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
		
		return idPost;
	}
	
}
