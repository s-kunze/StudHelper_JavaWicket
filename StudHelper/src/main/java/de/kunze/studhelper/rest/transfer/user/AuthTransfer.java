package de.kunze.studhelper.rest.transfer.user;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AuthTransfer implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private UserType type;
	private String username;

	public AuthTransfer() {

	}

	public AuthTransfer(Long id, UserType type, String username) {
		this.id = id;
		this.type = type;
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
