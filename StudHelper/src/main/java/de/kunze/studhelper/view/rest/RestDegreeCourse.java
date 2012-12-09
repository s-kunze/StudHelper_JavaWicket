package de.kunze.studhelper.view.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.type.TypeReference;

import com.sun.jersey.api.client.ClientResponse;

import de.kunze.studhelper.rest.transfer.backend.DegreeCourseTransfer;
import de.kunze.studhelper.rest.transfer.backend.PartTransfer;

public class RestDegreeCourse extends RestUtil {

	private static final long serialVersionUID = 1L;

	public RestDegreeCourse() {
		super();
	}

	public boolean createDegreeCourse(String id, DegreeCourseTransfer d) {
		ClientResponse cr = this.webResource.path(DEPARTMENT).path(id).path(DEGREECOURSE).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, d);

		return is2xx(cr.getStatus());
	}

	public DegreeCourseTransfer getDegreeCourse(String id) {
		return this.webResource.path(DEGREECOURSE).path(id).accept(MediaType.APPLICATION_JSON).get(DegreeCourseTransfer.class);
	}

	public List<DegreeCourseTransfer> getDegreeCourses() {
		try {
			String json = this.webResource.path(DEGREECOURSE).accept(MediaType.APPLICATION_JSON).get(String.class);
			return mapper.readValue(json, new TypeReference<List<DegreeCourseTransfer>>() {
			});
		} catch (IOException e) {
			logger.error("", e);
		}

		return new ArrayList<DegreeCourseTransfer>();
	}

	public List<PartTransfer> getPartForDegreeCourse(String id) {
		try {
			String json = this.webResource.path(DEGREECOURSE).path(id).path(PART).accept(MediaType.APPLICATION_JSON).get(String.class);
			return mapper.readValue(json, new TypeReference<List<PartTransfer>>() {
			});
		} catch (IOException e) {
			logger.error("", e);
		}

		return new ArrayList<PartTransfer>();
	}

	public boolean updateDegreeCourse(DegreeCourseTransfer d) {
		ClientResponse cr = this.webResource.path(DEGREECOURSE).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.put(ClientResponse.class, d);

		return is2xx(cr.getStatus());
	}

	public boolean deleteDegreeCourse(String id) {
		ClientResponse cr = webResource.path(DEGREECOURSE).path(id).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.delete(ClientResponse.class);

		return is2xx(cr.getStatus());
	}

}
