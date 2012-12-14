package de.kunze.studhelper.rest.models.dao;

import java.util.List;

import org.hibernate.Query;

import de.kunze.studhelper.rest.exception.NoUserFoundException;
import de.kunze.studhelper.rest.models.user.User;

public class UserDao extends BaseDao<User> {

	public User getUserByName(String username) throws NoUserFoundException {
		Query query = this.session.getNamedQuery("findUserByName").setString("username", username);
		
		List<User> users = query.list();
		
		if(users == null|| users.isEmpty()) {
			throw new NoUserFoundException("No User found");
		} else {
			if(users.size() != 1) {
				throw new NoUserFoundException("No user found");
			} else {
				return users.get(0);
			}
		}
	}
	
	
}
