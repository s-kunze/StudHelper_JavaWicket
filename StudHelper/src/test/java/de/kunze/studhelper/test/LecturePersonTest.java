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
 * Diese Klasse testet den BaseDao mit der Entity Person
 * 
 * @author Stefan Kunze
 * 
 */
public class LecturePersonTest {

	private SessionFactory sessionFactory;
	private Session session = null;

	@Before
	public void init() {
		try {
			sessionFactory = HibernateSession.getInstance().getSessionFactory();
			session = sessionFactory.openSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@After
	public void delete() {
		try {
			session.close();
			sessionFactory.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDelete() {
//		User person = new User();
//		person.setName("Test Testi");
//
//		BaseDao<User> dao = new BaseDao<User>(User.class);
//		Integer id = dao.save(person);
//
//		User newPerson = dao.get(id);
//
//		if (newPerson != null) {
//			dao.delete(newPerson);
//
//			newPerson = dao.get(id);
//
//			assertTrue(newPerson == null);
//		}
	}

	@Test
	public void testCreate() {
//
//		User person = new User();
//		person.setName("Test Testi");
//
//		BaseDao<User> dao = new BaseDao<User>(User.class);
//		Integer id = dao.save(person);
//
//		if (id != null) {
//
//			User newPerson = null;
//			try {
//				newPerson = dao.get(id);
//
//				assertEquals(person.getId(), newPerson.getId());
//				assertEquals(person.getName(), newPerson.getName());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			dao.delete(person);
//		}
	}

	@Test
	public void testGetAll() {
//		User person1 = new User();
//		person1.setName("Test");
//
//		User person2 = new User();
//		person2.setName("Test1");
//
//		User person3 = new User();
//		person3.setName("Test2");
//
//		BaseDao<User> dao = new BaseDao<User>(User.class);
//
//		try {
//			dao.save(person1);
//			dao.save(person2);
//			dao.save(person3);
//
//			List<User> result = dao.getAll();
//			
//			assertTrue(result.size() == 3);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		dao.delete(person1);
//		dao.delete(person2);
//		dao.delete(person3);
	}

	@Test
	public void testUpdate() {
//		User person = new User();
//		person.setName("Test");
//
//		BaseDao<User> dao = new BaseDao<User>(User.class);
//		Integer id = dao.save(person);
//
//		person.setName("Testi");
//
//		dao.update(person);
//
//		User newPerson = null;
//		try {
//			newPerson = dao.get(id);
//
//			assertEquals(person.getId(), newPerson.getId());
//			assertEquals(person.getName(), newPerson.getName());
//
//			dao.delete(newPerson);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	public static void main(String[] args) {
		SessionFactory sessionFactory = HibernateSession.getInstance().getSessionFactory();
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		 
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
	}

}
