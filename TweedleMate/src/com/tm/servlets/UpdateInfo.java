package com.tm.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tm.dao.ConnectionException;
import com.tm.dao.CountryDAO;
import com.tm.dao.EducationDAO;
import com.tm.dao.SchoolDAO;
import com.tm.dao.UserDAO;
import com.tm.forms.UpdateEducationForm;
import com.tm.forms.UpdatePicture;
import com.tm.tools.ConnectionTools;

@WebServlet("/UpdateInfo/*")
@MultipartConfig
public class UpdateInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String VUE_DEFAULT = "/Profile";

	private static final String AS_ERROR = "updateErrors";
	private static final String AS_RESULT = "updateMessages";
	private static final String L_EDUCATION = "educationLabel"; // L stands for
																// "Label"

	private Map<String, String> messagesMap = new HashMap<String, String>();

	@EJB
	private EducationDAO educationDao;
	@EJB
	private SchoolDAO schoolDao;
	@EJB
	private CountryDAO countryDao;
	@EJB
	private UserDAO userDao;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.sendRedirect(request.getContextPath() + VUE_DEFAULT);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Get path of the current URl
		String p = request.getRequestURI().replace(request.getContextPath() + "/", "");
		String[] position = p.split("/");

		// Do things accordingly
		if (position.length > 0) {
			switch (position[1]) {

			case "Education":
				UpdateEducationForm formEdu = new UpdateEducationForm(educationDao, schoolDao, countryDao);
				formEdu.addEducation(request, response);

				if (formEdu.getErrors().isEmpty()) {
					messagesMap.put(L_EDUCATION,
							"New education added ! You can now get contacted by mentees for an appointment.");
				} else {
					request.getSession().setAttribute(AS_ERROR, formEdu.getErrors());
				}
				break;

			case "Picture":
				UpdatePicture formPic = new UpdatePicture(userDao);
				formPic.addPicture(request, response);
				break;

			default:
				break;
			}
		}

		// Update the user of the session
		try {
			ConnectionTools.AlterConnection(request).RefreshUser();
		} catch (ConnectionException e) {
			// user not connected
		}

		// returns
		request.getSession().setAttribute(AS_RESULT, messagesMap);
		response.sendRedirect(request.getContextPath() + VUE_DEFAULT);
	}

}
