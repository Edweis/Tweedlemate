package com.tm.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tm.dao.ConnectionException;
import com.tm.dao.UserDAO;
import com.tm.entities.User;
import com.tm.forms.UserRegistrationForm;

/**
 * Servlet implementation class Inscription
 */
@WebServlet(urlPatterns = "/Inscription")
public class Inscription extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String A_FORM_RESPONSE = "form";

	private static final String VUE_SUCESS = "/Profile";
	private static final String VUE_FAIL = "/WEB-INF/pages/inscription.jsp";

	private static final String PS_USER = "connectedUser";

	@EJB
	private UserDAO userdao;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Nothing happened
		this.getServletContext().getRequestDispatcher(VUE_FAIL).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserRegistrationForm form = new UserRegistrationForm(userdao);
		User user = form.createUser(request);

		if (form.getErrors().isEmpty()) {
			// Connection
			try {
				request.getSession().setAttribute(PS_USER, userdao.Connect(user.getEmail(), user.getPassword()));
			} catch (ConnectionException e) {
				// The connection should not go wrong if the insert went good
			}

			response.sendRedirect(request.getContextPath() + VUE_SUCESS);
		} else {
			// NOK
			request.setAttribute(A_FORM_RESPONSE, form);
			this.getServletContext().getRequestDispatcher(VUE_FAIL).forward(request, response);
		}

	}

}
