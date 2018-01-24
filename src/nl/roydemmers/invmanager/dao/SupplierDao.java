package nl.roydemmers.invmanager.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import nl.roydemmers.invmanager.objects.Supplier;

@Transactional
@Component("supplierDao")
public class SupplierDao extends AbstractDao {
	
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	@SuppressWarnings("unchecked")
	public List<Supplier> getAllSuppliers() {

		return session().createQuery("from Supplier").list();

	}

	@Secured("ROLE_ADMIN")
	public void update(Supplier supplier) {
		session().update(supplier);
	}

	@Secured("ROLE_ADMIN")
	public void create(Supplier supplier) {

		session().save(supplier);

	}

	@Secured("ROLE_ADMIN")
	public boolean delete(String name) {
		MapSqlParameterSource params = new MapSqlParameterSource("name", name);
		return jdbc.update("delete from suppliers where name=:name", params) == 1;
	}

	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public Supplier getSupplier(int id) {

		Criteria crit = session().createCriteria(Supplier.class);
		crit.add(Restrictions.eq("supplierId", id));
		return (Supplier)crit.uniqueResult();
	}
	

}
