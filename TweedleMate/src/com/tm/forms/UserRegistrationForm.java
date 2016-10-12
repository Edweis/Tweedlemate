package com.tm.forms;

import javax.servlet.ServletRequest;

import com.tm.dao.UserDAO;
import com.tm.entities.User;

public final class UserRegistrationForm extends UpdateForm {
	private static final String F_FIRSTNAME = "firstName";
	private static final String F_EMAIL = "email";
	private static final String F_PICTUREPATH = "picturePath";
	private static final String F_PASSWORD = "password";
	private static final String F_PASSWORD_VERIF = "passwordVerification";
	private static final String F_INTROUCTIONTEXT = "introdutionText";
	private static final String F_APPOINTMENTPRICE = "appointmentPrice";

	private String resultat;
	private UserDAO userDao;
	private String firstname;
	private String email;
	private String picturePath;
	private String password;
	private String passwordVerif;
	private String introductionText;
	private String appointmentPrice;

	private User createdUser;

	public UserRegistrationForm(UserDAO userDao) {
		this.userDao = userDao;
	}

	@Override
	protected void getAllParameters(ServletRequest request) {
		firstname = request.getParameter(F_FIRSTNAME);
		email = request.getParameter(F_EMAIL);
		picturePath = request.getParameter(F_PICTUREPATH);
		password = request.getParameter(F_PASSWORD);
		passwordVerif = request.getParameter(F_PASSWORD_VERIF);
		introductionText = request.getParameter(F_INTROUCTIONTEXT);
		appointmentPrice = request.getParameter(F_APPOINTMENTPRICE);
	}

	@Override
	protected void checkData() {
		checkFirstName(firstname);
		checkEmail(email);
		checkPassword(password, passwordVerif);
		checkIntroductionText(introductionText);
		checkAppointmentPrice(appointmentPrice);

	}

	@Override
	protected void persist(User connectedUser) {
		User newUser = new User();

		newUser.setFirstName(firstname);
		newUser.setEmail(email);
		newUser.setPassword(password);
		newUser.setIntroductionText(introductionText);
		newUser.setAppointmentPrice(appointmentPrice);

		userDao.create(newUser);
		createdUser = newUser;
	}

	/**
	 * No checks
	 * 
	 * @param appointmentPrice
	 * @param user
	 */
	private void checkAppointmentPrice(String appointmentPrice) {
		// TODO Check appointment
	}

	/**
	 * No checks
	 * 
	 * @param introductionText
	 * @param user
	 */
	private void checkIntroductionText(String introductionText) {
		// TODO check Introdution text
	}

	private void checkPassword(String password, String passwordVerif) {
		if (password != null) {
			if (password.length() >= 5) {

				if (passwordVerif != null) {
					if (passwordVerif.equals(password)) {
						// Passwords OK
					} else {
						addError(F_PASSWORD_VERIF, "Passwords don't match.");
					}
				} else {
					addError(F_PASSWORD_VERIF, "Please insert the password twice.");
				}

			} else {
				addError(F_PASSWORD, "Your password should be at least 5 character long.");
			}
		} else {
			addError(F_PASSWORD, "Please insert a password.");
		}
	}

	private void checkFirstName(String firstname) {
		if (firstname != null) {
			if (firstname.matches("^[a-z0-9_-]{2,20}$")) {
				addError(F_FIRSTNAME,
						"You're first name can only contains letters, numbers, - and _. It should have more than 2 characters");
			}
		}
	}

	private void checkEmail(String email) {

		if (email != null && !email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
			addError(F_EMAIL, "Please enter a valide email address");
		} else {
			if (userDao.findFromEmail(email) != null) {
				addError(F_EMAIL,
						"It appears that this email is already register, if you have any problem regarding registration please contact us !");
			}
		}
	}

	public User getCreatedUser() {
		return createdUser;

	}

}