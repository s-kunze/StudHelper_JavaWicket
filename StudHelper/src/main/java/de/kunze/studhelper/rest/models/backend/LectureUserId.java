package de.kunze.studhelper.rest.models.backend;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import de.kunze.studhelper.rest.models.user.User;

@Embeddable
public class LectureUserId implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Lecture lecture;
	
	@ManyToOne
	private User user;

	public Lecture getLecture() {
		return lecture;
	}

	public void setLecture(Lecture lecture) {
		this.lecture = lecture;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public boolean equals(Object o) {
        if (this == o) 
        	return true;
  
        if (o == null || getClass() != o.getClass()) 
        	return false;
 
        LectureUserId that = (LectureUserId) o;
 
        if (user != null ? !user.equals(that.user) : that.user != null)
        	return false;
        
        if (lecture != null ? !lecture.equals(that.lecture) : that.lecture != null)
            return false;
 
        return true;
    }
	
	public int hashCode() {
        int result;
        result = (user != null ? user.hashCode() : 0);
        result = 31 * result + (lecture != null ? lecture.hashCode() : 0);
        return result;
    }
}
