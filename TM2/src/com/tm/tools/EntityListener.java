package com.tm.tools;

import javax.ejb.EJB;
import javax.persistence.PrePersist;

import com.tm.dao.SchoolDAO;
import com.tm.entities.Education;
import com.tm.entities.School;

/**
 * Class that contains all listener of all entities
 * 
 * @author François Rullière
 *
 */
public class EntityListener {
	@EJB
	protected SchoolDAO schoolDao;

	/**
	 * Education entity prepersist method
	 * 
	 * @param e
	 */
	@PrePersist
	public void prePersistEducation(Education e) {
		// If the school already exists, use the one that exists
		School s = e.getSchool();
		School doublon = schoolDao.find(s.getName(), s.getCountry());
		if (doublon != null) {
			e.setSchool(doublon);
		}
	}

	// /**
	// * School entity prePersist method
	// *
	// * @param s
	// */
	// @PrePersist
	// public void prePersistSchool(School s) {
	// // Return error if try to persist a school with a combination Name,
	// // Country that already exists
	// School doublon = schoolDao.find(s.getName(), s.getCountry());
	// if (doublon != null) {
	// throw new DAOException(
	// "ERROR the education : #" + s.getId() + " " + s + " already exists as #"
	// + doublon.getId());
	// }
	// }
}
