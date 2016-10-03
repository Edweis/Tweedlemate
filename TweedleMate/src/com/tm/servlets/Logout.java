package com.tm.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tm.tools.ConnectionTools;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		disconnect(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		disconnect(request, response);
	}

	private void disconnect(HttpServletRequest request, HttpServletResponse response) throws IOException {

		ConnectionTools.AlterConnection(request).disconnect();

		String previousURL = request.getHeader("Referer");

		// if the previous URL was not valid let's go to the home page
		try {
			response.sendRedirect(previousURL);
		} catch (IOException e) {
			response.sendRedirect(request.getContextPath());
		}

	}
}
