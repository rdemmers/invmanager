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
import nl.roydemmers.invmanager.objects.InventoryItem;
import nl.roydemmers.invmanager.objects.Supplier;
import nl.roydemmers.invmanager.objects.User;

@Service("supplierService")
public class SupplierService extends AbstractService{
	
	private SupplierDao supplierDao;	

	@Autowired
	public void setSupplierDao(SupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}
	
	public void create(Supplier supplier) {
		supplierDao.create(supplier);
	}
	
	public Supplier getSupplier(int id) {
		return supplierDao.getSupplier(id);
		
	}
	
	public List<Supplier> getAllSuppliers(){
		return supplierDao.getAllSuppliers();
	}
	
	public void setSupplierWorth(int id, long worth) {
		Supplier supplier = supplierDao.getSupplier(id);
		supplier.setTotalWorth(worth);
		supplierDao.update(supplier);
		
	}
	
	public List<Supplier> convertSupplierHashmapToObjectList(Map<Integer,Long> map){
		Iterator<Map.Entry<Integer, Long>> it = map.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<Integer, Long> pair = it.next();
			
			this.setSupplierWorth(pair.getKey(), pair.getValue());
		}
		
		List<Supplier> allSuppliers = this.getAllSuppliers();
		return allSuppliers;
	}
	
	public void update(Supplier supplier) {
		supplierDao.update(supplier);
	}
	
	public void delete(Supplier supplier) {
		
		supplierDao.delete(supplier.getName());
	}
}
