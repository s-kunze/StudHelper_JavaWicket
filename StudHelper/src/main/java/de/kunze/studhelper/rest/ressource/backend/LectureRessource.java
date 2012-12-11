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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.kunze.studhelper.rest.transfer.backend.LectureTransfer;

/**
 * 
 * @author Stefan Kunze
 * 
 */
@Path("/lecture")
public interface LectureRessource {

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<LectureTransfer> getAllLecture();

	@GET
	@Path("{lecture_id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public LectureTransfer getLecture(@PathParam("lecture_id") Long id);

	@PUT
	@Path("{modul_id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createLecture(@PathParam("modul_id") Long id, LectureTransfer lecture);

	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response updateLecture(LectureTransfer lecture);

	@DELETE
	@Path("{lecture_id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deleteLecture(@PathParam("lecture_id") Long id);

	@PUT
	@Path("{lecture_id}/adduser/{user_id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response addUserToLecture(@Context UriInfo info, @PathParam("lecture_id") Long lectureId, @PathParam("user_id") Long userId);
}
