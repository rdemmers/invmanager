package nl.roydemmers.invmanager.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import nl.roydemmers.invmanager.objects.User;

@Transactional
@Component("usersDao")
public class UserDao extends AbstractDao {
	

	@Secured("ROLE_ADMIN")
	@Transactional
	public void create(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		session().save(user);
		
	}
	
	public User getUserEmail(String username) {
		
		Criteria crit = session().createCriteria(User.class);
		crit.add(Restrictions.eq("username", username));
		User user = (User)crit.uniqueResult();
		return user;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getUserList(){
		return session().createQuery("from User").list();
	}
	
	
	
}