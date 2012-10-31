package de.kunze.studhelper.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.kunze.studhelper.rest.core.HibernateSession;
import de.kunze.studhelper.rest.models.backend.Lecture;
import de.kunze.studhelper.rest.models.backend.LectureUser;
import de.kunze.studhelper.rest.models.user.User;

/**
 * Diese Klasse testet eine N:M-Relationen mit einem Wert in der Verbindungstabelle
 * für Lecture und User
 * Notenrelation
 * 
 * @author stefan
 *
 */
public class NMRelationTest {

	private SessionFactory sessionFactory = null;
	private Session session = null;
	
	@Before
	public void tearUp() {
		try {
			this.sessionFactory = HibernateSession.getInstance().getSessionFactory();
			this.session = sessionFactory.openSession();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void tearDown() {
		try {
			session.close();
			sessionFactory.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	/** TODO: make this to a DAO */
	public void testRelation() {
		 
		this.session.beginTransaction();
		
	    Lecture lecture = new Lecture();
	    lecture.setName("Mathe 1");
	    lecture.setCreditPoints(3);
	 
	    User user = new User();
	    user.setFirstname("Stefan");
	    user.setLastname("Kunze");
	    
	    session.save(user);
	    
	    LectureUser lectureUser = new LectureUser();
	    lectureUser.setLecture(lecture);
	    lectureUser.setUser(user);
	    lectureUser.setMark(1.7f);
	    
	    lecture.getLecturePerson().add(lectureUser);
	    
	    session.save(lecture);
	 
	    session.getTransaction().commit();
	    
	    /** TODO: hier etwas testen */
	    
	    session.getTransaction().begin();
	    
	    session.delete(lecture);
	    session.delete(user);
	    session.delete(lectureUser);
	    
	    session.getTransaction().commit();
	}
	
}
