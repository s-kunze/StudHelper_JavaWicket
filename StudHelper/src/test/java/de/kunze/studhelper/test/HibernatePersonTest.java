package de.kunze.studhelper.test;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import de.kunze.studhelper.rest.core.HibernateSession;
import de.kunze.studhelper.rest.models.Person;
import de.kunze.studhelper.rest.models.dao.BaseDao;

/**
 * Diese Klasse testet den BaseDao mit der Entity Person
 * 
 * @author Stefan Kunze
 * 
 */
public class HibernatePersonTest {

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
		Person person = new Person();
		person.setName("Test Testi");

		BaseDao<Person> dao = new BaseDao<Person>(Person.class);
		Integer id = dao.save(person);

		Person newPerson = dao.get(id);

		if (newPerson != null) {
			dao.delete(newPerson);

			newPerson = dao.get(id);

			assertTrue(newPerson == null);
		}
	}

	@Test
	public void testCreate() {

		Person person = new Person();
		person.setName("Test Testi");

		BaseDao<Person> dao = new BaseDao<Person>(Person.class);
		Integer id = dao.save(person);

		if (id != null) {

			Person newPerson = null;
			try {
				newPerson = dao.get(id);

				assertEquals(person.getId(), newPerson.getId());
				assertEquals(person.getName(), newPerson.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}

			dao.delete(person);
		}
	}

	@Test
	public void testGetAll() {
		Person person1 = new Person();
		person1.setName("Test");

		Person person2 = new Person();
		person2.setName("Test1");

		Person person3 = new Person();
		person3.setName("Test2");

		BaseDao<Person> dao = new BaseDao<Person>(Person.class);

		try {
			dao.save(person1);
			dao.save(person2);
			dao.save(person3);

			List<Person> result = dao.getAll();
			
			assertTrue(result.size() == 3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		dao.delete(person1);
		dao.delete(person2);
		dao.delete(person3);
	}

	@Test
	public void testUpdate() {
		Person person = new Person();
		person.setName("Test");

		BaseDao<Person> dao = new BaseDao<Person>(Person.class);
		Integer id = dao.save(person);

		person.setName("Testi");

		dao.update(person);

		Person newPerson = null;
		try {
			newPerson = dao.get(id);

			assertEquals(person.getId(), newPerson.getId());
			assertEquals(person.getName(), newPerson.getName());

			dao.delete(newPerson);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
