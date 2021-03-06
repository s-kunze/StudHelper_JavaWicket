package de.kunze.studhelper.rest.impl.backend;

import static de.kunze.studhelper.rest.util.Constans.BASE_URL;
import static de.kunze.studhelper.rest.util.Constans.REST_PART;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.kunze.studhelper.rest.models.backend.Modul;
import de.kunze.studhelper.rest.models.backend.Part;
import de.kunze.studhelper.rest.models.dao.BaseDao;
import de.kunze.studhelper.rest.ressource.backend.PartRessource;
import de.kunze.studhelper.rest.transfer.backend.ModulTransfer;
import de.kunze.studhelper.rest.transfer.backend.PartTransfer;

public class PartImpl implements PartRessource {

	public List<PartTransfer> getAllParts() {
		List<PartTransfer> result = new ArrayList<PartTransfer>();

		BaseDao<Part> dao = new BaseDao<Part>(Part.class);
		List<Part> parts = dao.getAll();

		for (Part part : parts) {
			result.add(part.transform());
		}

		dao.close();
		return result;
	}

	public PartTransfer getPart(Long id) {
		BaseDao<Part> dao = new BaseDao<Part>(Part.class);

		Part result = dao.get(id);

		if (result != null) {
			dao.close();
			return result.transform();
		}

		dao.close();
		return new PartTransfer();
	}

	public Response createPart(Long id, PartTransfer part) {
		Part par = part.transform();

		BaseDao<Part> dao = new BaseDao<Part>(Part.class);
		dao.save(par);

		URI location;
		try {
			location = new URI(BASE_URL + REST_PART + "part/" + par.getId());
			dao.close();
			return Response.created(location).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		dao.close();
		return Response.serverError().build();
	}

	public Response updatePart(PartTransfer part) {
		BaseDao<Part> dao = new BaseDao<Part>(Part.class);
		
		Part par = part.transform();
		par.setDegreeCourse(dao.get(par.getId()).getDegreeCourse());
		
		if (dao.update(par)) {
			dao.close();
			return Response.status(Status.NO_CONTENT).build();
		} else {
			dao.close();
			return Response.serverError().build();
		}
	}

	public Response deletePart(Long id) {
		BaseDao<Part> dao = new BaseDao<Part>(Part.class);
		Part part = dao.get(id);

		if (part == null) {
			dao.close();
			return Response.status(Status.NOT_FOUND).build();
		}

		if (dao.delete(part)) {
			dao.close();
			return Response.status(Status.NO_CONTENT).build();
		} else {
			dao.close();
			return Response.serverError().build();
		}
	}

	public List<ModulTransfer> getAllModulesForPart(Long id) {
		List<ModulTransfer> result = new ArrayList<ModulTransfer>();
		
		BaseDao<Part> dao = new BaseDao<Part>(Part.class);
		Part part = dao.get(id);

		if (part != null) {
			List<Modul> moduls = part.getModule();
			
			if(moduls != null) {
				for(Modul modul : moduls) {
					result.add(modul.transform());
				}
			}
		}

		dao.close();
		return result;
	}

	public ModulTransfer getModulForPart(Long partId, Long modulId) {
		BaseDao<Modul> dao = new BaseDao<Modul>(Modul.class);
		
		Modul modul = dao.get(modulId);

		if (modul != null) {
			dao.close();
			return modul.transform();
		}
		
		dao.close();
		return new ModulTransfer();	
	}

	public Response createModulForPart(Long id, ModulTransfer modul) {
		Modul mod = modul.transform();

		BaseDao<Part> daoPar = new BaseDao<Part>(Part.class);
		Part part = daoPar.get(id);
		mod.setPart(part);

		BaseDao<Modul> daoMod = new BaseDao<Modul>(Modul.class);
		daoMod.save(mod);

		URI location;
		try {
			location = new URI(BASE_URL + REST_PART + "part/" + id + "/modul/"
					+ mod.getId());
			
			daoPar.close();
			daoMod.close();
			return Response.created(location).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		daoPar.close();
		daoMod.close();
		return Response.serverError().build();
	}

}
