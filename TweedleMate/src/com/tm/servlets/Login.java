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

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String P_EMAIL = "email";
	private static final String P_PASS = "password";

	private static final String AS_USER = "connectedUser";

	private static final String AS_ERROR_MESSAGE = "connError";
	private static final String AS_EMAIL = "connEmail";

	private static final String VUE_DEFAULT = "/";
	private static final String VUE_REDIRECT = "/Profile";

	@EJB
	private UserDAO userdao;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.sendRedirect(VUE_DEFAULT);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter(P_EMAIL);
		String pass = request.getParameter(P_PASS);

		try {
			User u = userdao.Connect(email, pass);
			request.getSession().setAttribute(AS_USER, u);

		} catch (ConnectionException e) {
			request.getSession().setAttribute(AS_EMAIL, email);
			request.getSession().setAttribute(AS_ERROR_MESSAGE, e.getMessage());
		}

		response.sendRedirect(request.getContextPath() + VUE_REDIRECT);

	}

}
