package de.kunze.studhelper.rest.models.backend;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.kunze.studhelper.rest.models.user.User;
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
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "CREDITPOINTS")
	private Integer creditPoints;

	@OneToMany(mappedBy = "degreeCourse", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Part> parts;

	@OneToMany(mappedBy = "degreeCourse", cascade = CascadeType.ALL)
	private List<User> users;
	
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
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public DegreeCourseTransfer transform() {
		DegreeCourseTransfer dct = new DegreeCourseTransfer();
		dct.setId(this.id);
		dct.setName(this.name);
		dct.setCreditPoints(this.creditPoints);
		
		return dct;
	}

}
