package darksky.modelo.bean;
// Generated 02-may-2016 18:38:04 by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Notificacion generated by hbm2java
 */
@Entity
@Table(name = "notificacion", catalog = "dark_sky")
public class Notificacion implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Transient
	public final static String PENDIENTE = "PEN";
	@Transient
	public final static String VISTO     = "VISTO";
	
	private Integer id;
	private EventoNotificacion eventoNotificacion;
	private String usuOrigin;
	private String idObjeto1;
	private String idObjeto2;
	private String estado = PENDIENTE;
	private Date fechaCreacion = new Date(System.currentTimeMillis());

	public Notificacion() {
	}

	public Notificacion(EventoNotificacion eventoNotificacion, String usuOrigin, String estado) {
		this.eventoNotificacion = eventoNotificacion;
		this.usuOrigin = usuOrigin;
		this.estado = estado;
	}

	public Notificacion(EventoNotificacion eventoNotificacion, String usuOrigin, String idObjeto1, String idObjeto2, String estado) {
		this.eventoNotificacion = eventoNotificacion;
		this.usuOrigin = usuOrigin;
		this.idObjeto1 = idObjeto1;
		this.idObjeto2 = idObjeto2;
		this.estado = estado;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EVENTO", nullable = false)
	public EventoNotificacion getEventoNotificacion() {
		return this.eventoNotificacion;
	}

	public void setEventoNotificacion(EventoNotificacion eventoNotificacion) {
		this.eventoNotificacion = eventoNotificacion;
	}

	@Column(name = "USU_ORIGIN", nullable = false, length = 30)
	public String getUsuOrigin() {
		return this.usuOrigin;
	}

	public void setUsuOrigin(String usuOrigin) {
		this.usuOrigin = usuOrigin;
	}

	@Column(name = "ID_OBJETO_1", length = 30)
	public String getIdObjeto1() {
		return this.idObjeto1;
	}

	public void setIdObjeto1(String idObjeto1) {
		this.idObjeto1 = idObjeto1;
	}

	@Column(name = "ID_OBJETO_2", length = 30)
	public String getIdObjeto2() {
		return this.idObjeto2;
	}

	public void setIdObjeto2(String idObjeto2) {
		this.idObjeto2 = idObjeto2;
	}

	@Column(name = "ESTADO", nullable = false, length = 45)
	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_CREACION", nullable = true, length = 19)
	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}
