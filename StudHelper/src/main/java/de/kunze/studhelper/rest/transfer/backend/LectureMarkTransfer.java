package de.kunze.studhelper.rest.transfer.backend;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LectureMarkTransfer implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private Integer creditPoints;
	private Float mark;

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

	public Float getMark() {
		return mark;
	}

	public void setMark(Float mark) {
		this.mark = mark;
	}


}
