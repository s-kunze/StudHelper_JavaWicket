package de.kunze.studhelper.test;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.kunze.studhelper.rest.core.HibernateSession;
import de.kunze.studhelper.rest.models.backend.Department;
import de.kunze.studhelper.rest.models.backend.University;
import de.kunze.studhelper.rest.models.dao.BaseDao;

/**
 * Testet die Verbindung zwischen Universitäten und Departments
 * @author stefan
 *
 */
public class UniversityDepartmentTest {

	private SessionFactory sessionFactory;
	private Session session = null;
	
	private Long idUni = null;

	@Before
	public void tearUp() {
		try {
			sessionFactory = HibernateSession.getInstance().getSessionFactory();
			session = sessionFactory.openSession();
		} catch (Exception e) {
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
	public void createUniversityWithDepartments() {
		
		//Universität anlegen
		University university = new University();
		university.setName("Friedrich Schiller University");

		BaseDao<University> daoUni = new BaseDao<University>(University.class);
		BaseDao<Department> daoDep = new BaseDao<Department>(Department.class);
		
		//Departments anlegen dafür
		Department department1 = new Department();
		department1.setName("Fakultät Mathematik und Informatik");

		Department department2 = new Department();
		department2.setName("Fakultät Psychologie");
		
		this.idUni = daoUni.save(university);
		
		department1.setUniversity(university);
		department2.setUniversity(university);
		
		daoDep.save(department1);
		daoDep.save(department2);

		List<Department> departments = new ArrayList<Department>();
		departments.add(department1);
		departments.add(department2);
		
		university.setDepartments(departments);
		
		daoUni.update(university);
	}
	
	@Test
	public void deleteUniversityWithDepartments() {
		BaseDao<University> daoUni = new BaseDao<University>(University.class);
		University university = daoUni.get(this.idUni);
		
		daoUni.delete(university);
	}
	
}
