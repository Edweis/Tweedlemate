package com.tm.converter;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.tm.dao.CountryDAO;
import com.tm.entities.Country;

/**
 * Convert a coe3 country to a country and in return.
 * 
 * @author François Rullière
 *
 */
@ManagedBean
@ViewScoped
public class CountryConverter implements Converter {

	@EJB
	private CountryDAO countryDao;

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
		if (modelValue == null) {
			return "EMPTY";
		}
		if (modelValue instanceof Country) {
			return ((Country) modelValue).getCode3();
		} else {
			throw new ConverterException(new FacesMessage(modelValue + " is not a valid Country entity"));
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {

		if (submittedValue == null) {
			throw new ConverterException(new FacesMessage("Please use the form t insert a country."));
		}

		if (submittedValue == "EMPTY") {
			return null;
		}
		if (submittedValue.length() > 3) {
			return null;
		}

		Country res = (Country) countryDao.findFromCode3(submittedValue);
		if (res == null) {
			throw new ConverterException(new FacesMessage(submittedValue + " is not a valid Country code3"));
		} else {
			return res;
		}
	}
}
