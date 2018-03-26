package nl.roydemmers.invmanager.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import nl.roydemmers.invmanager.dao.SupplierDao;
import nl.roydemmers.invmanager.objects.Product;
import nl.roydemmers.invmanager.objects.Supplier;
/**Service to manipulate Projects and communicate with the ProductDao
 * 
 * @see nl.roydemmers.invmanager.dao.SupplierDao
 * @author Roy Demmers
 *
 */
@Service("supplierService")
public class SupplierService{
	
	private SupplierDao supplierDao;	

	@Autowired
	public void setSupplierDao(SupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}
	
	/**Store a new Supplier object in the database
	 * 
	 * @param supplier Supplier to be stored
	 */
	public void create(Supplier supplier) {
		supplierDao.create(supplier);
	}
	
	/**Fetch a specific Supplier object from the database
	 * 
	 * @param id The integer id of the Supplier
	 * @return Returns the Supplier object
	 */
	public Supplier get(int id) {
		return supplierDao.get(id);
		
	}
	
	/**Fetch all Supplier objects from the database
	 * 
	 * @return A list with all Supplier objects
	 */
	public List<Supplier> getAll(){
		return supplierDao.getAll();
	}
	
	/** this method sets the totalWorth value of a Supplier.
	 * totalWorth is used to add all prices from products from a supplier together
	 * 
	 * @param id The integer id of the Supplier
	 * @param worth The long value of the total worth
	 */
	private void setSupplierWorth(int id, long worth) {
		Supplier supplier = supplierDao.get(id);
		supplier.setTotalWorth(worth);
		supplierDao.update(supplier);
		
	}
	
	public Supplier mapJsonToObject(Map<String,Object> data) {
		// Check for an id in the map data
		// an update to a product will have an ID, no id means that the product must be new
		
		boolean newSupplier = true;
		int supplierId = 0;
		if(data.get("supplierId") != null) {
			supplierId = (Integer)data.get("supplierId");
			newSupplier = false;
		}
		
		String name = (String)data.get("name");
		String contact = (String)data.get("contact");
		String orderMail = (String)data.get("orderMail");
		String questionMail = (String)data.get("questionMail");
		String phone = (String)data.get("phone");
		
		
		if(newSupplier) {
			return new Supplier.Builder(name, orderMail).contact(contact).questionMail(questionMail).phone(phone).build();
		}
		
		return new Supplier.Builder(name, orderMail).contact(contact).questionMail(questionMail).phone(phone).supplierId(supplierId).build();
		
		
	}

	
	public void update(Supplier supplier) {
		supplierDao.update(supplier);
	}
	
	public void delete(Supplier supplier) {
		
		supplierDao.delete(supplier.getName());
	}
}
