package com.tm.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@NotNull(message = "Please insert a title to initiate the chat")
	@Column(name = "Title")
	private String TitleChat;
	@OneToMany(mappedBy = "Chat")
	private List<Message> Messages;
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "Chat")
	private Room Room;

	public Message getLatestMessage() {
		if (Messages == null) {
			return null;
		}
		if (Messages.isEmpty()) {
			return null;
		}
		Message latest = Messages.get(0);
		for (Message m : Messages) {
			if (m.getDate().isBefore(latest.getDate())) {
				latest = m;
			}
		}
		return latest;
	}

	/*
	 * Getters and setters
	 */
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getTitleChat() {
		return TitleChat;
	}

	public void setTitleChat(String titleChat) {
		TitleChat = titleChat;
	}

	public List<Message> getMessages() {
		return Messages;
	}

	public void setMessages(List<Message> messages) {
		Messages = messages;
	}

	public Room getRoom() {
		return Room;
	}

	public void setRoom(Room room) {
		Room = room;
	}

}