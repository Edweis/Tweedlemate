package com.tm.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tm.dao.EducationDAO;
import com.tm.entities.Education;

@WebServlet("/UpdateInfo/*")
public class UpdateInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String VUE_DEFAULT = "/Profil";

	// Education
	private static final String F_SCHOOL_NAME = "schoolName";
	private static final String F_DURATION_MONTH = "durationMonth";
	private static final String F_START_YEAR = "startYear";
	private static final String F_PROMOTION = "promotion";
	private static final String F_MAJOR = "major";
	private static final String F_SCHOLARSHIP = "scholarship";
	private static final String F_IS_HOME = "isHomeuniversity";
	private static final String F_IS_CURRENT = "isCurrentEducation";

	private static final String AS_ERROR = "updateErrors";

	@EJB
	private EducationDAO educationDao;
	private Map<String, String> updateErrors = new HashMap<String, String>();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.sendRedirect(request.getContextPath() + VUE_DEFAULT);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Get the previous page, but if it doesn't make sense g to vue_default
		String p = request.getRequestURI().replace(request.getContextPath(), "");

		String[] position = p.split("/");

		if ("Education".equals(position[0])) {
			updateEducation(request, response);
		}

		response.sendRedirect(request.getContextPath() + VUE_DEFAULT);
	}

	private void updateEducation(HttpServletRequest request, HttpServletResponse response) {
		// Gather data
		String schoolName = request.getParameter(F_SCHOOL_NAME);
		String durationMonth = request.getParameter(F_DURATION_MONTH);
		String startYear = request.getParameter(F_START_YEAR);
		String promotion = request.getParameter(F_PROMOTION);
		String major = request.getParameter(F_MAJOR);
		String scholarship = request.getParameter(F_SCHOLARSHIP);
		String isHomeUniversity = request.getParameter(F_IS_HOME);
		String isCurrentEducation = request.getParameter(F_IS_CURRENT);

		// Check data
		// TODO

		// Create objects
		if (updateErrors.isEmpty()) {
			Education edu = new Education();
			edu.getSchool().setName(schoolName);
			edu.setDurationMonth(Integer.parseInt(durationMonth));
			edu.setStartYear(Integer.parseInt(startYear));
			edu.setPromotion(promotion);
			edu.setMajor(major);
			edu.setScholarship(scholarship);
			edu.setIsCurrentEducation(Boolean.parseBoolean(isCurrentEducation));
			edu.setIsHomeUniversity(Boolean.parseBoolean(isHomeUniversity));

			educationDao.create(edu);
		} else {
			request.getSession().setAttribute(AS_ERROR, updateErrors);
		}
	}
}
