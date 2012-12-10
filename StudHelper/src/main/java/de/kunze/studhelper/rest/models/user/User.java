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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.kunze.studhelper.rest.models.backend.DegreeCourse;
import de.kunze.studhelper.rest.models.backend.LectureUser;
import de.kunze.studhelper.rest.transfer.user.UserTransfer;

/**
 * TODO: Maybe we should use a Admin-user. Maybe Permsisions/Roles etc.
 * 
 * @author stefan
 *
 */
@Entity
@Table(name = "USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID")
	private long id;

	@Column(name = "USER_FIRSTNAME")
	private String firstname;

	@Column(name = "USER_LASTNAME")
	private String lastname;

	@Column(name = "USER_USERNAME")
	private String username;

	@Column(name = "USER_PASSWORD")
	private String password;

	@Column(name = "USER_CREDITPOINTS")
	private Integer creditPoints;

	@ManyToOne
	private DegreeCourse degreeCourse;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.user")
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
