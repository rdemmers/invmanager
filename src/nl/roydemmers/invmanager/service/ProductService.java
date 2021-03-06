package nl.roydemmers.invmanager.service;

import java.util.ArrayList;
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

/**Service to manipulate Projects and communicate with the ProductDao
 * 
 * @see nl.roydemmers.invmanager.dao.ProductDao
 * @author Roy Demmers
 *
 */
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


	/**Get all Products from the database
	 * 
	 * @return Returns a List<Product>
	 */
	public List<Product> getAllProducts() {
		return productDao.getAll();
	}
	
	/**Generate a list of products, filter out the products without attachments
	 * 
	 * 
	 * @return List<Product> that have attachments
	 */
	//TODO Move this logic from java to an HQL query
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
	
	/**Takes a map of JSON data and maps it to a Product Object.
	 * 
	 * If the JSON data contains no productID, the method will generate a new product
	 * 
	 * @param data The JSON product information that should be mapped
	 * @return returns a Product object
	 */
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
		String orderMetric = data.get("orderMetric").toString();
		int currentStock = Integer.parseInt(data.get("currentStock").toString());
		int stockMinimum = Integer.parseInt(data.get("stockMinimum").toString());
		String attachment = (String)data.get("attachment");
		
		
		Supplier supplier = supplierService.get(Integer.parseInt(data.get("supplierId").toString()));
		
		if(newProduct) {
			return new Product.Builder(barcode, name, supplier).description(description).deliveryTime(deliveryTime).price(price).orderMetric(orderMetric).currentStock(currentStock).stockMinimum(stockMinimum).attachment(attachment).build();
		}
		
		return new Product.Builder(barcode, name, supplier).id(id).description(description).deliveryTime(deliveryTime).price(price).orderMetric(orderMetric).currentStock(currentStock).stockMinimum(stockMinimum).attachment(attachment).build();
		
		
	}

	
	
	/**Fetch a list with Products that have currentStock < stockMinimum
	 * 
	 * @return Returns a list<Product> with products that have less stock than their minimum
	 */
	public List<Product> getLow() {
		return productDao.getLow();
	}


	/**Shorten an inventoryLogList to the supplied length
	 * 
	 * @param inventoryLogList List that should be shortened
	 * @param listLength Wanted length of the list
	 * 
	 * @return Shortened inventoryLogList
	 */
	private List<InventoryLogItem> shortenInventoryLogList(List<InventoryLogItem> inventoryLogList, int listLength) {

		if (inventoryLogList.size() > listLength) {
			inventoryLogList = inventoryLogList.subList(0, listLength);
		}

		return inventoryLogList;
	}

	/**Delete a product from the database
	 * @param id ID of the product
	 */
	public void delete(int id) {
		productDao.delete(id);
	}

	/**Create a product in the database
	 * @param product Product to be created
	 */
	public void create(Product product) {
		productDao.create(product);
	}

	/**Fetch a specific Product from the database
	 * @param id ID of the Product that should be fetched
	 * @return Product object
	 */
	public Product get(int id) {
		return productDao.get(id);
	}

	/**Update the values of a Product in the database
	 * @param product Product that overwrites its old version in the database
	 */
	public void update(Product product) {
		productDao.update(product);
	}

	/** Check if the currentStock of a product is lower than the minimumstock, send a notificationmail
	 * if it is
	 * @param id ID of the product that should be checked
	 * @deprecated Currently not being used since notificationmails are not being send.
	 */
	public void checkStockForMail(int id) {
		Product product = this.get(id);
		if (product.getCurrentStock() <= product.getStockMinimum()) {
			inventoryMailService.createNotificationMail(product);
		}

	}

	
}
