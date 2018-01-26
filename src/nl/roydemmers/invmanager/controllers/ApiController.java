package nl.roydemmers.invmanager.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.roydemmers.invmanager.objects.InventoryItem;
import nl.roydemmers.invmanager.objects.Supplier;

@Controller
@RequestMapping("/api")
public class ApiController extends AbstractController{

	@RequestMapping(value="/items", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<InventoryItem> getAllItems() {
		
		List<InventoryItem> items = inventoryService.getAllInventoryItems();
		
		
		return items;
	}
	
	@RequestMapping(value="/items/{id}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public InventoryItem getSingleItem(@PathVariable("id") int id) {
		
		return inventoryService.getInventoryItem(id);
	}
	
	@RequestMapping(value="/suppliers", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Supplier> getSuppliers() {
		
		return supplierService.getAllSuppliers();
	}
	
	@RequestMapping(value="/suppliers/{id}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Supplier getSuppliers(@PathVariable("id") int id) {
		
		return supplierService.getSupplier(id);
	}
}
