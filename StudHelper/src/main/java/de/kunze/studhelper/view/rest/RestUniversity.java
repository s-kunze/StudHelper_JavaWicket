package de.kunze.studhelper.view.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.type.TypeReference;

import com.sun.jersey.api.client.ClientResponse;

import de.kunze.studhelper.rest.transfer.backend.UniversityTransfer;

public class RestUniversity extends RestUtil {

	public final static String UNIVERSITY = "university";

	public RestUniversity() {
		super();
	}

	public boolean createUniversity(UniversityTransfer u) {
		ClientResponse cr = this.webResource.path(UNIVERSITY)
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, u);

		return is2xx(cr.getStatus());
	}

	public UniversityTransfer getUniversity(String id) {
		return this.webResource.path(UNIVERSITY).path(id)
				.accept(MediaType.APPLICATION_JSON)
				.get(UniversityTransfer.class);
	}

	public List<UniversityTransfer> getUniversities() {
		try {
			String json = this.webResource.path(UNIVERSITY)
					.accept(MediaType.APPLICATION_JSON).get(String.class);
			return mapper.readValue(json,
					new TypeReference<List<UniversityTransfer>>() {
					});
		} catch (IOException e) {
			logger.error("", e);
		}

		return new ArrayList<UniversityTransfer>();
	}
	
	public boolean updateUniversity(UniversityTransfer u) {
		ClientResponse cr = this.webResource.path(UNIVERSITY)
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, u);

		return is2xx(cr.getStatus());
	}
	
	public boolean deleteUniversity(String id) {
    	ClientResponse cr = webResource.path(UNIVERSITY)
                .path(id)
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .delete(ClientResponse.class);
    	
    	return is2xx(cr.getStatus());
    }

}
