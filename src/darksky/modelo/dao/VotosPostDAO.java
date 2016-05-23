package darksky.modelo.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import darksky.exceptions.ExceptionDAO;
import darksky.modelo.bean.Post;
import darksky.modelo.bean.VotosPost;
import darksky.modelo.bean.VotosPostId;

public class VotosPostDAO {

	private Session session;
	
	public VotosPostDAO(Session session) {
		this.session = session;
	}
	
	public void insertar(VotosPost voto) {
		try {
			session.saveOrUpdate(voto);
		} catch(ConstraintViolationException e) {
			if (e.getErrorCode() == 1062) { //Con saveOrUpdate no puede saltar nunca esta excepcion
				throw new ExceptionDAO("Yas has votado");
			} else {
				throw new ExceptionDAO("Error interno", e.getCause());
			}
		} catch(Exception e) {
			throw new ExceptionDAO("Error interno", e.getCause());
		}
	}
	
	public void eliminar(VotosPost voto) {
		try {
			session.delete(voto);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
	}
	
	public void eliminarPorPost(Post post) {
		try {
			String sql = "DELETE FROM VOTOS_POST WHERE ID_POST = :idPost";
			SQLQuery query = session.createSQLQuery(sql);
			query.setInteger("idPost", post.getId());
			query.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
	}
	
	public void update(VotosPost voto) {
		try {
			session.update(voto);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
	}
	
	public int getVotosPorPost(Post post) {
		int valorVotos = 0;
		
		try {
			
			@SuppressWarnings("unchecked")
			List<VotosPost> votos = session.createCriteria(VotosPost.class)
					.add(Restrictions.eq("id.idPost", post.getId()))
					.list();
			
			for (VotosPost voto: votos) {
				valorVotos += voto.getIntValor();
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
		
		return valorVotos;
	}
	
	@SuppressWarnings("unchecked")
	public List<VotosPost> getAllVotos() {
		
		List<VotosPost> votos = null;
		
		try {
			votos = session.createCriteria(VotosPost.class).list();
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
		
		return votos;
	}
	
	public VotosPost getVoto(int idPost, String nick) {
		VotosPostId id = new VotosPostId();
		id.setIdPost(idPost);
		id.setUsuarioNick(nick);
		
		try {
			VotosPost voto = session.get(VotosPost.class, id);
			return voto;
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
		
	}
	
}
