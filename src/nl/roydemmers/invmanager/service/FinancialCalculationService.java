package nl.roydemmers.invmanager.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import nl.roydemmers.invmanager.objects.Product;
import nl.roydemmers.invmanager.objects.Supplier;

// Service responsible for anything to do with money

@Service("financialCalculationService")
public class FinancialCalculationService{

	@Autowired
	private ProductService productService;

	// Returns a Map with <Supplier ID, Total worth as long>
	// Used to display the total stock worth for each supplier
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
