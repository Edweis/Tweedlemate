package com.tm.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;

import com.tm.dao.CountryDAO;
import com.tm.dao.DAOException;
import com.tm.dao.EducationDAO;
import com.tm.dao.SchoolDAO;
import com.tm.entities.Country;
import com.tm.entities.Education;
import com.tm.entities.School;
import com.tm.entities.User;
import com.tm.tools.ConnectionTools;

public class UpdateEducationForm {
	// Education
	private static final String F_SCHOOL_NAME = "schoolName";
	private static final String F_DURATION_MONTH = "durationMonth";
	private static final String F_START_YEAR = "startYear";
	private static final String F_PROMOTION = "promotion";
	private static final String F_MAJOR = "major";
	private static final String F_SCHOLARSHIP = "scholarship";
	private static final String F_IS_HOME = "isHomeUniversity";
	private static final String F_IS_CURRENT = "isCurrentEducation";
	private static final String F_COUNTRY_CODE3 = "country";

	private Map<String, String> errors = new HashMap<String, String>();

	private EducationDAO educationDao;
	private SchoolDAO schoolDao;
	private CountryDAO countryDao;

	public UpdateEducationForm(EducationDAO educationDao, SchoolDAO schoolDao, CountryDAO countryDao) {
		this.educationDao = educationDao;
		this.schoolDao = schoolDao;
		this.countryDao = countryDao;
	}

	public void addEducation(HttpServletRequest request, HttpServletResponse response) {

		// Gather data
		User user = ConnectionTools.getUserConnected(request);

		String schoolName = request.getParameter(F_SCHOOL_NAME);
		String countryCode3 = request.getParameter(F_COUNTRY_CODE3);
		String durationMonth = request.getParameter(F_DURATION_MONTH);
		String startYear = request.getParameter(F_START_YEAR);
		String promotion = request.getParameter(F_PROMOTION);
		String major = request.getParameter(F_MAJOR);
		String scholarship = request.getParameter(F_SCHOLARSHIP);
		String isHomeUniversity = request.getParameter(F_IS_HOME);
		String isCurrentEducation = request.getParameter(F_IS_CURRENT);

		Country cou = new Country();
		School sch = new School();
		Education edu = new Education();

		// Check data
		cou = checkAndGetCountry(countryCode3);
		checkDurationMonth(durationMonth);
		checkStartYear(startYear);
		checkPromotion(promotion);
		checkMajor(major);
		checkScholarchip(scholarship);

		// Create objects
		if (errors.isEmpty()) {
			try {
				sch.setCountry(cou);
				sch.setName(schoolName);

				schoolDao.create(sch);
			} catch (DAOException e) {
				addError("unexpected", e.getMessage());
			}

			try {
				edu.setUser(user);
				edu.setSchool(sch);
				edu.setDurationMonth(Integer.parseInt(durationMonth));
				edu.setStartYear(Integer.parseInt(startYear));
				edu.setPromotion(promotion);
				edu.setMajor(major);
				edu.setScholarship(scholarship);
				edu.setIsCurrentEducation(Boolean.parseBoolean(isCurrentEducation));
				edu.setIsHomeUniversity(Boolean.parseBoolean(isHomeUniversity));

				educationDao.create(edu);
			} catch (DAOException e) {
				addError("unexpected", e.getMessage());
			}
		}
	}

	private void checkScholarchip(String scholarship) {
	}

	private void checkMajor(String major) {
	}

	private void checkPromotion(String promotion) {
	}

	private void checkStartYear(String startYear) {
		try {
			int i = Integer.parseInt(startYear);
			if (i <= DateTime.now().getYear() + 1) {
				addError(F_DURATION_MONTH, "Oh wow you studied for a negative number of years ?");
			}
			if (i <= 1000) {
				addError(F_DURATION_MONTH, "Incorrect year");
			}
		} catch (Exception e) {
			addError(F_DURATION_MONTH, "Incorrect year");
		}
	}

	private void checkDurationMonth(String durationMonth) {
		try {
			int i = Integer.parseInt(durationMonth);
			if (i <= 0) {
				addError(F_DURATION_MONTH, "Oh wow you studied for a negative number of years ?");
			}
		} catch (Exception e) {
			addError(F_DURATION_MONTH, "The duration should be an integer.");
		}
	}

	private Country checkAndGetCountry(String countryCode3) {
		Country c = countryDao.findFromCode3(countryCode3);
		if (countryCode3.length() == 3 || c == null) {
			addError(F_COUNTRY_CODE3, "Oh and where was that ?");
		}
		return c;
	};

	public Map<String, String> getErrors() {
		return errors;
	}

	private void addError(String field, String message) {
		errors.put(field, message);
	}
}