package com.tm.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.tm.entities.School;

@Stateless
public class SchoolDAO {

	@PersistenceContext
	private EntityManager em;

	public void create(School school) throws DAOException {
		try {
			em.persist(school);
		} catch (Exception e) {
			throw new DAOException("Error in School persistence", e);
		}
	}
}
