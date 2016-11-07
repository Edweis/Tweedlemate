package com.tm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;
import org.joda.time.DateTime;

import com.tm.tools.JodaDateTimeConverter;

@Entity
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdWriter")
	@NotNull
	private User Writer;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdChat")
	@NotNull
	private Chat Chat;
	@NotNull(message = "Oups you forgot to write something")
	private String Content;
	@Column(columnDefinition = "TIMESTAMP")
	@Converter(name = "dateTimeConverter", converterClass = JodaDateTimeConverter.class)
	@Convert("dateTimeConverter")
	@NotNull
	private DateTime Date;

	@Override
	public String toString() {
		return Content;
	}

	/*
	 * Getter and setter
	 */
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public User getWriter() {
		return Writer;
	}

	public void setWriter(User writer) {
		Writer = writer;
	}

	public Chat getChat() {
		return Chat;
	}

	public void setChat(Chat chat) {
		Chat = chat;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public DateTime getDate() {
		return Date;
	}

	public void setDate(DateTime date) {
		Date = date;
	}
}