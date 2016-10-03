package com.tm.tools;

import javax.servlet.http.HttpServletRequest;

import com.tm.dao.UserDAO;
import com.tm.entities.User;

public class ConnectionTools {

	private static final String AS_USER = "connectedUser";

	public static User getUserConnected(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(AS_USER);
	}

	public static AlterConnection AlterConnection(UserDAO userDao, HttpServletRequest request) {
		return new AlterConnection(userDao, request);
	}

	public static AlterConnection AlterConnection(HttpServletRequest request) {
		return new AlterConnection(request);
	}

}
