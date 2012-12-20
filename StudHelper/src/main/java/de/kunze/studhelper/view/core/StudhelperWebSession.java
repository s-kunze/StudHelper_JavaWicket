package de.kunze.studhelper.view.core;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kunze.studhelper.rest.exception.NoUserFoundException;
import de.kunze.studhelper.rest.exception.WrongPasswordException;
import de.kunze.studhelper.rest.transfer.user.AuthTransfer;
import de.kunze.studhelper.rest.transfer.user.UserType;
import de.kunze.studhelper.view.rest.RestUser;

/**
 * This class represents a session. It has the userId, the UserType and the username as string
 * 
 * @author Stefan Kunze
 * @version 1.0
 * @since 1.0
 */
public class StudhelperWebSession extends AuthenticatedWebSession {

	private final static Logger logger = LoggerFactory.getLogger(StudhelperWebSession.class);
	
	private Long userId;
	private UserType type;
	private String username;

	public StudhelperWebSession(Request request) {
		super(request);
	}

	private static final long serialVersionUID = 1859498148545214318L;

	/**
	 * is the User a User ?
	 * @return
	 */
	public boolean isUser() {
		return UserType.USER.equals(this.type);
	}
	
	/**
	 * is the logged in User a Admin?
	 * @return
	 */
	public boolean isAdmin() {
		return UserType.ADMIN.equals(this.type);
	}
	
	/**
	 * This Method authenticate the User. It sends a request to the server.
	 */
	@Override
	public boolean authenticate(String username, String password) {
		RestUser restUser = new RestUser();

		try {
			AuthTransfer auth = restUser.login(username, password);
			this.userId = auth.getId();
			this.type = auth.getType();
			this.username = auth.getUsername();
			
			logger.info("UserID: {}", this.userId);
			logger.info("LOGIN WITH ROLE {}", this.type);
			return true;
		} catch (WrongPasswordException e) {
			// Return false at the end
		} catch (NoUserFoundException e) {
			// Return false at the end
		} catch (Exception e) {
			// Return false at the end
		}
		return false;
	}

	/**
	 * This Method returns the Roles. If the user is a user, it get the role USER. For admin, ADMIN
	 */
	@Override
	public Roles getRoles() {
		Roles roles = new Roles();

		if (isSignedIn()) {
			if(UserType.USER.equals(this.type)) {
				logger.info("Rolle User");
				roles.add("USER");				
			} else if(UserType.ADMIN.equals(this.type)) {
				logger.info("Rolle Admin");
				roles.add("ADMIN");								
			}
		}
		return roles;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
