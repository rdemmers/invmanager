package nl.roydemmers.invmanager.dao;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import nl.roydemmers.invmanager.service.InventoryMailService;

public abstract class AbstractDao {

	protected NamedParameterJdbcTemplate jdbc;
	
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	protected InventoryMailService inventoryMailService;
	
	public Session session() {
		return sessionFactory.getCurrentSession();
	}
		

	@Autowired
	public void setDataSource(DataSource jdbc) {
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}
}
