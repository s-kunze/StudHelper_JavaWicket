package de.kunze.studhelper.rest.models.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kunze.studhelper.rest.models.backend.Lecture;
import de.kunze.studhelper.rest.models.backend.LectureUser;
import de.kunze.studhelper.rest.models.user.User;

public class LectureUserDao extends BaseDao<Lecture> {

	private static Logger logger = LoggerFactory.getLogger(LectureUserDao.class);
	
	public boolean save(Lecture lecture, User user, float mark) {
		Transaction ta = null;
		
		try {
			ta = this.session.beginTransaction();
			LectureUser lectureUser = new LectureUser();
			lectureUser.setLecture(lecture);
			lectureUser.setUser(user);
			lectureUser.setMark(mark);

			Set<LectureUser> lecturePersons = lecture.getLecturePerson();

			if (lecturePersons == null) {
				lecturePersons = new HashSet<LectureUser>();
			}
			lecturePersons.add(lectureUser);

			this.session.saveOrUpdate(lecture);
			ta.commit();
		} catch (HibernateException e) {
			if (ta != null) ta.rollback();
			logger.error("", e);

			return false;
		}

		return true;
	}

}
