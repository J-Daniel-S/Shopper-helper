package growthCalclulator.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import growthCalclulator.entity.Item;
import growthCalclulator.services.ItemService;

public class ItemControllerTest {

	@Mock
	ItemService iRepo;

	ItemController controller;

	List<Item> items;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new ItemController(iRepo);
		items = new ArrayList<Item>();
		items.add(new Item(1, "test1", false));
		items.add(new Item(2, "test2", false));
		items.add(new Item(3, "test3", true));
	}

	@Test
	public void testAddItem() {
		// given
		List<Item> newItems = items;
		Item newItem = new Item(4, "test4", false);
		newItems.add(newItem);
		when(iRepo.findAll()).thenReturn(newItems);

		// when
		List<Item> returnedItems = controller.addItem(newItem);

		// then
		assertThat(returnedItems).contains(newItem, atIndex(3));
	}

	@Test
	public void testGetItems() {
		// given
		when(iRepo.findAll()).thenReturn(items);

		// when
		List<Item> returnedItems = controller.getItems();

		// then
		assertThat(returnedItems.size()).isEqualTo(3);
	}

	@Test
	public void testGetListItems() {
		// given
		when(iRepo.findAllOnList()).thenReturn(items.stream().filter(i -> !i.getInCart()).collect(Collectors.toList()));

		// when
		List<Item> returnedItems = controller.getItems();

		// then
		returnedItems.stream().forEach(i -> assertThat(i.getInCart()).isFalse());
	}

	@Test
	public void testDeleteItem() {
		// given
		when(iRepo.findAll()).thenReturn(items.stream().filter(i -> i.getItemId() != 1).collect(Collectors.toList()));

		// when
		List<Item> returnedItems = controller.deleteItem(1);

		// then
		assertThat(returnedItems.size()).isEqualTo(2);
	}

	@Test
	public void testMoveItemToCart() {
		// given
		List<Item> after = items.stream().collect(Collectors.toList());
		after.get(0).setInCart(true);
		when(iRepo.findAll()).thenReturn(after);
		when(iRepo.findById(anyLong())).thenReturn(items.stream().filter(i -> i.getItemId() == 1).findAny().get());

		// when
		List<Item> returnedItems = controller.moveItemToCart(1);

		// then
		assertThat(returnedItems.stream().filter(i -> i.getItemId() == 1).findAny().get().getInCart()).isTrue();
	}

	@Test
	public void testMoveItemsToCart() {
		// given
		List<Item> theItems = items.stream().collect(Collectors.toList());
		theItems.stream().forEach(i -> i.setInCart(true));
		when(iRepo.moveItemsToCart()).thenReturn(theItems);

		// when
		List<Item> returnedItems = controller.moveItemsToCart();

		// then
		returnedItems.stream().forEach(i -> assertThat(i.getInCart()).isTrue());
	}

	@Test
	public void testPurchaseCart() {
		// given
		List<Item> theItems = items.stream().filter(i -> !i.getInCart()).map(i -> i).collect(Collectors.toList());
		when(iRepo.findAll()).thenReturn(theItems);

		// when
		List<Item> returnedItems = controller.purchaseCart();

		// then
		returnedItems.stream().forEach(i -> assertThat(i.getInCart()).isFalse());
		assertThat(returnedItems.size()).isEqualTo(2);
	}

	@Test
	public void testPurchaseAll() {
		// given
		when(iRepo.purchaseAll()).thenReturn(new ArrayList<Item>());

		// when
		List<Item> returnedItems = controller.purchaseAll();

		// then
		assertThat(returnedItems.size()).isZero();
	}

	@Test
	public void testClearList() {
		// given
		List<Item> theItems = items.stream().collect(Collectors.toList());
		theItems = theItems.stream().filter(i -> i.getInCart()).map(i -> i).collect(Collectors.toList());
		when(iRepo.findAll()).thenReturn(theItems);

		// when
		List<Item> returnedItems = controller.clearList();

		// then
		assertThat(returnedItems.size()).isOne();
		returnedItems.stream().forEach(i -> assertThat(i.getInCart()).isTrue());
	}

}
