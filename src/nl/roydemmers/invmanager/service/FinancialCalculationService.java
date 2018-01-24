package nl.roydemmers.invmanager.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import nl.roydemmers.invmanager.objects.InventoryItem;
import nl.roydemmers.invmanager.objects.Supplier;

// Service responsible for anything to do with money

@Service("financialCalculationService")
public class FinancialCalculationService{

	@Autowired
	private InventoryService inventoryService;

	// Returns a Map with <Supplier ID, Total worth as long>
	// Used to display the total stock worth for each supplier
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public Map<Integer, Long> getSuppliersWithTotalWorth() {
	
		List<InventoryItem> inventoryItems = inventoryService.getAllInventoryItems();
		Map<Integer, Long> temporaryMap = new HashMap<>();

		
		for (InventoryItem currentItem : inventoryItems) {
			long value = currentItem.getCurrentStock() * currentItem.getPrice();
			
			
			if (temporaryMap.containsKey(currentItem.getSupplier().getSupplierId())) {
				long storage = temporaryMap.get(currentItem.getSupplier().getSupplierId());

				temporaryMap.put(currentItem.getSupplier().getSupplierId(), value + storage);
			} else {
				temporaryMap.put(currentItem.getSupplier().getSupplierId(), value);
			}
		}
		return temporaryMap;
		
	}

	// Money only has to be displayed with decimal value in front end
	// making it easier to convert the values only when needed by the view.
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public String convertLongtoCurrency(long amount) {
		BigDecimal payment = new BigDecimal(amount).movePointLeft(2);

		return ("€" + payment);
	}
}
