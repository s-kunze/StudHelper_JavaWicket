package de.kunze.studhelper.rest.transfer.user;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import de.kunze.studhelper.rest.models.user.User;

@XmlRootElement
public class NewUserTransfer implements Serializable {

	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private String password2;
	
	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	private static final long serialVersionUID = 1L;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User transform() {
		User user = new User();
		user.setFirstname(this.firstname);
		user.setLastname(this.lastname);
		user.setUsername(this.username);
		user.setPassword(this.password);
		
		return user;
	}
}
