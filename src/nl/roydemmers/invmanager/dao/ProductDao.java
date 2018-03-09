package nl.roydemmers.invmanager.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import nl.roydemmers.invmanager.objects.Product;

@Transactional
@Component("inventoryDao")
public class ProductDao extends AbstractDao {

	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	@SuppressWarnings("unchecked")
	public List<Product> getAll() {
		return session().createQuery("from Product").list();
	}

	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public void update(Product product) {
		session().update(product);
	}

	@Secured({"ROLE_ADMIN", "ROLE_MOD"})
	public void create(Product product) {
		session().save(product);

	}

	@Secured({"ROLE_ADMIN", "ROLE_MOD"})
	public boolean delete(int id) {
		MapSqlParameterSource params = new MapSqlParameterSource("id", id);
		return jdbc.update("delete from inventory where id=:id", params) == 1;
	}

	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public Product get(int id) {
		Criteria crit = session().createCriteria(Product.class);
		crit.add(Restrictions.eq("id", id));
		return (Product)crit.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> getLow(){
		return session().createQuery("from Product where currentstock < stockminimum").list();
		
	}

}
