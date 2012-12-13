package de.kunze.studhelper.view.core;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

public class StudhelperWebSession extends AuthenticatedWebSession {

	private String username;

	public StudhelperWebSession(Request request) {
		super(request);
	}

	private static final long serialVersionUID = 1859498148545214318L;

	@Override
	public boolean authenticate(String username, String password) {
		return ("test".equals(username) && "test".equals(password));
	}

	@Override
	public Roles getRoles() {
		Roles roles = new Roles();
		
		if(isSignedIn()) {
			roles.add("USER");
		}
	    return roles;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
