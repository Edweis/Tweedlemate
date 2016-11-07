package com.tm.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "passwordMatchValidator")
public class PasswordMatchValidator implements Validator {

	private static final String CHAMP_MOT_DE_PASSE = "passwordAtt";
	private static final String MOTS_DE_PASSE_DIFFERENTS = "Password and confirmation should be the same";

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		UIInput passwordComponent = (UIInput) component.getAttributes().get(CHAMP_MOT_DE_PASSE);

		String password = (String) passwordComponent.getValue();
		String confirmation = (String) value;

		if (confirmation != null && !confirmation.equals(password)) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, MOTS_DE_PASSE_DIFFERENTS, null));
		}
	}
}