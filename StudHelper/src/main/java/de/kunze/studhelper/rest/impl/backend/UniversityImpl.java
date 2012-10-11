package de.kunze.studhelper.rest.impl.backend;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.kunze.studhelper.rest.models.backend.University;
import de.kunze.studhelper.rest.models.dao.BaseDao;
import de.kunze.studhelper.rest.ressource.backend.UniversityRessource;
import de.kunze.studhelper.rest.transfer.backend.UniversityTransfer;
import static de.kunze.studhelper.rest.util.Constans.*;

public class UniversityImpl implements UniversityRessource {

	public List<UniversityTransfer> getAllUniversities() {
		List<UniversityTransfer> result = new ArrayList<UniversityTransfer>();

		BaseDao<University> dao = new BaseDao<University>(University.class);
		List<University> universities = dao.getAll();

		for (University university : universities) {
			result.add(university.transform());
		}

		return result;
	}

	public UniversityTransfer getUniversity(Long id) {
		BaseDao<University> dao = new BaseDao<University>(University.class);
		
		University result = dao.get(id);

		if (result != null) {
			return result.transform();
		}

		return new UniversityTransfer();
	}

	public Response createUniversity(UniversityTransfer university) {
		University uni = university.transform();

		BaseDao<University> dao = new BaseDao<University>(University.class);
		dao.save(uni);

		URI location;
		try {
			location = new URI(BASE_URL + REST_PART + "university/"
					+ uni.getId());
			return Response.created(location).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return Response.serverError().build();
	}

	public Response updateUniversity(UniversityTransfer university) {
		
		BaseDao<University> dao = new BaseDao<University>(University.class);
		if (dao.update(university.transform())) {
			return Response.status(Status.NO_CONTENT).build();
		} else {
			return Response.serverError().build();
		}
	}

	public Response deleteUniversity(Long id) {
		BaseDao<University> dao = new BaseDao<University>(University.class);
		University university = dao.get(id);

		if (university == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		if (dao.delete(university)) {
			return Response.status(Status.NO_CONTENT).build();
		} else {
			return Response.serverError().build();
		}
	}

}
