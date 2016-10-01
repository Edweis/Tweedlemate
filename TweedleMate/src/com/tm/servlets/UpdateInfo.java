package com.tm.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tm.forms.UpdateEducationForm;

@WebServlet("/UpdateInfo/*")
public class UpdateInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String VUE_DEFAULT = "/Profil";

	private static final String AS_ERROR = "updateErrors";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.sendRedirect(request.getContextPath() + VUE_DEFAULT);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Get the previous page, but if it doesn't make sense g to vue_default
		String p = request.getRequestURI().replace(request.getContextPath() + "/", "");
		String[] position = p.split("/");

		if (position.length > 0) {
			if ("Education".equals(position[1])) {
				UpdateEducationForm form = new UpdateEducationForm();
				form.addEducation(request, response);

				if (form.getErrors().isEmpty()) {

				} else {
					request.getSession().setAttribute(AS_ERROR, form.getErrors());
				}
			}
		}

		response.sendRedirect(request.getContextPath() + VUE_DEFAULT);

	}

}
