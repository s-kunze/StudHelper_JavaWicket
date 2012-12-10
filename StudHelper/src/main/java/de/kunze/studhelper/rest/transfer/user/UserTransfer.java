package de.kunze.studhelper.rest.transfer.user;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import de.kunze.studhelper.rest.models.user.User;

@XmlRootElement
public class UserTransfer implements Serializable {

	private Long id;
	private String firstname;
	private String lastname;
	private String username;
	private Integer creditpoints;
	private static final long serialVersionUID = -2698915082574372684L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Integer getCreditpoints() {
		return creditpoints;
	}

	public void setCreditpoints(Integer creditpoints) {
		this.creditpoints = creditpoints;
	}

	public User transform() {
		User result = new User();
		result.setId(this.id);
		result.setFirstname(this.firstname);
		result.setLastname(this.lastname);
		result.setUsername(this.username);		
		
		return result;
	}
	
}
