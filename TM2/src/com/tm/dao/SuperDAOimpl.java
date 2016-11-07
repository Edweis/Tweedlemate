package com.tm.dao;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 * Super class of all DAO that provides basics CRUD methods. All inherited class
 * should set the EntityManager when postConstruct like : <br/>
 * <code>
 *  &#64;PersistenceContext
 *  	private EntityManager em;
 *  
 *  	EntityManager superEm = em;
 *  	Class<?> clazz = User.class;
 * </code>
 * 
 * @author François Rullière
 *
 */
@Local
public abstract interface SuperDAOimpl {

	EntityManager em = null;
	Class<?> clazz = null;

	/**
	 * return the object with the id <code>id</code>
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default <T> T getFromId(Long id) {
		return (T) clazz.cast(em.find(clazz, id));
	}

	/**
	 * return the list of all elements in the database without any filters
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default <T> List<T> getAll() {
		String query = "SELECT x FROM " + clazz.getName() + " x";

		return (List<T>) em.createQuery(query, clazz).getResultList();
	}

	/**
	 * Persist an entity
	 * 
	 * @param entity
	 */
	default <T> void create(T entity) {
		try {
			em.persist(entity);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Persist a list of entity
	 * 
	 * @param entityList
	 */
	default <T> void create(List<T> entityList) {
		for (T entity : entityList) {
			em.persist(entity);
		}
	}

	/**
	 * Update an entity
	 * 
	 * @param entity
	 */
	default <T> void update(T entity) {
		em.merge(entity);
	}

	/**
	 * Update a set of entity
	 * 
	 * @param entitySet
	 */
	default <T> void update(Set<T> entitySet) {
		for (T entity : entitySet) {
			update(entity);
		}
	}

	/**
	 * Remove an entity
	 * 
	 * @param entity
	 */
	default <T> void remove(T entity) {
		em.remove(em.merge(entity));
	}

	/**
	 * refresh an entity
	 * 
	 * @param entity
	 */
	default <T> void refresh(T entity) {
		em.refresh(entity);
	}

}
