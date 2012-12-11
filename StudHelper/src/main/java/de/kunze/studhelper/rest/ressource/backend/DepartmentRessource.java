package de.kunze.studhelper.rest.ressource.backend;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.kunze.studhelper.rest.transfer.backend.DegreeCourseTransfer;
import de.kunze.studhelper.rest.transfer.backend.DepartmentTransfer;
import de.kunze.studhelper.rest.transfer.backend.ModulTransfer;

/**
 * This Interface shows the API for the department. You can create, get, update
 * and delete departments.
 * 
 * @author Stefan Kunze
 * @version 1.0
 * @since 1.0
 */
@Path("/department")
public interface DepartmentRessource {

	/**
	 * This Method returns all Departments from the DB
	 * 
	 * @return List of DepartmentTransfer
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<DepartmentTransfer> getAllDepartments();

	/**
	 * This Method returns a spezified Department from the DB
	 * 
	 * @param id
	 *            This is the id of the department which should be returned
	 * @return the DepartmentTransfer
	 */
	@GET
	@Path("{department_id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public DepartmentTransfer getDepartment(@PathParam("department_id") Long id);

	/**
	 * This Method creates a department, and conect the Department with the
	 * spezified University-ID
	 * 
	 * @param id
	 *            the id from the university
	 * @param department
	 *            The Object, which should be saved in the DB
	 * @return This Method returns 201 CREATED for success or 500 for
	 *         Server-Error
	 */
	@PUT
	@Path("{university_id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createDepartment(@PathParam("university_id") Long id, DepartmentTransfer department);

	/**
	 * This Method updates a spezified Department 
	 * 
	 * @param department the object from the Department
	 * @return This method returns 204 NO CONTENT fro success or 500 for Server-Error
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response updateDepartment(DepartmentTransfer department);

	/**
	 * This Method deletes a spezified department (ID)
	 * @param id the id which identified the department
	 * @return
	 */
	@DELETE
	@Path("{department_id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deleteDepartment(@PathParam("department_id") Long id);

	/**
	 * This Method returns all degreecourse in a department
	 * 
	 * @param id the id which identified the department
	 * @return a List of  @see DegreeCourseTransfer
	 */
	@GET
	@Path("{department_id}/degreecourse")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<DegreeCourseTransfer> getAllDegreeCoursesForDepartment(@PathParam("department_id") Long id);

	/**
	 * This Method returns a spezified degreeCourse in a department
	 * @param departmentId the id which spezified the department
	 * @param degreeCourseId the id which spezified the degreecourse
	 * @return @see DegreeCourseTransfer
	 */
	@GET
	@Path("{department_id}/degreecourse/{degreecourse_id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public DegreeCourseTransfer getDegreeCourseForDepartment(@PathParam("department_id") Long departmentId,
			@PathParam("degreecourse_id") Long degreeCourseId);

	/**
	 * This Method creates a degreeCourse in a department
	 * @param id the id which identified the department
	 * @param degreeCourse the object for saving in the DB
	 * @return This method returns 201 CREATED for success, and 500 for Server-Error
	 */
	@PUT
	@Path("{department_id}/degreecourse")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createDegreeCourseForDepartment(@PathParam("department_id") Long id, DegreeCourseTransfer degreeCourse);

	/**
	 * This Method returns all modules in a department
	 * This is needed to get Lectures to more modules (this is for N:M - Relation)
	 * 
	 * @param id the id which identified the department
	 * @return a list of @see ModulTransfer
	 */
	@GET
	@Path("{department_id}/modul")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<ModulTransfer> getModulsForDepartment(@PathParam("department_id") Long id);

}
