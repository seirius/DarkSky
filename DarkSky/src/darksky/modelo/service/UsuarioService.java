package darksky.modelo.service;

import org.hibernate.service.spi.ServiceException;

import darksky.exceptions.ExceptionService;
import darksky.modelo.bean.Comentario;
import darksky.modelo.bean.Item;
import darksky.modelo.bean.Post;
import darksky.modelo.bean.Respuesta;
import darksky.modelo.bean.Usuario;
import darksky.modelo.dao.ItemDAO;
import darksky.modelo.dao.UsuarioDAO;
import darksky.util.ErrorMsgs;
import darksky.util.ServiceManager;

public class UsuarioService {
	
	public void insertarUsuario(Usuario usuario, Item item) throws ExceptionService {
		ServiceManager manager = new ServiceManager();
		UsuarioDAO usuarioDAO = manager.getUsuarioDAO();
		ItemDAO itemDAO = manager.getItemDAO();
		
		try {
			manager.beginTransaction();
			usuarioDAO.insertar(usuario);
			itemDAO.addInventario(usuario, item);
			
			manager.commit();
		} catch(Exception e) {
			manager.rollback();
			if (e.getCause() == null) {
				e.printStackTrace();
				throw new ExceptionService(e.getMessage());
			} else {
				e.printStackTrace();
				throw new ExceptionService(e.getMessage(), e.getCause());
			}
		} finally {
			manager.close();
		}
	}

	public Usuario iniciarSesion(String nick, String password) {
		ServiceManager manager = new ServiceManager();
		UsuarioDAO usuarioDAO = manager.getUsuarioDAO();
		
		Usuario usuario = null;
		try {
			usuario = usuarioDAO.iniciarSesion(nick, password);
		} finally {
			manager.close();
		}
		
		return usuario;
	}
	
	public boolean exist(String nick) {
		ServiceManager manager = new ServiceManager();
		UsuarioDAO usuarioDAO = manager.getUsuarioDAO();
		
		try {
			return usuarioDAO.exist(nick);
		} finally {
			manager.close();
		}
	}
	
	public boolean existEmail(String email) {
		ServiceManager manager = new ServiceManager();
		UsuarioDAO usuarioDAO = manager.getUsuarioDAO();
		
		try {
			return usuarioDAO.existEmail(email);
		} finally {
			manager.close();
		}
	}

	public boolean permisoEditarForo(Usuario usuario) {
		String rol = usuario.getRol().getRol();
		
		if (rol.equals("ADMIN") || rol.equals("MOD") || rol.equals("GOD")) {
			return true;
		}
		
		return false;
	}
	
	public boolean permisoEditarMenu(Usuario usuario) {
		String rol = usuario.getRol().getRol();
		
		if (rol.equals("ADMIN") || rol.equals("GOD")) {
			return true;
		}
		
		return false;
	}
	
	public boolean permisoEditarUsuario(String target, String origin) {
		boolean permitido = false;
		ServiceManager manager = new ServiceManager();
		
		try {
			UsuarioDAO usuarioDAO = manager.getUsuarioDAO();
			Usuario usuarioOrigin = usuarioDAO.getUsuario(origin);
			String rol = usuarioOrigin.getRol().getRol();
			
			if (target.equalsIgnoreCase(origin) || rol.equals("ADMIN") || rol.equals("GOD")) {
				permitido = true;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			throw new ServiceException(ErrorMsgs.STANDARD_MSG);
		} finally {
			manager.close();
		}
		
		return permitido;
	}
	
	public boolean propietarioPost(Usuario usuario, Post post) {
		if (post.getUsuario().getNick().equals(usuario.getNick())) {
			return true;
		}
		
		return false;
	}
	
	public boolean propietarioComentario(Usuario usuario, Comentario comentario) {
		if (comentario.getUsuario().getNick().equals(usuario.getNick())) {
			return true;
		}
		
		return false;
	}
	
	public boolean propietarioRespuesta(Usuario usuario, Respuesta respuesta) {
		if (respuesta.getUsuario().getNick().equals(usuario.getNick())) {
			return true;
		}
		
		return false;
	}
	
	public Usuario getUsuario(String nick) throws ExceptionService {
		ServiceManager manager = new ServiceManager();
		UsuarioDAO usuarioDAO = manager.getUsuarioDAO();
		Usuario usuario = null;
		
		try {
			usuario = usuarioDAO.getUsuario(nick);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionService("Error interno", e.getCause());
		} finally {
			manager.close();
		}
		
		return usuario;
	}
	
	public Usuario updateUsuario(Usuario usuario) throws ExceptionService {
		ServiceManager manager = new ServiceManager();
		try {
			UsuarioDAO usuarioDAO = manager.getUsuarioDAO();
			
			manager.beginTransaction();
			usuarioDAO.update(usuario);
			manager.commit();
			
		} catch(Exception e) {
			manager.rollbackClose();
			e.printStackTrace();
			throw new ExceptionService(ErrorMsgs.STANDARD_MSG, e.getCause());
		} finally {
			manager.close();
		}
		
		return usuario;
	}
}
