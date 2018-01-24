package nl.roydemmers.invmanager.dao;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import nl.roydemmers.invmanager.objects.InventoryLogItem;

@Transactional
@Component("inventoryLogItemDao")
public class InventoryLogItemDao extends AbstractDao{


	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	@SuppressWarnings("unchecked")
	public List<InventoryLogItem> getQuantityChangesHistory() {
		
		return session().createQuery("from InventoryLogItem").list();
			
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public void appendUsernameToLogItem(String username) {
		
		InventoryLogItem inventoryLogItem = (InventoryLogItem)session().createQuery("from InventoryLogItem ORDER BY id DESC").setMaxResults(1).uniqueResult();
		
		inventoryLogItem.setUsername(username);
		session().update(inventoryLogItem);
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public InventoryLogItem getLastLogItem() {
		return (InventoryLogItem)session().createQuery("from InventoryLogItem ORDER BY id DESC").setMaxResults(1).uniqueResult();
		
	}
}
