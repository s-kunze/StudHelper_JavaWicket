package de.kunze.studhelper.rest.ressource.user;

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
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.kunze.studhelper.rest.transfer.backend.DegreeCourseTransfer;
import de.kunze.studhelper.rest.transfer.backend.LectureMarkTransfer;
import de.kunze.studhelper.rest.transfer.backend.LectureTransfer;
import de.kunze.studhelper.rest.transfer.user.NewUserTransfer;
import de.kunze.studhelper.rest.transfer.user.UserTransfer;

/**
 * 
 * @author Stefan Kunze
 *
 */
@Path("/user")
public interface UserRessource {

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<UserTransfer> getAllUsers();

	@GET
	@Path("{user_id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public UserTransfer getUser(@PathParam("user_id") Long id);

	@PUT
	@Path("{degreecourse_id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createUser(@PathParam("degreecourse_id") Long id, NewUserTransfer newUser);

	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response updateUser(UserTransfer user);

	@DELETE
	@Path("{user_id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deleteUser(@PathParam("user_id") Long id);
	
	@PUT
	@Path("auth")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response authUser(@Context HttpHeaders header);
	
	@GET
	@Path("{user_id}/degreecourse")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public DegreeCourseTransfer getDegreeCourseFromUser(@PathParam("user_id") Long id); 
	
	@GET
	@Path("{user_id}/lecture")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<LectureMarkTransfer> getLecturesFromUser(@PathParam("user_id") Long id);
}
