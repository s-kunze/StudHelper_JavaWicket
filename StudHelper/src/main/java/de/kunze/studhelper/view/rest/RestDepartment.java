package de.kunze.studhelper.view.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.type.TypeReference;

import com.sun.jersey.api.client.ClientResponse;

import de.kunze.studhelper.rest.transfer.backend.DepartmentTransfer;

public class RestDepartment extends RestUtil {

	public RestDepartment() {
		super();
	}

	public boolean createDepartment(String id, DepartmentTransfer d) {	
		ClientResponse cr = this.webResource.path(UNIVERSITY).path(id).path(DEPARTMENT)
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, d);

		return is2xx(cr.getStatus());
	}

	public DepartmentTransfer getDepartment(String id) {
		return this.webResource.path(DEPARTMENT).path(id)
				.accept(MediaType.APPLICATION_JSON)
				.get(DepartmentTransfer.class);
	}

	public List<DepartmentTransfer> getDepartments() {
		try {
			String json = this.webResource.path(DEPARTMENT)
					.accept(MediaType.APPLICATION_JSON).get(String.class);
			return mapper.readValue(json,
					new TypeReference<List<DepartmentTransfer>>() {
					});
		} catch (IOException e) {
			logger.error("", e);
		}

		return new ArrayList<DepartmentTransfer>();
	}
	
	public boolean updateDepartment(DepartmentTransfer d) {
		ClientResponse cr = this.webResource.path(DEPARTMENT)
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, d);

		return is2xx(cr.getStatus());
	}
	
	public boolean deleteDepartment(String id) {
    	ClientResponse cr = webResource.path(DEPARTMENT)
                .path(id)
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .delete(ClientResponse.class);
    	
    	return is2xx(cr.getStatus());
    }
	
}
