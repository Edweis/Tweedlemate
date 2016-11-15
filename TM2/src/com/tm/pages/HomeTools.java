package com.tm.pages;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.tm.dao.CRUDint;
import com.tm.entities.User;

@ManagedBean
@ViewScoped
public class HomeTools {

	@EJB
	CRUDint crud;

	public List<User> getFrontPageUsers() {
		return crud.getAll(User.class);
	}
}
