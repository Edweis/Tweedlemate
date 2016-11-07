package com.tm.forms.profile;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.tm.entities.Funding;
import com.tm.entities.Scholarship;

@ManagedBean
@ViewScoped
public class ScholarshipManager extends SuperInfoManager<Funding> {

	Funding fun;

	public ScholarshipManager() {
		super(Funding.class);
		fun = this.obj;
	}

	@Override
	protected void createNewEmptyObject() {
		obj = new Funding();
		obj.setScholarship(new Scholarship());
		fun = this.obj;
	}

	@Override
	protected List<Funding> getListAllowObj() {
		return connectedUser.getMyFunding();
	}

	public Funding getFun() {
		return fun;
	}

	public void setFun(Funding fun) {
		this.fun = fun;
	}

}
