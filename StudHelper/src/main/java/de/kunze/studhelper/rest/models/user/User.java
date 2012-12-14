package de.kunze.studhelper.rest.models.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NaturalId;

import de.kunze.studhelper.rest.models.backend.DegreeCourse;
import de.kunze.studhelper.rest.models.backend.LectureUser;
import de.kunze.studhelper.rest.transfer.user.UserTransfer;

/**
 * TODO: Maybe we should use a Admin-user. Maybe Permsisions/Roles etc.
 * 
 * @author Stefan Kunze
 * 
 */
@Entity
@Table(name = "USER")
@NamedQueries({ @NamedQuery(name = "findUserByName", query = "from User u where u.username = :username") })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long id;

	@Column(name = "FIRSTNAME")
	private String firstname;

	@Column(name = "LASTNAME")
	private String lastname;

	@Column(name = "USERNAME")
	@NaturalId
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "CREDITPOINTS")
	private Integer creditPoints;

	@ManyToOne
	private DegreeCourse degreeCourse;

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval=true, mappedBy = "pk.user")
	@Cascade(CascadeType.ALL)
	private Set<LectureUser> lectureUser = new HashSet<LectureUser>(0);

	public User() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getCreditPoints() {
		return creditPoints;
	}

	public void setCreditPoints(Integer creditPoints) {
		this.creditPoints = creditPoints;
	}

	public Set<LectureUser> getLectureUser() {
		return lectureUser;
	}

	public void setLectureUser(Set<LectureUser> lectureUser) {
		this.lectureUser = lectureUser;
	}

	public DegreeCourse getDegreeCourse() {
		return degreeCourse;
	}

	public void setDegreeCourse(DegreeCourse degreeCourse) {
		this.degreeCourse = degreeCourse;
	}

	public UserTransfer transform() {
		UserTransfer result = new UserTransfer();
		result.setId(this.id);
		result.setFirstname(this.firstname);
		result.setLastname(this.lastname);
		result.setUsername(this.username);

		return result;
	}

}
