package nl.roydemmers.invmanager.controllers;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;

import nl.roydemmers.invmanager.dao.ProductLogDao;
import nl.roydemmers.invmanager.objects.Product;
import nl.roydemmers.invmanager.service.FinancialCalculationService;
import nl.roydemmers.invmanager.service.InventoryMailService;
import nl.roydemmers.invmanager.service.ProductService;
import nl.roydemmers.invmanager.service.PreferenceService;
import nl.roydemmers.invmanager.service.SupplierService;
import nl.roydemmers.invmanager.service.UploadService;
import nl.roydemmers.invmanager.service.UserService;

public class AbstractController {


	protected ProductService productService;
	protected FinancialCalculationService financialCalculationService;
	protected Product currentItem;
	protected SupplierService supplierService;
	protected ProductLogDao productLogDao;
	protected UserService userService;
	protected PreferenceService preferenceService;
	protected InventoryMailService inventoryMailService;
	protected UploadService uploadService;
	
	@Autowired
	public void setUploadService(UploadService uploadService) {
		this.uploadService = uploadService;
	}
	
	@Autowired
	protected ServletContext servletContext;

	@Autowired
	public void setInventoryMailService(InventoryMailService inventoryMailService) {
		this.inventoryMailService = inventoryMailService;
	}

	@Autowired
	public void setPreferenceService(PreferenceService preferenceService) {
		this.preferenceService = preferenceService;
	}

	@Autowired
	public void setUsersService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setInventoryLogItemDao(ProductLogDao productLogDao) {
		this.productLogDao = productLogDao;
	}

	@Autowired
	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	@Autowired
	public void setInventoryService(ProductService productService) {
		this.productService = productService;
	}

	@Autowired
	public void setFinancialCalculationService(FinancialCalculationService financialCalculationService) {
		this.financialCalculationService = financialCalculationService;
	}
}
