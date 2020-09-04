package growthCalclulator.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import growthCalclulator.entity.Item;

public interface ItemRepository extends CrudRepository<Item, Long> {

	public List<Item> findAll();

	public Item findById(long id);
}
