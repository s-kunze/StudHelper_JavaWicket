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

import de.kunze.studhelper.rest.transfer.backend.DepartmentTransfer;
import de.kunze.studhelper.rest.transfer.backend.UniversityTransfer;

/**
 * 
 * @author Stefan Kunze
 * 
 */
@Path("/university")
public interface UniversityRessource {

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<UniversityTransfer> getAllUniversities();

	@GET
	@Path("{university_id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public UniversityTransfer getUniversity(@PathParam("university_id") Long id);

	@PUT
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createUniversity(UniversityTransfer university);

	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response updateUniversity(UniversityTransfer university);

	@DELETE
	@Path("{university_id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deleteUniversity(@PathParam("university_id") Long id);

	@GET
	@Path("{university_id}/department")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<DepartmentTransfer> getAllDepartmentsForUniversity(
			@PathParam("university_id") Long id);

	@GET
	@Path("{university_id}/department/{department_id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public DepartmentTransfer getDepartmentForUniversity(
			@PathParam("university_id") Long universityId,
			@PathParam("department_id") Long departmentId);

	@PUT
	@Path("{university_id}/department")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createDepartmentForUniversity(
			@PathParam("university_id") Long id, DepartmentTransfer department);
	
}
