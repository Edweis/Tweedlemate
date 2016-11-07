package com.tm.forms;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;

import com.tm.dao.UserDAO;
import com.tm.entities.User;
import com.tm.tools.FaceUtil;

@ManagedBean
@SessionScoped
public class Login implements Serializable {

	private static final long serialVersionUID = 1L;

	private User user = new User();
	/**
	 * true if the user is connected, false instead
	 */
	private boolean is = false;

	@EJB
	private UserDAO userDao;

	public String connect() {
		String email = user.getEmail();
		String password = user.getPassword();

		String redirect = null; // null==same page
		FacesMessage message;
		try {
			user = userDao.getUser(email, password);
			message = new FacesMessage("Succès de connection :) !");
			setIs(true);
			redirect = "/home.xhtml";
		} catch (NoResultException e) {
			user = null;
			message = new FacesMessage("Echec de connection :( !");
		}

		FacesContext.getCurrentInstance().addMessage(null, message);
		return redirect;
	}

	public String disconnect() {
		FaceUtil.setSessionUser(null);
		user = new User();
		setIs(false);
		return "/home.xhtml";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isIs() {
		return is;
	}

	public void setIs(boolean is) {
		this.is = is;
	}

}
