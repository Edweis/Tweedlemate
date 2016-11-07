package com.tm.validators;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.tm.dao.UserDAO;

@ManagedBean
@RequestScoped
public class EmailExistenceValidator implements Validator {

	private static final String EMAIL_ALREADY_EXISTS = "This mail address is already used";

	@EJB
	private UserDAO userDao;

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		String email = (String) value;
		if (userDao.findFromEmail(email) != null) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, EMAIL_ALREADY_EXISTS, null));
		}

	}

}
