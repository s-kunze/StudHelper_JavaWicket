package de.kunze.studhelper.rest.models;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FORMULAR")
@NamedQueries({
		@NamedQuery(name = "findAllFormulars", query = "from Formular f where f.project = :pid"),
		@NamedQuery(name = "findFormularById", query = "from Formular f where f.id = :fid") })
public class Formular {

	private int id;
	private String name;
	private String description;
//	private Project project;

	public Formular() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FORMULAR_ID")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "FORMULAR_NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "FORMULAR_DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "FORMULAR_PROJECTID")
//	public Project getProject() {
//		return project;
//	}
//
//	public void setProject(Project project) {
//		this.project = project;
//	}
//
//	public FormularTransfer transform() {
//		FormularTransfer formularTransfer = new FormularTransfer();
//		formularTransfer.setId(Integer.toString(this.id));
//		formularTransfer.setName(this.name);
//		formularTransfer.setDescription(this.description);
//
//		return formularTransfer;
//	}

}
