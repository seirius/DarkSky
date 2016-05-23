package darksky.modelo.dao;

import org.hibernate.Session;

import darksky.exceptions.ExceptionDAO;
import darksky.modelo.bean.Imagen;

public class ImagenDAO {

	private Session session;
	
	public ImagenDAO(Session session) {
		this.session = session;
	}
	
	public Imagen insert(Imagen imagen) {
		
		try {
			session.save(imagen);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
		
		return imagen;
	}
	
	public void delete(Imagen imagen) {
		try {
			session.delete(imagen);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
	}
	
}
