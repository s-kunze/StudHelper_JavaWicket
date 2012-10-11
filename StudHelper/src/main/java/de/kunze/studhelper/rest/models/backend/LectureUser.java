package de.kunze.studhelper.rest.models.backend;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.kunze.studhelper.rest.models.user.User;

@Entity
@Table(name = "LECTURE_USER")
@AssociationOverrides({
		@AssociationOverride(name = "pk.lecture", joinColumns = @JoinColumn(name = "LECTURE_ID")),
		@AssociationOverride(name = "pk.user", joinColumns = @JoinColumn(name = "USER_ID")) })
public class LectureUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LectureUserId pk = new LectureUserId();
	
	@Column(name = "MARK")
	private Float mark;
	
	public LectureUser() {
	}
	
	public LectureUserId getPk() {
		return pk;
	}

	public void setPk(LectureUserId pk) {
		this.pk = pk;
	}

	@Transient
	public Lecture getLecture() {
		return getPk().getLecture();
	}
	
	public void setLecture(Lecture lecture) {
		getPk().setLecture(lecture);
	}
	
	@Transient
	public User getUser() {
		return getPk().getUser();
	}
	
	public void setUser(User user) {
		getPk().setUser(user);
	}
	
	public Float getMark() {
		return mark;
	}

	public void setMark(Float mark) {
		this.mark = mark;
	}
	
	public boolean equals(Object o) {
		if(this == o)
			return true;
		
		if(o == null || getClass() != o.getClass())
			return false;
		
		LectureUser that = (LectureUser) o;
		
		if (getPk() != null ? !getPk().equals(that.getPk())
			: that.getPk() != null)
			return false;
			
		return true;
	}

	public int hashCode() {
		return (getPk() != null ? getPk().hashCode() : 0);
	}
}
