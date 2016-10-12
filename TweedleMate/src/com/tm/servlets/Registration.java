package com.tm.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tm.dao.UserDAO;
import com.tm.forms.UserRegistrationForm;
import com.tm.tools.ConnectionTools;

/**
 * Servlet implementation class Inscription
 */
@WebServlet(urlPatterns = "/Registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String A_FORM_RESPONSE = "form";

	private static final String VUE_SUCESS = "/Home";
	private static final String VUE_FAIL = "/WEB-INF/pages/registration.jsp";

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
		form.add(request);

		if (form.isSuccess()) {
			ConnectionTools.AlterConnection(request).ConnectionQuery(form.getCreatedUser());
			response.sendRedirect(request.getContextPath() + VUE_SUCESS);
		} else {
			// NOK
			request.setAttribute(A_FORM_RESPONSE, form);
			this.getServletContext().getRequestDispatcher(VUE_FAIL).forward(request, response);
		}

	}

}
