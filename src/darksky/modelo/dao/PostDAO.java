package darksky.modelo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import darksky.exceptions.ExceptionDAO;
import darksky.modelo.bean.CategoriaPost;
import darksky.modelo.bean.Comentario;
import darksky.modelo.bean.Post;
import darksky.modelo.bean.Respuesta;

public class PostDAO {

	private Session session;

	public PostDAO(Session session) {
		this.session = session;
	}
	
	public Post insert(Post post) {
		
		try {
			session.save(post);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
		
		return post;
	}
	
	public void update(Post post) {
		try {
			session.update(post);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
	}
	
	public void delete(Post post) {
		try {
			session.delete(post);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
	}
	
	public Post getPost(int idPost) {
		Post post = null;
		
		try {
			post = session.get(Post.class, idPost);
			
			if (post == null) {
				throw new ExceptionDAO("Esta entrada no existe o ha sido eliminada");
			}
		} catch(Exception e) {
			e.printStackTrace();
			
			if (e instanceof ExceptionDAO) {
				throw new ExceptionDAO(e.getMessage());
			}
			
			throw new ExceptionDAO("Error interno", e.getCause());
		}
		
		return post;
	}

	@SuppressWarnings("unchecked")
	public List<Post> getNoticias() {
		List<Post> list = null;

		String sql = "SELECT * FROM POST WHERE EN_PORTADA = 'S' ORDER BY FECHA_CREACION DESC";

		try {
			list = session.createSQLQuery(sql).addEntity(Post.class).list();
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error Interno", e.getCause());
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Post> getPostsPorCategoria(CategoriaPost categoria) {
		List<Post> list = null;

		try {
			list = session.createCriteria(Post.class)
				    .add(Restrictions.eq("categoriaPost", categoria))
				    .list();
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error Interno", e.getCause());
		}

		return list;
	}
	
	// Order By = cualquier valor de la clase Post.class
	@SuppressWarnings("unchecked")
	public List<Post> getPostsPorCategoria(CategoriaPost categoria, int resultados, String orderBy) {
		List<Post> list = null;

		try {
			Criteria criteria = session.createCriteria(Post.class)
					.add(Restrictions.eq("categoriaPost", categoria))
					.addOrder(Order.desc(orderBy));
			
			if (resultados != 0) {
				criteria.setMaxResults(resultados);
			}
			
			list = criteria.list();
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error Interno", e.getCause());
		}

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Comentario> getComentarios(Post post) {
		List<Comentario> comentarios = null;
		
		try {
			comentarios = session.createCriteria(Comentario.class)
					.add(Restrictions.eq("post", post))
					.addOrder(Order.asc("fechaCreacion"))
					.list();
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
		
		return comentarios;
	}
	
	@SuppressWarnings("unchecked")
	public List<Respuesta> getRespuestasPorComentario(Comentario comentario) {
		List<Respuesta> respuestas = null;
		
		try {
			respuestas = session.createCriteria(Respuesta.class)
					.add(Restrictions.eq("comentario", comentario))
					.addOrder(Order.asc("fechaCreacion"))
					.list();
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
		
		return respuestas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Respuesta> getRespuestasPorRespuesta(Respuesta respuesta) {
		List<Respuesta> respuestas = null;
		
		try {
			respuestas = session.createCriteria(Respuesta.class)
					.add(Restrictions.eq("respuesta", respuesta))
					.addOrder(Order.asc("fechaCreacion"))
					.list();
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
		
		return respuestas;
	}
	
	public void visitPost(Post post) {
		try {
			post.setVisitas(post.getVisitas() + 1);
			session.update(post);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
	}

	
	
	
	
	
	
	
	
	
	
	
}
