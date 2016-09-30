package com.tm.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import com.tm.entities.Education;

@Stateless
public class EducationDAO {

	private EntityManager em;

	public void create(Education education) {
		try {
			em.persist(education);
		} catch (Exception e) {
			throw new DAOException("Error in Education persistence", e);
		}
	}
}
