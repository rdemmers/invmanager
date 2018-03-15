package nl.roydemmers.invmanager.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import nl.roydemmers.invmanager.objects.Product;

// Service responsible for anything to do with money

/**
 * @author Roy Demmers
 *
 */
@Service("financialCalculationService")
public class FinancialCalculationService{

	@Autowired
	private ProductService productService;

	
	/**Calculate the amount of monetary value from each supplier.
	 * 
	 * @return Returns a HashMap with a supplierID as Key and the total monetary value of all products associated with that supplier as Value.
	 */
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public Map<Integer, Long> getSuppliersWithTotalWorth() {
	
		List<Product> products = productService.getAllProducts();
		Map<Integer, Long> temporaryMap = new HashMap<>();

		
		for (Product currentItem : products) {
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
}
