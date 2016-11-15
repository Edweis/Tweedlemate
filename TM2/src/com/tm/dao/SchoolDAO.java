package com.tm.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.tm.entities.Country;
import com.tm.entities.School;

@Stateless
public class SchoolDAO {

	private static final String Q_SELECT_NAME_BEGIN_NAME = "SELECT s.Name FROM School s WHERE s.Name LIKE CONCAT('%', ?1, '%')";
	private static final String Q_DOUBLON = "SELECT s FROM School S WHERE s.Name=?1 AND s.Country=?2";

	@PersistenceContext
	private EntityManager em;

	public List<String> selectSchoolName_StartWith(String startName) {
		return this.em.createQuery(Q_SELECT_NAME_BEGIN_NAME, String.class).setParameter(1, startName).getResultList();
	}

	public School find(String name, Country country) {
		School res;
		try {
			res = this.em.createQuery(Q_DOUBLON, School.class).setParameter(1, name).setParameter(2, country)
					.getSingleResult();
		} catch (NoResultException e) {
			res = null;
		}
		return res;

	}

}
