package com.tm.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.tm.entities.User;
import com.tm.tools.ConnectionTools;

/**
 * Upper class for all forms that update the current user information
 * 
 * @author François Rullière
 *
 */
public abstract class UpdateForm {

	/**
	 * Message to display when the form has been send with success <br/>
	 * Should be override, null instead.
	 */
	public String successMessage = null;
	/**
	 * Label where sucessMessage should be displayed <br/>
	 * Should be override, null instead.
	 */
	public String labelFieldSucess = null;

	private Map<String, String> errors = new HashMap<String, String>();

	/**
	 * This function is going to : <br/>
	 * <ul>
	 * <li>Get All Parameters necessary to handle form's input</li>
	 * <li>Check Data</li>
	 * <li>Persist (write in the database/server) important data</li>
	 * </ul>
	 * 
	 * @param request
	 */
	public final void add(HttpServletRequest request) {

		getAllParameters(request);
		if (isFormConcerned(request)) {

			checkData();
			if (getErrors().isEmpty()) {
				persist(getConnectedUser(request));
			}

		}

	}

	/**
	 * Inform of the success of the form validation and persistence.
	 * 
	 * @return true if the form has been send successfully
	 */
	public final boolean isSuccess() {
		return this.getErrors().isEmpty();
	}

	/**
	 * Return a map of all errors in their field name mapped with the error
	 * message related.<br/>
	 * This map should be send back to the JSP form page to inform the user of
	 * the form validation errors.
	 * 
	 * @return Map(errorField, errorMessage)
	 */
	public final Map<String, String> getErrors() {
		return errors;
	}

	/**
	 * Add an error the the error map
	 * 
	 * @param field
	 * @param message
	 */
	protected final void addError(String field, String message) {
		errors.put(field, message);
	}

	/**
	 * Get all relevant parameters from request
	 * 
	 * @param request
	 */
	abstract protected void getAllParameters(ServletRequest request);

	/**
	 * Check input parameters
	 */
	abstract protected void checkData();

	/**
	 * Persist relevant data
	 * 
	 * @param connectedUser
	 */
	abstract protected void persist(User connectedUser);

	/**
	 * Return true if the user input enough information to consider the
	 * form.<br/>
	 * If not override, the form is considered
	 * 
	 * @param request
	 * @return
	 */
	public boolean isFormConcerned(ServletRequest request) {
		return true;
	};

	/**
	 * Get connected user in session.
	 * 
	 * @param request
	 * @return Connected user
	 */
	private final User getConnectedUser(HttpServletRequest request) {
		return ConnectionTools.getUserConnected(request);
	}

}
