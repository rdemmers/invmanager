package nl.roydemmers.invmanager.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import nl.roydemmers.invmanager.objects.InventoryItem;
import nl.roydemmers.invmanager.objects.Supplier;

@Service("financialCalculationService")
public class FinancialCalculationService extends AbstractService {

	

	
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

	public String convertLongtoCurrency(long amount) {

		BigDecimal payment = new BigDecimal(amount).movePointLeft(2);

		return ("€" + payment);
	}
}
