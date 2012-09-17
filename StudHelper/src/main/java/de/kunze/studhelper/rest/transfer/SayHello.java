package de.kunze.studhelper.rest.transfer;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SayHello {
	
	private String message;

	public SayHello() {}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
