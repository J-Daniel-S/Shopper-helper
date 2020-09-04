package growthCalclulator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

@Entity
@Table(name = "items")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq")
	@SequenceGenerator(name = "item_seq", sequenceName = "item_seq", allocationSize = 1)
	private long itemId;
	@NonNull
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "price", nullable = true)
	private double price;
	@Column(name = "quantity", nullable = true)
	private int quantity;
	@NonNull
	@Column(name = "in_cart", nullable = false)
	private Boolean inCart;

	public Item(String name, double price, int quantity, Boolean inCart) {
		super();
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.inCart = inCart;
	}

	public Item(String name, double price, Boolean inCart) {
		super();
		this.name = name;
		this.price = price;
		this.inCart = inCart;
	}

	public Item(String name, Boolean inCart) {
		super();
		this.name = name;
		this.inCart = inCart;
	}

	public Item(long itemId, String name, Boolean inCart) {
		super();
		this.itemId = itemId;
		this.name = name;
		this.inCart = inCart;
	}

	public Item() {

	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Boolean getInCart() {
		return inCart;
	}

	public void setInCart(Boolean inCart) {
		this.inCart = inCart;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Item [name=" + name + ", price=" + price + ", inCart=" + inCart + "]";
	}

}
