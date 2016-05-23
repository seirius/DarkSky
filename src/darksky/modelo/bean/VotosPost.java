package darksky.modelo.bean;
// Generated 19-feb-2016 3:19:12 by Hibernate Tools 4.3.1.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import darksky.exceptions.ExceptionBEAN;
import darksky.util.MyUtil;

/**
 * VotosPost generated by hbm2java
 */
@Entity
@Table(name = "votos_post", catalog = "dark_sky")
public class VotosPost implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private VotosPostId id;
	private String valorVoto;

	public VotosPost() {
	}

	public VotosPost(VotosPostId id, String valorVoto) {
		this.id = id;
		this.valorVoto = valorVoto;
	}

	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "usuarioNick", column = @Column(name = "USUARIO_NICK", nullable = false, length = 30) ), @AttributeOverride(name = "idPost", column = @Column(name = "ID_POST", nullable = false) ) })
	public VotosPostId getId() {
		return this.id;
	}

	public void setId(VotosPostId id) {
		if (id == null) throw new ExceptionBEAN("El nick y post no pueden ser nulos");
		this.id = id;
	}

	@Column(name = "VALOR_VOTO", nullable = false, length = 5)
	public String getValorVoto() {
		return this.valorVoto;
	}

	public void setValorVoto(String valorVoto) {
		if (MyUtil.isEmpty(valorVoto)) throw new ExceptionBEAN("El valor de voto no puede ser nulo");
		if (valorVoto.length() > 5) throw new ExceptionBEAN("El valor de voto no puede tener mas de 5 caracteres");
		this.valorVoto = valorVoto;
	}
	
	@Transient
	public int getIntValor() {
		try {
			return Integer.parseInt(valorVoto);
		} catch(NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
}