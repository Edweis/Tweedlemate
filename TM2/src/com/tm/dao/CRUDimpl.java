package com.tm.dao;

import java.util.List;
import java.util.Set;

public interface CRUDimpl {

	/**
	 * Persist an entity
	 * 
	 * @param entity
	 */
	<T> void create(T entity);

	/**
	 * Persist a list of entity
	 * 
	 * @param entityList
	 */
	<T> void create(List<T> entityList);

	/**
	 * Save the modification to the database
	 * 
	 * @param entity
	 * @return
	 */
	<T> T merge(T entity);

	/**
	 * Update a set of entity
	 * 
	 * @param entitySet
	 */
	<T> void merge(Set<T> entitySet);

	/**
	 * Remove an entity, witch means it will fetch the actual value in the
	 * database and update the entity
	 * 
	 * @param entity
	 */
	<T> void remove(T entity);

	/**
	 * refresh an entity
	 * 
	 * @param entity
	 */
	<T> void refresh(T entity);

	<T> T getFromId(Class<?> clazz, Long id);

	<T> List<T> getAll(Class<?> clazz);

}