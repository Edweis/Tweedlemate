package com.tm.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.tm.entities.Country;

@Stateless
public class CountryDAO {

	@PersistenceContext(unitName = "db_tm_PU")
	private EntityManager em;

	private static final String Q_SELECT_FROM_NAME = "SELECT u FROM Country u WHERE u.Name = ?1";
	private static final String Q_SELECT_FROM_CODE2 = "SELECT u FROM Country u WHERE u.Code2 = ?1";
	private static final String Q_SELECT_FROM_CODE3 = "SELECT u FROM Country u WHERE u.Code3 = ?1";
	private static final String Q_SELECT_ALL = "SELECT u FROM Country u";

	/**
	 * Find a country from its name
	 * 
	 * @param country
	 */
	public Country findFromName(String countryName) {
		try {
			return em.createQuery(Q_SELECT_FROM_NAME, Country.class).setParameter(1, countryName).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Country findFromCode2(String code2) {
		try {
			return em.createQuery(Q_SELECT_FROM_CODE2, Country.class).setParameter(1, code2).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Country findFromCode3(String code3) {
		try {
			return em.createQuery(Q_SELECT_FROM_CODE3, Country.class).setParameter(1, code3).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void create(Country object) {
		this.em.persist(object);
	}

	public List<Country> getAll() {
		return this.em.createQuery(Q_SELECT_ALL, Country.class).getResultList();
	}

	public Country getFromId(Long id) {
		return this.em.find(Country.class, id);
	}

	public void remove(Country object) {
		// TODO Auto-generated method stub

	}

}
