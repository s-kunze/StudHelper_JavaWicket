package de.kunze.studhelper.rest.models.dao;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import de.kunze.studhelper.rest.models.backend.DegreeCourse;
import de.kunze.studhelper.rest.models.backend.Part;

public class DegreeCourseDao extends BaseDao<DegreeCourse> {

	public boolean savePart(DegreeCourse degreeCourse, Part part) {
		
		Transaction ta = null;
		try {
			if (degreeCourse != null && part != null) {
				ta = this.session.beginTransaction();
				this.session.update(degreeCourse);
				this.session.save(part);
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
