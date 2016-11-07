package com.tm.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Generic CRUD class that allow to create, remove, update, refresh and other
 * action that applies to all entities
 * 
 * @author François Rullière
 *
 */
@Stateless
@Local(CRUDimpl.class)
public class GenericCRUD implements CRUDimpl {

	@PersistenceContext
	protected EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tm.dao.CRUDimpl#getFromId(java.lang.Long)0
	 */
	@Override
	@SuppressWarnings("unchecked")
	public final <T> T getFromId(Class<?> clazz, Long id) {
		return (T) clazz.cast(em.find(clazz, id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tm.dao.CRUDimpl#getAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public final <T> List<T> getAll(Class<?> clazz) {
		String query = "SELECT x FROM " + clazz.getName() + " x";
			TypedQuery<?> a = em.createQuery(query, clazz);
			List<?> b = a.getResultList();
			return (List<T>) b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tm.dao.CRUDimpl#create(T)
	 */
	@Override
	public final <T> void create(T entity) {
		if (!constraintValidationsDetected(entity)) {
			em.persist(entity);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tm.dao.CRUDimpl#create(java.util.List)
	 */
	@Override
	public final <T> void create(List<T> entityList) {
		for (T entity : entityList) {
			em.persist(entity);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tm.dao.CRUDimpl#update(T)
	 */
	@Override
	public <T> T merge(T entity) {
		return em.merge(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tm.dao.CRUDimpl#update(java.util.Set)
	 */
	@Override
	public <T> void merge(Set<T> entitySet) {
		for (T entity : entitySet) {
			this.merge(entity);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tm.dao.CRUDimpl#remove(T)
	 */
	@Override
	public final <T> void remove(T entity) {
		if (!constraintValidationsDetected(entity)) {
			em.remove(em.merge(entity));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tm.dao.CRUDimpl#refresh(T)
	 */
	@Override
	public final <T> void refresh(T entity) {
		em.refresh(em.merge(entity));
	}

	private <T> boolean constraintValidationsDetected(T entity) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
		if (constraintViolations.size() > 0) {
			Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
			while (iterator.hasNext()) {
				ConstraintViolation<T> cv = iterator.next();
				System.err
						.println(cv.getRootBeanClass().getName() + "." + cv.getPropertyPath() + " " + cv.getMessage());

			}
			return true;
		} else {
			return false;
		}
	}
}
