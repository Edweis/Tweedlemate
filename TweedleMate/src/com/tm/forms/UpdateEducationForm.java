package com.tm.forms;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tm.dao.CountryDAO;
import com.tm.dao.DAOException;
import com.tm.dao.EducationDAO;
import com.tm.dao.SchoolDAO;
import com.tm.entities.Country;
import com.tm.entities.Education;
import com.tm.entities.School;

public class UpdateEducationForm {
	// Education
	private static final String F_SCHOOL_NAME = "schoolName";
	private static final String F_DURATION_MONTH = "durationMonth";
	private static final String F_START_YEAR = "startYear";
	private static final String F_PROMOTION = "promotion";
	private static final String F_MAJOR = "major";
	private static final String F_SCHOLARSHIP = "scholarship";
	private static final String F_IS_HOME = "isHomeuniversity";
	private static final String F_IS_CURRENT = "isCurrentEducation";
	private static final String F_COUNTRY_NAME = "countryName";

	private Map<String, String> errors = new HashMap<String, String>();

	@EJB
	private EducationDAO educationDao;

	@EJB
	private SchoolDAO schoolDao;

	@EJB
	private CountryDAO countryDao;

	public void addEducation(HttpServletRequest request, HttpServletResponse response) {
		// Gather data
		String schoolName = request.getParameter(F_SCHOOL_NAME);
		String countryName = request.getParameter(F_COUNTRY_NAME);
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
		cou = checkAndGetCountry(countryName);

		// Create objects
		if (errors.isEmpty()) {
			try {
				sch.setCountry(cou);
				sch.setName("schoolName");

				schoolDao.create(sch);
			} catch (DAOException e) {
				addError("unexpected", e.getMessage());
			}

			try {
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

	private Country checkAndGetCountry(String countryName) {
		return countryDao.findFromName(countryName);
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	private void addError(String field, String message) {
		errors.put(field, message);
	}
}