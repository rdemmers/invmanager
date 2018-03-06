package nl.roydemmers.invmanager.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import nl.roydemmers.invmanager.dao.OrderDao;
import nl.roydemmers.invmanager.dao.ProductDao;
import nl.roydemmers.invmanager.objects.Order;

@Service("orderService")
public class OrderService {
	@Autowired
	private OrderDao orderDao;

	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public List<Order> getAll() {
		return orderDao.getAll();
	}
	

	@Secured({"ROLE_ADMIN", "ROLE_MOD"})
	public void delete(int id) {
		orderDao.delete(id);
	}

	@Secured({"ROLE_ADMIN", "ROLE_MOD"})
	public void create(Order order) {
		orderDao.create(order);
	}

	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public Order get(int id) {
		return orderDao.get(id);
	}

	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public void update(Order order) {
		orderDao.update(order);
	}

	
}
