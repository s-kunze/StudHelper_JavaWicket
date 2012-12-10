package de.kunze.studhelper.rest.impl.user;

import static de.kunze.studhelper.rest.util.Constans.BASE_URL;
import static de.kunze.studhelper.rest.util.Constans.REST_PART;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.kunze.studhelper.rest.models.backend.DegreeCourse;
import de.kunze.studhelper.rest.models.dao.BaseDao;
import de.kunze.studhelper.rest.models.user.User;
import de.kunze.studhelper.rest.ressource.user.UserRessource;
import de.kunze.studhelper.rest.transfer.user.NewUserTransfer;
import de.kunze.studhelper.rest.transfer.user.UserTransfer;

public class UserImpl implements UserRessource {

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

}
