package nl.roydemmers.invmanager.service;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.roydemmers.invmanager.config.JavaBeanConfig;

@Service("abstractService")
public abstract class AbstractService {
	protected JavaBeanConfig javaBeanConfig;
	protected InventoryService inventoryService;
	protected FinancialCalculationService financialCalculationService;
	protected SupplierService supplierService;
	protected UserService userService;
	protected PreferenceService preferenceService;
	protected InventoryMailService inventoryMailService;
	@Autowired
	protected ServletContext context;

	@Autowired
	public void setInventoryMailService(InventoryMailService inventoryMailService) {
		this.inventoryMailService = inventoryMailService;
	}

	@Autowired
	public void setJavaBeanConfig(JavaBeanConfig javaBeanConfig) {
		this.javaBeanConfig = javaBeanConfig;
	}

	@Autowired
	public void setGlobalPrefService(PreferenceService preferenceService) {
		this.preferenceService = preferenceService;
	}
	
	@Autowired
	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	@Autowired
	protected void setFinancialCalculationService(FinancialCalculationService financialCalculationService) {
		this.financialCalculationService = financialCalculationService;
	}

	@Autowired
	protected void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	@Autowired
	protected void setUsersService(UserService userService) {
		this.userService = userService;
	}
	
	
}
