package de.kunze.studhelper.rest.models.backend;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.kunze.studhelper.rest.transfer.backend.LectureTransfer;

/**
 * 
 * @author Stefan Kunze
 * 
 */
@Entity
@Table(name = "LECTURE")
public class Lecture implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "LECTURE_ID")
	private Long id;

	@Column(name = "LECTURE_NAME")
	private String name;

	@Column(name = "LECTURE_CREDITPOINTS")
	private Integer creditPoints;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "lectures")
	private List<Modul> modules;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.lecture", cascade = CascadeType.ALL)
	private Set<LectureUser> lecturePerson = new HashSet<LectureUser>(0);

	public Lecture() {
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

	public Set<LectureUser> getLecturePerson() {
		return lecturePerson;
	}

	public void setLecturePerson(Set<LectureUser> lecturePerson) {
		this.lecturePerson = lecturePerson;
	}

	public List<Modul> getModules() {
		return modules;
	}

	public void setModules(List<Modul> modules) {
		this.modules = modules;
	}
	
	public LectureTransfer transform() {
		LectureTransfer lecture = new LectureTransfer();
		lecture.setId(this.id);
		lecture.setName(this.name);
		lecture.setCreditPoints(this.creditPoints);
		
		return lecture;
	}
}
