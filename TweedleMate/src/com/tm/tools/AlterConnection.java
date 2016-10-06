package com.tm.tools;

import javax.servlet.http.HttpServletRequest;

import com.tm.dao.ConnectionException;
import com.tm.dao.UserDAO;
import com.tm.entities.User;

public class AlterConnection {

	static UserDAO userDao;
	static HttpServletRequest request;
	private static final String AS_USER = "connectedUser";

	public AlterConnection(UserDAO userDao, HttpServletRequest request) {
		AlterConnection.userDao = userDao;
		AlterConnection.request = request;
	}

	public AlterConnection(HttpServletRequest request) {
		AlterConnection.request = request;
		AlterConnection.userDao = null;
	}

	/**
	 * Put in the database informations that has been changed.
	 * 
	 * @throws ConnectionException
	 *             When there is an error in the User connection
	 * @throws ConnectionLanguageException
	 *             when the userDAO is not specified in the AlterConnection
	 */
	public void RefreshUser() throws ConnectionLanguageException {
		User user = (User) request.getSession().getAttribute(AS_USER);
		String email = user.getEmail();
		String password = user.getPassword();

		if (userDao != null) {
			User newUser = null;
			try {
				newUser = userDao.Connect(email, password);
			} catch (ConnectionException e) {
				throw new ConnectionLanguageException("Refresh user is impossible if no user is connected.");
			}
			request.getSession().setAttribute(AS_USER, newUser);
		} else {
			throw new ConnectionLanguageException("You should specify the userDAO in AlterConnection");
		}
	}

	public void ConnectionQuery(String email, String password) throws ConnectionException {
		User user = userDao.Connect(email, password);
		if (userDao == null) {
			throw new ConnectionException("You have to input the user DAO");
		}
		request.getSession().setAttribute(AS_USER, user);
	}

	public void disconnect() {
		request.getSession().removeAttribute(AS_USER);
	}
}
