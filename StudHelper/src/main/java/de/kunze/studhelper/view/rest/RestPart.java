package de.kunze.studhelper.view.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.type.TypeReference;

import com.sun.jersey.api.client.ClientResponse;

import de.kunze.studhelper.rest.transfer.backend.ModulTransfer;
import de.kunze.studhelper.rest.transfer.backend.PartTransfer;

public class RestPart extends RestUtil {

	private static final long serialVersionUID = 1L;

	public RestPart() {
		super();
	}

	public boolean createPart(String id, PartTransfer d) {
		ClientResponse cr = this.webResource.path(DEGREECOURSE).path(id).path(PART).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, d);

		return is2xx(cr.getStatus());
	}

	public PartTransfer getPart(String id) {
		return this.webResource.path(PART).path(id).accept(MediaType.APPLICATION_JSON).get(PartTransfer.class);
	}

	public List<PartTransfer> getParts() {
		try {
			String json = this.webResource.path(PART).accept(MediaType.APPLICATION_JSON).get(String.class);
			return mapper.readValue(json, new TypeReference<List<PartTransfer>>() {
			});
		} catch (IOException e) {
			logger.error("", e);
		}

		return new ArrayList<PartTransfer>();
	}

	public List<ModulTransfer> getModulForPart(String id) {
		try {
			String json = this.webResource.path(PART).path(id).path(MODUL).accept(MediaType.APPLICATION_JSON).get(String.class);
			return mapper.readValue(json, new TypeReference<List<ModulTransfer>>() {
			});
		} catch (IOException e) {
			logger.error("", e);
		}

		return new ArrayList<ModulTransfer>();
	}

	public boolean updatePart(PartTransfer d) {
		ClientResponse cr = this.webResource.path(PART).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.put(ClientResponse.class, d);

		return is2xx(cr.getStatus());
	}

	public boolean deletePart(String id) {
		ClientResponse cr = webResource.path(PART).path(id).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.delete(ClientResponse.class);

		return is2xx(cr.getStatus());
	}
	
}
