package nl.roydemmers.invmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import nl.roydemmers.invmanager.service.OrderService;
import nl.roydemmers.invmanager.service.ProductService;
import nl.roydemmers.invmanager.service.SupplierService;

class AbstractController {
	

	protected ProductService productService;
	protected SupplierService supplierService;
	protected OrderService orderService;
	

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
