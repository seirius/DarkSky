package darksky.util.interfaces;

import java.util.Date;

import darksky.modelo.bean.Usuario;

public interface Comment {
	
	public void setNivel(int id);
	public int getNivel();
	public String getTexto();
	public Integer getId();
	public Usuario getUsuario();
	public Date getFechaCreacion();
}
