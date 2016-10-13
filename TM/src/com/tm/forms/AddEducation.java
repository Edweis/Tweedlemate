package com.tm.forms;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.tm.dao.EducationDAO;
import com.tm.dao.SchoolDAO;
import com.tm.entities.Education;

@ManagedBean
@ViewScoped
public class AddEducation implements Serializable {

	private static final long serialVersionUID = 1L;
	private Education edu;

	@EJB
	private EducationDAO educationDao;
	@EJB
	private SchoolDAO schoolDao;

	public AddEducation() {
		edu = new Education();
	}

	public void add() {
		schoolDao.create(edu.getSchool());
		educationDao.create(edu);

		FacesMessage message = new FacesMessage("Succès de l'inscription !");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public Education getEdu() {
		return edu;
	}

	public void setEdu(Education edu) {
		this.edu = edu;
	}

}
