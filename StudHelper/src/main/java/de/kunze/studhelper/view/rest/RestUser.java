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

import de.kunze.studhelper.rest.exception.NoUserFoundException;
import de.kunze.studhelper.rest.exception.WrongPasswordException;
import de.kunze.studhelper.rest.transfer.backend.DegreeCourseTransfer;
import de.kunze.studhelper.rest.transfer.backend.LectureMarkTransfer;
import de.kunze.studhelper.rest.transfer.user.AuthTransfer;
import de.kunze.studhelper.rest.transfer.user.NewUserTransfer;
import de.kunze.studhelper.rest.transfer.user.UserTransfer;

public class RestUser extends RestUtil {

	private static final long serialVersionUID = 1L;

	public RestUser() {
		super();
	}

	public AuthTransfer login(String username, String password) throws WrongPasswordException, NoUserFoundException {
		try {
			byte[] arr = DigestUtils.md5(password);
			password = new String(arr, "UTF-8");

			String userPass = username + ":" + password;

			byte[] authArr = Base64.encode(userPass);
			String auth = new String(authArr);

			ClientResponse cr = this.webResource.path(USER).path(AUTH).header(HttpHeaders.AUTHORIZATION, auth)
					.accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, null);

			int status = cr.getStatus();

			if (status == Status.BAD_REQUEST.getStatusCode()) {
				throw new IllegalArgumentException();
			} else if (status == Status.UNAUTHORIZED.getStatusCode()) {
				throw new WrongPasswordException();
			} else if (status == Status.NOT_FOUND.getStatusCode()) {
				logger.info("MAYBE WE HAVE A ADMIN");
				/** Try again the admin */
				cr = this.webResource.path(ADMIN).header(HttpHeaders.AUTHORIZATION, auth)
						.accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, null);
				
				status = cr.getStatus();
				
				if (status == Status.BAD_REQUEST.getStatusCode()) {
					throw new IllegalArgumentException();
				} else if (status == Status.UNAUTHORIZED.getStatusCode()) {
					throw new WrongPasswordException();
				} else if (status == Status.NOT_FOUND.getStatusCode()) {
					throw new NoUserFoundException();
				} else if (is2xx(status)) {
					AuthTransfer at = cr.getEntity(AuthTransfer.class);
					return at;
				} else {
					throw new IllegalArgumentException();
				}
			} else if (is2xx(status)) {
				AuthTransfer at = cr.getEntity(AuthTransfer.class);
				return at;
			} else {
				throw new IllegalArgumentException();
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("", e);
		}

		return null;
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
	
	public DegreeCourseTransfer getDegreeCourseFromUser(String id) {
		return this.webResource.path(USER).path(id).path(DEGREECOURSE).accept(MediaType.APPLICATION_JSON).get(DegreeCourseTransfer.class);
	}
	
	public List<LectureMarkTransfer> getLectureFromUser(String id) {
		try {
			String json = this.webResource.path(USER).path(id).path(LECTURE).accept(MediaType.APPLICATION_JSON).get(String.class);
			return mapper.readValue(json, new TypeReference<List<LectureMarkTransfer>>() {
			});
		} catch (IOException e) {
			logger.error("", e);
		}

		return new ArrayList<LectureMarkTransfer>();
	}

}
