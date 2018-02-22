package nl.roydemmers.invmanager.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.roydemmers.invmanager.objects.InventoryItem;
import nl.roydemmers.invmanager.objects.Supplier;

@Controller
@CrossOrigin(origins = "http://localhost:8080")
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
	
	@RequestMapping(value="/items/{id}", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public InventoryItem updateItem(@PathVariable("id") int id, @RequestBody Map<String, Object> data) {
		InventoryItem inventoryItem = inventoryService.mapJsonToObject(data);
		inventoryService.updateInventoryItem(inventoryItem);
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
