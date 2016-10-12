package com.tm.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

}