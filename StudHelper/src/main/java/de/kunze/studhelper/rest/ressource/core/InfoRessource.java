package de.kunze.studhelper.rest.ressource.core;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.kunze.studhelper.rest.transfer.ServerInfo;

@Path("/info")
public interface InfoRessource {
 
	@GET
	@Path("/server")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ServerInfo getServerInfo();
 
}
