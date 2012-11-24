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

/**
 * 
 * @author stefan
 * 
 */
@Path("/degreecourse")
public interface DegreeCourseRessource {

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<DegreeCourseTransfer> getAllDegreeCourse();
	
	@GET
	@Path("{degreecourse_id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public DegreeCourseTransfer getDegreeCourse(@PathParam("degreecourse_id") Long id);

	@POST
	@Path("{degreecourse_id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createDegreeCourse(@PathParam("degreecourse_id") Long id,
			DegreeCourseTransfer degreecourse);

	@PUT
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response updateDegreeCourse(DegreeCourseTransfer degreecourse);

	@DELETE
	@Path("{degreecourse_id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deleteDegreeCourse(@PathParam("degreecourse_id") Long id);
	
}
