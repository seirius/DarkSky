package darksky.modelo.bean;
// Generated 15-abr-2016 18:43:39 by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * SeguirUsuarioId generated by hbm2java
 */
@Embeddable
public class SeguirUsuarioId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String usuOrigin;
	private String usuTarget;

	public SeguirUsuarioId() {
	}

	public SeguirUsuarioId(String usuOrigin, String usuTarget) {
		this.usuOrigin = usuOrigin;
		this.usuTarget = usuTarget;
	}

	@Column(name = "USU_ORIGIN", nullable = false, length = 30)
	public String getUsuOrigin() {
		return this.usuOrigin;
	}

	public void setUsuOrigin(String usuOrigin) {
		this.usuOrigin = usuOrigin;
	}

	@Column(name = "USU_TARGET", nullable = false, length = 30)
	public String getUsuTarget() {
		return this.usuTarget;
	}

	public void setUsuTarget(String usuTarget) {
		this.usuTarget = usuTarget;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SeguirUsuarioId))
			return false;
		SeguirUsuarioId castOther = (SeguirUsuarioId) other;

		return ((this.getUsuOrigin() == castOther.getUsuOrigin()) || (this.getUsuOrigin() != null && castOther.getUsuOrigin() != null && this.getUsuOrigin().equals(castOther.getUsuOrigin()))) && ((this.getUsuTarget() == castOther.getUsuTarget()) || (this.getUsuTarget() != null && castOther.getUsuTarget() != null && this.getUsuTarget().equals(castOther.getUsuTarget())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getUsuOrigin() == null ? 0 : this.getUsuOrigin().hashCode());
		result = 37 * result + (getUsuTarget() == null ? 0 : this.getUsuTarget().hashCode());
		return result;
	}

}