package de.kunze.studhelper.rest.impl.backend;

import static de.kunze.studhelper.rest.util.Constans.BASE_URL;
import static de.kunze.studhelper.rest.util.Constans.REST_PART;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.kunze.studhelper.rest.models.backend.Lecture;
import de.kunze.studhelper.rest.models.backend.Modul;
import de.kunze.studhelper.rest.models.dao.BaseDao;
import de.kunze.studhelper.rest.models.dao.ModulDao;
import de.kunze.studhelper.rest.ressource.backend.ModulRessource;
import de.kunze.studhelper.rest.transfer.backend.LectureTransfer;
import de.kunze.studhelper.rest.transfer.backend.ModulTransfer;

public class ModulImpl implements ModulRessource {

	public List<ModulTransfer> getAllModules() {
		List<ModulTransfer> result = new ArrayList<ModulTransfer>();

		BaseDao<Modul> dao = new BaseDao<Modul>(Modul.class);
		List<Modul> moduls = dao.getAll();

		for (Modul modul : moduls) {
			result.add(modul.transform());
		}

		dao.close();
		return result;
	}

	public ModulTransfer getModul(Long id) {
		BaseDao<Modul> dao = new BaseDao<Modul>(Modul.class);

		Modul result = dao.get(id);

		if (result != null) {
			dao.close();
			return result.transform();
		}

		dao.close();
		return new ModulTransfer();
	}

	public Response createModul(Long id, ModulTransfer modul) {
		Modul mod = modul.transform();

		BaseDao<Modul> dao = new BaseDao<Modul>(Modul.class);
		dao.save(mod);

		URI location;
		try {
			location = new URI(BASE_URL + REST_PART + "modul/" + mod.getId());
			dao.close();
			return Response.created(location).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		dao.close();
		return Response.serverError().build();
	}

	public Response updateModul(ModulTransfer modul) {
		BaseDao<Modul> dao = new BaseDao<Modul>(Modul.class);
		
		Modul mod = modul.transform();
		mod.setPart(dao.get(mod.getId()).getPart());
		
		if (dao.update(mod)) {
			dao.close();
			return Response.status(Status.NO_CONTENT).build();
		} else {
			dao.close();
			return Response.serverError().build();
		}
	}

	public Response deleteModul(Long id) {
		BaseDao<Modul> dao = new BaseDao<Modul>(Modul.class);
		Modul modul = dao.get(id);

		if (modul == null) {
			dao.close();
			return Response.status(Status.NOT_FOUND).build();
		}

		if (dao.delete(modul)) {
			dao.close();
			return Response.status(Status.NO_CONTENT).build();
		} else {
			dao.close();
			return Response.serverError().build();
		}
	}

	public List<LectureTransfer> getAllLectureForModul(Long id) {
		List<LectureTransfer> result = new ArrayList<LectureTransfer>();
		
		BaseDao<Modul> dao = new BaseDao<Modul>(Modul.class);
		Modul modul = dao.get(id);

		if (modul != null) {
			List<Lecture> lectures = modul.getLectures();
			
			if(lectures != null) {
				for(Lecture lecture : lectures) {
					result.add(lecture.transform());
				}
			}
		}

		dao.close();
		return result;
	}

	public LectureTransfer getLectureForModul(Long modulId, Long lectureId) {
		BaseDao<Lecture> dao = new BaseDao<Lecture>(Lecture.class);
		
		Lecture lecture = dao.get(lectureId);

		if (lecture != null) {
			dao.close();
			return lecture.transform();
		}
		
		dao.close();
		return new LectureTransfer();	
	}

	public Response createLectureForModul(Long id, LectureTransfer lecture) {
		Lecture lec = lecture.transform();

		ModulDao daoMod = new ModulDao();
		Modul modul = daoMod.get(id);
	
		daoMod.saveLectureForModul(lec, modul);
		
		URI location;
		try {
			location = new URI(BASE_URL + REST_PART + "modul/" + id + "/lecture/"
					+ lec.getId());
			
			
			daoMod.close();
			return Response.created(location).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		daoMod.close();
		return Response.serverError().build();
	}

}
