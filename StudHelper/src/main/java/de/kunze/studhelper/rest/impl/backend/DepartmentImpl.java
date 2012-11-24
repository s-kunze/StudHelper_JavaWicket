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
import de.kunze.studhelper.rest.models.backend.Department;
import de.kunze.studhelper.rest.models.dao.BaseDao;
import de.kunze.studhelper.rest.ressource.backend.DepartmentRessource;
import de.kunze.studhelper.rest.transfer.backend.DegreeCourseTransfer;
import de.kunze.studhelper.rest.transfer.backend.DepartmentTransfer;

public class DepartmentImpl implements DepartmentRessource {

	public List<DepartmentTransfer> getAllDepartments() {
		List<DepartmentTransfer> result = new ArrayList<DepartmentTransfer>();

		BaseDao<Department> dao = new BaseDao<Department>(Department.class);
		List<Department> departments = dao.getAll();

		for (Department department : departments) {
			result.add(department.transform());
		}

		dao.close();
		return result;
	}

	public DepartmentTransfer getDepartment(Long id) {
		BaseDao<Department> dao = new BaseDao<Department>(Department.class);
		
		Department result = dao.get(id);

		if (result != null) {
			dao.close();
			return result.transform();
		}

		dao.close();
		return new DepartmentTransfer();
	}


	public Response createDepartment(Long id,DepartmentTransfer department) {
		Department dep = department.transform();

		BaseDao<Department> dao = new BaseDao<Department>(Department.class);
		dao.save(dep);

		URI location;
		try {
			location = new URI(BASE_URL + REST_PART + "department/"
					+ dep.getId());
			
			dao.close();
			return Response.created(location).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		dao.close();
		return Response.serverError().build();
	}

	public Response updateDepartment(DepartmentTransfer department) {
		BaseDao<Department> dao = new BaseDao<Department>(Department.class);
		if (dao.update(department.transform())) {
			dao.close();
			return Response.status(Status.NO_CONTENT).build();
		} else {
			dao.close();
			return Response.serverError().build();
		}
	}

	public Response deleteDepartment(Long id) {
		BaseDao<Department> dao = new BaseDao<Department>(Department.class);
		Department department = dao.get(id);

		if (department == null) {
			dao.close();
			return Response.status(Status.NOT_FOUND).build();
		}

		if (dao.delete(department)) {
			dao.close();
			return Response.status(Status.NO_CONTENT).build();
		} else {
			dao.close();
			return Response.serverError().build();
		}
	}

	public List<DegreeCourseTransfer> getAllDegreeCoursesForDepartment(Long id) {
		List<DegreeCourseTransfer> result = new ArrayList<DegreeCourseTransfer>();
		
		BaseDao<Department> dao = new BaseDao<Department>(Department.class);
		Department department = dao.get(id);

		if (department != null) {
			List<DegreeCourse> degreeCourses = department.getDegreecourses();
			
			if(degreeCourses != null) {
				for(DegreeCourse degreeCourse : degreeCourses) {
					result.add(degreeCourse.transform());
				}
			}
		}

		dao.close();
		return result;
	}

	public DegreeCourseTransfer getDegreeCourseForDepartment(Long departmentId, Long degreeCourseId) {
		BaseDao<DegreeCourse> dao = new BaseDao<DegreeCourse>(DegreeCourse.class);
		
		DegreeCourse degreeCourse = dao.get(degreeCourseId);

		if (degreeCourse != null) {
			dao.close();
			return degreeCourse.transform();
		}
		
		dao.close();
		return new DegreeCourseTransfer();
	}

	public Response createDegreeCourseForDepartment(Long id, DegreeCourseTransfer degreeCourse) {
		DegreeCourse deg = degreeCourse.transform();

		BaseDao<Department> daoDep = new BaseDao<Department>(Department.class);
		Department department = daoDep.get(id);
		deg.setDepartment(department);

		BaseDao<DegreeCourse> daoDeg = new BaseDao<DegreeCourse>(DegreeCourse.class);
		daoDeg.save(deg);

		URI location;
		try {
			location = new URI(BASE_URL + REST_PART + "department/" + id + "/degreecourse/"
					+ deg.getId());
			
			daoDep.close();
			daoDeg.close();
			return Response.created(location).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		daoDep.close();
		daoDeg.close();
		return Response.serverError().build();
	}

}
