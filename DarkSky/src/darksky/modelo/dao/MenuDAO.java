package darksky.modelo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import darksky.exceptions.ExceptionBEAN;
import darksky.exceptions.ExceptionDAO;
import darksky.modelo.bean.Menu;
import darksky.util.ErrorMsgs;

public class MenuDAO {

	private Session session;
	
	public MenuDAO(Session session) {
		this.session = session;
	}
	
	@SuppressWarnings("unchecked")
	public List<Menu> getMenus() {
		List<Menu> menus = null;
		
		try {
			Criteria criteria = session.createCriteria(Menu.class).addOrder(Order.asc("orden"));
			menus = criteria.list();
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
		
		return menus;
	}
	
	@SuppressWarnings("unchecked")
	public List<Menu> getMenusPorNivel(Long nivel) {
		List<Menu> menus = null;
		try {
			Criteria criteria = session.createCriteria(Menu.class)
											.add(Restrictions.eq("nivel", nivel))
											.addOrder(Order.asc("orden"));
			
			menus = criteria.list();
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
		
		return menus;
	}
	
	@SuppressWarnings("unchecked")
	public List<Menu> getMenusPorMenu(String nombreMenu) {
		List<Menu> menus = null;
		try {
			Menu menu = new Menu(nombreMenu);
			Criteria criteria = session.createCriteria(Menu.class)
											.add(Restrictions.eq("menu", menu))
											.addOrder(Order.asc("orden"));
			
			menus = criteria.list();
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
		
		return menus;
	}
	
	public Menu insert(String nombreMenu, String nombreMenuSuperior, Long nivel, String url, Long orden) {
		Menu menu = null;
		
		if (nombreMenuSuperior.equals("")) nombreMenuSuperior = null;
		
		try {
			menu = new Menu();
			menu.setNombreMenu(nombreMenu);
			if (nombreMenuSuperior != null) { menu.setMenu(new Menu(nombreMenuSuperior)); }
			menu.setNivel(nivel);
			menu.setUrl(url);
			menu.setOrden(orden);
			session.saveOrUpdate(menu);
		} catch(ExceptionBEAN e) {
			throw new ExceptionBEAN(e);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO("Error interno", e.getCause());
		}
		
		return menu;
	}
	
	public void delete(String nombreMenu) {
		try {
			session.delete(new Menu(nombreMenu));
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO(ErrorMsgs.STANDARD_MSG, e.getCause());
		}
	}
	
	public Menu getMenu(String nombreMenu) {
		Menu menu = null;
		try {
			menu = session.get(Menu.class, nombreMenu);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionDAO(ErrorMsgs.STANDARD_MSG, e.getCause());
		}
		
		return menu;
	}
	
}
