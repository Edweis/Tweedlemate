package com.tm.forms.profile;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.tm.entities.Experience;

@ManagedBean
@ViewScoped
public class ExperienceManager extends SuperInfoManager<Experience> {

	Experience exp;

	public ExperienceManager() {
		super(Experience.class);
		exp = this.obj;
	}

	@Override
	protected void createNewEmptyObject() {
		obj = new Experience();
		exp = this.obj;
	}

	@Override
	protected List<Experience> getListAllowObj() {
		return connectedUser.getMyExperience();
	}

	public Experience getExp() {
		return exp;
	}

	public void setExp(Experience exp) {
		this.exp = exp;
	}

}
