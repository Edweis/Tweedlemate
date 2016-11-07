package com.tm.pages;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.tm.dao.CRUDimpl;
import com.tm.entities.User;

@ManagedBean
@ViewScoped
public class HomeTools {

	@EJB
	CRUDimpl crud;

	public List<User> getFrontPageUsers() {
		return crud.getAll(User.class);
	}
}
