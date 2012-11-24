package de.kunze.studhelper.rest.models.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import de.kunze.studhelper.rest.models.backend.Lecture;
import de.kunze.studhelper.rest.models.backend.Modul;

public class ModulDao extends BaseDao<Modul> {
	
	public void saveLectureForModul(Lecture lecture, Modul modul) {
		
		if(lecture.getModules() == null) {
			List<Modul> modulList = new ArrayList<Modul>();
			modulList.add(modul);
			
			lecture.setModules(modulList);
		} else {
			lecture.getModules().add(modul);
		}
		
		if(modul.getLectures() == null) {
			List<Lecture> lectureList = new ArrayList<Lecture>();
			lectureList.add(lecture);
			
			modul.setLectures(lectureList);
		} else {
			modul.getLectures().add(lecture);
		}
		
		Transaction ta = null;
		
		try {
			ta = this.session.beginTransaction();
			
			this.session.save(lecture);
			this.session.update(modul);
			
			ta.commit();
		} catch(HibernateException e) {
			if(ta != null) 	ta.rollback();

			e.printStackTrace();
		}
		
	}
	
}
