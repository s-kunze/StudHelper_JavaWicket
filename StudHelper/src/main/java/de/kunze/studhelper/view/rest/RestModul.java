package de.kunze.studhelper.view.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.type.TypeReference;

import com.sun.jersey.api.client.ClientResponse;

import de.kunze.studhelper.rest.transfer.backend.LectureTransfer;
import de.kunze.studhelper.rest.transfer.backend.ModulTransfer;

public class RestModul extends RestUtil{

	public RestModul() {
		super();
	}

	public boolean createModul(String id, ModulTransfer m) {
		ClientResponse cr = this.webResource.path(PART).path(id).path(MODUL).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, m);

		return is2xx(cr.getStatus());
	}

	public ModulTransfer getModul(String id) {
		return this.webResource.path(MODUL).path(id).accept(MediaType.APPLICATION_JSON).get(ModulTransfer.class);
	}

	public List<ModulTransfer> getModuls() {
		try {
			String json = this.webResource.path(MODUL).accept(MediaType.APPLICATION_JSON).get(String.class);
			return mapper.readValue(json, new TypeReference<List<ModulTransfer>>() {
			});
		} catch (IOException e) {
			logger.error("", e);
		}

		return new ArrayList<ModulTransfer>();
	}

	public List<LectureTransfer> getLecturesForModul(String id) {
		try {
			String json = this.webResource.path(MODUL).path(id).path(LECTURE).accept(MediaType.APPLICATION_JSON).get(String.class);
			return mapper.readValue(json, new TypeReference<List<LectureTransfer>>() {
			});
		} catch (IOException e) {
			logger.error("", e);
		}

		return new ArrayList<LectureTransfer>();
	}

	public boolean updateModul(ModulTransfer d) {
		ClientResponse cr = this.webResource.path(MODUL).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.put(ClientResponse.class, d);

		return is2xx(cr.getStatus());
	}

	public boolean deleteModul(String id) {
		ClientResponse cr = webResource.path(MODUL).path(id).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.delete(ClientResponse.class);

		return is2xx(cr.getStatus());
	}
	
}
