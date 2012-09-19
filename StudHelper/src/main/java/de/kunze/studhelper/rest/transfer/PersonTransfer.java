package de.kunze.studhelper.rest.transfer;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PersonTransfer {

	public PersonTransfer() {}
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
