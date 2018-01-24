package nl.roydemmers.invmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import nl.roydemmers.invmanager.dao.UserDao;
import nl.roydemmers.invmanager.objects.User;

@Service("usersService")
public class UserService {
	
	private UserDao userDao;
	
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Secured("ROLE_ADMIN")
	public void create(User user) {
		userDao.create(user);
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public User getUserEmail(String username) {
		return userDao.getUserEmail(username);
		
	}
	
	@Secured("ROLE_ADMIN")
	public List<User> getAllUsers() {
		return userDao.getUserList();
		
		
	}
}
