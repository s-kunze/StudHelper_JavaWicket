package de.kunze.studhelper.view.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.type.TypeReference;

import com.sun.jersey.api.client.ClientResponse;

import de.kunze.studhelper.rest.transfer.backend.LectureTransfer;

/**
 * 
 * @author Stefan Kunze
 *
 */
public class RestLecture extends RestUtil {

	private static final long serialVersionUID = 1L;

	public RestLecture() {
		super();
	}

	public boolean createLecture(String id, LectureTransfer l) {
		ClientResponse cr = this.webResource.path(MODUL).path(id).path(LECTURE).type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, l);

		return is2xx(cr.getStatus());
	}

	public LectureTransfer getLecture(String id) {
		return this.webResource.path(LECTURE).path(id).accept(MediaType.APPLICATION_JSON).get(LectureTransfer.class);
	}

	public List<LectureTransfer> getLectures() {
		try {
			String json = this.webResource.path(LECTURE).accept(MediaType.APPLICATION_JSON).get(String.class);
			return mapper.readValue(json, new TypeReference<List<LectureTransfer>>() {
			});
		} catch (IOException e) {
			logger.error("", e);
		}

		return new ArrayList<LectureTransfer>();
	}

	public boolean updateLecture(LectureTransfer d) {
		ClientResponse cr = this.webResource.path(LECTURE).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, d);

		return is2xx(cr.getStatus());
	}

	public boolean deleteLecture(String id) {
		ClientResponse cr = webResource.path(LECTURE).path(id).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.delete(ClientResponse.class);

		return is2xx(cr.getStatus());
	}
	
	public boolean addUserToLecture(String lectureId, String userId, Float mark) {
		ClientResponse cr = this.webResource.path(LECTURE).path(lectureId).path(ADDUSER).path(userId).queryParam("mark", mark.toString()).type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, null);

		return is2xx(cr.getStatus());
	}

}
