package de.kunze.studhelper.rest.transfer.backend;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import de.kunze.studhelper.rest.models.backend.Modul;

@XmlRootElement
public class ModulTransfer implements Serializable {

	private Long id;
	private String name;
	private Integer creditPoints;
	private static final long serialVersionUID = 1850887400512642235L;
	
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

	public Modul transform() {
		Modul modul = new Modul();
		modul.setId(this.id);
		modul.setName(this.name);
		modul.setCreditPoints(this.creditPoints);
		
		return modul;
	}

}
