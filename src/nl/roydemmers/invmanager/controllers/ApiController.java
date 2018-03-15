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
public class ApiController extends AbstractController {

	@RequestMapping(value = "/items", method = RequestMethod.GET, produces = "application/json")
	public List<Product> getAllItems() {
		return productService.getAllProducts();
	}

	// Return all products with currentStock < stockMinimum. Filters products that already have an open Order.
	@RequestMapping(value = "/items/low", method = RequestMethod.GET, produces = "application/json")
	public List<Product> getLowItems() {

		return productService.getLow();
	}

	@RequestMapping(value = "/items/{id}", method = RequestMethod.GET, produces = "application/json")
	public Product getSingleItem(@PathVariable("id") int id) {

		return productService.get(id);
	}
	
	// Update a product based on id,
	@RequestMapping(value = "/items/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseStatus(value = HttpStatus.OK)
	public void updateItem(@PathVariable("id") int id, @RequestBody Map<String, Object> data) {
		Product product = productService.mapJsonToObject(data);

		productService.update(product);
	}
	
	// product deletion 
	@RequestMapping(value = "/items/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteProduct(@PathVariable("id") int id) {

		productService.delete(id);
	}

	// Mutate the currentStock of a product. This is a seperate mapping from .updateItem(), because all users can mutate
	// while only mod/admin can update
	@RequestMapping(value = "/items/{id}/mutate", method = RequestMethod.POST, produces = "application/json")
	@ResponseStatus(value = HttpStatus.OK)
	public void mutateProduct(@PathVariable("id") int id, @RequestBody Map<String, Object> data) {
		int currentStock = Integer.parseInt(data.get("currentStock").toString());

		Product product = productService.get(id);
		product.setCurrentStock(currentStock);
		productService.update(product);
	}

	@RequestMapping(value = "/items/new", method = RequestMethod.POST, produces = "application/json")
	@ResponseStatus(value = HttpStatus.OK)
	public void createItem(@RequestBody Map<String, Object> data) {

		Product product = productService.mapJsonToObject(data);
		productService.create(product);
	}

	@RequestMapping(value = "/suppliers", method = RequestMethod.GET, produces = "application/json")
	public List<Supplier> getSuppliers() {

		return supplierService.getAll();
	}

	@RequestMapping(value = "/suppliers/{id}", method = RequestMethod.GET, produces = "application/json")
	public Supplier getSuppliers(@PathVariable("id") int id) {

		return supplierService.get(id);
	}

	@RequestMapping(value = "/orders", method = RequestMethod.GET, produces = "application/json")
	public List<Order> getOrders() {
		
		return orderService.getAll();
	}
	
	@RequestMapping(value = "/orders/{id}", method = RequestMethod.POST, produces = "application/json")
	public List<Order> setOrderReceived(@PathVariable("id") int id) {
		Order order = orderService.get(id);
		Product product = order.getProductId();
		
		product.setCurrentStock(product.getCurrentStock() + order.getQuantityMultiplier());
		productService.update(product);
		order.setReceived(true);
		orderService.update(order);
		
		return orderService.getAll();
	}
	
	@RequestMapping(value = "/orders/new", method = RequestMethod.POST, produces = "application/json")
	public List<Order> newOrder(@RequestBody Map<String, Object> data) {
		
		int id = Integer.parseInt(data.get("productId").toString());
		int multiplier = Integer.parseInt(data.get("quantity").toString());
		
		Product product = productService.get(id);
		
		Order order = new Order(product, multiplier, false, false);
		
		orderService.create(order);
		
		return orderService.getAll();
	}
	
	// Grab authority of the logged in user, used to alter the front-end display.
	@RequestMapping(value = "/user", method = RequestMethod.GET, produces = "application/json")
	public Collection<? extends GrantedAuthority> getUser(Authentication authentication) {
		return authentication.getAuthorities();
	}

}
