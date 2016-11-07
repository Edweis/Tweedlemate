package com.tm.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.tm.entities.Room;
import com.tm.entities.User;
import com.tm.exceptions.DAOException;
import com.tm.wrappers.SetRoom;

@Stateless
public class RoomDAO {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Return all rooms concerning the users in parameter
	 * 
	 * @param connectedUser
	 * @param shownUser
	 * @return
	 */
	public SetRoom getRoomsBetween(User... users) {

		String query = "SELECT r FROM Room r WHERE ";
		int i = 1;
		for (User u : users) {
			if (u == null) {
				throw new DAOException("error in getRoomsBetween, one argument is null.");
			}
			query = query + "(?" + i + " MEMBER OF r.Participants) and ";
			i++;
		}
		query = query.substring(0, query.length() - 5);

		TypedQuery<Room> res = this.em.createQuery(query, Room.class);
		i = 1;
		for (User u : users) {
			res.setParameter(i, u);
			i++;
		}
		return new SetRoom(res.getResultList());

	}

}
