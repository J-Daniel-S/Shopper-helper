package growthCalclulator.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import growthCalclulator.dao.ItemRepository;
import growthCalclulator.entity.Item;

public class ItemServiceTest {

	@Mock
	ItemRepository iRepo;

	ItemService iServ;

	List<Item> items;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		iServ = new ItemService(iRepo);

		items = new ArrayList<>();
		items.add(new Item(1, "test1", false));
		items.add(new Item(2, "test2", true));
		items.add(new Item(3, "test3", false));
		items.add(new Item(4, "test4", true));
		items.add(new Item(5, "test5", false));
	}

	@Test
	public void testSave() {
		// given
		when(iRepo.save(any(Item.class))).thenReturn(new Item());

		// when
		Item item = iServ.save(new Item());

		// then
		assertThat(item).isNotNull();
	}

	@Test
	public void testFindAll() {
		// given
		when(iRepo.findAll()).thenReturn(items);

		// when
		List<Item> theItems = iServ.findAll();

		// then
		assertTrue(theItems.size() == 5);
	}

	@Test
	public void testFindAllOnList() {
		// given
		when(iRepo.findAll()).thenReturn(items);

		// when
		List<Item> theItems = iServ.findAllOnList();

		// then
		assertTrue(theItems.size() == 3);
	}

	@Test
	public void testFindAllInCart() {
		// given
		when(iRepo.findAll()).thenReturn(items);

		// when
		List<Item> theItems = iServ.findAllInCart();

		// then
		assertTrue(theItems.size() == 2);
	}

	@Test
	public void testFindById() {
		// given
		when(iRepo.findById(2)).thenReturn(items.get(1));

		// when
		Item item = iServ.findById(2);

		// then
		assertEquals(items.get(1).getItemId(), item.getItemId());
	}

	@Test
	public void testDeleteItem() {
		// given
		List<Item> newList = items.stream().filter(i -> i.getItemId() != 3).collect(Collectors.toList());
		when(iRepo.findAll()).thenReturn(newList);

		// when
		List<Item> testList = iServ.deleteItem(2);

		// then
		assertEquals(4, testList.size());
	}

	@Test
	public void testMoveItemToCart() {
		// given
		when(iRepo.findAll()).thenReturn(items);
		when(iRepo.findById(anyLong())).thenReturn(items.get(4));

		// when
		List<Item> theItems = iServ.moveItemToCart(5);

		// then
		assertTrue(theItems.get(4).getInCart());
	}

	@Test
	public void testMoveItemsToCart() {
		// given
		when(iRepo.findAll()).thenReturn(items);

		// when
		List<Item> theItems = iServ.moveItemsToCart();

		// then
		assertTrue(theItems.get(0).getInCart());
		assertTrue(theItems.get(2).getInCart());
		assertTrue(theItems.get(4).getInCart());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testPurchaseCart() {
		// given
		when(iRepo.findAll()).thenReturn(items,
				items.stream().filter(i -> !i.getInCart()).collect(Collectors.toList()));

		// when
		List<Item> theItems = iServ.purchaseCart();

		// then
		assertEquals(3, theItems.size());
	}

	@Test
	public void testPurchaseAll() {
		// given
		when(iRepo.findAll()).thenReturn(new ArrayList<Item>());

		// when
		List<Item> theItems = iServ.purchaseAll();

		// then
		assertThat(theItems.size()).isZero();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testClearList() {
		// given
		when(iRepo.findAll()).thenReturn(items, items.stream().filter(i -> i.getInCart()).collect(Collectors.toList()));

		// when
		List<Item> theItems = iServ.clearList();

		// then
		assertEquals(2, theItems.size());
	}

}
