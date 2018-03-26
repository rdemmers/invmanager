package nl.roydemmers.invmanager.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import nl.roydemmers.invmanager.objects.Order;

@Transactional
@Component("orderDao")
public class OrderDao extends AbstractDao {

	
	@SuppressWarnings("unchecked")
	public List<Order> getAll() {
		return session().createQuery("from Order").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Order> getPendingToOrder() {
		return session().createQuery("from Order o where o.ordered=false").list();
	}

	public void update(Order order) {
		session().update(order);
	}

	public void create(Order order) {
		session().save(order);

	}

	public boolean delete(int id) {
		MapSqlParameterSource params = new MapSqlParameterSource("id", id);
		return jdbc.update("delete from order where id=:id", params) == 1;
	}

	public Order get(int id) {
		Criteria crit = session().createCriteria(Order.class);
		crit.add(Restrictions.eq("id", id));
		return (Order)crit.uniqueResult();
	}


}
