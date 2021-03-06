package nl.roydemmers.invmanager.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import nl.roydemmers.invmanager.objects.Product;

@Transactional
@Component("inventoryDao")
public class ProductDao extends AbstractDao {

	
	@SuppressWarnings("unchecked")
	public List<Product> getAll() {
		return session().createQuery("from Product p where p.active is true").list();
	}

	
	public void update(Product product) {
		session().update(product);
	}

	
	public void create(Product product) {
		session().save(product);

	}

	
	public boolean delete(int id) {
		MapSqlParameterSource params = new MapSqlParameterSource("id", id);
		return jdbc.update("delete from inventory where id=:id", params) == 1;
	}

	
	public Product get(int id) {
		Criteria crit = session().createCriteria(Product.class);
		crit.add(Restrictions.eq("id", id));
		return (Product)crit.uniqueResult();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Product> getLow(){
		return session().createQuery("from Product p left outer join p.orders o where p.currentStock < p.stockMinimum and (p.orders is empty or o.received is true)").list();	
	}
	

}
