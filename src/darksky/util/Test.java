package darksky.util;

import java.util.List;

import darksky.exceptions.ExceptionService;
import darksky.modelo.bean.Menu;
import darksky.modelo.service.MenuService;

public class Test {

	public static void main(String[] args) throws ExceptionService {
		MenuService menuService = new MenuService();
		
		for (Menu menu: menuService.getMenusPorNivel(new Long(0))) {
			String menuNombre = menu.getNombreMenu();
			List<Menu> menus = menuService.getMenusPorMenu(menuNombre);
			for (Menu menu2: menus) {
				System.out.println(menu2);
			}
		}
	}
}
