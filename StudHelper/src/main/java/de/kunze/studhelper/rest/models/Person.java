package de.kunze.studhelper.rest.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.log4j.helpers.Transform;

import de.kunze.studhelper.rest.transfer.PersonTransfer;

@Entity
@Table(name = "PERSON")
@NamedQueries({ @NamedQuery(name = "findPersonById", query = "from Person p where p.id = :pid") })
public class Person {

	private int id;
	private String name;

	public Person() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PERSON_ID")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "PERSON_NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public PersonTransfer transform() {
		PersonTransfer result = new PersonTransfer();
		result.setName(this.name);
		
		return result;
	}

}
