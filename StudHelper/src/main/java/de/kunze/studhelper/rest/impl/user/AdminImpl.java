package de.kunze.studhelper.rest.impl.user;

import java.io.UnsupportedEncodingException;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.core.util.Base64;

import de.kunze.studhelper.rest.auth.AuthorizationHandler;
import de.kunze.studhelper.rest.exception.NoUserFoundException;
import de.kunze.studhelper.rest.exception.WrongPasswordException;
import de.kunze.studhelper.rest.ressource.user.AdminRessource;
import de.kunze.studhelper.rest.transfer.user.AuthTransfer;

public class AdminImpl implements AdminRessource {

	private static final Logger logger = LoggerFactory.getLogger(AdminImpl.class);
	
	public Response authAdmin(HttpHeaders header) {
		try {
			String authorization = header.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0);

			byte[] authorizationArr = Base64.decode(authorization);

			authorization = new String(authorizationArr, "UTF-8");

			String[] authArr = authorization.split(":");

			if (authArr.length == 2) {
				String username = authArr[0];
				String password = authArr[1];

				logger.info("Trying login of admin  {}", username);
				try {
					AuthorizationHandler auth = new AuthorizationHandler();
					AuthTransfer type = auth.authAdmin(username, password);
					return Response.status(Status.OK).entity(type).build();
				} catch (WrongPasswordException e) {
					logger.error("", e);
					return Response.status(Status.UNAUTHORIZED).build();
				} catch (NoUserFoundException e) {
					logger.error("", e);
					return Response.status(Status.NOT_FOUND).build();
				} catch (Exception e) {
					logger.error("", e);
					return Response.status(Status.INTERNAL_SERVER_ERROR).build();
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("", e);
		}

		return Response.status(Status.BAD_REQUEST).build();
	}

}
