package nl.roydemmers.invmanager.controllers;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
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
import nl.roydemmers.invmanager.objects.User;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api")
public class ApiController extends AbstractController {
	
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	@RequestMapping(value = "/items", method = RequestMethod.GET, produces = "application/json")
	public List<Product> getAllItems() {
		return productService.getAllProducts();
	}

	// Return all products with currentStock < stockMinimum. Filters products that already have an open Order.
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	@RequestMapping(value = "/items/low", method = RequestMethod.GET, produces = "application/json")
	public List<Product> getLowItems() {

		return productService.getLow();
	}

	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	@RequestMapping(value = "/items/{id}", method = RequestMethod.GET, produces = "application/json")
	public Product getSingleItem(@PathVariable("id") int id) {

		return productService.get(id);
	}
	
	// Update a product based on id,
	@Secured({"ROLE_ADMIN", "ROLE_MOD"})
	@RequestMapping(value = "/items/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseStatus(value = HttpStatus.OK)
	public void updateItem(@PathVariable("id") int id, @RequestBody Map<String, Object> data) {
		Product product = productService.mapJsonToObject(data);

		productService.update(product);
	}
	
	// product deletion. Sets product as inactive, so it doesn't show in any lists. Product will still exist in the database for history/log reasons
	@Secured({"ROLE_ADMIN", "ROLE_MOD"})
	@RequestMapping(value = "/items/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteProduct(@PathVariable("id") int id) {
		Product product  = productService.get(id);
		product.setActive(false);
		productService.update(product);
	}

	// Mutate the currentStock of a product. This is a seperate mapping from .updateItem(), because all users can mutate
	// while only mod/admin can update
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	@RequestMapping(value = "/items/{id}/mutate", method = RequestMethod.POST, produces = "application/json")
	@ResponseStatus(value = HttpStatus.OK)
	public void mutateProduct(@PathVariable("id") int id, @RequestBody Map<String, Object> data) {
		int currentStock = Integer.parseInt(data.get("currentStock").toString());

		Product product = productService.get(id);
		product.setCurrentStock(currentStock);
		productService.update(product);
	}

	@Secured({"ROLE_ADMIN", "ROLE_MOD"})
	@RequestMapping(value = "/items/new", method = RequestMethod.POST, produces = "application/json")
	@ResponseStatus(value = HttpStatus.OK)
	public void createItem(@RequestBody Map<String, Object> data) {

		Product product = productService.mapJsonToObject(data);
		productService.create(product);
	}

	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	@RequestMapping(value = "/suppliers", method = RequestMethod.GET, produces = "application/json")
	public List<Supplier> getSuppliers() {

		return supplierService.getAll();
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_MOD"})
	@RequestMapping(value = "/suppliers/new", method = RequestMethod.POST, produces = "application/json")
	@ResponseStatus(value = HttpStatus.OK)
	public void createSupplier(@RequestBody Map<String, Object> data) {
		supplierService.create(supplierService.mapJsonToObject(data));
		
	}

	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	@RequestMapping(value = "/suppliers/{id}", method = RequestMethod.GET, produces = "application/json")
	public Supplier getSuppliers(@PathVariable("id") int id) {

		return supplierService.get(id);
	}

	@Secured({"ROLE_ADMIN", "ROLE_MOD"})
	@RequestMapping(value = "/orders", method = RequestMethod.GET, produces = "application/json")
	public List<Order> getOrders() {
		
		return orderService.getAll();
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_MOD"})
	@RequestMapping(value = "/orders/{id}", method = RequestMethod.POST, produces = "application/json")
	public List<Order> setOrderReceived(@PathVariable("id") int id) {
		Order order = orderService.get(id);
		
		orderService.setReceived(order);
		
		return orderService.getAll();
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_MOD"})
	@RequestMapping(value = "/orders/new", method = RequestMethod.POST, produces = "application/json")
	public List<Order> newOrder(@RequestBody Map<String, Object> data) {
		
		int id = Integer.parseInt(data.get("productId").toString());
		int quantity = Integer.parseInt(data.get("quantity").toString());
		
		Product product = productService.get(id);
		
		Order order = new Order.Builder(product).quantity(quantity).ordered(false).received(false).build();
		orderService.create(order);
		
		return orderService.getAll();
	}
	
	// Grab authority of the logged in user, used to alter the front-end display.
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	@RequestMapping(value = "/user", method = RequestMethod.GET, produces = "application/json")
	public Collection<? extends GrantedAuthority> getUser(Authentication authentication) {
		return authentication.getAuthorities();
	}
	
	// Grab authority of the logged in user, used to alter the front-end display.
	@Secured({"ROLE_ADMIN"})
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/user", method = RequestMethod.POST, produces = "application/json")
	public void newUser(@RequestBody Map<String, Object> data) {
		
		
		User user = userService.mapJsonToObject(data);
		
		userService.create(user);
	}

}
