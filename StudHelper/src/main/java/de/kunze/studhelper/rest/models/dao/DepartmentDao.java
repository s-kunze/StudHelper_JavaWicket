package de.kunze.studhelper.rest.models.dao;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import de.kunze.studhelper.rest.models.backend.DegreeCourse;
import de.kunze.studhelper.rest.models.backend.Department;

public class DepartmentDao extends BaseDao<Department> {

	public boolean saveDegreeCourse(Department department, DegreeCourse degreeCourse) {
	
		Transaction ta = null;
		try {
			if (department != null && degreeCourse != null) {
				ta = this.session.beginTransaction();
				this.session.update(department);
				this.session.save(degreeCourse);
				ta.commit();
			} 
		} catch(HibernateException e) {
			if(ta != null) 	ta.rollback();
			e.printStackTrace();
			
			return false;
		}
		
		return true;
	}
	
}
