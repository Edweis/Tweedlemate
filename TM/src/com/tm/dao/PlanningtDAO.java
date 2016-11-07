package com.tm.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.tm.entities.Planning;

@Stateless
public class PlanningtDAO {

	@PersistenceContext
	private EntityManager em;

	public void create(Planning object) {
		this.em.persist(object);
	}

	public List<Planning> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Planning getFromId(Long id) {
		return this.em.find(Planning.class, id);
	}

	public void remove(Planning object) {
		// TODO Auto-generated method stub

	}

}
