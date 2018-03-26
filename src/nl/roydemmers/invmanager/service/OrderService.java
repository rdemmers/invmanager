package nl.roydemmers.invmanager.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import nl.roydemmers.invmanager.dao.OrderDao;
import nl.roydemmers.invmanager.objects.Order;
import nl.roydemmers.invmanager.objects.Product;

/**
 * @author Roy Demmers
 *
 */
@Service("orderService")
public class OrderService {
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private ProductService productService;

	
	/** Fetch all orders from the database
	 * 
	 * @return Returns a List<Order> with all orders from the database
	 */
	public List<Order> getAll() {
		return orderDao.getAll();
	}

	/**Fetch only the orders that have not been processed (sent) or received.
	 * 
	 * @return Returns a List<Order>. Filters out orders that are already ordered or received
	 */
	public List<Order> getPending() {
		return orderDao.getPendingToOrder();
	}

	/**
	 * Set the the Received property of the order to true The order quantity gets
	 * added to the Product currentStock
	 * 
	 * @param order
	 *            Order to be flagged as received
	 */
	public void setReceived(Order order) {
		Product product = order.getProductId();

		product.setCurrentStock(product.getCurrentStock() + order.getQuantityMultiplier());
		productService.update(product);
		order.setReceived(true);
		this.update(order);
	}

	/**
	 * Delete an order from the database
	 * 
	 * @param id Id of the Order that has to be removed
	 */
	public void delete(int id) {
		orderDao.delete(id);
	}

	/**
	 * Creates a new Order in the database
	 * 
	 * @param order Order to be added to the database
	 */
	public void create(Order order) {
		orderDao.create(order);
	}

	/**
	 * Fetch a specific order from the database
	 * 
	 * @param id ID of the order that has to be fetched from the database
	 * @return Order object
	 */
	public Order get(int id) {
		return orderDao.get(id);
	}

	
	/**
	 * Update an Order in the database
	 * 
	 * @param order Order that will be updated in the database
	 */
	public void update(Order order) {
		orderDao.update(order);
	}

}
