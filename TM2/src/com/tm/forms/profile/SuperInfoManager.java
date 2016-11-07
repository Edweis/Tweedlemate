package com.tm.forms.profile;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import com.tm.dao.CRUDimpl;
import com.tm.entities.SuperEntity;
import com.tm.entities.User;

/**
 * Super class of all forms in the profile page.<br/>
 * The constructor of subclasses should call the constructor if
 * <code>SuperInfoManager</code> with the class name in parameter for instance :
 * <code>
 * public EntityDAO(){
 * 		super(Entity.class);
 * }</code>
 * 
 * @author François Rullière
 *
 */
public abstract class SuperInfoManager<T extends SuperEntity> {

	protected static final String CURRENT_PAGE = "profile.xhtml";
	protected static String MSG_ERROR = "Error while editing, it seems you try to modify something that does not belongs to you ! :o";
	protected static String MSG_ADD_SUCCESS = "Entity added with success";
	protected static String MSG_REMOVE = "Entity deleted with success";

	@ManagedProperty(value = "#{profileTools.connectedUser}")
	protected User connectedUser;
	@ManagedProperty(value = "#{profileTools.shownUser}")
	protected User shownUser;
	@EJB
	CRUDimpl crud;

	private Class<?> clazz;

	protected boolean enableEditor;
	protected T obj;
	protected Long editingExistingObj;

	public SuperInfoManager(Class<?> clazz) {
		this.clazz = clazz;
	}

	/**
	 * Initiates an empty object that will be eventually filled by the user and
	 * submitted
	 */
	protected abstract void createNewEmptyObject();

	/**
	 * Action of the user who want to create a new object
	 */
	public final void editNew() {
		createNewEmptyObject();
		enableEditor = true;
	}

	/**
	 * Action of the user who wants to edit an existing object
	 * 
	 * @param Id
	 */
	public final void edit(String Id) {
		T o = crud.getFromId(clazz, Long.parseLong(Id));
		if (!getListAllowObj().contains(o)) {
			launchMessage(MSG_ERROR);
			return;
		}
		editingExistingObj = o.getId();
		enableEditor = true;
		obj = o;
	}

	/**
	 * Cancel the creation on a new entity
	 */
	public final void cancel() {
		editingExistingObj = null;
		enableEditor = false;
	}

	/**
	 * Action of the user who wants to remove an existing object
	 * 
	 * @param Id
	 */
	public final String remove(String Id) {
		T o = crud.getFromId(clazz, Long.parseLong(Id));
		if (!getListAllowObj().contains(o)) {
			launchMessage(MSG_ERROR);
			return null;
		}

		removeObj(o);
		refreshUser();
		launchMessage(o + " has been deleted.");
		return reloadMe();
	}

	/**
	 * Remove an object from the allowed list
	 * 
	 * @param o
	 */
	protected void removeObj(T o) {
		crud.remove(o);
		getListAllowObj().remove(o);
	}

	public String add() {
		if (enableEditor) {
			addEntity();
			refreshUser();
			enableEditor = false;
			launchMessage(MSG_ADD_SUCCESS);
		} else {
			launchMessage(MSG_ERROR);
		}
		return reloadMe();
	}

	private void addEntity() {
		crud.create(obj);
		getListAllowObj().add(obj);
		if (editingExistingObj != null) {
			remove(crud.getFromId(clazz, editingExistingObj));
		}

	}

	/**
	 * Update the information of the user, this should be done after any
	 * modification in order to display these information when the page is
	 * refresh
	 */
	protected void refreshUser() {
		crud.merge(connectedUser);
	}

	/**
	 * return the current page to refresh
	 * 
	 * @return
	 */
	protected String reloadMe() {
		return CURRENT_PAGE + "?faces-redirect=true&id=" + connectedUser.getId().toString();
	}

	/**
	 * Give a message to be displayed on the front end.
	 * 
	 * @param message
	 */
	protected void launchMessage(String message) {
		FacesMessage m = new FacesMessage(message);
		FacesContext.getCurrentInstance().addMessage(null, m);
	}

	/**
	 * Return the list of all objects that should be displayed on the
	 * profile.<br/>
	 * For example : <br/>
	 * <code>
	 * &#64;Override
	 * 	protected List<Education> getListAllowObj() {
	 * 		return connectedUser.getMyEducation();
	 * 	}
	 * </code>
	 * 
	 * @return
	 */
	protected abstract List<T> getListAllowObj();

	public User getConnectedUser() {
		return connectedUser;
	}

	public void setConnectedUser(User connectedUser) {
		this.connectedUser = connectedUser;
	}

	public User getShownUser() {
		return shownUser;
	}

	public void setShownUser(User shownUser) {
		this.shownUser = shownUser;
	}

	public CRUDimpl getCrud() {
		return crud;
	}

	public void setCrud(CRUDimpl crud) {
		this.crud = crud;
	}

	public boolean isEnableEditor() {
		return enableEditor;
	}

	public void setEnableEditor(boolean enableEditor) {
		this.enableEditor = enableEditor;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	public Long getEditingExistingObj() {
		return editingExistingObj;
	}

	public void setEditingExistingObj(Long editingExistingObj) {
		this.editingExistingObj = editingExistingObj;
	}

}
