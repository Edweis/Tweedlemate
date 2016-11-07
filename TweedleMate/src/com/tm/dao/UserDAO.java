package com.tm.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.tm.entities.User;

@Stateless
public class UserDAO {
	private static final String Q_SELECT_BY_EMAIL = "SELECT u FROM User u WHERE u.Email=?1";
	private static final String Q_SELECT_ALL = "SELECT u FROM User u";
	private static final String Q_SELECT_BY_ID = "SELECT u FROM User u WHERE u.Id=?1";

	private static final String Q_UPDATE_PICTURE_PATH = "UPDATE User u SET u.PicturePath=?1 WHERE u.Id=?2";

	// Manager injection
	@PersistenceContext
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

	public List<User> findAll() {
		return em.createQuery(Q_SELECT_ALL, User.class).getResultList();
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
			return this.findFromId(sId);
		}
	}

	private User findFromId(Integer id) {
		try {
			return em.createQuery(Q_SELECT_BY_ID, User.class).setParameter(1, id).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	private User findFromId(String id) {
		try {
			Integer i = Integer.parseInt(id);
			return this.findFromId(i);
		} catch (Exception e) {
			return null;
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

}