package darksky.modelo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import darksky.exceptions.ExceptionDAO;
import darksky.modelo.bean.CategoriaItem;
import darksky.modelo.bean.Inventario;
import darksky.modelo.bean.InventarioId;
import darksky.modelo.bean.Item;
import darksky.modelo.bean.Usuario;

public class ItemDAO {

	private Session session;
	
	public ItemDAO(Session session) {
		this.session = session;
	}
	
	@SuppressWarnings("unchecked")
	public List<Item> getItemsPorCategoria(CategoriaItem categoria) {
		if (categoria == null) throw new ExceptionDAO("Categoria no puede ser nulo");
		
		List<Item> items = null;
		
		try {
			items = session.createCriteria(Item.class)
					.add(Restrictions.eq("categoriaItem", categoria))
					.list();
			return items;
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
	}
	
	public Inventario addInventario(Usuario usuario, Item item) {
		try {
			Inventario inventario = new Inventario();
			inventario.setId(new InventarioId(usuario.getNick(), item.getNombre()));
			inventario.setUsuario(usuario);
			inventario.setItem(item);
			inventario.setCantidad(new Long(1));
			session.save(inventario);
			return inventario;
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
	}
	
	public Inventario addInventario(Usuario usuario, Item item, Long cantidad) {
		try {
			Inventario inventario = new Inventario();
			inventario.setUsuario(usuario);
			inventario.setItem(item);
			inventario.setCantidad(cantidad);
			session.save(inventario);
			return inventario;
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
	}
	
	public Item getItem(String nombre) {
		try {
			Item item = session.get(Item.class, nombre);
			return item;
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
	}
	
	
}
