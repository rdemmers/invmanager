package nl.roydemmers.invmanager.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import nl.roydemmers.invmanager.dao.SupplierDao;
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
	@Secured("ROLE_ADMIN")
	public void create(Supplier supplier) {
		supplierDao.create(supplier);
	}
	
	/**Fetch a specific Supplier object from the database
	 * 
	 * @param id The integer id of the Supplier
	 * @return Returns the Supplier object
	 */
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public Supplier get(int id) {
		return supplierDao.get(id);
		
	}
	
	/**Fetch all Supplier objects from the database
	 * 
	 * @return A list with all Supplier objects
	 */
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public List<Supplier> getAll(){
		return supplierDao.getAll();
	}
	
	/** this method sets the totalWorth value of a Supplier.
	 * totalWorth is used to add all prices from products from a supplier together
	 * 
	 * @param id The integer id of the Supplier
	 * @param worth The long value of the total worth
	 */
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public void setSupplierWorth(int id, long worth) {
		Supplier supplier = supplierDao.get(id);
		supplier.setTotalWorth(worth);
		supplierDao.update(supplier);
		
	}
	
	// Used to easily access the supplier list front end
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public List<Supplier> convertSupplierHashmapToObjectList(Map<Integer,Long> map){
		Iterator<Map.Entry<Integer, Long>> it = map.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<Integer, Long> pair = it.next();
			
			this.setSupplierWorth(pair.getKey(), pair.getValue());
		}
		
		return this.getAll();
	}
	
	@Secured("ROLE_ADMIN")
	public void update(Supplier supplier) {
		supplierDao.update(supplier);
	}
	
	@Secured("ROLE_ADMIN")
	public void delete(Supplier supplier) {
		
		supplierDao.delete(supplier.getName());
	}
}
