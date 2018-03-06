package nl.roydemmers.invmanager.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import nl.roydemmers.invmanager.dao.ProductDao;
import nl.roydemmers.invmanager.dao.ProductLogDao;
import nl.roydemmers.invmanager.objects.InventoryLogItem;
import nl.roydemmers.invmanager.objects.Product;
import nl.roydemmers.invmanager.objects.Supplier;

@Service("productService")
public class ProductService {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductLogDao productLogDao;
	@Autowired
	private InventoryMailService inventoryMailService;
	@Autowired
	private SupplierService supplierService;


	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public List<Product> getAllProducts() {
		return productDao.getAll();
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public List<Product> getProductsWithAttachment(){
		List<Product> products = this.getAllProducts();
		List<Product> productsAttachments = new ArrayList<>();
		
		for(Product product : products) {
			if(product.getAttachment().length() > 4) {
				productsAttachments.add(product);
			}
		}
		
		return productsAttachments;	
	}
	
	public Product mapJsonToObject(Map<String,Object> data) {
		// Check for an id in the map data
		// an update to a product will have an ID, no id means that the product must be new
		
		boolean newProduct = true;
		int id = 0;
		if(data.get("id") != null) {
			id = (Integer)data.get("id");
			newProduct = false;
		}
		
		String barcode = (String)data.get("barcode");
		String description = (String)data.get("description");
		int deliveryTime = Integer.parseInt(data.get("deliveryTime").toString());
		long price = Long.parseLong(data.get("price").toString());
		String name = (String)data.get("name");
		int orderQuantity = Integer.parseInt(data.get("orderQuantity").toString());
		int currentStock = Integer.parseInt(data.get("currentStock").toString());
		int stockMinimum = Integer.parseInt(data.get("stockMinimum").toString());
		String attachment = (String)data.get("attachment");
		
		
		Supplier supplier = supplierService.getSupplier(Integer.parseInt(data.get("supplierId").toString()));
		
		if(newProduct) {
			return new Product(barcode, description, deliveryTime, price, name, orderQuantity, currentStock, stockMinimum, attachment, supplier);
		}
		
		return new Product(id, barcode, description, deliveryTime, price, name, orderQuantity, currentStock, stockMinimum, attachment, supplier);
		
		
	}

	
	// To display a List with items under the stock minimum
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public List<Product> getLowProducts() {
		List<Product> productList = productDao.getAll();
		List<Product> lowList = new ArrayList<>();

		for (int i = 0; i < productList.size(); i++) {
			if (productList.get(i).getCurrentStock() <= productList.get(i).getStockMinimum()) {
				lowList.add(productList.get(i));
			}
		}
		return lowList;
	}

	// Display the last x changes in inventory amount
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public List<InventoryLogItem> getRecentChanges(boolean getAll) {

		List<InventoryLogItem> inventoryLogList = productLogDao.getAll();

		// Try - catch statement in case of an empty database
		try {
			Collections.reverse(inventoryLogList);
			
			if (!getAll) {
				this.shortenInventoryLogList(inventoryLogList, 19);
			}

			for (InventoryLogItem inventoryLogItem : inventoryLogList) {
				Product product = this.getInventoryItem(inventoryLogItem.getItemID());
				inventoryLogItem.setProductName(product.getName());
				inventoryLogItem.setActualChange(inventoryLogItem.getNewQuantity() - inventoryLogItem.getOldQuantiy());
				inventoryLogItem.setBarcode(product.getBarcode());
			}
		} catch (NullPointerException e) {
			System.out.println(e);
		}

		return inventoryLogList;
	}

	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public List<InventoryLogItem> shortenInventoryLogList(List<InventoryLogItem> inventoryLogList, int listLength) {

		if (inventoryLogList.size() > listLength) {
			inventoryLogList = inventoryLogList.subList(0, listLength);
		}

		return inventoryLogList;
	}

	@Secured({"ROLE_ADMIN", "ROLE_MOD"})
	public void deleteInventoryItem(int id) {
		productDao.delete(id);
	}

	@Secured({"ROLE_ADMIN", "ROLE_MOD"})
	public void create(Product product) {
		productDao.create(product);
	}

	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public Product getInventoryItem(int id) {
		return productDao.get(id);
	}

	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public void updateInventoryItem(Product product) {
		productDao.update(product);
	}

	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public void checkStockForMail(int id) {
		Product product = this.getInventoryItem(id);
		if (product.getCurrentStock() <= product.getStockMinimum()) {
			inventoryMailService.createNotificationMail(product);
		}

	}

	// Since log items aren't stored with all their properties this function appends the object
	// with values from the database
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public void fillLogItemWithItemProps(InventoryLogItem logItem, Product product) {

		logItem.setProductName(product.getName());
		logItem.setBarcode(product.getBarcode());
		logItem.setQuantityMinimum(product.getStockMinimum());
		logItem.setSupplier(product.getSupplier().getName());
		if (logItem.getNewQuantity() < logItem.getQuantityMinimum()) {
			logItem.setUnderMinimum(true);
		} else {
			logItem.setUnderMinimum(false);
		}
	}
	
}
