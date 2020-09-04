package growthCalclulator.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import growthCalclulator.dao.ItemRepository;
import growthCalclulator.entity.Item;

@Service
public class ItemService {

	@Autowired
	ItemRepository iRepo;

	public Item save(Item item) {
		return iRepo.save(item);
	}

	public List<Item> findAll() {
		return iRepo.findAll();
	}

	public List<Item> findAllOnList() {
		List<Item> items = iRepo.findAll();
		List<Item> listItems = items.stream().filter(i -> !i.getInCart()).collect(Collectors.toList());
		return listItems;
	}

	public List<Item> findAllInCart() {
		List<Item> items = iRepo.findAll();
		List<Item> listItems = items.stream().filter(i -> i.getInCart()).collect(Collectors.toList());
		return listItems;
	}

	public Item findById(long id) {
		return iRepo.findById(id);
	}

	public List<Item> deleteItem(long id) {
		iRepo.deleteById(id);
		return iRepo.findAll();
	}

	// returns items on the list after moving the item
	public List<Item> moveItemToCart(long id) {
		Item item = iRepo.findById(id);
		item.setInCart(true);
		iRepo.save(item);
		return iRepo.findAll();
	}

	public List<Item> moveItemsToCart() {
		List<Item> items = iRepo.findAll();
		items.stream().forEach(i -> i.setInCart(true));
		iRepo.deleteAll();
		iRepo.saveAll(items);
		return iRepo.findAll();
	}

	public List<Item> purchaseCart() {
		List<Item> items = iRepo.findAll();
		List<Item> cartBuy = items.stream().filter(i -> i.getInCart()).collect(Collectors.toList());
		iRepo.deleteAll(cartBuy);
		return iRepo.findAll();
	}

	public List<Item> purchaseAll() {
		iRepo.deleteAll();
		return iRepo.findAll();
	}

	public List<Item> clearList() {
		List<Item> items = iRepo.findAll();
		List<Item> notList = items.stream().filter(i -> !i.getInCart()).collect(Collectors.toList());
		iRepo.deleteAll(notList);
		return iRepo.findAll();

	}

	// for testing
	public ItemService(ItemRepository iRepo) {
		this.iRepo = iRepo;
	}
}
