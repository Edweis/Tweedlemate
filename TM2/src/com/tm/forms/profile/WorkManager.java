package com.tm.forms.profile;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.tm.entities.Country;
import com.tm.entities.Education;
import com.tm.entities.Work;
import com.tm.entities.WorkCursus;

@ManagedBean
@ViewScoped
public class WorkManager extends SuperInfoManager<WorkCursus> {

	// front
	private WorkCursus wc;

	public WorkManager() {
		super(Education.class);
		wc = this.obj;
	}

	@Override
	protected void createNewEmptyObject() {
		obj = new WorkCursus();
		obj.setWork(new Work());
		obj.getWork().setCountry(new Country());
		wc = this.obj;
	}

	@Override
	protected List<WorkCursus> getListAllowObj() {
		return connectedUser.getMyWorkcursus();
	}

	public WorkCursus getWc() {
		return wc;
	}

	public void setWc(WorkCursus wc) {
		this.wc = wc;
	}
}
