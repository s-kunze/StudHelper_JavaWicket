package de.kunze.studhelper.rest.models.backend;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.kunze.studhelper.rest.models.BaseModel;

/**
 * 
 * @author Stefan Kunze
 * 
 */
@Entity
@Table(name = "MODUL")
public class Modul extends BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MODUL_ID")
	private Long id;

	@Column(name = "MODUL_NAME")
	private String name;

	@Column(name = "MODUL_CREDITPOINTS")
	private Integer creditPoints;

	@OneToMany(mappedBy = "modul", orphanRemoval = true)
	private List<Lecture> lectures;

	@ManyToOne
	private DegreeCourse degreeCourse;

	public Modul() {
	}

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

	public List<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(List<Lecture> lectures) {
		this.lectures = lectures;
	}

	public DegreeCourse getDegreeCourse() {
		return degreeCourse;
	}

	public void setDegreeCourse(DegreeCourse degreeCourse) {
		this.degreeCourse = degreeCourse;
	}

}
