package darksky.modelo.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import darksky.exceptions.ExceptionBEAN;
import darksky.exceptions.ExceptionService;
import darksky.modelo.bean.Menu;
import darksky.modelo.dao.MenuDAO;
import darksky.util.ErrorMsgs;
import darksky.util.ServiceManager;

public class MenuService {
	
	public JsonArray getMenusJSON() throws ExceptionService {
		MenuDAO menuDAO = new ServiceManager().getMenuDAO();
		JsonArray jsonArray  = new JsonArray();
		
		try {
			List<Menu> menus = menuDAO.getMenus();
			for (Menu menu: menus) {
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("nombreMenu", menu.getNombreMenu());
				String nombreMenuSuperior = "";
				if (menu.getMenu() != null) {
					nombreMenuSuperior = menu.getMenu().getNombreMenu();
				}
				jsonObject.addProperty("nombreMenuSuperior", nombreMenuSuperior);
				jsonObject.addProperty("nivel", menu.getNivel());
				jsonObject.addProperty("url", menu.getUrl());
				jsonObject.addProperty("orden", menu.getOrden());
				
				jsonArray.add(jsonObject);
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionService(ErrorMsgs.STANDARD_MSG, e.getCause());
		}
		
		return jsonArray;
	}

	public List<Menu> getMenusPorNivel(Long nivel) throws ExceptionService {
		ServiceManager manager = new ServiceManager();
		MenuDAO menuDAO = manager.getMenuDAO();
		
		List<Menu> menus = null;
		
		try {
			menus = menuDAO.getMenusPorNivel(nivel);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionService(ErrorMsgs.STANDARD_MSG, e.getCause());
		}
		
		return menus;
	}
	
	public List<Menu> getMenusPorMenu(String nombreMenu) throws ExceptionService {
		ServiceManager manager = new ServiceManager();
		MenuDAO menuDAO = manager.getMenuDAO();
		
		List<Menu> menus = null;
		
		try {
			menus = menuDAO.getMenusPorMenu(nombreMenu);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionService(ErrorMsgs.STANDARD_MSG, e.getCause());
		}
		
		return menus;
	}
	
	public Menu insertar(String nombreMenu, String nombreMenuSuperior, Long nivel, String url, Long orden) throws ExceptionService {
		ServiceManager manager = new ServiceManager();
		MenuDAO menuDAO = manager.getMenuDAO();
		Menu menu = null;
		try {
			manager.beginTransaction();
			menu = menuDAO.insert(nombreMenu, nombreMenuSuperior, nivel, url, orden);
			manager.commitClose();
		} catch(ExceptionBEAN e) {
			throw new ExceptionBEAN(e);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionService(ErrorMsgs.STANDARD_MSG, e.getCause());
		}
		return menu;
	}
	
	public Menu insertar(String nombreMenu, Long nivel, String url, Long orden) throws ExceptionService {
		ServiceManager manager = new ServiceManager();
		MenuDAO menuDAO = manager.getMenuDAO();
		Menu menu = null;
		try {
			manager.beginTransaction();
			menu = menuDAO.insert(nombreMenu, null, nivel, url, orden);
			manager.commitClose();
		} catch(ExceptionBEAN e) {
			throw new ExceptionBEAN(e);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionService(ErrorMsgs.STANDARD_MSG, e.getCause());
		}
		return menu;
	}
	
	public void eliminar(String nombreMenu) throws ExceptionService {
		ServiceManager manager = new ServiceManager();
		MenuDAO menuDAO = manager.getMenuDAO();
		try {
			manager.beginTransaction();
			menuDAO.delete(nombreMenu);
			manager.commitClose();
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionService(ErrorMsgs.STANDARD_MSG, e.getCause());
		}
	}
	
}
