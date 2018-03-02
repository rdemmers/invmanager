package nl.roydemmers.invmanager.test.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nl.roydemmers.invmanager.dao.ProductDao;
import nl.roydemmers.invmanager.dao.SupplierDao;
import nl.roydemmers.invmanager.objects.Product;
import nl.roydemmers.invmanager.objects.Supplier;

@ActiveProfiles("dev")
@ContextConfiguration(locations = { "classpath:nl/roydemmers/invmanager/config/dao-context.xml", "classpath:nl/roydemmers/invmanager/config/security-context.xml",
		"classpath:nl/roydemmers/invmanager/test/config/datasource.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SupplierDaoTest {

	@Autowired
	private SupplierDao supplierDao;
	
	@Autowired
	private DataSource dataSource;
	
	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		jdbc.execute("delete from suppliers");
	}
	
	@Test
	public void testCreateSupplier() {
		Supplier supplier1 = new Supplier("Supplier A", "Jane Doe", "orderstuff@ordermail.com", "questions@questionmail.com", "071991992");
		Supplier supplier2 = new Supplier("Supplier B", "Jane Doe", "orderstuff@ordermail.com", "questions@questionmail.com", "071991992");
		Supplier supplier3 = new Supplier("Supplier C", "Jane Doe", "orderstuff@ordermail.com", "questions@questionmail.com", "071991992");
		Supplier supplier4 = new Supplier("Supplier D", "Jane Doe", "orderstuff@ordermail.com", "questions@questionmail.com", "071991992");
		Supplier supplier5 = new Supplier("Supplier E", "Jane Doe", "orderstuff@ordermail.com", "questions@questionmail.com", "071991992");
		
		supplierDao.create(supplier1);
		supplierDao.create(supplier2);
		supplierDao.create(supplier3);
		supplierDao.create(supplier4);
		supplierDao.create(supplier5);
		
		List<Supplier> suppliers = supplierDao.getAll();
		
		assertEquals("Number of suppliers should be 5", 5, suppliers.size());
		assertEquals("Created supplier should be identical to retrieved supplier", supplier1, suppliers.get(0));
		
		supplierDao.delete("Supplier B");
		supplierDao.delete("Supplier C");
		supplierDao.delete("Supplier E");
		
		List<Supplier> suppliersDeleted = supplierDao.getAll();
		
		assertEquals("Number of suppliers should be 2 after deletion", 2, suppliersDeleted.size());
		
		supplier1.setName("Actual supplier name");
		supplier5.setOrderMail("hotmail@gmail.com");
		
		supplierDao.update(supplier1);
		supplierDao.update(supplier5);
		
		List<Supplier> suppliersUpdated = supplierDao.getAll();
		
		assertEquals("Supplier1 hasn't properly updated in the database", supplier1.getName(), suppliersUpdated.get(0).getName());
		assertEquals("Supplier6 hasn't properly updated in the database", supplier1.getOrderMail(), suppliersUpdated.get(1).getOrderMail());
	}
	
}
