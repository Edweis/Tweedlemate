package com.tm.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.tm.entities.Country;

@Stateless
public class CountryDAO {

	private static final String Q_SELECT_FROM_NAME = "SELECT u FROM Country u WHERE u.Name = ?1";
	private static final String Q_SELECT_FROM_CODE2 = "SELECT u FROM Country u WHERE u.Code2 = ?1";
	private static final String Q_SELECT_FROM_CODE3 = "SELECT u FROM Country u WHERE u.Code3 = ?1";
	private static final String Q_SELECT_ID_NAME = "SELECT c FROM Country c ORDER BY c.Name";

	@PersistenceContext
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

	public List<Country> getList() {
		List<Country> listResultat = em.createQuery(Q_SELECT_ID_NAME, Country.class).getResultList();
		return listResultat;
	}

}
