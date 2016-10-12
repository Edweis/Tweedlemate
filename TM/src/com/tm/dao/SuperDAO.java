package com.tm.dao;

import java.util.List;

public abstract interface SuperDAO<T> {

	/**
	 * Persist the object in the data base
	 * 
	 * @param object
	 */
	public void create(T object);

	/**
	 * Return a list of all object in the data base
	 * 
	 * @return
	 */
	public List<T> getAll();

	/**
	 * Return one object with the specific id. If it is not found or the id is
	 * false, return null
	 * 
	 * @param id
	 * @return
	 */
	public T getFromId(Long id);

	/**
	 * Remove the object from the database
	 * 
	 * @param object
	 */
	public void remove(T object);

}
