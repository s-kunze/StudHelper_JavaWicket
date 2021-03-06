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

import de.kunze.studhelper.rest.transfer.backend.PartTransfer;

/**
 * 
 * @author Stefan Kunze
 * 
 */
@Entity
@Table(name = "PART")
public class Part {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "CREDITPOINTS")
	private Integer creditPoints;

	@OneToMany(mappedBy = "part", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Modul> module;
	
	@ManyToOne
	private DegreeCourse degreeCourse;

	public Part() {
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

	public DegreeCourse getDegreeCourse() {
		return this.degreeCourse;
	}

	public void setDegreeCourse(DegreeCourse degreeCourse) {
		this.degreeCourse = degreeCourse;
	}

	public List<Modul> getModule() {
		return module;
	}

	public void setModule(List<Modul> module) {
		this.module = module;
	}

	public PartTransfer transform() {
		PartTransfer part = new PartTransfer();
		part.setId(this.id);
		part.setName(this.name);
		part.setCreditPoints(this.creditPoints);
		
		return part;
	}
	
}
