package de.kunze.studhelper.rest.impl.backend;

import static de.kunze.studhelper.rest.util.Constans.BASE_URL;
import static de.kunze.studhelper.rest.util.Constans.REST_PART;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.kunze.studhelper.rest.models.backend.Department;
import de.kunze.studhelper.rest.models.backend.University;
import de.kunze.studhelper.rest.models.dao.BaseDao;
import de.kunze.studhelper.rest.models.dao.UniversityDao;
import de.kunze.studhelper.rest.ressource.backend.UniversityRessource;
import de.kunze.studhelper.rest.transfer.backend.DepartmentTransfer;
import de.kunze.studhelper.rest.transfer.backend.UniversityTransfer;

public class UniversityImpl implements UniversityRessource {

	public List<UniversityTransfer> getAllUniversities() {
		List<UniversityTransfer> result = new ArrayList<UniversityTransfer>();

		BaseDao<University> dao = new BaseDao<University>(University.class);
		List<University> universities = dao.getAll();

		for (University university : universities) {
			result.add(university.transform());
		}

		dao.close();
		return result;
	}

	public UniversityTransfer getUniversity(Long id) {
		BaseDao<University> dao = new BaseDao<University>(University.class);
		
		University result = dao.get(id);

		if (result != null) {
			dao.close();
			return result.transform();
		}

		dao.close();
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
			dao.close();
			return Response.created(location).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		dao.close();
		return Response.serverError().build();
	}

	public Response updateUniversity(UniversityTransfer university) {
		BaseDao<University> dao = new BaseDao<University>(University.class);
		if (dao.update(university.transform())) {
			dao.close();
			return Response.status(Status.NO_CONTENT).build();
		} else {
			dao.close();
			return Response.serverError().build();
		}
	}

	public Response deleteUniversity(Long id) {
		BaseDao<University> dao = new BaseDao<University>(University.class);
		University university = dao.get(id);

		if (university == null) {
			dao.close();
			return Response.status(Status.NOT_FOUND).build();
		}

		if (dao.delete(university)) {
			dao.close();
			return Response.status(Status.NO_CONTENT).build();
		} else {
			dao.close();
			return Response.serverError().build();
		}
	}

	public List<DepartmentTransfer> getAllDepartmentsForUniversity(Long id) {
		List<DepartmentTransfer> result = new ArrayList<DepartmentTransfer>();
		
		BaseDao<University> dao = new BaseDao<University>(University.class);
		University university = dao.get(id);

		if (university != null) {
			List<Department> departments = university.getDepartments();
			
			if(departments != null) {
				for(Department department : departments) {
					result.add(department.transform());
				}
			}
		}

		dao.close();
		
		return result;
	}

	public Response createDepartmentForUniversity(Long id, DepartmentTransfer department) {
		Department dep = department.transform();

		UniversityDao daoUni = new UniversityDao();

		University uni = daoUni.get(id);
		dep.setUniversity(uni);
		
		daoUni.saveDepartment(uni, dep);

		URI location;
		try {
			location = new URI(BASE_URL + REST_PART + "university/" + id + "/department/"
					+ dep.getId());
			
			daoUni.close();
			return Response.created(location).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		daoUni.close();
		return Response.serverError().build();
	}

	public DepartmentTransfer getDepartmentForUniversity(Long universityId,Long departmentId) {
		BaseDao<Department> dao = new BaseDao<Department>(Department.class);
		
		Department department = dao.get(departmentId);

		if (department != null) {
			return department.transform();
		}
		
		dao.close();
		return new DepartmentTransfer();
	}

}
