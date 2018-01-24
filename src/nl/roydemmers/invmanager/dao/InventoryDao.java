package nl.roydemmers.invmanager.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import nl.roydemmers.invmanager.objects.InventoryItem;

@Transactional
@Component("inventoryDao")
public class InventoryDao extends AbstractDao {

	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	@SuppressWarnings("unchecked")
	public List<InventoryItem> getInventoryList() {
		
		return session().createQuery("from InventoryItem").list();

	}

	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public void update(InventoryItem inventoryItem) {
		session().update(inventoryItem);
	}

	@Secured({"ROLE_ADMIN", "ROLE_MOD"})
	public void create(InventoryItem inventoryItem) {
		session().save(inventoryItem);

	}

	@Secured({"ROLE_ADMIN", "ROLE_MOD"})
	public boolean delete(int id) {
		MapSqlParameterSource params = new MapSqlParameterSource("id", id);
		return jdbc.update("delete from inventory where id=:id", params) == 1;
	}

	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public InventoryItem getInventoryItem(int id) {
		Criteria crit = session().createCriteria(InventoryItem.class);
		crit.add(Restrictions.eq("id", id));
		return (InventoryItem)crit.uniqueResult();
	}



}
