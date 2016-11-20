package com.tm.converter;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * Convert a search URL like </br>
 * <tt>para+meter+1,parma2</tt> </br>
 * to an <tt>ArrayList<String></tt></br>
 * <tt>{para meter 1, param2}</tt>
 * 
 * @author François Rullière
 *
 */
@ManagedBean
@ViewScoped
public class SearchURLConverter implements Converter {

	/**
	 * Decode the URL to a <tt>String</tt> tag input : String output :
	 * ArayList<String>
	 */
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return new ArrayList<String>();
		}
		List<String> res = new ArrayList<String>();
		for (String i : value.split(",")) {
			res.add(i.replace("+", " "));
		}
		return res;// Arraylist
	}

	/**
	 * Code the URL from <tt>String</tt> tag input : ArrayList<String> output :
	 * String
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		if (obj == null) {
			return "";
		}
		if (obj instanceof List<?>) {
			ArrayList<String> a = (ArrayList<String>) obj;
			if (a.size() == 0) {
				return "";
			}
			String res = "";
			for (String s : a) {
				res = res + "," + s;
			}
			return res.substring(0, res.length() - 1);

		} else {
			throw new ConverterException(
					new FacesMessage(obj + " is not a valid parameter list, it should be a String"));
		}

	}

	public String getAsString(List<String> listParameter) {
		return getAsString(null, null, listParameter);
	}

}
