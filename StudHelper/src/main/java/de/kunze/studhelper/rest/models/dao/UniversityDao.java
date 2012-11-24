package de.kunze.studhelper.rest.models.dao;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import de.kunze.studhelper.rest.models.backend.Department;
import de.kunze.studhelper.rest.models.backend.University;

public class UniversityDao extends BaseDao<University> {

	public boolean saveDepartment(University university, Department department) {
	
		Transaction ta = null;
		try {
			if (university != null && department != null) {
				ta = this.session.beginTransaction();
				this.session.update(university);
				this.session.save(department);
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
