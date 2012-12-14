package de.kunze.studhelper.view.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.jackson.type.TypeReference;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.Base64;

import de.kunze.studhelper.rest.transfer.user.NewUserTransfer;
import de.kunze.studhelper.rest.transfer.user.UserTransfer;

public class RestUser extends RestUtil {

	private static final long serialVersionUID = 1L;

	public RestUser() {
		super();
	}

	public boolean login(String username, String password) {
		try {
			byte[] arr = DigestUtils.md5(password);
			password = new String(arr, "UTF-8");

			String userPass = username + ":" + password;

			byte[] authArr = Base64.encode(userPass);
			String auth = new String(authArr);

			ClientResponse cr = this.webResource.path(USER).path(AUTH).header(HttpHeaders.AUTHORIZATION, auth)
					.accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, null);

			int status = cr.getStatus();

			/** TODO: make exceptions */
			if (status == Status.BAD_REQUEST.getStatusCode()) {
				/** wrong input */
				return false;
			} else if (status == Status.UNAUTHORIZED.getStatusCode()) {
				/** Wrong Password */
				return false;
			} else if (status == Status.NOT_FOUND.getStatusCode()) {
				/** No username found */
				return false;
			} else if (is2xx(status)) {
				/** auth */
				return true;
			} else {
				/** other failure */
				return false;
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("", e);
		}
		
		return false;
	}

	public boolean createUser(NewUserTransfer newUser, String id) {
		logger.debug("Request - Create New User");
		ClientResponse cr = this.webResource.path(USER).path(id).type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, newUser);

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
		ClientResponse cr = this.webResource.path(USER).type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, u);

		return is2xx(cr.getStatus());
	}

	public boolean deleteUser(String id) {
		ClientResponse cr = webResource.path(USER).path(id).type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).delete(ClientResponse.class);

		return is2xx(cr.getStatus());
	}

}
