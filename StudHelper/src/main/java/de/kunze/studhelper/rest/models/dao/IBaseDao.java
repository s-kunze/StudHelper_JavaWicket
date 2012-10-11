/**
 * This file is part of StudHelper_JavaWicket
 */
package de.kunze.studhelper.rest.models.dao;

import java.util.List;

/**
 * This Class Shows a BaseDao Interface
 * @author Stefan Kunze
 *
 * @param <T>
 */
public interface IBaseDao<T> {

	/**
	 * To get one Object
	 * @param id id of the Object
	 * @return the Object
	 */
	public T get(Long id);
	
	/**
	 * To get all Objects
	 * @return all Objects
	 */
	public List<T> getAll();
	
	/**
	 * to Save a Object
	 * @param t the Object to save
	 * @return the Id in the database
	 */
	public Long save(T t);
	
	/**
	 * To update a Object
	 * @param t the Object to update
	 * @return the Id in the database
	 */
	public boolean update(T t);
	
	/**
	 * To delete the Object
	 * @param t the Object to delete
	 * @return successful?
	 */
	public boolean delete(T t);
}
