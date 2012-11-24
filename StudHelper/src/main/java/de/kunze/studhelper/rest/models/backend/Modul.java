package de.kunze.studhelper.rest.models.backend;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import de.kunze.studhelper.rest.transfer.backend.ModulTransfer;

/**
 * 
 * @author Stefan Kunze
 * 
 */
@Entity
@Table(name = "MODUL")
public class Modul {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MODUL_ID")
	private Long id;

	@Column(name = "MODUL_NAME")
	private String name;

	@Column(name = "MODUL_CREDITPOINTS")
	private Integer creditPoints;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "MODUL_LECTURE", joinColumns = { 
			@JoinColumn(name = "MODUL_ID", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "LECTURE_ID", 
					nullable = false, updatable = false) })
	private List<Lecture> lectures;

	@ManyToOne
	private Part part;

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

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public ModulTransfer transform() {
		ModulTransfer modul = new ModulTransfer();
		modul.setId(this.id);
		modul.setName(this.name);
		modul.setCreditPoints(this.creditPoints);
		
		return modul;
	}
	
}
