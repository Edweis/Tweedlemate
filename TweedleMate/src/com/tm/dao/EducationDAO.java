package com.tm.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.tm.entities.Education;

@Stateless
public class EducationDAO {

	@PersistenceContext
	private EntityManager em;

	public void create(Education education) throws DAOException {
		try {
			em.persist(education);
		} catch (Exception e) {
			throw new DAOException("Error in Education persistence", e);
		}
	}
}
