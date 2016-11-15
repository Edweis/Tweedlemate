package com.tm.converter;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.tm.dao.CRUDint;
import com.tm.entities.User;

/**
 * Convert a User from his ID and in return
 * 
 * @author François Rullière
 *
 */
@ManagedBean
@ViewScoped
public class UserConverter implements Converter {

	@EJB
	private CRUDint crud;

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
		if (modelValue == null) {
			return "";
		}

		if (modelValue instanceof User) {
			return String.valueOf(((User) modelValue).getId());
		} else {
			throw new ConverterException(new FacesMessage(modelValue + "is not a valid User entity"));
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
		if (submittedValue == null || submittedValue.isEmpty()) {
			return null;
		}

		try {
			return crud.getFromId(User.class, Long.valueOf(submittedValue));
		} catch (NumberFormatException e) {
			throw new ConverterException(new FacesMessage(submittedValue + " is not a valid User ID", e.getMessage()));
		}
	}

}
