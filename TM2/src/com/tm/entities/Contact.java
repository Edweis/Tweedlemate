package com.tm.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Contact extends SuperEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdUser")
	private User User;
	private String Type;
	private String Value;

	@Override
	public String toString() {
		return "for:" + User + ", " + Type + ", " + Value;
	}

	@NotNull(message = "the contact should have a user")
	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}

	@NotNull(message = "the contact should have a type")
	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	@NotNull(message = "the contact should have a value")
	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}
}