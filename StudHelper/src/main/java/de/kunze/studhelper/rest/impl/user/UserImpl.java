package de.kunze.studhelper.rest.impl.user;

import static de.kunze.studhelper.rest.util.Constans.BASE_URL;
import static de.kunze.studhelper.rest.util.Constans.REST_PART;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.core.util.Base64;

import de.kunze.studhelper.rest.auth.AuthorizationHandler;
import de.kunze.studhelper.rest.exception.NoUserFoundException;
import de.kunze.studhelper.rest.exception.WrongPasswordException;
import de.kunze.studhelper.rest.models.backend.DegreeCourse;
import de.kunze.studhelper.rest.models.dao.BaseDao;
import de.kunze.studhelper.rest.models.user.User;
import de.kunze.studhelper.rest.ressource.user.UserRessource;
import de.kunze.studhelper.rest.transfer.user.NewUserTransfer;
import de.kunze.studhelper.rest.transfer.user.UserTransfer;

public class UserImpl implements UserRessource {

	private static final Logger logger = LoggerFactory.getLogger(UserImpl.class);

	public List<UserTransfer> getAllUsers() {
		List<UserTransfer> result = new ArrayList<UserTransfer>();

		BaseDao<User> dao = new BaseDao<User>(User.class);
		List<User> users = dao.getAll();

		for (User user : users) {
			result.add(user.transform());
		}

		dao.close();
		return result;
	}

	public UserTransfer getUser(Long id) {
		BaseDao<User> dao = new BaseDao<User>(User.class);

		User result = dao.get(id);

		if (result != null) {
			dao.close();
			return result.transform();
		}

		dao.close();
		return new UserTransfer();
	}

	public Response createUser(Long id, NewUserTransfer newUser) {
		logger.info("Creating new user");
		User user = newUser.transform();

		BaseDao<DegreeCourse> daoUni = new BaseDao<DegreeCourse>(DegreeCourse.class);
		DegreeCourse degreeCourse = daoUni.get(id);

		user.setDegreeCourse(degreeCourse);

		BaseDao<User> daoUser = new BaseDao<User>(User.class);
		daoUser.save(user);

		URI location;
		try {
			location = new URI(BASE_URL + REST_PART + "user/" + user.getId());

			daoUni.close();
			daoUser.close();
			return Response.created(location).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		daoUni.close();
		daoUser.close();
		return Response.serverError().build();
	}

	public Response updateUser(UserTransfer user) {
		BaseDao<User> dao = new BaseDao<User>(User.class);
		if (dao.update(user.transform())) {
			dao.close();
			return Response.status(Status.NO_CONTENT).build();
		} else {
			dao.close();
			return Response.serverError().build();
		}
	}

	public Response deleteUser(Long id) {
		logger.info("Delete user   {}", id);
		BaseDao<User> dao = new BaseDao<User>(User.class);
		User user = dao.get(id);

		if (user == null) {
			dao.close();
			return Response.status(Status.NOT_FOUND).build();
		}

		if (dao.delete(user)) {
			dao.close();
			return Response.status(Status.NO_CONTENT).build();
		} else {
			dao.close();
			return Response.serverError().build();
		}
	}

	public Response authUser(HttpHeaders header) {
		try {
			
			String authorization = header.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0);

			byte[] authorizationArr = Base64.decode(authorization);

			authorization = new String(authorizationArr, "UTF-8");

			String[] authArr = authorization.split(":");

			if (authArr.length == 2) {
				String username = authArr[0];
				String password = authArr[1];

				logger.info("Trying login of user  {}", username);
				try {
					AuthorizationHandler auth = new AuthorizationHandler();
					auth.auth(username, password);
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

				return Response.status(Status.OK).build();
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("", e);
		}

		return Response.status(Status.BAD_REQUEST).build();
	}

}
