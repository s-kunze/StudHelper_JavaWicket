package de.kunze.studhelper.rest.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kunze.studhelper.rest.exception.NoUserFoundException;
import de.kunze.studhelper.rest.exception.WrongPasswordException;
import de.kunze.studhelper.rest.models.dao.UserDao;
import de.kunze.studhelper.rest.models.user.User;

public class AuthorizationHandler {

	private final static Logger logger = LoggerFactory.getLogger(AuthorizationHandler.class);
	
	public boolean auth(String username, String password) throws NoUserFoundException, WrongPasswordException {
		UserDao dao = new UserDao();
		
		try {
			User user = dao.getUserByName(username);
			
			if(!user.getPassword().equals(password)) {
				throw new WrongPasswordException("Wrong password");
			}
			
			return true;
		} catch(NoUserFoundException e) {
			throw e;
		}
	}
	
}
