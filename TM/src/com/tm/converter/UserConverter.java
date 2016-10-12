package com.tm.converter;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.tm.dao.UserDAO;
import com.tm.entities.User;

@ManagedBean
@ViewScoped
public class UserConverter implements Converter {

	@EJB
	private UserDAO userDao;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		User u = userDao.getFromId(Long.parseLong(value));
		return u;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		User u = (User) obj;
		return u.getId().toString();
	}

	public Object getAsObject(String value) {
		if (value == null) {
			return null;
		}
		if (value.equals("")) {
			return null;
		}

		return getAsObject(null, null, value);

	}

}
