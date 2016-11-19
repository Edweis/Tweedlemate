package com.tm.pages;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.tm.dao.CRUDint;
import com.tm.dao.UserDAO;
import com.tm.entities.Contact;
import com.tm.entities.User;

@ManagedBean
@ViewScoped
public class ProfileTools {

	protected static final String CURRENT_PAGE = "profile.xhtml";
	protected static final String ERROR_MESSAGE_ACCESS_DENIED = "Error you try to modify something you haven't access !";

	@ManagedProperty(value = "#{login.user}")
	private User connectedUser;

	// Open variables
	private User shownUser;
	private boolean enableIT;
	private boolean enableEditContact;
	private Contact contact;
	private UploadedFile file;

	// private
	@EJB
	private CRUDint crud;
	@EJB
	private UserDAO userDao;

	@PostConstruct
	public void init() {
		enableIT = false;
		enableEditContact = false;
		contact = new Contact();
	}

	// *** Contacts

	public void addContact() {
		connectedUser.addContact(contact);
		crud.create(contact);
		saveUser();
		contact = new Contact();
	}

	public void deleteContact(String contactId) {
		Contact c = crud.getFromId(Contact.class, Long.parseLong(contactId));
		if (connectedUser.getMyContactsInfo().contains(c)) {
			crud.remove(c);
			connectedUser.removeContact(c);
			saveUser();
		} else {
			launchMessage(ERROR_MESSAGE_ACCESS_DENIED);
		}
	}

	// *** Picture

	/**
	 * Event that change the picture with AJAX
	 * 
	 * @param event
	 */
	@Deprecated()
	public void changePic(FileUploadEvent event) {

		try {
			saveFile(event.getFile(), connectedUser.getPictureFolder(), connectedUser.getPicNameWithoutExtension());
			connectedUser.setPictureExtension(FilenameUtils.getExtension(event.getFile().getFileName()));
			crud.merge(connectedUser);

			launchMessage("Succesful", event.getFile().getFileName() + " is uploaded.");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Action that change the picture with ajax
	 * 
	 * @param event
	 */
	public void changePic2() {

		try {
			saveFile(file, connectedUser.getPictureFolder(), connectedUser.getPicNameWithoutExtension());
			connectedUser.setPictureExtension(FilenameUtils.getExtension(file.getFileName()));
			crud.merge(connectedUser);

			launchMessage("Succesful", file.getFileName() + " is uploaded.");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Save the profile picture on the disk
	 * 
	 * @param myFile
	 * @param picFolder
	 * @param picFileName
	 * @throws IOException
	 *             Witch occurs when there is an error in copying the file from
	 *             the temp directory to its destination
	 */
	private void saveFile(UploadedFile myFile, String picFolder, String picFileName) throws IOException {

		File folder = new File(picFolder);
		String filename = picFileName;// FilenameUtils.getBaseName(myFile.getFileName());
		String extension = FilenameUtils.getExtension(myFile.getFileName());
		File file = new File(folder, filename + "." + extension);

		try (InputStream input = myFile.getInputstream()) {
			Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}

		System.out.println("Uploaded file successfully saved in " + file);
	}

	public String resetPic() {
		connectedUser.setPictureExtension(null);
		saveUser();
		return reloadMe();
	}

	// *** Introduction text

	/**
	 * Action that validate the modifications on introduction text
	 */
	public void changeIT() {
		saveUser();
		enableIT = false;
	}

	/**
	 * Action that cancel the edition of introduction text and delete the
	 * changes
	 */
	public void cancelIT() {
		enableIT = false;
	}

	/**
	 * Action that enable the edition of introduction text
	 */
	public void enableEditIT() {
		enableIT = true;
	}

	// *** Others

	/**
	 * Redirect to this same page to refresh
	 * 
	 * @return
	 */
	private String reloadMe() {
		return CURRENT_PAGE + "?faces-redirect=true&id=" + connectedUser.getId().toString();
	}

	/**
	 * Save the connectedUser modification and refresh the shownUser
	 */
	private void saveUser() {
		crud.merge(connectedUser);
		shownUser = connectedUser;
	}

	/**
	 * Send a qick message to the front end
	 * 
	 * @param summary
	 */
	private void launchMessage(String summary) {
		launchMessage(null, summary, null);
	}

	/**
	 * Send a message to the front end
	 * 
	 * @param summary
	 * @param details
	 */
	private void launchMessage(String summary, String details) {
		launchMessage(null, summary, details);
	}

	/**
	 * Send a message to the front end with a specifique clientId
	 * 
	 * @param clientId
	 * @param summary
	 * @param details
	 */
	private void launchMessage(String clientId, String summary, String details) {
		FacesMessage message = new FacesMessage(summary, details);
		FacesContext.getCurrentInstance().addMessage(clientId, message);
	}
	// *** getters and setters

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

	public boolean isEnableIT() {
		return enableIT;
	}

	public void setEnableIT(boolean enableIT) {
		this.enableIT = enableIT;
	}

	public boolean isEnableEditContact() {
		return enableEditContact;
	}

	public void setEnableEditContact(boolean enableEditContact) {
		this.enableEditContact = enableEditContact;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
}
