package com.tm.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.tm.entities.School;

@Stateless
public class SchoolDAO {

	@PersistenceContext(unitName = "db_tm_PU")
	private EntityManager em;

	public void create(School object) {
		this.em.persist(object);
	}

	public List<School> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public School getFromId(Long id) {
		return this.em.find(School.class, id);
	}

	public void remove(School object) {
		// TODO Auto-generated method stub

	}

}
