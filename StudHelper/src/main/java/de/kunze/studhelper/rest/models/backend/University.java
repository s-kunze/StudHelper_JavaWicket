package de.kunze.studhelper.rest.models.backend;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.kunze.studhelper.rest.models.BaseModel;
import de.kunze.studhelper.rest.transfer.backend.UniversityTransfer;

/**
 * 
 * @author Stefan Kunze
 * 
 */
@Entity
@Table(name = "UNIVERSITY")
public class University extends BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "UNIVERSITY_ID")
	private Long id;

	@Column(name = "UNIVERSITY_NAME")
	private String name;
	
	@OneToMany(mappedBy = "university", orphanRemoval = true)
	private List<Department> departments;
	
	public University() {
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

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
	
	public UniversityTransfer transform() {
		UniversityTransfer result = new UniversityTransfer();
		result.setId(this.id);
		result.setName(this.name);
		
		return result;
	}

}
