package com.tm.dao;

import java.util.List;

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
	private static final String Q_FIND_ALL = "SELECT u FROM User u";

	private static final String Q_UPDATE_PICTURE_PATH = "UPDATE User u SET u.PicturePath=?1 WHERE u.Id=?2";

	@PersistenceContext
	private EntityManager em;

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
	 * Find the user from the Id in the URI parameter.<br/>
	 * For instance /part1/45/Karo/BoT will return user with id 45
	 * 
	 * @param uri
	 * @return
	 */
	public User findFromURI(String uri) {
		String[] parts = uri.split("/");
		if (parts.length < 2) {
			return null;
		} else {
			String sId = parts[3];// fetch the "id" field
			return this.getFromId(Long.parseLong(sId));
		}
	}

	/**
	 * Update the picturePath of the connectedUser in the database. The new
	 * picturePath is the one referenced in the object connectedUser
	 * 
	 * @param connectedUser
	 */
	public void updatePicturePath(User connectedUser) {
		Object newPicturePath = connectedUser.getPicturePath();
		Object userId = connectedUser.getId();
		em.createQuery(Q_UPDATE_PICTURE_PATH).setParameter(1, newPicturePath).setParameter(2, userId).executeUpdate();
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

	public void create(User object) {
		this.em.persist(object);
	}

	public List<User> getAll() {
		return this.em.createQuery(Q_FIND_ALL, User.class).getResultList();
	}

	public User getFromId(Long id) {
		return this.em.find(User.class, id);
	}

	public void remove(User object) {
		this.em.remove(object);
	}

}