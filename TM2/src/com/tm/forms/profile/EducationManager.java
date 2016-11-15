package com.tm.forms.profile;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.tm.dao.SchoolDAO;
import com.tm.entities.Country;
import com.tm.entities.Education;
import com.tm.entities.School;

@ManagedBean
@ViewScoped
public class EducationManager extends SuperInfoManager<Education> {

	// front
	private Education edu;
	@EJB
	private SchoolDAO schoolDao;

	public EducationManager() {
		super(Education.class);
		edu = this.obj;
	}

	@Override
	protected void createNewEmptyObject() {
		obj = new Education();
		obj.setSchool(new School());
		obj.getSchool().setCountry(new Country());
		edu = this.obj;
	}

	@Override
	protected List<Education> getListAllowObj() {
		return connectedUser.getMyEducation();
	}

	public Education getEdu() {
		return edu;
	}

	public void setEdu(Education edu) {
		this.edu = edu;
	}

}
