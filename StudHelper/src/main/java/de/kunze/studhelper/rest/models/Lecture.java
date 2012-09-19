package de.kunze.studhelper.rest.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LECTURE")
public class Lecture {

	private long id;
	private String name;
	private Person lecturer;
	private int creditPoints;

	public Lecture() {}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "LECTURE_ID")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "LECTURE_NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToOne(cascade = CascadeType.ALL)
	public Person getLecturer() {
		return lecturer;
	}

	public void setLecturer(Person lecturer) {
		this.lecturer = lecturer;
	}

	@Column(name = "LECTURE_CREDITPOINTS")
	public int getCreditPoints() {
		return creditPoints;
	}

	public void setCreditPoints(int creditPoints) {
		this.creditPoints = creditPoints;
	}

}
