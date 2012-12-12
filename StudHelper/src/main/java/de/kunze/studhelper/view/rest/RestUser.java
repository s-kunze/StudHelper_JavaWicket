package de.kunze.studhelper.view.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.type.TypeReference;

import com.sun.jersey.api.client.ClientResponse;

import de.kunze.studhelper.rest.transfer.user.NewUserTransfer;
import de.kunze.studhelper.rest.transfer.user.UserTransfer;

public class RestUser extends RestUtil {

	private static final long serialVersionUID = 1L;

	public RestUser() {
		super();
	}

	public boolean createUser(NewUserTransfer newUser, String id) {
		logger.debug("Request - Create New User");
		ClientResponse cr = this.webResource.path(USER).path(id).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.put(ClientResponse.class, newUser);

		return is2xx(cr.getStatus());
	}

	public UserTransfer getUser(String id) {
		return this.webResource.path(USER).path(id).accept(MediaType.APPLICATION_JSON).get(UserTransfer.class);
	}

	public List<UserTransfer> getUsers() {
		try {
			String json = this.webResource.path(USER).accept(MediaType.APPLICATION_JSON).get(String.class);
			return mapper.readValue(json, new TypeReference<List<UserTransfer>>() {
			});
		} catch (IOException e) {
			logger.error("", e);
		}

		return new ArrayList<UserTransfer>();
	}

	public boolean updateUser(UserTransfer u) {
		ClientResponse cr = this.webResource.path(USER).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, u);

		return is2xx(cr.getStatus());
	}

	public boolean deleteUser(String id) {
		ClientResponse cr = webResource.path(USER).path(id).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.delete(ClientResponse.class);

		return is2xx(cr.getStatus());
	}

}
