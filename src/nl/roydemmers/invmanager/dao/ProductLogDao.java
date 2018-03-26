package nl.roydemmers.invmanager.dao;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import nl.roydemmers.invmanager.objects.InventoryLogItem;

@Transactional
@Component("productLogDao")
public class ProductLogDao extends AbstractDao{


	@SuppressWarnings("unchecked")
	public List<InventoryLogItem> getAll() {
		
		return session().createQuery("from InventoryLogItem").list();	
	}
	
	
	public InventoryLogItem getLast() {
		return (InventoryLogItem)session().createQuery("from InventoryLogItem ORDER BY id DESC").setMaxResults(1).uniqueResult();
		
	}
}
