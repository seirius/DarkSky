package darksky.modelo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import darksky.exceptions.ExceptionDAO;
import darksky.modelo.bean.Usuario;
import darksky.util.ErrorMsgs;
import darksky.util.MyUtil;

public class UsuarioDAO {

	private Session session;
	
	public UsuarioDAO(Session session) {
		this.session = session;
	}
	
	public void insertar(Usuario usuario) {
		try {
			session.save(usuario);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
	}
	
	public Usuario iniciarSesion(String nick, String password) {
		if (MyUtil.isEmpty(nick)) throw new ExceptionDAO("Nick no puede ser nulo");
		if (MyUtil.isEmpty(password)) throw new ExceptionDAO("Contraseña no puede ser nula");
		
		try {
			@SuppressWarnings("unchecked")
			List<Usuario> usuarios = session.createCriteria(Usuario.class)
					.add(Restrictions.eq("nick", nick))
					.add(Restrictions.eq("password", password))
					.list();
			
			if (usuarios.size() > 0) return usuarios.get(0);
			else return null;
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
	}
	
	public boolean exist(String nick) {
		if (MyUtil.isEmpty(nick)) throw new ExceptionDAO("Nick no puede ser nulo");
		
		try {
			Usuario usuario = session.get(Usuario.class, nick);
			if (usuario == null) return false;
			else return true;
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
	}
	
	public boolean existEmail(String email) {
		if (MyUtil.isEmpty(email)) throw new ExceptionDAO("Email no puede ser nulo");
		
		try {
			@SuppressWarnings("unchecked")
			List<Usuario> usuarios = session.createCriteria(Usuario.class)
					.add(Restrictions.eq("email", email))
					.list();
			if (usuarios.size() > 0) return true;
			else return false;
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error itnerno", e.getCause());
		}
	}
	
	public Usuario getUsuario(String nick) {
		Usuario usuario = null;
		
		try {
			usuario = session.get(Usuario.class, nick);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
		
		return usuario;
	}
	
	public Usuario update(Usuario usuario) {
		try {
			session.merge(usuario);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO(ErrorMsgs.STANDARD_MSG, e.getCause());
		}
		
		return usuario;
	}
	
	
	
	
	
	
	
	
	
}
