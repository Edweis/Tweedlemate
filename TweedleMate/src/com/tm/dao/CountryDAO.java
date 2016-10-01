package com.tm.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.tm.entities.Country;

@Stateless
public class CountryDAO {

	private static final String Q_SELECT_FROM_NAME = "SELECT u FROM User u WHERE u.Name = ?1";

	@PersistenceContext(unitName = "db_tm_PU")
	private EntityManager em;

	/**
	 * Create a country. It should not be used since countries are fixed.
	 * 
	 * @param country
	 */
	@Deprecated
	public void create(Country country) throws DAOException {
	}

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

}
