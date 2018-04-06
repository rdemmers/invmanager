package nl.roydemmers.invmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import nl.roydemmers.invmanager.service.OrderService;
import nl.roydemmers.invmanager.service.ProductService;
import nl.roydemmers.invmanager.service.SupplierService;
import nl.roydemmers.invmanager.service.UserService;

class AbstractController {
	

	protected ProductService productService;
	protected SupplierService supplierService;
	protected OrderService orderService;
	protected UserService userService;
	
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	@Autowired
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	
	@Autowired
	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	@Autowired
	public void setInventoryService(ProductService productService) {
		this.productService = productService;
	}

}
