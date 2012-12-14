package de.kunze.studhelper.rest.auth;

import de.kunze.studhelper.rest.exception.NoUserFoundException;
import de.kunze.studhelper.rest.exception.WrongPasswordException;
import de.kunze.studhelper.rest.models.dao.AdminDao;
import de.kunze.studhelper.rest.models.dao.UserDao;
import de.kunze.studhelper.rest.models.user.Admin;
import de.kunze.studhelper.rest.models.user.User;
import de.kunze.studhelper.rest.transfer.user.AuthTransfer;
import de.kunze.studhelper.rest.transfer.user.UserType;

public class AuthorizationHandler {
	
	public AuthTransfer authUser(String username, String password) throws NoUserFoundException, WrongPasswordException {
		UserDao dao = new UserDao();
		
		try {
			User user = dao.getUserByName(username);
			
			if(!user.getPassword().equals(password)) {
				throw new WrongPasswordException("Wrong password");
			}
			
			return new AuthTransfer(user.getId(), UserType.USER, user.getUsername());
		} catch(NoUserFoundException e) {
			throw e;
		}
	}
	
	public AuthTransfer authAdmin(String username, String password) throws NoUserFoundException, WrongPasswordException {
		AdminDao dao = new AdminDao();
		
		try {
			Admin admin = dao.getUserByName(username);
			
			if(!admin.getPassword().equals(password)) {
				throw new WrongPasswordException("Wrong password");
			}
			
			return new AuthTransfer(admin.getId(), UserType.ADMIN, admin.getUsername());
		} catch(NoUserFoundException e) {
			throw e;
		}
	}
	
}
