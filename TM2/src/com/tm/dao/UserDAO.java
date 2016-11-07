package com.tm.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.tm.entities.User;
import com.tm.exceptions.ConnectionException;

@Stateless
public class UserDAO {

	private static final String Q_SELECT_BY_EMAIL = "SELECT u FROM User u WHERE u.Email=?1";
	private static final String Q_FIND = "SELECT u FROM User u WHERE u.Email=?1 AND u.Password=?2";

	@PersistenceContext
	protected EntityManager em;

	/**
	 * Find a user from this email
	 * 
	 * @param email
	 * @return The User if found, null otherwise
	 */
	public User findFromEmail(String email) {
		try {
			User query = em.createQuery(Q_SELECT_BY_EMAIL, User.class).setParameter(1, email).getSingleResult();

			if (query.getEmail() == null) {
				return null;
			} else {
				return query;
			}
		} catch (NoResultException e) {
			return null;
		}

	}

	/**
	 * 
	 * @param email
	 * @param password
	 * @return The current connected User
	 * @throws ConnectionException
	 *             When the password and email don't match with the information
	 *             in the database
	 */
	public User Connect(String email, String password) throws ConnectionException {
		User user = findFromEmail(email);
		if (user != null) {
			if (user.getPassword().equals(password)) {
				// connection
				return user;
			} else {
				// wrong password
				throw new ConnectionException("Email or password incorrect.");
			}
		} else {
			// user not found
			throw new ConnectionException("Email or password incorrect.");
		}
	}

	/**
	 * Return true if password and email match
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public boolean checkIdentification(String email, String password) {
		try {
			return getUser(email, password) != null;
		} catch (Exception e) {
			return false;
		}
	}

	public User getUser(String email, String password) {
		User u = em.createQuery(Q_FIND, User.class).setParameter(1, email).setParameter(2, password).getSingleResult();
		return u;
	}

}