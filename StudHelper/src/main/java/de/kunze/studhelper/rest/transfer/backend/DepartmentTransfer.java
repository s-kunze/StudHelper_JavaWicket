package de.kunze.studhelper.rest.transfer.backend;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import de.kunze.studhelper.rest.models.backend.Department;

@XmlRootElement
public class DepartmentTransfer implements Serializable {

	private static final long serialVersionUID = -2539448030532664865L;

	private Long id;
	private String name;
	private UniversityTransfer universityTransfer;

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

	public UniversityTransfer getUniversityTransfer() {
		return universityTransfer;
	}

	public void setUniversityTransfer(UniversityTransfer universityTransfer) {
		this.universityTransfer = universityTransfer;
	}
	
	public Department transform() {
		Department department = new Department();
		department.setId(this.id);
		department.setName(this.name);
		
		return department;
	}

}
