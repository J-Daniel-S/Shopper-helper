package growthCalclulator.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import growthCalclulator.entity.Item;
import growthCalclulator.services.ItemService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = { RequestMethod.POST, RequestMethod.GET,
		RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
@RequestMapping("/shopper-helper")
public class ItemController {

	@Autowired
	private ItemService iRepo;

	@PostMapping
	public List<Item> addItem(@RequestBody Item item) {
		iRepo.save(item);
		return iRepo.findAll();
	}

	@GetMapping
	public List<Item> getItems() {
		return iRepo.findAll();
	}

	@DeleteMapping("/{id}")
	public List<Item> deleteItem(@PathVariable long id) {
		iRepo.deleteItem(id);
		return iRepo.findAll();
	}

	@PutMapping("/{id}")
	public List<Item> moveItemToCart(@PathVariable long id) {
		Item item = iRepo.findById(id);
		item.setInCart(true);
		iRepo.save(item);
		return iRepo.findAll();
	}

	@PutMapping("/list")
	public List<Item> moveItemsToCart() {
		iRepo.moveItemsToCart();
		return iRepo.findAll();
	}

	@DeleteMapping("/cart")
	public List<Item> purchaseCart() {
		iRepo.purchaseCart();
		return iRepo.findAll();
	}

	@DeleteMapping
	public List<Item> purchaseAll() {
		iRepo.purchaseAll();
		return iRepo.findAll();
	}

	@DeleteMapping("/list")
	public List<Item> clearList() {
		iRepo.clearList();
		return iRepo.findAll();
	}

	// for testing
	public ItemController(ItemService iRepo) {
		this.iRepo = iRepo;
	}

}
