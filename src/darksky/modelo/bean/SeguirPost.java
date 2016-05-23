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
 * SeguirPost generated by hbm2java
 */
@Entity
@Table(name = "seguir_post", catalog = "dark_sky")
public class SeguirPost implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private SeguirPostId id;

	public SeguirPost() {
	}

	public SeguirPost(SeguirPostId id) {
		this.id = id;
	}

	@EmbeddedId
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "usuOrigin", column = @Column(name = "USU_ORIGIN", nullable = false, length = 30) ), @AttributeOverride(name = "postTarget", column = @Column(name = "POST_TARGET", nullable = false) ) })
	public SeguirPostId getId() {
		return this.id;
	}

	public void setId(SeguirPostId id) {
		this.id = id;
	}

}
