package darksky.modelo.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import darksky.exceptions.ExceptionDAO;
import darksky.exceptions.ExceptionService;
import darksky.modelo.bean.EventoNotificacion;
import darksky.modelo.bean.Notificacion;
import darksky.util.ErrorMsgs;

public class NotificacionDAO {

	private Session session;
	
	public NotificacionDAO(Session session) {
		this.session = session;
	}
	
	public Notificacion insert(Notificacion notificacion) {

		try {
			session.save(notificacion);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
		
		return notificacion;
	}
	
	public void delete(Notificacion notificacion) {
		try {
			session.delete(notificacion);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
	}
	
	public void update(Notificacion notificacion) {
		try {
			session.update(notificacion);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO(ErrorMsgs.INTERN_ERROR, e.getCause());
		}
	}
	
	public Notificacion getNotificacion(int idNotificacion) {
		Notificacion notificacion = null;
		
		try {
			notificacion = session.get(Notificacion.class, idNotificacion);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO(ErrorMsgs.INTERN_ERROR, e.getCause());
		}
		
		return notificacion;
	}
	
	/**
	 * Devuelve todas las notificaciones del usuario con un o varios eventos dados, ordenados por fecha (desc)
	 * 
	 * @param nick
	 * @param evento Una array de eventos
	 * @param estado 
	 * @param limit  El numero de filas que se quiere devolver. Con valor 0 no hace limite.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Notificacion> getAllNotificacionesPorUsuario(String nick, String[] evento, String estado, int limit) {
		
		List<Notificacion> listaNotificaciones = new ArrayList<Notificacion>();
		
		if (evento.length == 0) {
			return listaNotificaciones;
		}
		
		try {
			Criteria criteria = session.createCriteria(Notificacion.class);
			criteria.add(Restrictions.eq("usuOrigin", nick));
			criteria.add(Restrictions.eq("estado", estado));
			Disjunction disjunction = Restrictions.disjunction();
			for (int i = 0; i < evento.length; i++) {
				disjunction.add(Restrictions.eq("eventoNotificacion.evento", evento[i]));
			}
			criteria.add(disjunction);
			if (limit > 0) {
				criteria.setMaxResults(limit);
			}
			
			listaNotificaciones = criteria.list();
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
		
		return listaNotificaciones;
	}
	
	/**
	 * Recupera todas las notificacion de un post dirigidos a un usuario para los eventos: NEW_COMENTARIO y NEW_RESPUESTA
	 * 
	 * @param nick
	 * @param idPost
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Notificacion> getAllNotificacionesPorPostUsuario(String nick, int idPost) {
		List<Notificacion> listaNotificaciones = new ArrayList<Notificacion>();
		
		try {
			String idPostS = String.valueOf(idPost);
			
			String sql = ""
					+ "SELECT ID, USU_ORIGIN, EVENTO, ID_OBJETO_1, ID_OBJETO_2, ESTADO, FECHA_CREACION "
					+ "FROM NOTIFICACION "
					+ "WHERE "
					+ "USU_ORIGIN = :USU_ORIGIN "
					+ "AND "
					+ "ID_OBJETO_1 = :ID_OBJETO_1 "
					+ "AND "
					+ "(EVENTO = :EVENTO1 OR EVENTO = :EVENTO2) ";
			Query query = session.createSQLQuery(sql).addEntity(Notificacion.class);
			query.setParameter("USU_ORIGIN", nick);
			query.setParameter("ID_OBJETO_1", idPostS);
			query.setParameter("EVENTO1", EventoNotificacion.POST_NEW_COMENTARIO);
			query.setParameter("EVENTO2", EventoNotificacion.POST_NEW_RESPUESTA);
			
			listaNotificaciones = query.list();
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
		
		return listaNotificaciones;
	}
	
	/**
	 * Borra todas las notificacion del post indicado para el usuario indicado. Con evento de NEW_COMENTARIO y NEW_RESPUESTA
	 * 
	 * @param nick
	 * @param idPost
	 * @return
	 */
	public int deleteAllNotificacionesPorPostUsuario(String nick, int idPost) {
		int numeroRegistros = 0;
		
		try {
			String idPostS = String.valueOf(idPost);
			
			String sql = ""
					+ "DELETE "
					+ "FROM NOTIFICACION "
					+ "WHERE USU_ORIGIN = :USU_ORIGIN "
					+ "AND "
					+ "ID_OBJETO_1 = :ID_OBJETO_1 "
					+ "AND "
					+ "(EVENTO = :EVENTO1 OR EVENTO = :EVENTO2) ";
			Query query = session.createSQLQuery(sql).addEntity(Notificacion.class);
			query.setParameter("USU_ORIGIN", nick);
			query.setParameter("ID_OBJETO_1", idPostS);
			query.setParameter("EVENTO1", EventoNotificacion.POST_NEW_COMENTARIO);
			query.setParameter("EVENTO2", EventoNotificacion.POST_NEW_RESPUESTA);
			
			numeroRegistros = query.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return numeroRegistros;
	}
	
	/**
	 * Crea una nueva notificacion con estado PENDIENTE
	 * 
	 * @param usuOrigen Usuario al que va dirigido
	 * @param evento Evento a partir del cual se crea la notificacion
	 * @param idObject1 Depende del estado. En caso de: NEW_COMENTARIO/RESPUESTA: idPost, NEW_COMENTARIO/POST_USUARIO: Nick_Usuario_Target
	 * @param idObject2 Depende del estado. En caso de: NEW_COMENTARIO/RESPUESTA: idComentario/Respuesta, NEW_COMENTARIO/POST_USUARIO: idComentario/Respuesta
	 * @return
	 * @throws ExceptionService
	 */
	public Notificacion nuevaNotificacion(String usuOrigen, String evento, String idObject1, String idObject2) throws ExceptionService {
		return nuevaNotificacion(usuOrigen, evento, idObject1, idObject2, Notificacion.PENDIENTE);
	}
	
	/**
	 * Crea una nueva notificacion
	 * 
	 * @param usuOrigen Usuario al que va dirigido
	 * @param evento Evento a partir del cual se crea la notificacion
	 * @param idObject1 Depende del evento. En caso de: NEW_COMENTARIO/RESPUESTA: idPost, NEW_COMENTARIO/POST_USUARIO: Nick_Usuario_Target
	 * @param idObject2 Depende del evento. En caso de: NEW_COMENTARIO/RESPUESTA: idComentario/Respuesta, NEW_COMENTARIO/POST_USUARIO: idComentario/Respuesta
	 * @param estado Estado en el que estara la notificacion. Todos los estados posibles estan en la clase Notificacion en forma de Strings staticos.
	 * @return
	 * @throws ExceptionService
	 */
	public Notificacion nuevaNotificacion(String usuOrigen, String evento, String idObject1, String idObject2, String estado) throws ExceptionService {

		Notificacion notificacion = null;
		
		try {
			EventoNotificacion eventoNotificacion = new EventoNotificacion();
			eventoNotificacion.setEvento(evento);
			
			notificacion = new Notificacion();
			notificacion.setUsuOrigin(usuOrigen);
			notificacion.setIdObjeto1(idObject1);
			notificacion.setIdObjeto2(idObject2);
			notificacion.setEventoNotificacion(eventoNotificacion);
			notificacion.setEstado(estado);
			
			insert(notificacion);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
		
		return notificacion;
	}
	
	@SuppressWarnings("unchecked")
	public List<EventoNotificacion> getAllEventos() throws ExceptionService {
		List<EventoNotificacion> eventos = null;
		
		try {
			eventos = session.createCriteria(EventoNotificacion.class).list();
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO(ErrorMsgs.INTERN_ERROR, e.getCause());
		}
		
		return eventos;
	}
	
	public int getNotificacionCountPorUsuario(String nick, String[] evento, String estado) {
		int count = 0;
		
		try {
			Criteria criteria = session.createCriteria(Notificacion.class);
			criteria.add(Restrictions.eq("usuOrigin", nick));
			criteria.add(Restrictions.eq("estado", estado));
			Disjunction disjunction = Restrictions.disjunction();
			for (int i = 0; i < evento.length; i++) {
				disjunction.add(Restrictions.eq("eventoNotificacion.evento", evento[i]));
			}
			criteria.add(disjunction);
			
			Long longCount = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
			count = longCount.intValue();
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO(ErrorMsgs.INTERN_ERROR, e.getCause());
		}
		
		return count;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
