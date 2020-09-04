package growthCalclulator.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ItemTest {

	Item theItem;

	@Before
	public void setup() {
		theItem = new Item("test", 1.1, 10, false);
	}

	@Test
	public void testItemStringDoubleBoolean() {
		Item item = new Item("test", 1.1, false);
		assertThat(item).isNotNull();
	}

	@Test
	public void testItem() {
		Item item = new Item();
		assertThat(item).isNotNull();
	}

	@Test
	public void testGetQuantity() {
		assertTrue(theItem.getQuantity() == 10);
	}

	@Test
	public void testSetQuantity() {
		theItem.setQuantity(11);
		assertTrue(theItem.getQuantity() == 11);
	}

	@Test
	public void testGetInCart() {
		assertFalse(theItem.getInCart());
	}

	@Test
	public void testSetInCart() {
		theItem.setInCart(true);
		assertTrue(theItem.getInCart());
	}

	@Test
	public void testGetName() {
		assertTrue(theItem.getName().equals("test"));
	}

	@Test
	public void testSetName() {
		theItem.setName("mervin");
		assertTrue(theItem.getName().equals("mervin"));
	}

	@Test
	public void testGetPrice() {
		assertTrue(theItem.getPrice() == 1.1);
	}

	@Test
	public void testSetPrice() {
		theItem.setPrice(1.2);
		assertTrue(theItem.getPrice() == 1.2);
	}

}
