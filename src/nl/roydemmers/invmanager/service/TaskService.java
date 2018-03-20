package nl.roydemmers.invmanager.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import nl.roydemmers.invmanager.objects.Order;
import nl.roydemmers.invmanager.objects.Supplier;

@Service("taskService")
public class TaskService {
	@Autowired
	private OrderService orderService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private InventoryMailService inventoryMailService;
	
	// actual cron :  cron="0 18 * * 1-5"
	@Async
	//@Scheduled(cron="1 * * * * 1-5")
	public void generateOrderMails() {
		
		// Fetch list of all orders and suppliers
		List<Order> currentOrders = orderService.getPending();
		List<Supplier> allSuppliers = supplierService.getAll();
		
		// Convert allSuppliers to hashmap, with id as key and an empty Supplier list as value
		Map<Integer,List<Order>> supplierMap = new HashMap<>();
		for(Supplier s : allSuppliers) {
			List<Order> list = new LinkedList<>();
			
			supplierMap.put(s.getSupplierId(), list);
		}
		
		// Sorts the orders by supplier into the proper List 
		for(int i = 0; i < currentOrders.size(); i++) {
			int supplierId = currentOrders.get(i).getProductId().getSupplier().getSupplierId();
			List<Order> temporaryList = supplierMap.get(supplierId);
			temporaryList.add(currentOrders.get(i));
			
			supplierMap.put(supplierId, temporaryList);
		}
		
		// Sends all list of sorted orders to the mailService for mailing
		// update to orders to ordered=true
		for (List<Order> value : supplierMap.values()) {
			if(!value.isEmpty()) {
				// Currently working, but commented out because of too many emails during testing
		        //inventoryMailService.createBatchOrderMail(value);
				
				for(Order o : value) {
					o.setOrdered(true);
					orderService.update(o);
				}
			}
		}
	    
	}
	
}
