package nl.roydemmers.invmanager.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import nl.roydemmers.invmanager.dao.SupplierDao;
import nl.roydemmers.invmanager.dao.UserDao;
import nl.roydemmers.invmanager.objects.Product;
import nl.roydemmers.invmanager.objects.Supplier;
import nl.roydemmers.invmanager.objects.User;

@Service("supplierService")
public class SupplierService{
	
	private SupplierDao supplierDao;	

	@Autowired
	public void setSupplierDao(SupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}
	
	@Secured("ROLE_ADMIN")
	public void create(Supplier supplier) {
		supplierDao.create(supplier);
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public Supplier getSupplier(int id) {
		return supplierDao.getSupplier(id);
		
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public List<Supplier> getAllSuppliers(){
		return supplierDao.getAllSuppliers();
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public void setSupplierWorth(int id, long worth) {
		Supplier supplier = supplierDao.getSupplier(id);
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
		
		return this.getAllSuppliers();
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
