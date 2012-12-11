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

import de.kunze.studhelper.rest.transfer.backend.ModulTransfer;
import de.kunze.studhelper.rest.transfer.backend.PartTransfer;

/**
 * 
 * @author Stefan Kunze
 * 
 */
@Path("/part")
public interface PartRessource {

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<PartTransfer> getAllParts();

	@GET
	@Path("{part_id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public PartTransfer getPart(@PathParam("part_id") Long id);

	@PUT
	@Path("{degreecourse_id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createPart(@PathParam("degreecourse_id") Long id, PartTransfer part);

	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response updatePart(PartTransfer part);

	@DELETE
	@Path("{part_id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deletePart(@PathParam("part_id") Long id);

	@GET
	@Path("{part_id}/modul")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<ModulTransfer> getAllModulesForPart(@PathParam("part_id") Long id);

	@GET
	@Path("{part_id}/modul/{modul_id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ModulTransfer getModulForPart(@PathParam("part_id") Long partId, @PathParam("modul_id") Long modulId);

	@PUT
	@Path("{part_id}/modul")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createModulForPart(@PathParam("part_id") Long id, ModulTransfer modul);

}
