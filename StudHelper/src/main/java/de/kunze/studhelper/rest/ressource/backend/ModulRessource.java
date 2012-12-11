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
import de.kunze.studhelper.rest.transfer.backend.LectureTransfer;
import de.kunze.studhelper.rest.transfer.backend.ModulTransfer;

/**
 * 
 * @author Stefan Kunze
 * 
 */
@Path("/modul")
public interface ModulRessource {

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<ModulTransfer> getAllModules();

	@GET
	@Path("{modul_id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ModulTransfer getModul(@PathParam("modul_id") Long id);

	@PUT
	@Path("{part_id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createModul(@PathParam("part_id") Long id, ModulTransfer modul);

	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response updateModul(ModulTransfer modul);

	@DELETE
	@Path("{modul_id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deleteModul(@PathParam("modul_id") Long id);

	@GET
	@Path("{modul_id}/lecture")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<LectureTransfer> getAllLectureForModul(@PathParam("modul_id") Long id);

	@GET
	@Path("{modul_id}/lecture/{lecture_id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public LectureTransfer getLectureForModul(@PathParam("modul_id") Long modulId, @PathParam("lecture_id") Long lectureId);

	@PUT
	@Path("{modul_id}/lecture")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createLectureForModul(@PathParam("modul_id") Long id, LectureTransfer lecture);

	@GET
	@Path("{modul_id}/department")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public DepartmentTransfer getDepartmentForModul(@PathParam("modul_id") Long id);
}
