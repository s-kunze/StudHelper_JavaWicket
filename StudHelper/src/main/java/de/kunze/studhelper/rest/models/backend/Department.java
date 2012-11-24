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

import de.kunze.studhelper.rest.transfer.backend.DepartmentTransfer;

/**
 * 
 * @author Stefan Kunze
 * 
 */
@Entity
@Table(name = "DEPARTMENT")
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DEPARTMENT_ID")
	private Long id;

	@Column(name = "DEPARTMENT_NAME")
	private String name;

	@OneToMany(mappedBy = "department", orphanRemoval=true , cascade = CascadeType.ALL)
	private List<DegreeCourse> degreecourses;

	@ManyToOne
	private University university;

	public Department() {
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

	public List<DegreeCourse> getDegreecourses() {
		return degreecourses;
	}

	public void setDegreecourses(List<DegreeCourse> degreecourses) {
		this.degreecourses = degreecourses;
	}

	public University getUniversity() {
		return university;
	}

	public void setUniversity(University university) {
		this.university = university;
	}

	public DepartmentTransfer transform() {
		DepartmentTransfer dt = new DepartmentTransfer();
		dt.setId(this.id);
		dt.setName(this.name);

		return dt;
	}

}
