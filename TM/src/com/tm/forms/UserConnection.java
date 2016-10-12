package com.tm.forms;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.tm.dao.UserDAO;
import com.tm.entities.User;
import com.tm.tools.FaceUtil;

@ManagedBean
@ViewScoped
public class UserConnection implements Serializable {

	private static final long serialVersionUID = 1L;
	private User user;
	@EJB
	private UserDAO userDao;

	public UserConnection() {
		user = new User();
	}

	public void connect() {
		String email = user.getEmail();
		String password = user.getPassword();

		User user = userDao.getUser(email, password);
		FaceUtil.setSessionUser(user);

		FacesMessage message = new FacesMessage("Succès de connection !");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void disconnect() {
		FaceUtil.setSessionUser(null);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
