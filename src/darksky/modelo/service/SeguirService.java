package darksky.modelo.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import darksky.exceptions.ExceptionService;
import darksky.modelo.bean.Comentario;
import darksky.modelo.bean.EventoNotificacion;
import darksky.modelo.bean.Notificacion;
import darksky.modelo.bean.Respuesta;
import darksky.modelo.bean.SeguirPost;
import darksky.modelo.bean.SeguirUsuario;
import darksky.modelo.dao.NotificacionDAO;
import darksky.modelo.dao.SeguirDAO;
import darksky.util.ErrorMsgs;
import darksky.util.ServiceManager;

public class SeguirService {

	public SeguirPost followPost(String usuOrigen, int idPost) {
		ServiceManager manager = new ServiceManager();
		SeguirDAO seguirDAO = manager.getSeguirDAO();
		
		SeguirPost seguirPost = null;
		
		try {
			manager.beginTransaction();
			seguirPost = seguirDAO.insertarSeguirPost(usuOrigen, idPost);
		} finally {
			manager.commitClose();
		}
		
		return seguirPost;
	}
	
	public void unfollowPost(String usuOrigen, int idPost) {
		ServiceManager manager = new ServiceManager();
		SeguirDAO seguirDAO = manager.getSeguirDAO();
		NotificacionDAO notificacionDAO = manager.getNotificacionDAO();
		
		try {
			manager.beginTransaction();
			seguirDAO.deleteSeguirPost(usuOrigen, idPost);
			notificacionDAO.deleteAllNotificacionesPorPostUsuario(usuOrigen, idPost);
		} finally {
			manager.commitClose();
		}
	}
	
	public SeguirUsuario followUsuario(String usuOrigen, String usuTarget) {
		ServiceManager manager = new ServiceManager();
		SeguirDAO seguirDAO = manager.getSeguirDAO();
		
		SeguirUsuario seguirUsuario = new SeguirUsuario();
		
		try {
			manager.beginTransaction();
			seguirUsuario = seguirDAO.insertSeguirUsuario(usuOrigen, usuTarget);
		} finally {
			manager.commitClose();
		}
		
		return seguirUsuario;
	}
	
	public void unfollowUsuario(String usuOrigen, String usuTarget) {
		ServiceManager manager = new ServiceManager();
		SeguirDAO seguirDAO = manager.getSeguirDAO();
		
		try {
			manager.beginTransaction();
			seguirDAO.deleteSeguirUsuario(usuOrigen, usuTarget);
		} finally {
			manager.commitClose();
		}
	}
	
	public SeguirPost getSeguirPost(String usuOrigen, int idPost) {
		ServiceManager manager = new ServiceManager();
		SeguirDAO seguirDAO = manager.getSeguirDAO();
		
		SeguirPost seguirPost = null;
		
		try {
			seguirPost = seguirDAO.getSeguirPost(usuOrigen, idPost);
		} finally {
			manager.close();
		}
		
		return seguirPost;
	}

	public boolean siguiendoPost(String usuOrigen, int idPost) {
		ServiceManager manager = new ServiceManager();
		SeguirDAO seguirDAO = manager.getSeguirDAO();
		
		SeguirPost seguirPost = null;
		
		try {
			seguirPost = seguirDAO.getSeguirPost(usuOrigen, idPost);
		} finally {
			manager.close();
		}
		
		if (seguirPost == null) return false;
		return true;
	}
	
	/**
	 * Devuelve notificaciones dirigidas a un usuario y con eventos relacionados al post.
	 * 
	 * @param usuario
	 * @param estado 
	 * @param limit Si es 0 no habra limite
	 * @return
	 * @throws ExceptionService
	 */
	public List<Notificacion> getAllNotificacionPorUsuario_Entrada(String usuario, String estado, int limit) throws ExceptionService {
		List<Notificacion> notificaciones = null;
		ServiceManager manager = new ServiceManager();
		NotificacionDAO notificacionDAO = manager.getNotificacionDAO();
		
		try {
			String[] evento = new String[2];
			evento[0] = EventoNotificacion.POST_NEW_COMENTARIO;
			evento[1] = EventoNotificacion.POST_NEW_RESPUESTA;
			notificaciones = notificacionDAO.getAllNotificacionesPorUsuario(usuario, evento, estado, limit);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionService("Error Interno", e.getCause());
		} finally {
			manager.close();
		}
		
		return notificaciones;
	}
	
	public List<Notificacion> getAllNotificacion(String usuario, String estado, int limit) throws ExceptionService {
		List<Notificacion> notificaciones = null;
		
		try {
			List<String> evento = getAllEventos();
			
			ServiceManager manager = new ServiceManager();
			NotificacionDAO notificacionDAO = manager.getNotificacionDAO();
			notificaciones = notificacionDAO.getAllNotificacionesPorUsuario(usuario, evento.toArray(new String[0]), estado, limit);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionService(ErrorMsgs.INTERN_ERROR, e.getCause());
		}
		
		return notificaciones;
	}
	
	/**
	 * Prepara el retorno en forma de una array de JSONs. Si se quiere usar en java la array recordar que hay que recuperarla
	 * con .getAsJsonArray().
	 * 
	 * @param usuario
	 * @param estado
	 * @param limit
	 * @return
	 * @throws ExceptionService
	 */
	public JsonArray getAllJsonNotificacionPorUsuario_Entrada(String usuario, String estado, int limit) throws ExceptionService {
		JsonArray jsonArray = null;
		try {
			ServiceManager manager          = new ServiceManager();
			NotificacionDAO notificacionDAO = manager.getNotificacionDAO();
			PostService postService         = new PostService();
			jsonArray                       = new JsonArray();
			
			String[] evento = new String[2];
			evento[0] = EventoNotificacion.POST_NEW_COMENTARIO;
			evento[1] = EventoNotificacion.POST_NEW_RESPUESTA;
			List<Notificacion> notificaciones = notificacionDAO.getAllNotificacionesPorUsuario(usuario, evento, estado, limit);
			
			for (Notificacion notificacion: notificaciones) {
				int idNotificacion = notificacion.getId();
				int idPost         = Integer.parseInt(notificacion.getIdObjeto1());
				int idComment      = Integer.parseInt(notificacion.getIdObjeto2());
				String eventoNoti  = notificacion.getEventoNotificacion().getEvento();
				String usuOrigin   = notificacion.getUsuOrigin();
				String usuTarget   = "";
				String commentText = "";
				String postTitulo  = postService.getPost(idPost).getTitulo();
				
				
				if (eventoNoti.equals(EventoNotificacion.POST_NEW_COMENTARIO)) {
					Comentario comentario = postService.getComentario(idComment);
					
					usuTarget   = comentario.getUsuario().getNick();
					commentText = comentario.getTexto();
				} else if (eventoNoti.equals(EventoNotificacion.POST_NEW_RESPUESTA)) {
					Respuesta respuesta = postService.getRespuesta(idComment);
					
					usuTarget   = respuesta.getUsuario().getNick();
					commentText = respuesta.getTexto();
				}
				
				JsonObject jsonNotificacion = new JsonObject();
				jsonNotificacion.addProperty("idNotificacion", idNotificacion);
				jsonNotificacion.addProperty("idPost",         idPost);
				jsonNotificacion.addProperty("idComment",      idComment);
				jsonNotificacion.addProperty("evento",         eventoNoti);
				jsonNotificacion.addProperty("usuOrigin",      usuOrigin);
				jsonNotificacion.addProperty("usuTarget",      usuTarget);
				jsonNotificacion.addProperty("commentText",    commentText);
				jsonNotificacion.addProperty("postTitulo",     postTitulo);
				jsonNotificacion.addProperty("estado",         estado);
				
				jsonArray.add(jsonNotificacion);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionService(ErrorMsgs.INTERN_ERROR, e.getCause());
		}
		
		return jsonArray;
	}
	
	public void marcarComoVistaNotificacion(int idNotificacion) throws ExceptionService {
		try {
			ServiceManager manager = new ServiceManager();
			manager.beginTransaction();
			
			NotificacionDAO notificacionDAO = manager.getNotificacionDAO();
			Notificacion notificacion = notificacionDAO.getNotificacion(idNotificacion);
			notificacion.setEstado(Notificacion.VISTO);
			notificacionDAO.update(notificacion);
			
			manager.commitClose();
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionService(ErrorMsgs.INTERN_ERROR, e.getCause());
		}
	}
	
	public void marcarComoVistasNotificaciones_UsuarioPost(String nick, int idPost) throws ExceptionService {
		try {
			ServiceManager manager = new ServiceManager();
			NotificacionDAO notificacionDAO = manager.getNotificacionDAO();
			manager.beginTransaction();
			
			List<Notificacion> notificaciones = notificacionDAO.getAllNotificacionesPorPostUsuario(nick, idPost);
			
			for (Notificacion notificacion: notificaciones) {
				if (notificacion.getEstado().equals(Notificacion.PENDIENTE)) {
					notificacion.setEstado(Notificacion.VISTO);
					notificacionDAO.update(notificacion);
				}
			}
			
			
			manager.commitClose();
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionService(ErrorMsgs.INTERN_ERROR, e.getCause());
		}
	}
	
	public List<String> getAllEventos() throws ExceptionService {
		List<String> eventos = null;
		
		try {
			ServiceManager manager = new ServiceManager();
			NotificacionDAO notificacionDAO = manager.getNotificacionDAO();
			eventos = new ArrayList<String>();
			
			List<EventoNotificacion> eventosNotificacion = notificacionDAO.getAllEventos();
			for (EventoNotificacion eventoNotificacion: eventosNotificacion) {
				eventos.add(eventoNotificacion.getEvento());
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionService(ErrorMsgs.INTERN_ERROR, e.getCause());
		}
		
		return eventos;
	}
	
	public int getNotificacionCountPorUsuario(String nick, String[] evento, String estado) throws ExceptionService {
		int count = 0;
		
		try {
			ServiceManager manager = new ServiceManager();
			NotificacionDAO notificacionDAO = manager.getNotificacionDAO();
			
			count = notificacionDAO.getNotificacionCountPorUsuario(nick, evento, estado);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionService(ErrorMsgs.INTERN_ERROR, e.getCause());
		}
		
		return count;
	}
	
	
	
}
