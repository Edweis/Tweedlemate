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

import com.tm.dao.CountryDAO;
import com.tm.dao.EducationDAO;
import com.tm.dao.SchoolDAO;
import com.tm.dao.UserDAO;
import com.tm.forms.UpdateEducationForm;
import com.tm.forms.UpdateForm;
import com.tm.forms.UpdatePicture;
import com.tm.tools.ConnectionLanguageException;
import com.tm.tools.ConnectionTools;

@WebServlet("/UpdateInfo/*")
@MultipartConfig
public class UpdateInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String VUE_DEFAULT = "/Profile";

	private static final String AS_ERROR = "updateErrors";
	private static final String AS_RESULT = "updateMessages";

	private Map<String, String> resultMap = new HashMap<String, String>();

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
		UpdateForm form = null;
		if (position.length > 0) {
			switch (position[1]) {

			case "Education":
				form = new UpdateEducationForm(educationDao, schoolDao, countryDao);
				break;
			case "Picture":
				form = new UpdatePicture(userDao);
				break;
			default:
				// If we don't understand, return to the default page
				response.sendRedirect(request.getContextPath() + VUE_DEFAULT);
				return;
			}

			// From handling
			form.add(request);

			if (form.isSuccess()) {
				resultMap.put(form.labelFieldSucess, form.successMessage);
			} else {
				request.getSession().setAttribute(AS_ERROR, form.getErrors());
			}

			// Refresh User to update informations
			try {
				ConnectionTools.AlterConnection(userDao, request).RefreshUser();
			} catch (ConnectionLanguageException e) {
				// should never happen becuse the user is connected
				e.printStackTrace();
			}
		}

		// returns
		request.getSession().setAttribute(AS_RESULT, resultMap);
		response.sendRedirect(request.getContextPath() + VUE_DEFAULT);
	}

}
