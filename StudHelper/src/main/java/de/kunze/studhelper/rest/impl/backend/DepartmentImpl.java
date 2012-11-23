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
import de.kunze.studhelper.rest.models.dao.BaseDao;
import de.kunze.studhelper.rest.ressource.backend.DepartmentRessource;
import de.kunze.studhelper.rest.transfer.backend.DepartmentTransfer;

public class DepartmentImpl implements DepartmentRessource {

	public List<DepartmentTransfer> getAllDepartments() {
		List<DepartmentTransfer> result = new ArrayList<DepartmentTransfer>();

		BaseDao<Department> dao = new BaseDao<Department>(Department.class);
		List<Department> departments = dao.getAll();

		for (Department department : departments) {
			result.add(department.transform());
		}

		return result;
	}

	public DepartmentTransfer getDepartment(Long id) {
		BaseDao<Department> dao = new BaseDao<Department>(Department.class);
		
		Department result = dao.get(id);

		if (result != null) {
			return result.transform();
		}

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
			return Response.created(location).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return Response.serverError().build();
	}

	public Response updateDepartment(DepartmentTransfer department) {
		BaseDao<Department> dao = new BaseDao<Department>(Department.class);
		if (dao.update(department.transform())) {
			return Response.status(Status.NO_CONTENT).build();
		} else {
			return Response.serverError().build();
		}
	}

	public Response deleteDepartment(Long id) {
		BaseDao<Department> dao = new BaseDao<Department>(Department.class);
		Department department = dao.get(id);

		if (department == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		if (dao.delete(department)) {
			return Response.status(Status.NO_CONTENT).build();
		} else {
			return Response.serverError().build();
		}
	}

}
