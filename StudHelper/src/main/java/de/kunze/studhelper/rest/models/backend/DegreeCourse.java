package de.kunze.studhelper.rest.models.backend;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import de.kunze.studhelper.rest.transfer.backend.DegreeCourseTransfer;

/**
 * 
 * @author Stefan Kunze
 * 
 */
@Entity
@Table(name = "DEGREECOURSE")
public class DegreeCourse {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DEGREECOURSE_ID")
	private Long id;

	@Column(name = "DEGREECOURSE_NAME")
	private String name;

	@Column(name = "DEGREECOURSE_CREDITPOINTS")
	private Integer creditPoints;

	@OneToMany(mappedBy = "degreeCourse", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Part> parts;

	@ManyToOne
	private Department department;

	public DegreeCourse() {
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

	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public DegreeCourseTransfer transform() {
		DegreeCourseTransfer dct = new DegreeCourseTransfer();
		dct.setId(this.id);
		dct.setName(this.name);
		dct.setCreditPoints(this.creditPoints);
		
		return dct;
	}

}
