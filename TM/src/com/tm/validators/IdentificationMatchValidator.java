package com.tm.validators;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.tm.dao.UserDAO;

@ManagedBean
@RequestScoped
public class IdentificationMatchValidator implements Validator {

	private static final String F_EMAIL = "emailAtt";
	private static final String ID_INCORRECT = "Identification incorrect";

	@EJB
	private UserDAO userDao;

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		UIInput passwordComponent = (UIInput) component.getAttributes().get(F_EMAIL);

		String email = (String) passwordComponent.getValue();
		String password = (String) value;

		if (!userDao.checkIdentification(email, password)) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ID_INCORRECT, null));
		}

	}

}
