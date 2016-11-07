package com.tm.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.tm.entities.Education;

@Stateless
public class EducationDAO {

	@PersistenceContext
	private EntityManager em;

	EntityManager superEm = em;
	Class<?> clazz = Education.class;

}
