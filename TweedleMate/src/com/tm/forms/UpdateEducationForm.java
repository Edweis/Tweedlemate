package com.tm.forms;

import javax.servlet.ServletRequest;

import org.joda.time.DateTime;

import com.tm.dao.CountryDAO;
import com.tm.dao.EducationDAO;
import com.tm.dao.SchoolDAO;
import com.tm.entities.Country;
import com.tm.entities.Education;
import com.tm.entities.School;
import com.tm.entities.User;

public class UpdateEducationForm extends UpdateForm {
	// Education
	String sucessMessage = "New education added ! You can now get contacted by mentees for an appointment.";
	String labelFieldSucess = "educationLabel";

	private static final String F_SCHOOL_NAME = "schoolName";
	private static final String F_DURATION_MONTH = "durationMonth";
	private static final String F_START_YEAR = "startYear";
	private static final String F_PROMOTION = "promotion";
	private static final String F_MAJOR = "major";
	private static final String F_SCHOLARSHIP = "scholarship";
	private static final String F_IS_HOME = "isHomeUniversity";
	private static final String F_IS_CURRENT = "isCurrentEducation";
	private static final String F_COUNTRY_CODE3 = "country";

	private EducationDAO educationDao;
	private SchoolDAO schoolDao;
	private CountryDAO countryDao;

	private String schoolName;
	private String countryCode3;
	private String durationMonth;
	private String startYear;
	private String promotion;
	private String scholarship;
	private String major;
	private String isHomeUniversity;
	private String isCurrentEducation;
	private Country inputCountry;

	public UpdateEducationForm(EducationDAO educationDao, SchoolDAO schoolDao, CountryDAO countryDao) {
		this.educationDao = educationDao;
		this.schoolDao = schoolDao;
		this.countryDao = countryDao;
	}

	protected void getAllParameters(ServletRequest request) {
		schoolName = request.getParameter(F_SCHOOL_NAME);
		countryCode3 = request.getParameter(F_COUNTRY_CODE3);
		durationMonth = request.getParameter(F_DURATION_MONTH);
		startYear = request.getParameter(F_START_YEAR);
		promotion = request.getParameter(F_PROMOTION);
		major = request.getParameter(F_MAJOR);
		scholarship = request.getParameter(F_SCHOLARSHIP);
		isHomeUniversity = request.getParameter(F_IS_HOME);
		isCurrentEducation = request.getParameter(F_IS_CURRENT);

		inputCountry = checkAndGetCountry(countryCode3);
	}

	protected void checkData() {
		checkDurationMonth(durationMonth);
		checkStartYear(startYear);
		checkPromotion(promotion);
		checkMajor(major);
		checkScholarchip(scholarship);
	}

	private School createSchool() {
		School sch = new School();

		sch.setCountry(inputCountry);
		sch.setName(schoolName);

		return sch;
	}

	private Education createEducation(User connectedUser, School inputSchool) {
		Education edu = new Education();

		edu.setUser(connectedUser);
		edu.setSchool(inputSchool);
		edu.setDurationMonth(Integer.parseInt(durationMonth));
		edu.setStartYear(Integer.parseInt(startYear));
		edu.setPromotion(promotion);
		edu.setMajor(major);
		edu.setScholarship(scholarship);
		edu.setIsCurrentEducation(Boolean.parseBoolean(isCurrentEducation));
		edu.setIsHomeUniversity(Boolean.parseBoolean(isHomeUniversity));

		return edu;
	}

	protected void persist(User connectedUser) {
		School sch = createSchool();
		Education edu = createEducation(connectedUser, sch);

		schoolDao.create(sch);
		educationDao.create(edu);
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
			if (i > DateTime.now().getYear() + 1) {
				addError(F_DURATION_MONTH, "You can't add a school you have't been yet.");
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
		if (c == null) {
			addError(F_COUNTRY_CODE3, "Oh and where was that ?");
		}
		return c;
	};

}