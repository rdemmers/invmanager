package nl.roydemmers.invmanager.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import nl.roydemmers.invmanager.objects.InventoryItem;

@Transactional
@Component("inventoryDao")
public class InventoryDao extends AbstractDao {

	@SuppressWarnings("unchecked")
	public List<InventoryItem> getInventoryList() {
		
		return session().createQuery("from InventoryItem").list();

	}

	public void update(InventoryItem inventoryItem) {
		session().update(inventoryItem);
	}

	public void create(InventoryItem inventoryItem) {
		session().save(inventoryItem);

	}

	public boolean delete(int id) {
		MapSqlParameterSource params = new MapSqlParameterSource("id", id);
		return jdbc.update("delete from inventory where id=:id", params) == 1;
	}

	public InventoryItem getInventoryItem(int id) {
		Criteria crit = session().createCriteria(InventoryItem.class);
		crit.add(Restrictions.eq("id", id));
		return (InventoryItem)crit.uniqueResult();
	}



}
