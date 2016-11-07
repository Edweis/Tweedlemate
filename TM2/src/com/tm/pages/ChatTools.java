package com.tm.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.joda.time.DateTime;

import com.tm.dao.CRUDimpl;
import com.tm.dao.MessageDAO;
import com.tm.dao.RoomDAO;
import com.tm.entities.Chat;
import com.tm.entities.Message;
import com.tm.entities.Room;
import com.tm.entities.User;
import com.tm.wrappers.SetChats;
import com.tm.wrappers.SetRoom;

@ManagedBean
@ViewScoped
public class ChatTools {

	@ManagedProperty(value = "#{login.user}")
	private User connectedUser;
	@ManagedProperty(value = "#{appointmentTools.shownUser}")
	private User shownUser;

	// Closed variables
	private boolean areThereChats;

	// Open variable (getter and setter)
	/**
	 * List of all Chats that should be displayed
	 */
	private SetChats AllChats;
	/**
	 * Refer to the active chat, the one where the messages will be send and
	 * fetched
	 */
	private Chat selectedChat;
	/**
	 * Message written by the connected user
	 */
	private Message message;
	/**
	 * List of all messages in the conversation of the current chat
	 */
	private List<Message> displayedMessages;

	// privae variables
	private DateTime lastUpdate;

	@EJB
	MessageDAO messageDao;
	@EJB
	RoomDAO roomDao;
	@EJB
	CRUDimpl crud;

	@PostConstruct
	public void init() {
		// Get list of rooms
		SetRoom commonRooms;
		if (shownUser == null) {
			// Get all rooms of the connecedUser
			commonRooms = roomDao.getRoomsBetween(connectedUser);
		} else {
			// Get all rooms between these two fellows
			commonRooms = roomDao.getRoomsBetween(connectedUser, shownUser);
		}

		// Get the list of chat and select the one we will use on the page
		areThereChats = !commonRooms.isEmpty();
		if (areThereChats) {
			// Get all the chats and use the latest
			AllChats = commonRooms.getAllChats();
			selectedChat = AllChats.latest();
			displayedMessages = selectedChat.getMessages();
			displayedMessages.size();
		} else {
			if (shownUser == null) {
				// If the connected user has never chat with anyone we render
				// null variable and the front end will display emptiness
				// messages accordingly. For now it is impossible to create a
				// chat outside the page dedicated to a special user
				AllChats = null;
				selectedChat = null;
				displayedMessages = null;
			} else {
				// Prepare an empty chat between theses two fellows
				Room r = Prepare.newRoom(Arrays.asList(connectedUser, shownUser));
				Chat c = Prepare.newChat(r, "First meeting between " + shownUser + " and " + connectedUser);
				r.setChat(c);
				AllChats = new SetChats(c);
				selectedChat = c;
				displayedMessages = null;
			}
		}

		// Prepare local variable for new message
		lastUpdate = DateTime.now();
		message = new Message();

	}

	public void sendMessage() {
		message.setDate(DateTime.now());
		message.setChat(selectedChat);
		message.setWriter(connectedUser);

		if (!areThereChats) {
			// If they just met we persist the room
			crud.create(selectedChat.getRoom());
			areThereChats = true;
		}

		crud.create(message);

		FacesMessage message = new FacesMessage("Message sent");
		FacesContext.getCurrentInstance().addMessage(null, message);

		lastMessages();
		this.message.setContent("");
	}

	public void lastMessages() {
		Message m = messageDao.getFirstAfter(selectedChat, lastUpdate);
		if (m != null) {
			if (displayedMessages == null) {
				displayedMessages = new ArrayList<Message>();
			}
			displayedMessages.add(m);
			lastUpdate = m.getDate().plus(10);
		}
	}

	private static class Prepare {
		/**
		 * Prepare a room between Users
		 * 
		 * @param users
		 * @return the room
		 */
		public static Room newRoom(List<User> users) {
			Room res = new Room();
			res.setParticipants(users);
			res.setOngoing(true);
			return res;
		}

		/**
		 * Prepare a chat between Users from a room
		 * 
		 * @param room
		 * @param chatTitle
		 * @return
		 */
		public static Chat newChat(Room room, String chatTitle) {
			Chat res = new Chat();
			res.setRoom(room);
			res.setTitleChat(chatTitle);
			return res;
		}
	}

	/*
	 * Getters and Setters
	 */
	public User getConnectedUser() {
		return connectedUser;
	}

	public void setConnectedUser(User connectedUser) {
		this.connectedUser = connectedUser;
	}

	public boolean isHadMetBefore() {
		return areThereChats;
	}

	public void setHadMetBefore(boolean hadMetBefore) {
		this.areThereChats = hadMetBefore;
	}

	public SetChats getAllChats() {
		return AllChats;
	}

	public void setAllChats(SetChats allChats) {
		AllChats = allChats;
	}

	public Chat getSelectedChat() {
		return selectedChat;
	}

	public void setSelectedChat(Chat selectedChat) {
		this.selectedChat = selectedChat;
	}

	public DateTime getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(DateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public List<Message> getDisplayedMessages() {
		return displayedMessages;
	}

	public void setDisplayedMessages(List<Message> displayedMessages) {
		this.displayedMessages = displayedMessages;
	}

	public User getShownUser() {
		return shownUser;
	}

	public void setShownUser(User shownUser) {
		this.shownUser = shownUser;
	}

}
