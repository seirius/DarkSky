package darksky.modelo.bean;
// Generated 11-feb-2016 3:11:32 by Hibernate Tools 4.3.1.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import darksky.exceptions.ExceptionDAO;
import darksky.util.MyUtil;

/**
 * Item generated by hbm2java
 */
@Entity
@Table(name = "item", catalog = "dark_sky")
public class Item implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private CategoriaItem categoriaItem;
	private Imagen imagen;
	private long precio;
	private String cantidadMax = "INFINITO"; //Default infinito
	private Set<Inventario> inventarios = new HashSet<Inventario>(0);

	public Item() {
	}

	public Item(String nombre, CategoriaItem categoriaItem, long precio) {
		this.setNombre(nombre);
		this.setCategoriaItem(categoriaItem);
		this.setPrecio(precio);
	}

	public Item(String nombre, CategoriaItem categoriaItem, Imagen imagen, long precio, String cantidadMax, Set<Inventario> inventarios) {
		this.setNombre(nombre);
		this.setCategoriaItem(categoriaItem);
		this.imagen = imagen;
		this.setPrecio(precio);
		this.setCantidadMax(cantidadMax);
		this.inventarios = inventarios;
	}

	@Id

	@Column(name = "NOMBRE", unique = true, nullable = false, length = 30)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		if (MyUtil.isEmpty(nombre)) {
			throw new ExceptionDAO("Nombre de item no puede ser nulo");
		} else if (nombre.length() > 30) {
			throw new ExceptionDAO("Nombre de item no puede tener mas de 30 caracteres");
		}
		
		this.nombre = nombre;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORIA", nullable = false)
	public CategoriaItem getCategoriaItem() {
		return this.categoriaItem;
	}

	public void setCategoriaItem(CategoriaItem categoriaItem) {
		if (categoriaItem == null) throw new ExceptionDAO("Categoria no puede ser nula");
		
		this.categoriaItem = categoriaItem;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IMAGEN")
	public Imagen getImagen() {
		return this.imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

	@Column(name = "PRECIO", nullable = false, precision = 10, scale = 0)
	public long getPrecio() {
		return this.precio;
	}

	public void setPrecio(Long precio) {
		if (precio == null) throw new ExceptionDAO("Precio no puede ser nulo");
		
		this.precio = precio;
	}

	@Column(name = "CANTIDAD_MAX", length = 10)
	public String getCantidadMax() {
		return this.cantidadMax;
	}

	public void setCantidadMax(String cantidadMax) {
		try {
			Integer.parseInt(cantidadMax);
		} catch(NumberFormatException e) {
			throw new ExceptionDAO("Cantidad maxima tiene que ser un entero");
		}
		
		this.cantidadMax = cantidadMax;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
	public Set<Inventario> getInventarios() {
		return this.inventarios;
	}

	public void setInventarios(Set<Inventario> inventarios) {
		this.inventarios = inventarios;
	}

}
