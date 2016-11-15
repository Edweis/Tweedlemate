package com.tm.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.tm.forms.search.Searchable;

@Entity
public class Experience extends SuperEntity {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdUser")
	@NotNull
	private User User;
	@Searchable(userFetchPath = "User.myExperience")
	private String Name;
	private String Duration;
	private String Details;

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDuration() {
		return Duration;
	}

	public void setDuration(String duration) {
		Duration = duration;
	}

	public String getDetails() {
		return Details;
	}

	public void setDetails(String details) {
		Details = details;
	}

}
