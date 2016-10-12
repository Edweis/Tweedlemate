package com.tm.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tm.dao.UserDAO;
import com.tm.entities.User;
import com.tm.tools.ConnectionTools;

/**
 * Servlet implementation class Appointment
 */
@WebServlet(urlPatterns = { "/Appointment", "/Appointment/", "/Appointment/*" })
public class Appointment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String VUE_PERSO = "/WEB-INF/pages/app/myAppointments.jsp";
	private static final String VUE = "/WEB-INF/pages/createAppointments.jsp";
	private static final String VUE_ERROR = "/WEB-INF/pages/errors/appointmentsError.jsp";

	private static final String A_SHOWN_USER = "shownUser";

	@EJB
	private UserDAO userDao;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String uri = request.getRequestURI();
		User u = ConnectionTools.getUserConnected(request);

		if (uri.endsWith("/Appointment") || uri.endsWith("/Appointment/")) {
			// Thanks to the LoginFilter, we can assume the user is connected
			response.sendRedirect(request.getContextPath() + u.getAppointmentURI());
		} else {
			if (u != null) {
				// if connected
				if (uri.endsWith(u.getAppointmentURI())) {
					// My appointments
					showMyAppointments(request, response);
				} else {
					// Create an appointment with someone else
					createAppointment(response, request, uri);
				}
			} else {
				createAppointment(response, request, uri);
			}
		}
	}

	private void showMyAppointments(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VUE_PERSO).forward(request, response);
	}

	private void createAppointment(HttpServletResponse response, HttpServletRequest request, String uri)
			throws ServletException, IOException {

		User u = userDao.findFromURI(uri);
		if (u != null) {
			if (uri.endsWith(u.getAppointmentURI())) {
				request.setAttribute(A_SHOWN_USER, u);

				this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
			} else {
				// If the URI is not complete we redirect
				response.sendRedirect(request.getContextPath() + u.getAppointmentURI());
			}
		} else {
			this.getServletContext().getRequestDispatcher(VUE_ERROR).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
