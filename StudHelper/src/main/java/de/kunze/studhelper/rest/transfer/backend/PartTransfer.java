package de.kunze.studhelper.rest.transfer.backend;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import de.kunze.studhelper.rest.models.backend.Part;

@XmlRootElement
public class PartTransfer implements Serializable {

	private Long id;
	private String name;
	private Integer creditPoints;
	private static final long serialVersionUID = 1L;
	
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
	public Integer getCreditPoints() {
		return creditPoints;
	}
	public void setCreditPoints(Integer creditPoints) {
		this.creditPoints = creditPoints;
	}

	public Part transform() {
		Part part = new Part();
		part.setId(this.id);
		part.setName(this.name);
		part.setCreditPoints(this.creditPoints);
		
		return part;
	}
	
}
