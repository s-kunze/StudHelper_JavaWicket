package de.kunze.studhelper.rest.impl.backend;

import static de.kunze.studhelper.rest.util.Constans.BASE_URL;
import static de.kunze.studhelper.rest.util.Constans.REST_PART;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kunze.studhelper.rest.models.backend.Lecture;
import de.kunze.studhelper.rest.models.dao.BaseDao;
import de.kunze.studhelper.rest.models.dao.LectureUserDao;
import de.kunze.studhelper.rest.models.user.User;
import de.kunze.studhelper.rest.ressource.backend.LectureRessource;
import de.kunze.studhelper.rest.transfer.backend.LectureTransfer;

public class LectureImpl implements LectureRessource {

	private static Logger logger = LoggerFactory.getLogger(LectureImpl.class);
	
	public List<LectureTransfer> getAllLecture() {
		List<LectureTransfer> result = new ArrayList<LectureTransfer>();

		BaseDao<Lecture> dao = new BaseDao<Lecture>(Lecture.class);
		List<Lecture> lectures = dao.getAll();

		for (Lecture lecture : lectures) {
			result.add(lecture.transform());
		}

		dao.close();
		return result;
	}

	public LectureTransfer getLecture(Long id) {
		BaseDao<Lecture> dao = new BaseDao<Lecture>(Lecture.class);

		Lecture result = dao.get(id);

		if (result != null) {
			dao.close();
			return result.transform();
		}

		dao.close();
		return new LectureTransfer();
	}

	public Response createLecture(Long id, LectureTransfer lecture) {
		Lecture lec = lecture.transform();

		BaseDao<Lecture> dao = new BaseDao<Lecture>(Lecture.class);
		dao.save(lec);

		URI location;
		try {
			location = new URI(BASE_URL + REST_PART + "lecture/" + lec.getId());
			dao.close();
			return Response.created(location).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		dao.close();
		return Response.serverError().build();
	}

	public Response updateLecture(LectureTransfer lecture) {
		try {
			BaseDao<Lecture> dao = new BaseDao<Lecture>(Lecture.class);

			Lecture lec = lecture.transform();
			lec.setModules(dao.get(lec.getId()).getModules());

			if (dao.update(lec)) {
				dao.close();
				return Response.status(Status.NO_CONTENT).build();
			} else {
				dao.close();
				return Response.serverError().build();
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		
		return Response.serverError().build();
	}

	public Response deleteLecture(Long id) {
		BaseDao<Lecture> dao = new BaseDao<Lecture>(Lecture.class);
		Lecture lecture = dao.get(id);

		if (lecture == null) {
			dao.close();
			return Response.status(Status.NOT_FOUND).build();
		}

		if (dao.delete(lecture)) {
			dao.close();
			return Response.status(Status.NO_CONTENT).build();
		} else {
			dao.close();
			return Response.serverError().build();
		}
	}

	public Response addUserToLecture(UriInfo info, Long lectureId, Long userId) {
		LectureUserDao daoLec = new LectureUserDao();
		BaseDao<User> daoUser = new BaseDao<User>(User.class);
		
		Lecture lecture = daoLec.get(lectureId);
		User user = daoUser.get(userId);
		
		String markStr = info.getQueryParameters().getFirst("mark");
		
		float mark = 0;
		if(markStr != null && markStr.length() != 0) {
			mark = Float.parseFloat(markStr);
		}
	    
	    if(daoLec.save(lecture, user, mark)) {
	    	daoLec.close();
	    	daoUser.close();
			return Response.status(Status.NO_CONTENT).build();
	    } else {
	    	daoLec.close();
	    	daoUser.close();
	    	return Response.serverError().build();
	    }
	}

}
