package de.kunze.studhelper.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.kunze.studhelper.rest.core.HibernateSession;
import de.kunze.studhelper.rest.models.backend.University;
import de.kunze.studhelper.rest.models.dao.BaseDao;

/**
 * Diese Klasse testet den DAO der Anwendung
 * 
 * @author Stefan Kunze
 * 
 */
public class DAOTest {

//	private SessionFactory sessionFactory;
//	private Session session = null;
//
//	@Before
//	public void tearUp() {
//		try {
//			sessionFactory = HibernateSession.getInstance().getSessionFactory();
//			session = sessionFactory.openSession();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@After
//	public void tearDown() {
//		try {
//			session.close();
//			sessionFactory.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testCreate() {
//		
//		University university = new University();
//		university.setName("Friedrich Schiller University");
//
//		BaseDao<University> dao = new BaseDao<University>(University.class);
//		Long id = dao.save(university);
//		
//		if (id != null) {
//
//			University newUniversity = null;
//			try {
//				newUniversity = dao.get(id);
//
//				assertEquals(university.getId(), newUniversity.getId());
//				assertEquals(university.getName(), newUniversity.getName());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			dao.delete(university);
//		}
//	}
//
//	@Test
//	public void testDelete() {
//		University university = new University();
//		university.setName("Friedrich Schiller Universität");
//		
//		BaseDao<University> dao = new BaseDao<University>(University.class);
//		Long id = dao.save(university);
//
//		University newUniversity = dao.get(id);
//		
//		if(newUniversity != null) {
//			dao.delete(newUniversity);
//			
//			newUniversity = dao.get(id);
//			
//			assertTrue(newUniversity == null);
//		}
//	}
//	
//	@Test
//	public void testGetAll() {
//		University university1 = new University();
//		university1.setName("Test");
//
//		University university2 = new University();
//		university2.setName("Test1");
//
//		University university3 = new University();
//		university3.setName("Test2");
//
//		BaseDao<University> dao = new BaseDao<University>(University.class);
//
//		try {
//			dao.save(university1);
//			dao.save(university2);
//			dao.save(university3);
//
//			List<University> result = dao.getAll();
//			
//			System.out.println("Result-Size: " + result.size());
//			
//			assertTrue(result.size() == 3);
//			assertTrue(result.get(0).getName().equals(university1.getName()));
//			assertTrue(result.get(1).getName().equals(university2.getName()));
//			assertTrue(result.get(2).getName().equals(university3.getName()));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		dao.delete(university1);
//		dao.delete(university2);
//		dao.delete(university3);
//	}
//
//	@Test
//	public void testUpdate() {
//		University university = new University();
//		university.setName("Test");
//
//		BaseDao<University> dao = new BaseDao<University>(University.class);
//		Long id = dao.save(university);
//
//		university.setName("Testi");
//
//		dao.update(university);
//
//		University newUniversity = null;
//		try {
//			newUniversity = dao.get(id);
//
//			assertEquals(university.getId(), newUniversity.getId());
//			assertEquals(university.getName(), newUniversity.getName());
//
//			dao.delete(newUniversity);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
