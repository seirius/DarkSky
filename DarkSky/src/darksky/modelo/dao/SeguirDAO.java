package darksky.modelo.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import darksky.exceptions.ExceptionDAO;
import darksky.modelo.bean.SeguirPost;
import darksky.modelo.bean.SeguirPostId;
import darksky.modelo.bean.SeguirUsuario;
import darksky.modelo.bean.SeguirUsuarioId;

public class SeguirDAO {

	private Session session;
	
	public SeguirDAO(Session session) {
		this.session = session;
	}
	
	public SeguirUsuario insertSeguirUsuario(String usuOrigen, String usuTarget) {
		try {
			SeguirUsuarioId seguirUsuarioID = new SeguirUsuarioId();
			seguirUsuarioID.setUsuOrigin(usuOrigen);
			seguirUsuarioID.setUsuTarget(usuTarget);
			
			SeguirUsuario seguirUsuario = new SeguirUsuario();
			seguirUsuario.setId(seguirUsuarioID);
			
			session.save(seguirUsuario);
			
			return seguirUsuario;
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
	}
	
	public void deleteSeguirUsuario(String usuOrigen, String usuTarget) {
		try {
			SeguirUsuarioId seguirUsuarioID = new SeguirUsuarioId();
			seguirUsuarioID.setUsuOrigin(usuOrigen);
			seguirUsuarioID.setUsuTarget(usuTarget);
			
			SeguirUsuario seguirUsuario = new SeguirUsuario();
			seguirUsuario.setId(seguirUsuarioID);
			
			session.delete(seguirUsuario);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
	}
	
	public SeguirPost insertarSeguirPost(String usuOrigen, int idPost) {
		try {
			SeguirPostId seguirPostID = new SeguirPostId();
			seguirPostID.setUsuOrigin(usuOrigen);
			seguirPostID.setPostTarget(idPost);
			
			SeguirPost seguirPost = new SeguirPost();
			seguirPost.setId(seguirPostID);
			
			session.save(seguirPost);
			
			return seguirPost;
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
	}
	
	public void deleteSeguirPost(String usuOrigen, int idPost) {
		try {
			SeguirPostId seguirPostID = new SeguirPostId();
			seguirPostID.setUsuOrigin(usuOrigen);
			seguirPostID.setPostTarget(idPost);
			
			SeguirPost seguirPost = new SeguirPost();
			seguirPost.setId(seguirPostID);
			
			session.delete(seguirPost);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
	}
	
	public SeguirPost getSeguirPost(String usuOrigen, int idPost) {
		SeguirPost seguirPost = null;
		try {
			SeguirPostId seguirPostID = new SeguirPostId();
			seguirPostID.setUsuOrigin(usuOrigen);
			seguirPostID.setPostTarget(idPost);
			
			seguirPost = session.get(SeguirPost.class, seguirPostID);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
		
		return seguirPost;
	}
	
	@SuppressWarnings("unchecked")
	public List<SeguirPost> getSeguirPostPorPost(int idPost) {
		List<SeguirPost> listaSeguir = new ArrayList<SeguirPost>();
		
		try {
			String sql = "SELECT USU_ORIGIN, POST_TARGET "
					   + "FROM SEGUIR_POST "
					   + "WHERE POST_TARGET = :idPost";
			
			Query query = session.createSQLQuery(sql).addEntity(SeguirPost.class);
			query.setParameter("idPost", idPost);
			listaSeguir = query.list();
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
		
		return listaSeguir;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
