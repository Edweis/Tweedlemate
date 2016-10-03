package com.tm.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.tm.entities.User;

@Stateless
public class UserDAO {
	private static final String Q_SELECT_BY_EMAIL = "SELECT u FROM User u WHERE u.Email=:email";
	private static final String Q_SELECT_ALL = "SELECT u FROM User u";
	private static final String PQ_EMAIL = "email";

	// Manager injection
	@PersistenceContext(unitName = "db_tm_PU")
	private EntityManager em;

	/**
	 * Create a new user
	 * 
	 * @param User
	 */
	public void create(User User) throws DAOException {
		try {
			em.persist(User);
		} catch (Exception e) {
			throw new DAOException("Error in User persistence", e);
		}
	}

	/**
	 * Find a user from this email
	 * 
	 * @param email
	 * @return The User if found, null otherwise
	 */
	public User findFromEmail(String email) {

		try {
			User query = em.createQuery(Q_SELECT_BY_EMAIL, User.class).setParameter(PQ_EMAIL, email).getSingleResult();

			if (query.getEmail() == null) {
				return null;
			} else {
				return query;
			}
		} catch (NoResultException e) {
			return null;
		}

	}

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

	public List<User> findAll() {
		return em.createQuery(Q_SELECT_ALL, User.class).getResultList();
	}

}