package de.kunze.studhelper.rest.transfer.backend;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import de.kunze.studhelper.rest.models.backend.University;

@XmlRootElement
public class UniversityTransfer implements Serializable {

	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public University transform() {
		University result = new University();
		result.setId(this.id);
		result.setName(this.name);
		
		return result;
	}

	
}
