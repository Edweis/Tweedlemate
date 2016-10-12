package com.tm.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.tm.entities.Education;

@Stateless
public class EducationDAO {

	@PersistenceContext(unitName = "db_tm_PU")
	private EntityManager em;

	public void create(Education object) {
		this.em.persist(object);
	}

	public List<Education> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Education getFromId(Long id) {
		return this.em.find(Education.class, id);
	}

	public void remove(Education object) {
		// TODO Auto-generated method stub

	}

}
