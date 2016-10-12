package com.tm.tools;

import java.io.IOException;

import javax.faces.context.FacesContext;

import com.tm.entities.User;

public class FaceUtil {

	private static final String USER_ATTRIBUTE = "connectedUser";

	/**
	 * Get an object in the session scope map
	 * 
	 * @param variable
	 *            name
	 * @return the session object as Object
	 */
	public static Object getSessionMapValue(String key) {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
	}

	/**
	 * Set an object in the session scope map
	 * 
	 * @param variable
	 *            name
	 * @param object
	 */
	public static void setSessionMapValue(String key, Object value) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
	}

	/**
	 * Set the connected user
	 * 
	 * @param user
	 * @throws IOException
	 */
	public static void setSessionUser(User user) {
		if (user != null) {
			setSessionMapValue(USER_ATTRIBUTE, user);
		} else {
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/home.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Get the connected user
	 * 
	 * @return
	 */
	public static User getSessionUser() {
		return (User) getSessionMapValue(USER_ATTRIBUTE);
	}

	/**
	 * Get an object in the application scope map
	 * 
	 * @param variable
	 *            name
	 * @param object
	 */
	public static Object getApplicationMapValue(String key) {
		return FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get(key);
	}

	/**
	 * Set an object in the application scope map
	 * 
	 * @param variable
	 *            name
	 * @param object
	 */
	public static void setApplicationMapValue(String key, Object value) {
		FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put(key, value);
	}
}
