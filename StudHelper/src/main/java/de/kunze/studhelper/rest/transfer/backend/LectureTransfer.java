package de.kunze.studhelper.rest.transfer.backend;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import de.kunze.studhelper.rest.models.backend.Lecture;

@XmlRootElement
public class LectureTransfer implements Serializable {

	private Long id;
	private String name;
	private Integer creditPoints;
	private static final long serialVersionUID = 5506263912547366276L;

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

	public Lecture transform() {
		Lecture lecture = new Lecture();
		lecture.setId(this.id);
		lecture.setName(this.name);
		lecture.setCreditPoints(this.creditPoints);
		
		return lecture;
	}
	
}
