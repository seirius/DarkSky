package darksky.modelo.bean;
// Generated 15-abr-2016 18:43:39 by Hibernate Tools 4.3.1.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SeguirUsuario generated by hbm2java
 */
@Entity
@Table(name = "seguir_usuario", catalog = "dark_sky")
public class SeguirUsuario implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private SeguirUsuarioId id;

	public SeguirUsuario() {
	}

	public SeguirUsuario(SeguirUsuarioId id) {
		this.id = id;
	}

	@EmbeddedId
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "usuOrigin", column = @Column(name = "USU_ORIGIN", nullable = false, length = 30) ), @AttributeOverride(name = "usuTarget", column = @Column(name = "USU_TARGET", nullable = false, length = 30) ) })
	public SeguirUsuarioId getId() {
		return this.id;
	}

	public void setId(SeguirUsuarioId id) {
		this.id = id;
	}

}