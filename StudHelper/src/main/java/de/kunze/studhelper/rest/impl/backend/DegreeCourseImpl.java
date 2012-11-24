package de.kunze.studhelper.rest.impl.backend;

import static de.kunze.studhelper.rest.util.Constans.BASE_URL;
import static de.kunze.studhelper.rest.util.Constans.REST_PART;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.kunze.studhelper.rest.models.backend.DegreeCourse;
import de.kunze.studhelper.rest.models.backend.Part;
import de.kunze.studhelper.rest.models.dao.BaseDao;
import de.kunze.studhelper.rest.ressource.backend.DegreeCourseRessource;
import de.kunze.studhelper.rest.transfer.backend.DegreeCourseTransfer;
import de.kunze.studhelper.rest.transfer.backend.PartTransfer;

public class DegreeCourseImpl implements DegreeCourseRessource {

	public List<DegreeCourseTransfer> getAllDegreeCourse() {
		List<DegreeCourseTransfer> result = new ArrayList<DegreeCourseTransfer>();

		BaseDao<DegreeCourse> dao = new BaseDao<DegreeCourse>(
				DegreeCourse.class);
		List<DegreeCourse> degreeCourses = dao.getAll();

		for (DegreeCourse degreeCourse : degreeCourses) {
			result.add(degreeCourse.transform());
		}

		dao.close();
		return result;
	}

	public DegreeCourseTransfer getDegreeCourse(Long id) {
		BaseDao<DegreeCourse> dao = new BaseDao<DegreeCourse>(
				DegreeCourse.class);

		DegreeCourse result = dao.get(id);

		if (result != null) {
			dao.close();
			return result.transform();
		}

		dao.close();
		return new DegreeCourseTransfer();
	}

	public Response createDegreeCourse(Long id,
			DegreeCourseTransfer degreecourse) {
		DegreeCourse deg = degreecourse.transform();

		BaseDao<DegreeCourse> dao = new BaseDao<DegreeCourse>(
				DegreeCourse.class);
		dao.save(deg);

		URI location;
		try {
			location = new URI(BASE_URL + REST_PART + "degreecourse/"
					+ deg.getId());
			dao.close();
			return Response.created(location).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		dao.close();
		return Response.serverError().build();
	}

	public Response updateDegreeCourse(DegreeCourseTransfer degreecourse) {
		BaseDao<DegreeCourse> dao = new BaseDao<DegreeCourse>(
				DegreeCourse.class);
		if (dao.update(degreecourse.transform())) {
			dao.close();
			return Response.status(Status.NO_CONTENT).build();
		} else {
			dao.close();
			return Response.serverError().build();
		}
	}

	public Response deleteDegreeCourse(Long id) {
		BaseDao<DegreeCourse> dao = new BaseDao<DegreeCourse>(
				DegreeCourse.class);
		DegreeCourse degreeCourse = dao.get(id);

		if (degreeCourse == null) {
			dao.close();
			return Response.status(Status.NOT_FOUND).build();
		}

		if (dao.delete(degreeCourse)) {
			dao.close();
			return Response.status(Status.NO_CONTENT).build();
		} else {
			dao.close();
			return Response.serverError().build();
		}
	}

	public List<PartTransfer> getAllPartsForDegreeCourse(Long id) {
		List<PartTransfer> result = new ArrayList<PartTransfer>();
		
		BaseDao<DegreeCourse> dao = new BaseDao<DegreeCourse>(DegreeCourse.class);
		DegreeCourse degreeCourse = dao.get(id);

		if (degreeCourse != null) {
			List<Part> parts = degreeCourse.getParts();
			
			if(parts != null) {
				for(Part part : parts) {
					result.add(part.transform());
				}
			}
		}

		dao.close();
		return result;
	}

	public PartTransfer getPartForDegreeCourse(Long degreeCourseId,
			Long partId) {
		BaseDao<Part> dao = new BaseDao<Part>(Part.class);
		
		Part part = dao.get(partId);

		if (part != null) {
			dao.close();
			return part.transform();
		}
		
		dao.close();
		return new PartTransfer();
	}

	public Response createPartForDegreeCourse(Long id, PartTransfer part) {
		Part par = part.transform();

		BaseDao<DegreeCourse> daoDeg = new BaseDao<DegreeCourse>(DegreeCourse.class);
		DegreeCourse degreeCourse = daoDeg.get(id);
		par.setDegreeCourse(degreeCourse);

		BaseDao<Part> daoPar = new BaseDao<Part>(Part.class);
		daoPar.save(par);

		URI location;
		try {
			location = new URI(BASE_URL + REST_PART + "degreecourse/" + id + "/part/"
					+ par.getId());
			
			daoDeg.close();
			daoPar.close();
			return Response.created(location).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		daoDeg.close();
		daoPar.close();
		return Response.serverError().build();
	}

}
