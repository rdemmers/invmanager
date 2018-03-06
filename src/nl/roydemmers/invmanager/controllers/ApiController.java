package nl.roydemmers.invmanager.controllers;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import nl.roydemmers.invmanager.objects.Order;
import nl.roydemmers.invmanager.objects.Product;
import nl.roydemmers.invmanager.objects.Supplier;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api")
public class ApiController extends AbstractController{
	


	@RequestMapping(value="/items", method=RequestMethod.GET, produces="application/json")
	public List<Product> getAllItems() {
		return productService.getAllProducts();
	}
	
	@RequestMapping(value="/test", method=RequestMethod.GET, produces="application/json")
	public List<Product> test() {
		
		Product product = productService.get(1);
		Order order = new Order(product, 5, false, "hoi");
		
		orderService.create(order);
		return productService.getAllProducts();
	}
	
	@RequestMapping(value="/items/low", method=RequestMethod.GET, produces="application/json")
	public List<Product> getLowItems() {
		
		return productService.getLow();
	}
	
	@RequestMapping(value="/items/{id}", method=RequestMethod.GET, produces="application/json")
	public Product getSingleItem(@PathVariable("id") int id) {
		
		return productService.get(id);
	}
	
	@RequestMapping(value="/items/{id}", method=RequestMethod.POST, produces="application/json")
	@ResponseStatus(value= HttpStatus.OK)
	public void updateItem(@PathVariable("id") int id, @RequestBody Map<String, Object> data) {
		Product product = productService.mapJsonToObject(data);
		
		productService.update(product);
	}
	
	@RequestMapping(value="/items/{id}", method=RequestMethod.DELETE, produces="application/json")
	@ResponseStatus(value= HttpStatus.OK)
	public void deleteProduct(@PathVariable("id") int id) {
		
		productService.delete(id);
	}
	
	@RequestMapping(value="/items/{id}/mutate", method=RequestMethod.POST, produces="application/json")
	@ResponseStatus(value= HttpStatus.OK)
	public void mutateProduct(@PathVariable("id") int id, @RequestBody Map<String, Object> data) {
		int currentStock = Integer.parseInt(data.get("currentStock").toString());
		
		Product product = productService.get(id);
		product.setCurrentStock(currentStock);
		productService.update(product);
	}
	
	@RequestMapping(value="/items/new", method=RequestMethod.POST, produces="application/json")
	@ResponseStatus(value= HttpStatus.OK)
	public void createItem(@RequestBody Map<String, Object> data) {
		
		Product product = productService.mapJsonToObject(data);
		productService.create(product);
	}
		
	
	@RequestMapping(value="/suppliers", method=RequestMethod.GET, produces="application/json")
	public List<Supplier> getSuppliers() {
		
		return supplierService.getAll();
	}
	
	@RequestMapping(value="/suppliers/{id}", method=RequestMethod.GET, produces="application/json")
	public Supplier getSuppliers(@PathVariable("id") int id) {
		
		return supplierService.get(id);
	}
	
	@RequestMapping(value="/user", method=RequestMethod.GET, produces="application/json")
	public Collection<? extends GrantedAuthority> getUser(Authentication authentication) {
		return authentication.getAuthorities();
	}
}