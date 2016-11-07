package com.tm.pages;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.tm.entities.User;

@ManagedBean
@ViewScoped
public class AppointmentTools {

	private User shownUser;

	public User getShownUser() {
		return shownUser;
	}

	public void setShownUser(User shownUser) {
		this.shownUser = shownUser;
	}

}
