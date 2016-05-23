package darksky.modelo.bean;
// Generated 02-may-2016 18:38:04 by Hibernate Tools 4.3.1.Final

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * EventoNotificacion generated by hbm2java
 */
@Entity
@Table(name = "evento_notificacion", catalog = "dark_sky")
public class EventoNotificacion implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Transient
	public final static String POST_NEW_COMENTARIO  = "POST_NEW_COMENTARIO";
	@Transient
	public final static String POST_NEW_RESPUESTA     = "POST_NEW_RESPUESTA";
	@Transient
	public final static String USUARIO_NEW_COMENTARIO = "USUARIO_NEW_COMENTARIO";
	@Transient
	public final static String USUARIO_NEW_POST       = "USUARIO_NEW_POST";
	
	private String evento;
	private Set<Notificacion> notificacions = new HashSet<Notificacion>(0);

	public EventoNotificacion() {
	}

	public EventoNotificacion(String evento) {
		this.evento = evento;
	}

	public EventoNotificacion(String evento, Set<Notificacion> notificacions) {
		this.evento = evento;
		this.notificacions = notificacions;
	}

	@Id

	@Column(name = "EVENTO", unique = true, nullable = false, length = 50)
	public String getEvento() {
		return this.evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eventoNotificacion")
	public Set<Notificacion> getNotificacions() {
		return this.notificacions;
	}

	public void setNotificacions(Set<Notificacion> notificacions) {
		this.notificacions = notificacions;
	}

}
