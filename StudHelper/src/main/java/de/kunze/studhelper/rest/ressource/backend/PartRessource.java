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

import de.kunze.studhelper.rest.transfer.backend.PartTransfer;

/**
 * 
 * @author stefan
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

	@POST
	@Path("{part_id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createPart(@PathParam("part_id") Long id,
			PartTransfer part);

	@PUT
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response updatePart(PartTransfer part);

	@DELETE
	@Path("{part_id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deletePart(@PathParam("part_id") Long id);
	
}
