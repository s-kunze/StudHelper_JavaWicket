package de.kunze.studhelper.rest.transfer.backend;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import de.kunze.studhelper.rest.models.backend.DegreeCourse;

@XmlRootElement
public class DegreeCourseTransfer implements Serializable {

	private Long id;
	private String name;	
	private Integer creditPoints;
	private static final long serialVersionUID = -2178356906943117817L;
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
	
	public DegreeCourse transform() {
		DegreeCourse degreeCourse = new DegreeCourse();
		degreeCourse.setId(this.id);
		degreeCourse.setName(this.name);
		degreeCourse.setCreditPoints(this.creditPoints);
		
		return degreeCourse;
	}
}
