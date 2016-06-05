package darksky.util;

import org.hibernate.Session;

import darksky.modelo.dao.CategoriaPostDAO;
import darksky.modelo.dao.ComentarioDAO;
import darksky.modelo.dao.ImagenDAO;
import darksky.modelo.dao.ItemDAO;
import darksky.modelo.dao.MenuDAO;
import darksky.modelo.dao.NotificacionDAO;
import darksky.modelo.dao.PostDAO;
import darksky.modelo.dao.SeguirDAO;
import darksky.modelo.dao.UsuarioDAO;
import darksky.modelo.dao.VotosPostDAO;

public class ServiceManager {

	private Session session;
	private HibernateUtil hibernateUtil;
	
	public ServiceManager() {
		hibernateUtil = new HibernateUtil();
		this.session = hibernateUtil.getSessionFactory().openSession();
	}
	
	public PostDAO getPostDAO() {
		return new PostDAO(session);
	}
	
	public CategoriaPostDAO getCategoriaPostDAO() {
		return new CategoriaPostDAO(session);
	}
	
	public VotosPostDAO getVotosPostDAO() {
		return new VotosPostDAO(session);
	}
	
	public UsuarioDAO getUsuarioDAO() {
		return new UsuarioDAO(session);
	}
	
	public ItemDAO getItemDAO() {
		return new ItemDAO(session);
	}
	
	public ComentarioDAO getComentarioDAO() {
		return new ComentarioDAO(session);
	}
	
	public ImagenDAO getImagenDAO() {
		return new ImagenDAO(session);
	}
	
	public SeguirDAO getSeguirDAO() {
		return new SeguirDAO(session);
	}
	
	public NotificacionDAO getNotificacionDAO() {
		return new NotificacionDAO(session);
	}
	
	public MenuDAO getMenuDAO() {
		return new MenuDAO(session);
	}
	
	public Session getSession() {
		return session;
	}
	
	public void beginTransaction() {
		session.beginTransaction();
	}
	
	public void rollback() {
		session.getTransaction().rollback();
	}
	
	public void rollbackClose() {
		session.getTransaction().rollback();
		session.close();
		hibernateUtil.close();
	}
	
	public void commitClose() {
		session.getTransaction().commit();
		session.close();
		hibernateUtil.close();
	}
	
	public void commit() {
		session.getTransaction().commit();
	}
	
	public void close() {
		session.close();
		hibernateUtil.close();
	}
	
}
