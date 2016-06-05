package darksky.modelo.service;

import java.util.List;

import darksky.modelo.bean.CategoriaItem;
import darksky.modelo.bean.Item;
import darksky.modelo.dao.ItemDAO;
import darksky.util.ServiceManager;

public class ItemService {

	public List<Item> getItemPorCategoria(CategoriaItem categoria) {
		ServiceManager manager = new ServiceManager();
		ItemDAO itemDAO = manager.getItemDAO();
		
		List<Item> items = null;
		try {
			items = itemDAO.getItemsPorCategoria(categoria);
			return items;
		} finally {
			manager.close();
		}
	}
}
