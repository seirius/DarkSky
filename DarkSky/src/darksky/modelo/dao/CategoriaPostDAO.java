package darksky.modelo.dao;

import java.util.List;

import org.hibernate.Session;

import darksky.exceptions.ExceptionDAO;
import darksky.modelo.bean.CategoriaPost;

public class CategoriaPostDAO {
	
	private Session session; 
	
	public CategoriaPostDAO(Session session) {
		this.session = session;
	}
	
	@SuppressWarnings("unchecked")
	public List<CategoriaPost> getCategorias() {
		
		List<CategoriaPost> categorias = null;
		
		String sql = "SELECT * FROM CATEGORIA_POST";
		
		try {
			categorias = session.createSQLQuery(sql).addEntity(CategoriaPost.class).list();
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error Interno", e.getCause());
		}
		
		return categorias;
	}

}
