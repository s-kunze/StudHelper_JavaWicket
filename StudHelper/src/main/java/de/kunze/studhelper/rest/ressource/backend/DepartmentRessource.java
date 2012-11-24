package de.kunze.studhelper.rest.ressource.backend;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.kunze.studhelper.rest.transfer.backend.DegreeCourseTransfer;
import de.kunze.studhelper.rest.transfer.backend.DepartmentTransfer;

/**
 * 
 * @author stefan
 * 
 */
@Path("/department")
public interface DepartmentRessource {

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<DepartmentTransfer> getAllDepartments();
	
	@GET
	@Path("{department_id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public DepartmentTransfer getDepartment(@PathParam("department_id") Long id);

	@POST
	@Path("{university_id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createDepartment(@PathParam("university_id") Long id,
			DepartmentTransfer department);

	@PUT
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response updateDepartment(DepartmentTransfer department);

	@DELETE
	@Path("{department_id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deleteDepartment(@PathParam("department_id") Long id);
	
	@GET
	@Path("{department_id}/degreecourse")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<DegreeCourseTransfer> getAllDegreeCoursesForDepartment(
			@PathParam("department_id") Long id);

	@GET
	@Path("{department_id}/degreecourse/{degreecourse_id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public DegreeCourseTransfer getDegreeCourseForDepartment(
			@PathParam("department_id") Long departmentId,
			@PathParam("degreecourse_id") Long degreeCourseId);

	@POST
	@Path("{department_id}/degreecourse")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createDegreeCourseForDepartment(
			@PathParam("department_id") Long id, DegreeCourseTransfer degreeCourse);

}
