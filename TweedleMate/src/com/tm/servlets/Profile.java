package com.tm.servlets;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tm.dao.CountryDAO;
import com.tm.dao.UserDAO;
import com.tm.entities.Country;
import com.tm.entities.User;
import com.tm.tools.ConnectionTools;

/**
 * Servlet implementation class Profile
 */
@WebServlet(urlPatterns = { "/Profile", "/Profile/", "/Profile/*" })
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String VUE_PERSO = "/WEB-INF/pages/app/myProfile.jsp";
	private static final String VUE = "/WEB-INF/pages/profile.jsp";
	private static final String VUE_ERROR = "/WEB-INF/pages/errors/profileError.jsp";

	private static final String A_LISTCOUNTRIES = "countries";
	private static final String A_SHOWN_USER = "shownUser";

	@EJB
	private CountryDAO countryDao;
	@EJB
	private UserDAO userDao;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String uri = request.getRequestURI();
		User u = ConnectionTools.getUserConnected(request);

		if (uri.endsWith("/Profile") || uri.endsWith("/Profile/")) {
			// Thanks to the LoginFilter, we can assume the user is connected
			response.sendRedirect(request.getContextPath() + u.getProfileURI());
		} else {
			if (u != null) {
				// if connected
				if (uri.endsWith(u.getProfileURI())) {
					// His own profile
					showMyProfile(request, response);
				} else {
					// He is looking for another user's profile
					showSomeoneProfile(response, request, uri);
				}
			} else {
				showSomeoneProfile(response, request, uri);
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	private void showMyProfile(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		// Fetch countries
		List<Country> allCountries = countryDao.getList();
		request.setAttribute(A_LISTCOUNTRIES, allCountries);

		this.getServletContext().getRequestDispatcher(VUE_PERSO).forward(request, response);
	}

	private void showSomeoneProfile(HttpServletResponse response, HttpServletRequest request, String uri)
			throws ServletException, IOException {

		User u = userDao.findFromURI(uri);
		if (u != null) {
			if (uri.endsWith(u.getProfileURI())) {
				request.setAttribute(A_SHOWN_USER, u);

				this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
			} else {
				// If the firstname is incorrect, we redirect
				response.sendRedirect(request.getContextPath() + u.getProfileURI());
			}
		} else {
			this.getServletContext().getRequestDispatcher(VUE_ERROR).forward(request, response);
		}
	}

}
