package com.tm.forms;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.joda.time.DateTime;

import com.tm.dao.UserDAO;
import com.tm.entities.User;

@ManagedBean
@ViewScoped
public class UserRegistration implements Serializable {

	private static final long serialVersionUID = 1L;
	private User user;

	@EJB
	private UserDAO userDao;

	private final static String REDIRECTION_PAGE = "registration.xhtml";

	public UserRegistration() {
		user = new User();
	}

	public String register() {
		initiateRegistrationDate();
		userDao.create(user);

		FacesMessage message = new FacesMessage("Succès de l'inscription !");
		FacesContext.getCurrentInstance().addMessage(null, message);

		return REDIRECTION_PAGE;
	}

	public User getUser() {
		return user;
	}

	private void initiateRegistrationDate() {
		user.setDateRegistration(DateTime.now());
	}
}