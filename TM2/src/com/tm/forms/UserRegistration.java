package com.tm.forms;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.joda.time.DateTime;

import com.tm.dao.CRUDint;
import com.tm.entities.Education;
import com.tm.entities.School;
import com.tm.entities.User;

@ManagedBean
@ViewScoped
public class UserRegistration implements Serializable {

	private static final long serialVersionUID = 1L;

	// Open variables
	private User user;
	private School school;

	@EJB
	private CRUDint crud;

	@PostConstruct
	public void init() {
		user = new User();
		school = new School();
	}

	public void register() {
		user.setDateRegistration(DateTime.now());
		crud.create(user);
		if (school.getName() != null) {
			Education edu = new Education();
			edu.setUser(user);
			edu.setSchool(school);
			crud.create(edu);
		}

		FacesMessage message = new FacesMessage("Succès de l'inscription !");
		FacesContext.getCurrentInstance().addMessage(null, message);

	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

}