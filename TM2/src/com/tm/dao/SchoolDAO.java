package com.tm.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SchoolDAO {

	private static final String Q_SELECT_NAME_BEGIN_NAME = "SELECT s.Name FROM School s WHERE s.Name LIKE CONCAT('%', ?1, '%')";

	@PersistenceContext
	private EntityManager em;

	public List<String> selectSchoolName_StartWith(String startName) {
		return this.em.createQuery(Q_SELECT_NAME_BEGIN_NAME, String.class).setParameter(1, startName).getResultList();
	}

}
