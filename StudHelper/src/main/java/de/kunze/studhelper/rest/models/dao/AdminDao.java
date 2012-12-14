package de.kunze.studhelper.rest.models.dao;

import java.util.List;

import org.hibernate.Query;

import de.kunze.studhelper.rest.exception.NoUserFoundException;
import de.kunze.studhelper.rest.models.user.Admin;

public class AdminDao extends BaseDao<Admin> {

	public Admin getUserByName(String username) throws NoUserFoundException {
		Query query = this.session.getNamedQuery("findAdminByName").setString("username", username);
		
		List<Admin> admins = query.list();
		
		if(admins == null|| admins.isEmpty()) {
			throw new NoUserFoundException("No User found");
		} else {
			if(admins.size() != 1) {
				throw new NoUserFoundException("No user found");
			} else {
				return admins.get(0);
			}
		}
	}
	
	
}