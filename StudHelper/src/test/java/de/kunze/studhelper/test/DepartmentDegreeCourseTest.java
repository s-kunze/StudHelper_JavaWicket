package de.kunze.studhelper.test;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.kunze.studhelper.rest.core.HibernateSession;
import de.kunze.studhelper.rest.models.backend.DegreeCourse;
import de.kunze.studhelper.rest.models.backend.Department;
import de.kunze.studhelper.rest.models.dao.BaseDao;

/**
 * Testet die Verbindung zwischen Departments und Studiengang
 * @author stefan
 *
 */
public class DepartmentDegreeCourseTest {

	private SessionFactory sessionFactory;
	private Session session = null;
	
	private Long idDep = null;

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
		
		//Department anlegen
		Department department = new Department();
		department.setName("Fakultät Mathematik und Informatik");

		BaseDao<Department> daoDep = new BaseDao<Department>(Department.class);
		BaseDao<DegreeCourse> daoDeg = new BaseDao<DegreeCourse>(DegreeCourse.class);
		
		//Studiengang anlegen
		DegreeCourse deg1 = new DegreeCourse();
		deg1.setName("Bachelor Informatik");
		deg1.setCreditPoints(new Integer(180));
		
		DegreeCourse deg2 = new DegreeCourse();
		deg2.setName("Master Informatik");
		deg2.setCreditPoints(new Integer(120));
		
		this.idDep = daoDep.save(department);
		
		deg1.setDepartment(department);
		deg2.setDepartment(department);
		
		daoDeg.save(deg1);
		daoDeg.save(deg2);

		List<DegreeCourse> degreeCourses = new ArrayList<DegreeCourse>();
		degreeCourses.add(deg1);
		degreeCourses.add(deg2);
		
		department.setDegreecourses(degreeCourses);
		
		daoDep.update(department);
	}
	
	@Test
	public void deleteUniversityWithDepartments() {
		BaseDao<Department> dapDep = new BaseDao<Department>(Department.class);
		Department department = dapDep.get(this.idDep);
		
		dapDep.delete(department);
	}
	
}
